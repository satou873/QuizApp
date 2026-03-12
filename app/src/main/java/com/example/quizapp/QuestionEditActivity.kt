package com.example.quizapp

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.data.QuizData
import com.example.quizapp.model.ExamType
import com.example.quizapp.model.Question

class QuestionEditActivity : AppCompatActivity() {

    private lateinit var listContainer: LinearLayout
    private lateinit var addButton: Button
    private var currentExamType: ExamType = ExamType.ENGINEERING_A
    private var currentTerm: String? = null

    private val predefinedTerms = listOf(
        Pair(2021, "令和3年2月期"),
        Pair(2021, "令和3年6月期"),
        Pair(2021, "令和3年10月期"),
        Pair(2022, "令和4年2月期"),
        Pair(2022, "令和4年6月期"),
        Pair(2022, "令和4年10月期"),
        Pair(2023, "令和5年2月期"),
        Pair(2023, "令和5年6月期"),
        Pair(2023, "令和5年10月期"),
        Pair(2024, "令和6年2月期"),
        Pair(2024, "令和6年6月期"),
        Pair(2024, "令和6年10月期"),
        Pair(2025, "令和7年2月期"),
        Pair(2025, "令和7年6月期"),
        Pair(2025, "令和7年10月期"),
        Pair(2026, "令和8年2月期")
    )

    // ファイルピッカー（画像/PDF添付）
    private var onFilePicked: ((Uri) -> Unit)? = null
    private lateinit var pickFileLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ファイルピッカーの登録（onCreateで呼ぶ必要あり）
        pickFileLauncher = registerForActivityResult(
            ActivityResultContracts.OpenDocument()
        ) { uri: Uri? ->
            if (uri != null) {
                try {
                    contentResolver.takePersistableUriPermission(
                        uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )
                } catch (_: Exception) {}
                onFilePicked?.invoke(uri)
            }
        }

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

        root.addView(makeExamTypeRow())
        root.addView(makeTermRow())

        addButton = Button(this).apply {
            text = "➕ この試験期に問題を追加"
            textSize = 15f
            setTextColor(Color.WHITE)
            backgroundTintList = android.content.res.ColorStateList.valueOf(
                Color.parseColor("#4CAF50")
            )
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 8
            layoutParams = lp
            setPadding(16, 36, 16, 36)
            visibility = android.view.View.GONE
            setOnClickListener { showEditDialog(null, currentTerm) }
        }
        root.addView(addButton)

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
                    currentTerm     = null
                    rebuildExamTypeRow()
                    rebuildTermRow()
                    refreshList()
                }
            })
        }
    }

    // ===== 試験期フィルター =====
    private lateinit var termRow: LinearLayout

    private fun makeTermRow(): HorizontalScrollView {
        val hsv = HorizontalScrollView(this).apply {
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 8
            layoutParams = lp
        }
        termRow = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        hsv.addView(termRow)
        rebuildTermRow()
        return hsv
    }

    private fun rebuildTermRow() {
        termRow.removeAllViews()

        termRow.addView(Button(this).apply {
            text     = "全期"
            textSize = 12f
            setTextColor(Color.WHITE)
            backgroundTintList = android.content.res.ColorStateList.valueOf(
                Color.parseColor("#607D8B")
            )
            alpha = if (currentTerm == null) 1.0f else 0.5f
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.marginEnd = 8
            layoutParams = lp
            setPadding(20, 20, 20, 20)
            setOnClickListener {
                currentTerm = null
                addButton.visibility = android.view.View.GONE
                rebuildTermRow()
                refreshList()
            }
        })

        predefinedTerms.forEach { (_, termLabel) ->
            termRow.addView(Button(this).apply {
                text     = termLabel
                textSize = 12f
                setTextColor(Color.WHITE)
                backgroundTintList = android.content.res.ColorStateList.valueOf(
                    Color.parseColor("#FF9800")
                )
                alpha = if (currentTerm == termLabel) 1.0f else 0.5f
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                lp.marginEnd = 8
                layoutParams = lp
                setPadding(20, 20, 20, 20)
                setOnClickListener {
                    currentTerm = termLabel
                    addButton.visibility = android.view.View.VISIBLE
                    rebuildTermRow()
                    refreshList()
                }
            })
        }
    }

    // ===== リスト更新 =====
    private fun refreshList() {
        listContainer.removeAllViews()

        val list = if (currentTerm != null) {
            QuizData.getQuestionsByTerm(this, currentExamType, currentTerm!!)
        } else {
            QuizData.getQuestionsByExamType(this, currentExamType)
        }

        // ユーザー追加問題のIDセット（内蔵問題かどうかの判定に使用）
        val userIds    = QuestionStorage.loadQuestions(this).map { it.id }.toSet()
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
        val grouped     = list.groupBy { it.year }
        val yearsSorted = grouped.keys.sortedDescending()

        yearsSorted.forEach { year ->
            val questionsInYear = grouped[year] ?: return@forEach
            questionsInYear.forEachIndexed { indexInYear, question ->
                val isUserAdded  = question.id in userIds
                val numberInYear = indexInYear + 1
                addQuestionRow(question, isUserAdded, numberInYear)
            }
        }
    }

    // ===== 問題カード =====
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

        headerRow.addView(TextView(this).apply {
            text = "問$numberInYear　${question.periodLabel}　" +
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

        // 編集ボタン（内蔵・追加問わず表示）
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

        // 削除ボタン（内蔵・追加問わず表示）
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
                        if (isUserAdded) {
                            QuestionStorage.deleteQuestion(this@QuestionEditActivity, question.id)
                        } else {
                            // 内蔵問題は削除フラグを保存
                            QuestionStorage.saveDeletedId(this@QuestionEditActivity, question.id)
                        }
                        rebuildTermRow()
                        refreshList()
                    }
                    .setNegativeButton("キャンセル", null)
                    .show()
            }
        })

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
    private fun showEditDialog(existing: Question?, preselectedTerm: String? = null) {
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

        // 試験期選択（Spinner）
        dialogView.addView(label("試験期"))
        val termLabels = predefinedTerms.map { it.second }
        val spinnerTerm = Spinner(this).apply {
            adapter = ArrayAdapter(this@QuestionEditActivity,
                android.R.layout.simple_spinner_dropdown_item,
                termLabels)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        // 初期選択：既存問題の term → 一致するものを選択、なければ preselectedTerm → なければ先頭
        val initialTermLabel = existing?.term?.takeIf { it.isNotEmpty() } ?: preselectedTerm
        val initialTermIdx = if (initialTermLabel != null)
            termLabels.indexOfFirst { it == initialTermLabel }.coerceAtLeast(0)
        else 0
        spinnerTerm.setSelection(initialTermIdx)
        dialogView.addView(spinnerTerm)

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
        dialogView.addView(label("選択肢⑤"))
        val etC4 = editText("選択肢⑤", existing?.choices?.getOrNull(4) ?: "")
        dialogView.addView(etC4)

        dialogView.addView(label("正解"))
        val spinnerCorrect = Spinner(this).apply {
            adapter = ArrayAdapter(this@QuestionEditActivity,
                android.R.layout.simple_spinner_dropdown_item,
                listOf("①", "②", "③", "④", "⑤"))
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

        // 画像/PDF添付ボタン
        dialogView.addView(label("画像/PDF添付"))
        var attachedUriString = existing?.imageUriString ?: ""
        val btnAttach = Button(this).apply {
            text = if (attachedUriString.isEmpty()) "📎 画像/PDFを添付" else "📎 添付済（タップで変更）"
            textSize = 13f
            setTextColor(Color.WHITE)
            backgroundTintList = android.content.res.ColorStateList.valueOf(
                Color.parseColor("#FF7043")
            )
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            setPadding(16, 20, 16, 20)
        }
        dialogView.addView(btnAttach)

        btnAttach.setOnClickListener {
            onFilePicked = { uri ->
                attachedUriString = uri.toString()
                btnAttach.text = "📎 添付済（タップで変更）"
            }
            pickFileLauncher.launch(arrayOf("*/*"))
        }

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
                val selectedTermPair = predefinedTerms[spinnerTerm.selectedItemPosition]
                val term  = selectedTermPair.second
                val yr    = selectedTermPair.first
                val selectedExamType = examTypes[spinnerExam.selectedItemPosition]

                if (qText.isEmpty() || c0.isEmpty() || c1.isEmpty() ||
                    c2.isEmpty() || c3.isEmpty()) {
                    Toast.makeText(this, "問題文と選択肢を全て入力してください",
                        Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                val choices = if (c4.isEmpty()) listOf(c0, c1, c2, c3)
                              else listOf(c0, c1, c2, c3, c4)
                val correctIdx = spinnerCorrect.selectedItemPosition
                if (correctIdx >= choices.size) {
                    Toast.makeText(this, "選択肢⑤が空のため、正解を⑤に設定できません",
                        Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }

                // 重複チェック（同じ term × examType × questionText の問題が既に存在するか）
                val duplicate = QuizData.getAllQuestions(this).any { q ->
                    q.id != (existing?.id ?: -1) &&
                    q.examType == selectedExamType &&
                    q.term == term &&
                    q.questionText == qText
                }

                fun doSave() {
                    QuestionStorage.saveQuestion(this, Question(
                        id                 = existing?.id ?: QuestionStorage.generateId(this),
                        questionText       = qText,
                        choices            = choices,
                        correctAnswerIndex = correctIdx,
                        explanation        = exp,
                        examType           = selectedExamType,
                        year               = yr,
                        term               = term,
                        imageUriString     = attachedUriString
                    ))
                    rebuildTermRow()
                    refreshList()
                }

                if (duplicate) {
                    AlertDialog.Builder(this)
                        .setTitle("重複確認")
                        .setMessage("同じ問題が既に存在します。上書きしますか？")
                        .setPositiveButton("上書き") { _, _ -> doSave() }
                        .setNegativeButton("キャンセル", null)
                        .show()
                } else {
                    doSave()
                }
            }
            .setNegativeButton("キャンセル", null)
            .create()

        dialog.setOnShowListener { etQuestion.requestFocus() }
        dialog.show()
    }
}