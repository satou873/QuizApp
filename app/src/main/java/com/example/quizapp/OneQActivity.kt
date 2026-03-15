package com.example.quizapp

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.model.OneQEntry

class OneQActivity : AppCompatActivity() {

    private lateinit var tvProgress: TextView
    private lateinit var tvQuestion: TextView
    private lateinit var choiceButtons: List<Button>
    private lateinit var tvResult: TextView
    private lateinit var tvExplanation: TextView
    private lateinit var btnNext: Button

    private lateinit var questions: List<OneQEntry>
    private var currentIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scroll = ScrollView(this)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 40, 32, 32)
            setBackgroundColor(Color.parseColor("#F5F5F5"))
        }
        scroll.addView(root)
        setContentView(scroll)

        // タイトル
        root.addView(TextView(this).apply {
            text = "❓ 一問一答"
            textSize = 22f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(Color.parseColor("#333333"))
            gravity = Gravity.CENTER
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 4
            layoutParams = lp
        })

        // 進捗表示
        tvProgress = TextView(this).apply {
            textSize = 13f
            setTextColor(Color.parseColor("#888888"))
            gravity = Gravity.CENTER
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 16
            layoutParams = lp
        }
        root.addView(tvProgress)

        // 問題文カード
        val questionCard = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.WHITE)
            setPadding(24, 20, 24, 20)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 20
            layoutParams = lp
        }
        tvQuestion = TextView(this).apply {
            textSize = 15f
            setTextColor(Color.parseColor("#222222"))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        questionCard.addView(tvQuestion)
        root.addView(questionCard)

        // 選択肢ボタン（最大5択）
        choiceButtons = (0 until 5).map { idx ->
            Button(this).apply {
                textSize = 14f
                setTextColor(Color.parseColor("#333333"))
                gravity = Gravity.START or Gravity.CENTER_VERTICAL
                setPadding(24, 20, 24, 20)
                backgroundTintList = android.content.res.ColorStateList.valueOf(
                    Color.parseColor("#EEEEEE")
                )
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                lp.bottomMargin = 10
                layoutParams = lp
                setOnClickListener { onChoiceSelected(idx) }
            }.also { root.addView(it) }
        }

        // 正誤表示
        tvResult = TextView(this).apply {
            textSize = 18f
            typeface = Typeface.DEFAULT_BOLD
            gravity = Gravity.CENTER
            visibility = View.GONE
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.topMargin = 8
            lp.bottomMargin = 8
            layoutParams = lp
        }
        root.addView(tvResult)

        // 解説カード
        tvExplanation = TextView(this).apply {
            textSize = 13f
            setTextColor(Color.parseColor("#444444"))
            setBackgroundColor(Color.parseColor("#FFF9C4"))
            setPadding(20, 16, 20, 16)
            visibility = View.GONE
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 16
            layoutParams = lp
        }
        root.addView(tvExplanation)

        // 次の問題ボタン
        btnNext = Button(this).apply {
            text = "次の問題へ ▶"
            textSize = 15f
            setTextColor(Color.WHITE)
            backgroundTintList = android.content.res.ColorStateList.valueOf(
                Color.parseColor("#4CAF50")
            )
            visibility = View.GONE
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 12
            layoutParams = lp
            setPadding(16, 36, 16, 36)
            setOnClickListener { showNext() }
        }
        root.addView(btnNext)

        // 戻るボタン
        root.addView(Button(this).apply {
            text = "← ホームへ戻る"
            textSize = 14f
            setTextColor(Color.WHITE)
            backgroundTintList = android.content.res.ColorStateList.valueOf(
                Color.parseColor("#607D8B")
            )
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams = lp
            setPadding(16, 32, 16, 32)
            setOnClickListener { finish() }
        })

        // 問題リストを読み込みシャッフル
        questions = OneQStorage.loadAll(this).shuffled()

        if (questions.isEmpty()) {
            tvQuestion.text = "出題できる問題がありません。\nマイページの一問一答管理から問題を追加してください。"
            tvProgress.text = ""
            choiceButtons.forEach { it.visibility = View.GONE }
        } else {
            showQuestion()
        }
    }

    private fun showQuestion() {
        val q = questions[currentIndex]
        tvProgress.text = "${currentIndex + 1} / ${questions.size} 問  （正解：$score 問）"
        tvQuestion.text = q.question

        choiceButtons.forEachIndexed { idx, btn ->
            val choice = q.choices.getOrNull(idx)
            if (choice != null) {
                btn.text = "${('A' + idx)}. $choice"
                btn.visibility = View.VISIBLE
                btn.isEnabled = true
                btn.backgroundTintList = android.content.res.ColorStateList.valueOf(
                    Color.parseColor("#EEEEEE")
                )
                btn.setTextColor(Color.parseColor("#333333"))
            } else {
                btn.visibility = View.GONE
            }
        }

        tvResult.visibility = View.GONE
        tvExplanation.visibility = View.GONE
        btnNext.visibility = View.GONE
    }

    private fun onChoiceSelected(selectedIdx: Int) {
        val q = questions[currentIndex]

        // 全ボタン無効化・色付け
        choiceButtons.forEachIndexed { idx, btn ->
            if (btn.visibility == View.VISIBLE) {
                btn.isEnabled = false
                when {
                    idx == q.correctIndex -> {
                        btn.backgroundTintList = android.content.res.ColorStateList.valueOf(
                            Color.parseColor("#81C784")  // 正解：緑
                        )
                        btn.setTextColor(Color.WHITE)
                    }
                    idx == selectedIdx -> {
                        btn.backgroundTintList = android.content.res.ColorStateList.valueOf(
                            Color.parseColor("#E57373")  // 選択した誤答：赤
                        )
                        btn.setTextColor(Color.WHITE)
                    }
                    else -> {
                        btn.backgroundTintList = android.content.res.ColorStateList.valueOf(
                            Color.parseColor("#BDBDBD")
                        )
                    }
                }
            }
        }

        val isCorrect = selectedIdx == q.correctIndex
        if (isCorrect) score++

        tvResult.text = if (isCorrect) "⭕ 正解！" else "❌ 不正解"
        tvResult.setTextColor(
            if (isCorrect) Color.parseColor("#388E3C") else Color.parseColor("#D32F2F")
        )
        tvResult.visibility = View.VISIBLE

        if (q.explanation.isNotEmpty()) {
            tvExplanation.text = "💡 解説：${q.explanation}"
            tvExplanation.visibility = View.VISIBLE
        }

        val isLast = currentIndex == questions.size - 1
        btnNext.text = if (isLast) "結果を確認する ✓" else "次の問題へ ▶"
        btnNext.visibility = View.VISIBLE
    }

    private fun showNext() {
        if (currentIndex == questions.size - 1) {
            // 全問終了ダイアログ
            android.app.AlertDialog.Builder(this)
                .setTitle("🎉 全問終了！")
                .setMessage(
                    "正解数：$score / ${questions.size} 問\n" +
                    "正答率：${if (questions.isNotEmpty()) "%.1f".format(score.toDouble() / questions.size * 100) else "0.0"}%"
                )
                .setPositiveButton("もう一度") { _, _ ->
                    questions = OneQStorage.loadAll(this).shuffled()
                    currentIndex = 0
                    score = 0
                    showQuestion()
                }
                .setNegativeButton("ホームへ戻る") { _, _ -> finish() }
                .show()
        } else {
            currentIndex++
            showQuestion()
        }
    }
}
