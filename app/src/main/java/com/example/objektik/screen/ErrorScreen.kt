package com.example.objektik.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
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
import com.example.objektik.ui.components.CustomPopup
import com.example.objektik.ui.theme.ErrorColor

/**
 * ErrorScreen - Un composant pour afficher la page d'erreur (en cas de différence entre objet(s) de la photo et objet chercher)
 *
 * @param nomFrancais Nom de l'objet recherché (en français), affiché pour guider l'utilisateur.
 * @param onStartClick Action déclenchée lorsqu'on clique sur le bouton "Réessayer".
 */
@Composable
fun ErrorScreen(nomFrancais: String, onStartClick: () -> Unit) {
    // Conteneur principal
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        // Carte contenant le message d'erreur
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, ErrorColor),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                // Popup personnalise pour afficher un titre d'erreur
                CustomPopup(
                    text = "Oups ! \uD83D\uDE4A",
                    borderColor = ErrorColor,
                    textColor = ErrorColor,
                )
                // Contenu principal de l'erreur
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                            .padding(bottom = 200.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Image d'emoji triste
                        Image(
                            painter = painterResource(id = R.drawable.sad),
                            contentDescription = "Image emoji sad",
                            modifier = Modifier.size(150.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer( modifier = Modifier.fillMaxWidth().height(50.dp) )// Espace entre l'image et le texte
                        // Message d'erreur
                        Text(
                            text = "L’objet n’a pas été reconnu, essayez encore ! \nL'objet à trouver est \"$nomFrancais\"",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            color = ErrorColor
                        )
                    }

                }
            }
        }
        // Bouton pour réessayer
        CustomButton(
            text = "Réessayer",
            onClick = onStartClick,
            backgroundColor = ErrorColor,
            textColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 75.dp)
                .width(200.dp)
                .height(50.dp)
                .zIndex(2f)
        )
    }

}