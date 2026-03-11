package com.example.quizapp.model

data class Question(
    val id: Int,
    val questionText: String,
    val choices: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String,
    val examType: ExamType = ExamType.ENGINEERING_A,
    val year: Int = 2024,
    val term: String = ""   // 例："令和8年2月期" / "" の場合は year から自動生成
) {
    // 表示用ラベル（term が設定されていればそれを使用）
    val periodLabel: String get() = term.ifEmpty { "${year}年" }
}

enum class ExamType(val label: String) {
    ENGINEERING_A("無線工学A"),
    ENGINEERING_B("無線工学B"),
    LAW_A("法規A"),
    LAW_B("法規B")
}