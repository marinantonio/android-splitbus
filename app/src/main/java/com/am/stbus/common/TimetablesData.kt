/*
 * MIT License
 *
 * Copyright (c) 2013 - 2019 Antonio Marin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.am.stbus.common

import com.am.stbus.R
import com.am.stbus.domain.models.Timetable

class TimetablesData {

    fun getTimetableTitle(lineId: Int): Int =
            when(lineId) {
                // Gradski
                1 -> R.string.bus32
                2 -> R.string.bus32
                3 -> R.string.bus6
                4 -> R.string.bus7
                5 -> R.string.bus8
                6 -> R.string.bus9
                7 -> R.string.bus11
                8 -> R.string.bus121
                9 -> R.string.bus121
                10 -> R.string.bus14
                11 -> R.string.bus15
                12 -> R.string.bus17
                13 -> R.string.bus18
                14 -> R.string.bus20
                15 -> R.string.bus21
                16 -> R.string.bus241
                17 -> R.string.bus242
                18 -> R.string.bus251
                19 -> R.string.bus252
                20 -> R.string.bus261
                21 -> R.string.bus262
                22 -> R.string.bus271
                23 -> R.string.bus272
                24 -> R.string.bus39
                25 -> R.string.bus40


                // Urbano
                101 -> R.string.bus1
                102 -> R.string.bus021
                103 -> R.string.bus022
                104 -> R.string.bus023
                105 -> R.string.bus5
                106 -> R.string.bus51
                107 -> R.string.bus10
                108 -> R.string.bus16
                109 -> R.string.bus22
                110 -> R.string.bus23
                111 -> R.string.bus281
                112 -> R.string.bus282
                113 -> R.string.bus291
                114 -> R.string.bus292
                115 -> R.string.bus301
                116 -> R.string.bus302
                117 -> R.string.bus311
                118 -> R.string.bus312
                119 -> R.string.bus321
                120 -> R.string.bus322
                121 -> R.string.bus331
                122 -> R.string.bus332
                123 -> R.string.bus341
                124 -> R.string.bus342
                125 -> R.string.bus351
                126 -> R.string.bus352
                127 -> R.string.bus361
                128 -> R.string.bus362
                129 -> R.string.bus371
                130 -> R.string.bus372
                131 -> R.string.bus381
                132 -> R.string.bus382
                133 -> R.string.bus601
                134 -> R.string.bus602
                135 -> R.string.zeljkstari
                136 -> R.string.kstarizelj
                137 -> R.string.trostdirekt2

                // Prigradsko
                201 -> R.string.bus671
                202 -> R.string.bus672
                203 -> R.string.bus681
                204 -> R.string.bus682
                205 -> R.string.bus691
                206 -> R.string.bus692
                207 -> R.string.bus711
                208 -> R.string.bus712
                209 -> R.string.bus731
                210 -> R.string.bus732
                211 -> R.string.bus761
                212 -> R.string.bus762
                213 -> R.string.bus771
                214 -> R.string.bus772
                215 -> R.string.bus801
                216 -> R.string.bus802
                217 -> R.string.bus811
                218 -> R.string.bus812
                219 -> R.string.bus861
                220 -> R.string.bus862
                // 84
                // 85
                // 86
                221 -> R.string.bus900
                222 -> R.string.bus911
                223 -> R.string.bus912
                224 -> R.string.bus931
                225 -> R.string.bus932
                226 -> R.string.kstarirudine
                227 -> R.string.rudinekstari


                // Trogir i Solta
                301 -> R.string.bus411
                302 -> R.string.bus412
                303 -> R.string.bus421
                304 -> R.string.bus422
                305 -> R.string.bus441
                306 -> R.string.bus442
                307 -> R.string.bus451
                308 -> R.string.bus452
                309 -> R.string.bus47
                310 -> R.string.bus48
                311 -> R.string.bus491
                312 -> R.string.bus492
                313 -> R.string.bus501
                314 -> R.string.bus502
                315 -> R.string.bus511
                316 -> R.string.bus512
                317 -> R.string.bus521
                318 -> R.string.bus522

                401 -> R.string.buss1
                402 -> R.string.buss2
                403 -> R.string.buss3
                404 -> R.string.buss4
                else -> throw IllegalArgumentException("Illegal lineId $lineId")
            }

    fun getTimetableTitleAsOnPrometWebsite(lineId: Int): String =
            when(lineId) {
                // Gradski
                1 -> "3 BRDA - LOVRINAC"
                2 -> "3 LOVRINAC - BRDA"
                3 -> "6 KILA - VUKOVARSKA - HNK - KILA"
                4 -> "7 ŽNJAN - ZVONČAC - ŽNJAN"
                5 -> "8 ŽNJAN - TRŽNICA - ZVONČAC - TRŽNICA - ŽNJAN"
                6 -> "9 RAVNE NJIVE - TR.LUKA - RAVNE NJIVE"
                7 -> "11 RAVNE NJIVE-PUJANKE-HNK-RAVNE NJIVE"
                8 -> "12 SV. FRANE - BENE"
                9 -> "12 BENE - (MEJE) - SV.FRANE"
                10 -> "14 BRDA - KOPILICA - DUBROVAČKA - BOLNICE - ŽNJAN - DUILOVO"
                11 -> "15 DUILOVO - ŽNJAN - TR. LUKA - DUILOVO"
                12 -> "17 SPINUT - LORA - TRSTENIK - LORA -SPINUT"
                13 -> "18 BRNIK - HNK - BRNIK"
                14 -> "20 RAVNE NJIVE - ZVONČAC"
                15 -> "21 SV.FRANE - MEJE - SV.FRANE"
                16 -> "24 SPLIT - KOPILICA - DOM. RATA - STOBREČ - TTTS"
                17 -> "24 TTTS - STOBREČ - DOM. RATA - KOPILICA - SPLIT"
                18 -> "25 SPLIT - STOBREČ"
                19 -> "25 STOBREČ - SPLIT"
                20 -> "26 SPLIT - KAMEN"
                21 -> "26 KAMEN - SPLIT"
                22 -> "27 SPLIT - KOREŠNICA - ŽRNOVNICA"
                23 -> "27 ŽRNOVNICA - KOREŠNICA - SPLIT"
                24 -> "39 LORA - POLJIČKA - TTTS - LORA"
                25 -> "40 TRAJ. LUKA - KILA - TRAJ. LUKA"
                
                // Urbano
                101 -> "1 STARINE - HNK - STARINE"
                102 -> "2 ZRAČNA L. - K. SUĆURAC (STRINJE) - POLJIČKA - SPLIT"
                103 -> "2 SPLIT- POLJIČKA - K. SUĆURAC (STRINJE) - ZRAČNA L."
                104 -> "2A K. SUĆURAC(STRINJE) - TR. LUKA - K. SUĆURAC(STRINJE)"
                105 -> "5 DRAČEVAC - HNK - DRAČEVAC"
                106 -> "5A DRAČEVAC - SOLIN - VISOKA - HNK - DRAČEVAC"
                107 -> "10 JAPIRKO - HNK - JAPIRKO"
                108 -> "16 NINČEVIĆI - HNK - NINČEVIĆI"
                109 -> "22 KLIS MEGDAN-G.RUPOTINA-HNK-KLIS MEGDAN"
                110 -> "23 HNK - SOLIN - HNK"
                111 -> "28 SPLIT - SITNO G. - DUBRAVA"
                112 -> "28 DUBRAVA - SITNO G. - SPLIT"
                113 -> "29 SPLIT - TUGARE - NAKLICE"
                114 -> "29 NAKLICE - TUGARE - SPLIT"
                115 -> "30 SPLIT - PODSTRANA (MUTOGRAS)"
                116 -> "30 PODSTRANA (MUTOGRAS ) - SPLIT"
                117 -> "31 SPLIT - VRANJIC"
                118 -> "31 VRANJIC - SPLIT"
                119 -> "32 SPLIT - KUČINE"
                120 -> "32 KUČINE - SPLIT"
                121 -> "33 SPLIT - KOSA"
                122 -> "33 KOSA - SPLIT"
                123 -> "34 SPLIT - KLIS (MEGDAN)"
                124 -> "34 KLIS (MEGDAN) - SPLIT"
                125 -> "35 SPLIT - DUGOPOLJE"
                126 -> "35 DUGOPOLJE - SPLIT"
                127 -> "36 SPLIT - KOPRIVNO"
                128 -> "36 KOPRIVNO - SPLIT"
                129 -> "37 SPLIT - AIRPORT - TROGIR"
                130 -> "37 TROGIR - AIRPORT - SPLIT"
                131 -> "38 SPLIT - K. STARI - RESNIK"
                132 -> "38 RESNIK - K. STARI - SPLIT"
                133 -> "60 SPLIT - OMIŠ - RAVNIČKI MOST"
                134 -> "60 RAVNIČKI MOST - OMIŠ - SPLIT"
                135 -> "ŽELJEZNIČKA STANICA - KAŠTEL STARI"
                136 -> "KAŠTEL STARI - ŽELJEZNIČKA STANICA"
                137 -> "TROGIR - SPLIT (direktna)"


                // Prigradsko
                201 -> "67 SPLIT - KOTLENICE - DOLAC D. - DOLAC G."
                202 -> "67 DOLAC G. - DOLAC D. - KOTLENICE - SPLIT"
                203 -> "68 SPLIT-TUGARE-BLATO-ŠESTANOVAC"
                204 -> "68 ŠESTANOVAC-BLATO-TUGARE-SPLIT"
                205 -> "69 SPLIT-BISKO-TRNBUSI-BLATO-ŠESTANOVAC"
                206 -> "69 ŠESTANOVAC-BLATO-TRNBUSI-BISKO-SPLIT"
                207 -> "71 SPLIT - NEORIĆ - SUTINA"
                208 -> "71 SUTINA - NEORIĆ - SPLIT"
                209 -> "73 SPLIT-MUĆ / MUĆ-OGORJE"
                210 -> "73 OGORJE - MUĆ / MUĆ - SPLIT"
                211 -> "76 SPLIT - CRIVAC- KLJACI"
                212 -> "76 KLJACI - CRIVAC - SPLIT"
                213 -> "77 SPLIT - MUĆ - CRIVAC"
                214 -> "77 CRIVAC - MUĆ - SPLIT"
                215 -> "80 SPLIT - KLJACI - DRNIŠ"
                216 -> "80 DRNIŠ - KLJACI - SPLIT"
                217 -> "81 SPLIT - BRŠTANOVO - NISKO"
                218 -> "81 NISKO - BRŠTANOVO - SPLIT"
                219 -> "86 SPLIT - KONJSKO - LEĆEVICA - KLADNJICE"
                220 -> "86 KLADNJICE - LEĆEVICA - KONJSKO - SPLIT"
                // 84
                // 85
                // 86
                221 -> "90 SITNO - BOGDANOVIĆI - RADOŠIĆ - MALAČKA - K. STARI"
                222 -> "91 K. STARI - PLANO - PRIMORSKI DOLAC - SITNO - BOGDANOVIĆI - DIVOJEVIĆI"
                223 -> "91 DIVOJEVIĆI - BOGDANOVIĆI - SITNO - PRIMORSKI DOLAC - PLANO - K. STARI"
                224 -> "93 K. STARI - MALAČKA - TEŠIJE - ĐIRLIĆI - ŠERIĆI"
                225 -> "93 ŠERIĆI - ĐIRLIĆI - TEŠIJE - MALAČKA - K. STARI"
                226 -> "KAŠTEL STARI - RUDINE"
                227 -> "RUDINE - KAŠTEL STARI"

                // Trogir i Solta
                301 -> "41 TROGIR - PLANO - MALJKOVIĆI"
                302 -> "41 MALJKOVIĆI - PLANO - TROGIR"
                303 -> "42 TROGIR - SLATINE"
                304 -> "42 SLATINE - TROGIR"
                305 -> "44 TROGIR - OKRUG DONJI"
                306 -> "44 OKRUG DONJI - TROGIR"
                307 -> "45 TROGIR - HOTEL MEDENA - VRANJICA"
                308 -> "45 VRANJICA - HOTEL MEDENA - TROGIR"
                309 -> "47 TROGIR - HOTEL MEDENA - VRSINE - MARINA - TROGIR"
                310 -> "48 TROGIR - HOTEL MEDENA - MARINA - DOGRADE - VRSINE - TROGIR"
                311 -> "49 TROGIR - HOTEL MEDENA - VINIŠĆE"
                312 -> "49 VINIŠĆE - HOTEL MEDENA - TROGIR"
                313 -> "50 TROGIR - HOTEL MEDENA - SEVID"
                314 -> "50 SEVID - HOTEL MEDENA - TROGIR"
                315 -> "51 TROGIR - LJUBITOVICA"
                316 -> "51 LJUBITOVICA - TROGIR"
                317 -> "52 TROGIR - VINOVAC"
                318 -> "52 VINOVAC - TROGIR"

                401 -> "MASLINICA - DONJE SELO - SREDNJE SELO - GROHOTE - ROGAČ"
                402 -> "ROGAČ - GROHOTE - SREDNJE SELO - DONJE SELO - MASLINICA"
                403 -> "STOMORSKA - GORNJE SELO - NEČUJAM - GROHOTE - ROGAČ"
                404 -> "ROGAČ - GROHOTE - NEČUJAM - GORNJE SELO - STOMORSKA"
                else -> throw IllegalArgumentException("Illegal lineId $lineId")
            }

    companion object {

        const val AREA_CITY = 0
        const val AREA_URBAN = 1
        const val AREA_SUBURBAN = 2
        const val AREA_TROGIR = 4
        const val AREA_SOLTA = 5

        val list: List<Timetable> = listOf(
                // Gradski
                Timetable(1, "3", 31, AREA_CITY, 0, "", ""),
                Timetable(2, "3", 32, AREA_CITY, 0, "", ""),
                Timetable(3, "6", 60, AREA_CITY, 0, "", ""),
                Timetable(4, "7", 70, AREA_CITY, 0, "", ""),
                Timetable(5, "8", 80, AREA_CITY, 0, "", ""),
                Timetable(6, "9", 90, AREA_CITY, 0, "", ""),
                Timetable(7, "11", 110, AREA_CITY, 0, "", ""),
                Timetable(8, "12", 121, AREA_CITY, 0, "", ""),
                Timetable(9, "12", 122, AREA_CITY, 0, "", ""),
                Timetable(10, "14", 140, AREA_CITY, 0, "", ""),
                Timetable(11, "15", 150, AREA_CITY, 0, "", ""),
                Timetable(12, "17", 170, AREA_CITY, 0, "", ""),
                Timetable(13, "18", 180, AREA_CITY, 0, "", ""),
                Timetable(14, "20", 200, AREA_CITY, 0, "", ""),
                Timetable(15, "21", 210, AREA_CITY, 0, "", ""),
                Timetable(16, "24", 241, AREA_CITY, 0, "", ""),
                Timetable(17, "24", 242, AREA_CITY, 0, "", ""),
                Timetable(18, "25", 251, AREA_CITY, 0, "", ""),
                Timetable(19, "25", 252, AREA_CITY, 0, "", ""),
                Timetable(20, "26", 261, AREA_CITY, 0, "", ""),
                Timetable(21, "26", 262, AREA_CITY, 0, "", ""),
                Timetable(22, "27", 271, AREA_CITY, 0, "", ""),
                Timetable(23, "27", 272, AREA_CITY, 0, "", ""),
                Timetable(24, "39", 390, AREA_CITY, 0, "", ""),
                Timetable(25, "40", 400, AREA_CITY, 0, "", ""),

                // Urbano
                Timetable(101, "1", 10, AREA_URBAN, 0, "", ""),
                Timetable(102, "2", 21, AREA_URBAN, 0, "", ""),
                Timetable(103, "2", 22, AREA_URBAN, 0, "", ""),
                Timetable(104, "2A", 23, AREA_URBAN, 0, "", ""),
                Timetable(105, "5", 50, AREA_URBAN, 0, "", ""),
                Timetable(106, "5A", 51, AREA_URBAN, 0, "", ""),
                Timetable(107, "10", 100, AREA_URBAN, 0, "", ""),
                Timetable(108, "16", 160, AREA_URBAN, 0, "", ""),
                Timetable(109, "22", 220, AREA_URBAN, 0, "", ""),
                Timetable(110, "23", 230, AREA_URBAN, 0, "", ""),
                Timetable(111, "28", 281, AREA_URBAN, 0, "", ""),
                Timetable(112, "28", 282, AREA_URBAN, 0, "", ""),
                Timetable(113, "29", 291, AREA_URBAN, 0, "", ""),
                Timetable(114, "29", 292, AREA_URBAN, 0, "", ""),
                Timetable(115, "30", 301, AREA_URBAN, 0, "", ""),
                Timetable(116, "30", 302, AREA_URBAN, 0, "", ""),
                Timetable(117, "31", 311, AREA_URBAN, 0, "", ""),
                Timetable(118, "31", 312, AREA_URBAN, 0, "", ""),
                Timetable(119, "32", 321, AREA_URBAN, 0, "", ""),
                Timetable(121, "33", 331, AREA_URBAN, 0, "", ""),
                Timetable(122, "33", 332, AREA_URBAN, 0, "", ""),
                Timetable(123, "34", 341, AREA_URBAN, 0, "", ""),
                Timetable(124, "34", 342, AREA_URBAN, 0, "", ""),
                Timetable(125, "35", 351, AREA_URBAN, 0, "", ""),
                Timetable(126, "35", 352, AREA_URBAN, 0, "", ""),
                Timetable(127, "36", 361, AREA_URBAN, 0, "", ""),
                Timetable(128, "36", 362, AREA_URBAN, 0, "", ""),
                Timetable(129, "37", 371, AREA_URBAN, 0, "", ""),
                Timetable(130, "37", 372, AREA_URBAN, 0, "", ""),
                Timetable(131, "38", 381, AREA_URBAN, 0, "", ""),
                Timetable(132, "38", 382, AREA_URBAN, 0, "", ""),
                Timetable(133, "60", 601, AREA_URBAN, 0, "", ""),
                Timetable(134, "60", 602, AREA_URBAN, 0, "", ""),
                Timetable(135, "", 1000, AREA_URBAN, 0, "", ""),
                Timetable(136, "", 1001, AREA_URBAN, 0, "", ""),
                Timetable(137, "", 1002, AREA_URBAN, 0, "", ""),

                // Prigradsko
                Timetable(201, "67", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(202, "67", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(203, "68", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(204, "68", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(205, "69", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(206, "69", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(207, "71", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(208, "71", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(209, "73", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(210, "73", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(211, "76", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(212, "76", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(213, "77", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(214, "77", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(215, "80", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(216, "80", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(217, "81", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(218, "81", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(219, "86", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(220, "86", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(221, "90", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(222, "91", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(223, "91", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(224, "93", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(225, "93", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(226, "", 31, AREA_SUBURBAN, 0, "", ""),
                Timetable(226, "", 31, AREA_SUBURBAN, 0, "", ""),

                // Trogir i Solta
                Timetable(301, "41", 31, AREA_TROGIR, 0, "", ""),
                Timetable(302, "41", 31, AREA_TROGIR, 0, "", ""),
                Timetable(303, "42", 31, AREA_TROGIR, 0, "", ""),
                Timetable(304, "42", 31, AREA_TROGIR, 0, "", ""),
                Timetable(305, "44", 31, AREA_TROGIR, 0, "", ""),
                Timetable(306, "44", 31, AREA_TROGIR, 0, "", ""),
                Timetable(307, "45", 31, AREA_TROGIR, 0, "", ""),
                Timetable(308, "45", 31, AREA_TROGIR, 0, "", ""),
                Timetable(309, "47", 31, AREA_TROGIR, 0, "", ""),
                Timetable(310, "48", 31, AREA_TROGIR, 0, "", ""),
                Timetable(311, "49", 31, AREA_TROGIR, 0, "", ""),
                Timetable(312, "49", 31, AREA_TROGIR, 0, "", ""),
                Timetable(313, "50", 31, AREA_TROGIR, 0, "", ""),
                Timetable(314, "50", 31, AREA_TROGIR, 0, "", ""),
                Timetable(315, "51", 31, AREA_TROGIR, 0, "", ""),
                Timetable(316, "51", 31, AREA_TROGIR, 0, "", ""),
                Timetable(317, "52", 31, AREA_TROGIR, 0, "", ""),
                Timetable(318, "52", 31, AREA_TROGIR, 0, "", ""),

                Timetable(401, "", 31, AREA_SOLTA, 0, "", ""),
                Timetable(402, "", 31, AREA_SOLTA, 0, "", ""),
                Timetable(403, "", 31, AREA_SOLTA, 0, "", ""),
                Timetable(403, "", 31, AREA_SOLTA, 0, "", "")
        )

    }
}