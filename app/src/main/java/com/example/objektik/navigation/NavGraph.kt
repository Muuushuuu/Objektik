package com.example.objektik.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.objektik.screen.ErrorScreen
import com.example.objektik.screen.HomeScreen
import com.example.objektik.screen.GameScreen
import com.example.objektik.screen.SuccessScreen
import com.example.objektik.services.PointsManager

/**
 * NavGraph - Un composant permettant de gérer la navigation entre les différentes pages de l'application.
 *
 * @param navController Le contrôleur de navigation utilisé pour gérer les transitions entre les écrans.
 */
@Composable
fun NavGraph(navController: NavHostController, pointsManager: PointsManager) {
    NavHost(
        navController = navController,
        startDestination = "home"  // Écran de départ
    ) {
        // Composable HomeScreen
        composable("home") {
            HomeScreen(
                onStartClick = { navController.navigate("game") },  // Démarrer GameScreen
                points = pointsManager.getPoints()
            )
        }

        // Composable GameScreen
        composable("game") {
            GameScreen(navController = navController)
        }

        // Composable SuccessScreen : affiché lorsqu'un objet a été trouvé avec succès
        composable("success/{nomFrancais}") { backStackEntry ->
            val nomFrancais = backStackEntry.arguments?.getString("nomFrancais")
            nomFrancais?.let {
                SuccessScreen(
                    nomFrancais = it,
                    onStartClick = {
                        navController.navigate("game")
                    },
                    onAddPoints = { points ->  // Ajout des points
                        pointsManager.addPoints(points)
                    },
                    points = pointsManager.getPoints()
                )
            }
        }

        // Composable ErrorScreen : affiché lorsqu'aucun objet n'a été trouvé
        composable("error/{nomFrancais}") { backStackEntry ->
            val nomFrancais = backStackEntry.arguments?.getString("nomFrancais")
            nomFrancais?.let {
                ErrorScreen(nomFrancais = it, onStartClick = { navController.navigate("game") })
            }
        }
    }
}