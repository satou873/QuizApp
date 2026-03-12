package com.example.quizapp

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.model.ExamType
import com.example.quizapp.model.WordEntry

class FlashCardActivity : AppCompatActivity() {

    private var cards: List<WordEntry> = emptyList()
    private var currentIndex = 0
    private var isFlipped    = false
    private var lastTapTime  = 0L
    private val DOUBLE_TAP_INTERVAL = 400L

    private lateinit var tvProgress: TextView
    private lateinit var tvCategory: TextView
    private lateinit var cardView: LinearLayout
    private lateinit var tvFront: TextView
    private lateinit var tvBack: TextView
    private lateinit var tvTapHint: TextView
    private lateinit var btnPrev: Button
    private lateinit var btnNext: Button
    private lateinit var tvEmpty: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val subject  = intent.getStringExtra("SUBJECT")  ?: "ENGINEERING"
        val category = intent.getStringExtra("CATEGORY") ?: "ALL"

        val scroll = ScrollView(this)
        val root   = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 32, 24, 32)
            setBackgroundColor(Color.parseColor("#F5F5F5"))
        }
        scroll.addView(root)
        setContentView(scroll)

        // タイトル
        root.addView(TextView(this).apply {
            text     = "⚡ 一問一答"
            textSize = 20f
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            setTextColor(Color.parseColor("#333333"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 4
            layoutParams = lp
        })

        // カテゴリ表示
        tvCategory = TextView(this).apply {
            text = if (category == "ALL") {
                if (subject == "ENGINEERING") "📡 無線工学 すべて" else "📋 法規 すべて"
            } else {
                category
            }
            textSize = 13f
            setTextColor(Color.parseColor("#888888"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 16
            layoutParams = lp
        }
        root.addView(tvCategory)

        // 進捗
        tvProgress = TextView(this).apply {
            textSize = 13f
            setTextColor(Color.parseColor("#888888"))
            gravity  = Gravity.CENTER
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 12
            layoutParams = lp
        }
        root.addView(tvProgress)

        // カードView
        cardView = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity     = Gravity.CENTER
            setBackgroundColor(Color.WHITE)
            elevation   = 8f
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 16
            layoutParams = lp
            setPadding(32, 32, 32, 32)
        }
        root.addView(cardView)

        tvFront = TextView(this).apply {
            textSize  = 20f
            typeface  = android.graphics.Typeface.DEFAULT_BOLD
            setTextColor(Color.parseColor("#333333"))
            gravity   = Gravity.CENTER
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        cardView.addView(tvFront)

        tvBack = TextView(this).apply {
            textSize   = 16f
            setTextColor(Color.parseColor("#555555"))
            gravity    = Gravity.CENTER
            visibility = View.GONE
            maxLines   = 6   // 通常表示は最大6行（長文は長押しで全文表示）
            ellipsize  = android.text.TextUtils.TruncateAt.END
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.topMargin = 24
            layoutParams = lp
            setOnLongClickListener {
                maxLines  = Int.MAX_VALUE
                ellipsize = null
                invalidate()
                true
            }
        }
        cardView.addView(tvBack)

        tvTapHint = TextView(this).apply {
            text    = "タップして答えを見る"
            textSize = 12f
            setTextColor(Color.parseColor("#AAAAAA"))
            gravity  = Gravity.CENTER
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.topMargin = 32
            layoutParams = lp
        }
        cardView.addView(tvTapHint)

        // カードタップ：表面=1タップで裏面へ、裏面=ダブルタップで次へ
        cardView.setOnClickListener {
            if (!isFlipped) {
                flipCard()
            } else {
                val now = System.currentTimeMillis()
                if (now - lastTapTime < DOUBLE_TAP_INTERVAL) {
                    nextCard()
                    lastTapTime = 0L
                } else {
                    lastTapTime = now
                }
            }
        }

        // ナビゲーションボタン行
        val navRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity     = Gravity.CENTER
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 16
            layoutParams = lp
        }

        btnPrev = Button(this).apply {
            text = "◀ 前へ"
            textSize = 14f
            setTextColor(Color.WHITE)
            backgroundTintList = android.content.res.ColorStateList.valueOf(
                Color.parseColor("#607D8B")
            )
            val lp = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
            )
            lp.marginEnd = 8
            layoutParams = lp
            setPadding(8, 24, 8, 24)
            setOnClickListener { prevCard() }
        }
        navRow.addView(btnPrev)

        btnNext = Button(this).apply {
            text = "次へ ▶"
            textSize = 14f
            setTextColor(Color.WHITE)
            backgroundTintList = android.content.res.ColorStateList.valueOf(
                Color.parseColor("#9C27B0")
            )
            val lp = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
            )
            layoutParams = lp
            setPadding(8, 24, 8, 24)
            setOnClickListener { nextCard() }
        }
        navRow.addView(btnNext)
        root.addView(navRow)

        // 空メッセージ
        tvEmpty = TextView(this).apply {
            text      = "このカテゴリの単語・公式が\nまだ登録されていません\n\n単語帳から登録してください📚"
            textSize  = 15f
            gravity   = Gravity.CENTER
            setTextColor(Color.parseColor("#999999"))
            visibility = View.GONE
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.topMargin = 48
            layoutParams = lp
        }
        root.addView(tvEmpty)

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
            lp.topMargin = 16
            layoutParams = lp
            setPadding(16, 36, 16, 36)
            setOnClickListener { finish() }
        })

        // データ読み込み
        loadCards(subject, category)
    }

    // ===== データ読み込み =====
    private fun loadCards(subject: String, category: String) {
        val allWords = WordStorage.loadWords(this)

        cards = allWords.filter { entry ->
            val matchSubject = when (subject) {
                "ENGINEERING" -> entry.examType.isEmpty() ||
                        entry.examType == ExamType.ENGINEERING_A.name ||
                        entry.examType == ExamType.ENGINEERING_B.name
                "LAW" -> entry.examType.isEmpty() ||
                        entry.examType == ExamType.LAW_A.name ||
                        entry.examType == ExamType.LAW_B.name
                else -> true
            }
            val matchCategory = category == "ALL" || entry.category == category
            matchSubject && matchCategory
        }.shuffled()

        currentIndex = 0
        isFlipped    = false

        if (cards.isEmpty()) {
            cardView.visibility   = View.GONE
            tvProgress.visibility = View.GONE
            tvEmpty.visibility    = View.VISIBLE
            btnPrev.isEnabled     = false
            btnNext.isEnabled     = false
        } else {
            cardView.visibility   = View.VISIBLE
            tvProgress.visibility = View.VISIBLE
            tvEmpty.visibility    = View.GONE
            showCard()
        }
    }

    // ===== カード表示 =====
    private fun showCard() {
        if (cards.isEmpty()) return
        val entry = cards[currentIndex]
        isFlipped    = false
        lastTapTime  = 0L

        tvFront.text         = entry.title
        tvBack.text          = entry.content
        tvBack.visibility    = View.GONE
        tvTapHint.visibility = View.VISIBLE
        tvTapHint.text       = "タップして答えを見る"
        cardView.setBackgroundColor(Color.WHITE)

        tvProgress.text   = "${currentIndex + 1} / ${cards.size}"
        btnPrev.isEnabled = currentIndex > 0
        btnNext.isEnabled = true
        btnNext.text      = if (currentIndex == cards.size - 1) "完了 ✅" else "次へ ▶"
    }

    // ===== カードめくり =====
    private fun flipCard() {
        isFlipped = !isFlipped
        if (isFlipped) {
            tvBack.maxLines  = 6
            tvBack.ellipsize = android.text.TextUtils.TruncateAt.END
            tvBack.visibility = View.VISIBLE
            tvTapHint.text    = "ダブルタップで次へ　長押しで全文表示"
            cardView.setBackgroundColor(Color.parseColor("#E8F5E9"))
        } else {
            tvBack.visibility = View.GONE
            tvTapHint.text    = "タップして答えを見る"
            cardView.setBackgroundColor(Color.WHITE)
        }
    }

    private fun prevCard() {
        if (currentIndex > 0) {
            currentIndex--
            showCard()
        }
    }

    private fun nextCard() {
        if (currentIndex < cards.size - 1) {
            currentIndex++
            showCard()
        } else {
            android.app.AlertDialog.Builder(this)
                .setTitle("✅ 完了！")
                .setMessage("全${cards.size}枚のカードを確認しました！\nもう一度シャッフルして練習しますか？")
                .setPositiveButton("🔀 シャッフルして再挑戦") { _, _ ->
                    cards = cards.shuffled()
                    currentIndex = 0
                    showCard()
                }
                .setNegativeButton("← 戻る") { _, _ -> finish() }
                .show()
        }
    }
}