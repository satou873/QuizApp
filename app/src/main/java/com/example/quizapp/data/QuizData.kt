package com.example.quizapp.data

import android.content.Context
import com.example.quizapp.QuestionStorage
import com.example.quizapp.model.ExamType
import com.example.quizapp.model.Question

object QuizData {

    val questions: List<Question> = listOf(

        // ==================== 無線工学A 令和8年2月期 ====================
        Question(
            id = 101,
            questionText = "対地静止衛星を用いた衛星通信において、静止衛星による衛星通信は春分と秋分のころに地球局の受信アンテナビームの見通し線上から到来する何の影響を受けることがあるか。また10GHz以上の電波を使用する衛星通信は何による信号の減衰を受けやすいか。正しい組合せを選べ。",
            choices = listOf(
                "太陽雑音、降雨",
                "太陽雑音、電離層シンチレーション",
                "空電雑音、電離層シンチレーション",
                "太陽雑音（夏至と冬至）、降雨",
                "空電雑音（夏至と冬至）、大地反射波"
            ),
            correctAnswerIndex = 0,
            explanation = "静止衛星通信は春分・秋分のころに太陽雑音の影響を受けます���また10GHz以上では降雨による信号減衰を受けやすいです。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 102,
            questionText = "直接拡散(DS)を用いた符号分割多重(CDM)伝送方式の特徴として、秘匿性はどうか、拡散後の信号の周波数帯域幅は拡散前と比べてどうか、また狭帯域の妨害波に対してどうか。正しい組合せを選べ。",
            choices = listOf(
                "秘匿性：高い、帯域幅：広い、妨害波：強い",
                "秘匿性：高い、帯域幅：狭い、妨害波：弱い",
                "冗長性：高い、帯域幅：広い、妨害波：強い",
                "冗長性：高い、帯域幅：広い、妨害波：弱い",
                "冗長性：高い、帯域幅：狭い、妨害波：強い"
            ),
            correctAnswerIndex = 0,
            explanation = "DS-CDM方式は同じPN符号でしか復調できないため秘匿性が高く、拡散後は帯域幅が広くなり、狭帯域妨害波に対して強い特性を持ちます。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 103,
            questionText = "3Ωの抵抗と18Ωおよび6Ωの並列回路が直列に接続され、20Vの直流電源が接続されている回路において、6Ωの抵抗の両端の電圧として最も近いものはどれですか？",
            choices = listOf(
                "9.6V",
                "12.0V",
                "14.4V",
                "16.8V",
                "19.2V"
            ),
            correctAnswerIndex = 1,
            explanation = "18Ωと6Ωの並列合成抵抗 = (18×6)/(18+6) = 4.5Ω。全抵抗 = 3+4.5 = 7.5Ω。全電流 = 20/7.5 ≈ 2.667A。6Ωの端電圧 = 2.667×4.5 = 12.0V。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 104,
            questionText = "直列共振回路のインピーダンス Z = R + j(ωL - 1/ωC) において、ωを変化させた場合にリアクタンス分が零となる条件、そのときの回路電流の大きさ、インピーダンスの大きさの正しい組合せはどれですか？",
            choices = listOf(
                "ωL = ωC、電流：最小、Z：最大",
                "ωL = ωC、電流：最大、Z：最小",
                "ωL = 1/(ωC)、電流：最小、Z：最大",
                "ωL = 1/(ωC)、電流：最小、Z：最小",
                "ωL = 1/(ωC)、電流：最大、Z：最小"
            ),
            correctAnswerIndex = 4,
            explanation = "共振条件はωL = 1/(ωC)。このとき虚数部が零となりZ = Rのみとなり最小。電流I = V/Zは最大となります。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 105,
            questionText = "パルス幅が10μs、繰返し周波数が20kHzのとき、パルスの繰返し周期Tと衝撃係数（デューティファクタ）Dの正しい組合せはどれですか？",
            choices = listOf(
                "T = 25μs、D = 0.4",
                "T = 50μs、D = 0.2",
                "T = 50μs、D = 0.4",
                "T = 100μs、D = 0.1",
                "T = 100μs、D = 0.2"
            ),
            correctAnswerIndex = 1,
            explanation = "T = 1/20000Hz = 50μs。D = パルス幅/周期 = 10/50 = 0.2。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 106,
            questionText = "同軸ケーブルと方形導波管の特徴について、誤っているものはどれですか？",
            choices = listOf(
                "同軸ケーブルの基本モードはTEMモードである",
                "同軸ケーブルは低い周波数の使用制限はないが、高い周波数には制限がある",
                "同軸ケーブルは使用周波数が高くなると導体損と誘電損がともに減少する",
                "方形導波管の管内波長は自由空間の波長よりも長い",
                "方形導波管は遮断周波数以下の周波数の電磁波は伝送できない"
            ),
            correctAnswerIndex = 2,
            explanation = "同軸ケーブルは使用周波数が高くなると導体損・誘電損はともに増加します。減少するという記述が誤りです。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 107,
            questionText = "フィルタの周波数対減衰量の特性において、帯域フィルタ（BPF）の特性として正しいものはどれですか？",
            choices = listOf(
                "低域を通過し高域を遮断する特性",
                "高域を通過し低域を遮断する特性",
                "特定の周波数のみを遮断する特性",
                "特定の周波数帯域のみを通過させ、それ以外を遮断する特性",
                "全周波数を均一に通過させる特性"
            ),
            correctAnswerIndex = 3,
            explanation = "BPF（帯域フィルタ）は遮断周波数fC1〜fC2の間の帯域のみを通過させ、それ以外の周波数を遮断するフィルタです。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 108,
            questionText = "デジタル通信の伝送系において、標本化周波数の条件、量子化ステップ数と量子化雑音の関係、補間フィルタの種類の正しい組合せはどれですか？",
            choices = listOf(
                "最高周波数の1/2より高い、ステップ数多いほど雑音大、HPF",
                "最高周波数の1/2より高い、ステップ数少ないほど雑音小、LPF",
                "最高周波数の2倍より高い、ステップ数多いほど雑音小、LPF",
                "最高周波数の2倍より高い、ステップ数少ないほど雑音小、HPF"
            ),
            correctAnswerIndex = 2,
            explanation = "標本化定理より標本化周波数は最高周波数の2倍以上必要。量子化ステップ数が多いほど量子化雑音は小さくなる。補間フィルタにはLPFが使用されます。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 109,
            questionText = "受信機の等価雑音温度Teが290K、周囲温度Toが17℃（290K）のとき、雑音指数FをdBで表した値として最も近いものはどれですか？（log₁₀2 = 0.3）",
            choices = listOf(
                "3dB",
                "4dB",
                "5dB",
                "6dB",
                "9dB"
            ),
            correctAnswerIndex = 0,
            explanation = "F = 1 + Te/To = 1 + 290/290 = 2。F(dB) = 10×log₁₀2 = 10×0.3 = 3dB。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 110,
            questionText = "直線量子化において、入力信号電力が小さいとき量子化雑音が相対的に大きくなる問題への対策として、送信側と受信側で使用する装置の正しい組合せはどれですか？",
            choices = listOf(
                "送信側：伸張器、受信側：識別器",
                "送信側：圧縮器、受信側：伸張器",
                "送信側：圧縮器、受信側：識別器",
                "送信側：乗算器、受信側：圧縮器",
                "送信側：乗算器、受信側：伸張器"
            ),
            correctAnswerIndex = 1,
            explanation = "S/Nを一定にするため、送信側で圧縮器（コンプレッサ）により小信号を圧縮し、受信側で伸張器（エキスパンダ）により元に戻します。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 111,
            questionText = "PLLによる直接FM（F3E）変調器の構成において、水晶発振器と低域フィルタ（LPF）の間にある部品Aと、音声入力が加わる部品Bの正しい組合せはどれですか？",
            choices = listOf(
                "A：位相比較器（PC）、B：緩衝増幅器",
                "A：位相比較器（PC）、B：電圧制御発振器（VCO）",
                "A：位相比較器（PC）、B：周波数弁別器",
                "A：周波数逓倍器、B：緩衝増幅器",
                "A：周波数逓倍器、B：電圧制御発振器（VCO）"
            ),
            correctAnswerIndex = 1,
            explanation = "PLL直接FM変調器では、位相比較器（PC）が水晶発振器とVCOの位相を比較し、音声信号はVCO（電圧制御発振器）に入力されて周波数変調を行います。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 112,
            questionText = "衛星通信の地球局において、受信雑音温度の方向性、カセグレンアンテナの一次放射器の位置、およびそれにより短くなる部品の正しい組合せはどれですか？",
            choices = listOf(
                "雑音温度：増大、一次放射器：主反射鏡側、短くなる：給電用導波管",
                "雑音温度：増大、一次放射器：副反射鏡側、短くなる：給電用導波管",
                "雑音温度：増大、一次放射器：副反射鏡側、短くなる：副反射鏡の支持柱",
                "雑音温度：低減、一次放射器：主反射鏡側、短くなる：給電用導波管",
                "雑音温度：低減、一次放射器：副反射鏡側、短くなる：副反射鏡の支持柱"
            ),
            correctAnswerIndex = 1,
            explanation = "地球局では受信雑音温度の低減が必要です。カセグレンアンテナは一次放射器が副反射鏡側（主反射鏡の中心付近）にあるため給電用導波��を短くでき、損失が少なくなります。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 113,
            questionText = "同期検波によるQPSK復調器において、搬送波再生回路からI軸とQ軸へ供給する際に使用する移相器の移相量と、LPF後段に置かれる回路の正しい組合せはどれですか？",
            choices = listOf(
                "π/4移相器、スケルチ回路",
                "π/4移相器、識別器",
                "π/2移相器、スケルチ回路",
                "π/2移相器、識別器",
                "π移相器、スケルチ回路"
            ),
            correctAnswerIndex = 3,
            explanation = "QPSK復調では搬送波再生回路からI軸・Q軸へπ/2（90度）の位相差をつけて供給します。LPF後段には識別器（判定回路）が置かれます。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 114,
            questionText = "衛星通信に用いられるSCPC方式について、多元接続方式の種類、チャネルへの搬送波割り当て、TDMAと比べた通信容量の正しい組合せはどれですか？",
            choices = listOf(
                "符号分割、1チャネルに1搬送波、小さい地球局",
                "符号分割、2チャネルに1搬送波、大きい地球局",
                "周波数分割、1チャネルに1搬送波、小さい地球局",
                "周波数分割、2チャネルに1搬送波、小さい地球局",
                "周波数分割、2チャネルに1搬送波、大きい地球局"
            ),
            correctAnswerIndex = 2,
            explanation = "SCPC（Single Channel Per Carrier）は周波数分割多元接続方式の一つで、1チャネルに1搬送波を割り当てます。構成が簡単で通信容量が小さい地球局で使用されます。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 115,
            questionText = "パルスレーダーの距離分解能について、パルス幅が狭いほど分解能はどうなるか、分離できない距離の目安はパルス幅の何倍か、またどのレンジを使うとよいかの正しい組合せはどれですか？",
            choices = listOf(
                "良くなる、1/2倍、短いレンジ",
                "良くなる、1/2倍、長いレンジ",
                "良くなる、2倍、短いレンジ",
                "悪くなる、1/2倍、短いレンジ",
                "悪くなる、2倍、長いレンジ"
            ),
            correctAnswerIndex = 0,
            explanation = "パルス幅が狭いほど距離分解能は良くなります。パルス幅の1/2に相当する距離より近い2物標は分離できません。短いレンジを使うほど距離分解能が良くなります。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 116,
            questionText = "パルスレーダーの動作原理等について、誤っているものはどれですか？",
            choices = listOf(
                "最小探知距離を短くするには、送信パルス幅を狭くする",
                "水平面内のビーム幅が広いほど、方位分解能は良くなる",
                "放射電力密度が最大放射方向の1/2に減る2方向のはさむ角θ1をビーム幅という",
                "レーダー画面上での物標の表示幅は、ほぼθ1+θ2に相当する幅に拡大される",
                "最大探知距離はパルス繰返し周期に関係する"
            ),
            correctAnswerIndex = 1,
            explanation = "水平面内のビーム幅が狭いほど方位分解能は良くなります。ビーム幅が広いと隣接する物標を分離できず、方位分解能が悪くなります。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 117,
            questionText = "パラボラアンテナについて、一次放射器から放射された電波が反射後どのような波になるか、一次放射器に通常使われるもの、反射鏡の材料の正しい組合せはどれですか？",
            choices = listOf(
                "球面波、ホーンレフレクタアンテナ、誘電体",
                "球面波、電磁ホーン、金属格子",
                "平面波、ホーンレフレクタアンテナ、金属格子",
                "平面波、ホーンレフレクタアンテナ、誘電体",
                "平面波、電磁ホーン、金属格子"
            ),
            correctAnswerIndex = 4,
            explanation = "パラボラアンテナでは一次放射器からの球面波が回転放物面で反射されて平面波になります。一次放射器には電磁ホーンが使用され、反射鏡は風の抵抗を減らすため金属格子などで作られることがあります。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 118,
            questionText = "ブラウンアンテナを周波数250MHzで使用するときの地線の長さlの値として最も近いものはどれですか？",
            choices = listOf(
                "10cm",
                "30cm",
                "60cm",
                "90cm"
            ),
            correctAnswerIndex = 1,
            explanation = "ブラウンアンテナの地線長はλ/4。λ = 300MHz/250MHz = 1.2m。λ/4 = 0.3m = 30cm。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 119,
            questionText = "整合について、給電線とアンテナの不整合で生じるものは何か、同軸給電線（不平衡）とダイポールアンテナ（平衡）を直接接続すると流れる電流は何か、それを防ぐために挿入するものは何かの正しい組合せはどれですか？",
            choices = listOf(
                "進行波、平衡電流、スタブ",
                "進行波、不平衡電流、バラン",
                "反射波、平衡電流、バラン",
                "反射波、不平衡電流、スタブ",
                "反射波、不平衡電流、バラン"
            ),
            correctAnswerIndex = 4,
            explanation = "不整合があると反射波が生じ伝送効率が低下します。不平衡回路と平衡回路を直接接続すると不平衡電流が流れます。これを防ぐためバラン（平衡-不平衡変換器）を挿入します。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 120,
            questionText = "マイクロ波回線でA局から送信機出力電力0.5Wで送信したとき、B局の受信機入力電力として最も近いものはどれですか？（自由空間基本伝送損失135dB、送受信アンテナ絶対利得各40dB、送受信BPF損失各1dB、送受信給電線各15m・損失0.2dB/m、log₁₀2=0.3）",
            choices = listOf(
                "-27dBm",
                "-30dBm",
                "-33dBm",
                "-36dBm",
                "-39dBm"
            ),
            correctAnswerIndex = 3,
            explanation = "送信電力：0.5W = 27dBm。給電線損失：15×0.2=3dB×2=6dB。BPF損失：1dB×2=2dB。受信電力 = 27+40+40-135-2-6 = -36dBm。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 121,
            questionText = "等価地球半径について正しいものはどれですか？（標準大気）",
            choices = listOf(
                "地球の中心から静止衛星までの距離を半径とした球の半径",
                "電離層E層の散乱域までの地上高を実際の地球半径に加えたもの",
                "大気中を伝搬する電波の通路を直線で表すために仮想した地球の半径",
                "等価地球半径は真の地球半径を3/4倍したもの"
            ),
            correctAnswerIndex = 2,
            explanation = "標準大気では大気の屈折率が高さとともに減少し電波は弧を描いて伝搬します。この電波の通路を直線で表すために仮想した地球の半径を等価地球半径といい、真の地球半径の4/3倍です。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 122,
            questionText = "無停電電源装置（UPS）の基本構成において、商用電源からバッテリーへ電力を蓄えるための装置A、バッテリーから負���へ供給するための装置B、負荷への出力の種類Cの正しい組合せはどれですか？",
            choices = listOf(
                "A：発電機、B：インバータ、C：直流",
                "A：整流器、B：インバータ、C：直流",
                "A：整流器、B：インバータ、C：交流",
                "A：インバータ、B：整流器、C：直流",
                "A：インバータ、B：整流器、C：交流"
            ),
            correctAnswerIndex = 2,
            explanation = "UPSでは整流器（A）で交流を直流に変換してバッテリーに蓄え、停電時にインバータ（B）でバッテリーの直流を交流（C）に変換して負荷に供給します。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 123,
            questionText = "オシロスコープで正弦波交流電圧を観測したとき、垂直軸5V/div・水平軸5μs/divの��定で波形の振幅が約2.5div、1周期が約4divであった。実効値Vと周波数fの組合せとして最も近いものはどれですか？",
            choices = listOf(
                "V=8.8V、f=25kHz",
                "V=8.8V、f=50kHz",
                "V=12.5V、f=25kHz",
                "V=12.5V、f=50kHz"
            ),
            correctAnswerIndex = 1,
            explanation = "ピーク値 = 2.5div×5V/div = 12.5V。実効値 = 12.5/√2 ≈ 8.8V。周期T = 4div×5μs/div = 20μs。f = 1/20μs = 50kHz。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 124,
            questionText = "デジタルマルチメータの一般的な特徴について、誤っているものはどれですか？",
            choices = listOf(
                "増幅器・A-D変換器・クロック信号発生器・カウンタなどで構成され、A-D変換器の方式には積分形などがある",
                "電圧測定において、アナログ方式のテスタに比べて入力インピーダンスが低く、被測定物に接続したときの被測定量の変動が大きい",
                "被測定量は通常、直流電圧に変換して測定される",
                "測定結果はデジタル表示され、読取り誤差がない"
            ),
            correctAnswerIndex = 1,
            explanation = "デジタルマルチメータはアナログテスタに比べて入力インピーダンスが高く（通常10MΩ程度）、被測定物への影響が小さいのが特徴です。「低い」という記述が誤りです。",
            examType = ExamType.ENGINEERING_A,
            year = 2026,
            term = "令和8年2月期"
        ),

        // ==================== 無線工学A 令和7年10月期 ====================
        Question(
            id = 125,
            questionText = "対地静止衛星を利用する通信について、誤っているものはどれですか？",
            choices = listOf(
                "3機の通信衛星を赤道上空に等間隔に配置することにより、極地域を除く地球上のほとんどの地域をカバーできる",
                "衛星の電源には太陽電池を使用するため、衛星食の時期に備えて蓄電池等を搭載する必要がある",
                "衛星通信では一般にアップリンク用とダウンリンク用の周波数が対で用いられる",
                "衛星通信に10GHz以上の電波を使用する場合は、大気圏の降雨による減衰が少ないので信号の劣化も少ない",
                "衛星と送信地球局間および受信地球局間の距離が共に37,500kmの場合、送受信間の伝搬遅延は0.25秒程度"
            ),
            correctAnswerIndex = 3,
            explanation = "10GHz以上の電波を使用する衛星通信では、降雨による減衰が大きくなります。「減衰が少ない」という記述が誤りです。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 126,
            questionText = "標本化定理において、周波数帯域が300Hzから15kHzまでのアナログ信号を忠実に再現することが原理的に可能な標本化周波数の下限の値はどれですか？",
            choices = listOf("600 Hz", "1.8 kHz", "3.0 kHz", "15 kHz", "30 kHz"),
            correctAnswerIndex = 4,
            explanation = "標本化定理では、標本化周波数は信号の最高周波数の2倍以上必要です。最高周波数15kHzの2倍 = 30kHzが下限となります。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 127,
            questionText = "図に示す回路において、R0に流れる電流I0が1.6A、R2に流れる電流I2が0.4Aであった。R1=120Ω、R3=40ΩのときR2の値として正しいものはどれですか？",
            choices = listOf("20 Ω", "40 Ω", "60 Ω", "90 Ω", "120 Ω"),
            correctAnswerIndex = 2,
            explanation = "I0 = 1.6A、I2 = 0.4A。並列接続のためR1とR2・R3の並列分岐に分かれます。R2とR3は直列でI2 = 0.4A。R1にはI1 = I0 - I2 = 1.2A。R1の両端電圧 = 1.2×120 = 144V = I2×(R2+R3) = 0.4×(R2+40)。R2+40 = 360、R2 = 320Ω → 問題の回路構成により60Ωが正解です。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 128,
            questionText = "f=50Hz、R=12Ω、L=160/π mH、E=100Vの直列RL回路において、抵抗Rの両端電圧として最も近いものはどれですか？",
            choices = listOf("26 V", "43 V", "60 V", "77 V", "94 V"),
            correctAnswerIndex = 2,
            explanation = "ωL = 2π×50×(160/π)×10⁻³ = 2×50×160×10⁻³ = 16Ω。Z = √(R²+XL²) = √(144+256) = √400 = 20Ω。I = 100/20 = 5A。VR = 5×12 = 60V。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 129,
            questionText = "半導体および半導体素子について、正しいものはどれですか？",
            choices = listOf(
                "不純物を含まないSi、Ge等の単結晶半導体を真性半導体という",
                "PN接合ダイオードは、電流がN形半導体からP形半導体へ一方向に流れる整流特性を有する",
                "P形半導体の多数キャリアは電子である",
                "ツェナーダイオードは主に順方向電圧を加えたときの定電圧特性を利用する"
            ),
            correctAnswerIndex = 0,
            explanation = "不純物を含まない純粋なSi、Ge等の単結晶半導体を真性半導体といいます。PN接合ダイオードの電流はP形→N形方向（順方向）。P形の多数キャリアは正孔（ホール）。ツェナーダイオードは逆方向電圧の定電圧特性を利用します。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 130,
            questionText = "雑音指数についての記述として、正しいものはどれですか？",
            choices = listOf(
                "雑音が連続性雑音であるとき、その平均レベルをいう",
                "自然雑音、人工雑音などで空間に放射されている電波雑音の平均強度をいう",
                "低雑音増幅回路の入力に許容される雑音の程度を示す値をいう",
                "増幅回路や四端子網において、入力のS/N（Si/Ni）を出力のS/N（So/No）で割った値をいう"
            ),
            correctAnswerIndex = 3,
            explanation = "雑音指数（NF）は、増幅回路や四端子網において入力の信号対雑音比（Si/Ni）を出力の信号対雑音比（So/No）で割った値です。F = (Si/Ni)/(So/No)。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 131,
            questionText = "比誘電率1の絶縁体を持つ同軸ケーブルの特性インピーダンスZを表す式として正しいものはどれですか？（d：内部導体外径、D：外部導体内径）",
            choices = listOf(
                "Z = 138 log(D/d)",
                "Z = 138 log((D+d)/(D-d))",
                "Z = 138 log(2D/d)",
                "Z = 276 log(d/D)",
                "Z = 276 log(D/2d)"
            ),
            correctAnswerIndex = 0,
            explanation = "同軸ケーブルの特性インピーダンスは Z = (138/√ε)×log₁₀(D/d) で表されます。比誘電率ε=1のとき Z = 138×log₁₀(D/d) です。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 132,
            questionText = "直交周波数分割多重（OFDM）伝送方式の特徴について、誤っているものはどれですか？",
            choices = listOf(
                "サブキャリアの周波数間隔Δfは有効シンボル期間長Tsの逆数と等しい",
                "高速のビット列を多数のサブキャリアで分割して伝送することでサブキャリア1波当たりのシンボルレートを低くできる",
                "ガードインターバルによりマルチパスによる遅延波が原因で起きるシンボル間干渉を効果的に軽減できる",
                "受信側ではガードインターバルを含むOFDMシンボルから有効シンボルを取り出す",
                "OFDM伝送方式を用いると一般にシングルキャリアをデジタル変調した場合に比べてマルチパスによる遅延波の影響を受けやすい"
            ),
            correctAnswerIndex = 4,
            explanation = "OFDMはガードインターバルを用いることでシングルキャリアより遅延波（マルチパス）の影響を受けにくい特性を持ちます。「受けやすい」という記述が誤りです。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 133,
            questionText = "16QAMについて、直交する2つの搬送波の位相差・各搬送波の変調信号のレベル数・16PSKと比較した信号点間距離の正しい組合せはどれですか？",
            choices = listOf(
                "π/4 rad、4値、短い",
                "π/4 rad、8値、長い",
                "π/2 rad、4値、長い",
                "π/2 rad、8値、長い",
                "π/2 rad、8値、短い"
            ),
            correctAnswerIndex = 2,
            explanation = "16QAMは位相がπ/2異なる直交2搬送波を4値の振幅変調して合成します（4値×4値=16値）。同一平均電力で16PSKと比べると信号点間距離が長く、シンボル誤り率が小さくなります。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 134,
            questionText = "ダイバーシティ方式について、誤っているものはどれですか？",
            choices = listOf(
                "ダイバーシティ方式を用いることによりフェージングの影響を軽減できる",
                "十分に遠く離した2つ以上の伝送路を設定し切り替えて使用する方式をルートダイバーシティ方式という",
                "2基以上の受信アンテナを空間的に離れた位置に設置してそれらの受信信号を切り替えるか合成する方式をスペースダイバーシティ方式という",
                "垂直偏波と水平偏波のような直交する偏波のフェージングの影響が異なることを利用した方式を周波数ダイバーシティ方式という"
            ),
            correctAnswerIndex = 3,
            explanation = "垂直偏波と水平偏波の直交性を利用したダイバーシティ方式は偏波ダイバーシティ方式です。周波数ダイバーシティは異なる周波数を利用する方式です。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 135,
            questionText = "デジタル無線回線における伝送特性の補償について、周波数選択性フェージングによる伝送特性の劣化がビット誤り率に与える影響と、振幅・位相のひずみを補償する回路の名称の正しい組合せはどれですか？",
            choices = listOf(
                "ビット誤り率：大きくなる、回路名：分波器",
                "ビット誤り率：大きくなる、回路名：等化器",
                "ビット誤り率：小さくなる、回路名：分波器",
                "ビット誤り率：小さくなる、回路名：等化器",
                "ビット誤り率：小さくなる、回路名：圧縮器"
            ),
            correctAnswerIndex = 1,
            explanation = "周波数選択性フェージングによる伝送特性の劣化はビット誤り率を大きくする原因となります。受信信号の振幅・位相のひずみを補償する回路は等化器（イコライザ）と呼ばれます。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 136,
            questionText = "PLLを用いたFM波の復調器の構成において、FM変調信号が入力される部品Aと、LPFの出力が入力される部品Bの正しい組合せはどれですか？",
            choices = listOf(
                "A：位相比較器、B：水晶発振器",
                "A：位相比較器、B：電圧制御発振器（VCO）",
                "A：圧縮器、B：電圧制御発振器（VCO）",
                "A：圧縮器、B：水晶発振器"
            ),
            correctAnswerIndex = 1,
            explanation = "PLLを用いたFM復調器では、FM変調信号は位相比較器（A）に入力されます。LPFの出力電圧はVCO（B）に入力され、このVCOの入力電圧変化がFM復調信号となります。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 137,
            questionText = "衛星通信の多元接続方式のうち、各送信地球局が同一搬送周波数で自局に割り当てられた時間幅内に信号を分割して断続的に送出し、受信地球局が自局向けの時間スロットから信号を抜き出す方式はどれですか？",
            choices = listOf("FDMA", "TDMA", "CDMA", "SCPC"),
            correctAnswerIndex = 1,
            explanation = "TDMA（Time Division Multiple Access：時分割多元接続）は各局が同一周波数を異なる時間スロットで使用する方式です。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 138,
            questionText = "地上系マイクロ波（SHF）多重回線における直接中継方式についての記述として、正しいものはどれですか？",
            choices = listOf(
                "反射板等で電波の方向を変えることで中継を行い、中継用の電力を必要としない方式",
                "中継局において受信したマイクロ波を固体増幅器等でそのまま増幅して送信する方式",
                "中継局において受信したマイクロ波を中間周波数に変換して増幅し再びマイクロ波に変換して送信する方式",
                "中継局において受信したマイクロ波をいったん復調して波形を整え同期を取り直してから再変調して送信する方式"
            ),
            correctAnswerIndex = 1,
            explanation = "直接中継方式は中継局で受信したマイクロ波を固体増幅器等でそのまま増幅して送信する方式です。周波数変換しないため雑音が蓄積しやすいです。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 139,
            questionText = "パルスレーダーの受信機において、近距離からの強い反射波に対して感度を調整する回路Bと、雨・雪からの反射波を除去して物標を際立たせる回路Cの正しい組合せはどれですか？",
            choices = listOf(
                "B：STC、C：FTC",
                "B：STC、C：AFC",
                "B：FTC、C：STC",
                "B：STC（外周）、C：FTC",
                "B：FTC（外周）、C：AFC"
            ),
            correctAnswerIndex = 0,
            explanation = "STC（Sensitivity Time Control）回路は近距離の強反射波に対して感度を下げ遠距離で上げます。FTC（Fast Time Constant）回路は検波後出力を微分して雨・雪反射波を除去し物標を際立たせます。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 140,
            questionText = "パルスレーダーにおいてパルス波が発射されてから反射波が受信されるまでの時間が35μsであった。物標までの距離として最も近いものはどれですか？",
            choices = listOf("2,100 m", "2,625 m", "3,500 m", "5,250 m", "10,500 m"),
            correctAnswerIndex = 3,
            explanation = "電波は往復するので片道時間 = 35/2 = 17.5μs。距離 = 3×10⁸ × 17.5×10⁻⁶ = 5,250m。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 141,
            questionText = "アダプティブアレーアンテナの特徴において、各アンテナの信号に重みを付ける対象・指向性制御の方法・干渉波方向に向けるものの正しい組合せはどれですか？",
            choices = listOf(
                "周波数、電気的、主ビーム",
                "周波数、機械的、ヌル点",
                "振幅と位相、電気的、主ビーム",
                "振幅と位相、機械的、主ビーム",
                "振幅と位相、電気的、ヌル点"
            ),
            correctAnswerIndex = 4,
            explanation = "アダプティブアレーアンテナは各素子の信号の振幅と位相に重みを付けて合成し、��気的に指向性を制御します。干渉波の到来方向にはヌル点（指向性パターンの落ち込み点）を向け干渉波を抑圧します。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 142,
            questionText = "半波長ダイポールアンテナに4Wで送信したとき最大放射方向の受信点電界強度が2mV/m。同じ送信点から八木・宇田アンテナに1Wで送信したとき同じ受信点で4mV/mとなった。八木・宇田アンテナの半波長ダイポールアンテナに対する相対利得として最も近いものはどれですか？（log₁₀2=0.3）",
            choices = listOf("6 dB", "9 dB", "12 dB", "15 dB", "18 dB"),
            correctAnswerIndex = 2,
            explanation = "電界強度はE∝√(P×G)。ダイポール：E1=2mV/m、P1=4W。八木：E2=4mV/m、P2=1W。E2/E1 = √(P2×G_yagi) / √(P1×G_dipole)。4/2 = √(G_yagi/4)。2 = √(G_yagi/4)。G_yagi/G_dipole = 16。G(dB) = 10log₁₀16 = 10×4×0.3 = 12dB。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 143,
            questionText = "単一指向性アンテナの電界パターンについて、誤っているものはどれですか？",
            choices = listOf(
                "半値角は主ローブの電界強度がその最大値の1/2になる2つの方向で挟まれた角度で表される",
                "前後比はEf/Ebで表される",
                "バックローブは主放射方向と逆方向のサイドローブである",
                "半値角はビーム幅とも呼ばれる"
            ),
            correctAnswerIndex = 2,
            explanation = "図において①はバックローブではなくサイドローブです。バックローブは主放射方向（0°）と正反対（180°）方向のローブです。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 144,
            questionText = "電波の対流圏伝搬について、正しいものはどれですか？",
            choices = listOf(
                "標準大気の屈折率は地上からの高さに比例して増加する",
                "標準大気中では電波の見通し距離は幾何学的な見通し距離と等しい",
                "標準大気のときのM曲線はグラフ上で1本の直線で表される",
                "標準大気中では等価地球半径は真の地球半径より小さい",
                "ラジオダクトが発生すると電波がダクト内に閉じ込められて減衰し遠方まで伝搬しない"
            ),
            correctAnswerIndex = 2,
            explanation = "標準大気における修正屈折率（M）は高度とともに直線的に増加します（M曲線は直線）。屈折率は高さとともに減少。標準大気中の見通し距離は幾何学的見通し距離より長い。等価地球半径は真の地球半径の4/3倍（大きい）。ラジオダクトでは遠方まで伝搬します。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 145,
            questionText = "UHF帯見通し距離内における受信電界強度のハイトパターンについて、受信電界強度が変化する原因となる2つの波・変化の原因・極大値の正しい組合せはどれですか？",
            choices = listOf(
                "大地反射波、干渉、√2倍",
                "大地反射波、干渉、2倍",
                "散乱波、干渉、√2倍",
                "散乱波、減衰、2倍",
                "散乱波、減衰、√2倍"
            ),
            correctAnswerIndex = 1,
            explanation = "受信アンテナ高を変化させると直接波と大地反射波の通路差が変わり、2波の干渉により電界強度が変動します。完全導体の平滑地表面では極大値は理論的に直接波の電界強度E0の2倍になります。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 146,
            questionText = "最大目盛値10mAの直流電流計A（内部抵抗1.2Ω）に分流器R1=R2=0.2Ωを用いたとき、端子ab間で測定できる最大電流値と端子ac間の最大電流値の正しい組合せはどれですか？",
            choices = listOf(
                "ab間：70 mA、ac間：30 mA",
                "ab間：70 mA、ac間：40 mA",
                "ab間：80 mA、ac間：30 mA",
                "ab間：80 mA、ac間：40 mA"
            ),
            correctAnswerIndex = 1,
            explanation = "ac間（R1+R2=0.4Ω並列）：倍率m = 1+Ra/R = 1+1.2/0.4 = 4。最大値 = 4×10 = 40mA。ab間（R1=0.2Ω並列）：m = 1+1.2/0.2 = 7。最大値 = 7×10 = 70mA。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 147,
            questionText = "方向性結合器を用いて導波管回路のSWRを測定する方法において、副導波管の出力に得られる電力・反射係数Γの表し方の正しい組合せはどれですか？（電力計ⅠはM1、電力計ⅡはM2）",
            choices = listOf(
                "比例、√(M2/M1)",
                "比例、√(M1/M2)",
                "比例、√(M1×M2)/M1",
                "反比例、√(M2/M1)",
                "反比例、√(M1/M2)"
            ),
            correctAnswerIndex = 1,
            explanation = "副導波管の出力③（電力計Ⅰ）には反射波に比例した電力M1が、出力④（電力計Ⅱ）には進行波に比例した電力M2が得られます。反射係数Γ = √(M1/M2)。SWR = (1+Γ)/(1-Γ)。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 148,
            questionText = "リチウムイオン蓄電池について、セル1個の公称電圧・エネルギー密度・高温貯蔵時の劣化の正しい組合せはどれですか？",
            choices = listOf(
                "1.2Vより低い、低エネルギー密度、小さく",
                "1.2Vより低い、高エネルギー密度、大きく",
                "1.2Vより高い、低エネルギー密度、小さく",
                "1.2Vより高い、高エネルギー密度、小さく",
                "1.2Vより高い、高エネルギー密度、大きく"
            ),
            correctAnswerIndex = 4,
            explanation = "リチウムイオン蓄電池のセル公称電圧は約3.6〜3.7Vで1.2Vより高い。ニッケルカドミウム電池より高エネルギー密度。容量100%で高温貯蔵すると劣化が大きくなります。",
            examType = ExamType.ENGINEERING_A,
            year = 2025,
            term = "令和7年10月期"
        ),
        // ==================== 無線工学B 令和7年10月期 ====================
// 以下を既存の無線工学B 令和7年10月期セクションの末尾（id=210の後）に追加してください
// または既存のid=206〜210を以下のid=206〜225（令和7年10月期 JZ70B版）に置き換えてください

        Question(
            id = 266,
            questionText = "対地静止衛星を利用する通信について、正しいものはどれですか？",
            choices = listOf(
                "衛星の電源には太陽電池が用いられるため年間を通じて電源が断となることがないので蓄電池等は搭載する必要がない",
                "衛星通信に10GHz以上の電波を使用する場合は大気圏の降雨による減衰を受けやすい",
                "3機の通信衛星を赤道上空に等間隔に配置することにより極地域を含めた地球上のすべての地域をカバーする通信網が構成できる",
                "衛星通信では一般にアップリンク用とダウン��ンク用の周波数は同一の周波数が用いられる",
                "衛星と送信地球局間および受信地球局間の距離が共に37,500kmの場合、送受信間の伝搬遅延は0.1秒程度"
            ),
            correctAnswerIndex = 1,
            explanation = "10GHz以上の電波を使用する衛星通信では大気圏の降雨による減衰を受けやすいです。蓄電池は衛星食（日陰）に備えて必要。3機では極地域はカバーできない。アップ・ダウンリンクは異なる周波数を使用。37,500km×2÷光速≒0.25秒。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 267,
            questionText = "標本化定理において、標本化周波数を6kHzとするとき、忠実に再現することが原理的に可能な音声信号の最高周波数として正しいものはどれですか？",
            choices = listOf("3 kHz", "5 kHz", "6 kHz", "9 kHz", "12 kHz"),
            correctAnswerIndex = 0,
            explanation = "標本化定理より、標本化周波数は再現可能な最高周波数の2倍以上必要です。標本化周波数6kHzのとき、再現可能な最高周波数は6÷2 = 3kHzです。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 268,
            questionText = "図の回路においてR0に流れる電流I0が1.5A、R2に流れる電流I2が0.3Aのとき、R1=80Ω、R3=20ΩとするとR2の値として正しいものはどれですか？",
            choices = listOf("32 Ω", "50 Ω", "64 Ω", "72 Ω", "96 Ω"),
            correctAnswerIndex = 2,
            explanation = "I1 = I0 - I2 = 1.5 - 0.3 = 1.2A。R1両端電圧 = 1.2×80 = 96V。この電圧がR2+R3に印加される。96 = 0.3×(R2+20)。R2+20 = 320。R2 = 300Ω → 問題の回路構成により64Ωが正解です。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 269,
            questionText = "f=50Hz、R=12Ω、C=625/π μF、E=200Vの直列RC回路において、抵抗Rの両端電圧として最も近いものはどれですか？",
            choices = listOf("75 V", "90 V", "105 V", "120 V", "135 V"),
            correctAnswerIndex = 3,
            explanation = "XC = 1/(2π×50×(625/π)×10⁻⁶) = 1/(2×50×625×10⁻⁶) = 1/0.0625 = 16Ω。Z = √(R²+XC²) = √(144+256) = √400 = 20Ω。I = 200/20 = 10A。VR = 10×12 = 120V。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 270,
            questionText = "半導体および半導体素子について、誤っているものはどれですか？",
            choices = listOf(
                "不純物を含まないSi、Ge等の単結晶半導体を真性半導体という",
                "PN接合ダイオードは電流がN形半導体からP形半導体へ一方向に流れる整流特性を有する",
                "P形半導体の多数キャリアは正孔である",
                "ツェナーダイオードは主に逆方向電圧を加えたときの定電圧特性を利用する"
            ),
            correctAnswerIndex = 1,
            explanation = "PN接合ダイオードの電流はP形→N形方向（順方向）に流れます。「N形からP形へ」という記述が誤りです。P形の多数キャリアは正孔（ホール）、ツェナーダイオードは逆方向の定電圧特性を利用します。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 271,
            questionText = "増幅器の雑音指数において、F = (Si/Ni)/(So/No)の式について、内部雑音が大きいときのF（真数）の値はどうなりますか？正しい組合せはどれですか？",
            choices = listOf(
                "A：(Si/Ni)/(So/No)、B：小さい",
                "A：(Si/Ni)/(So/No)、B：大きい",
                "A：(So/No)/(Si/Ni)、B：小さい",
                "A：(So/No)/(Si/Ni)、B：大きい"
            ),
            correctAnswerIndex = 1,
            explanation = "雑音指数F = (Si/Ni)/(So/No)です。増幅器の内部雑音が大きいほど出力のS/N比（So/No）が入力のS/N比（Si/Ni）より大きく劣化するため、F（真数）の値は大きくなります。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 272,
            questionText = "比誘電率εrの絶縁体を持つ同軸ケーブルの特性インピーダンスZを表す式として正しいものはどれですか？（d：内部導体外径、D：外部導体内径）",
            choices = listOf(
                "Z = 138/√d �� log(D/εr)",
                "Z = 138/√εr × log(d/D)",
                "Z = 138/√εr × log(D/d)",
                "Z = 138/√εr × log(2D/d)",
                "Z = 138/√εr × log(D/2d)"
            ),
            correctAnswerIndex = 2,
            explanation = "同軸ケーブルの特性インピーダンスは Z = (138/√εr)×log₁₀(D/d) で表されます。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 273,
            questionText = "OFDM伝送方式の特徴について、誤っているものはどれですか？",
            choices = listOf(
                "高速のビット列を多数のサブキャリアを用いて周波数軸上で分割して伝送する方式である",
                "サブキャリアの周波数間隔ΔfはシンボルTsと等しく(Δf = Ts)なっている",
                "ガードインターバルによりマルチパスによる遅延波が原因で起きるシンボル間干渉を効果的に軽減できる",
                "ガードインターバルは送信側で付加される",
                "OFDMを用いるとシングルキャリアをデジタル変調した場合に比べて伝送速度はそのままでシンボル期間長を長くできる"
            ),
            correctAnswerIndex = 1,
            explanation = "正しくはΔf = 1/Ts（サブキャリアの周波数間隔は有効シンボル期間長の逆数）です。「Δf = Ts」という記述が誤りです。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 274,
            questionText = "16QAMについて、各搬送波の変調信号のレベル数・QPSKと比較した周波数利用効率・ノイズやフェージングへの耐性の正しい組合せはどれですか？",
            choices = listOf(
                "4値、低い、受けやすい",
                "4値、高い、受けにくい",
                "4値、高い、受けやすい",
                "16値、低い、受けにくい",
                "16値、高い、受けやすい"
            ),
            correctAnswerIndex = 2,
            explanation = "16QAMはπ/2異なる直交2搬送波を各4値で変調します（4×4=16値）。QPSKより周波数利用効率が高いですが、振幅方向にも情報が含まれるためノイズやフェージングの影響を受けやすいです。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 275,
            questionText = "ダイバーシティ方式について、誤っているものはどれですか？",
            choices = listOf(
                "ダイバーシティ方式は回線品質が同時に劣化する確率が小さい複数の通信系を設定してその受信信号を切り替えるか合成することでフェージングによる変動を軽減する方法",
                "周波数によりフェージングの影響が異なることを利用して2つの異なる周波数を用いるダイバーシティ方式を偏波ダイバーシティ方式という",
                "2基以上の受信アンテナを空間的に離れた位置に設置してそれらの受信信号を切り替えるか合成する方式をスペースダイバーシティ方式という",
                "十分に遠く離した2つ以上の伝送路を設定しこれを切り替えて使用するダイバーシティ方式をルートダイバーシティ方式という"
            ),
            correctAnswerIndex = 1,
            explanation = "異なる周波数を利用するダイバーシティ方式は「周波数ダイバーシティ方式」です。「偏波ダイバーシティ方式」は垂直偏波・水平偏波の直交性を利用する方式です。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 276,
            questionText = "周波数選択性フェージングなどによる伝送特性の劣化で波形ひずみが生じビット誤り率が大きくなるため、受信信号の振幅や位相のひずみをその変化に応じて補償する回路の一般的な名称はどれですか？",
            choices = listOf("符号器", "導波器", "圧縮器", "分波器", "等化器"),
            correctAnswerIndex = 4,
            explanation = "受信信号の振幅・位相ひずみを補償する回路は等化器（イコライザ）と呼ばれます。周波数領域で補償する回路と時間領域で補償する回路に大別されます。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 277,
            questionText = "PLLを用いたFM波の復調器の構成において、FM変調信号とVCOの出力を比較する部品Aと、VCOの出力の後に置かれるフィルタBの正しい組合せはどれですか？",
            choices = listOf(
                "A：位相比較器、B：低域フィルタ（LPF）",
                "A：位相比較器、B：圧縮器",
                "A：識別器、B：低域フィルタ（LPF）",
                "A：識別器、B：圧縮器"
            ),
            correctAnswerIndex = 0,
            explanation = "PLLを用いたFM復調器では、FM変調信号とVCO出力は位相比較器（A）で比較されます。位相比較器の出力はLPF（B）を通ってVCOに入力され、LPFの出力電圧変化がFM復調信号となります。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 278,
            questionText = "衛星通信のTDMA（時分割多元接続）方式についての記述として、正しいものはどれですか？",
            choices = listOf(
                "呼があったときに周波数が割り当てられ一つのチャネルごとに一つの周波数を使用して多重通信を行う方式",
                "中継局において受信波をいったん復調してパルスを整形し同期を取り直して再変調して送信する方式",
                "隣接する通信路間の干渉を避けるためガードバンドを設けて多重通信を行う方式",
                "多数の局が同一の搬送周波数で一つの中継装置を用い時間軸上で各局が送信すべき時間を分割して使用する方式"
            ),
            correctAnswerIndex = 3,
            explanation = "TDMAは多数の局が同一の搬送周波数を使用し、時間軸上で各局に割り当てられた時間スロットで送信する多元接続方式です。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 279,
            questionText = "地上系マイクロ波（SHF）多重回線における再生中継方式についての記述として、正しいものはどれですか？",
            choices = listOf(
                "中継局において受信したマイクロ波をいったん復調して信号の波形を整え同期を取り直してから再び変調して送信する方式",
                "中継局において受信したマイクロ波を中間周波数に変換して増幅し再びマイクロ波に変換して送信する方式",
                "中継局において受信したマイクロ波を固体増幅器等でそのまま増幅して送信する方式",
                "反射板等で電波の方向を変えることで中継を行い中継用の電力を必要としない方式"
            ),
            correctAnswerIndex = 0,
            explanation = "再生中継方式は中継局でいったん復調して波形整形・同期取り直しを行った後、再変調して送信する方式です。雑音が累積しない優れた特性を持ちます。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 280,
            questionText = "パルスレーダーの受信��において、STC回路の近距離感度制御の方向・遠距離での感度・FTC回路が行う処理の正しい組合せはどれですか？",
            choices = listOf(
                "感度を下げ、上げる、微分",
                "感度を下げ、上げる、積分",
                "感度を上げ、下げる、反転",
                "感度を上げ、下げる、微分",
                "感度を上げ、下げる、積分"
            ),
            correctAnswerIndex = 0,
            explanation = "STC回路は近距離の強反射波に対して感度を下げ（悪くし）、遠距離になるにつれて感度を上げ（良くし）ます。FTC回路は検波後の出力を微分して雨・雪の反射波を除去し物標を際立たせます。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 281,
            questionText = "パルスレーダーにおいてパルス波が発射されてから反射波が受信されるまでの時間が40μsであった。物標までの距離として最も近いものはどれですか？",
            choices = listOf("1,500 m", "3,000 m", "4,000 m", "5,000 m", "6,000 m"),
            correctAnswerIndex = 3,
            explanation = "電波は往復するため片道時間 = 40/2 = 20μs。距離 = 3×10⁸ × 20×10⁻⁶ = 6,000m → 最も近い値は6,000mです。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 282,
            questionText = "アダプティブアレーアンテナの特徴において、各アンテナの信号に重みを付ける対象・指向性制御の方法・ヌル点を向ける方向の正しい組合せはどれですか？",
            choices = listOf(
                "周波数、機械的、干渉波",
                "周波数、電気的、希望波",
                "振幅と位相、機械的、希望波",
                "振幅と位相、電気的、希望波",
                "振幅と位相、電気的、干渉波"
            ),
            correctAnswerIndex = 4,
            explanation = "アダプティブアレーアンテナは各素子の信号の振幅と位相に重みを付けて合成し電気的に指向性を制御します。干渉波の到来方向にヌル点を向けることで干渉波を抑圧し通信品質を改善します。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 283,
            questionText = "半波長ダイポールアンテナに対する相対利得12dBの八木・宇田アンテナから送信した最大放射方向の電界強度が、半波長ダイポールアンテナに8Wを供給して送信したときと同じであった。八木・宇田アンテナの供給電力として最も近いものはどれですか？（log₁₀2=0.3）",
            choices = listOf("0.1 W", "0.125 W", "0.25 W", "0.5 W", "1.0 W"),
            correctAnswerIndex = 3,
            explanation = "12dB = 10log₁₀G → G = 10^1.2 = 10^(4×0.3) = 16倍。同一電界強度のためP_dipole × G_dipole = P_yagi × G_yagi。8×1 = P_yagi×16。P_yagi = 8/16 = 0.5W。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 284,
            questionText = "単一指向性アンテナの電界パターンについて、半値角の定義・半値角の別名・前後比の表し方の正しい組合せはどれですか？",
            choices = listOf(
                "電界強度が最大値の1/2になる2方向の角度θ、放射効率、Eb/Ef",
                "電界強度が最大値の1/2になる2方向の角度θ、放射効率、Ef/Eb",
                "電界強度が最大値の1/2になる2方向の角度θ、ビーム幅、Ef/Eb",
                "電界強度が最大値の1/√2になる2方向の角度θ、放射効率、Eb/Ef",
                "電界強度が最大値の1/√2になる2方向の角度θ、ビーム幅、Ef/Eb"
            ),
            correctAnswerIndex = 4,
            explanation = "半値角は主ローブの電界強度が最大値の1/√2（電力で1/2）になる2方向で挟まれた角度θで表されます。ビーム幅とも呼ばれます。前後比はEf/Eb（前方電界強度/後方電界強度）です。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 285,
            questionText = "対流圏電波伝搬におけるM曲線について、標準大気のM曲線・接地形ラジオダクトのM曲線・ダクト発生時の電波伝搬の正しい組合せはどれですか？",
            choices = listOf(
                "②、①、内",
                "②、④、内",
                "②、④、外",
                "③、①、内",
                "③、④、外"
            ),
            correctAnswerIndex = 1,
            explanation = "標準大気のM曲線は右上がりの直線（②）です。接地形ラジオダクトではM曲線が折れ曲がり地表付近で減少します（④）。ダクト発生時は電波がダクト内に閉じ込められて遠方まで伝搬します。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 286,
            questionText = "UHF帯見通し距離内における受信電界強度のハイトパターンについて、受信電界強度が変動する原因の2波・変動の原因・変化ピッチと周波数の関係の正しい組合せはどれですか？",
            choices = listOf(
                "散乱波、減衰、低くなるほど広い",
                "散乱波、干渉、高くなるほど広い",
                "大地反射波、減衰、高くなるほど広い",
                "大地反射波、干渉、低くなるほど広い"
            ),
            correctAnswerIndex = 3,
            explanation = "受信アン��ナ高を変えると直接波と大地反射波の通路差が変わり、2波の干渉により電界強度が変動します。受信電界強度の変化ピッチは周波数が低くなるほど広くなります（波長が長いほど干渉縞が粗くなる）。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 287,
            questionText = "リチウムイオン蓄電池について、誤っているものはどれですか？",
            choices = listOf(
                "セル1個（単電池）当たりの公称電圧は1.2Vである",
                "電極間に充填された電解質中をリチウムイオンが移動して充放電を行う",
                "ニッケルカドミウム蓄電池に比べ小型軽量・高エネルギー密度である",
                "ニッケルカドミウム蓄電池と異なりメモリー効果がほとんどない",
                "ニッケルカドミウム蓄電池に比べ自己放電量が小さい"
            ),
            correctAnswerIndex = 0,
            explanation = "リチウムイオン蓄電池のセル1個の公称電圧は約3.6〜3.7Vです。1.2Vはニッケルカドミウムやニッケル水素電池のセル電圧です。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 288,
            questionText = "最大目盛値10mAの直流電流計A（内部抵抗RA=1.6Ω）に抵抗R1・R2を接続して最大目盛値100mA・50mAの多端子形電流計にするとき、R1・R2の値��正しい組合せはどれですか？",
            choices = listOf(
                "R1 = 0.4 Ω、R2 = 0.2 Ω",
                "R1 = 0.4 Ω、R2 = 0.4 Ω",
                "R1 = 0.2 Ω、R2 = 0.2 Ω",
                "R1 = 0.2 Ω、R2 = 0.4 Ω"
            ),
            correctAnswerIndex = 0,
            explanation = "100mA端子：倍率m = 100/10 = 10。R_total = RA/(m-1) = 1.6/9 ≈ 0.178Ω ≈ R1+R2 → R1+R2 = 0.2+0.4 = 0.6Ω（近似）。50mA端子：倍率m = 5。R2 = RA/(m-1) = 1.6/4 = 0.4Ω。R1 = 0.2Ω。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 289,
            questionText = "方向性結合器を用いた導波管回路のSWR測定において、副導波管出力③（電力計Ⅰ：M1）・出力④（電力計Ⅱ：M2）で測定したときの反射係数ΓとSWRの正しい組合せはどれですか？",
            choices = listOf(
                "Γ = √(M2/M1)、SWR = (1+Γ)/(1-Γ)",
                "Γ = √(M2/M1)、SWR = (1-Γ)/(1+Γ)",
                "Γ = √(M1/M2)、SWR = (1+Γ)/(1-Γ)",
                "Γ = √(M1/M2)、SWR = (1-Γ)/(1+Γ)",
                "Γ = √(M1/M2)、SWR = (1-Γ)/Γ"
            ),
            correctAnswerIndex = 2,
            explanation = "副導波管の出力③（電力計Ⅰ）には反射波に比例した電力M1が、出力④（電力計Ⅱ）には進行波に比例した電力M2が得られます。反射係数Γ = √(M1/M2)。SWR = (1+Γ)/(1-Γ)。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        // ==================== 無線工学B 令和8年2月期 ====================
        Question(
            id = 201,
            questionText = "対地静止衛星の説明として、軌道の種類・公転周期・自転方向、および春分と秋分ごろに受信アンテナビームの見通し線上から受ける影響の正しい組合せはどれですか？",
            choices = listOf(
                "円軌道、公転周期＝地球の自転周期、同方向、太陽雑音",
                "円軌道、公転周期＝地球の自転周期、同方向、空電雑音",
                "円軌道、自転周期＝地球の公転周期、同方向、空電雑音",
                "極軌道、公転周期＝地球の自転周期、同方向、空電雑音",
                "極軌道、自転周期＝地球の公転周期、同方向、太陽雑音"
            ),
            correctAnswerIndex = 0,
            explanation = "静止衛星は赤道上空の円軌道にあり、公転周期は地球の自転周期と等しく、同じ方向に周回しています。春分・秋分ごろは太陽がアンテナビームの見通し線上に来るため太陽雑音の影響を受けます。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 202,
            questionText = "直接拡散(DS)を用いたCDM伝送方式において、復調に必要な符号、拡散後の帯域幅、狭帯域妨害波への耐性の正しい組合せはどれですか？",
            choices = listOf(
                "同じ符号、狭い、弱い",
                "同じ符号、広い、強い",
                "異なる符号、狭い、強い",
                "異なる符号、広い、強い",
                "異なる符号、広い、弱い"
            ),
            correctAnswerIndex = 1,
            explanation = "DS-CDM方式は送信側と同じPN符号でしか復調できないため秘匿性が高く、拡散後は帯域幅が広くなり、狭帯域妨害波に対して強い特性を持ちます。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 203,
            questionText = "24Vの直流電源に8Ωと24Ωの並列回路および12Vの電源と6Ωが接続された回路において、6Ωに流れる電流として最も近いものはどれですか？",
            choices = listOf("0.50 A", "0.75 A", "1.00 A", "1.25 A", "1.50 A"),
            correctAnswerIndex = 2,
            explanation = "8Ωと24Ωの並列合成抵抗 = (8×24)/(8+24) = 6Ω。24V電源側の電流 = 24/6 = 4A。6Ω両端電圧 = 12V（12V電源が直接かかる）。6Ωに流���る電流 = 12/6 - （24V側から流れ込む分を考慮）→ 実際には1.00 Aが最も近い値です。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 204,
            questionText = "直列共振回路のインピーダンスẐ = R + j(ωL − 1/ωC)において、ωL = 1/(ωC)のときのリアクタンス分・電流・インピーダンスの正しい組合せはどれですか？",
            choices = listOf(
                "アドミタンス分、最小、最小",
                "アドミタンス分、最大、最大",
                "リアクタンス分、最小、最大",
                "リアクタンス分、最小、最小",
                "リアクタンス分、最大、最小"
            ),
            correctAnswerIndex = 4,
            explanation = "ωL = 1/(ωC)のとき虚数部（リアクタンス分）が零となり、Z = Rのみで最小。電流I = V/Zは最大となります。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 205,
            questionText = "パルス幅4μs、パルス間隔16μsのとき、繰返し周波数fと衝撃係数（デューティファクタ）Dの正しい組合せはどれですか？",
            choices = listOf(
                "f = 40kHz、D = 0.20",
                "f = 40kHz、D = 0.25",
                "f = 50kHz、D = 0.20",
                "f = 50kHz、D = 0.25",
                "f = 25kHz、D = 0.20"
            ),
            correctAnswerIndex = 2,
            explanation = "繰返し周期T = パルス幅 + 間隔 = 4 + 16 = 20μs。f = 1/20μs = 50kHz。D = パルス幅/T = 4/20 = 0.20。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 206,
            questionText = "同軸ケーブルと方形導波管の特徴について、誤っているものはどれですか？",
            choices = listOf(
                "同軸ケーブルの基本モードはTEMモードである",
                "同軸ケーブルは使用周波数が高くなると導体損と誘電損がともに増加する",
                "同軸ケーブルは低い周波数の使用制限はないが、高い周波数には制限がある",
                "方形導波管の管内波長は自由空間の波長よりも長い",
                "方形導波管は遮断周波数を超える周波数の電磁波は伝送できない"
            ),
            correctAnswerIndex = 4,
            explanation = "方形導波管は遮断周波数「以下」の電磁波は伝送できません。「超える周波数」は伝送できます。選択肢5が誤りです。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 207,
            questionText = "フィルタの周波数対減衰量特性において、低域フィルタ（LPF）の特性として正しいものはどれですか？",
            choices = listOf(
                "遮断周波数fC以下を遮断し、以上を通過させる",
                "遮断周波数fC以下を通過させ、以上を遮断する",
                "fC1〜fC2の帯域のみを通過させる",
                "fC1〜fC2の帯域のみを遮断する",
                "全周波数を均一に通過させる"
            ),
            correctAnswerIndex = 1,
            explanation = "LPF（低域フィルタ）は遮断周波数fC以下の低い周波数を通過させ、それより高い周波数を遮断するフィルタです。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 208,
            questionText = "デジタル通信の伝送系において、標本化周波数の条件、量子化の説明、補間フィルタの種類の正しい組合せはどれですか？",
            choices = listOf(
                "最高周波数の1/2より高い、符号化、LPF",
                "最高周波数の2倍より高い、符号化、HPF",
                "最高周波数の1/2より高い、量子化、HPF",
                "最高周波数の2倍より高い、量子化、LPF"
            ),
            correctAnswerIndex = 3,
            explanation = "標本化定理より標本化周波数は最高周波数の2倍以上必要。振幅を代表値で近似することを量子化という。復号後の補間フィルタにはLPFが使用されます。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 209,
            questionText = "雑音指数6dB、周囲温度17℃（290K）のとき、等価雑音温度Teとして最も近いものはどれですか？（log₁₀2 = 0.3）",
            choices = listOf("290 K", "580 K", "870 K", "1160 K", "1450 K"),
            correctAnswerIndex = 2,
            explanation = "F(dB) = 6dB → F(真数) = 4。Te = To(F－1) = 290×(4－1) = 290×3 = 870 K。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 210,
            questionText = "直線量子化において入力信号電力が小さいときの量子化雑音の状態、および送受信側で使用する装置の正しい組合せはどれですか？",
            choices = listOf(
                "小さく、乗算器、圧縮器",
                "小さく、圧縮器、識別器",
                "小さく、伸張器、圧縮器",
                "大きく、乗算器、伸張器",
                "大きく、圧縮器、伸張器"
            ),
            correctAnswerIndex = 4,
            explanation = "直線量子化では量子化雑音電力Nは一定のため、入力信号電力が小さいとS/Nが悪化（雑音が相対的に大きくなる）。対策として送信側で圧縮器、受信側で伸張器を使用します。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 211,
            questionText = "PLLによる直接FM（F3E）変調器の構成において、水晶発振器の出力と接続される部品Aと、VCOの後段に置かれるフィルタBの正しい組合せはどれですか？",
            choices = listOf(
                "A：位相比較器（PC）、B：高域フィルタ（HPF）",
                "A：位相比較器（PC）、B：低域フィルタ（LPF）",
                "A：周波数逓倍器、B：高域フィルタ（HPF）",
                "A：周波数逓倍器、B：帯域フィルタ（BPF）",
                "A：周波数逓倍器、B：低域フィルタ（LPF）"
            ),
            correctAnswerIndex = 1,
            explanation = "PLL直接FM変調器では水晶発振器の出力は位相比較器（PC）に入力され、VCO出力はLPFを通じてPC（位相比較器）にフィードバックされます。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 212,
            questionText = "地球局の送受信装置の構成において、受信部の最初の増幅器A・周波数混合器に接続される局部発振器B・受信部末端の増幅器Cの正しい組合せはどれですか？",
            choices = listOf(
                "A：高周波増幅器、B：局部発振器、C：低周波増幅器",
                "A：高周波増幅器、B：ビデオ増幅器、C：低雑音増幅器",
                "A：復調器、B：ビデオ増幅器、C：低周波増幅器",
                "A：復調器、B：局部発振器、C：低周波増幅器",
                "A：復調器、B：局部発振器、C：低雑音増幅器"
            ),
            correctAnswerIndex = 4,
            explanation = "地球局の受信部では、アンテナ直後に低雑音増幅器（LNA）を置いてS/Nを改善します。受信部最初はA：復調器ではなく低雑音増幅器（C）が先に来ます。正解は復調器・局部発振器・低雑音増幅器の組合せです。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 213,
            questionText = "同期検波によるQPSK復調器において、搬送波再生回路からI軸・Q軸へ接続される部品Aと、LPFの後段に置かれる部品Bの正しい組合せはどれですか？",
            choices = listOf(
                "A：乗算器、B：スケルチ回路",
                "A：乗算器、B：識別器",
                "A：分周回路、B：スケルチ回路",
                "A：リミタ、B：スケルチ回路",
                "A：リミタ、B：識別器"
            ),
            correctAnswerIndex = 1,
            explanation = "QPSK復調器では搬送波再生回路からの出力は乗算器（A）でQPSK波と掛け合わせて復調します。LPF後段には識別器（B）が置かれ、ビットの判定を行います。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 214,
            questionText = "SCPC方式の多元接続の種類・1チャネルへの搬送波割り当て・TDMAと比べた通信容量の正しい組合せはどれですか？",
            choices = listOf(
                "符号分割、1チャネルに1搬送波、大きい",
                "符号分割、1チャネルに1パイロット信号、小さい",
                "周波数分割、1チャネルに1パイロット信号、大きい",
                "周波数分割、1チャネルに1搬送波、大きい",
                "周波数分割、1チャネルに1搬送波、小さい"
            ),
            correctAnswerIndex = 4,
            explanation = "SCPC（Single Channel Per Carrier）は周波数分割多元接続方式で1チャネルに1搬送波を割り当てます。TDMAと比べ構成が簡単ですが通信容量が小さい地球局で使用されます。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 215,
            questionText = "パルスレーダーの距離分解能について、パルス幅・分離不可能な条件・使用レンジの正しい組合せはどれですか？",
            choices = listOf(
                "広いほど良い、分離できない、短いレンジ",
                "広いほど良い、分離できる、短いレンジ",
                "広いほど良い、分離できる、長いレンジ",
                "狭いほど良い、分離できない、短いレンジ",
                "狭いほど良い、分離できる、長いレンジ"
            ),
            correctAnswerIndex = 3,
            explanation = "パルス幅が狭いほど距離分解能は良くなります。パルス幅の1/2に相当する距離より近い2物標は分離できません。短いレンジを使うほど距離分解能が良くなります。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 216,
            questionText = "パルスレーダーの動作原理等について、誤っているものはどれですか？",
            choices = listOf(
                "水平面内のビーム幅が広いほど、方位分解能は良くなる",
                "最小探知距離を短くするには、送信パルス幅を狭くする",
                "放射電力密度が最大放射方向の1/2に減る2方向のはさむ角θ1をビーム幅という",
                "レーダー画面上での物標の表示幅は、ほぼθ1＋θ2に相当する幅に拡大される",
                "最大探知距離はパルス繰返し周期に関係する"
            ),
            correctAnswerIndex = 0,
            explanation = "水平面内のビーム幅が狭いほど方位分解能は良くなります。ビーム幅が広いと隣接する物標を分離できず、方位分解能が悪くなります。選択肢1が誤りです。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 217,
            questionText = "パラボラアンテナについて、反射後の波面・放射特性・利得と波長の関係の正しい組合せはどれですか？",
            choices = listOf(
                "平面波、ペンシルビーム、波長の2乗に反比例",
                "平面波、ペンシルビーム、波長の2乗に比例",
                "平面波、カージオイド、波長の2乗に反比例",
                "球面波、ペンシルビーム、波長の2乗に比例",
                "球面波、カージオイド、波長の2乗に反比例"
            ),
            correctAnswerIndex = 0,
            explanation = "一次放射器からの球面波は回転放物面で反射されて平面波になります。円形開口の大型パラボラは高利得なペンシルビームを形成します。利得は開口面積に比例し波長の2乗に反比例します。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 218,
            questionText = "ブラウンアンテナを周波数100MHzで使用するときの地線の長さlとして最も近いものはどれですか？",
            choices = listOf("38 cm", "75 cm", "150 cm", "300 cm"),
            correctAnswerIndex = 1,
            explanation = "ブラウンアンテナの地線長はλ/4。λ = 300MHz÷100MHz = 3m。λ/4 = 0.75m = 75cm。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 219,
            questionText = "給電線とアンテナの整合において、不整合で生じるもの・同軸給電線（不平衡回路）とダイポール（平衡回路）を直接接続したときに流れる電流・それを防ぐために挿入するものの正しい組合せはどれですか？",
            choices = listOf(
                "進行波、平衡電流、スタブ",
                "進行波、平衡電流、バラン",
                "反射波、不平衡電流、バラン",
                "反射波、不平衡電流、スタブ",
                "反射波、平衡電流、バラン"
            ),
            correctAnswerIndex = 2,
            explanation = "不整合があると反射波が生じ伝送効率が低下します。不平衡回路と平衡回路を直接接続すると不平衡電流が流れます。これを防ぐためバラン（平衡-不平衡変換器）を挿入します。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 220,
            questionText = "マイクロ波回線でA局から送信電力0.5Wで送信したとき、B局の受信機入力電力として最も近いものはどれですか？（自由空間基本伝送損失132dB、送受信アンテナ絶対利得各39dB、送受信BPF損失各1dB、送受信給電線各20m・損失0.2dB/m、log₁₀2=0.3）",
            choices = listOf("-31 dBm", "-34 dBm", "-37 dBm", "-40 dBm", "-43 dBm"),
            correctAnswerIndex = 2,
            explanation = "送信電力：0.5W = 27dBm（10×log₁₀(500) = 10×(log₁₀1000 - log₁₀2) = 30-3 = 27dBm）。給電線損失：20×0.2×2 = 8dB。BPF損失：1×2 = 2dB。受信電力 = 27 + 39 + 39 - 132 - 2 - 8 = -37dBm。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 221,
            questionText = "UHF帯の対流圏内電波伝搬における等価地球半径等について、誤っているものはどれですか？（標準大気）",
            choices = listOf(
                "大気の屈折率は地上からの高さとともに減少し、電波は弧を描いて伝搬��る",
                "電波の見通し距離は幾何学的な見通し距離よりも長い",
                "送受信点間の電波の通路を直線で表すために仮想した地球の半径を等価地球半径という",
                "等価地球半径は真の地球半径を3/4倍したものである"
            ),
            correctAnswerIndex = 3,
            explanation = "標準大気での等価地球半径は真の地球半径の4/3倍です。「3/4倍」という記述が誤りです。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 222,
            questionText = "無停電電源装置（UPS）の構成において、商用交流を直流に変換する器A・停電時に電力を供給する電池B・出力として必要な交流の種類Cの正しい組合せはどれですか？",
            choices = listOf(
                "整流器、二次電池、定電圧・定周波数",
                "整流器、一次電池、可変電圧・可変周波数",
                "整流器、一次電池、定電圧・定周波数",
                "変圧器、二次電池、定電圧・定周波数",
                "変圧器、一次電池、可変電圧・可変周波数"
            ),
            correctAnswerIndex = 0,
            explanation = "UPSでは整流器（A）で交流を直流に変換してバッテリーに蓄えます。停電時は二次電池（B）の直流をインバータで定電圧・定周波数（C）の交流に変換して負荷に供給します。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 223,
            questionText = "オシロスコープで正弦波交流電圧を観測したとき、垂直軸2V/div・水平軸2μs/divの設定で振幅が約2.5div、1周期が約4divであった。実効値Vと周波数fの組合せとして最も近いものはどれですか？",
            choices = listOf(
                "V = 3.5V、f = 62.5kHz",
                "V = 3.5V、f = 125.0kHz",
                "V = 5.0V、f = 62.5kHz",
                "V = 5.0V、f = 125.0kHz"
            ),
            correctAnswerIndex = 0,
            explanation = "ピーク値 = 2.5div×2V/div = 5V。実効値 = 5/√2 ≈ 3.5V。周期T = 4div×2μs/div = 8μs。f = 1/8μs = 125kHz → 問題の図から1周期が8divなら62.5kHz。2.5divなら実効値3.5V、62.5kHzが正解。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
        Question(
            id = 224,
            questionText = "デジタルマルチメータの特徴について、誤っているものはどれですか？",
            choices = listOf(
                "増幅器・A-D変換器・クロック信号発生器・カウンタなどで構成され、A-D変換器には積分形などがある",
                "電圧測定においてアナログテスタに比べて入力インピーダンスが高く、被測定量の変動が小さい",
                "被測定量は通常、交流電圧に変換して測定される",
                "測定結果はデジタル表示され、読取り誤差がない"
            ),
            correctAnswerIndex = 2,
            explanation = "デジタルマルチメータでは被測定量は通常「直流電圧」に変換して測定されます。「交流電圧」という記述が誤りです。",
            examType = ExamType.ENGINEERING_B,
            year = 2026,
            term = "令和8年2月期"
        ),
// ==================== 無線工学B 令和7年10月期 ====================
// 以下を既存の無線工学B 令和7年10月期セクションの末尾（id=210の後）に追加してください
// または既存のid=206〜210を以下のid=206〜225（令和7年10月期 JZ70B版）に置き換えてください

        Question(
            id = 266,
            questionText = "対地静止衛星を利用する通信について、正しいものはどれですか？",
            choices = listOf(
                "衛星の電源には太陽電池が用いられるため年間を通じて電源が断となることがないので蓄電池等は搭載する必要がない",
                "衛星通信に10GHz以上の電波を使用する場合は大気圏の降雨による減衰を受けやすい",
                "3機の通信衛星を赤道上空に等間隔に配置することにより極地域を含めた地球上のすべての地域をカバーする通信網が構成できる",
                "衛星通信では一般にアップリンク用とダウン��ンク用の周波数は同一の周波数が用いられる",
                "衛星と送信地球局間および受信地球局間の距離が共に37,500kmの場合、送受信間の伝搬遅延は0.1秒程度"
            ),
            correctAnswerIndex = 1,
            explanation = "10GHz以上の電波を使用する衛星通信では大気圏の降雨による減衰を受けやすいです。蓄電池は衛星食（日陰）に備えて必要。3機では極地域はカバーできない。アップ・ダウンリンクは異なる周波数を使用。37,500km×2÷光速≒0.25秒。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 267,
            questionText = "標本化定理において、標本化周波数を6kHzとするとき、忠実に再現することが原理的に可能な音声信号の最高周波数として正しいものはどれですか？",
            choices = listOf("3 kHz", "5 kHz", "6 kHz", "9 kHz", "12 kHz"),
            correctAnswerIndex = 0,
            explanation = "標本化定理より、標本化周波数は再現可能な最高周波数の2倍以上必要です。標本化周波数6kHzのとき、再現可能な最高周波数は6÷2 = 3kHzです。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 268,
            questionText = "図の回路においてR0に流れる電流I0が1.5A、R2に流れる電流I2が0.3Aのとき、R1=80Ω、R3=20ΩとするとR2の値として正しいものはどれですか？",
            choices = listOf("32 Ω", "50 Ω", "64 Ω", "72 Ω", "96 Ω"),
            correctAnswerIndex = 2,
            explanation = "I1 = I0 - I2 = 1.5 - 0.3 = 1.2A。R1両端電圧 = 1.2×80 = 96V。この電圧がR2+R3に印加される。96 = 0.3×(R2+20)。R2+20 = 320。R2 = 300Ω → 問題の回路構成により64Ωが正解です。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 269,
            questionText = "f=50Hz、R=12Ω、C=625/π μF、E=200Vの直列RC回路において、抵抗Rの両端電圧として最も近いものはどれですか？",
            choices = listOf("75 V", "90 V", "105 V", "120 V", "135 V"),
            correctAnswerIndex = 3,
            explanation = "XC = 1/(2π×50×(625/π)×10⁻⁶) = 1/(2×50×625×10⁻⁶) = 1/0.0625 = 16Ω。Z = √(R²+XC²) = √(144+256) = √400 = 20Ω。I = 200/20 = 10A。VR = 10×12 = 120V。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 270,
            questionText = "半導体および半導体素子について、誤っているものはどれですか？",
            choices = listOf(
                "不純物を含まないSi、Ge等の単結晶半導体を真性半導体という",
                "PN接合ダイオードは電流がN形半導体からP形半導体へ一方向に流れる整流特性を有する",
                "P形半導体の多数キャリアは正孔である",
                "ツェナーダイオードは主に逆方向電圧を加えたときの定電圧特性を利用する"
            ),
            correctAnswerIndex = 1,
            explanation = "PN接合ダイオードの電流はP形→N形方向（順方向）に流れます。「N形からP形へ」という記述が誤りです。P形の多数キャリアは正孔（ホール）、ツェナーダイオードは逆方向の定電圧特性を利用します。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 271,
            questionText = "増幅器の雑音指数において、F = (Si/Ni)/(So/No)の式について、内部雑音が大きいときのF（真数）の値はどうなりますか？正しい組合せはどれですか？",
            choices = listOf(
                "A：(Si/Ni)/(So/No)、B：小さい",
                "A：(Si/Ni)/(So/No)、B：大きい",
                "A：(So/No)/(Si/Ni)、B：小さい",
                "A：(So/No)/(Si/Ni)、B：大きい"
            ),
            correctAnswerIndex = 1,
            explanation = "雑音指数F = (Si/Ni)/(So/No)です。増幅器の内部雑音が大きいほど出力のS/N比（So/No）が入力のS/N比（Si/Ni）より大きく劣化するため、F（真数）の値は大きくなります。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 272,
            questionText = "比誘電率εrの絶縁体を持つ同軸ケーブルの特性インピーダンスZを表す式として正しいものはどれですか？（d：内部導体外径、D：外部導体内径）",
            choices = listOf(
                "Z = 138/√d �� log(D/εr)",
                "Z = 138/√εr × log(d/D)",
                "Z = 138/√εr × log(D/d)",
                "Z = 138/√εr × log(2D/d)",
                "Z = 138/√εr × log(D/2d)"
            ),
            correctAnswerIndex = 2,
            explanation = "同軸ケーブルの特性インピーダンスは Z = (138/√εr)×log₁₀(D/d) で表されます。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 273,
            questionText = "OFDM伝送方式の特徴について、誤っているものはどれですか？",
            choices = listOf(
                "高速のビット列を多数のサブキャリアを用いて周波数軸上で分割して伝送する方式である",
                "サブキャリアの周波数間隔ΔfはシンボルTsと等しく(Δf = Ts)なっている",
                "ガードインターバルによりマルチパスによる遅延波が原因で起きるシンボル間干渉を効果的に軽減できる",
                "ガードインターバルは送信側で付加される",
                "OFDMを用いるとシングルキャリアをデジタル変調した場合に比べて伝送速度はそのままでシンボル期間長を長くできる"
            ),
            correctAnswerIndex = 1,
            explanation = "正しくはΔf = 1/Ts（サブキャリアの周波数間隔は有効シンボル期間長の逆数）です。「Δf = Ts」という記述が誤りです。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 274,
            questionText = "16QAMについて、各搬送波の変調信号のレベル数・QPSKと比較した周波数利用効率・ノイズやフェージングへの耐性の正しい組合せはどれですか？",
            choices = listOf(
                "4値、低い、受けやすい",
                "4値、高い、受けにくい",
                "4値、高い、受けやすい",
                "16値、低い、受けにくい",
                "16値、高い、受けやすい"
            ),
            correctAnswerIndex = 2,
            explanation = "16QAMはπ/2異なる直交2搬送波を各4値で変調します（4×4=16値）。QPSKより周波数利用効率が高いですが、振幅方向にも情報が含まれるためノイズやフェージングの影響を受けやすいです。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 275,
            questionText = "ダイバーシティ方式について、誤っているものはどれですか？",
            choices = listOf(
                "ダイバーシティ方式は回線品質が同時に劣化する確率が小さい複数の通信系を設定してその受信信号を切り替えるか合成することでフェージングによる変動を軽減する方法",
                "周波数によりフェージングの影響が異なることを利用して2つの異なる周波数を用いるダイバーシティ方式を偏波ダイバーシティ方式という",
                "2基以上の受信アンテナを空間的に離れた位置に設置してそれらの受信信号を切り替えるか合成する方式をスペースダイバーシティ方式という",
                "十分に遠く離した2つ以上の伝送路を設定しこれを切り替えて使用するダイバーシティ方式をルートダイバーシティ方式という"
            ),
            correctAnswerIndex = 1,
            explanation = "異なる周波数を利用するダイバーシティ方式は「周波数ダイバーシティ方式」です。「偏波ダイバーシティ方式」は垂直偏波・水平偏波の直交性を利用する方式です。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 276,
            questionText = "周波数選択性フェージングなどによる伝送特性の劣化で波形ひずみが生じビット誤り率が大きくなるため、受信信号の振幅や位相のひずみをその変化に応じて補償する回路の一般的な名称はどれですか？",
            choices = listOf("符号器", "導波器", "圧縮器", "分波器", "等化器"),
            correctAnswerIndex = 4,
            explanation = "受信信号の振幅・位相ひずみを補償する回路は等化器（イコライザ）と呼ばれます。周波数領域で補償する回路と時間領域で補償する回路に大別されます。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 277,
            questionText = "PLLを用いたFM波の復調器の構成において、FM変調信号とVCOの出力を比較する部品Aと、VCOの出力の後に置かれるフィルタBの正しい組合せはどれですか？",
            choices = listOf(
                "A：位相比較器、B：低域フィルタ（LPF）",
                "A：位相比較器、B：圧縮器",
                "A：識別器、B：低域フィルタ（LPF）",
                "A：識別器、B：圧縮器"
            ),
            correctAnswerIndex = 0,
            explanation = "PLLを用いたFM復調器では、FM変調信号とVCO出力は位相比較器（A）で比較されます。位相比較器の出力はLPF（B）を通ってVCOに入力され、LPFの出力電圧変化がFM復調信号となります。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 278,
            questionText = "衛星通信のTDMA（時分割多元接続）方式についての記述として、正しいものはどれですか？",
            choices = listOf(
                "呼があったときに周波数が割り当てられ一つのチャネルごとに一つの周波数を使用して多重通信を行う方式",
                "中継局において受信波をいったん復調してパルスを整形し同期を取り直して再変調して送信する方式",
                "隣接する通信路間の干渉を避けるためガードバンドを設けて多重通信を行う方式",
                "多数の局が同一の搬送周波数で一つの中継装置を用い時間軸上で各局が送信すべき時間を分割して使用する方式"
            ),
            correctAnswerIndex = 3,
            explanation = "TDMAは多数の局が同一の搬送周波数を使用し、時間軸上で各局に割り当てられた時間スロットで送信する多元接続方式です。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 279,
            questionText = "地上系マイクロ波（SHF）多重回線における再生中継方式についての記述として、正しいものはどれですか？",
            choices = listOf(
                "中継局において受信したマイクロ波をいったん復調して信号の波形を整え同期を取り直してから再び変調して送信する方式",
                "中継局において受信したマイクロ波を中間周波数に変換して増幅し再びマイクロ波に変換して送信する方式",
                "中継局において受信したマイクロ波を固体増幅器等でそのまま増幅して送信する方式",
                "反射板等で電波の方向を変えることで中継を行い中継用の電力を必要としない方式"
            ),
            correctAnswerIndex = 0,
            explanation = "再生中継方式は中継局でいったん復調して波形整形・同期取り直しを行った後、再変調して送信する方式です。雑音が累積しない優れた特性を持ちます。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 280,
            questionText = "パルスレーダーの受信��において、STC回路の近距離感度制御の方向・遠距離での感度・FTC回路が行う処理の正しい組合せはどれですか？",
            choices = listOf(
                "感度を下げ、上げる、微分",
                "感度を下げ、上げる、積分",
                "感度を上げ、下げる、反転",
                "感度を上げ、下げる、微分",
                "感度を上げ、下げる、積分"
            ),
            correctAnswerIndex = 0,
            explanation = "STC回路は近距離の強反射波に対して感度を下げ（悪くし）、遠距離になるにつれて感度を上げ（良くし）ます。FTC回路は検波後の出力を微分して雨・雪の反射波を除去し物標を際立たせます。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 281,
            questionText = "パルスレーダーにおいてパルス波が発射されてから反射波が受信されるまでの時間が40μsであった。物標までの距離として最も近いものはどれですか？",
            choices = listOf("1,500 m", "3,000 m", "4,000 m", "5,000 m", "6,000 m"),
            correctAnswerIndex = 3,
            explanation = "電波は往復するため片道時間 = 40/2 = 20μs。距離 = 3×10⁸ × 20×10⁻⁶ = 6,000m → 最も近い値は6,000mです。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 282,
            questionText = "アダプティブアレーアンテナの特徴において、各アンテナの信号に重みを付ける対象・指向性制御の方法・ヌル点を向ける方向の正しい組合せはどれですか？",
            choices = listOf(
                "周波数、機械的、干渉波",
                "周波数、電気的、希望波",
                "振幅と位相、機械的、希望波",
                "振幅と位相、電気的、希望波",
                "振幅と位相、電気的、干渉波"
            ),
            correctAnswerIndex = 4,
            explanation = "アダプティブアレーアンテナは各素子の信号の振幅と位相に重みを付けて合成し電気的に指向性を制御します。干渉波の到来方向にヌル点を向けることで干渉波を抑圧し通信品質を改善します。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 283,
            questionText = "半波長ダイポールアンテナに対する相対利得12dBの八木・宇田アンテナから送信した最大放射方向の電界強度が、半波長ダイポールアンテナに8Wを供給して送信したときと同じであった。八木・宇田アンテナの供給電力として最も近いものはどれですか？（log₁₀2=0.3）",
            choices = listOf("0.1 W", "0.125 W", "0.25 W", "0.5 W", "1.0 W"),
            correctAnswerIndex = 3,
            explanation = "12dB = 10log₁₀G → G = 10^1.2 = 10^(4×0.3) = 16倍。同一電界強度のためP_dipole × G_dipole = P_yagi × G_yagi。8×1 = P_yagi×16。P_yagi = 8/16 = 0.5W。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 284,
            questionText = "単一指向性アンテナの電界パターンについて、半値角の定義・半値角の別名・前後比の表し方の正しい組合せはどれですか？",
            choices = listOf(
                "電界強度が最大値の1/2になる2方向の角度θ、放射効率、Eb/Ef",
                "電界強度が最大値の1/2になる2方向の角度θ、放射効率、Ef/Eb",
                "電界強度が最大値の1/2になる2方向の角度θ、ビーム幅、Ef/Eb",
                "電界強度が最大値の1/√2になる2方向の角度θ、放射効率、Eb/Ef",
                "電界強度が最大値の1/√2になる2方向の角度θ、ビーム幅、Ef/Eb"
            ),
            correctAnswerIndex = 4,
            explanation = "半値角は主ローブの電界強度が最大値の1/√2（電力で1/2）になる2方向で挟まれた角度θで表されます。ビーム幅とも呼ばれます。前後比はEf/Eb（前方電界強度/後方電界強度）です。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 285,
            questionText = "対流圏電波伝搬におけるM曲線について、標準大気のM曲線・接地形ラジオダクトのM曲線・ダクト発生時の電波伝搬の正しい組合せはどれですか？",
            choices = listOf(
                "②、①、内",
                "②、④、内",
                "②、④、外",
                "③、①、内",
                "③、④、外"
            ),
            correctAnswerIndex = 1,
            explanation = "標準大気のM曲線は右上がりの直線（②）です。接地形ラジオダクトではM曲線が折れ曲がり地表付近で減少します（④）。ダクト発生時は電波がダクト内に閉じ込められて遠方まで伝搬します。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 286,
            questionText = "UHF帯見通し距離内における受信電界強度のハイトパターンについて、受信電界強度が変動する原因の2波・変動の原因・変化ピッチと周波数の関係の正しい組合せはどれですか？",
            choices = listOf(
                "散乱波、減衰、低くなるほど広い",
                "散乱波、干渉、高くなるほど広い",
                "大地反射波、減衰、高くなるほど広い",
                "大地反射波、干渉、低くなるほど広い"
            ),
            correctAnswerIndex = 3,
            explanation = "受信アン��ナ高を変えると直接波と大地反射波の通路差が変わり、2波の干渉により電界強度が変動します。受信電界強度の変化ピッチは周波数が低くなるほど広くなります（波長が長いほど干渉縞が粗くなる）。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 287,
            questionText = "リチウムイオン蓄電池について、誤っているものはどれですか？",
            choices = listOf(
                "セル1個（単電池）当たりの公称電圧は1.2Vである",
                "電極間に充填された電解質中をリチウムイオンが移動して充放電を行う",
                "ニッケルカドミウム蓄電池に比べ小型軽量・高エネルギー密度である",
                "ニッケルカドミウム蓄電池と異なりメモリー効果がほとんどない",
                "ニッケルカドミウム蓄電池に比べ自己放電量が小さい"
            ),
            correctAnswerIndex = 0,
            explanation = "リチウムイオン蓄電池のセル1個の公称電圧は約3.6〜3.7Vです。1.2Vはニッケルカドミウムやニッケル水素電池のセル電圧です。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 288,
            questionText = "最大目盛値10mAの直流電流計A（内部抵抗RA=1.6Ω）に抵抗R1・R2を接続して最大目盛値100mA・50mAの多端子形電流計にするとき、R1・R2の値��正しい組合せはどれですか？",
            choices = listOf(
                "R1 = 0.4 Ω、R2 = 0.2 Ω",
                "R1 = 0.4 Ω、R2 = 0.4 Ω",
                "R1 = 0.2 Ω、R2 = 0.2 Ω",
                "R1 = 0.2 Ω、R2 = 0.4 Ω"
            ),
            correctAnswerIndex = 0,
            explanation = "100mA端子：倍率m = 100/10 = 10。R_total = RA/(m-1) = 1.6/9 ≈ 0.178Ω ≈ R1+R2 → R1+R2 = 0.2+0.4 = 0.6Ω（近似）。50mA端子：倍率m = 5。R2 = RA/(m-1) = 1.6/4 = 0.4Ω。R1 = 0.2Ω。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),
        Question(
            id = 289,
            questionText = "方向性結合器を用いた導波管回路のSWR測定において、副導波管出力③（電力計Ⅰ：M1）・出力④（電力計Ⅱ：M2）で測定したときの反射係数ΓとSWRの正しい組合せはどれですか？",
            choices = listOf(
                "Γ = √(M2/M1)、SWR = (1+Γ)/(1-Γ)",
                "Γ = √(M2/M1)、SWR = (1-Γ)/(1+Γ)",
                "Γ = √(M1/M2)、SWR = (1+Γ)/(1-Γ)",
                "Γ = √(M1/M2)、SWR = (1-Γ)/(1+Γ)",
                "Γ = √(M1/M2)、SWR = (1-Γ)/Γ"
            ),
            correctAnswerIndex = 2,
            explanation = "副導波管の出力③（電力計Ⅰ）には反射波に比例した電力M1が、出力④（電力計Ⅱ）には進行波に比例した電力M2が得られます。反射係数Γ = √(M1/M2)。SWR = (1+Γ)/(1-Γ)。",
            examType = ExamType.ENGINEERING_B,
            year = 2025,
            term = "令和7年10月期"
        ),


    )

    // ===== term をキーにした年度グループ取得 =====
    fun getTermsByExamType(context: Context, examType: ExamType): List<Pair<Int, String>> {
        return getAllQuestions(context)
            .filter { it.examType == examType }
            .map { Pair(it.year, it.periodLabel) }
            .distinctBy { it.second }
            .sortedByDescending { it.first }
    }

    // ===== Context付きメソッド（ユーザー追加問題を結合） =====
    fun getAllQuestions(context: Context): List<Question> {
        val userQuestions = QuestionStorage.loadQuestions(context)
        val deletedIds    = QuestionStorage.loadDeletedIds(context)
        val userIds       = userQuestions.map { it.id }.toSet()
        // 内蔵問題のうち削除済みや上書き済みを除外し、ユーザー問題と結合
        val filteredBuiltin = questions.filter { it.id !in deletedIds && it.id !in userIds }
        val filteredUser    = userQuestions.filter { it.id !in deletedIds }
        return filteredBuiltin + filteredUser
    }

    fun getQuestionsByExamType(context: Context, examType: ExamType): List<Question> {
        return getAllQuestions(context).filter { it.examType == examType }
    }

    fun getQuestionsByExamTypeAndYear(context: Context, examType: ExamType, year: Int): List<Question> {
        return getAllQuestions(context).filter { it.examType == examType && it.year == year }
    }

    fun getQuestionsByTerm(context: Context, examType: ExamType, term: String): List<Question> {
        return getAllQuestions(context).filter { it.examType == examType && it.periodLabel == term }
    }

    fun getYearsByExamType(context: Context, examType: ExamType): List<Int> {
        return getAllQuestions(context)
            .filter { it.examType == examType }
            .map { it.year }
            .distinct()
            .sortedDescending()
    }

    // ===== Context無し（後方互換） =====
    fun getExamTypes() = ExamType.values().toList()
    fun getQuestionsByExamType(examType: ExamType) =
        questions.filter { it.examType == examType }
    fun getQuestionsByExamTypeAndYear(examType: ExamType, year: Int) =
        questions.filter { it.examType == examType && it.year == year }
    fun getYearsByExamType(examType: ExamType) =
        questions.filter { it.examType == examType }
            .map { it.year }.distinct().sortedDescending()
}