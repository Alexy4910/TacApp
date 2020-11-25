package tac.station.weather.model

import io.realm.RealmObject

/**
 * Class englobant les données général de la météo
 */
open class Weather: RealmObject() {
    /**
     * id
     */
    var id:Int? = null
    /**
     * Le main qui décrit la météo en un mot ex: "Ciel clair" etc..
     */
    var main:String? = null
    /**
     * Une description de la météo
     */
    var description:String? = null
    /**
     * Un identifiant d'icon que je récupère par la suite
     */
    var icon:String? = null
}