package com.am.stbus.screens.information

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.am.stbus.R
import com.am.stbus.networking.models.Information

class InformationListViewModel(private val context: Context) : ViewModel() {

    private val _informationList = MutableLiveData<List<Information>>()

    val informationList: LiveData<List<Information>>
        get() = _informationList

    init {
        _informationList.postValue(generateInformationList())
    }

    private fun generateInformationList(): List<Information> {
        return listOf(
                Information(0, context.getString(R.string.information_news_title), context.getString(R.string.information_news_desc)),
                Information(1, context.getString(R.string.informacije_gmaps_naslov), context.getString(R.string.informacije_gmaps_opis)),
                Information(2, context.getString(R.string.informacije_karta_grad_naslov), context.getString(R.string.informacije_karta_grad_opis)),
                Information(3, context.getString(R.string.informacije_tarifne_zone_naslov), context.getString(R.string.informacije_tarifne_zone_opis)),
                Information(4, context.getString(R.string.informacije_karta_prigrad_naslov), context.getString(R.string.informacije_karta_prigrad_opis)),
                Information(5, context.getString(R.string.informacije_tarifne_zone_naslov), context.getString(R.string.informacije_tarifne_zone_opis)),
                Information(6, context.getString(R.string.informacije_prometsplit_naslov), context.getString(R.string.informacije_prometsplit_opis)),
                Information(7, context.getString(R.string.informacije_parking_naslov), context.getString(R.string.informacije_parking_opis)),
                Information(8, context.getString(R.string.informacije_garaze_naslov), context.getString(R.string.informacije_garaze_opis))
        )
    }

}