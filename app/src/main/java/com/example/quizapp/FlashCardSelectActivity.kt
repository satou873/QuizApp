package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class FlashCardSelectActivity : AppCompatActivity() {

    // 無線工学カテゴリ
    private val engineeringCategories = listOf(
        "多重通信システムの概要",
        "基礎理論",
        "変復調",
        "無線送受信装置",
        "多重通信システム",
        "中継方式",
        "レーダー",
        "空中線・給電線",
        "電波伝搬",
        "電源",
        "測定"
    )

    // 法規カテゴリ
    private val lawCategories = listOf(
        "電波法の概要",
        "無線局の免許",
        "無線設備",
        "無線従事者",
        "運用",
        "業務書類等",
        "監督等"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scroll = ScrollView(this)
        val root   = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
            setBackgroundColor(Color.parseColor("#F5F5F5"))
        }
        scroll.addView(root)
        setContentView(scroll)

        // タイトル
        root.addView(TextView(this).apply {
            text     = "⚡ 一問一答"
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
            text     = "科目を選択してください"
            textSize = 14f
            setTextColor(Color.parseColor("#888888"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 32
            layoutParams = lp
        })

        // ===== 無線工学セクション =====
        root.addView(makeSectionHeader("📡 無線工学", "#1565C0"))
        engineeringCategories.forEach { category ->
            root.addView(makeCategoryButton(category, "ENGINEERING", "#1565C0"))
        }

        // 無線工学「全カテゴリ」ボタン
        root.addView(makeAllButton("無線工学 すべて", "ENGINEERING", "#0D47A1"))

        val divider = TextView(this).apply {
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 2
            )
            lp.topMargin    = 24
            lp.bottomMargin = 24
            layoutParams = lp
            setBackgroundColor(Color.parseColor("#DDDDDD"))
        }
        root.addView(divider)

        // ===== 法規セクション =====
        root.addView(makeSectionHeader("📋 法規", "#6A1B9A"))
        lawCategories.forEach { category ->
            root.addView(makeCategoryButton(category, "LAW", "#6A1B9A"))
        }

        // 法規「全カテゴリ」ボタン
        root.addView(makeAllButton("法規 すべて", "LAW", "#4A148C"))

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
            lp.topMargin = 32
            layoutParams = lp
            setPadding(16, 36, 16, 36)
            setOnClickListener { finish() }
        })
    }

    private fun makeSectionHeader(text: String, color: String): TextView {
        return TextView(this).apply {
            this.text = text
            textSize  = 18f
            typeface  = android.graphics.Typeface.DEFAULT_BOLD
            setTextColor(Color.parseColor(color))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 12
            layoutParams = lp
        }
    }

    private fun makeCategoryButton(category: String, subject: String, color: String): Button {
        return Button(this).apply {
            text = category
            textSize = 15f
            gravity  = Gravity.START or Gravity.CENTER_VERTICAL
            setTextColor(Color.WHITE)
            backgroundTintList = android.content.res.ColorStateList.valueOf(
                Color.parseColor(color)
            )
            alpha = 0.85f
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 10
            layoutParams = lp
            setPadding(32, 28, 32, 28)
            setOnClickListener {
                startFlashCard(subject, category)
            }
        }
    }

    private fun makeAllButton(label: String, subject: String, color: String): Button {
        return Button(this).apply {
            text = "⚡ $label"
            textSize = 15f
            setTextColor(Color.WHITE)
            backgroundTintList = android.content.res.ColorStateList.valueOf(
                Color.parseColor(color)
            )
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.topMargin    = 4
            lp.bottomMargin = 4
            layoutParams = lp
            setPadding(32, 28, 32, 28)
            setOnClickListener {
                startFlashCard(subject, "ALL")
            }
        }
    }

    private fun startFlashCard(subject: String, category: String) {
        val intent = Intent(this, FlashCardActivity::class.java).apply {
            putExtra("SUBJECT",  subject)
            putExtra("CATEGORY", category)
        }
        startActivity(intent)
    }
}