package com.example.quizapp

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.data.QuizData
import com.example.quizapp.model.ExamType
import com.example.quizapp.model.Question

class QuestionEditActivity : AppCompatActivity() {

    private lateinit var listContainer: LinearLayout
    private var currentExamType: ExamType = ExamType.ENGINEERING_A
    private var currentYear: Int? = null

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

        root.addView(TextView(this).apply {
            text     = "📝 問題管理"
            textSize = 24f
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            setTextColor(Color.parseColor("#333333"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 16
            layoutParams = lp
        })

        root.addView(Button(this).apply {
            text = "➕ 問題を追加する"
            textSize = 15f
            setTextColor(Color.WHITE)
            backgroundTintList = android.content.res.ColorStateList.valueOf(
                Color.parseColor("#4CAF50")
            )
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 16
            layoutParams = lp
            setPadding(16, 36, 16, 36)
            setOnClickListener { showEditDialog(null) }
        })

        root.addView(makeExamTypeRow())
        root.addView(makeYearRow())

        listContainer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        root.addView(listContainer)

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

    // ===== 試験種別フィルター =====
    private lateinit var examTypeRow: LinearLayout

    private fun makeExamTypeRow(): HorizontalScrollView {
        val hsv = HorizontalScrollView(this).apply {
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 8
            layoutParams = lp
        }
        examTypeRow = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        hsv.addView(examTypeRow)
        rebuildExamTypeRow()
        return hsv
    }

    private fun rebuildExamTypeRow() {
        examTypeRow.removeAllViews()
        val colorMap = mapOf(
            ExamType.ENGINEERING_A to "#1565C0",
            ExamType.ENGINEERING_B to "#1976D2",
            ExamType.LAW_A         to "#6A1B9A",
            ExamType.LAW_B         to "#7B1FA2"
        )
        ExamType.values().forEach { et ->
            examTypeRow.addView(Button(this).apply {
                text     = et.label
                textSize = 12f
                setTextColor(Color.WHITE)
                backgroundTintList = android.content.res.ColorStateList.valueOf(
                    Color.parseColor(colorMap[et] ?: "#607D8B")
                )
                alpha = if (currentExamType == et) 1.0f else 0.5f
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                lp.marginEnd = 8
                layoutParams = lp
                setPadding(20, 20, 20, 20)
                setOnClickListener {
                    currentExamType = et
                    currentYear     = null
                    rebuildExamTypeRow()
                    rebuildYearRow()
                    refreshList()
                }
            })
        }
    }

    // ===== 年度フィルター =====
    private lateinit var yearRow: LinearLayout

    private fun makeYearRow(): HorizontalScrollView {
        val hsv = HorizontalScrollView(this).apply {
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 16
            layoutParams = lp
        }
        yearRow = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        hsv.addView(yearRow)
        rebuildYearRow()
        return hsv
    }

    private fun rebuildYearRow() {
        yearRow.removeAllViews()

        yearRow.addView(Button(this).apply {
            text     = "全年度"
            textSize = 12f
            setTextColor(Color.WHITE)
            backgroundTintList = android.content.res.ColorStateList.valueOf(
                Color.parseColor("#607D8B")
            )
            alpha = if (currentYear == null) 1.0f else 0.5f
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.marginEnd = 8
            layoutParams = lp
            setPadding(20, 20, 20, 20)
            setOnClickListener {
                currentYear = null
                rebuildYearRow()
                refreshList()
            }
        })

        val years = QuizData.getYearsByExamType(this, currentExamType)
        years.forEach { year ->
            yearRow.addView(Button(this).apply {
                text     = "${year}年"
                textSize = 12f
                setTextColor(Color.WHITE)
                backgroundTintList = android.content.res.ColorStateList.valueOf(
                    Color.parseColor("#FF9800")
                )
                alpha = if (currentYear == year) 1.0f else 0.5f
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                lp.marginEnd = 8
                layoutParams = lp
                setPadding(20, 20, 20, 20)
                setOnClickListener {
                    currentYear = year
                    rebuildYearRow()
                    refreshList()
                }
            })
        }
    }

    // ===== リスト更新 =====
    private fun refreshList() {
        listContainer.removeAllViews()

        val list = if (currentYear != null) {
            QuizData.getQuestionsByExamTypeAndYear(this, currentExamType, currentYear!!)
        } else {
            QuizData.getQuestionsByExamType(this, currentExamType)
        }

        val builtinIds = QuizData.questions.map { it.id }.toSet()

        if (list.isEmpty()) {
            listContainer.addView(TextView(this).apply {
                text     = "問題がありません"
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

        listContainer.addView(TextView(this).apply {
            text     = "${list.size} 問"
            textSize = 13f
            setTextColor(Color.parseColor("#888888"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 8
            layoutParams = lp
        })

        // 年度ごとにグループ化して問番号を割り当て
        // 全年度表示の場合：年度ごとに問1から採番
        // 特定年度表示の場合：その年度内で問1から採番
        val grouped = list.groupBy { it.year }
        val yearsSorted = grouped.keys.sortedDescending()

        yearsSorted.forEach { year ->
            val questionsInYear = grouped[year] ?: return@forEach
            questionsInYear.forEachIndexed { indexInYear, question ->
                val isUserAdded    = question.id !in builtinIds
                val numberInYear   = indexInYear + 1   // ← 年度内の問番号（1始まり）
                addQuestionRow(question, isUserAdded, numberInYear)
            }
        }
    }

    // ===== 問題カード =====
    // numberInYear：その年度・試験種別内での問番号（1始まり）
    private fun addQuestionRow(question: Question, isUserAdded: Boolean, numberInYear: Int) {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(
                if (isUserAdded) Color.parseColor("#E8F5E9")
                else Color.WHITE
            )
            setPadding(20, 16, 20, 16)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 8
            layoutParams = lp
        }

        val headerRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity     = Gravity.CENTER_VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }

        // ===== 問番号を「問1」「問2」形式で表示 =====
        headerRow.addView(TextView(this).apply {
            text = "問$numberInYear　${question.year}年　" +
                    if (isUserAdded) "✏️ 追加" else "📘 内蔵"
            textSize = 11f
            setTextColor(
                if (isUserAdded) Color.parseColor("#2E7D32")
                else Color.parseColor("#888888")
            )
            layoutParams = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
            )
        })

        if (isUserAdded) {
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
                lp.marginEnd = 8
                layoutParams = lp
                setPadding(16, 8, 16, 8)
                setOnClickListener { showEditDialog(question) }
            })
            headerRow.addView(Button(this).apply {
                text = "削除"
                textSize = 11f
                setTextColor(Color.WHITE)
                backgroundTintList = android.content.res.ColorStateList.valueOf(
                    Color.parseColor("#F44336")
                )
                setPadding(16, 8, 16, 8)
                setOnClickListener {
                    AlertDialog.Builder(this@QuestionEditActivity)
                        .setTitle("削除確認")
                        .setMessage("この問題を削除しますか？")
                        .setPositiveButton("削除") { _, _ ->
                            QuestionStorage.deleteQuestion(this@QuestionEditActivity, question.id)
                            rebuildYearRow()
                            refreshList()
                        }
                        .setNegativeButton("キャンセル", null)
                        .show()
                }
            })
        }
        card.addView(headerRow)

        card.addView(TextView(this).apply {
            text     = question.questionText
            textSize = 14f
            setTextColor(Color.parseColor("#333333"))
            typeface = android.graphics.Typeface.DEFAULT_BOLD
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.topMargin    = 6
            lp.bottomMargin = 6
            layoutParams = lp
        })

        question.choices.forEachIndexed { index, choice ->
            card.addView(TextView(this).apply {
                text = "${if (index == question.correctAnswerIndex) "✅" else "　"} ${index + 1}. $choice"
                textSize = 13f
                setTextColor(
                    if (index == question.correctAnswerIndex) Color.parseColor("#2E7D32")
                    else Color.parseColor("#555555")
                )
            })
        }

        listContainer.addView(card)
    }

    // ===== 問題追加・編集ダイアログ =====
    private fun showEditDialog(existing: Question?) {
        val scrollDialog = ScrollView(this)
        val dialogView   = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(48, 32, 48, 16)
        }
        scrollDialog.addView(dialogView)

        fun label(text: String) = TextView(this).apply {
            this.text = text
            textSize  = 13f
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
                this.hint  = hint
                textSize   = 14f
                inputType  = if (multiLine)
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
                else
                    InputType.TYPE_CLASS_TEXT
                if (multiLine) { minLines = 2; maxLines = 5 }
                gravity    = if (multiLine) Gravity.TOP or Gravity.START else Gravity.CENTER_VERTICAL
                isFocusable = true
                isFocusableInTouchMode = true
                setText(value)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
            }

        dialogView.addView(label("試験種別"))
        val examTypes   = ExamType.values()
        val spinnerExam = Spinner(this).apply {
            adapter = ArrayAdapter(this@QuestionEditActivity,
                android.R.layout.simple_spinner_dropdown_item,
                examTypes.map { it.label })
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        spinnerExam.setSelection(
            examTypes.indexOfFirst { it == (existing?.examType ?: currentExamType) }
                .coerceAtLeast(0)
        )
        dialogView.addView(spinnerExam)

        dialogView.addView(label("年度（例：2024）"))
        val etYear = EditText(this).apply {
            hint      = "例：2024"
            textSize  = 14f
            inputType = InputType.TYPE_CLASS_NUMBER
            setText(existing?.year?.toString() ?: "2024")
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        dialogView.addView(etYear)

        dialogView.addView(label("問題文"))
        val etQuestion = editText("問題文を入力", existing?.questionText ?: "", multiLine = true)
        dialogView.addView(etQuestion)

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

        dialogView.addView(label("正解"))
        val spinnerCorrect = Spinner(this).apply {
            adapter = ArrayAdapter(this@QuestionEditActivity,
                android.R.layout.simple_spinner_dropdown_item,
                listOf("①", "②", "③", "④"))
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        spinnerCorrect.setSelection(existing?.correctAnswerIndex ?: 0)
        dialogView.addView(spinnerCorrect)

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
                val exp   = etExplanation.text.toString().trim()
                val yr    = etYear.text.toString().trim().toIntOrNull() ?: 2024

                if (qText.isEmpty() || c0.isEmpty() || c1.isEmpty() ||
                    c2.isEmpty() || c3.isEmpty()) {
                    Toast.makeText(this, "問題文と選択肢を全て入力してください",
                        Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                QuestionStorage.saveQuestion(this, Question(
                    id                 = existing?.id ?: QuestionStorage.generateId(this),
                    questionText       = qText,
                    choices            = listOf(c0, c1, c2, c3),
                    correctAnswerIndex = spinnerCorrect.selectedItemPosition,
                    explanation        = exp,
                    examType           = examTypes[spinnerExam.selectedItemPosition],
                    year               = yr
                ))
                rebuildYearRow()
                refreshList()
            }
            .setNegativeButton("キャンセル", null)
            .create()

        dialog.setOnShowListener { etQuestion.requestFocus() }
        dialog.show()
    }
}