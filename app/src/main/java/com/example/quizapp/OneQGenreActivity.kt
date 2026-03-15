package com.example.quizapp

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class OneQGenreActivity : AppCompatActivity() {

    private lateinit var genreListContainer: LinearLayout

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
            text     = "🏷️ ジャンル管理"
            textSize = 24f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(Color.parseColor("#333333"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 8
            layoutParams = lp
        })

        root.addView(TextView(this).apply {
            text     = "一問一答で使用するジャンルを管理します"
            textSize = 13f
            setTextColor(Color.parseColor("#888888"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 24
            layoutParams = lp
        })

        // 新規ジャンル追加フォーム
        val inputRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 20
            layoutParams = lp
        }
        val etNewGenre = EditText(this).apply {
            hint      = "新しいジャンル名"
            textSize  = 14f
            inputType = InputType.TYPE_CLASS_TEXT
            layoutParams = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
            )
            setPadding(16, 12, 16, 12)
        }
        inputRow.addView(etNewGenre)
        inputRow.addView(Button(this).apply {
            text = "追加"
            textSize = 14f
            setTextColor(Color.WHITE)
            backgroundTintList = android.content.res.ColorStateList.valueOf(
                Color.parseColor("#4CAF50")
            )
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.marginStart = 8
            layoutParams = lp
            setPadding(24, 12, 24, 12)
            setOnClickListener {
                val name = etNewGenre.text.toString().trim()
                if (name.isEmpty()) {
                    Toast.makeText(
                        this@OneQGenreActivity, "ジャンル名を入力してください", Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                if (OneQGenreStorage.loadGenres(this@OneQGenreActivity).contains(name)) {
                    Toast.makeText(
                        this@OneQGenreActivity, "同じ名前のジャンルが既に存在します", Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                OneQGenreStorage.addGenre(this@OneQGenreActivity, name)
                etNewGenre.setText("")
                refreshGenreList()
            }
        })
        root.addView(inputRow)

        // ジャンル一覧コンテナ
        genreListContainer = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        root.addView(genreListContainer)

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
            lp.topMargin = 24
            layoutParams = lp
            setPadding(16, 28, 16, 28)
            setOnClickListener { finish() }
        })

        refreshGenreList()
    }

    override fun onResume() {
        super.onResume()
        refreshGenreList()
    }

    private fun refreshGenreList() {
        genreListContainer.removeAllViews()
        val genres = OneQGenreStorage.loadGenres(this)

        if (genres.isEmpty()) {
            genreListContainer.addView(TextView(this).apply {
                text     = "ジャンルがまだ登録されていません。\n上のフォームからジャンルを追加してください。"
                textSize = 13f
                setTextColor(Color.parseColor("#888888"))
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                lp.topMargin = 8
                layoutParams = lp
            })
            return
        }

        genreListContainer.addView(TextView(this).apply {
            text     = "登録済みジャンル（${genres.size}件）"
            textSize = 13f
            setTextColor(Color.parseColor("#555555"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 8
            layoutParams = lp
        })

        genres.forEach { genre ->
            val row = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                gravity     = Gravity.CENTER_VERTICAL
                setBackgroundColor(Color.WHITE)
                setPadding(16, 12, 16, 12)
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                lp.bottomMargin = 6
                layoutParams = lp
            }

            row.addView(TextView(this).apply {
                text     = "🏷️ $genre"
                textSize = 15f
                setTextColor(Color.parseColor("#333333"))
                layoutParams = LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f
                )
            })

            // 編集ボタン
            row.addView(Button(this).apply {
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
                setOnClickListener { showRenameDialog(genre) }
            })

            // 削除ボタン
            row.addView(Button(this).apply {
                text = "削除"
                textSize = 11f
                setTextColor(Color.WHITE)
                backgroundTintList = android.content.res.ColorStateList.valueOf(
                    Color.parseColor("#F44336")
                )
                setPadding(16, 8, 16, 8)
                setOnClickListener { showDeleteDialog(genre) }
            })

            genreListContainer.addView(row)
        }
    }

    private fun showRenameDialog(currentName: String) {
        val et = EditText(this).apply {
            setText(currentName)
            inputType = InputType.TYPE_CLASS_TEXT
            selectAll()
            setPadding(48, 24, 48, 24)
        }
        AlertDialog.Builder(this)
            .setTitle("ジャンル名を変更")
            .setView(et)
            .setPositiveButton("保存") { _, _ ->
                val newName = et.text.toString().trim()
                if (newName.isEmpty()) {
                    Toast.makeText(this, "名前を入力してください", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                if (newName == currentName) return@setPositiveButton
                if (OneQGenreStorage.loadGenres(this).contains(newName)) {
                    Toast.makeText(this, "同じ名前のジャンルが既に存在します", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                OneQGenreStorage.renameGenre(this, currentName, newName)
                // 旧ジャンル名を持つ問題も一括更新する
                OneQStorage.updateGenreForAll(this, currentName, newName)
                refreshGenreList()
            }
            .setNegativeButton("キャンセル", null)
            .show()
    }

    private fun showDeleteDialog(genre: String) {
        val count = OneQStorage.loadAll(this).count { it.genre == genre }
        val msg = if (count > 0)
            "「$genre」を削除しますか？\nこのジャンルが設定された問題が ${count} 件あります。\n削除後、それらの問題のジャンルは未設定になります。"
        else
            "「$genre」を削除しますか？"
        AlertDialog.Builder(this)
            .setTitle("ジャンルを削除")
            .setMessage(msg)
            .setPositiveButton("削除") { _, _ ->
                OneQStorage.updateGenreForAll(this, genre, "")
                OneQGenreStorage.deleteGenre(this, genre)
                refreshGenreList()
            }
            .setNegativeButton("キャンセル", null)
            .show()
    }
}
