package com.example.objektik.screen

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.objektik.ui.components.RequestCameraPermission
import com.example.objektik.ui.components.CameraCaptureScreen
import com.example.objektik.services.getRandomObjectFromServer
import org.json.JSONObject

/**
 * GameScreen - C'est ici que l'utilisateur va prendre une photo pour deviner l'objet.
 * On gère aussi la permission de la caméra ici.
 *
 * @param navController Le truc qui permet de passer d'un écran à un autre (succès ou échec).
 */
@Composable
fun GameScreen(navController: NavHostController) {
    // Booléen pour savoir si la permission caméra est accorder
    var permissionGranted by remember { mutableStateOf(false) }

    // Contient l'objet aléatoire du json du serveur
    var randomObject by remember { mutableStateOf<JSONObject?>(null) }

    // URL du fichier JSON
    val jsonUrl = "https://mcida.fr/objektik.json"

    val context = LocalContext.current

    // Appelle l'API pour trouver un objet aléatoire quand l'écran est chargé
    LaunchedEffect(Unit) {
        randomObject = getRandomObjectFromServer(jsonUrl)
    }

    // Si la permission caméra est OK => on affiche l'écran pour capturer la photo
    if (permissionGranted) {
        CameraCaptureScreen(randomObject = randomObject, navController = navController)
    } else {
        // Sinon on demande la permission à l'utilisateur
        RequestCameraPermission(onPermissionGranted = {
            permissionGranted = true
        })
    }
}