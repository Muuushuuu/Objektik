package com.example.objektik.services

import android.content.Context
import android.content.SharedPreferences

class PointsManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

    // Fonction pour sauvegarder les points
    fun savePoints(points: Int) {
        sharedPreferences.edit().putInt("user_points", points).apply()
    }

    // Fonction pour récupérer les points
    fun getPoints(): Int {
        return sharedPreferences.getInt("user_points", 0) // defaut 0
    }

    // Fonction pour ajouter des points
    fun addPoints(points: Int) {
        val currentPoints = getPoints()
        savePoints(currentPoints + points)
    }
}