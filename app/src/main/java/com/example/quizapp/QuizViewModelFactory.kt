package com.example.quizapp.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class QuizViewModelFactory(
    private val context: Context,
    private val questionIds: List<Int>? = null,
    private val startIndex: Int = 0
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            return QuizViewModel(context, questionIds, startIndex) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}