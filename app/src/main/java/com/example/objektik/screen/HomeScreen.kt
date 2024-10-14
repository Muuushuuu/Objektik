package com.example.objektik.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.objektik.R
import com.example.objektik.ui.components.CustomButton
import com.example.objektik.ui.theme.AccentText
import com.example.objektik.ui.theme.BluePrimary
import com.example.objektik.ui.theme.MyTypography
import com.example.objektik.ui.theme.PrimaryText

@Composable
fun HomeScreen(onStartClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Cercle en arrière-plan
        Box(
            modifier = Modifier
                .width(700.dp)
                .absoluteOffset(x = 0.dp, y = 550.dp)  // Position absolue en bas
                .aspectRatio(1f) // Même Hauteur que Longeur
                .background(
                    color = Color(0xFF76D7C4),
                    shape = CircleShape
                )
                .zIndex(-1f)
        )

        // Contenu principal
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp)
                .padding(bottom = 32.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Titre "Objektik"
            Text(
                text = "Objektik",
                color = PrimaryText,
                style = MyTypography.displayLarge,
                modifier = Modifier.padding(top = 16.dp),
                textAlign = TextAlign.Center
            )

            // Texte descriptif
            Text(
                text = "Prends des photos des objets du quotidien et gagne des points en les découvrant !",
                color = AccentText,
                style = MyTypography.displayMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            // Section Trophées
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                // Icône de trophée
                Image(
                    painter = painterResource(id = R.drawable.trophy_primary),
                    contentDescription = "Icône de trophée",
                    modifier = Modifier.size(48.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.size(16.dp, 0.dp))

                // Texte des trophées
                Text(
                    text = "0 Trophées",
                    color = BluePrimary,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 8.dp),
                    textAlign = TextAlign.Center
                )
            }

            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Image principale
                Image(
                    painter = painterResource(id = R.drawable.polaroid),
                    contentDescription = "Image d'objet",
                    modifier = Modifier.size(275.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.size(0.dp, 16.dp))

                // Bouton lancement jeu
                CustomButton(
                    text = "Jouer maintenant",
                    onClick = onStartClick,
                    backgroundColor = BluePrimary,
                    textColor = PrimaryText,
                    modifier = Modifier
                        .width(250.dp)
                        .height(50.dp)
                )
            }
        }
    }
}
