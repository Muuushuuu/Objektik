package com.example.objektik.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp


/**
 * SuccessScreen - Un composant pour afficher la page de success (si objet chercher == objet(s) de la photo)
 *
 * @param nomFrancais Le nom en francais de l'objet à chercher
 */
@Composable
fun SuccessScreen(nomFrancais: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Bravo ! Vous avez trouvé : $nomFrancais", color = Color.Green, fontSize = 24.sp)
    }
}