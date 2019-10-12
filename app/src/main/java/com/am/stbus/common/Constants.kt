package com.am.stbus.common

object Constants {
    const val PROMET_URL = "http://www.promet-split.hr/"
    const val PROMET_NOVOSTI_URL = "http://www.promet-split.hr/obavijesti"

    // Informacije
    const val KARTA_GRAD_URL = "https://www.dropbox.com/s/wdh2p8tbfzbtk5b/kartagrad.png?dl=1"
    const val KARTA_PRIGRAD_URL = "https://www.dropbox.com/s/0k6c96k4e261kwf/kartaprigrad.png?dl=1"
    const val TARIFNE_URL = "https://www.dropbox.com/s/ujsw5xjicx7i0az/tarifnezone.png?dl=1"
    const val PARKING_URL = "http://www.splitparking.hr/parkiralista"
    const val GARAZE_URL = "http://www.splitparking.hr/garaze"

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
}

val smt = "<iframe class=\"nextbike_content_iframe\" id=\"nextbikemap\" name=\"nextbikemap\" " +
        "src=\"https://iframe.nextbike.net/reservation/?lat=43.5162&amp;lng=16.4637&amp;zoom=12&amp;height=400" +
        "&amp;maponly=1&amp;language=hr&amp;redirect_signup=https://secure.nextbike.net/gt/hr/split/registrirajse/\" " +
        "width=\"100%\" height=\"400\" frameborder=\"0\"></iframe>"
