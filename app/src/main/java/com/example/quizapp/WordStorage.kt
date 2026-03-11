package com.example.quizapp

import android.content.Context
import com.example.quizapp.model.WordEntry
import org.json.JSONArray
import org.json.JSONObject

object WordStorage {

    private const val PREF_NAME = "word_prefs"
    private const val KEY_WORDS = "word_entries"

    fun saveWord(context: Context, entry: WordEntry) {
        val list = loadWords(context).toMutableList()
        val index = list.indexOfFirst { it.id == entry.id }
        if (index >= 0) {
            list[index] = entry
        } else {
            list.add(entry)
        }
        save(context, list)
    }

    fun deleteWord(context: Context, id: Long) {
        val list = loadWords(context).toMutableList()
        list.removeAll { it.id == id }
        save(context, list)
    }

    fun loadWords(context: Context): List<WordEntry> {
        val json = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_WORDS, null) ?: return emptyList()
        return try {
            val array = JSONArray(json)
            val list  = mutableListOf<WordEntry>()
            for (i in 0 until array.length()) {
                val obj  = array.getJSONObject(i)
                val qIds = obj.optJSONArray("questionIds") ?: JSONArray()
                list.add(WordEntry(
                    id          = obj.getLong("id"),
                    title       = obj.getString("title"),
                    content     = obj.getString("content"),
                    examType    = obj.optString("examType", ""),
                    questionIds = (0 until qIds.length()).map { qIds.getInt(it) }
                ))
            }
            list
        } catch (e: Exception) { emptyList() }
    }

    // 問題IDに関連する単語・公式を取得
    fun getWordsForQuestion(context: Context, questionId: Int): List<WordEntry> {
        return loadWords(context).filter { entry ->
            entry.questionIds.isEmpty() || questionId in entry.questionIds
        }
    }

    // 試験種別に関連する単語・公式を取得
    fun getWordsForExamType(context: Context, examType: String): List<WordEntry> {
        return loadWords(context).filter { entry ->
            entry.examType.isEmpty() || entry.examType == examType
        }
    }

    private fun save(context: Context, list: List<WordEntry>) {
        val array = JSONArray()
        for (e in list) {
            val obj  = JSONObject()
            val qIds = JSONArray()
            e.questionIds.forEach { qIds.put(it) }
            obj.put("id",          e.id)
            obj.put("title",       e.title)
            obj.put("content",     e.content)
            obj.put("examType",    e.examType)
            obj.put("questionIds", qIds)
            array.put(obj)
        }
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit().putString(KEY_WORDS, array.toString()).apply()
    }
}