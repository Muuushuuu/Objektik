package com.example.objektik.services

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * loadObjectsFromServer - Fonction qui effectue une requête HTTP GET pour récupérer un objet JSON à partir d'un URL donné.
 *
 * @param url L'URL du serveur où l'on récupère le fichier JSON.
 * @return Un objet JSON si la requête est réussie, ou null en cas d'erreur.
 */
suspend fun loadObjectsFromServer(url: String): JSONObject? {
    return withContext(Dispatchers.IO) {
        // On construit le client HTTP avec des timeouts pour éviter que la requête ne dure trop longtemps
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)  // Timeout pour la connexion
            .readTimeout(30, TimeUnit.SECONDS)  // Timeout pour la lecture des données
            .build()

        Log.d("HttpRequest", "Démarrage de la requête vers $url")

        // On prépare la requête GET avec l'URL donnée
        val request = Request.Builder()
            .url(url)
            .build()

        try {
            // On exécute la requête et récupère la réponse
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()  // Récupère le contenu de la réponse sous forme de chaîne

            // Vérifie si la réponse est réussie (code 200) et que le corps de la réponse n'est pas vide
            if (response.isSuccessful && !responseBody.isNullOrEmpty()) {
                Log.d("HttpRequest", "Réponse reçue : $responseBody")
                JSONObject(responseBody)  // Retourne l'objet JSON parsé
            } else {
                Log.e("HttpRequest", "Erreur de requête : code=${response.code}")
                null  // Retourne null en cas de problème
            }
        } catch (e: IOException) {
            Log.e("HttpRequest", "Erreur lors de la récupération des données", e)
            null  // Retourne null en cas d'exception
        }
    }
}

/**
 * getRandomObjectFromServer - Fonction qui récupère un objet JSON aléatoire depuis un tableau d'objets situé dans un fichier JSON à une URL donnée.
 *
 * @param url L'URL du serveur où l'on récupère le fichier JSON.
 * @return Un objet JSON aléatoire s'il est trouvé, ou null en cas d'erreur ou si le tableau d'objets est vide.
 */
suspend fun getRandomObjectFromServer(url: String): JSONObject? {
    // On récupère d'abord le JSON à partir du serveur
    val jsonObject = loadObjectsFromServer(url)

    return try {
        if (jsonObject != null) {
            // On récupère le tableau 'objects' du JSON
            val objectsArray = jsonObject.optJSONArray("objects")

            // Si le tableau existe et qu'il contient au moins un objet
            if (objectsArray != null && objectsArray.length() > 0) {
                // Génère un index aléatoire pour choisir un objet
                val randomIndex = kotlin.random.Random.nextInt(objectsArray.length())
                objectsArray.getJSONObject(randomIndex)  // Retourne l'objet choisi aléatoirement
            } else {
                Log.e("HttpRequest", "Le tableau 'objects' est vide ou inexistant")
                null  // Retourne null si le tableau est vide ou absent
            }
        } else {
            Log.e("HttpRequest", "JSON Object est null")
            null  // Retourne null si le JSON récupéré est nul
        }
    } catch (e: JSONException) {
        Log.e("HttpRequest", "Erreur lors de l'analyse du JSON", e)
        null  // Retourne null en cas d'erreur de parsing JSON
    }
}