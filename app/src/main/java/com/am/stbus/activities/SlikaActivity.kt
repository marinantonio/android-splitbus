package com.am.stbus.activities

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.am.stbus.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_slika.*


class SlikaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slika)

        val naslov = intent.getStringExtra("naslov")
        val url = intent.getStringExtra("url")

        setSupportActionBar(toolbar)
        assert(supportActionBar != null)
        run {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = naslov
            toolbar.setNavigationOnClickListener { finish() }
        }

        showLoading(true)

        Glide.with(this).asDrawable().load(url)
                .listener(object : RequestListener<Drawable> {
                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        showLoading(false)
                        return false
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        showLoading(false)
                        return false
                    }
                })
                .into(iv_slika)
    }

    private fun showLoading(boolean: Boolean) {
        if (boolean) {
            iv_slika.visibility = View.INVISIBLE
            pb_loading.visibility = View.VISIBLE
        } else {
            iv_slika.visibility = View.VISIBLE
            pb_loading.visibility = View.INVISIBLE
        }
    }

}
