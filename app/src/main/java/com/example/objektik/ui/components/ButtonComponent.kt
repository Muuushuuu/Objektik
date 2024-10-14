package com.example.objektik.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

/**
 * CustomButton - Un bouton réutilisable avec un texte, une image et une couleur de fond personnalisés.
 * Peut être utilisé pour des boutons avec des couleurs différentes.
 *
 * @param text Le texte affiché à l'intérieur du bouton
 * @param onClick Action déclenchée lors du clic sur le bouton
 * @param backgroundColor La couleur de fond du bouton
 * @param textColor La couleur du texte affiché dans le bouton (par défaut, blanc)
 * @param imageResource L'icône à afficher à gauche du texte (optionnel)
 * @param modifier Un paramètre optionnel pour personnaliser le layout du bouton
 */
@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color,
    textColor: Color = Color.White,
    modifier: Modifier = Modifier,
    imageResource: Int? = null // Ajout d'une icône en option
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        imageResource?.let {
            Icon(
                painter = painterResource(id = it),
                contentDescription = null,
                modifier = Modifier.size(24.dp).padding(end = 8.dp), // Taille et padding de l'icône
                tint = Color.White
            )
        }
        Text(text = text, color = textColor)
    }
}