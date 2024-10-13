package com.example.objektik.ui.components

import android.net.Uri
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageCapture.OutputFileOptions
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import java.io.File

@Composable
fun CameraCaptureScreen() {
    val context = LocalContext.current // Contexte Android
    var imageUri by remember { mutableStateOf<Uri?>(null) } // URI de la photo capturée (dépars null)
    val imageCapture = remember { ImageCapture.Builder().build() } // Prépare la capture d'image
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) } // Prépare l'accès à la caméra

    Box {
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx) // Vue pour la prevu de la caméra
                val cameraProvider = cameraProviderFuture.get() // Accèder à la caméra

                val preview = Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider) // Affiche la prevu
                }

                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA // Caméra arrière

                try {
                    // Détache les précédente liaison, lie la caméra au cycle de vie et active la prevu
                    cameraProvider.unbindAll()
                    cameraProvider.bindToLifecycle(
                        ctx as LifecycleOwner, cameraSelector, preview, imageCapture
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                previewView // Retourn la vue de prevu
            },
            modifier = Modifier.fillMaxSize()
        )

        Button(onClick = {
            // Crée un fichier pour stocker la photo capturé
            val file = File(context.externalMediaDirs.first(), "${System.currentTimeMillis()}.jpg")
            val outputOptions = OutputFileOptions.Builder(file).build() // Configure du lieud e sauvegard

            // Capture la photo
            imageCapture.takePicture(
                outputOptions, ContextCompat.getMainExecutor(context), // Sauvegarde l'image sur le fichier
                object : ImageCapture.OnImageSavedCallback {
                    override fun onError(exc: ImageCaptureException) {
                        exc.printStackTrace()
                    }

                    override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                        imageUri = Uri.fromFile(file)  // Met à jour l'URI
                    }
                }
            )
        }) {
            Text("Prendre une photo")
        }


        if (imageUri != null) {
            Text("Photo capturée à : $imageUri")
        }
    }
}