package com.example.objektik.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment

// Définir la fonction @Composable pour l'écran d'accueil
@Composable
fun HomeScreen(onStartClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),  // Marges autour du contenu
        verticalArrangement = Arrangement.Center,  // Centrer verticalement
        horizontalAlignment = Alignment.CenterHorizontally  // Centrer horizontalement
    ) {
        // Texte d'accueil
        Text(text = "Bienvenue dans l'application !")

        Spacer(modifier = Modifier.height(24.dp))  // Espace entre le texte et le bouton

        // Bouton pour démarrer
        Button(onClick = onStartClick) {
            Text(text = "Commencer")
        }
    }
}

