package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.data.QuizData
import com.example.quizapp.model.ExamType
import com.example.quizapp.model.Question
import com.example.quizapp.model.QuizResult

class AchievementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scroll = ScrollView(this)
        val root   = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 40, 40, 40)
            setBackgroundColor(Color.parseColor("#F5F5F5"))
        }
        scroll.addView(root)
        setContentView(scroll)

        val results = QuizStorage.loadResults(this)

        root.addView(TextView(this).apply {
            text      = "🏆 達成度"
            textSize  = 24f
            setTextColor(Color.parseColor("#333333"))
            typeface  = android.graphics.Typeface.DEFAULT_BOLD
            val lp    = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 32
            layoutParams = lp
        })

        ExamType.values().forEach { examType ->
            val allQuestions = QuizData.getQuestionsByExamType(this, examType)
            if (allQuestions.isEmpty()) return@forEach

            root.addView(makeSectionHeader("📋 ${examType.label}"))

            // 全年度まとめ（タップで詳細へ）
            addAchievementRow(root, results, examType, allQuestions, "全年度まとめ", null)

            // 年度別
            val terms = QuizData.getTermsByExamType(this, examType)
            terms.forEach { (yr, termLabel) ->
                val yearQs = QuizData.getQuestionsByExamTypeAndYear(this, examType, yr)
                addAchievementRow(root, results, examType, yearQs, termLabel, yr)
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

    private fun addAchievementRow(
        root: LinearLayout,
        results: List<QuizResult>,
        examType: ExamType,
        questions: List<Question>,
        label: String,
        year: Int?
    ) {
        val total       = questions.size
        val ids         = questions.map { it.id }.toSet()
        val filtered    = results.filter { it.questionId in ids }
        val answered    = filtered.size
        val perfect     = filtered.count { it.checkLevel.name == "PERFECT" }
        val answeredPct = if (total > 0) answered * 100 / total else 0
        val masteredPct = if (total > 0) perfect  * 100 / total else 0

        val row = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.WHITE)
            setPadding(24, 16, 24, 16)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 2
            layoutParams = lp
        }

        // ラベル行
        val headerRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity     = Gravity.CENTER_VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        headerRow.addView(TextView(this).apply {
            text     = "📅 $label"
            textSize = 15f
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            setTextColor(Color.parseColor("#555555"))
            layoutParams = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
            )
        })
        headerRow.addView(TextView(this).apply {
            text     = "回答$answeredPct% / 覚えた$masteredPct%"
            textSize = 13f
            setTextColor(Color.parseColor("#888888"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.marginEnd = 8
            layoutParams = lp
        })
        headerRow.addView(TextView(this).apply {
            text     = "▶"
            textSize = 16f
            setTextColor(Color.parseColor("#BBBBBB"))
        })
        row.addView(headerRow)

        // プログレスバー（回答済み）
        row.addView(makeProgressBar(answeredPct, "#2196F3"))
        // プログレスバー（覚えた）
        row.addView(makeProgressBar(masteredPct, "#4CAF50"))

        row.setOnClickListener {
            val intent = Intent(this, AchievementDetailActivity::class.java)
            intent.putExtra("EXAM_TYPE", examType.name)
            intent.putExtra("YEAR",      year ?: -1)
            intent.putExtra("LABEL",     label)
            startActivity(intent)
        }
        root.addView(row)
    }

    private fun makeProgressBar(progress: Int, color: String): ProgressBar {
        return ProgressBar(
            this, null, android.R.attr.progressBarStyleHorizontal
        ).apply {
            max = 100
            this.progress = progress
            progressTintList = android.content.res.ColorStateList.valueOf(
                Color.parseColor(color)
            )
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 20
            )
            lp.topMargin = 6
            layoutParams = lp
        }
    }
}