package com.example.quizapp

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.model.OneQEntry

class OneQListActivity : AppCompatActivity() {

    private lateinit var listContainer: LinearLayout
    private lateinit var genreFilterRow: LinearLayout
    private var currentGenreFilter: String = "ALL"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val scroll = ScrollView(this)
        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(16, 32, 16, 32)
            setBackgroundColor(Color.parseColor("#F5F5F5"))
        }
        scroll.addView(root)
        setContentView(scroll)

        // タイトル
        root.addView(TextView(this).apply {
            text = "📋 一問一答管理"
            textSize = 24f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(Color.parseColor("#333333"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 12
            layoutParams = lp
        })

        // 上部ボタン行：新規追加 & ジャンル管理
        val btnRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 12
            layoutParams = lp
        }
        btnRow.addView(Button(this).apply {
            text = "➕ 新規追加"
            textSize = 14f
            setTextColor(Color.WHITE)
            backgroundTintList = android.content.res.ColorStateList.valueOf(
                Color.parseColor("#4CAF50")
            )
            val lp = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
            )
            lp.marginEnd = 8
            layoutParams = lp
            setPadding(16, 24, 16, 24)
            setOnClickListener { showOneQDialog(null) }
        })
        btnRow.addView(Button(this).apply {
            text = "🏷️ ジャンル管理"
            textSize = 14f
            setTextColor(Color.WHITE)
            backgroundTintList = android.content.res.ColorStateList.valueOf(
                Color.parseColor("#9C27B0")
            )
            layoutParams = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
            )
            setPadding(16, 24, 16, 24)
            setOnClickListener {
                startActivity(Intent(this@OneQListActivity, OneQGenreActivity::class.java))
            }
        })
        root.addView(btnRow)

        // ジャンルフィルター（横スクロール）
        genreFilterRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        root.addView(HorizontalScrollView(this).apply {
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 12
            layoutParams = lp
            isHorizontalScrollBarEnabled = false
            addView(genreFilterRow)
        })

        // 問題リストコンテナ
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
            lp.topMargin = 16
            layoutParams = lp
            setPadding(16, 28, 16, 28)
            setOnClickListener { finish() }
        })
    }

    override fun onResume() {
        super.onResume()
        refreshGenreFilter()
        refreshList()
    }

    // ===== ジャンルフィルター更新 =====
    private fun refreshGenreFilter() {
        genreFilterRow.removeAllViews()
        val genres = OneQGenreStorage.loadGenres(this)

        // 選択中のジャンルが削除されていた場合はリセット
        if (currentGenreFilter != "ALL" && !genres.contains(currentGenreFilter)) {
            currentGenreFilter = "ALL"
        }

        val allFilters = listOf("ALL") + genres
        allFilters.forEach { key ->
            val label      = if (key == "ALL") "すべて" else key
            val isSelected = key == currentGenreFilter
            genreFilterRow.addView(Button(this).apply {
                text = label
                textSize = 13f
                setTextColor(if (isSelected) Color.WHITE else Color.parseColor("#555555"))
                backgroundTintList = android.content.res.ColorStateList.valueOf(
                    if (isSelected) Color.parseColor("#4CAF50") else Color.parseColor("#DDDDDD")
                )
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                lp.marginEnd = 8
                layoutParams = lp
                setPadding(24, 16, 24, 16)
                setOnClickListener {
                    currentGenreFilter = key
                    refreshGenreFilter()
                    refreshList()
                }
            })
        }
    }

    // ===== 一問一答リスト更新 =====
    private fun refreshList() {
        listContainer.removeAllViews()
        val entries = OneQStorage.loadByGenre(this, currentGenreFilter)

        if (entries.isEmpty()) {
            listContainer.addView(TextView(this).apply {
                text = if (currentGenreFilter == "ALL")
                    "追加された問題がありません。\n「新規追加」ボタンで問題を追加してください。"
                else
                    "「${currentGenreFilter}」ジャンルの問題がありません。"
                textSize = 13f
                setTextColor(Color.parseColor("#888888"))
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                lp.topMargin = 16
                lp.bottomMargin = 8
                layoutParams = lp
            })
            return
        }

        listContainer.addView(TextView(this).apply {
            text = "全 ${entries.size} 問"
            textSize = 12f
            setTextColor(Color.parseColor("#666666"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 8
            layoutParams = lp
        })

        entries.forEachIndexed { index, entry ->
            addEntryRow(entry, index + 1)
        }
    }

    // ===== 一問一答問題カード =====
    private fun addEntryRow(entry: OneQEntry, number: Int) {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.parseColor("#E8F5E9"))
            setPadding(16, 12, 16, 12)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 8
            layoutParams = lp
        }

        val headerRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER_VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        val genreLabel = if (entry.genre.isNotEmpty()) "【${entry.genre}】" else "【未設定】"
        headerRow.addView(TextView(this).apply {
            text = "問$number　$genreLabel"
            textSize = 11f
            setTextColor(Color.parseColor("#2E7D32"))
            layoutParams = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
            )
        })

        // 編集ボタン
        headerRow.addView(Button(this).apply {
            text = "編集"
            textSize = 11f
            setTextColor(Color.WHITE)
            backgroundTintList = android.content.res.ColorStateList.valueOf(
                Color.parseColor("#2196F3")
            )
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.marginEnd = 6
            layoutParams = lp
            setPadding(16, 8, 16, 8)
            setOnClickListener { showOneQDialog(entry) }
        })

        // 削除ボタン
        headerRow.addView(Button(this).apply {
            text = "削除"
            textSize = 11f
            setTextColor(Color.WHITE)
            backgroundTintList = android.content.res.ColorStateList.valueOf(
                Color.parseColor("#F44336")
            )
            setPadding(16, 8, 16, 8)
            setOnClickListener {
                AlertDialog.Builder(this@OneQListActivity)
                    .setTitle("削除確認")
                    .setMessage("この問題を削除しますか？")
                    .setPositiveButton("削除") { _, _ ->
                        OneQStorage.delete(this@OneQListActivity, entry.id)
                        refreshList()
                    }
                    .setNegativeButton("キャンセル", null)
                    .show()
            }
        })

        card.addView(headerRow)

        card.addView(TextView(this).apply {
            text = entry.question
            textSize = 13f
            setTextColor(Color.parseColor("#333333"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.topMargin = 4
            lp.bottomMargin = 4
            layoutParams = lp
        })

        entry.choices.forEachIndexed { idx, choice ->
            card.addView(TextView(this).apply {
                text = "${if (idx == entry.correctIndex) "✅" else "　"} ${idx + 1}. $choice"
                textSize = 12f
                setTextColor(
                    if (idx == entry.correctIndex) Color.parseColor("#2E7D32")
                    else Color.parseColor("#555555")
                )
            })
        }

        listContainer.addView(card)
    }

    // ===== 問題追加・編集ダイアログ =====
    private fun showOneQDialog(existing: OneQEntry?) {
        val scrollDialog = ScrollView(this)
        val dialogView = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 32, 48, 16)
        }
        scrollDialog.addView(dialogView)

        fun label(text: String) = TextView(this).apply {
            this.text = text
            textSize = 13f
            setTextColor(Color.parseColor("#888888"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.topMargin = 12
            layoutParams = lp
        }

        fun editText(hint: String, value: String = "", multiLine: Boolean = false) =
            EditText(this).apply {
                this.hint = hint
                textSize = 14f
                inputType = if (multiLine)
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
                else
                    InputType.TYPE_CLASS_TEXT
                if (multiLine) { minLines = 2; maxLines = 5 }
                gravity = if (multiLine) Gravity.TOP or Gravity.START else Gravity.CENTER_VERTICAL
                isFocusable = true
                isFocusableInTouchMode = true
                setText(value)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }

        // ジャンル選択
        dialogView.addView(label("ジャンル"))
        val genres = OneQGenreStorage.loadGenres(this)
        val genreOptions = listOf("（未設定）") + genres
        val spinnerGenre = Spinner(this).apply {
            adapter = ArrayAdapter(
                this@OneQListActivity,
                android.R.layout.simple_spinner_dropdown_item,
                genreOptions
            )
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        val initialGenreIdx = genreOptions.indexOf(
            existing?.genre?.takeIf { it.isNotEmpty() } ?: "（未設定）"
        ).coerceAtLeast(0)
        spinnerGenre.setSelection(initialGenreIdx)
        dialogView.addView(spinnerGenre)

        // 問題文
        dialogView.addView(label("問題文"))
        val etQuestion = editText("問題文を入力", existing?.question ?: "", multiLine = true)
        dialogView.addView(etQuestion)

        // 選択肢①〜⑤
        dialogView.addView(label("選択肢①"))
        val etC0 = editText("選択肢①", existing?.choices?.getOrNull(0) ?: "")
        dialogView.addView(etC0)
        dialogView.addView(label("選択肢②"))
        val etC1 = editText("選択肢②", existing?.choices?.getOrNull(1) ?: "")
        dialogView.addView(etC1)
        dialogView.addView(label("選択肢③"))
        val etC2 = editText("選択肢③", existing?.choices?.getOrNull(2) ?: "")
        dialogView.addView(etC2)
        dialogView.addView(label("選択肢④"))
        val etC3 = editText("選択肢④", existing?.choices?.getOrNull(3) ?: "")
        dialogView.addView(etC3)
        dialogView.addView(label("選択肢⑤（任意）"))
        val etC4 = editText("選択肢⑤", existing?.choices?.getOrNull(4) ?: "")
        dialogView.addView(etC4)

        // 正解
        dialogView.addView(label("正解"))
        val spinnerCorrect = Spinner(this).apply {
            adapter = ArrayAdapter(
                this@OneQListActivity,
                android.R.layout.simple_spinner_dropdown_item,
                listOf("①", "②", "③", "④", "⑤")
            )
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        spinnerCorrect.setSelection(existing?.correctIndex ?: 0)
        dialogView.addView(spinnerCorrect)

        // 解説
        dialogView.addView(label("解説"))
        val etExplanation = editText("解説を入力", existing?.explanation ?: "", multiLine = true)
        dialogView.addView(etExplanation)

        val dialog = AlertDialog.Builder(this)
            .setTitle(if (existing == null) "➕ 問題を追加" else "✏️ 問題を編集")
            .setView(scrollDialog)
            .setPositiveButton("保存") { _, _ ->
                val qText = etQuestion.text.toString().trim()
                val c0    = etC0.text.toString().trim()
                val c1    = etC1.text.toString().trim()
                val c2    = etC2.text.toString().trim()
                val c3    = etC3.text.toString().trim()
                val c4    = etC4.text.toString().trim()
                val exp   = etExplanation.text.toString().trim()

                if (qText.isEmpty() || c0.isEmpty() || c1.isEmpty() ||
                    c2.isEmpty() || c3.isEmpty()) {
                    Toast.makeText(
                        this,
                        "問題文と選択肢①〜④を全て入力してください",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setPositiveButton
                }

                val choices    = if (c4.isEmpty()) listOf(c0, c1, c2, c3)
                                 else listOf(c0, c1, c2, c3, c4)
                val correctIdx = spinnerCorrect.selectedItemPosition
                if (correctIdx >= choices.size) {
                    Toast.makeText(
                        this,
                        "選択肢⑤が空のため、正解を⑤に設定できません",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setPositiveButton
                }

                val selectedGenre = genreOptions[spinnerGenre.selectedItemPosition]
                    .let { if (it == "（未設定）") "" else it }

                OneQStorage.save(this, OneQEntry(
                    id           = existing?.id ?: OneQStorage.generateId(this),
                    question     = qText,
                    choices      = choices,
                    correctIndex = correctIdx,
                    explanation  = exp,
                    genre        = selectedGenre
                ))
                refreshGenreFilter()
                refreshList()
            }
            .setNegativeButton("キャンセル", null)
            .create()

        dialog.setOnShowListener { etQuestion.requestFocus() }
        dialog.show()
    }
}
