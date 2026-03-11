package com.example.quizapp

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.data.QuizData
import com.example.quizapp.model.ExamType
import com.example.quizapp.model.Question
import com.example.quizapp.model.QuizResult

class AchievementDetailActivity : AppCompatActivity() {

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

        val examTypeName = intent.getStringExtra("EXAM_TYPE") ?: return
        val year         = intent.getIntExtra("YEAR", -1)
        val label        = intent.getStringExtra("LABEL") ?: ""
        val examType     = try { ExamType.valueOf(examTypeName) } catch (e: Exception) { return }

        val questions = if (year > 0) {
            QuizData.getQuestionsByExamTypeAndYear(this, examType, year)
        } else {
            QuizData.getQuestionsByExamType(this, examType)
        }
        val results  = QuizStorage.loadResults(this)
        val ids      = questions.map { it.id }.toSet()
        val filtered = results.filter { it.questionId in ids }

        // タイトル
        root.addView(TextView(this).apply {
            text     = "🏆 ${examType.label}"
            textSize = 22f
            setTextColor(Color.parseColor("#333333"))
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            val lp   = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 4
            layoutParams = lp
        })
        root.addView(TextView(this).apply {
            text     = "📅 $label　　タップで解答・解説表示"
            textSize = 14f
            setTextColor(Color.parseColor("#888888"))
            val lp   = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 16
            layoutParams = lp
        })

        // 凡例
        root.addView(makeLegend())

        // 問題一覧
        questions.forEach { question ->
            val result = filtered.find { it.questionId == question.id }
            addQuestionRow(root, question, result)
        }

        // 戻るボタン
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

    private fun makeLegend(): LinearLayout {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity     = Gravity.CENTER_VERTICAL
            val lp      = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 16
            layoutParams = lp
        }
        listOf("⭕" to "#4CAF50", "❌" to "#F44336", "－" to "#BBBBBB").forEach { (mark, color) ->
            row.addView(TextView(this).apply {
                text     = mark
                textSize = 18f
                setTextColor(Color.parseColor(color))
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                lp.marginEnd = 4
                layoutParams = lp
            })
        }
        row.addView(TextView(this).apply {
            text     = "← 直近4回の正誤（新しい順）"
            textSize = 12f
            setTextColor(Color.parseColor("#888888"))
        })
        return row
    }

    private fun addQuestionRow(
        root: LinearLayout,
        question: Question,
        result: QuizResult?
    ) {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity     = Gravity.CENTER_VERTICAL
            setBackgroundColor(Color.WHITE)
            setPadding(16, 14, 16, 14)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 2
            layoutParams = lp
        }

        // Q番号
        row.addView(TextView(this).apply {
            text     = "Q${question.id}"
            textSize = 13f
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            setTextColor(Color.parseColor("#2196F3"))
            width    = 60
            gravity  = Gravity.CENTER
        })

        // 問題文
        row.addView(TextView(this).apply {
            text      = question.questionText
            textSize  = 13f
            setTextColor(Color.parseColor("#333333"))
            maxLines  = 2
            ellipsize = android.text.TextUtils.TruncateAt.END
            layoutParams = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
            )
        })

        // 直近4回の正誤（新しい順）
        val histRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity     = Gravity.CENTER_VERTICAL
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.marginStart = 8
            layoutParams = lp
        }
        val history = result?.recentHistory?.reversed() ?: emptyList()
        repeat(4) { i ->
            histRow.addView(TextView(this).apply {
                text     = when {
                    i >= history.size -> "－"
                    history[i]        -> "⭕"
                    else              -> "❌"
                }
                textSize = 15f
                setTextColor(Color.parseColor(when {
                    i >= history.size -> "#BBBBBB"
                    history[i]        -> "#4CAF50"
                    else              -> "#F44336"
                }))
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                lp.marginStart = 2
                layoutParams = lp
            })
        }
        row.addView(histRow)

        // チェックレベル絵文字
        row.addView(TextView(this).apply {
            text     = result?.checkLevel?.emoji ?: "❓"
            textSize = 16f
            gravity  = Gravity.CENTER
            val lp   = LinearLayout.LayoutParams(40, LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.marginStart = 8
            layoutParams = lp
        })

        // タップで解答・解説ダイアログ
        row.setOnClickListener { showQuestionDialog(question, result) }

        root.addView(row)

        // 区切り線
        root.addView(View(this).apply {
            setBackgroundColor(Color.parseColor("#EEEEEE"))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 1
            )
        })
    }

    private fun showQuestionDialog(question: Question, result: QuizResult?) {
        val pct = result?.let { (it.accuracy * 100).toInt() } ?: 0
        val msg = buildString {
            if (result != null) {
                append("【正解率】$pct%（正解${result.correctCount}回 / 不正解${result.wrongCount}回）\n\n")
            } else {
                append("【未回答】\n\n")
            }
            append("【選択肢】\n")
            question.choices.forEachIndexed { index, choice ->
                val mark = if (index == question.correctAnswerIndex) "✅ " else "　 "
                append("$mark${index + 1}. $choice\n")
            }
            append("\n【正解】\n")
            append("${question.correctAnswerIndex + 1}. ${question.choices[question.correctAnswerIndex]}\n")
            append("\n【解説】\n")
            append(question.explanation)
        }
        android.app.AlertDialog.Builder(this)
            .setTitle("Q${question.id}　${question.questionText}")
            .setMessage(msg)
            .setPositiveButton("閉じる", null)
            .show()
    }
}