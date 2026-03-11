package com.example.quizapp

import android.content.Intent
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

class CheckStatusActivity : AppCompatActivity() {

    private lateinit var root: LinearLayout
    private lateinit var listContainer: LinearLayout
    private var allQuestions: List<Question>  = emptyList()
    private var allResults:   List<QuizResult> = emptyList()
    private var currentFilter: CheckLevel? = null   // null = すべて

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val examTypeName = intent.getStringExtra("EXAM_TYPE")
        val year         = intent.getIntExtra("YEAR", -1)

        // 試験種別・年度が未指定 → 選択画面として機能
        if (examTypeName == null) {
            showSelectScreen()
            return
        }

        val examType = try { ExamType.valueOf(examTypeName) } catch (e: Exception) {
            showSelectScreen(); return
        }

        allQuestions = if (year > 0) {
            QuizData.getQuestionsByExamTypeAndYear(this, examType, year)
        } else {
            QuizData.getQuestionsByExamType(this, examType)
        }
        allResults = QuizStorage.loadResults(this)

        val yearLabel = if (year > 0) "${year}年" else "全年度"

        val scroll = ScrollView(this)
        root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
            setBackgroundColor(Color.parseColor("#F5F5F5"))
        }
        scroll.addView(root)
        setContentView(scroll)

        // タイトル
        root.addView(TextView(this).apply {
            text     = "✅ ${examType.label}　$yearLabel"
            textSize = 20f
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            setTextColor(Color.parseColor("#333333"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 16
            layoutParams = lp
        })

        // フィルターボタン行
        root.addView(makeFilterRow())

        // リストコンテナ
        listContainer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        root.addView(listContainer)

        // 戻るボタン
        root.addView(Button(this).apply {
            text = "← 戻る"
            textSize = 15f
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
            setPadding(16, 36, 16, 36)
            setOnClickListener { finish() }
        })

        refreshList()
    }

    // ===== 試験種別・年度選択画面 =====
    private fun showSelectScreen() {
        val scroll = ScrollView(this)
        val root   = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
            setBackgroundColor(Color.parseColor("#F5F5F5"))
        }
        scroll.addView(root)
        setContentView(scroll)

        root.addView(TextView(this).apply {
            text     = "✅ チェック状態"
            textSize = 24f
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            setTextColor(Color.parseColor("#333333"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 8
            layoutParams = lp
        })
        root.addView(TextView(this).apply {
            text     = "試験・年度を選択してください"
            textSize = 14f
            setTextColor(Color.parseColor("#888888"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 24
            layoutParams = lp
        })

        ExamType.values().forEach { examType ->
            val allQs = QuizData.getQuestionsByExamType(this, examType)
            if (allQs.isEmpty()) return@forEach

            // 試験種別ヘッダー
            root.addView(TextView(this).apply {
                text     = "📋 ${examType.label}"
                textSize = 16f
                typeface = android.graphics.Typeface.DEFAULT_BOLD
                setTextColor(Color.WHITE)
                setBackgroundColor(Color.parseColor("#37474F"))
                setPadding(20, 16, 20, 16)
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                lp.topMargin    = 16
                lp.bottomMargin = 4
                layoutParams = lp
            })

            // 全年度ボタン
            root.addView(makeSelectButton("🗂️ 全年度まとめ", "#4CAF50") {
                goToDetail(examType, -1)
            })

            // 年度別ボタン（ユーザー追加問題含む）
            QuizData.getYearsByExamType(this, examType).forEach { year ->
                root.addView(makeSelectButton("📅 ${year}年", "#2196F3") {
                    goToDetail(examType, year)
                })
            }
        }

        root.addView(Button(this).apply {
            text = "← 戻る"
            textSize = 15f
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
            setPadding(16, 36, 16, 36)
            setOnClickListener { finish() }
        })
    }

    private fun makeSelectButton(label: String, color: String, onClick: () -> Unit): Button {
        return Button(this).apply {
            text = label
            textSize = 15f
            setTextColor(Color.WHITE)
            backgroundTintList = android.content.res.ColorStateList.valueOf(
                Color.parseColor(color)
            )
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 6
            layoutParams = lp
            setPadding(16, 32, 16, 32)
            setOnClickListener { onClick() }
        }
    }

    private fun goToDetail(examType: ExamType, year: Int) {
        val intent = Intent(this, CheckStatusActivity::class.java)
        intent.putExtra("EXAM_TYPE", examType.name)
        intent.putExtra("YEAR", year)
        startActivity(intent)
    }

    // ===== フィルターボタン行 =====
    private fun makeFilterRow(): LinearLayout {
        val scroll = android.widget.HorizontalScrollView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 16
            layoutParams = lp
        }
        scroll.addView(row)

        data class FilterItem(val label: String, val level: CheckLevel?, val color: String)
        val filters = listOf(
            FilterItem("すべて",    null,              "#607D8B"),
            FilterItem("💪 覚えた", CheckLevel.PERFECT,"#4CAF50"),
            FilterItem("😊 まあまあ",CheckLevel.GOOD,  "#2196F3"),
            FilterItem("🤔 微妙",   CheckLevel.POOR,   "#FF9800"),
            FilterItem("😢 忘れた", CheckLevel.FORGOT, "#F44336"),
            FilterItem("❓ 未チェック",CheckLevel.UNCHECKED,"#9E9E9E")
        )

        val container = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        filters.forEach { item ->
            val btn = Button(this).apply {
                text = item.label
                textSize = 13f
                setTextColor(Color.WHITE)
                backgroundTintList = android.content.res.ColorStateList.valueOf(
                    Color.parseColor(item.color)
                )
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                lp.marginEnd = 8
                layoutParams = lp
                setPadding(16, 20, 16, 20)
                alpha = if (currentFilter == item.level) 1.0f else 0.6f
            }
            btn.setOnClickListener {
                currentFilter = item.level
                refreshList()
            }
            row.addView(btn)
        }

        // HorizontalScrollView をラップする LinearLayout
        val wrapper = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        wrapper.addView(scroll)
        return wrapper
    }

    // ===== リスト更新 =====
    private fun refreshList() {
        listContainer.removeAllViews()

        val results = QuizStorage.loadResults(this)

        val displayQuestions = allQuestions.filter { q ->
            val result = results.find { it.questionId == q.id }
            val level  = result?.checkLevel ?: CheckLevel.UNCHECKED
            currentFilter == null || level == currentFilter
        }

        if (displayQuestions.isEmpty()) {
            listContainer.addView(TextView(this).apply {
                text     = "該当する問題がありません"
                textSize = 15f
                setTextColor(Color.parseColor("#999999"))
                gravity  = Gravity.CENTER
                val lp   = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                lp.topMargin = 32
                layoutParams = lp
            })
            return
        }

        // 件数表示
        listContainer.addView(TextView(this).apply {
            text     = "${displayQuestions.size} 件"
            textSize = 13f
            setTextColor(Color.parseColor("#888888"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 8
            layoutParams = lp
        })

        displayQuestions.forEach { question ->
            val result = results.find { it.questionId == question.id }
            val level  = result?.checkLevel ?: CheckLevel.UNCHECKED
            addRow(question, result, level)
        }
    }

    private fun addRow(question: Question, result: QuizResult?, level: CheckLevel) {
        val bgColor = when (level) {
            CheckLevel.PERFECT   -> "#E8F5E9"
            CheckLevel.GOOD      -> "#E3F2FD"
            CheckLevel.POOR      -> "#FFF3E0"
            CheckLevel.FORGOT    -> "#FFEBEE"
            CheckLevel.UNCHECKED -> "#FFFFFF"
        }

        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity     = Gravity.CENTER_VERTICAL
            setBackgroundColor(Color.parseColor(bgColor))
            setPadding(16, 14, 16, 14)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 2
            layoutParams = lp
        }

        // チェック絵文字
        row.addView(TextView(this).apply {
            text     = level.emoji
            textSize = 20f
            gravity  = Gravity.CENTER
            width    = 56
        })

        // 問題文
        row.addView(TextView(this).apply {
            text      = "Q${question.id}　${question.questionText}"
            textSize  = 13f
            setTextColor(Color.parseColor("#333333"))
            maxLines  = 2
            ellipsize = android.text.TextUtils.TruncateAt.END
            layoutParams = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
            )
        })

        // 正解率
        val pct = result?.let { (it.accuracy * 100).toInt() } ?: -1
        row.addView(TextView(this).apply {
            text     = if (pct >= 0) "$pct%" else "未"
            textSize = 13f
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            setTextColor(Color.parseColor(when {
                pct < 0  -> "#BBBBBB"
                pct >= 80 -> "#4CAF50"
                pct >= 60 -> "#FF9800"
                else      -> "#F44336"
            }))
            gravity  = Gravity.END
            width    = 60
        })

        // タップで解答・解説
        row.setOnClickListener { showDialog(question, result) }
        listContainer.addView(row)

        // 区切り線
        listContainer.addView(View(this).apply {
            setBackgroundColor(Color.parseColor("#DDDDDD"))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 1
            )
        })
    }

    private fun showDialog(question: Question, result: QuizResult?) {
        val pct = result?.let { (it.accuracy * 100).toInt() }
        val msg = buildString {
            if (pct != null) {
                append("【正解率】$pct%（正解${result!!.correctCount}回 / 不正解${result.wrongCount}回）\n\n")
            } else {
                append("【未回答】\n\n")
            }
            append("【選択肢】\n")
            question.choices.forEachIndexed { index, choice ->
                val mark = if (index == question.correctAnswerIndex) "✅ " else "　 "
                append("$mark${index + 1}. $choice\n")
            }
            append("\n【正解】\n${question.correctAnswerIndex + 1}. ${question.choices[question.correctAnswerIndex]}\n")
            append("\n【解説】\n${question.explanation}")
        }
        android.app.AlertDialog.Builder(this)
            .setTitle("Q${question.id}　${question.questionText}")
            .setMessage(msg)
            .setPositiveButton("閉じる", null)
            .show()
    }
}