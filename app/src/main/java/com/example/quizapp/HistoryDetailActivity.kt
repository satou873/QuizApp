package com.example.quizapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.data.QuizData
import com.example.quizapp.model.CheckLevel
import com.example.quizapp.model.ExamType
import com.example.quizapp.model.Question
import com.example.quizapp.model.QuizResult
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_detail)

        val score       = intent.getIntExtra("SCORE", 0)
        val total       = intent.getIntExtra("TOTAL", 0)
        val timestamp   = intent.getLongExtra("TIMESTAMP", 0L)
        val examTypeName = intent.getStringExtra("EXAM_TYPE") ?: ""
        val year        = intent.getIntExtra("YEAR", -1)
        val questionIds = intent.getIntegerArrayListExtra("QUESTION_IDS") ?: arrayListOf()

        val tvTitle     = findViewById<TextView>(R.id.tvDetailTitle)
        val tvSummary   = findViewById<TextView>(R.id.tvDetailSummary)
        val listView    = findViewById<ListView>(R.id.lvDetailQuestions)
        val tvEmpty     = findViewById<TextView>(R.id.tvEmpty)
        val btnBack     = findViewById<Button>(R.id.btnBack)

        // タイトル
        val date = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.JAPAN)
            .format(Date(timestamp))
        val examLabel = if (examTypeName.isNotEmpty()) {
            try {
                val et = ExamType.valueOf(examTypeName)
                val termStr = if (year > 0) {
                    val qs = QuizData.getQuestionsByExamTypeAndYear(this, et, year)
                    " " + (qs.firstOrNull()?.periodLabel ?: "${year}年")
                } else " 全年度"
                et.label + termStr
            } catch (e: Exception) { "" }
        } else ""
        tvTitle.text   = if (examLabel.isNotEmpty()) "$examLabel｜$date" else date
        val pct = if (total > 0) (score * 100 / total) else 0
        tvSummary.text = "スコア：$score / $total 問正解（$pct%）"

        // 問題リスト（内蔵＋ユーザー追加を両方検索）
        val allQuestions = QuizData.questions + QuestionStorage.loadQuestions(this)
        val questions = questionIds.mapNotNull { id ->
            allQuestions.find { it.id == id }
        }
        val results = QuizStorage.loadResults(this)

        if (questions.isEmpty()) {
            tvEmpty.visibility  = View.VISIBLE
            listView.visibility = View.GONE
        } else {
            listView.adapter = QuestionListAdapter(questions, results)
            listView.setOnItemClickListener { _, _, position, _ ->
                val q = questions[position]
                showQuestionDialog(q)
            }
        }

        btnBack.setOnClickListener { finish() }
    }

    private fun showQuestionDialog(question: Question) {
        val dialog = android.app.AlertDialog.Builder(this)
        dialog.setTitle("Q${question.id}　${question.questionText}")

        val msg = buildString {
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

        dialog.setMessage(msg)
        dialog.setPositiveButton("閉じる", null)
        dialog.show()
    }

    inner class QuestionListAdapter(
        private val questions: List<Question>,
        private val results:   List<QuizResult>
    ) : BaseAdapter() {

        override fun getCount() = questions.size
        override fun getItem(position: Int) = questions[position]
        override fun getItemId(position: Int) = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(this@HistoryDetailActivity)
                .inflate(R.layout.item_history_question, parent, false)

            val q      = questions[position]
            val result = results.find { it.questionId == q.id }
            val level  = result?.checkLevel ?: CheckLevel.UNCHECKED

            view.findViewById<TextView>(R.id.tvQNumber).text     = "Q${q.id}"
            view.findViewById<TextView>(R.id.tvQText).text       = q.questionText
            view.findViewById<TextView>(R.id.tvQAnswer).text     =
                "正解：${q.choices[q.correctAnswerIndex]}"
            view.findViewById<TextView>(R.id.tvCheckLevel).apply {
                text      = level.emoji
                textSize  = 18f
                setTextColor(android.graphics.Color.parseColor(level.color))
            }
            return view
        }
    }
}