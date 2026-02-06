
package dev.aurakai.auraframefx.aura.ui.components

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import dev.aurakai.auraframefx.aura.lab.ImageTransformation // Import ImageTransformation
import java.io.File

@Composable
fun ImagePicker(onImageSelected: (Uri?, ImageTransformation) -> Unit) {
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var showConfirmationDialog by remember { mutableStateOf(false) }
    var showTransformationDialog by remember { mutableStateOf(false) } // State for transformation dialog
    var currentImageTransformation by remember { mutableStateOf(ImageTransformation()) } // Default transformation

    // Launcher for Gallery/Photos
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            selectedImageUri = it
            if (it != null) {
                showTransformationDialog = true // Show transformation dialog instead of confirmation
                currentImageTransformation = ImageTransformation() // Reset transformation for new image
            }
        }
    )

    // Launcher for Camera
    val cameraImageUri = remember { mutableStateOf<Uri?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                selectedImageUri = cameraImageUri.value
                showTransformationDialog = true // Show transformation dialog
                currentImageTransformation = ImageTransformation() // Reset transformation
            } else {
                cameraImageUri.value = null
            }
        }
    )

    // Launcher for File Manager
    val fileManagerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            selectedImageUri = it
            if (it != null) {
                showTransformationDialog = true // Show transformation dialog
                currentImageTransformation = ImageTransformation() // Reset transformation
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Select an Image",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Image Preview (for selected image before transformation)
        selectedImageUri?.let { uri ->
            if (!showTransformationDialog) { // Only show preview if transformation dialog is not open
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .size(100.dp) // Smaller preview initially
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = { galleryLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }) {
                Icon(Icons.Default.Image, contentDescription = "Gallery")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Gallery")
            }
            Button(onClick = {
                val uri = createImageUri(context)
                cameraImageUri.value = uri
                cameraLauncher.launch(uri)
            }) {
                Icon(Icons.Default.CameraAlt, contentDescription = "Camera")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Camera")
            }
            Button(onClick = { fileManagerLauncher.launch("image/*") }) {
                Icon(Icons.Default.FolderOpen, contentDescription = "File Manager")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Files")
            }
        }

        if (showConfirmationDialog && selectedImageUri != null) {
            AlertDialog(
                onDismissRequest = { showConfirmationDialog = false },
                title = { Text("Confirm Image Selection") },
                text = { Text("Do you want to use this image?") },
                confirmButton = {
                    TextButton(onClick = {
                        showConfirmationDialog = false
                        // This part will be replaced by transformation handling
                        // onImageSelected(selectedImageUri)
                    }) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showConfirmationDialog = false
                        selectedImageUri = null // Clear selection if dismissed
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }

        // Image Transformation Dialog
        if (showTransformationDialog && selectedImageUri != null) {
            Dialog(onDismissRequest = { showTransformationDialog = false }) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text("Adjust Image", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(16.dp))
                        // Image preview with overlay for cropping
                        Box(modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()) {
                            Image(
                                painter = rememberAsyncImagePainter(selectedImageUri),
                                contentDescription = "Image for Transformation",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit // Use Fit to see full image before crop
                            )
                            ImageCropOverlay(
                                imageTransformation = currentImageTransformation,
                                onTransformationChange = { currentImageTransformation = it }
                            )
                        }

                        // Transformation controls
                        ImageTransformationPanel(
                            currentTransformation = currentImageTransformation,
                            onTransformationChange = { currentImageTransformation = it }
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Button(onClick = { showTransformationDialog = false; selectedImageUri = null }) {
                                Text("Cancel")
                            }
                            Button(onClick = {
                                showTransformationDialog = false
                                onImageSelected(selectedImageUri, currentImageTransformation) // Pass transformation params
                            }) {
                                Text("Apply & Select")
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun createImageUri(context: Context): Uri {
    val imagePath = File(context.cacheDir, "camera_image_${System.currentTimeMillis()}.jpg")
    return Uri.fromFile(imagePath)
}
