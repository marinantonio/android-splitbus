package com.am.stbus.fragments

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.am.stbus.R
import kotlinx.android.synthetic.main.fragment_main.*

class ObavijestFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main,
                container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        contact.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "antoniomarinnn@gmail.com", null))
            var debug_info = "\n\n\n Informacije o uredaju \n --------------------------------"
            try {
                debug_info += "\n Split Bus verzija: " + context!!.getPackageManager().getPackageInfo(context!!.getPackageName(), 0).versionName
            } catch (nne: PackageManager.NameNotFoundException) {
                nne.printStackTrace()
            }

            debug_info += "\n Android: " + Build.VERSION.RELEASE + " (API " + Build.VERSION.SDK_INT + ") \n Model: " + Build.MODEL + " \n Proizvod: " + Build.PRODUCT + " \n Uredaj: " + Build.DEVICE
            emailIntent.putExtra(Intent.EXTRA_TEXT, debug_info)
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Split Bus")
            startActivity(Intent.createChooser(emailIntent, "Email"))
        }
    }

}