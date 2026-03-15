package com.example.quizapp

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.model.ExamType
import com.example.quizapp.model.Question

class ProfileActivity : AppCompatActivity() {

    // 一問一答管理セクション
    private lateinit var tvOneQToggleIcon: TextView
    private lateinit var containerOneQSection: LinearLayout
    private lateinit var listOneQContainer: LinearLayout
    private var isOneQSectionExpanded = false

    // ファイルピッカー（画像/PDF添付）
    private var onFilePicked: ((Uri) -> Unit)? = null
    private lateinit var pickFileLauncher: ActivityResultLauncher<Array<String>>

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
                } catch (_: Exception) {
                    // 永続パーミッションが取得できない場合（非対応プロバイダ等）は無視
                }
                onFilePicked?.invoke(uri)
            }
        }

        setContentView(R.layout.activity_profile)

        val btnReview       = findViewById<Button>(R.id.btnReview)
        val btnUnanswered   = findViewById<Button>(R.id.btnUnanswered)
        val btnWordBook     = findViewById<Button>(R.id.btnWordBook)
        val btnQuestionEdit = findViewById<Button>(R.id.btnQuestionEdit)
        val btnCheckStatus  = findViewById<Button>(R.id.btnCheckStatus)
        val btnHistory      = findViewById<Button>(R.id.btnHistory)
        val btnAchievement  = findViewById<Button>(R.id.btnAchievement)
        val btnScoreByExam  = findViewById<Button>(R.id.btnScoreByExam)
        val btnBackHome     = findViewById<Button>(R.id.btnBackHome)

        // 一問一答管理アコーディオン
        val headerOneQSection = findViewById<LinearLayout>(R.id.headerOneQSection)
        tvOneQToggleIcon      = findViewById(R.id.tvOneQToggleIcon)
        containerOneQSection  = findViewById(R.id.containerOneQSection)
        listOneQContainer     = findViewById(R.id.listOneQContainer)
        val btnAddNewOneQ     = findViewById<Button>(R.id.btnAddNewOneQ)

        btnReview.setOnClickListener {
            startActivity(Intent(this, ExamSelectActivity::class.java)
                .putExtra("MODE", "REVIEW"))
        }
        btnUnanswered.setOnClickListener {
            startActivity(Intent(this, ExamSelectActivity::class.java)
                .putExtra("MODE", "UNANSWERED"))
        }
        btnWordBook.setOnClickListener {
            startActivity(Intent(this, WordBookActivity::class.java))
        }
        btnQuestionEdit.setOnClickListener {
            startActivity(Intent(this, QuestionEditActivity::class.java))
        }
        btnCheckStatus.setOnClickListener {
            startActivity(Intent(this, CheckStatusActivity::class.java))
        }
        btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }
        btnAchievement.setOnClickListener {
            startActivity(Intent(this, AchievementActivity::class.java))
        }
        btnScoreByExam.setOnClickListener {
            startActivity(Intent(this, ScoreByExamActivity::class.java))
        }
        btnBackHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

        // 一問一答管理 アコーディオン トグル
        headerOneQSection.setOnClickListener {
            isOneQSectionExpanded = !isOneQSectionExpanded
            containerOneQSection.visibility =
                if (isOneQSectionExpanded) View.VISIBLE else View.GONE
            tvOneQToggleIcon.text = if (isOneQSectionExpanded) "▼" else "▶"
            if (isOneQSectionExpanded) refreshOneQList()
        }

        // 新規追加ボタン
        btnAddNewOneQ.setOnClickListener {
            showOneQDialog(null)
        }
    }

    override fun onResume() {
        super.onResume()
        if (isOneQSectionExpanded) refreshOneQList()
    }

    // ===== 一問一答リスト更新 =====
    private fun refreshOneQList() {
        listOneQContainer.removeAllViews()
        val questions = QuestionStorage.loadQuestions(this)

        if (questions.isEmpty()) {
            listOneQContainer.addView(TextView(this).apply {
                text = "追加された問題がありません。\n「新規追加」ボタンで問題を追加してください。"
                textSize = 13f
                setTextColor(Color.parseColor("#888888"))
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                lp.topMargin = 8
                lp.bottomMargin = 8
                layoutParams = lp
            })
            return
        }

        listOneQContainer.addView(TextView(this).apply {
            text = "${questions.size} 問"
            textSize = 12f
            setTextColor(Color.parseColor("#666666"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 4
            layoutParams = lp
        })

        questions.forEach { question ->
            addOneQRow(question)
        }
    }

    // ===== 一問一答問題カード =====
    private fun addOneQRow(question: Question) {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.parseColor("#E8F5E9"))
            setPadding(16, 12, 16, 12)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 6
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

        headerRow.addView(TextView(this).apply {
            text = "✏️ 追加　${question.examType.label}　${question.periodLabel}"
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
            setOnClickListener { showOneQDialog(question) }
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
                AlertDialog.Builder(this@ProfileActivity)
                    .setTitle("削除確認")
                    .setMessage("この問題を削除しますか？")
                    .setPositiveButton("削除") { _, _ ->
                        QuestionStorage.deleteQuestion(this@ProfileActivity, question.id)
                        refreshOneQList()
                    }
                    .setNegativeButton("キャンセル", null)
                    .show()
            }
        })

        card.addView(headerRow)

        card.addView(TextView(this).apply {
            text = question.questionText
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

        question.choices.forEachIndexed { index, choice ->
            card.addView(TextView(this).apply {
                text = "${if (index == question.correctAnswerIndex) "✅" else "　"} ${index + 1}. $choice"
                textSize = 12f
                setTextColor(
                    if (index == question.correctAnswerIndex) Color.parseColor("#2E7D32")
                    else Color.parseColor("#555555")
                )
            })
        }

        listOneQContainer.addView(card)
    }

    // ===== 問題追加・編集ダイアログ =====
    private fun showOneQDialog(existing: Question?) {
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

        // 試験種別選択
        dialogView.addView(label("試験種別"))
        val examTypes = ExamType.values()
        val spinnerExam = Spinner(this).apply {
            adapter = ArrayAdapter(
                this@ProfileActivity,
                android.R.layout.simple_spinner_dropdown_item,
                examTypes.map { it.label }
            )
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        spinnerExam.setSelection(
            examTypes.indexOfFirst { it == (existing?.examType ?: ExamType.ENGINEERING_A) }
                .coerceAtLeast(0)
        )
        dialogView.addView(spinnerExam)

        // 試験期選択
        dialogView.addView(label("試験期"))
        val termLabels = predefinedTerms.map { it.second }
        val spinnerTerm = Spinner(this).apply {
            adapter = ArrayAdapter(
                this@ProfileActivity,
                android.R.layout.simple_spinner_dropdown_item,
                termLabels
            )
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        val initialTermLabel = existing?.term?.takeIf { it.isNotEmpty() }
        val initialTermIdx = if (initialTermLabel != null)
            termLabels.indexOfFirst { it == initialTermLabel }.coerceAtLeast(0)
        else termLabels.size - 1  // デフォルト：最新の試験期
        spinnerTerm.setSelection(initialTermIdx)
        dialogView.addView(spinnerTerm)

        // 問題文
        dialogView.addView(label("問題文"))
        val etQuestion = editText("問題文を入力", existing?.questionText ?: "", multiLine = true)
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
                this@ProfileActivity,
                android.R.layout.simple_spinner_dropdown_item,
                listOf("①", "②", "③", "④", "⑤")
            )
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        spinnerCorrect.setSelection(existing?.correctAnswerIndex ?: 0)
        dialogView.addView(spinnerCorrect)

        // 解説
        dialogView.addView(label("解説"))
        val etExplanation = editText("解説を入力", existing?.explanation ?: "", multiLine = true)
        dialogView.addView(etExplanation)

        // 画像/PDF添付ボタン
        dialogView.addView(label("画像/PDF添付（任意）"))
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
            pickFileLauncher.launch(arrayOf("image/*", "application/pdf"))
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
                    Toast.makeText(
                        this,
                        "問題文と選択肢①〜④を全て入力してください",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setPositiveButton
                }

                val choices = if (c4.isEmpty()) listOf(c0, c1, c2, c3)
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
                refreshOneQList()
            }
            .setNegativeButton("キャンセル", null)
            .create()

        dialog.setOnShowListener { etQuestion.requestFocus() }
        dialog.show()
    }
}