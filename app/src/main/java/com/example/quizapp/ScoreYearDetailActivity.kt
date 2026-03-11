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
import com.example.quizapp.model.CheckLevel
import com.example.quizapp.model.ExamType
import com.example.quizapp.model.Question
import com.example.quizapp.model.QuizResult

class ScoreYearDetailActivity : AppCompatActivity() {

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
            text     = "📝 ${examType.label}"
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
            text     = "📅 $label"
            textSize = 16f
            setTextColor(Color.parseColor("#888888"))
            val lp   = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 8
            layoutParams = lp
        })

        // サマリー
        val correctCount = filtered.count { it.correctCount > 0 }
        val total        = filtered.size
        val pct          = if (total > 0) correctCount * 100 / total else 0
        root.addView(TextView(this).apply {
            text     = "正解率：$correctCount / $total 問（$pct%）"
            textSize = 18f
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
            lp.bottomMargin = 24
            layoutParams = lp
        })

        // 問題一覧
        val sorted = questions.mapNotNull { q ->
            filtered.find { it.questionId == q.id }?.let { Pair(q, it) }
        }

        if (sorted.isEmpty()) {
            root.addView(TextView(this).apply {
                text     = "まだ回答データがありません"
                textSize = 15f
                setTextColor(Color.parseColor("#999999"))
                gravity  = Gravity.CENTER
            })
        } else {
            sorted.forEach { (question, result) ->
                addQuestionRow(root, question, result)
            }
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

    private fun addQuestionRow(
        root: LinearLayout,
        question: Question,
        result: QuizResult
    ) {
        val pct   = (result.accuracy * 100).toInt()
        val level = result.checkLevel

        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity     = Gravity.CENTER_VERTICAL
            setBackgroundColor(Color.WHITE)
            setPadding(16, 16, 16, 16)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 2
            layoutParams = lp
        }

        // チェックレベルバッジ
        row.addView(TextView(this).apply {
            text     = level.emoji
            textSize = 16f
            setTextColor(Color.parseColor(level.color))
            gravity  = Gravity.CENTER
            width    = 40
        })

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

        // 正解率
        row.addView(TextView(this).apply {
            text     = "$pct%"
            textSize = 14f
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            setTextColor(Color.parseColor(when {
                pct >= 80 -> "#4CAF50"
                pct >= 60 -> "#FF9800"
                else      -> "#F44336"
            }))
            gravity  = Gravity.END
            width    = 60
        })

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

    private fun showQuestionDialog(question: Question, result: QuizResult) {
        val pct = (result.accuracy * 100).toInt()
        val msg = buildString {
            append("【正解率】$pct%（正解${result.correctCount}回 / 不正解${result.wrongCount}回）\n\n")
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