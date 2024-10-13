package com.example.objektik.ui.components

import android.Manifest
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Text

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