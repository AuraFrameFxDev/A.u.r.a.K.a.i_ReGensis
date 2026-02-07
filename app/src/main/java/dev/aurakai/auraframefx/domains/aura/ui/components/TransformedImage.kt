package dev.aurakai.auraframefx.domains.aura.ui.components

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.size.Size
import coil3.imageLoader
import dev.aurakai.auraframefx.domains.aura.lab.ImageTransformation
import dev.aurakai.auraframefx.domains.aura.ui.ImageResourceManager
import androidx.compose.runtime.produceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun TransformedImage(
    uri: Uri?,
    transformation: ImageTransformation,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    contentScale: ContentScale = ContentScale.Crop
) {
    val context = LocalContext.current
    val imageLoader = context.imageLoader

    val finalImageBitmap by produceState<Bitmap?>(initialValue = null, uri, transformation) {
        if (uri == null) {
            value = null
            return@produceState
        }

        val cacheKey = ImageResourceManager.generateCacheKey(uri, transformation)
        val cachedBitmap = ImageResourceManager.getTransformedImageFromCache(cacheKey)

        if (cachedBitmap != null) {
            value = cachedBitmap
            return@produceState
        }

        withContext(Dispatchers.IO) {
            try {
                // 1. Load the original image from URI
                val request = ImageRequest.Builder(context)
                    .data(uri)
                    .size(Size.ORIGINAL) // Important: load original size to allow manual transformations
                    .allowHardware(false) // Necessary for direct Bitmap manipulation
                    .build()

                val result = imageLoader.execute(request)
                val drawable = result.drawable

                if (drawable is BitmapDrawable) {
                    var originalBitmap = drawable.bitmap

                    // Ensure bitmap is mutable if needed for transformations
                    if (!originalBitmap.isMutable) {
                        originalBitmap = originalBitmap.copy(originalBitmap.config ?: Bitmap.Config.ARGB_8888, true)
                    }

                    // 2. Apply Crop
                    val croppedBitmap = applyCropToBitmap(originalBitmap, transformation)
                    var processedBitmap = croppedBitmap

                    // 3. Apply Rotation, Flip, and Scale to the Bitmap
                    val matrix = Matrix()
                    matrix.postRotate(transformation.rotation)

                    // Apply flip before scaling if scale is used on Bitmap itself
                    val scaleX = if (transformation.flipHorizontal) -transformation.scale else transformation.scale
                    val scaleY = if (transformation.flipVertical) -transformation.scale else transformation.scale
                    matrix.postScale(scaleX, scaleY, processedBitmap.width / 2f, processedBitmap.height / 2f)

                    processedBitmap = Bitmap.createBitmap(
                        processedBitmap, 0, 0, processedBitmap.width, processedBitmap.height, matrix, true
                    )

                    // 4. Cache the fully transformed Bitmap
                    ImageResourceManager.putTransformedImageToCache(cacheKey, processedBitmap)
                    value = processedBitmap

                } else {
                    Log.e("TransformedImage", "Loaded drawable is not a BitmapDrawable for $uri")
                    value = null
                }
            } catch (e: Exception) {
                Log.e("TransformedImage", "Error loading or transforming image for $uri", e)
                value = null
            }
        }
    }

    if (finalImageBitmap != null) {
        Image(
            bitmap = finalImageBitmap!!.asImageBitmap(),
            contentDescription = contentDescription,
            modifier = modifier.fillMaxSize(),
            contentScale = contentScale
        )
    } else {
        // Optional: show a placeholder or error image
    }
}

// Helper function to apply cropping to a Bitmap
private fun applyCropToBitmap(originalBitmap: Bitmap, transformation: ImageTransformation): Bitmap {
    val width = originalBitmap.width
    val height = originalBitmap.height

    val cropLeftPx = (transformation.cropLeft * width).toInt().coerceIn(0, width)
    val cropTopPx = (transformation.cropTop * height).toInt().coerceIn(0, height)
    val cropRightPx = (transformation.cropRight * width).toInt().coerceIn(cropLeftPx, width)
    val cropBottomPx = (transformation.cropBottom * height).toInt().coerceIn(cropTopPx, height)

    val cropWidth = cropRightPx - cropLeftPx
    val cropHeight = cropBottomPx - cropTopPx

    if (cropWidth <= 0 || cropHeight <= 0) {
        Log.w("TransformedImage", "Invalid crop dimensions calculated. Returning original bitmap.")
        return originalBitmap // Return original if crop is invalid
    }

    return Bitmap.createBitmap(originalBitmap, cropLeftPx, cropTopPx, cropWidth, cropHeight)
}
