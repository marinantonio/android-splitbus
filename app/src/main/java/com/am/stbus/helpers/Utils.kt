package com.am.stbus.helpers

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
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
        var debug_info = "\n\n\n Informacije o uredaju \n --------------------------------"
        try {
            debug_info += "\n Linija: $naziv \n Split Bus verzija: " + context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName
        } catch (nne: PackageManager.NameNotFoundException) {
            nne.printStackTrace()
        }

        debug_info += "\n Android: " + Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ") \n Model: " + Build.MODEL + " \n Proizvod: " + Build.PRODUCT + " \n Uredaj: " + Build.DEVICE
        emailIntent.putExtra(Intent.EXTRA_TEXT, debug_info)
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Split Bus")
        context.startActivity(Intent.createChooser(emailIntent, "Email"))
    }

}