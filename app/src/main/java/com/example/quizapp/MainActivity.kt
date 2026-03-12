package com.example.quizapp

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.UnderlineSpan
import android.view.View
import android.view.ViewTreeObserver
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.quizapp.model.CheckLevel
import com.example.quizapp.model.ExamType
import com.example.quizapp.model.Question
import com.example.quizapp.model.WordEntry
import com.example.quizapp.viewmodel.QuizViewModel
import com.example.quizapp.viewmodel.QuizViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: QuizViewModel

    private lateinit var scrollQuestion: ScrollView
    private lateinit var tvQuestion: TextView
    private lateinit var tvQuestionNumber: TextView
    private lateinit var tvAnswerResult: TextView
    private lateinit var tvExplanation: TextView
    private lateinit var layoutExplanation: LinearLayout
    private lateinit var layoutCheckLevel: LinearLayout
    private lateinit var ivQuestionImage: ImageView
    private lateinit var btnQuestionPdf: Button
    private lateinit var btnChoice0: Button
    private lateinit var btnChoice1: Button
    private lateinit var btnChoice2: Button
    private lateinit var btnChoice3: Button
    private lateinit var btnChoice4: Button
    private lateinit var btnSubmit: Button
    private lateinit var btnNext: Button
    private lateinit var btnCheckPerfect: Button
    private lateinit var btnCheckGood: Button
    private lateinit var btnCheckPoor: Button
    private lateinit var btnCheckForgot: Button
    private lateinit var btnSave: Button
    private lateinit var btnHome: Button

    private var selectedIndex: Int = -1
    private var currentQuestionId: Int = -1

    private var examTypeName: String = ""
    private var year: Int = -1
    private var questionIds: ArrayList<Int>? = null
    private var startIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        examTypeName = intent.getStringExtra("EXAM_TYPE") ?: ""
        year         = intent.getIntExtra("YEAR", -1)
        questionIds  = intent.getIntegerArrayListExtra("QUESTION_IDS")
        startIndex   = intent.getIntExtra("START_INDEX", 0)

        val factory = QuizViewModelFactory(this, questionIds, startIndex)
        viewModel = ViewModelProvider(this, factory)[QuizViewModel::class.java]

        scrollQuestion    = findViewById(R.id.scrollQuestion)
        tvQuestion        = findViewById(R.id.tvQuestion)
        tvQuestionNumber  = findViewById(R.id.tvQuestionNumber)
        tvAnswerResult    = findViewById(R.id.tvAnswerResult)
        tvExplanation     = findViewById(R.id.tvExplanation)
        layoutExplanation = findViewById(R.id.layoutExplanation)
        layoutCheckLevel  = findViewById(R.id.layoutCheckLevel)
        ivQuestionImage   = findViewById(R.id.ivQuestionImage)
        btnQuestionPdf    = findViewById(R.id.btnQuestionPdf)
        btnChoice0        = findViewById(R.id.btnChoice0)
        btnChoice1        = findViewById(R.id.btnChoice1)
        btnChoice2        = findViewById(R.id.btnChoice2)
        btnChoice3        = findViewById(R.id.btnChoice3)
        btnChoice4        = findViewById(R.id.btnChoice4)
        btnSubmit         = findViewById(R.id.btnSubmit)
        btnNext           = findViewById(R.id.btnNext)
        btnCheckPerfect   = findViewById(R.id.btnCheckPerfect)
        btnCheckGood      = findViewById(R.id.btnCheckGood)
        btnCheckPoor      = findViewById(R.id.btnCheckPoor)
        btnCheckForgot    = findViewById(R.id.btnCheckForgot)
        btnSave           = findViewById(R.id.btnSave)
        btnHome           = findViewById(R.id.btnHome)

        // 問題文スクロールエリアの高さを画面の約40%に制限
        scrollQuestion.viewTreeObserver.addOnGlobalLayoutListener(
            object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    scrollQuestion.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    val maxHeight = (resources.displayMetrics.heightPixels * 0.40).toInt()
                    if (scrollQuestion.measuredHeight > maxHeight) {
                        val lp = scrollQuestion.layoutParams
                        lp.height = maxHeight
                        scrollQuestion.layoutParams = lp
                    }
                }
            }
        )

        val choiceButtons = listOf(btnChoice0, btnChoice1, btnChoice2, btnChoice3, btnChoice4)

        choiceButtons.forEachIndexed { index, button ->
            button.setOnClickListener {
                choiceButtons.forEach {
                    it.setBackgroundColor(Color.parseColor("#607D8B"))
                    it.setTextColor(Color.WHITE)
                }
                button.setBackgroundColor(Color.parseColor("#1565C0"))
                button.setTextColor(Color.WHITE)
                selectedIndex = index
                btnSubmit.isEnabled = true
            }
        }

        btnSubmit.setOnClickListener {
            if (selectedIndex == -1) return@setOnClickListener
            viewModel.answer(selectedIndex)
        }

        btnNext.setOnClickListener {
            selectedIndex = -1
            viewModel.nextQuestion()
        }

        btnCheckPerfect.setOnClickListener { saveCheckLevel(CheckLevel.PERFECT) }
        btnCheckGood.setOnClickListener    { saveCheckLevel(CheckLevel.GOOD) }
        btnCheckPoor.setOnClickListener    { saveCheckLevel(CheckLevel.POOR) }
        btnCheckForgot.setOnClickListener  { saveCheckLevel(CheckLevel.FORGOT) }

        btnSave.setOnClickListener {
            saveResume()
            android.widget.Toast.makeText(
                this, "💾 途中保存しました！", android.widget.Toast.LENGTH_SHORT
            ).show()
            goHome()
        }

        btnHome.setOnClickListener {
            android.app.AlertDialog.Builder(this)
                .setTitle("ホームに戻る")
                .setMessage("途中保存してからホームに戻りますか？\n（保存しない場合は進捗が失われます）")
                .setPositiveButton("保存して戻る") { _, _ ->
                    saveResume()
                    android.widget.Toast.makeText(
                        this, "💾 途中保存しました！", android.widget.Toast.LENGTH_SHORT
                    ).show()
                    goHome()
                }
                .setNeutralButton("保存せず戻る") { _, _ ->
                    QuizStorage.clearResume(this)
                    goHome()
                }
                .setNegativeButton("キャンセル", null)
                .show()
        }

        viewModel.currentQuestion.observe(this) { question ->
            currentQuestionId         = question.id
            tvQuestion.text           = question.questionText
            tvQuestion.movementMethod = null

            // 画像/PDF添付の表示
            val uri = question.imageUriString
            ivQuestionImage.visibility  = View.GONE
            btnQuestionPdf.visibility   = View.GONE
            if (uri.isNotEmpty()) {
                val extension = uri.substringAfterLast('.', "").lowercase()
                when {
                    extension in listOf("jpg", "jpeg", "png", "webp") -> {
                        try {
                            ivQuestionImage.setImageURI(Uri.parse(uri))
                            ivQuestionImage.visibility = View.VISIBLE
                        } catch (_: Exception) {}
                    }
                    extension == "pdf" -> {
                        btnQuestionPdf.visibility = View.VISIBLE
                        btnQuestionPdf.setOnClickListener {
                            try {
                                val intent = Intent(Intent.ACTION_VIEW)
                                intent.setDataAndType(Uri.parse(uri), "application/pdf")
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                                startActivity(intent)
                            } catch (_: Exception) {
                                android.widget.Toast.makeText(
                                    this, "PDFを開くアプリが見つかりません", android.widget.Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    else -> {
                        // content:// URI など拡張子不明の場合は画像として試みる
                        try {
                            ivQuestionImage.setImageURI(Uri.parse(uri))
                            ivQuestionImage.visibility = View.VISIBLE
                        } catch (_: Exception) {}
                    }
                }
            }

            choiceButtons.forEachIndexed { index, button ->
                if (index < question.choices.size) {
                    button.text = question.choices[index]
                    button.visibility = View.VISIBLE
                    button.setBackgroundColor(Color.parseColor("#607D8B"))
                    button.setTextColor(Color.WHITE)
                    button.isEnabled = true
                } else {
                    button.visibility = View.GONE
                }
            }
            btnSubmit.isEnabled          = false
            btnSubmit.visibility         = View.VISIBLE
            tvAnswerResult.visibility    = View.GONE
            layoutExplanation.visibility = View.GONE
            layoutCheckLevel.visibility  = View.GONE
            btnNext.visibility           = View.GONE
            resetCheckButtons()

            tvQuestionNumber.text = buildQuestionNumberText(question)
        }

        viewModel.isAnswered.observe(this) { isAnswered ->
            if (!isAnswered) return@observe
            val question  = viewModel.currentQuestion.value ?: return@observe
            val selected  = viewModel.selectedIndex.value  ?: return@observe
            val isCorrect = selected == question.correctAnswerIndex

            choiceButtons.forEachIndexed { index, button ->
                button.isEnabled = false
                when (index) {
                    question.correctAnswerIndex -> {
                        button.setBackgroundColor(Color.parseColor("#1B5E20"))
                        button.setTextColor(Color.WHITE)
                    }
                    selected -> if (!isCorrect) {
                        button.setBackgroundColor(Color.parseColor("#B71C1C"))
                        button.setTextColor(Color.WHITE)
                    }
                    else -> {
                        button.setBackgroundColor(Color.parseColor("#455A64"))
                        button.setTextColor(Color.parseColor("#BBBBBB"))
                    }
                }
            }
            btnSubmit.visibility = View.GONE

            if (isCorrect) {
                tvAnswerResult.text = "✅ 正解！"
                tvAnswerResult.setTextColor(Color.WHITE)
                tvAnswerResult.setBackgroundColor(Color.parseColor("#2E7D32"))
            } else {
                tvAnswerResult.text =
                    "❌ 不正解！\n正解：${question.choices[question.correctAnswerIndex]}"
                tvAnswerResult.setTextColor(Color.WHITE)
                tvAnswerResult.setBackgroundColor(Color.parseColor("#C62828"))
            }
            tvAnswerResult.visibility    = View.VISIBLE
            tvExplanation.text           = question.explanation
            layoutExplanation.visibility = View.VISIBLE
            layoutCheckLevel.visibility  = View.VISIBLE
            btnNext.visibility           = View.VISIBLE

            val words = WordStorage.loadWords(this)
            applyWordLinks(tvQuestion,    question.questionText, words)
            applyWordLinks(tvExplanation, question.explanation,  words)
        }

        viewModel.isFinished.observe(this) { isFinished ->
            if (!isFinished) return@observe

            val score   = viewModel.getScore()
            val results = viewModel.getResults()
            val usedIds = viewModel.getQuestionIds()

            QuizStorage.incrementPlayCount(this)
            QuizStorage.updateHighScore(this, score)
            QuizStorage.saveResults(this, results)
            QuizStorage.addHistory(
                context     = this,
                score       = score,
                total       = viewModel.totalQuestions,
                examType    = examTypeName,
                year        = year,
                questionIds = usedIds
            )
            QuizStorage.clearResume(this)

            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("SCORE", score)
            intent.putExtra("TOTAL", viewModel.totalQuestions)
            startActivity(intent)
            finish()
        }
    }

    private fun buildQuestionNumberText(question: Question): String {
        val allQs = viewModel.questions
        val groupQs = allQs.filter { q ->
            q.examType == question.examType && q.periodLabel == question.periodLabel
        }
        val posInGroup = groupQs.indexOfFirst { q -> q.id == question.id }

        return if (posInGroup >= 0) {
            "${question.examType.label}　${question.periodLabel}　問${posInGroup + 1} / ${groupQs.size}"
        } else {
            val posInAll = allQs.indexOfFirst { q -> q.id == question.id }
            val numInAll = if (posInAll >= 0) posInAll + 1 else viewModel.currentIndex + 1
            "${question.examType.label}　${question.periodLabel}　問$numInAll / ${allQs.size}"
        }
    }

    private fun applyWordLinks(textView: TextView, text: String, words: List<WordEntry>) {
        if (words.isEmpty()) {
            textView.text = text
            textView.movementMethod = null
            return
        }

        val spannable   = SpannableString(text)
        var hasLink     = false
        val sortedWords = words.sortedByDescending { it.title.length }

        sortedWords.forEach { entry ->
            val keyword    = entry.title
            var startIndex = 0
            while (startIndex < text.length) {
                val idx = text.indexOf(keyword, startIndex, ignoreCase = true)
                if (idx == -1) break
                val end = idx + keyword.length

                val clickSpan = object : ClickableSpan() {
                    override fun onClick(widget: View) { showWordDetail(entry) }
                    override fun updateDrawState(ds: android.text.TextPaint) {
                        ds.color           = Color.parseColor("#1565C0")
                        ds.isUnderlineText = true
                        ds.isFakeBoldText  = true
                    }
                }
                spannable.setSpan(clickSpan,      idx, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannable.setSpan(UnderlineSpan(), idx, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                spannable.setSpan(
                    ForegroundColorSpan(Color.parseColor("#1565C0")),
                    idx, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                hasLink    = true
                startIndex = end
            }
        }

        textView.text = spannable
        if (hasLink) {
            textView.movementMethod = LinkMovementMethod.getInstance()
            textView.highlightColor = Color.parseColor("#BBDEFB")
        } else {
            textView.movementMethod = null
        }
    }

    private fun showWordDetail(entry: WordEntry) {
        val examLabel = when (entry.examType) {
            ExamType.ENGINEERING_A.name -> "無線工学A"
            ExamType.ENGINEERING_B.name -> "無線工学B"
            ExamType.LAW_A.name         -> "法規A"
            ExamType.LAW_B.name         -> "法規B"
            else                        -> "全試験共通"
        }
        android.app.AlertDialog.Builder(this)
            .setTitle("📖 ${entry.title}")
            .setMessage("【種別】$examLabel\n\n${entry.content}")
            .setPositiveButton("閉じる", null)
            .show()
    }

    private fun saveResume() {
        QuizStorage.saveResume(
            this,
            QuizStorage.ResumeData(
                examType     = examTypeName,
                year         = year,
                questionIds  = viewModel.getQuestionIds(),
                currentIndex = viewModel.currentIndex,
                answeredIds  = viewModel.getAnsweredIds()
            )
        )
    }

    private fun goHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    private fun saveCheckLevel(level: CheckLevel) {
        if (currentQuestionId == -1) return
        QuizStorage.updateCheckLevel(this, currentQuestionId, level)
        resetCheckButtons()
        when (level) {
            CheckLevel.PERFECT -> btnCheckPerfect.setBackgroundColor(Color.parseColor("#1B5E20"))
            CheckLevel.GOOD    -> btnCheckGood.setBackgroundColor(Color.parseColor("#0D47A1"))
            CheckLevel.POOR    -> btnCheckPoor.setBackgroundColor(Color.parseColor("#E65100"))
            CheckLevel.FORGOT  -> btnCheckForgot.setBackgroundColor(Color.parseColor("#B71C1C"))
            else -> {}
        }
    }

    private fun resetCheckButtons() {
        btnCheckPerfect.setBackgroundColor(Color.parseColor("#4CAF50"))
        btnCheckGood.setBackgroundColor(Color.parseColor("#2196F3"))
        btnCheckPoor.setBackgroundColor(Color.parseColor("#FF9800"))
        btnCheckForgot.setBackgroundColor(Color.parseColor("#F44336"))
    }
}