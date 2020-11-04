package tac.station.weather

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import io.realm.Realm
import io.realm.RealmConfiguration

class WeatherStationApplication : Application() {
    companion object {
        lateinit var instance: WeatherStationApplication
            private set
        const val REALM_DB_VERSION = 1
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)

        Realm.init(this)
        val config = RealmConfiguration.Builder()
                .schemaVersion(REALM_DB_VERSION.toLong())
                .deleteRealmIfMigrationNeeded()
                .build()
        Realm.setDefaultConfiguration(config)
    }

}