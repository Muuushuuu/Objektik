package com.example.objektik.screen


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.objektik.services.PointsManager
import com.example.objektik.ui.components.RequestCameraPermission
import com.example.objektik.ui.components.CameraCaptureScreen
import com.example.objektik.services.getRandomObjectFromServer
import com.example.objektik.ui.theme.BluePrimary
import org.json.JSONObject

enum class RecognitionState {
    Idle,      // Pas d'action en cours
    Loading,   // Analyse en cours
    Success,   // Analyse réussie
    Error      // Analyse échouée
}

/**
 * GameScreen - C'est ici que l'utilisateur va prendre une photo pour deviner l'objet.
 * On gère aussi la permission de la caméra ici.
 *
 * @param navController Le truc qui permet de passer d'un écran à un autre (succès ou échec).
 */
@Composable
fun GameScreen(navController: NavHostController) {

    val context = LocalContext.current

    val pointsManager = remember { PointsManager(context) } // Instance unique de PointsManager
    var points by remember { mutableStateOf(pointsManager.getPoints()) } // Récupère les points au lancement

    var shouldLoadNewWord by remember { mutableStateOf(true) } // verifie si le mot dois changer ou pas
    val recognitionState = remember { mutableStateOf(RecognitionState.Idle) }
    // Booléen pour savoir si la permission caméra est accorder
    var permissionGranted by remember { mutableStateOf(false) }

    // Contient l'objet aléatoire du json du serveur
    var randomObject by remember { mutableStateOf<JSONObject?>(null) }

    // URL du fichier JSON
    val jsonUrl = "https://mcida.fr/objektik.json"


    // Appelle l'API pour trouver un objet aléatoire quand l'écran est chargé
    LaunchedEffect(shouldLoadNewWord) {
        if (shouldLoadNewWord) {
            randomObject = getRandomObjectFromServer(jsonUrl)
            shouldLoadNewWord = false // Réinitialise pour éviter des appels inutiles
        }
    }

    fun resetGame(loadNewWord: Boolean) {
        recognitionState.value = RecognitionState.Idle
        shouldLoadNewWord = loadNewWord
    }

    when (recognitionState.value) {
        RecognitionState.Idle -> {
            if (permissionGranted) {
                CameraCaptureScreen(
                    randomObject = randomObject,
                    navController = navController,
                    recognitionState = recognitionState,
                    points = points
                )
            } else {
                RequestCameraPermission(onPermissionGranted = {
                    permissionGranted = true
                })
            }
        }
        RecognitionState.Loading -> {
            // Page de chargement simple
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = BluePrimary,
                    strokeWidth = 4.dp
                )
            }
        }
        RecognitionState.Success -> {
            SuccessScreen(
                nomFrancais = randomObject?.getString("nom_francais") ?: "",
                onStartClick = { resetGame(loadNewWord = true) },
                onAddPoints = { addedPoints ->
                    points += addedPoints // Met à jour les points dans l'UI
                    pointsManager.addPoints(addedPoints) // Sauvegarde dans SharedPreferences
                },
                points = points
            )
        }
        RecognitionState.Error -> {
            ErrorScreen(
                nomFrancais = randomObject?.getString("nom_francais") ?: "",
                onStartClick = { resetGame(loadNewWord = false) },
            )
        }
    }
}