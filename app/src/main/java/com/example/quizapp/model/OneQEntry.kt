package com.example.quizapp.model

data class OneQEntry(
    val id: Long,
    var question: String,
    var choices: List<String>,
    var correctIndex: Int,
    var explanation: String = "",
    var genre: String = "",
    var pdfUriString: String = ""
)
