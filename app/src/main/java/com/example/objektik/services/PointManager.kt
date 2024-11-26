package com.example.objektik.services

import android.content.Context
import android.content.SharedPreferences

/**
 * PointsManager - Une classe pour gérer les points de l'utilisateur en les stockant dans les préférences partagées.
 *
 * @param context Contexte de l'application, utilisé pour accéder aux SharedPreferences.
 */
class PointsManager(context: Context) {
    // Instance de SharedPreferences utilisée pour stocker les points
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    /**
     * Sauvegarde le score actuel de l'utilisateur dans les préférences partagées.
     *
     * @param points Nombre de points à sauvegarder.
     */
    fun savePoints(points: Int) {
        // Utilise apply() pour sauvegarder les points de manière asynchrone
        sharedPreferences.edit().putInt("user_points", points).apply()
    }

    /**
     * Récupère le score actuel de l'utilisateur à partir des préférences partagées.
     *
     * @return Le nombre de points actuellement sauvegardé. Retourne 0 si aucune valeur n'est trouvée.
     */
    fun getPoints(): Int {
        return sharedPreferences.getInt("user_points", 0) // Défaut à 0 si aucune valeur n'est trouvée
    }

    /**
     * Ajoute un certain nombre de points au score actuel de l'utilisateur.
     *
     * @param points Nombre de points à ajouter au score actuel.
     */
    fun addPoints(points: Int) {
        // Récupère les points actuels, ajoute le nouveau score, puis sauvegarde le total
        val currentPoints = getPoints()
        savePoints(currentPoints + points)
    }
}