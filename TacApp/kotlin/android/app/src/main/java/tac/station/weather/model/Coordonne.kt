package tac.station.weather.model

import io.realm.RealmObject

open class Coordonne: RealmObject() {
    var lon:String? = null
    var lat:String? = null
}