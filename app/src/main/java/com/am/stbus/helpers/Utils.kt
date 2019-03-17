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

package com.am.stbus.helpers

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.preference.PreferenceManager
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import com.am.stbus.BuildConfig
import com.am.stbus.R

open class Utils {

    open fun onlineStatus(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

    open fun cleanVozniRed(context: Context, vozniRed: String): String {
        var cleanString = vozniRed.replace(",", "").replace(" ", "\t\t")
        cleanString = cleanString.substring(1, cleanString.length - 1)
        val whitoutSpaces = cleanString.replace("\\s+".toRegex(), " ")
        if (whitoutSpaces.length == 1)
            cleanString = context.getString(R.string.vozni_red_nema_polazaka)
        return cleanString
    }

    open fun reportIssue(context: Context, naziv: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "antoniomarinnn@gmail.com", null))
        var debugInfo = "\n\n\n Informacije o uredaju \n --------------------------------"
        debugInfo += "\n Linija: $naziv \n Split Bus verzija: " + BuildConfig.VERSION_NAME
        debugInfo += "\n Android: " + Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ") \n Model: " + Build.MODEL + " \n Proizvod: " + Build.PRODUCT + " \n Uredaj: " + Build.DEVICE
        emailIntent.putExtra(Intent.EXTRA_TEXT, debugInfo)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Split Bus")
        context.startActivity(Intent.createChooser(emailIntent, "Email"))
    }

    open fun openUrl(context: Context, url: String) {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val prefOption = sharedPreferences.getBoolean("open_urls", true)
        if (prefOption) {
            val customTabsIntent : CustomTabsIntent = buildCustomTabsIntent(context)
            //customTabsIntent.launchUrl()
            customTabsIntent.launchUrl(context, Uri.parse(url))
        } else {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
        }
    }

    private fun buildCustomTabsIntent(context: Context): CustomTabsIntent {
        val intentBuilder = CustomTabsIntent.Builder()
        // Show the title
        intentBuilder.setShowTitle(true)
        // Set the color of Toolbar
        intentBuilder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
        return intentBuilder.build()
    }

    fun pxToDp(px: Int): Int {
        return (px / Resources.getSystem().displayMetrics.density).toInt()
    }

    fun dpToPx(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }


    // Koristi se za VozniRedActivity, ako Promet promjeni naziv linije da ne resetam DB bolje odavde povuć
    fun getWebName(id: Int): String {
        var webLineName = "default" // Samo da ne rusi ako slucajno ne dobije odavde
        when (id) {
            1 -> webLineName = "3 BRDA - LOVRINAC"
            2 -> webLineName = "3 LOVRINAC - BRDA"
            3 -> webLineName = "6 KILA - VUKOVARSKA - HNK - KILA"
            4 -> webLineName = "7 ŽNJAN - ZVONČAC - ŽNJAN"
            5 -> webLineName = "8 ŽNJAN - TRŽNICA - ZVONČAC - TRŽNICA - ŽNJAN"
            6 -> webLineName = "9 RAVNE NJIVE - TR.LUKA - RAVNE NJIVE"
            7 -> webLineName = "11 RAVNE NJIVE-PUJANKE-HNK-RAVNE NJIVE"
            8 -> webLineName = "12 SV. FRANE - BENE"
            9 -> webLineName = "12 BENE - (MEJE) - SV.FRANE"
            10 -> webLineName = "14 RAVNE NJIVE-DUBROVAČKA-HNK"
            11 -> webLineName = "15 DUILOVO - ŽNJAN - TR. LUKA - DUILOVO"
            12 -> webLineName = "17 SPINUT - LORA - TRSTENIK - LORA -SPINUT"
            13 -> webLineName = "18 BRNIK - HNK - BRNIK"
            14 -> webLineName = "20 RAVNE NJIVE - ZVONČAC"
            15 -> webLineName = "21 SV.FRANE - MEJE - SV.FRANE"
            16 -> webLineName = "24 SPLIT - TTTS"
            17 -> webLineName = "24 TTTS - SPLIT"
            18 -> webLineName = "25 SPLIT - STOBREČ"
            19 -> webLineName = "25 STOBREČ - SPLIT"
            20 -> webLineName = "26 SPLIT - KAMEN"
            21 -> webLineName = "26 KAMEN - SPLIT"
            22 -> webLineName = "27 SPLIT - KOREŠNICA - ŽRNOVNICA"
            23 -> webLineName = "27 ŽRNOVNICA - KOREŠNICA - SPLIT"
            24 -> webLineName = "39 LORA - POLJIČKA - TTTS - LORA"
            25 -> webLineName = "40 TRAJ. LUKA - KILA - TRAJ. LUKA"

            26 -> webLineName = "1 STARINE - HNK - STARINE"
            27 -> webLineName = "2 K.SUĆURAC(STRINJE) - TR. LUKA - K.SUĆURAC(STRINJE)"
            28 -> webLineName = "5 DRAČEVAC - HNK - DRAČEVAC"
            29 -> webLineName = "5A DRAČEVAC - SOLIN - VISOKA - HNK - DRAČEVAC"
            30 -> webLineName = "10 JAPIRKO - TRAJ. LUKA - JAPIRKO"
            31 -> webLineName = "16 NINČEVIĆI - HNK - NINČEVIĆI"
            32 -> webLineName = "22 KLIS MEGDAN-G.RUPOTINA-HNK-KLIS MEGDAN"
            33 -> webLineName = "23 HNK - SOLIN - HNK"
            34 -> webLineName = "28 SPLIT - SITNO G. - DUBRAVA"
            35 -> webLineName = "28 DUBRAVA - SITNO G. - SPLIT"
            36 -> webLineName = "29 SPLIT - TUGARE - NAKLICE"
            37 -> webLineName = "29 NAKLICE - TUGARE - SPLIT"
            38 -> webLineName = "30 SPLIT - PODSTRANA (MUTOGRAS) - SPLIT"
            39 -> webLineName = "30 PODSTRANA (MUTOGRAS ) - SPLIT"
            40 -> webLineName = "31 SPLIT - VRANJIC"
            41 -> webLineName = "31 VRANJIC - SPLIT"
            42 -> webLineName = "32 SPLIT - KUČINE"
            43 -> webLineName = "32 KUČINE - SPLIT"
            44 -> webLineName = "33 SPLIT - KOSA"
            45 -> webLineName = "33 KOSA - SPLIT"
            46 -> webLineName = "34 SPLIT - KLIS (MEGDAN)"
            47 -> webLineName = "34 KLIS (MEGDAN) - SPLIT"
            48 -> webLineName = "35 SPLIT - DUGOPOLJE"
            49 -> webLineName = "35 DUGOPOLJE - SPLIT"
            50 -> webLineName = "36 SPLIT - KOPRIVNO"
            51 -> webLineName = "36 KOPRIVNO - SPLIT"
            52 -> webLineName = "37 SPLIT - AIRPORT - TROGIR"
            53 -> webLineName = "37 TROGIR - AIRPORT - SPLIT"
            54 -> webLineName = "38 SPLIT - K. STARI - RESNIK"
            55 -> webLineName = "38 RESNIK - K.STARI - SPLIT"
            56 -> webLineName = "60 SPLIT - OMIŠ - RAVNIČKI MOST"
            57 -> webLineName = "60 RAVNIČKI MOST - OMIŠ - SPLIT"
            58 -> webLineName = "ŽELJEZNIČKA STANICA - KAŠTEL STARI"
            59 -> webLineName = "KAŠTEL STARI - ŽELJEZNIČKA STANICA"
            60 -> webLineName = "TROGIR - SPLIT (direktna)"
            // 61 -> webLineName = "40 TRAJ. LUKA - KILA - TRAJ. LUKA"

            62 -> webLineName = "67 SPLIT - KOTLENICE - DOLAC D. - DOLAC G."
            63 -> webLineName = "67 DOLAC G. - DOLAC D. - KOTLENICE - SPLIT"
            64 -> webLineName = "68 SPLIT-TUGARE-BLATO-ŠESTANOVAC"
            65 -> webLineName = "68 ŠESTANOVAC-BLATO-TUGARE-SPLIT"
            66 -> webLineName = "69 SPLIT-BISKO-TRNBUSI-BLATO-ŠESTANOVAC"
            67 -> webLineName = "69 ŠESTANOVAC-BLATO-TRNBUSI-BISKO-SPLIT"
            68 -> webLineName = "71 SPLIT - NEORIĆ - SUTINA"
            69 -> webLineName = "71 SUTINA - NEORIĆ - SPLIT"
            70 -> webLineName = "73 SPLIT-MUĆ / MUĆ-OGORJE"
            71 -> webLineName = "73 OGORJE - MUĆ / MUĆ - SPLIT"
            72 -> webLineName = "76 SPLIT - CRIVAC- KLJACI"
            73 -> webLineName = "76 KLJACI - CRIVAC - SPLIT"
            74 -> webLineName = "77 SPLIT - MUĆ - CRIVAC"
            75 -> webLineName = "77 CRIVAC - MUĆ - SPLIT"
            // 76 -> webLineName = "40 TRAJ. LUKA - KILA - TRAJ. LUKA"
            // 77 -> webLineName = "40 TRAJ. LUKA - KILA - TRAJ. LUKA"
            78 -> webLineName = "80 SPLIT - KLJACI - DRNIŠ"
            79 -> webLineName = "80 DRNIŠ - KLJACI - SPLIT"
            80 -> webLineName = "81 SPLIT - BRŠTANOVO - NISKO"
            81 -> webLineName = "81 NISKO - BRŠTANOVO - SPLIT"
            82 -> webLineName = "86 SPLIT - KONJSKO - LEĆEVICA - KLADNJICE"
            83 -> webLineName = "86 KLADNJICE - LEĆEVICA - KONJSKO - SPLIT"
            // 84
            // 85
            // 86
            87 -> webLineName = "90 SITNO - BOGDANOVIĆI - RADOŠIĆ - MALAČKA - K. STARI"
            88 -> webLineName = "91 K. STARI - PLANO - PRIMORSKI DOLAC - SITNO - BOGDANOVIĆI - DIVOJEVIĆI"
            89 -> webLineName = "91 DIVOJEVIĆI - BOGDANOVIĆI - SITNO - PRIMORSKI DOLAC - PLANO - K. STARI"
            90 -> webLineName = "93 K. STARI - MALAČKA - TEŠIJE - ĐIRLIĆI - ŠERIĆI"
            91 -> webLineName = "93 ŠERIĆI - ĐIRLIĆI - TEŠIJE - MALAČKA - K. STARI"
            92 -> webLineName = "KAŠTEL STARI - RUDINE"
            93 -> webLineName = "RUDINE - KAŠTEL STARI"

            //4 Trogir
            94 -> webLineName = "41 TROGIR - PLANO - MALJKOVIĆI"
            95 -> webLineName = "41 MALJKOVIĆI - PLANO - TROGIR"
            96 -> webLineName = "42 TROGIR - SLATINE"
            97 -> webLineName = "42 SLATINE - TROGIR"
            98 -> webLineName = "44 TROGIR - OKRUG DONJI"
            99 -> webLineName = "44 OKRUG DONJI - TROGIR"
            100 -> webLineName = "45 TROGIR - HOTEL MEDENA - VRANJICA"
            101 -> webLineName = "45 VRANJICA - HOTEL MEDENA - TROGIR"
            102 -> webLineName = "47 TROGIR - HOTEL MEDENA - VRSINE - MARINA - TROGIR"
            103 -> webLineName = "48 TROGIR - HOTEL MEDENA - MARINA - DOGRADE - VRSINE - TROGIR"
            104 -> webLineName = "49 TROGIR - HOTEL MEDENA - VINIŠĆE"
            105 -> webLineName = "49 VINIŠĆE - HOTEL MEDENA - TROGIR"
            106 -> webLineName = "50 TROGIR - HOTEL MEDENA - SEVID"
            107 -> webLineName = "50 SEVID - HOTEL MEDENA - TROGIR"
            108 -> webLineName = "51 TROGIR - LJUBITOVICA"
            109 -> webLineName = "51 LJUBITOVICA - TROGIR"
            110 -> webLineName = "52 TROGIR - VINOVAC"
            111 -> webLineName = "52 VINOVAC - TROGIR"

            //5 solta
            112 -> webLineName = "MASLINICA - DONJE SELO - SREDNJE SELO - GROHOTE - ROGAČ"
            113 -> webLineName = "ROGAČ - GROHOTE - SREDNJE SELO - DONJE SELO - MASLINICA"
            114 -> webLineName = "STOMORSKA - GORNJE SELO - NEČUJAM - GROHOTE - ROGAČ"
            115 -> webLineName = "ROGAČ - GROHOTE - NEČUJAM - GORNJE SELO - STOMORSKA"
        }
        return webLineName
    }

    // Koristi se za google maps, za alert dialogove
    fun getFullName(context: Context, line: String): String {
        var completeLineName = ""
        when (line) {
            "1" -> completeLineName = context.getString(R.string.bus1)
            "2" -> completeLineName = context.getString(R.string.bus2)
            "3" -> completeLineName = context.getString(R.string.bus31)
            "5" -> completeLineName = context.getString(R.string.bus5)
            "5A" -> completeLineName = context.getString(R.string.bus51)
            "6" -> completeLineName = context.getString(R.string.bus6)
            "7" -> completeLineName = context.getString(R.string.bus7)
            "8" -> completeLineName = context.getString(R.string.bus8)
            "9" -> completeLineName = context.getString(R.string.bus9)
            "10" -> completeLineName = context.getString(R.string.bus10)
            "11" -> completeLineName = context.getString(R.string.bus11)
            "12" -> completeLineName = context.getString(R.string.bus121)
            "14" -> completeLineName = context.getString(R.string.bus14)
            "15" -> completeLineName = context.getString(R.string.bus15)
            "16" -> completeLineName = context.getString(R.string.bus16)
            "17" -> completeLineName = context.getString(R.string.bus17)
            "18" -> completeLineName = context.getString(R.string.bus18)
            "20" -> completeLineName = context.getString(R.string.bus20)
            "21" -> completeLineName = context.getString(R.string.bus21)
            "22" -> completeLineName = context.getString(R.string.bus22)
            "23" -> completeLineName = context.getString(R.string.bus23)
            "24" -> completeLineName = context.getString(R.string.bus241)
            "25" -> completeLineName = context.getString(R.string.bus251)
            "26" -> completeLineName = context.getString(R.string.bus261)
            "27" -> completeLineName = context.getString(R.string.bus271)
            "28" -> completeLineName = context.getString(R.string.bus281)
            "29" -> completeLineName = context.getString(R.string.bus291)
            "31" -> completeLineName = context.getString(R.string.bus311)
            "32" -> completeLineName = context.getString(R.string.bus321)
            "33" -> completeLineName = context.getString(R.string.bus331)
            "34" -> completeLineName = context.getString(R.string.bus341)
            "35" -> completeLineName = context.getString(R.string.bus351)
            "39" -> completeLineName = context.getString(R.string.bus39)
            "40" -> completeLineName = context.getString(R.string.bus40)
            "30" -> completeLineName = context.getString(R.string.bus301)
            "37" -> completeLineName = context.getString(R.string.bus371)
            "38" -> completeLineName = context.getString(R.string.bus381)
            "60" -> completeLineName = context.getString(R.string.bus601)
            "67" -> completeLineName = context.getString(R.string.bus671)
            "36" -> completeLineName = context.getString(R.string.bus361)
            "68" -> completeLineName = context.getString(R.string.bus681)
            "71" -> completeLineName = context.getString(R.string.bus711)
            "72" -> completeLineName = context.getString(R.string.bus721)
            "76" -> completeLineName = context.getString(R.string.bus761)
            "77" -> completeLineName = context.getString(R.string.bus771)
            "80" -> completeLineName = context.getString(R.string.bus801)
            "81" -> completeLineName = context.getString(R.string.bus811)
            "73" -> completeLineName = context.getString(R.string.bus731)
            "86" -> completeLineName = context.getString(R.string.bus861)
            "88" -> completeLineName = context.getString(R.string.bus861)
            "91" -> completeLineName = context.getString(R.string.bus911)
            "93" -> completeLineName = context.getString(R.string.bus931)
        }
        return "$line $completeLineName" // Broj linije i naziv
    }

}