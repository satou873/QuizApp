package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.data.QuizData
import com.example.quizapp.model.ExamType

class ScoreByExamActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scroll = ScrollView(this)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 40, 40, 40)
            setBackgroundColor(Color.parseColor("#F5F5F5"))
        }
        scroll.addView(root)
        setContentView(scroll)

        val results = QuizStorage.loadResults(this)

        root.addView(TextView(this).apply {
            text     = "📝 試験ごとの成績"
            textSize = 24f
            setTextColor(Color.parseColor("#333333"))
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            val lp   = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 32
            layoutParams = lp
        })

        if (results.isEmpty()) {
            root.addView(TextView(this).apply {
                text     = "まだ回答していません！"
                textSize = 16f
                setTextColor(Color.parseColor("#999999"))
                gravity  = Gravity.CENTER
            })
        } else {
            ExamType.values().forEach { examType ->
                val allQuestions = QuizData.getQuestionsByExamType(examType)
                if (allQuestions.isEmpty()) return@forEach
                val ids       = allQuestions.map { it.id }.toSet()
                val hasResult = results.any { it.questionId in ids }
                if (!hasResult) return@forEach

                // 試験種別ヘッダー
                root.addView(makeSectionHeader("📋 ${examType.label}"))

                // 全年度まとめ行（タップで詳細へ）
                addSummaryRow(root, results, examType, allQuestions, "全年度まとめ", null)

                // 年度別行
                val years = QuizData.getYearsByExamType(examType)
                years.forEach { year ->
                    val yearQs  = QuizData.getQuestionsByExamTypeAndYear(examType, year)
                    val yearIds = yearQs.map { it.id }.toSet()
                    if (!results.any { it.questionId in yearIds }) return@forEach
                    addSummaryRow(root, results, examType, yearQs, "${year}年", year)
                }
            }
        }

        val btnBack = Button(this).apply {
            text = "← 戻る"
            textSize = 16f
            setTextColor(Color.WHITE)
            backgroundTintList = android.content.res.ColorStateList.valueOf(
                Color.parseColor("#607D8B")
            )
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.topMargin = 24
            layoutParams = lp
            setPadding(16, 40, 16, 40)
        }
        btnBack.setOnClickListener { finish() }
        root.addView(btnBack)
    }

    private fun makeSectionHeader(title: String): TextView {
        return TextView(this).apply {
            text     = title
            textSize = 18f
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.parseColor("#37474F"))
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            setPadding(24, 20, 24, 20)
            gravity  = Gravity.CENTER_VERTICAL
            val lp   = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.topMargin    = 24
            lp.bottomMargin = 4
            layoutParams    = lp
        }
    }

    private fun addSummaryRow(
        root: LinearLayout,
        results: List<com.example.quizapp.model.QuizResult>,
        examType: ExamType,
        questions: List<com.example.quizapp.model.Question>,
        label: String,
        year: Int?
    ) {
        val ids          = questions.map { it.id }.toSet()
        val filtered     = results.filter { it.questionId in ids }
        if (filtered.isEmpty()) return

        val correctCount = filtered.count { it.correctCount > 0 }
        val total        = filtered.size
        val pct          = if (total > 0) correctCount * 100 / total else 0

        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity     = Gravity.CENTER_VERTICAL
            setBackgroundColor(Color.WHITE)
            setPadding(24, 20, 24, 20)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 2
            layoutParams = lp
        }

        row.addView(TextView(this).apply {
            text     = "📅 $label"
            textSize = 15f
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            setTextColor(Color.parseColor("#555555"))
            layoutParams = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
            )
        })
        row.addView(TextView(this).apply {
            text     = "$correctCount / $total 問"
            textSize = 14f
            setTextColor(Color.parseColor("#555555"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.marginEnd = 16
            layoutParams = lp
        })
        row.addView(TextView(this).apply {
            text     = "$pct%"
            textSize = 16f
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            setTextColor(Color.parseColor(when {
                pct >= 80 -> "#4CAF50"
                pct >= 60 -> "#FF9800"
                else      -> "#F44336"
            }))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.marginEnd = 12
            layoutParams = lp
        })
        row.addView(TextView(this).apply {
            text     = "▶"
            textSize = 16f
            setTextColor(Color.parseColor("#BBBBBB"))
        })

        // タップで詳細画面へ
        row.setOnClickListener {
            val intent = Intent(this, ScoreYearDetailActivity::class.java)
            intent.putExtra("EXAM_TYPE", examType.name)
            intent.putExtra("YEAR",      year ?: -1)
            intent.putExtra("LABEL",     label)
            startActivity(intent)
        }
        root.addView(row)
    }
}