package com.example.quizapp.model

data class WordEntry(
    val id: Long = System.currentTimeMillis(),
    var title: String,
    var content: String,
    var examType: String  = "",
    var category: String  = "",   // 区分（例："基礎理論"、"電波法の概要" など）
    var isFormula: Boolean = false, // 公式かどうか
    var questionIds: List<Int> = emptyList()
)