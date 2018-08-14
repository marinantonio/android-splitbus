package com.am.stbus.helpers

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

open class Utils {

    open fun onlineStatus (context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

}