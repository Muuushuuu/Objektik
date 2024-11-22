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

// Définition des routes
object Routes {
    const val HOME = "home"
    const val GAME = "game"
    const val SUCCESS = "success/{nomFrancais}"
    const val ERROR = "error/{nomFrancais}"
}

/**
 * NavGraph - Gère la navigation entre les écrans de l'application.
 *
 * @param navController Contrôleur pour gérer les transitions entre les écrans.
 * @param pointsManager Gestionnaire des points accumulés dans l'application.
 */
@Composable
fun NavGraph(navController: NavHostController, pointsManager: PointsManager) {
    NavHost(
        navController = navController,
        startDestination = Routes.HOME  // Écran affiché au démarrage
    ) {
        // Composable HomeScreen
        composable(Routes.HOME) {
            HomeScreen(
                onStartClick = { navController.navigate(Routes.GAME) },  // Démarrer GameScreen => l'écran de jeu
                points = pointsManager.getPoints()
            )
        }

        // Composable GameScreen
        composable(Routes.GAME) {
            GameScreen(navController = navController)
        }

        // Composable SuccessScreen : affiché lorsqu'un objet a été trouvé avec succès
        composable(Routes.SUCCESS) { backStackEntry ->
            val nomFrancais = backStackEntry.arguments?.getString("nomFrancais")
            nomFrancais?.let {
                SuccessScreen(
                    nomFrancais = it,
                    onStartClick = { navController.navigate(Routes.GAME) }, // Rejouer
                    onAddPoints = { points -> pointsManager.addPoints(points) }, // Ajoute des points
                    points = pointsManager.getPoints()
                )
            }
        }

        // Composable ErrorScreen : affiché lorsqu'aucun objet n'a été trouvé
        composable(Routes.ERROR) { backStackEntry ->
            val nomFrancais = backStackEntry.arguments?.getString("nomFrancais")
            nomFrancais?.let {
                ErrorScreen(
                    nomFrancais = it,
                    onStartClick = { navController.navigate(Routes.GAME) })
            }
        }
    }
}