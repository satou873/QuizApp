package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val btnReview       = findViewById<Button>(R.id.btnReview)
        val btnUnanswered   = findViewById<Button>(R.id.btnUnanswered)
        val btnWordBook     = findViewById<Button>(R.id.btnWordBook)
        val btnQuestionEdit = findViewById<Button>(R.id.btnQuestionEdit)
        val btnCheckStatus  = findViewById<Button>(R.id.btnCheckStatus)
        val btnHistory      = findViewById<Button>(R.id.btnHistory)
        val btnAchievement  = findViewById<Button>(R.id.btnAchievement)
        val btnScoreByExam  = findViewById<Button>(R.id.btnScoreByExam)
        val btnBackHome     = findViewById<Button>(R.id.btnBackHome)

        btnReview.setOnClickListener {
            startActivity(Intent(this, ExamSelectActivity::class.java)
                .putExtra("MODE", "REVIEW"))
        }
        btnUnanswered.setOnClickListener {
            startActivity(Intent(this, ExamSelectActivity::class.java)
                .putExtra("MODE", "UNANSWERED"))
        }
        btnWordBook.setOnClickListener {
            startActivity(Intent(this, WordBookActivity::class.java))
        }
        btnQuestionEdit.setOnClickListener {
            startActivity(Intent(this, QuestionEditActivity::class.java))
        }
        btnCheckStatus.setOnClickListener {
            startActivity(Intent(this, CheckStatusActivity::class.java))
        }
        btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
        btnAchievement.setOnClickListener {
            startActivity(Intent(this, AchievementActivity::class.java))
        }
        btnScoreByExam.setOnClickListener {
            startActivity(Intent(this, ScoreByExamActivity::class.java))
        }
        btnBackHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }
    }
}