package com.example.objektik.ui.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * CustomButton - Un bouton réutilisable avec un texte et une couleur de fond personnalisés.
 * Peut être utilisé pour des boutons avec des couleurs différentes.
 *
 * @param text Le texte affiché à l'intérieur du bouton
 * @param onClick Action déclenchée lors du clic sur le bouton
 * @param backgroundColor La couleur de fond du bouton
 * @param textColor La couleur du texte affiché dans le bouton (par défaut, blanc)
 * @param modifier Un paramètre optionnel pour personnaliser le layout du bouton
 */
@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color,
    textColor: Color = Color.White,  // Par défaut, le texte est en blanc
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        )
    ) {
        Text(text = text, color = textColor)
    }
}