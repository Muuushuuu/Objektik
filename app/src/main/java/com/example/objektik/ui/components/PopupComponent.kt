package com.example.objektik.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


/**
 * CustomPopup - Un composant pour afficher une popup après un succès ou une erreur
 * La popup a une bordure colorée et un texte qui changent en fonction du type (succès ou erreur)
 *
 * @param text Le texte affiché à l'intérieur de la popup
 * @param borderColor La couleur de la bordure de la popup
 * @param textColor La couleur du texte affiché dans la popup
 * @param modifier Un paramètre (optionnel) pour appliquer des modifications supplémentaires au composant
 */
@Composable
fun CustomPopup(
    text: String,
    borderColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, borderColor),
        modifier = modifier.padding(16.dp)
    ) {
        Box(modifier = Modifier.padding(16.dp)) {
            Text(text = text, color = textColor)
        }
    }
}