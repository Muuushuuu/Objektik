package com.example.objektik.screen

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.objektik.ui.theme.SuccessColor


/**
 * SuccessScreen - Un composant pour afficher la page de success (si objet chercher == objet(s) de la photo)
 *
 * @param nomFrancais Nom de l'objet trouvé (en français).
 * @param onStartClick Action déclenchée lorsqu'on clique sur le bouton "Rejouer".
 * @param onAddPoints Fonction pour ajouter des points au score actuel.
 * @param points Score actuel de l'utilisateur.
 */
@Composable
fun SuccessScreen(nomFrancais: String, onStartClick: () -> Unit, onAddPoints: (Int) -> Unit, points: Int) {
    // Ajoute des points (une seule fois)
    LaunchedEffect(Unit) {
        onAddPoints(10) // Ajout 10 point
    }
    // Conteneur principal
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Carte centrale avec le message de succès
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, SuccessColor),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                // Popup de succès
                CustomPopup(
                    text = "Bien joué ! \uD83C\uDF89",
                    borderColor = SuccessColor,
                    textColor = SuccessColor,
                )
                // Contenu principal de l'écran
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
                        // Image de trophée
                        Image(
                            painter = painterResource(id = R.drawable.trophy_sucess),
                            contentDescription = "Image trophy",
                            modifier = Modifier.size(150.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer( modifier = Modifier.fillMaxWidth().height(50.dp) ) // Espace entre l'image et le texte
                        // Message de succès
                        Text(
                            text = "Bravo tu as trouvé l'objet \"$nomFrancais\"\nTu gagnes 10 points",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            color = SuccessColor
                        )
                    }
                }
            }
        }
        // Bouton pour rejouer
        CustomButton(
            text = "Rejoué",
            onClick = onStartClick,
            backgroundColor = SuccessColor,
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