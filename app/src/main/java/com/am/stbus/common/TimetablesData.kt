/*
 * MIT License
 *
 * Copyright (c) 2013 - 2021 Antonio Marin
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
                when (lineId) {
                        // Gradski
                        1 -> R.string.bus32
                        2 -> R.string.bus31
                        3 -> R.string.bus6
                        4 -> R.string.bus71
                        5 -> R.string.bus72
                        6 -> R.string.bus81
                        7 -> R.string.bus82
                        8 -> R.string.bus9
                        9 -> R.string.bus111
                        10 -> R.string.bus112
                        11 -> R.string.bus121
                        12 -> R.string.bus122
                        13 -> R.string.bus141
                        14 -> R.string.bus142
                        15 -> R.string.bus15
                        16 -> R.string.bus171
                        17 -> R.string.bus172
                        18 -> R.string.bus18
                        19 -> R.string.bus20
                        20 -> R.string.bus21
                        21 -> R.string.bus241
                        22 -> R.string.bus242
                        23 -> R.string.bus251
                        24 -> R.string.bus252
                        25 -> R.string.bus261
                        26 -> R.string.bus262
                        27 -> R.string.bus271
                        28 -> R.string.bus272
                        29 -> R.string.bus39
                        30 -> R.string.bus40


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
                        137 -> R.string.trogir_kstari
                        138 -> R.string.kstari_trogir
                        139 -> R.string.trostdirekt2

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
                        309 -> R.string.bus481
                        310 -> R.string.bus482
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
                when (lineId) {
                        // Gradski
                        1 -> "3 BRNIK"
                        2 -> "3A BRNIK"
                        3 -> "6 KILA"
                        4 -> "7 ŽNJAN"
                        5 -> "7 ZAPADNA"
                        6 -> "8 ŽNJAN"
                        7 -> "8 ZVONČAC"
                        8 -> "9 RAVNE NJIVE"
                        9 -> "11 SPINUT"
                        10 -> "11 RAVNE"
                        11 -> "12 SV. FRANE"
                        12 -> "12 BENE"
                        13 -> "14 BRDA"
                        14 -> "14 DUILOVO"
                        15 -> "15 DUILOVO"
                        16 -> "17 SPINUT"
                        17 -> "17 TRSTENIK"
                        18 -> "18 SIROBUJA"
                        19 -> "20 BRDA"
                        20 -> "21 SV.FRANE"
                        21 -> "24 SPLIT"
                        22 -> "24 TTTS"
                        23 -> "25 SPLIT"
                        24 -> "25 STOBREČ"
                        25 -> "26 SPLIT"
                        26 -> "26 KAMEN"
                        27 -> "27 SPLIT"
                        28 -> "27 ŽRNOVNICA"
                        29 -> "39 LORA"
                        30 -> "40 TRAJ"

                        // Urbano
                        101 -> "1 BUNJE"
                        102 -> "2 ZRAČN"
                        103 -> "2 SPLIT"
                        104 -> "2A"
                        105 -> "5 DRAČEVAC"
                        106 -> "5A DRAČEVAC"
                        107 -> "10 JAPIRKO"
                        108 -> "16 NINČEVIĆI"
                        109 -> "22 KLIS"
                        110 -> "23 HNK"
                        111 -> "28 SPLIT"
                        112 -> "28 DUBRAVA"
                        113 -> "29 SPLIT"
                        114 -> "29 NAKLICE"
                        115 -> "30 SPLIT"
                        116 -> "30 PODSTRANA"
                        117 -> "31 SPLIT"
                        118 -> "31 VRANJIC"
                        119 -> "32 SPLIT"
                        120 -> "32 KUČINE"
                        121 -> "33 SPLIT"
                        122 -> "33 K"
                        123 -> "34 SPLIT"
                        124 -> "34 KLIS"
                        125 -> "35 SPLIT"
                        126 -> "35 DU"
                        127 -> "36 SPLIT"
                        128 -> "36 KOPRIVNO"
                        129 -> "37 SP"
                        130 -> "37 TR"
                        131 -> "38 SPLIT"
                        132 -> "38 ZRAČNA"
                        133 -> "60 SPLIT"
                        134 -> "60 RAVNIČKI"
                        135 -> "ŽELJEZNIČKA STANICA - KAŠTEL STARI"
                        136 -> "KAŠTEL STARI - ŽELJEZNIČKA STANICA"
                        137 -> "TROGIR KOLODVOR - ŽELJEZNIČKA STANICA"
                        138 -> "ŽELJEZNIČKA STANICA (K. STARI) - TROGIR KOLODVOR INTEGRIRANA "
                        139 -> "TROGIR - SPLIT"


                        // Prigradsko
                        201 -> "67 SPLIT"
                        202 -> "67 DOLAC"
                        203 -> "68 SPLIT"
                        204 -> "68 ŠEST"
                        205 -> "69 SPLIT"
                        206 -> "69 ŠEST"
                        207 -> "71 SPLIT"
                        208 -> "71 SUTINA"
                        209 -> "73 SPLIT"
                        210 -> "73 OGORJE"
                        211 -> "76 SPLIT"
                        212 -> "76 KLJACI"
                        213 -> "77 SPLIT"
                        214 -> "77 CRIVAC"
                        215 -> "80 SPLIT"
                        216 -> "80 DRNIŠ"
                        217 -> "81 SPLIT"
                        218 -> "81 NISKO"
                        219 -> "86 SPLIT"
                        220 -> "86 KLADNJICE"
                        // 84
                        // 85
                        // 86
                        221 -> "90 SITNO"
                        222 -> "91 K"
                        223 -> "91 D"
                        224 -> "93 K"
                        225 -> "93 ŠE"
                        226 -> "KAŠTEL STARI - RUDINE"
                        227 -> "RUDINE - KAŠTEL STARI"

                        // Trogir i Solta
                        301 -> "41 TROGIR"
                        302 -> "41 PLANO"
                        303 -> "42 TROGIR"
                        304 -> "42 SLATINE"
                        305 -> "44 TROGIR"
                        306 -> "44 OKRUG DONJI"
                        307 -> "45 TROGIR"
                        308 -> "45 SEGET"
                        309 -> "48 TROGIR"
                        310 -> "48 MARINA"
                        311 -> "49 TROGIR"
                        312 -> "49 VINIŠĆE"
                        313 -> "50 TROGIR"
                        314 -> "50 SEVID"
                        315 -> "51 TROGIR"
                        316 -> "51 LJUBITOVICA"
                        317 -> "52 TROGIR"
                        318 -> "52 VINOVAC"

                        401 -> "MASLINICA"
                        402 -> "ROGAČ - GROHOTE - SREDNJE SELO"
                        403 -> "STOMORSKA"
                        404 -> "ROGAČ - GROHOTE - NEČUJAM"
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
                        Timetable(2, "3A", 32, AREA_CITY, 0, "", ""),
                        Timetable(3, "6", 60, AREA_CITY, 0, "", ""),
                        Timetable(4, "7", 71, AREA_CITY, 0, "", ""),
                        Timetable(5, "7", 72, AREA_CITY, 0, "", ""),
                        Timetable(6, "8", 81, AREA_CITY, 0, "", ""),
                        Timetable(7, "8", 82, AREA_CITY, 0, "", ""),
                        Timetable(8, "9", 90, AREA_CITY, 0, "", ""),
                        Timetable(9, "11", 111, AREA_CITY, 0, "", ""),
                        Timetable(10, "11", 112, AREA_CITY, 0, "", ""),
                        Timetable(11, "12", 121, AREA_CITY, 0, "", ""),
                        Timetable(12, "12", 122, AREA_CITY, 0, "", ""),
                        Timetable(13, "14", 141, AREA_CITY, 0, "", ""),
                        Timetable(14, "14", 142, AREA_CITY, 0, "", ""),
                        Timetable(15, "15", 150, AREA_CITY, 0, "", ""),
                        Timetable(16, "17", 171, AREA_CITY, 0, "", ""),
                        Timetable(17, "17", 172, AREA_CITY, 0, "", ""),
                        Timetable(18, "18", 180, AREA_CITY, 0, "", ""),
                        Timetable(19, "20", 200, AREA_CITY, 0, "", ""),
                        Timetable(20, "21", 210, AREA_CITY, 0, "", ""),
                        Timetable(21, "24", 241, AREA_CITY, 0, "", ""),
                        Timetable(22, "24", 242, AREA_CITY, 0, "", ""),
                        Timetable(23, "25", 251, AREA_CITY, 0, "", ""),
                        Timetable(24, "25", 252, AREA_CITY, 0, "", ""),
                        Timetable(25, "26", 261, AREA_CITY, 0, "", ""),
                        Timetable(26, "26", 262, AREA_CITY, 0, "", ""),
                        Timetable(27, "27", 271, AREA_CITY, 0, "", ""),
                        Timetable(28, "27", 272, AREA_CITY, 0, "", ""),
                        Timetable(29, "39", 390, AREA_CITY, 0, "", ""),
                        Timetable(30, "40", 400, AREA_CITY, 0, "", ""),

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
                        Timetable(120, "32", 322, AREA_URBAN, 0, "", ""),
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
                        Timetable(138, "", 1003, AREA_URBAN, 0, "", ""),
                        Timetable(139, "", 1004, AREA_URBAN, 0, "", ""),

                        // Prigradsko
                        Timetable(201, "67", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(202, "67", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(203, "68", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(204, "68", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(205, "69", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(206, "69", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(207, "71", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(208, "71", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(209, "73", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(210, "73", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(211, "76", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(212, "76", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(213, "77", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(214, "77", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(215, "80", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(216, "80", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(217, "81", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(218, "81", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(219, "86", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(220, "86", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(221, "90", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(222, "91", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(223, "91", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(224, "93", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(225, "93", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(226, "", 9999, AREA_SUBURBAN, 0, "", ""),
                        Timetable(227, "", 9999, AREA_SUBURBAN, 0, "", ""),

                        // Trogir i Solta
                        Timetable(301, "41", 9999, AREA_TROGIR, 0, "", ""),
                        Timetable(302, "41", 9999, AREA_TROGIR, 0, "", ""),
                        Timetable(303, "42", 9999, AREA_TROGIR, 0, "", ""),
                        Timetable(304, "42", 9999, AREA_TROGIR, 0, "", ""),
                        Timetable(305, "44", 9999, AREA_TROGIR, 0, "", ""),
                        Timetable(306, "44", 9999, AREA_TROGIR, 0, "", ""),
                        Timetable(307, "45", 9999, AREA_TROGIR, 0, "", ""),
                        Timetable(308, "45", 9999, AREA_TROGIR, 0, "", ""),
                        Timetable(309, "48", 9999, AREA_TROGIR, 0, "", ""),
                        Timetable(310, "48", 9999, AREA_TROGIR, 0, "", ""),
                        Timetable(311, "49", 9999, AREA_TROGIR, 0, "", ""),
                        Timetable(312, "49", 9999, AREA_TROGIR, 0, "", ""),
                        Timetable(313, "50", 9999, AREA_TROGIR, 0, "", ""),
                        Timetable(314, "50", 9999, AREA_TROGIR, 0, "", ""),
                        Timetable(315, "51", 9999, AREA_TROGIR, 0, "", ""),
                        Timetable(316, "51", 9999, AREA_TROGIR, 0, "", ""),
                        Timetable(317, "52", 9999, AREA_TROGIR, 0, "", ""),
                        Timetable(318, "52", 9999, AREA_TROGIR, 0, "", ""),

                        Timetable(401, "", 9999, AREA_SOLTA, 0, "", ""),
                        Timetable(402, "", 9999, AREA_SOLTA, 0, "", ""),
                        Timetable(403, "", 9999, AREA_SOLTA, 0, "", ""),
                        Timetable(404, "", 9999, AREA_SOLTA, 0, "", "")
                )

        }
}