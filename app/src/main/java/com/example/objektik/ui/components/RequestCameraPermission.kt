package com.example.objektik.ui.components

import android.Manifest
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text

/**
 * RequestCameraPermission - Composable qui gère la demande de permission pour accéder à la caméra.
 * Si la permission est accordée, la fonction passée en paramètre est appelée.
 *
 * @param onPermissionGranted Fonction appelée lorsque la permission est accordée.
 */
@Composable
fun RequestCameraPermission(onPermissionGranted: () -> Unit) {
    var permissionGranted by rememberSaveable { mutableStateOf(false) }

    // Lanceur de permission
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted -> permissionGranted = isGranted }
    )

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    if (permissionGranted) {
        onPermissionGranted()
    } else {
        Text("Permission refusée.")
    }
}