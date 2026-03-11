package com.example.quizapp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.data.QuizData
import com.example.quizapp.model.Question
import com.example.quizapp.model.QuizResult

class QuizViewModel(
    context: Context,
    private val questionIds: List<Int>? = null,
    private val startIndex: Int = 0
) : ViewModel() {

    // Context は ApplicationContext で保持（メモリリーク防止）
    private val appContext = context.applicationContext

    // 内蔵問題＋ユーザー追加問題を結合して取得
    val questions: List<Question> = run {
        val all = QuizData.getAllQuestions(appContext)
        if (questionIds != null) {
            questionIds.mapNotNull { id -> all.find { it.id == id } }
        } else {
            all.shuffled()
        }
    }

    val totalQuestions = questions.size

    var currentIndex = startIndex
        private set

    private val resultMap   = mutableMapOf<Int, QuizResult>()
    private val answeredIds = mutableListOf<Int>()

    private val _currentQuestion = MutableLiveData<Question>()
    val currentQuestion: LiveData<Question> = _currentQuestion

    private val _isAnswered = MutableLiveData<Boolean>()
    val isAnswered: LiveData<Boolean> = _isAnswered

    private val _isFinished = MutableLiveData<Boolean>()
    val isFinished: LiveData<Boolean> = _isFinished

    private val _selectedIndex = MutableLiveData<Int>()
    val selectedIndex: LiveData<Int> = _selectedIndex

    init {
        if (questions.isNotEmpty() && currentIndex < totalQuestions) {
            loadQuestion()
        } else {
            _isFinished.value = true
        }
    }

    private fun loadQuestion() {
        _currentQuestion.value = questions[currentIndex]
        _isAnswered.value      = false
    }

    fun answer(index: Int) {
        if (_isAnswered.value == true) return
        _selectedIndex.value = index

        val question  = questions[currentIndex]
        val isCorrect = index == question.correctAnswerIndex

        val result = resultMap.getOrPut(question.id) {
            QuizResult(questionId = question.id, questionText = question.questionText)
        }
        if (isCorrect) result.correctCount++ else result.wrongCount++
        result.addHistory(isCorrect)

        if (question.id !in answeredIds) answeredIds.add(question.id)

        _isAnswered.value = true
    }

    fun nextQuestion() {
        currentIndex++
        if (currentIndex >= totalQuestions) {
            _isFinished.value = true
        } else {
            loadQuestion()
        }
    }

    fun getScore(): Int                = resultMap.values.count { it.correctCount > 0 }
    fun getResults(): List<QuizResult> = resultMap.values.toList()
    fun getQuestionIds(): List<Int>    = questions.map { it.id }
    fun getAnsweredIds(): List<Int>    = answeredIds.toList()
}