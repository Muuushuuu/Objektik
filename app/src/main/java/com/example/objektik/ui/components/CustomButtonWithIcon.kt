package com.example.objektik.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

/**
 * CustomButtonWithIcon - Un composant de bouton personnalisé avec une icône optionnelle.
 *
 * Utilisé principalement pour prendre une photo ou exécuter une action liée à un bouton avec un style spécifique.
 *
 * @param text Texte associé au bouton (non utilisé dans cette implémentation, mais prévu pour des extensions futures).
 * @param onClick Fonction appelée lors du clic sur le bouton.
 * @param backgroundColor Couleur de fond du bouton.
 * @param textColor Couleur du texte du bouton (par défaut : blanc).
 * @param modifier Modificateur pour personnaliser l'apparence et le comportement du bouton.
 * @param imageResource Identifiant de la ressource d'image utilisée comme icône (optionnel).
 */
@Composable
fun CustomButtonWithIcon(
    text: String,
    onClick: () -> Unit,
    backgroundColor: Color,
    textColor: Color = Color.White,
    modifier: Modifier = Modifier,
    imageResource: Int? = null
) {
    // Bouton principal
    Button(
        onClick = onClick,
        modifier = modifier
            .size(80.dp),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        // Affiche l'icône uniquement si une image est fournie
        imageResource?.let {
            Icon(
                painter = painterResource(id = it),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}