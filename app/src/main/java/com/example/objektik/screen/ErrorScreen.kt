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
 * ErrorScreen - Un composant pour afficher la page d'erreur (en cas de différence entre objet(s) de la photo et objet chercher)
 *
 * @param nomFrancais Le nom en francais de l'objet à chercher
 */
@Composable
fun ErrorScreen(nomFrancais: String) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "Erreur ! Vous n'avez pas trouvé : $nomFrancais", color = Color.Red, fontSize = 24.sp)
    }
}