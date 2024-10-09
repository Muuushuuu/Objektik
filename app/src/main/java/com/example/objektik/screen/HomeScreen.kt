package com.example.objektik.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.objektik.ui.components.CustomButton
import com.example.objektik.ui.components.CustomPopup
import com.example.objektik.ui.theme.BluePrimary
import com.example.objektik.ui.theme.ErrorColor
import com.example.objektik.ui.theme.GreenAccent
import com.example.objektik.ui.theme.SuccessColor

@Composable
fun HomeScreen(onStartClick: () -> Unit) {
    // Test de mes components
    Row (modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Deux popups
        Column {
            // Popup de succès
            CustomPopup(
                text = "Action réussie !",
                borderColor = SuccessColor,
                textColor = SuccessColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Popup d'erreur
            CustomPopup(
                text = "Une erreur est survenue.",
                borderColor = ErrorColor,
                textColor = ErrorColor
            )
        }
        // Deux boutons
        Column {
            // Bouton avec couleur primaire
            CustomButton(
                text = "Bouton Primary",
                onClick = onStartClick,
                backgroundColor = BluePrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Bouton avec couleur accent
            CustomButton(
                text = "Bouton Accent",
                onClick = onStartClick,
                backgroundColor = GreenAccent
            )
        }

    }
}

