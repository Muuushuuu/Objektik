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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.objektik.R
import com.example.objektik.screen.RecognitionState
import com.example.objektik.services.detectObjectsWithGoogleVision
import com.example.objektik.services.uriToBitmap
import com.example.objektik.ui.theme.AccentText
import com.example.objektik.ui.theme.BluePrimary
import com.example.objektik.ui.theme.GreenAccent
import org.json.JSONObject
import java.io.File

@Composable
fun CameraCaptureScreen(randomObject: JSONObject?, navController: NavController, recognitionState: MutableState<RecognitionState>, points: Int) {
    Log.d("randomObject", randomObject.toString())

    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }  // On va stocker l'URI de l'image capturée ici
    val imageCapture = remember { ImageCapture.Builder().build() }  // Crée l'objet pour capturer des images
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }  // On récupère la caméra

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Carte qui contient l'aperçu de la caméra
            Card(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(16.dp),
                border = BorderStroke(2.dp, BluePrimary),
            ) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { ctx ->
                        val previewView = PreviewView(ctx)  // On affiche la preview
                        val cameraProvider = cameraProviderFuture.get()
                        val preview = Preview.Builder().build().also {
                            it.setSurfaceProvider(previewView.surfaceProvider)  // On lie la preview de la caméra
                        }
                        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA  // On choisi la caméra arrière
                        try {
                            cameraProvider.unbindAll()  // On "libère" tout ce qui pourrait déjà être lié à la caméra
                            cameraProvider.bindToLifecycle(
                                ctx as LifecycleOwner, cameraSelector, preview, imageCapture  // On associe la caméra à l'aperçu
                            )
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        previewView  // On renvoie la vue qui affiche la caméra
                    },
                )
            }

            // Ligne pour afficher le bouton "Prendre une photo"
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .height(75.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                CustomButtonWithIcon(
                    text = "Prendre une photo",
                    onClick = {
                        recognitionState.value = RecognitionState.Loading // Passe en état de chargement
                        // On prépare le fichier où la photo va être sauvegardée
                        val file = File(context.externalMediaDirs.first(), "${System.currentTimeMillis()}.jpg")
                        val outputOptions = OutputFileOptions.Builder(file).build()

                        // Capture de l'image
                        imageCapture.takePicture(
                            outputOptions, ContextCompat.getMainExecutor(context),
                            object : ImageCapture.OnImageSavedCallback {
                                override fun onError(exc: ImageCaptureException) {
                                    exc.printStackTrace()
                                }

                                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                    imageUri = Uri.fromFile(file)  // On récupère l'URI du fichier

                                    // On transforme l'URI en Bitmap pour l'envoyer à Google Vision
                                    val bitmap = uriToBitmap(context, imageUri!!)
                                    if (bitmap != null && randomObject != null) {
                                        detectObjectsWithGoogleVision(context, bitmap,
                                            onSuccess = { objects ->
                                                // On récupère les noms anglais depuis JSON
                                                val nomsAnglais = randomObject.getJSONArray("noms_anglais")

                                                // Transforme en liste
                                                val nomsAnglaisList = (0 until nomsAnglais.length()).map { nomsAnglais.getString(it) }

                                                // Vérifie si un objet détecté correspond à un nom dans la liste
                                                val objectFound = objects.any { it in nomsAnglaisList }

                                                // Si c'est bon, on va sur la page de succès, sinon sur la page d'erreur
//                                                if (objectFound) {
//                                                    navController.navigate("success/${randomObject.getString("nom_francais")}")
//                                                } else {
//                                                    navController.navigate("error/${randomObject.getString("nom_francais")}")
//                                                }
                                                if (objectFound) {
                                                    recognitionState.value = RecognitionState.Success
                                                } else {
                                                    recognitionState.value = RecognitionState.Error
                                                }
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
                    imageResource = R.drawable.ic_camera
                )
            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.trophy_accent),
                    contentDescription = "Icône de trophée",
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.size(16.dp, 0.dp))
                Text(
                    text = "$points Trophées",
                    color = GreenAccent,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        // Objet aléatoire a capturer
        if (randomObject != null) {
            CustomPopup(
                text = "Vous devez trouver : ${randomObject.getString("nom_francais")}",
                borderColor = BluePrimary,
                textColor = BluePrimary,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}