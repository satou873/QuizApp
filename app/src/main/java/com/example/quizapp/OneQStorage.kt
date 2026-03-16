package com.example.quizapp

import android.content.Context
import com.example.quizapp.model.OneQEntry
import org.json.JSONArray
import org.json.JSONObject

object OneQStorage {

    private const val PREF_NAME   = "oneq_prefs"
    private const val KEY_ENTRIES = "oneq_entries"

    fun save(context: Context, entry: OneQEntry) {
        val list  = loadAll(context).toMutableList()
        val index = list.indexOfFirst { it.id == entry.id }
        if (index >= 0) list[index] = entry else list.add(entry)
        saveAll(context, list)
    }

    fun delete(context: Context, id: Long) {
        val list = loadAll(context).toMutableList()
        list.removeAll { it.id == id }
        saveAll(context, list)
    }

    fun loadAll(context: Context): List<OneQEntry> {
        val json = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_ENTRIES, null) ?: return emptyList()
        return try {
            val arr = JSONArray(json)
            (0 until arr.length()).map { i ->
                val obj = arr.getJSONObject(i)
                val ca  = obj.getJSONArray("choices")
                OneQEntry(
                    id           = obj.getLong("id"),
                    question     = obj.getString("question"),
                    choices      = (0 until ca.length()).map { ca.getString(it) },
                    correctIndex = obj.getInt("correctIndex"),
                    explanation  = obj.optString("explanation", ""),
                    genre        = obj.optString("genre", ""),
                    pdfUriString = obj.optString("pdfUriString", "")
                )
            }
        } catch (e: Exception) { emptyList() }
    }

    fun loadByGenre(context: Context, genre: String): List<OneQEntry> =
        if (genre == "ALL") loadAll(context)
        else loadAll(context).filter { it.genre == genre }

    fun generateId(context: Context): Long {
        val existing = loadAll(context)
        return if (existing.isEmpty()) System.currentTimeMillis()
               else maxOf(existing.maxOf { it.id } + 1, System.currentTimeMillis())
    }

    // 旧ジャンル名を持つ全エントリを一括更新する
    fun updateGenreForAll(context: Context, oldGenre: String, newGenre: String) {
        val list = loadAll(context)
        if (list.none { it.genre == oldGenre }) return
        saveAll(context, list.map { if (it.genre == oldGenre) it.copy(genre = newGenre) else it })
    }

    private fun saveAll(context: Context, list: List<OneQEntry>) {
        val arr = JSONArray()
        list.forEach { e ->
            val obj = JSONObject()
            val ca  = JSONArray()
            e.choices.forEach { ca.put(it) }
            obj.put("id",           e.id)
            obj.put("question",     e.question)
            obj.put("choices",      ca)
            obj.put("correctIndex", e.correctIndex)
            obj.put("explanation",  e.explanation)
            obj.put("genre",        e.genre)
            obj.put("pdfUriString", e.pdfUriString)
            arr.put(obj)
        }
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit().putString(KEY_ENTRIES, arr.toString()).apply()
    }
}
