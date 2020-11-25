package tac.station.weather.model

import io.realm.RealmObject

/**
 * Class pour récupérer les informations principales de la météo
 */
open class Main: RealmObject() {
    /**
     * La température
     */
    var temp:Double? = null
    /**
     * La température ressenti
     */
    var feels_like:Double? = null
    /**
     * La température minimum
     */
    var temp_min:Double? = null
    /**
     * La température maximum
     */
    var temp_max:Double? = null
    /**
     * La pression
     */
    var pressure:Double? = null
    /**
     * L'humidité
     */
    var humidity:Double? = null
}