package com.example.quizapp

import android.content.Context
import com.example.quizapp.model.ExamType
import com.example.quizapp.model.Question
import org.json.JSONArray
import org.json.JSONObject

object QuestionStorage {

    private const val PREF_NAME   = "question_prefs"
    private const val KEY_QUESTIONS = "user_questions"

    // 保存済み問題を全取得
    fun loadQuestions(context: Context): List<Question> {
        val json = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .getString(KEY_QUESTIONS, null) ?: return emptyList()
        return try {
            val array = JSONArray(json)
            val list  = mutableListOf<Question>()
            for (i in 0 until array.length()) {
                val obj     = array.getJSONObject(i)
                val choices = obj.getJSONArray("choices")
                list.add(Question(
                    id                 = obj.getInt("id"),
                    questionText       = obj.getString("questionText"),
                    choices            = (0 until choices.length()).map { choices.getString(it) },
                    correctAnswerIndex = obj.getInt("correctAnswerIndex"),
                    explanation        = obj.getString("explanation"),
                    examType           = ExamType.valueOf(obj.getString("examType")),
                    year               = obj.getInt("year")
                ))
            }
            list
        } catch (e: Exception) { emptyList() }
    }

    // 問題を保存（新規 or 更新）
    fun saveQuestion(context: Context, question: Question) {
        val list  = loadQuestions(context).toMutableList()
        val index = list.indexOfFirst { it.id == question.id }
        if (index >= 0) list[index] = question else list.add(question)
        save(context, list)
    }

    // 問題を削除
    fun deleteQuestion(context: Context, id: Int) {
        val list = loadQuestions(context).toMutableList()
        list.removeAll { it.id == id }
        save(context, list)
    }

    // 新しいIDを生成（既存の最大ID+1）
    fun generateId(context: Context): Int {
        val userMax   = loadQuestions(context).maxOfOrNull { it.id } ?: 0
        val builtinMax = com.example.quizapp.data.QuizData.questions.maxOfOrNull { it.id } ?: 0
        return maxOf(userMax, builtinMax) + 1
    }

    private fun save(context: Context, list: List<Question>) {
        val array = JSONArray()
        for (q in list) {
            val obj     = JSONObject()
            val choices = JSONArray()
            q.choices.forEach { choices.put(it) }
            obj.put("id",                 q.id)
            obj.put("questionText",       q.questionText)
            obj.put("choices",            choices)
            obj.put("correctAnswerIndex", q.correctAnswerIndex)
            obj.put("explanation",        q.explanation)
            obj.put("examType",           q.examType.name)
            obj.put("year",               q.year)
            array.put(obj)
        }
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            .edit().putString(KEY_QUESTIONS, array.toString()).apply()
    }
}