package com.example.quizapp

import android.content.Context
import com.example.quizapp.model.CheckLevel
import com.example.quizapp.model.QuizResult
import org.json.JSONArray
import org.json.JSONObject

object QuizStorage {

    private const val PREF_NAME      = "quiz_prefs"
    private const val KEY_RESULTS    = "quiz_results"
    private const val KEY_PLAY_COUNT = "play_count"
    private const val KEY_HIGH_SCORE = "high_score"
    private const val KEY_HISTORY    = "quiz_history"
    private const val KEY_RESUME     = "quiz_resume"

    // ===== 結果の保存・読み込み =====

    fun saveResults(context: Context, results: List<QuizResult>) {
        val existing = loadResults(context).toMutableList()
        for (newResult in results) {
            val index = existing.indexOfFirst { it.questionId == newResult.questionId }
            if (index >= 0) {
                existing[index].correctCount += newResult.correctCount
                existing[index].wrongCount   += newResult.wrongCount
                // 直近履歴をマージ（最大4件）
                for (h in newResult.recentHistory) {
                    existing[index].addHistory(h)
                }
            } else {
                existing.add(newResult)
            }
        }
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_RESULTS, resultsToJson(existing)).apply()
    }

    fun loadResults(context: Context): List<QuizResult> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json  = prefs.getString(KEY_RESULTS, null) ?: return emptyList()
        return jsonToResults(json)
    }

    fun updateCheckLevel(context: Context, questionId: Int, checkLevel: CheckLevel) {
        val existing = loadResults(context).toMutableList()
        val index = existing.indexOfFirst { it.questionId == questionId }
        if (index >= 0) existing[index].checkLevel = checkLevel
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_RESULTS, resultsToJson(existing)).apply()
    }

    private fun resultsToJson(results: List<QuizResult>): String {
        val array = JSONArray()
        for (r in results) {
            val obj = JSONObject()
            obj.put("questionId",   r.questionId)
            obj.put("questionText", r.questionText)
            obj.put("correctCount", r.correctCount)
            obj.put("wrongCount",   r.wrongCount)
            obj.put("checkLevel",   r.checkLevel.name)
            val hist = JSONArray()
            r.recentHistory.forEach { hist.put(it) }
            obj.put("recentHistory", hist)
            array.put(obj)
        }
        return array.toString()
    }

    private fun jsonToResults(json: String): List<QuizResult> {
        val list  = mutableListOf<QuizResult>()
        val array = JSONArray(json)
        for (i in 0 until array.length()) {
            val obj  = array.getJSONObject(i)
            val hist = obj.optJSONArray("recentHistory") ?: JSONArray()
            val recentHistory = (0 until hist.length())
                .map { hist.getBoolean(it) }
                .toMutableList()
            list.add(QuizResult(
                questionId    = obj.getInt("questionId"),
                questionText  = obj.getString("questionText"),
                correctCount  = obj.getInt("correctCount"),
                wrongCount    = obj.getInt("wrongCount"),
                checkLevel    = try {
                    CheckLevel.valueOf(obj.optString("checkLevel", "UNCHECKED"))
                } catch (e: Exception) { CheckLevel.UNCHECKED },
                recentHistory = recentHistory
            ))
        }
        return list
    }

    // ===== プレイ回数 =====

    fun incrementPlayCount(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().putInt(KEY_PLAY_COUNT, getPlayCount(context) + 1).apply()
    }

    fun getPlayCount(context: Context): Int =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_PLAY_COUNT, 0)

    // ===== ハイスコア =====

    fun updateHighScore(context: Context, score: Int) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        if (score > getHighScore(context))
            prefs.edit().putInt(KEY_HIGH_SCORE, score).apply()
    }

    fun getHighScore(context: Context): Int =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_HIGH_SCORE, 0)

    // ===== 学習履歴 =====

    data class HistoryEntry(
        val score: Int,
        val total: Int,
        val timestamp: Long,
        val examType: String,
        val year: Int,
        val questionIds: List<Int>
    )

    fun addHistory(
        context: Context,
        score: Int,
        total: Int,
        examType: String = "",
        year: Int = -1,
        questionIds: List<Int> = emptyList()
    ) {
        val prefs    = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val existing = prefs.getString(KEY_HISTORY, null)
        val array    = if (existing != null) JSONArray(existing) else JSONArray()
        val obj = JSONObject()
        obj.put("score",    score)
        obj.put("total",    total)
        obj.put("date",     System.currentTimeMillis())
        obj.put("examType", examType)
        obj.put("year",     year)
        val ids = JSONArray()
        questionIds.forEach { ids.put(it) }
        obj.put("questionIds", ids)
        array.put(obj)
        prefs.edit().putString(KEY_HISTORY, array.toString()).apply()
    }

    fun loadHistory(context: Context): List<HistoryEntry> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json  = prefs.getString(KEY_HISTORY, null) ?: return emptyList()
        val list  = mutableListOf<HistoryEntry>()
        val array = JSONArray(json)
        for (i in 0 until array.length()) {
            val obj      = array.getJSONObject(i)
            val idsArray = obj.optJSONArray("questionIds") ?: JSONArray()
            val ids      = (0 until idsArray.length()).map { idsArray.getInt(it) }
            list.add(HistoryEntry(
                score       = obj.getInt("score"),
                total       = obj.getInt("total"),
                timestamp   = obj.getLong("date"),
                examType    = obj.optString("examType", ""),
                year        = obj.optInt("year", -1),
                questionIds = ids
            ))
        }
        return list.reversed()
    }

    // ===== 途中保存 =====

    data class ResumeData(
        val examType: String,
        val year: Int,
        val questionIds: List<Int>,
        val currentIndex: Int,
        val answeredIds: List<Int>  // 回答済みの問題ID
    )

    fun saveResume(context: Context, data: ResumeData) {
        val obj = JSONObject()
        obj.put("examType",     data.examType)
        obj.put("year",         data.year)
        obj.put("currentIndex", data.currentIndex)
        val ids = JSONArray(); data.questionIds.forEach { ids.put(it) }
        obj.put("questionIds",  ids)
        val answered = JSONArray(); data.answeredIds.forEach { answered.put(it) }
        obj.put("answeredIds",  answered)
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit().putString(KEY_RESUME, obj.toString()).apply()
    }

    fun loadResume(context: Context): ResumeData? {
        val json = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_RESUME, null) ?: return null
        return try {
            val obj      = JSONObject(json)
            val idsArr   = obj.optJSONArray("questionIds") ?: JSONArray()
            val ids      = (0 until idsArr.length()).map { idsArr.getInt(it) }
            val ansArr   = obj.optJSONArray("answeredIds") ?: JSONArray()
            val answered = (0 until ansArr.length()).map { ansArr.getInt(it) }
            ResumeData(
                examType     = obj.optString("examType", ""),
                year         = obj.optInt("year", -1),
                questionIds  = ids,
                currentIndex = obj.optInt("currentIndex", 0),
                answeredIds  = answered
            )
        } catch (e: Exception) { null }
    }

    fun clearResume(context: Context) {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit().remove(KEY_RESUME).apply()
    }

    fun hasResume(context: Context): Boolean =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .contains(KEY_RESUME)
}