package tac.station.weather.model

import io.realm.RealmObject

open class Main: RealmObject() {
    var temp:Double? = null
    var feels_like:Double? = null
    var temp_min:Double? = null
    var temp_max:Double? = null
    var pressure:Double? = null
    var humidity:Double? = null
}