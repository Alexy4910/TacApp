package tac.station.weather

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate

class WeatherStationApplication : Application() {
    companion object {
        lateinit var instance: WeatherStationApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

}