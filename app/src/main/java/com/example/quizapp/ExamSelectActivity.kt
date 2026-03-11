package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.model.ExamType

class ExamSelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exam_select)

        val mode = intent.getStringExtra("MODE") ?: "EXAM"

        val tvTitle         = findViewById<TextView>(R.id.tvExamSelectTitle)
        val btnEngineeringA = findViewById<Button>(R.id.btnEngineeringA)
        val btnEngineeringB = findViewById<Button>(R.id.btnEngineeringB)
        val btnLawA         = findViewById<Button>(R.id.btnLawA)
        val btnLawB         = findViewById<Button>(R.id.btnLawB)
        val btnBack         = findViewById<Button>(R.id.btnBack)

        // モードに合わせてタイトル変更
        tvTitle.text = when (mode) {
            "REVIEW"     -> "🔁 復習問題 - 試験を選択"
            "UNANSWERED" -> "❓ 未回答問題 - 試験を選択"
            else         -> "📡 試験を選択"
        }

        btnEngineeringA.setOnClickListener { goToYearSelect(ExamType.ENGINEERING_A, mode) }
        btnEngineeringB.setOnClickListener { goToYearSelect(ExamType.ENGINEERING_B, mode) }
        btnLawA.setOnClickListener         { goToYearSelect(ExamType.LAW_A, mode) }
        btnLawB.setOnClickListener         { goToYearSelect(ExamType.LAW_B, mode) }
        btnBack.setOnClickListener         { finish() }
    }

    private fun goToYearSelect(examType: ExamType, mode: String) {
        val intent = Intent(this, YearSelectActivity::class.java)
        intent.putExtra("EXAM_TYPE", examType.name)
        intent.putExtra("MODE", mode)
        startActivity(intent)
    }
}