package com.example.quizapp

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.Gravity
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.model.ExamType
import com.example.quizapp.model.WordEntry

class WordBookActivity : AppCompatActivity() {

    private lateinit var listContainer: LinearLayout
    private lateinit var etSearch: EditText
    private lateinit var aiueoRow: LinearLayout
    private lateinit var filterRow: LinearLayout

    private var currentFilter: String  = "ALL"
    private var searchQuery: String    = ""
    private var aiueoFilter: String    = ""
    private var categoryFilter: String = ""
    private var formulaOnly: Boolean   = false

    // ===== 無線工学カテゴリ =====
    private val engineeringCategories = listOf(
        "多重通信システムの概要", "基礎理論", "変復調", "無線送受信装置",
        "多重通信システム", "中継方式", "レーダー", "空中線・給電線",
        "電波伝搬", "電源", "測定"
    )

    // ===== 法規カテゴリ =====
    private val lawCategories = listOf(
        "電波法の概要", "無線局の免許", "無線設備", "無線従事者",
        "運用", "業務書類等", "監督等"
    )

    // ===== 試験種別に対応するカテゴリリストを返す =====
    private fun getCategoriesForExamType(examTypeName: String?): List<String> {
        return when (examTypeName) {
            ExamType.ENGINEERING_A.name,
            ExamType.ENGINEERING_B.name -> listOf("なし") + engineeringCategories
            ExamType.LAW_A.name,
            ExamType.LAW_B.name         -> listOf("なし") + lawCategories
            else                        -> listOf("なし") + engineeringCategories + lawCategories
        }
    }

    // ===== 五十音行定義 =====
    data class AiueoItem(val label: String, val type: AiueoType, val chars: String = "")
    enum class AiueoType { KANA, KANJI, ALPHA, OTHER }

    private val aiueoItems = listOf(
        AiueoItem("ア行", AiueoType.KANA,  "あいうえおぁぃぅぇぉアイウエオァィゥェォ"),
        AiueoItem("カ行", AiueoType.KANA,  "かきくけこカキクケコ"),
        AiueoItem("サ行", AiueoType.KANA,  "さしすせそサシスセソ"),
        AiueoItem("タ行", AiueoType.KANA,  "たちつてとタチツテト"),
        AiueoItem("ナ行", AiueoType.KANA,  "なにぬねのナニヌネノ"),
        AiueoItem("ハ行", AiueoType.KANA,  "はひふへほハヒフヘホ"),
        AiueoItem("マ行", AiueoType.KANA,  "まみむめもマミムメモ"),
        AiueoItem("ヤ行", AiueoType.KANA,  "やゆよヤユヨ"),
        AiueoItem("ラ行", AiueoType.KANA,  "らりるれろラリルレロ"),
        AiueoItem("ワ行", AiueoType.KANA,  "わをんワヲン"),
        AiueoItem("漢字",  AiueoType.KANJI),
        AiueoItem("英語",  AiueoType.ALPHA),
        AiueoItem("その他", AiueoType.OTHER)
    )

    private val allKanaChars by lazy {
        aiueoItems.filter { it.type == AiueoType.KANA }.joinToString("") { it.chars }
    }

    data class FilterItem(val label: String, val key: String, val color: String)
    private val examFilterItems = listOf(
        FilterItem("すべて",   "ALL",         "#607D8B"),
        FilterItem("無線工学", "ENGINEERING", "#1565C0"),
        FilterItem("法規",     "LAW",         "#6A1B9A")
    )

    data class SpinnerItem(val label: String, val examType: ExamType?)
    private val spinnerItems = listOf(
        SpinnerItem("全試験共通", null),
        SpinnerItem("無線工学A",  ExamType.ENGINEERING_A),
        SpinnerItem("無線工学B",  ExamType.ENGINEERING_B),
        SpinnerItem("法規A",      ExamType.LAW_A),
        SpinnerItem("法規B",      ExamType.LAW_B)
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

        root.addView(TextView(this).apply {
            text     = "📚 単語・公式帳"
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
            text = "➕ 新しく登録する"
            textSize = 15f
            setTextColor(Color.WHITE)
            backgroundTintList = android.content.res.ColorStateList.valueOf(
                Color.parseColor("#4CAF50")
            )
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 12
            layoutParams = lp
            setPadding(16, 36, 16, 36)
            setOnClickListener { showEditDialog(null) }
        })

        // 検索バー
        etSearch = EditText(this).apply {
            hint      = "🔍 単語名・内容で検索"
            textSize  = 14f
            inputType = InputType.TYPE_CLASS_TEXT
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 8
            layoutParams = lp
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, st: Int, c: Int, a: Int) {}
                override fun onTextChanged(s: CharSequence?, st: Int, b: Int, c: Int) {}
                override fun afterTextChanged(s: Editable?) {
                    searchQuery = s?.toString()?.trim() ?: ""
                    refreshList()
                }
            })
        }
        root.addView(etSearch)

        // 公式フィルターボタン
        root.addView(makeFormulaFilterButton())

        // 五十音フィルター
        root.addView(TextView(this).apply {
            text     = "絞り込み（タップで選択・再タップで解除）"
            textSize = 12f
            setTextColor(Color.parseColor("#888888"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 4
            layoutParams = lp
        })
        root.addView(makeAiueoFilterArea())

        // 試験種別フィルター
        filterRow = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        root.addView(makeExamFilterRow())

        // カテゴリフィルター
        root.addView(makeCategoryFilterArea())

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

    // ===== 公式フィルターボタン =====
    private lateinit var btnFormula: Button

    private fun makeFormulaFilterButton(): Button {
        btnFormula = Button(this).apply {
            text = "📐 公式のみ表示"
            textSize = 13f
            setTextColor(Color.WHITE)
            backgroundTintList = android.content.res.ColorStateList.valueOf(
                if (formulaOnly) Color.parseColor("#E65100")
                else             Color.parseColor("#BDBDBD")
            )
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 8
            layoutParams = lp
            setPadding(24, 16, 24, 16)
            setOnClickListener {
                formulaOnly = !formulaOnly
                backgroundTintList = android.content.res.ColorStateList.valueOf(
                    if (formulaOnly) Color.parseColor("#E65100")
                    else             Color.parseColor("#BDBDBD")
                )
                refreshList()
            }
        }
        return btnFormula
    }

    // ===== 五十音フィルターエリア =====
    private fun makeAiueoFilterArea(): HorizontalScrollView {
        val hsv = HorizontalScrollView(this).apply {
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            lp.bottomMargin = 8
            layoutParams = lp
        }
        aiueoRow = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        hsv.addView(aiueoRow)
        rebuildAiueoRow()
        return hsv
    }

    private fun rebuildAiueoRow() {
        aiueoRow.removeAllViews()
        aiueoRow.addView(makeAiueoButton("全て", ""))
        aiueoItems.forEach { item ->
            val color = when (item.type) {
                AiueoType.KANA  -> "#607D8B"
                AiueoType.KANJI -> "#795548"
                AiueoType.ALPHA -> "#00695C"
                AiueoType.OTHER -> "#546E7A"
            }
            aiueoRow.addView(makeAiueoButton(item.label, item.label, color))
        }
    }

    private fun makeAiueoButton(label: String, key: String, baseColor: String = "#607D8B"): Button {
        return Button(this).apply {
            text = label; textSize = 11f; setTextColor(Color.WHITE)
            val isSelected = aiueoFilter == key
            backgroundTintList = android.content.res.ColorStateList.valueOf(
                if (isSelected) Color.parseColor("#FF6F00") else Color.parseColor(baseColor)
            )
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.marginEnd = 6; layoutParams = lp; setPadding(16, 12, 16, 12)
            setOnClickListener {
                aiueoFilter = if (aiueoFilter == key) "" else key
                rebuildAiueoRow(); refreshList()
            }
        }
    }

    private fun matchesAiueo(title: String, filterKey: String): Boolean {
        if (filterKey.isEmpty()) return true
        val firstChar = title.firstOrNull() ?: return false
        val item = aiueoItems.find { it.label == filterKey } ?: return true
        return when (item.type) {
            AiueoType.KANA  -> firstChar in item.chars
            AiueoType.KANJI -> Character.UnicodeBlock.of(firstChar) in listOf(
                Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS,
                Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A,
                Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B,
                Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
            )
            AiueoType.ALPHA -> firstChar in 'A'..'Z' || firstChar in 'a'..'z' ||
                    firstChar in '\uFF21'..'\uFF3A' || firstChar in '\uFF41'..'\uFF5A'
            AiueoType.OTHER -> firstChar !in allKanaChars &&
                    Character.UnicodeBlock.of(firstChar) !in listOf(
                Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS,
                Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A,
                Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B,
                Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
            ) && firstChar !in 'A'..'Z' && firstChar !in 'a'..'z' &&
                    firstChar !in '\uFF21'..'\uFF3A' && firstChar !in '\uFF41'..'\uFF5A'
        }
    }

    // ===== 試験種別フィルター =====
    private fun makeExamFilterRow(): LinearLayout {
        val row = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.bottomMargin = 8; layoutParams = lp
        }
        examFilterItems.forEach { item ->
            row.addView(Button(this).apply {
                text = item.label; textSize = 13f; setTextColor(Color.WHITE)
                backgroundTintList = android.content.res.ColorStateList.valueOf(
                    Color.parseColor(item.color))
                alpha = if (currentFilter == item.key) 1.0f else 0.5f
                val lp = LinearLayout.LayoutParams(
                    0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                lp.marginEnd = 8; layoutParams = lp; setPadding(8, 20, 8, 20)
                setOnClickListener {
                    currentFilter  = item.key
                    categoryFilter = ""
                    rebuildCategoryRow()
                    refreshList()
                }
            })
        }
        return row
    }

    // ===== カテゴリフィルターエリア =====
    private lateinit var categoryScrollView: HorizontalScrollView
    private lateinit var categoryRowView: LinearLayout

    private fun makeCategoryFilterArea(): HorizontalScrollView {
        categoryScrollView = HorizontalScrollView(this).apply {
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.bottomMargin = 12; layoutParams = lp
        }
        categoryRowView = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        categoryScrollView.addView(categoryRowView)
        rebuildCategoryRow()
        return categoryScrollView
    }

    private fun rebuildCategoryRow() {
        categoryRowView.removeAllViews()

        val categories = when (currentFilter) {
            "ENGINEERING" -> engineeringCategories
            "LAW"         -> lawCategories
            else          -> emptyList()
        }

        if (categories.isEmpty()) {
            categoryScrollView.visibility = android.view.View.GONE
            return
        }
        categoryScrollView.visibility = android.view.View.VISIBLE
        categoryRowView.addView(makeCategoryButton("すべて", ""))
        categories.forEach { cat ->
            categoryRowView.addView(makeCategoryButton(cat, cat))
        }
    }

    private fun makeCategoryButton(label: String, key: String): Button {
        return Button(this).apply {
            text = label; textSize = 11f; setTextColor(Color.WHITE)
            val isSelected = categoryFilter == key
            val baseColor = when (currentFilter) {
                "ENGINEERING" -> "#1565C0"
                "LAW"         -> "#6A1B9A"
                else          -> "#607D8B"
            }
            backgroundTintList = android.content.res.ColorStateList.valueOf(
                if (isSelected) Color.parseColor("#FF6F00") else Color.parseColor(baseColor)
            )
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.marginEnd = 6; layoutParams = lp; setPadding(16, 12, 16, 12)
            setOnClickListener {
                categoryFilter = if (categoryFilter == key) "" else key
                rebuildCategoryRow(); refreshList()
            }
        }
    }

    // ===== リスト更新 =====
    private fun refreshList() {
        listContainer.removeAllViews()
        var list = WordStorage.loadWords(this)

        // 試験種別フィルター
        list = when (currentFilter) {
            "ENGINEERING" -> list.filter {
                it.examType.isEmpty() ||
                        it.examType == ExamType.ENGINEERING_A.name ||
                        it.examType == ExamType.ENGINEERING_B.name
            }
            "LAW" -> list.filter {
                it.examType.isEmpty() ||
                        it.examType == ExamType.LAW_A.name ||
                        it.examType == ExamType.LAW_B.name
            }
            else -> list
        }

        // カテゴリフィルター
        if (categoryFilter.isNotEmpty()) {
            list = list.filter { it.category == categoryFilter }
        }

        // 公式フィルター
        if (formulaOnly) {
            list = list.filter { it.isFormula }
        }

        // 検索フィルター
        if (searchQuery.isNotEmpty()) {
            list = list.filter {
                it.title.contains(searchQuery, ignoreCase = true) ||
                        it.content.contains(searchQuery, ignoreCase = true)
            }
        }

        // 五十音フィルター
        if (aiueoFilter.isNotEmpty()) {
            list = list.filter { matchesAiueo(it.title, aiueoFilter) }
        }

        if (list.isEmpty()) {
            listContainer.addView(TextView(this).apply {
                text = "条件に一致する単語がありません"
                textSize = 15f
                setTextColor(Color.parseColor("#999999"))
                gravity = Gravity.CENTER
                val lp = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                lp.topMargin = 32; layoutParams = lp
            })
            return
        }

        listContainer.addView(TextView(this).apply {
            text = "${list.size} 件"
            textSize = 13f
            setTextColor(Color.parseColor("#888888"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.bottomMargin = 8; layoutParams = lp
        })

        list.forEach { entry -> addEntryRow(entry) }
    }

    // ===== 単語カード =====
    private fun addEntryRow(entry: WordEntry) {
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.WHITE)
            setPadding(20, 16, 20, 16)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.bottomMargin = 8; layoutParams = lp
        }

        val headerRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL; gravity = Gravity.CENTER_VERTICAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        }
        headerRow.addView(TextView(this).apply {
            text = "${if (entry.isFormula) "📐" else "📖"} ${entry.title}"
            textSize = 15f; typeface = android.graphics.Typeface.DEFAULT_BOLD
            setTextColor(Color.parseColor("#333333"))
            layoutParams = LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        })
        headerRow.addView(Button(this).apply {
            text = "編集"; textSize = 11f; setTextColor(Color.WHITE)
            backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#2196F3"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.marginEnd = 8; layoutParams = lp; setPadding(16, 8, 16, 8)
            setOnClickListener { showEditDialog(entry) }
        })
        headerRow.addView(Button(this).apply {
            text = "削除"; textSize = 11f; setTextColor(Color.WHITE)
            backgroundTintList = android.content.res.ColorStateList.valueOf(Color.parseColor("#F44336"))
            setPadding(16, 8, 16, 8)
            setOnClickListener {
                AlertDialog.Builder(this@WordBookActivity)
                    .setTitle("削除確認").setMessage("「${entry.title}」を削除しますか？")
                    .setPositiveButton("削除") { _, _ ->
                        WordStorage.deleteWord(this@WordBookActivity, entry.id); refreshList()
                    }.setNegativeButton("キャンセル", null).show()
            }
        })
        card.addView(headerRow)

        card.addView(TextView(this).apply {
            text = entry.content; textSize = 13f; setTextColor(Color.parseColor("#555555"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.topMargin = 8; layoutParams = lp
        })

        val tagRow = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.topMargin = 8; layoutParams = lp
        }
        val tagLabel = when (entry.examType) {
            ExamType.ENGINEERING_A.name -> Pair("無線工学A", "#1565C0")
            ExamType.ENGINEERING_B.name -> Pair("無線工学B", "#1976D2")
            ExamType.LAW_A.name         -> Pair("法規A",    "#6A1B9A")
            ExamType.LAW_B.name         -> Pair("法規B",    "#7B1FA2")
            else                        -> Pair("全試験共通", "#607D8B")
        }
        tagRow.addView(makeTag(tagLabel.first, tagLabel.second))
        if (entry.category.isNotEmpty()) {
            tagRow.addView(makeTag(entry.category, "#FF6F00"))
        }
        if (entry.isFormula) {
            tagRow.addView(makeTag("📐 公式", "#E65100"))
        }
        card.addView(tagRow)
        listContainer.addView(card)
    }

    private fun makeTag(text: String, color: String): TextView {
        return TextView(this).apply {
            this.text = text; textSize = 11f; setTextColor(Color.WHITE)
            setBackgroundColor(Color.parseColor(color)); setPadding(12, 4, 12, 4)
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.marginEnd = 6; layoutParams = lp
        }
    }

    // ===== 登録・編集ダイアログ =====
    private fun showEditDialog(existing: WordEntry?) {
        val scrollDialog = ScrollView(this)
        val dialogView   = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL; setPadding(48, 32, 48, 16)
        }
        scrollDialog.addView(dialogView)

        fun lbl(t: String) = TextView(this).apply {
            text = t; textSize = 13f; setTextColor(Color.parseColor("#888888"))
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.topMargin = 12; layoutParams = lp
        }

        // 単語名
        dialogView.addView(lbl("単語・公式名"))
        val etTitle = EditText(this).apply {
            hint = "例：フレネルゾーン"; textSize = 15f
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
            imeOptions = EditorInfo.IME_FLAG_NO_ENTER_ACTION
            isFocusable = true; isFocusableInTouchMode = true
            setText(existing?.title ?: "")
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        }
        dialogView.addView(etTitle)

        // 説明
        dialogView.addView(lbl("意味・説明・公式"))
        val etContent = EditText(this).apply {
            hint = "例：電波伝搬における楕円体領域"; textSize = 14f
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
            minLines = 3; maxLines = 10; gravity = Gravity.TOP or Gravity.START
            isFocusable = true; isFocusableInTouchMode = true
            setText(existing?.content ?: "")
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        }
        dialogView.addView(etContent)

        // 公式チェックボックス
        dialogView.addView(lbl("種類"))
        val cbFormula = CheckBox(this).apply {
            text = "📐 公式として登録する"
            textSize = 14f
            isChecked = existing?.isFormula ?: false
            val lp = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            lp.bottomMargin = 8; layoutParams = lp
        }
        dialogView.addView(cbFormula)

        // 試験��別スピナー
        dialogView.addView(lbl("試験種別"))
        val spinnerExam = Spinner(this).apply {
            adapter = ArrayAdapter(this@WordBookActivity,
                android.R.layout.simple_spinner_dropdown_item,
                spinnerItems.map { it.label })
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        }
        val defaultExamIndex = spinnerItems.indexOfFirst {
            it.examType?.name == existing?.examType }.coerceAtLeast(0)
        // ★ setSelectionはリスナー設定より先に行う
        spinnerExam.setSelection(defaultExamIndex)
        dialogView.addView(spinnerExam)

        // ===== 区分スピナー（試験種別に連動） =====
        dialogView.addView(lbl("区分（カテゴリ）"))
        val spinnerCategory = Spinner(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        }
        dialogView.addView(spinnerCategory)

        // 試験種別に対応するカテゴリをセットする関数
        fun applyCategoriesToSpinner(examIndex: Int, preselectCategory: String = "") {
            val examTypeName = spinnerItems[examIndex].examType?.name
            val categories   = getCategoriesForExamType(examTypeName)
            spinnerCategory.adapter = ArrayAdapter(
                this@WordBookActivity,
                android.R.layout.simple_spinner_dropdown_item,
                categories
            )
            val idx = categories.indexOfFirst { it == preselectCategory }.coerceAtLeast(0)
            spinnerCategory.setSelection(idx)
        }

        // 既存カテゴリを保持（初期表示で正しく選択するために使用）
        var pendingCategory: String? = existing?.category

        spinnerExam.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long
            ) {
                // pendingCategory が非nullの場合は初回呼び出し：既存カテゴリを復元
                val preselect = pendingCategory ?: ""
                pendingCategory = null  // 次回以降はリセット（ユーザー操作時は空文字）
                applyCategoriesToSpinner(position, preselect)
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // 関連問題ID
        dialogView.addView(lbl("関連問題ID（カンマ区切り　空欄=全問題に表示）"))
        val etIds = EditText(this).apply {
            hint = "例：101,102,103"; textSize = 14f
            inputType = InputType.TYPE_CLASS_PHONE
            setText(existing?.questionIds?.joinToString(",") ?: "")
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        }
        dialogView.addView(etIds)

        val dialog = AlertDialog.Builder(this)
            .setTitle(if (existing == null) "➕ 新規登録" else "✏️ 編集")
            .setView(scrollDialog)
            .setPositiveButton("保存") { _, _ ->
                val title   = etTitle.text.toString().trim()
                val content = etContent.text.toString().trim()
                if (title.isEmpty() || content.isEmpty()) {
                    Toast.makeText(this, "タイトルと内容を入力してください", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                val selectedExam = spinnerItems[spinnerExam.selectedItemPosition].examType
                val categories   = getCategoriesForExamType(selectedExam?.name)
                val selectedCat  = categories[spinnerCategory.selectedItemPosition]
                    .let { if (it == "なし") "" else it }
                val idsText = etIds.text.toString().trim()
                val ids = if (idsText.isEmpty()) emptyList()
                else idsText.split(",").mapNotNull { it.trim().toIntOrNull() }

                WordStorage.saveWord(this, WordEntry(
                    id          = existing?.id ?: System.currentTimeMillis(),
                    title       = title,
                    content     = content,
                    examType    = selectedExam?.name ?: "",
                    category    = selectedCat,
                    isFormula   = cbFormula.isChecked,
                    questionIds = ids
                ))
                refreshList()
            }
            .setNegativeButton("キャンセル", null).create()

        dialog.setOnShowListener { etTitle.requestFocus() }
        dialog.show()
    }}
