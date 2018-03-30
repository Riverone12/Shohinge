package biz.riverone.shohinge.models

/**
 * ShoshingeData.kt: 正信偈のデータ
 * Created by kawahara on 2017/12/05.
 */
object ShoshingeData {

    fun timeToTitle(sec: Int): String {

        if (sec < 437) {
            var title1 = "正信念仏偈 "

            if (sec < 4) {
                return title1
            } else if (sec < 17) {
                return title1 + "総讃"
            } else if (sec < 160) {
                title1 += "依経段 "
                if (sec < 82) {
                    return title1 + "弥陀章"
                } else if (sec < 148) {
                    return title1 + "釈迦章"
                }
                return title1 + "結誡"
            }

            title1 += "依釈段 "
            if (sec < 175) {
                return title1 + "総讃"
            } else if (sec < 216) {
                return title1 + "龍樹章"
            } else if (sec < 254) {
                return title1 + "天親章"
            } else if (sec < 291) {
                return title1 + "曇鸞章"
            } else if (sec < 319) {
                return title1 + "道綽章"
            } else if (sec < 354) {
                return title1 + "善導章"
            } else if (sec < 380) {
                return title1 + "源信章"
            } else if (sec < 406) {
                return title1 + "源空章"
            }
            return title1 + "結勧"
        } else if (sec < 491) {
            return "念仏"
        } else if (sec < 620) {
            return "和讃"
        } else if (sec < 650) {
            return "回向"
        }
        return ""
    }

    fun timeToIndex(sec: Int): Int {
        return contentsList.indices
                .firstOrNull { contentsList[it].time > sec }
                ?.let { it - 1 }
                ?: -9
    }

    val contentsList: List<SD> = listOf(
            SD(4, "帰命無量寿如来","きみょうむりょうじゅにょらい"),
            SD(12, "南無不可思議光", "なむふかしぎこう"),
            SD(17, "法蔵菩薩因位時","ほうぞうぼさいんにじ"),
            SD(21, "在世自在王仏所","ざいせじざいおうぶっしょ"),
            SD(25, "覩見諸仏浄土因","とけんしょぶじょうどいん"),
            SD(30, "国土人天之善悪","こくどにんでんしぜんまく"),
            SD(33, "建立無上殊勝願", "こんりゅうむじょうしゅしょうがん"),
            SD(37, "超発希有大弘誓", "ちょうほっけうだいぐぜい"),
            SD(40, "五劫思惟之摂受", "ごこうしゆいししょうじゅ"),
            SD(44, "重誓名声聞十方", "じゅうせいみょうしょうもんじっぽう"),
            SD(47, "普放無量無辺光", "ふほうむりょうむへんこう"),
            SD(51, "無碍無対光炎王", "むげむたいこうえんのう"),
            SD(53, "清浄歓喜智慧光", "しょうじょうかんぎちえこう"),
            SD(57, "不断難思無称光", "ふだんなんしむしょうこう"),
            SD(60, "超日月光照塵刹", "ちょうにちがっこうしょうじんせ"),
            SD(64, "一切群生蒙光照", "いっさいぐんじょうむこうしょう"),
            SD(67, "本願名号正定業", "ほんがんみょうごうしょうじょうごう"),
            SD(71, "至心信楽願為因", "ししんしんぎょうがんにいん"),
            SD(75, "成等覚証大涅槃", "じょうとうがくしょうだいねはん"),
            SD(78, "必至滅度願成就", "ひっしめっどがんじょうじゅ"),
            SD(82, "如来所以興出世", "にょらいしょいこうしゅっせ"),
            SD(85, "唯説弥陀本願海", "ゆいせみだほんがんかい"),
            SD(88, "五濁悪時群生海", "ごじょくあくじぐんじょうかい"),
            SD(91, "応信如来如実言", "おうしんにょらいにょじっごん"),
            SD(95, "能発一念喜愛心", "のうほいちねんきあいしん"),
            SD(98, "不断煩悩得涅槃", "ふだんぼんのうとくねはん"),
            SD(102, "凡聖逆謗斉廻入", "ぼんしょうぎゃくほうさいえにゅう"),
            SD(105, "如衆水入海一味", "にょしゅしにゅうかいいちみ"),
            SD(108, "摂取心光常照護", "せっしゅしんこうじょうしょうご"),
            SD(111, "已能雖破無明闇", "いのうすいはむみょうあん"),
            SD(115, "貪愛瞋憎之雲霧", "とんないしんぞうしうんむ"),
            SD(118, "常覆真実信心天", "じょうふしんじしんじんてん"),
            SD(121, "譬如日光覆雲霧", "ひにょにっこうふうんむ"),
            SD(125, "雲霧之下明無闇", "うんむしげみょうむあん"),
            SD(128, "獲信見敬大慶喜", "ぎゃくしんけんきょうだいきょうき"),
            SD(131, "即横超截五悪趣", "そくおうちょうぜっごあくしゅ"),
            SD(135, "一切善悪凡夫人", "いっさいぜんまくぼんぶにん"),
            SD(138, "聞信如来弘誓願", "もんしんにょらいぐぜいがん"),
            SD(141, "仏言広大勝解者", "ぶつごんこうだいしょうげしゃ"),
            SD(144, "是人名分陀利華", "ぜにんみょうふんだりけ"),
            SD(148, "弥陀仏本願念仏", "みだぶほんがんねんぶ"),
            SD(151, "邪見驕慢悪衆生", "じゃけんきょうまんあくしゅじょう"),
            SD(154, "信楽受持甚以難", "しんぎょうじゅじじんになん"),
            SD(157, "難中之難無過斯", "なんちゅうしなんむかし"),
            SD(160, "印度西天之論家", "いんどさいてんしろんげ"),
            SD(164, "中夏日域之高僧", "ちゅうかじちいきしこうそう"),
            SD(168, "顕大聖興世正意", "けんだいしょうこうせしょうい"),
            SD(172, "明如来本誓応機", "みょうにょらいほんぜいおうき"),
            SD(175, "釈迦如来楞伽山", "しゃかにょらいりょうがせん"),
            SD(179, "為衆告命南天竺", "いしゅごうみょうなんてんじく"),
            SD(183, "龍樹大士出於世", "りゅうじゅだいじしゅっとせ"),
            SD(187, "悉能摧破有無見", "しつのうざいはうむけん"),
            SD(190, "宣説大乗無上法", "せんぜだいじょうむじょうほう"),
            SD(193, "証歓喜地生安楽", "しょうかんぎじしょうあんらく"),
            SD(196, "顕示難行陸路苦", "けんじなんぎょうろくろく"),
            SD(199, "信楽易行水道楽", "しんぎょういぎょうしどうらく"),
            SD(202, "憶念弥陀仏本願", "おくねんみだぶほんがん"),
            SD(205, "自然即時入必定", "じねんそくじにゅうひっじょう"),
            SD(209, "唯能常称如来号", "ゆいのうじょうしょうにょらいごう"),
            SD(212, "応報大悲弘誓恩", "おうほうだいひぐぜいおん"),
            SD(216, "天親菩薩造論説", "てんじんぼさぞうろんせ"),
            SD(219, "帰命無碍光如来", "きみょうむげこうにょらい"),
            SD(222, "依修多羅顕真実", "えしゅたらけんしんじ"),
            SD(225, "光闡横超大誓願", "こうせんおうちょうだいせいがん"),
            SD(228, "広由本願力廻向", "こうゆほんがんりきえこう"),
            SD(231, "為度群生彰一心", "いどぐんじょうしょういっしん"),
            SD(235, "帰入功徳大宝海", "きにゅうくどくだいほうかい"),
            SD(237, "必獲入大会衆数", "ひっぎゃくにゅうだいえしゅしゅ"),
            SD(241, "得至蓮華蔵世界", "とくしれんげぞうせかい"),
            SD(244, "即証真如法性身", "そくしょうしんにょほっしょうしん"),
            SD(248, "遊煩悩林現神通", "ゆぼんのうりんげんじんづう"),
            SD(250, "入生死薗示応化", "にゅうしょうじおんじおげ"),
            SD(254, "本師曇鸞梁天子", "ほんじどんらんりょうてんし"),
            SD(257, "常向鸞処菩薩礼", "じょうこうらんしょぼさらい"),
            SD(260, "三蔵流支授浄教", "さんぞうるしじゅじょうきょう"),
            SD(263, "焚焼仙経帰楽邦", "ぼんしょうせんぎょうきらくほう"),
            SD(266, "天親菩薩論註解", "てんじんぼさろんちゅうげ"),
            SD(269, "報土因果顕誓願", "ほどいんがけんせいがん"),
            SD(273, "往還廻向由他力", "おうげんえこうゆたりき"),
            SD(275, "正定之因唯信心", "しょうじょうしいんゆいしんじん"),
            SD(279, "惑染凡夫信心発", "わくぜんぼんぶしんじんほ"),
            SD(281, "証知生死即涅槃", "しょうちしょうじそくねはん"),
            SD(286, "必至無量光明土", "ひっしむりょうこうみょうど"),
            SD(289, "諸有衆生皆普化", "しょうしゅじょうかいふけ"),
            SD(291, "道綽決聖道難証", "どうしゃっけっしょうどうなんしょう"),
            SD(295, "唯明浄土可通入", "ゆいみょうじょうどかつうにゅう"),
            SD(298, "万善自力貶懃修", "まんぜんじりきへんごんしゅ"),
            SD(301, "円満徳号勧専称", "えんまんとくごうかんせんしょう"),
            SD(304, "三不三信誨慇懃", "さんぷさんしんけおんごん"),
            SD(307, "像末法滅同悲引", "ぞうまほうめどうひいん"),
            SD(310, "一生造悪値弘誓", "いっしょうぞうあくちぐぜい"),
            SD(314, "至安養界証妙果", "しあんにょうかいしょうみょうか"),
            SD(319, "善導独明仏正意", "ぜんどうどくみょうぶっしょうい"),
            SD(327, "矜哀定散与逆悪", "こうあいじょうさんよぎゃくあく"),
            SD(333, "光明名号顕因縁", "こうみょうみょうごうけんいんねん"),
            SD(337, "開入本願大智海", "かいにゅうほんがんだいちかい"),
            SD(340, "行者正受金剛心", "ぎょうじゃしょうじゅこんごうしん"),
            SD(344, "慶喜一念相応後", "きょうきいちねんそうおうご"),
            SD(347, "与韋提等獲三忍", "よいだいとうぎゃくさんにん"),
            SD(350, "即証法性之常楽", "そくしょうほっしょうしじょうらく"),
            SD(354, "源信広開一代教", "げんしんこうかいいちだいきょう"),
            SD(357, "偏帰安養勧一切", "へんきあんにょうかんいっさい"),
            SD(360, "専雑執心判浅深", "せんぞうしゅうしんはんせんじん"),
            SD(364, "報化二土正弁立", "ほうけにどしょうべんりゅう"),
            SD(367, "極重悪人唯称仏", "ごくじゅうあくにんゆいしょうぶ"),
            SD(370, "我亦在彼摂取中", "がやくざいひせっしゅちゅう"),
            SD(374, "煩悩障眼雖不見", "ぼんのうしょうげんすいふけん"),
            SD(377, "大悲無倦常照我", "だいひむけんじょうしょうが"),
            SD(380, "本師源空明仏教", "ほんじげんくうみょうぶっきょう"),
            SD(383, "憐愍善悪凡夫人", "れんみんぜんまくぼんぶにん"),
            SD(386, "真宗教証興片州", "しんしゅうきょうしょうこうへんしゅう"),
            SD(389, "選択本願弘悪世", "せんじゃくほんがんぐあくせ"),
            SD(392, "還来生死輪転家", "げんらいしょうじりんでんげ"),
            SD(396, "決以疑情為所止", "けっちぎじょういしょし"),
            SD(399, "速入寂静無為楽", "そくにゅうじゃくじょうむいらく"),
            SD(402, "必以信心為能入", "ひっちしんじんいのうにゅう"),
            SD(406, "弘経大士宗師等", "ぐきょうだいじしゅうしとう"),
            SD(411, "拯済無辺極濁悪", "じょうさいむへんごくじょくあく"),
            SD(415, "道俗時衆共同心", "どうぞくじしゅうぐどうしん"),
            SD(420, "唯可信斯高僧説", "ゆいかしんしこうそうせ"),
            SD(437, "南無阿弥陀仏","なむあみだぶ"),
            SD(453, "南無阿弥陀仏","なむあみだぶ"),
            SD(459, "南無阿弥陀仏","なむあみだぶ"),
            SD(466, "南無阿弥陀仏","なむあみだぶ"),
            SD(471, "南無阿弥陀仏","なむあみだぶ"),
            SD(481, "南無阿弥陀仏","なむあみだぶ"),
            SD(486, "南無阿弥","なむあみ"),
            SD(490, "", ""),
            SD(491, "弥陀成仏のこのかたは","みだじょうぶのこのかたは"),
            SD(500, "いまに十劫をへたまへり","いまにじっこうをへたまえり"),
            SD(507, "法身の光輪きはもなく","ほっしんのこうりんきわもなく"),
            SD(511, "世の盲冥をてらすなり","せのもうみょうをてらすなり"),
            SD(516, "智慧の光明はかりなし","ちえのこうみょうはかりなし"),
            SD(521, "有量の諸相ことごとく","うりょうのしょそうことごとく"),
            SD(527, "光暁かふらぬものはなし","こうきょうかむらぬものはなし"),
            SD(531, "真実明に帰命せよ","しんじみょうにきみょうせよ"),
            SD(537, "解脱の光輪きはもなし","げだのこうりんきわもなし"),
            SD(543, "光触かふるものはみな","こうそくかむるものはみな"),
            SD(550, "有無をはなるとのべたまふ","うむをはなるとのべたもう"),
            SD(554, "平等覚に帰命せよ","びょうどうかくにきみょうせよ"),
            SD(558, "光雲無碍如虚空","こううんむげにょこくう"),
            SD(563, "一切の有碍にさはりなし","いっさいのうげにさわりなし"),
            SD(570, "光沢かふらぬものぞなき","こうたくかむらぬものぞなき"),
            SD(575, "難思議を帰命せよ","なんしぎをきみょうせよ"),
            SD(578, "清浄光明ならびなし","しょうじょうこうみょうならびなし"),
            SD(586, "遇斯光のゆへなれば","ぐしこうのゆえなれば"),
            SD(588, "一切の業繋ものぞこりぬ","いっさいのごうけものぞこりぬ"),
            SD(593, "畢竟依を帰命せよ","ひっきょうえをきみょうせよ"),
            SD(597, "仏光照曜最第一","ぶっこうしょうようさいだいいち"),
            SD(602, "光炎王仏となづけたり","こうえんのうぶっとなづけたり"),
            SD(608, "三塗の黒闇ひらくなり","さんづのこくあんひらくなり"),
            SD(612, "大応供を帰命せよ","だいおうぐをきみょうせよ"),
            SD(620, "願以此功徳","がんにしくどく"),
            SD(629, "平等施一切","びょうどうせいさい"),
            SD(634, "同発菩提心","どうほっぼだいしん"),
            SD(639, "往生安楽国","おうじょうあんらこ"),
            SD(646, "",""),
            SD(650, "", "")
    )
}