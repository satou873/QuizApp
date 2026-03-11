package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val tvScore = findViewById<TextView>(R.id.tvScore)
        val tvMessage = findViewById<TextView>(R.id.tvMessage)
        val btnRetry = findViewById<Button>(R.id.btnRetry)
        val btnHome = findViewById<Button>(R.id.btnHome)
        val btnProfile = findViewById<Button>(R.id.btnProfile)

        val score = intent.getIntExtra("SCORE", 0)
        val total = intent.getIntExtra("TOTAL", 5)

        tvScore.text = "$score / $total 問正解！"

        tvMessage.text = when (score) {
            total -> "🎉 完璧です！素晴らしい！"
            in (total * 3 / 4)..total -> "👍 よくできました！"
            in (total / 2)..(total * 3 / 4) -> "🤔 もう少し頑張ろう！"
            else -> "❌ 復習が必要です！"
        }

        btnRetry.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        btnHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}