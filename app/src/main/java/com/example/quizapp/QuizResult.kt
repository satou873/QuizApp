package com.example.quizapp.model

data class QuizResult(
    val questionId: Int,
    val questionText: String,
    var correctCount: Int = 0,
    var wrongCount: Int = 0,
    var checkLevel: CheckLevel = CheckLevel.UNCHECKED,
    var recentHistory: MutableList<Boolean> = mutableListOf() // 直近4回の正誤(true=正解)
) {
    val totalCount: Int get() = correctCount + wrongCount

    val accuracy: Float get() =
        if (totalCount == 0) 0f
        else correctCount.toFloat() / totalCount.toFloat()

    val rating: Rating get() = when {
        totalCount == 0   -> Rating.UNKNOWN
        accuracy == 1.0f  -> Rating.PERFECT
        accuracy >= 0.75f -> Rating.GOOD
        accuracy >= 0.5f  -> Rating.AVERAGE
        else              -> Rating.POOR
    }

    // 正誤を記録（最大4件保持）
    fun addHistory(isCorrect: Boolean) {
        recentHistory.add(isCorrect)
        if (recentHistory.size > 4) {
            recentHistory.removeAt(0)
        }
    }
}

enum class Rating(val label: String, val emoji: String) {
    PERFECT( "完璧",   "✅"),
    GOOD(    "普通",   "👍"),
    AVERAGE( "微妙",   "🤔"),
    POOR(    "苦手",   "❌"),
    UNKNOWN( "未挑戦", "❓")
}

enum class CheckLevel(val label: String, val emoji: String, val color: String) {
    PERFECT(  "覚えた！",   "💪", "#4CAF50"),
    GOOD(     "まあまあ",   "😊", "#2196F3"),
    POOR(     "微妙",      "🤔", "#FF9800"),
    FORGOT(   "忘れた",    "😢", "#F44336"),
    UNCHECKED("未チェック", "❓", "#999999")
}