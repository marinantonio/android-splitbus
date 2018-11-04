/*
 * MIT License
 *
 * Copyright (c) 2013 - 2018 Antonio Marin
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

package com.am.stbus.models

/**
 * Created by marin on 10.3.2018..
 */
class VozniRed {

    var id: Int = 0
    var naziv: String
    var web: Int = 0
    var gmaps: String
    var tip: Int = 0
    var nedavno: Int = 0

    constructor(naziv: String, web: Int, gmaps: String, tip: Int, nedavno: Int) {
        this.naziv = naziv
        this.web = web
        this.gmaps = gmaps
        this.tip = tip
        this.nedavno = nedavno
    }

    constructor(id: Int, naziv: String, web: Int, gmaps: String, tip: Int, nedavno: Int) {
        this.id = id
        this.naziv = naziv
        this.web = web
        this.gmaps = gmaps
        this.tip = tip
        this.nedavno = nedavno
    }

}