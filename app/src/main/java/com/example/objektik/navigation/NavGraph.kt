package com.example.objektik.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.objektik.screen.HomeScreen
import com.example.objektik.screen.GameScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"  // Screen de départ
    ) {
        // Composable HomeScreen
        composable("home") {
            HomeScreen(
                onStartClick = { navController.navigate("game") }  // Démarer GameScreen
            )
        }

        // Composable GameScreen
        composable("game") {
            GameScreen(
//                onCaptureClick = { /* Appelle la fonction de capture de la photo ici */ }
            )
        }
    }
}