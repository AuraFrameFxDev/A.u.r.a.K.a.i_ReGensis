package dev.aurakai.auraframefx.domains.aura.ui.components

import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.FolderOpen
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil3.compose.rememberAsyncImagePainter
import dev.aurakai.auraframefx.domains.aura.lab.ImageTransformation
import java.io.File

@Composable
fun ImagePicker(onImageSelected: (Uri?, ImageTransformation) -> Unit) {
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var showConfirmationDialog by remember { mutableStateOf(false) }
    var showTransformationDialog by remember { mutableStateOf(false) }
    var currentImageTransformation by remember { mutableStateOf(ImageTransformation()) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
            selectedImageUri = it
            if (it != null) {
                showTransformationDialog = true
                currentImageTransformation = ImageTransformation()
            }
        }
    )

    val cameraImageUri = remember { mutableStateOf<Uri?>(null) }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            if (success) {
                selectedImageUri = cameraImageUri.value
                showTransformationDialog = true
                currentImageTransformation = ImageTransformation()
            } else {
                cameraImageUri.value = null
            }
        }
    )

    val fileManagerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {
            selectedImageUri = it
            if (it != null) {
                showTransformationDialog = true
                currentImageTransformation = ImageTransformation()
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

        selectedImageUri?.let { uri ->
            if (!showTransformationDialog) {
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .size(100.dp)
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
                    }) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showConfirmationDialog = false
                        selectedImageUri = null
                    }) {
                        Text("Cancel")
                    }
                }
            )
        }

        if (showTransformationDialog && selectedImageUri != null) {
            Dialog(onDismissRequest = { showTransformationDialog = false }) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text("Adjust Image", style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(16.dp))
                        Box(modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()) {
                            Image(
                                painter = rememberAsyncImagePainter(selectedImageUri),
                                contentDescription = "Image for Transformation",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                            ImageCropOverlay(
                                imageTransformation = currentImageTransformation,
                                onTransformationChange = { currentImageTransformation = it }
                            )
                        }

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
                                onImageSelected(selectedImageUri, currentImageTransformation)
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
