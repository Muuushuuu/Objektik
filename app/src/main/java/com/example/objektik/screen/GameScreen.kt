package com.example.objektik.screen

import androidx.compose.runtime.*
import com.example.objektik.ui.components.RequestCameraPermission
import com.example.objektik.ui.components.CameraCaptureScreen

@Composable
fun GameScreen() {
    // Gestion de l'état de la permission avec remember et mutableStateOf
    var permissionGranted by remember { mutableStateOf(false) }

    // Si la permission est accordée, afficher la caméra, sinon demander la permission
    if (permissionGranted) {
        // Affiche l'interface de capture de photo
        CameraCaptureScreen()
    } else {
        // Demande la permission d'utiliser la caméra
        RequestCameraPermission(onPermissionGranted = {
            permissionGranted = true  // Mettre à jour l'état lorsque la permission est accordée
        })
    }
}