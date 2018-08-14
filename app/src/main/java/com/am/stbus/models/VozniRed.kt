package com.am.stbus.models

/**
 * Created by marin on 10.3.2018..
 */
class VozniRed {

    var id: Int = 0
    var naziv: String
    var web: Int = 0
    var gmaps: Int = 0
    var tip: Int = 0
    var nedavno: Int = 0


    constructor(id: Int, naziv: String, web: Int, imageName: Int) {
        this.id = id
        this.naziv = naziv
        this.web = web
        this.gmaps = imageName
    }

    constructor(id: Int, naziv: String, web: Int) {
        this.id = id
        this.naziv = naziv
        this.web = web
    }

    constructor(naziv: String, web: Int, gmaps: Int, tip: Int, nedavno: Int) {
        this.naziv = naziv
        this.web = web
        this.gmaps = gmaps
        this.tip = tip
        this.nedavno = nedavno
    }

    constructor(id: Int, naziv: String, web: Int, gmaps: Int, tip: Int, nedavno: Int) {
        this.id = id
        this.naziv = naziv
        this.web = web
        this.gmaps = gmaps
        this.tip = tip
        this.nedavno = nedavno
    }

}