package com.am.stbus.models

/**
 * Created by marin on 10.3.2018..
 */
class Novost {

    var url: String
    var naslov: String
    var datum: String
    var sazetak: String


    constructor(url: String, naslov: String, datum: String, sazetak: String) {
        this.url = url
        this.naslov = naslov
        this.datum = datum
        this.sazetak = sazetak
    }
}