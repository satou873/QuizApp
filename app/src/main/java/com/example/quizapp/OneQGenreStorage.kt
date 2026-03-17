package com.example.quizapp

import android.content.Context
import org.json.JSONArray

object OneQGenreStorage {

    private const val PREF_NAME  = "oneq_prefs"
    private const val KEY_GENRES = "oneq_genres"

    private val DEFAULT_GENRES = listOf("その他")

    fun loadGenres(context: Context): List<String> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json  = prefs.getString(KEY_GENRES, null)
        if (json == null) {
            // 初回アクセス時はデフォルトジャンルを保存して返す
            saveGenres(context, DEFAULT_GENRES)
            return DEFAULT_GENRES
        }
        return try {
            val arr = JSONArray(json)
            (0 until arr.length()).map { arr.getString(it) }
        } catch (e: Exception) { emptyList() }
    }

    fun saveGenres(context: Context, genres: List<String>) {
        val arr = JSONArray()
        genres.forEach { arr.put(it) }
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit().putString(KEY_GENRES, arr.toString()).apply()
    }

    fun addGenre(context: Context, genre: String) {
        val list = loadGenres(context).toMutableList()
        if (!list.contains(genre)) {
            list.add(genre)
            saveGenres(context, list)
        }
    }

    fun deleteGenre(context: Context, genre: String) {
        val list = loadGenres(context).toMutableList()
        list.remove(genre)
        saveGenres(context, list)
    }

    fun renameGenre(context: Context, oldName: String, newName: String) {
        val list = loadGenres(context).toMutableList()
        val idx = list.indexOf(oldName)
        if (idx >= 0) {
            list[idx] = newName
            saveGenres(context, list)
        }
    }
}
