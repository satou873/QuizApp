package com.example.quizapp

import android.content.Intent
import android.graphics.Color
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
import com.example.quizapp.model.ExamType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val listView = findViewById<ListView>(R.id.lvHistory)
        val tvEmpty  = findViewById<TextView>(R.id.tvEmpty)
        val btnBack  = findViewById<Button>(R.id.btnBack)

        val history = QuizStorage.loadHistory(this)

        if (history.isEmpty()) {
            tvEmpty.visibility  = View.VISIBLE
            listView.visibility = View.GONE
        } else {
            listView.adapter = HistoryAdapter(history)
            listView.setOnItemClickListener { _, _, position, _ ->
                val entry = history[position]
                val intent = Intent(this, HistoryDetailActivity::class.java)
                intent.putExtra("SCORE",      entry.score)
                intent.putExtra("TOTAL",      entry.total)
                intent.putExtra("TIMESTAMP",  entry.timestamp)
                intent.putExtra("EXAM_TYPE",  entry.examType)
                intent.putExtra("YEAR",       entry.year)
                intent.putIntegerArrayListExtra(
                    "QUESTION_IDS", ArrayList(entry.questionIds)
                )
                startActivity(intent)
            }
        }

        btnBack.setOnClickListener { finish() }
    }

    inner class HistoryAdapter(
        private val history: List<QuizStorage.HistoryEntry>
    ) : BaseAdapter() {

        override fun getCount() = history.size
        override fun getItem(position: Int) = history[position]
        override fun getItemId(position: Int) = position.toLong()

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = convertView ?: LayoutInflater.from(this@HistoryActivity)
                .inflate(R.layout.item_history, parent, false)

            val entry = history[position]
            val date  = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.JAPAN)
                .format(Date(entry.timestamp))
            val percentage = if (entry.total > 0) (entry.score * 100 / entry.total) else 0

            // 試験種別・年度ラベル
            val examLabel = if (entry.examType.isNotEmpty()) {
                try {
                    val et = ExamType.valueOf(entry.examType)
                    val termStr = if (entry.year > 0) {
                        val qs = QuizData.getQuestionsByExamTypeAndYear(
                            this@HistoryActivity, et, entry.year
                        )
                        " " + (qs.firstOrNull()?.periodLabel ?: "${entry.year}年")
                    } else " 全年度"
                    et.label + termStr
                } catch (e: Exception) { "" }
            } else ""

            view.findViewById<TextView>(R.id.tvHistoryDate).text =
                if (examLabel.isNotEmpty()) "$date｜$examLabel" else date
            view.findViewById<TextView>(R.id.tvHistoryScore).text =
                "${entry.score} / ${entry.total} 問正解　　タップして詳細 ▶"
            view.findViewById<TextView>(R.id.tvHistoryPercent).apply {
                text = "$percentage%"
                setTextColor(Color.parseColor(
                    when {
                        percentage >= 80 -> "#4CAF50"
                        percentage >= 60 -> "#FF9800"
                        else             -> "#F44336"
                    }
                ))
            }
            return view
        }
    }
}