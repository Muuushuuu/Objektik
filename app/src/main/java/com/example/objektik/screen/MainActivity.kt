package com.example.objektik.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.objektik.ui.theme.ObjektikTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ObjektikTheme {
                // Appel de la fonction composable HomeScreen
                HomeScreen(onStartClick = {
                    // Action à exécuter lorsque le bouton "Commencer" est cliqué
                })
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ObjektikTheme {
        HomeScreen {  }
    }
}