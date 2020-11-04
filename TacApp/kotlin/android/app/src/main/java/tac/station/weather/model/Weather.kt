package tac.station.weather.model

import io.realm.RealmObject

open class Weather: RealmObject() {
    var id:Int? = null
    var main:String? = null
    var description:String? = null
    var icon:String? = null
}