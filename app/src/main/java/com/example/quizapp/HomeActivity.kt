package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnStart     = findViewById<Button>(R.id.btnStart)
        val btnFlashCard = findViewById<Button>(R.id.btnFlashCard)
        val btnWordBook  = findViewById<Button>(R.id.btnWordBook)
        val btnProfile   = findViewById<Button>(R.id.btnProfile)

        btnStart.setOnClickListener {
            if (QuizStorage.hasResume(this)) showResumeDialog()
            else goToExamSelect()
        }

        btnFlashCard.setOnClickListener {
            startActivity(Intent(this, FlashCardSelectActivity::class.java))
        }

        btnWordBook.setOnClickListener {
            startActivity(Intent(this, WordBookActivity::class.java))
        }

        btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }

    private fun showResumeDialog() {
        val resume = QuizStorage.loadResume(this) ?: run { goToExamSelect(); return }

        val examLabel = try {
            val et = com.example.quizapp.model.ExamType.valueOf(resume.examType)
            val yr = if (resume.year > 0) " ${resume.year}年" else " 全年度"
            et.label + yr
        } catch (e: Exception) { "前回の続き" }

        val answered  = resume.answeredIds.size
        val total     = resume.questionIds.size
        val remaining = total - resume.currentIndex

        android.app.AlertDialog.Builder(this)
            .setTitle("💾 途中保存があります")
            .setMessage(
                "【$examLabel】\n進捗：$answered / $total 問回答済み\n残り：$remaining 問\n\n続きから再開しますか？"
            )
            .setPositiveButton("▶ 続きから再開") { _, _ -> resumeQuiz(resume) }
            .setNeutralButton("🆕 最初から始める") { _, _ ->
                QuizStorage.clearResume(this); goToExamSelect()
            }
            .setNegativeButton("キャンセル", null)
            .show()
    }

    private fun resumeQuiz(resume: QuizStorage.ResumeData) {
        val intent = Intent(this, MainActivity::class.java).apply {
            putExtra("EXAM_TYPE",   resume.examType)
            putExtra("YEAR",        resume.year)
            putExtra("START_INDEX", resume.currentIndex)
            putIntegerArrayListExtra("QUESTION_IDS", ArrayList(resume.questionIds))
        }
        startActivity(intent)
    }

    private fun goToExamSelect() {
        startActivity(Intent(this, ExamSelectActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        val btnStart = findViewById<Button>(R.id.btnStart)
        if (QuizStorage.hasResume(this)) {
            btnStart.text = "▶ 第一級陸上無線技術士（続きあり💾）"
            btnStart.backgroundTintList = android.content.res.ColorStateList.valueOf(
                android.graphics.Color.parseColor("#FF5722")
            )
        } else {
            btnStart.text = "▶ 第一級陸上無線技術士"
            btnStart.backgroundTintList = android.content.res.ColorStateList.valueOf(
                android.graphics.Color.parseColor("#4CAF50")
            )
        }
    }
}