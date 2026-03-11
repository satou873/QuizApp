package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.data.QuizData
import com.example.quizapp.model.CheckLevel
import com.example.quizapp.model.ExamType

class YearSelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_year_select)

        val examTypeName = intent.getStringExtra("EXAM_TYPE") ?: return
        val examType     = ExamType.valueOf(examTypeName)
        val mode         = intent.getStringExtra("MODE") ?: "EXAM"

        val tvTitle    = findViewById<TextView>(R.id.tvExamTitle)
        val tvSub      = findViewById<TextView>(R.id.tvExamSub)
        val layout     = findViewById<LinearLayout>(R.id.layoutYearButtons)
        val btnAllYear = findViewById<Button>(R.id.btnAllYear)
        val btnBack    = findViewById<Button>(R.id.btnBack)

        tvTitle.text = when (mode) {
            "REVIEW"     -> "🔁 復習問題\n${examType.label}"
            "UNANSWERED" -> "❓ 未回答問題\n${examType.label}"
            else         -> examType.label
        }
        tvSub.text = "試験期を選択してください"

        // term（試験期）ごとにボタンを生成
        val terms = QuizData.getTermsByExamType(this, examType)

        terms.forEach { (year, termLabel) ->
            val ids   = getFilteredIdsByTerm(mode, examType, termLabel)
            val count = ids.size
            if (count == 0) return@forEach

            val btn = Button(this).apply {
                text = "📅 $termLabel　（${count}問）"
                textSize = 16f
                setTextColor(Color.WHITE)
                backgroundTintList = android.content.res.ColorStateList.valueOf(
                    Color.parseColor(
                        when (mode) {
                            "REVIEW"     -> "#F44336"
                            "UNANSWERED" -> "#FF9800"
                            else         -> "#FF9800"
                        }
                    )
                )
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply { setMargins(0, 0, 0, 16) }
                setPadding(16, 40, 16, 40)
                gravity = Gravity.CENTER
            }
            btn.setOnClickListener {
                startQuizByTerm(mode, examType, year, termLabel, ids)
            }
            layout.addView(btn)
        }

        btnAllYear.setOnClickListener {
            val ids = getFilteredIds(mode, examType, null)
            if (ids.isEmpty()) {
                android.widget.Toast.makeText(
                    this, "該当する問題がありません", android.widget.Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            startQuizByTerm(mode, examType, -1, "全試験期", ids)
        }

        btnAllYear.backgroundTintList = android.content.res.ColorStateList.valueOf(
            Color.parseColor(
                when (mode) {
                    "REVIEW"     -> "#B71C1C"
                    "UNANSWERED" -> "#E65100"
                    else         -> "#9C27B0"
                }
            )
        )
        btnAllYear.text = when (mode) {
            "REVIEW"     -> "🔁 全試験期の復習問題"
            "UNANSWERED" -> "❓ 全試験期の未回答・未チェック問題"
            else         -> "🗂️ 全試験期まとめて解く"
        }

        btnBack.setOnClickListener { finish() }
    }

    // ===== term 指定でフィルタリング =====
    private fun getFilteredIdsByTerm(
        mode: String,
        examType: ExamType,
        term: String
    ): List<Int> {
        val base = QuizData.getQuestionsByTerm(this, examType, term)
        return applyModeFilter(mode, base.map { it.id }, base)
    }

    private fun getFilteredIds(
        mode: String,
        examType: ExamType,
        year: Int?
    ): List<Int> {
        val base = if (year != null) {
            QuizData.getQuestionsByExamTypeAndYear(this, examType, year)
        } else {
            QuizData.getQuestionsByExamType(this, examType)
        }
        return applyModeFilter(mode, base.map { it.id }, base)
    }

    private fun applyModeFilter(
        mode: String,
        baseIds: List<Int>,
        baseQuestions: List<com.example.quizapp.model.Question>
    ): List<Int> {
        return when (mode) {
            "REVIEW" -> {
                val results   = QuizStorage.loadResults(this)
                val reviewIds = results
                    .filter {
                        it.checkLevel == CheckLevel.FORGOT ||
                                it.checkLevel == CheckLevel.POOR   ||
                                it.checkLevel == CheckLevel.UNCHECKED
                    }
                    .map { it.questionId }.toSet()
                baseIds.filter { it in reviewIds }
            }
            "UNANSWERED" -> {
                val results = QuizStorage.loadResults(this)
                val neverAnsweredIds = baseQuestions
                    .filter { q -> results.none { it.questionId == q.id } }
                    .map { it.id }
                val uncheckedIds = results
                    .filter { it.checkLevel == CheckLevel.UNCHECKED }
                    .map { it.questionId }
                    .filter { id -> baseQuestions.any { it.id == id } }
                (neverAnsweredIds + uncheckedIds).distinct()
            }
            else -> baseIds
        }
    }

    private fun startQuizByTerm(
        mode: String,
        examType: ExamType,
        year: Int,
        term: String,
        ids: List<Int>
    ) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("MODE",      mode)
        intent.putExtra("EXAM_TYPE", examType.name)
        intent.putExtra("YEAR",      year)
        intent.putExtra("TERM",      term)
        intent.putIntegerArrayListExtra("QUESTION_IDS", ArrayList(ids))
        startActivity(intent)
    }
}