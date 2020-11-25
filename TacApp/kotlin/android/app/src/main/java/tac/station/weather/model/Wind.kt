package tac.station.weather.model

import io.realm.RealmObject

/**
 * Class pour récupérer la vitesse du vent
 */
open class Wind: RealmObject() {
    /**
     * Vitesse du vent
     */
    var speed:Double? = null
}