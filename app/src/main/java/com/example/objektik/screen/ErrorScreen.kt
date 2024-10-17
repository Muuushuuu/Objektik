package com.example.objektik.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.objektik.R
import com.example.objektik.ui.components.CustomButton
import com.example.objektik.ui.components.CustomPopup
import com.example.objektik.ui.theme.BluePrimary
import com.example.objektik.ui.theme.ErrorColor
import com.example.objektik.ui.theme.GreenAccent
import com.example.objektik.ui.theme.ObjektikTheme
import com.example.objektik.ui.theme.PrimaryText

/**
 * ErrorScreen - Un composant pour afficher la page d'erreur (en cas de différence entre objet(s) de la photo et objet chercher)
 *
 * @param nomFrancais Le nom en francais de l'objet à chercher
 */
@Composable
fun ErrorScreen(nomFrancais: String, onStartClick: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        // Cercle en arrière-plan
        Box(
            modifier = Modifier
                .width(700.dp)
                .absoluteOffset(x = 0.dp, y = 550.dp)  // Position absolue en bas
                .aspectRatio(1f) // Même Hauteur que Longeur
                .background(
                    color = GreenAccent,
                    shape = CircleShape
                )
                .zIndex(1f)
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(2.dp, ErrorColor),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                CustomPopup(
                    text = "Oups ! \uD83D\uDE4A",
                    borderColor = ErrorColor,
                    textColor = ErrorColor,
                )

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
                        Image(
                            painter = painterResource(id = R.drawable.sad),
                            contentDescription = "Image emohi sad",
                            modifier = Modifier.size(150.dp),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(
                            modifier = Modifier.fillMaxWidth()
                                .height(50.dp))

                        Text(
                            text = "Dommage ce n'est pas l'objet à trouver... \nL'objet à trouver est \"$nomFrancais\"",
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            color = ErrorColor
                        )
                    }

                }
            }

        }
        CustomButton(
            text = "Réessayer",
            onClick = onStartClick,
            backgroundColor = BluePrimary,
            textColor = Color.White,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 75.dp) // Ajuste la marge en bas si nécessaire
                .width(200.dp)
                .height(50.dp)
                .zIndex(2f)
        )
    }

}