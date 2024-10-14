package com.example.objektik.ui.components

import android.net.Uri
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageCapture.OutputFileOptions
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.zIndex
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.example.objektik.R
import com.example.objektik.ui.theme.BluePrimary
import java.io.File

@Composable
fun CameraCaptureScreen() {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var detectedObjects by remember { mutableStateOf<List<String>>(emptyList()) }
    val imageCapture = remember { ImageCapture.Builder().build() }
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    // Utilisation d'un Box pour superposer la popup par-dessus l'écran
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Cercle en arrière-plan
        Box(
            modifier = Modifier
                .width(700.dp)
                .absoluteOffset(x = 0.dp, y = 550.dp)  // Position absolue en bas
                .aspectRatio(1f) // Même Hauteur que Longeur
                .background(
                    color = Color(0xFF76D7C4),
                    shape = CircleShape
                )
                .zIndex(-1f)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Carte avec l'aperçu de la caméra
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(8.dp)
                    .zIndex(-2f),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, BluePrimary),
            ) {
                // Vue pour la prévisualisation de la caméra
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { ctx ->
                        val previewView = PreviewView(ctx)
                        val cameraProvider = cameraProviderFuture.get()
                        val preview = Preview.Builder().build().also {
                            it.setSurfaceProvider(previewView.surfaceProvider)
                        }
                        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                        try {
                            cameraProvider.unbindAll()
                            cameraProvider.bindToLifecycle(
                                ctx as LifecycleOwner, cameraSelector, preview, imageCapture
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        previewView
                    },
                )
            }

            // Bouton de capture de la photo avec style circulaire
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(75.dp), // Taille fixe du bouton en bas
                horizontalArrangement = Arrangement.Center
            ) {
                CustomButtonWithIcon(
                    text = "Prendre une photo",
                    onClick = {
                        val file = File(context.externalMediaDirs.first(), "${System.currentTimeMillis()}.jpg")
                        val outputOptions = OutputFileOptions.Builder(file).build()

                        imageCapture.takePicture(
                            outputOptions, ContextCompat.getMainExecutor(context),
                            object : ImageCapture.OnImageSavedCallback {
                                override fun onError(exc: ImageCaptureException) {
                                    exc.printStackTrace()
                                }

                                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                    imageUri = Uri.fromFile(file)

                                    val bitmap = uriToBitmap(context, imageUri!!)
                                    if (bitmap != null) {
                                        detectObjectsWithGoogleVision(context, bitmap,
                                            onSuccess = { objects ->
                                                detectedObjects = objects
                                            },
                                            onError = { error ->
                                                Log.e("GoogleVision", "Erreur lors de la détection d'objets", error)
                                            }
                                        )
                                    }
                                }
                            }
                        )
                    },
                    backgroundColor = BluePrimary,
                    textColor = Color.White,
                    imageResource = R.drawable.ic_camera // Icône de l'appareil photo
                )
            }
        }

        // Affiche la popup avec la liste des objets détectés si elle n'est pas vide, au-dessus de tout le reste
        if (detectedObjects.isNotEmpty()) {
            CustomPopup(
                text = detectedObjects.joinToString(", "),
                borderColor = BluePrimary,
                textColor = BluePrimary,
                modifier = Modifier
                    .align(Alignment.TopCenter) // Aligne la popup au centre en haut
                    .padding(top = 16.dp) // Ajoute un padding en haut pour la visibilité
            )
        }
    }
}

@Composable
fun CustomButtonWithIcon(
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color,
    textColor: Color = Color.White,
    modifier: Modifier = Modifier,
    imageResource: Int? = null
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .size(80.dp), // Taille du bouton
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        imageResource?.let {
            Icon(
                painter = painterResource(id = it),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}