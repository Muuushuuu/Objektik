package com.example.objektik.services

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

/**
 * Fonction pour détecter les objets via Google Vision API en utilisant gRPC.
 *
 * @param context Contexte de l'application, utilisé pour accéder aux ressources.
 * @param bitmap Image en Bitmap à analyser.
 * @param onSuccess Callback appelé en cas de succès avec une liste des objets détectés.
 * @param onError Callback appelé en cas d'erreur avec l'exception correspondante.
 */fun detectObjectsWithGoogleVision(
    context: Context,
    bitmap: Bitmap,
    onSuccess: (List<String>) -> Unit,
    onError: (Exception) -> Unit
) {
    try {
        // Log de début du processus de détection
        Log.d("GoogleVision", "Début de la détection d'objets avec Google Vision")

        // Charger les informations d'identification du compte de service depuis le fichier JSON
        Log.d("GoogleVision", "Chargement des informations d'identification du compte de service")
        val inputStream: InputStream = context.resources.openRawResource(R.raw.objektikgv)
        val credentials = GoogleCredentials.fromStream(inputStream)

        // Créer un client Vision en utilisant les informations d'identification
        Log.d("GoogleVision", "Création du client Google Vision")
        val client = ImageAnnotatorClient.create(
            ImageAnnotatorSettings.newBuilder()
                .setCredentialsProvider { credentials }
                .build()
        )

        // Convertir le bitmap en ByteString pour l'envoyer à l'API Vision
        Log.d("GoogleVision", "Conversion du bitmap en ByteString")
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val imageBytes = ByteString.copyFrom(byteArrayOutputStream.toByteArray())
        val image = Image.newBuilder().setContent(imageBytes).build()

        // Configuration de la requête pour détecter les étiquettes (LABEL_DETECTION)
        Log.d("GoogleVision", "Configuration de la demande d'analyse pour la détection des étiquettes")
        val feature = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build()
        val request = AnnotateImageRequest.newBuilder()
            .addFeatures(feature)
            .setImage(image)
            .build()

        // Envoi de la requête au service Google Vision
        Log.d("GoogleVision", "Envoi de la requête au service Google Vision")
        val response = client.batchAnnotateImages(listOf(request))
        val labels = response.responsesList[0].labelAnnotationsList

        // Extraction des descriptions des objets détectés
        Log.d("GoogleVision", "==================================================")
        Log.d("GoogleVision", "Extraction des objets détectés")
        val detectedObjects = labels.map { it.description }
        Log.d("GoogleVision", "Objets détectés: $detectedObjects")
        // Appel du callback de succès avec les objets détectés
        onSuccess(detectedObjects)
        Log.d("GoogleVision", "==================================================")

        // Fermer le client après utilisation ==> libérer les ressources
        Log.d("GoogleVision", "Fermeture du client Google Vision")
        client.shutdown()

    } catch (e: Exception) {
        // Gestion des erreurs et appel du callback d'erreur
        Log.e("GoogleVision", "Erreur lors de la détection d'objets", e)
        onError(e)
    }
}

/**
 * Convertit un Uri en Bitmap.
 *
 * @param context Contexte de l'application, utilisé pour accéder au contentResolver.
 * @param uri URI de l'image à convertir.
 * @return Un objet Bitmap si la conversion est réussie, sinon null.
 */
fun uriToBitmap(context: Context, uri: Uri): Bitmap? {
    val inputStream = context.contentResolver.openInputStream(uri)
    return BitmapFactory.decodeStream(inputStream)
}