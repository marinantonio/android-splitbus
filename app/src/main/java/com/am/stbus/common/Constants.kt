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

object Constants {

    const val DB_VERSION = 4

    const val PROMET_URL = "http://www.promet-split.hr"
    const val PROMET_NOVOSTI_URL = "http://www.promet-split.hr/obavijesti"

    // Timetable Base URLs
    const val AREA_CITY_URL = "$PROMET_URL/vozni-red/split/"
    const val AREA_URBAN_URL = "$PROMET_URL/vozni-red/urbano-podrucje"
    const val AREA_SUBURBAN_URL = "$PROMET_URL/vozni-red/prigradsko-podrucje"
    const val AREA_TROGIR_URL = "$PROMET_URL/vozni-red/trogir/"
    const val AREA_SOLTA_URL = "$PROMET_URL/vozni-red/otok-solta/"

    // Timetable string delimited
    const val EMA_DELIMITER = "emayz"
    const val DOWNLOADED_RECENTLY = 3L

    // O Aplikaciji
    const val GITHUB_URL = "https://github.com/marinantonio/android-splitbus"
    const val FACEBOOK_URL = "https://www.facebook.com/splitbuss/"
    const val LINKEDIN_URL = "https://www.linkedin.com/in/antoniomarin91/"
    const val WEBSITE_URL = "https://marinantonio.github.io/"

    const val NETWORK_REQUEST_TIMEOUT = 30000
}

object InformationConstants {

    const val TYPE_HEADER = 0
    const val TYPE_ITEM = 1

    const val ID_GENERAL_CATEGORY = 0
    const val ID_LATEST_NEWS = 1
    const val ID_TOURIST_INFO = 2
    const val ID_MAPS_CATEGORY = 3
    const val ID_GMAPS = 4
    const val ID_URBAN_MAP = 5
    const val ID_SUBURBAN_MAP = 6
    const val ID_TARIFF_ZONES_MAP = 7
    const val ID_WEBSITES_CATEGORY = 8
    const val ID_CYCLES = 9
    const val ID_PARKING = 10
    const val ID_GARAGES = 11
    const val ID_PROMET_WEB = 12

    // Informacije
    const val KARTA_GRAD_URL = "https://splitbuspublic.s3.eu-central-1.amazonaws.com/public/grad.jpg"
    const val KARTA_PRIGRAD_URL = "https://splitbuspublic.s3.eu-central-1.amazonaws.com/public/prigrad.jpg"
    const val TARIFNE_URL = "https://splitbuspublic.s3.eu-central-1.amazonaws.com/public/tarifne.jpg"
    const val NEXT_BIKE_URL = "https://www.nextbike.hr/hr/split/lokacije/"
    const val PARKING_URL = "http://www.splitparking.hr/parkiralista"
    const val GARAZE_URL = "http://www.splitparking.hr/garaze"

    const val NEXT_BIKE_IFRAME = "<iframe class=\"nextbike_content_iframe\" id=\"nextbikemap\" name=\"nextbikemap\" " +
            "src=\"https://iframe.nextbike.net/reservation/?lat=43.5162&amp;lng=16.4637&amp;zoom=12&amp;height=400" +
            "&amp;maponly=1&amp;language=hr&amp;redirect_signup=https://secure.nextbike.net/gt/hr/split/registrirajse/\" " +
            "width=\"100%\" height=\"400\" frameborder=\"0\"></iframe>"

}

