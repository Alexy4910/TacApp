package tac.station.weather.model

import io.realm.RealmObject

/**
 * Class pour récupérer les coordonnées
 */
open class Coordonne: RealmObject() {
    /**
     * Longitude
     */
    var lon:String? = null
    /**
     * Lattitude
     */
    var lat:String? = null
}