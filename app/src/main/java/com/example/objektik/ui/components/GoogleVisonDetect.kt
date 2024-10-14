package com.example.objektik.ui.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.example.objektik.R
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.vision.v1.AnnotateImageRequest
import com.google.cloud.vision.v1.Feature
import com.google.cloud.vision.v1.Image
import com.google.cloud.vision.v1.ImageAnnotatorClient
import com.google.cloud.vision.v1.ImageAnnotatorSettings
import com.google.protobuf.ByteString
import java.io.ByteArrayOutputStream
import java.io.InputStream

// Fonction pour détecter les objets via Google Vision API en utilisant gRPC
fun detectObjectsWithGoogleVision(
    context: Context,
    bitmap: Bitmap,
    onSuccess: (List<String>) -> Unit,
    onError: (Exception) -> Unit
) {
    try {
        // Charger le fichier JSON des informations d'identification du compte de service
        val inputStream: InputStream = context.resources.openRawResource(R.raw.objektikgv)
        val credentials = GoogleCredentials.fromStream(inputStream)

        // Créer un client Vision en utilisant les informations d'identification
        val client = ImageAnnotatorClient.create(
            ImageAnnotatorSettings.newBuilder()
            .setCredentialsProvider { credentials }
            .build()
        )

        // Convertir le bitmap en ByteString pour l'envoyer à l'API Vision
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageBytes = ByteString.copyFrom(byteArrayOutputStream.toByteArray())
        val image = Image.newBuilder().setContent(imageBytes).build()

        // Configurer la demande d'analyse
        val feature = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build()
        val request = AnnotateImageRequest.newBuilder()
            .addFeatures(feature)
            .setImage(image)
            .build()

        // Envoyer la requête
        val response = client.batchAnnotateImages(listOf(request))
        val labels = response.responsesList[0].labelAnnotationsList

        // Extraire les descriptions des objets détectés
        val detectedObjects = labels.map { it.description }
        onSuccess(detectedObjects)

        // Fermer le client après utilisation
        client.shutdown()

    } catch (e: Exception) {
        Log.e("GoogleVision", "Erreur lors de la détection d'objets", e)
        onError(e)
    }
}

// Fonction utilitaire pour convertir Bitmap en ByteString
fun bitmapToByteString(bitmap: Bitmap): ByteString {
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
    return ByteString.copyFrom(byteArrayOutputStream.toByteArray())
}

fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
    val inputStream = context.contentResolver.openInputStream(uri)
    return BitmapFactory.decodeStream(inputStream)
}