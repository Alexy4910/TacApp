package tac.station.weather.model

import androidx.recyclerview.widget.DiffUtil
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class Meteo: RealmObject() {
    @PrimaryKey
    private var id = 0

    private var name: String? = null
    private var timezone = 0
    private var message: String? = null



    private var isFavorite = false;

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name
    }

    fun getTimezone(): Int {
        return timezone
    }

    fun setTimezone(timezone: Int) {
        this.timezone = timezone
    }

    fun setId(id: Int) {
        this.id = id
    }

    fun getId(): Int {
        return id
    }

    fun setMessage(message: String?) {
        this.message = message
    }

    fun getMessage(): String? {
        return message
    }


    fun setIsFavorite(favorite: Boolean) {
        this.isFavorite = favorite
    }

    fun getIsFavorite(): Boolean {
        return isFavorite
    }
}