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
 * Bouton personnalisé avec icône, qu'on utilise pour prendre la photo.
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
    Button(
        onClick = onClick,
        modifier = modifier
            .size(80.dp),
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
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