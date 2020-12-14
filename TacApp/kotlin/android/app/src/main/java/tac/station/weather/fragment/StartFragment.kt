package tac.station.weather.fragment

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.realm.Realm
import retrofit2.Call
import tac.station.weather.NavigationHost
import tac.station.weather.R
import tac.station.weather.config.ServiceWeatherStation
import tac.station.weather.config.WeatherStationApi
import tac.station.weather.listener.StartFragmentListener
import tac.station.weather.model.Ville
import java.io.ByteArrayOutputStream


/**
 * Fragment representing the login screen for Shrine.
 */
class StartFragment : Fragment(), StartFragmentListener {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.weather_login_fragment, container, false)
        val getMeteoTask = GetMeteoTask(this)
        getMeteoTask.execute()
        return view
    }

    override fun onSyncFinish() {
        (activity as NavigationHost).navigateTo(ProductGridFragment(), true)
    }

    private class GetMeteoTask(var startFragmentListener: StartFragmentListener) : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg p0: Void?): Boolean {
            getInfoWS()
            return true
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
            startFragmentListener.onSyncFinish()
        }

        private fun getInfoWS() {
            /**
             * TODO taper dans un fichier mais pour le moment j'ai la flemme
             */
            val villes = ArrayList<String>()
            villes.add("Lille")
            villes.add("Paris")
            villes.add("Lyon")
            villes.add("Nice")
            villes.add("Bordeaux")
            villes.add("Toulouse")
            villes.add("Toulon")
            villes.add("Bailleul")
            villes.add("Steenvoorde")
            villes.add("Godewaersvelde")
            villes.add("Tourcoing")
            villes.add("Menton")
            villes.add("Lens")
            val resultVille: ArrayList<Ville> = ArrayList()
            val realm = Realm.getDefaultInstance()
            for (villeTmp in villes) {
                val service: ServiceWeatherStation = WeatherStationApi.getService()
                val call: Call<Ville> = service.getWeatherByCity(villeTmp, ServiceWeatherStation.API_KEY, ServiceWeatherStation.API_LANG)
                val villeData = call.execute().body()
                if (villeData != null) {
                    try {
                        val managedVille = realm.where(Ville::class.java).equalTo("id", villeData.id).findFirst()
                        if (managedVille != null){
                            villeData.isFavorite = managedVille.isFavorite
                        }
                    } catch (ioe: Exception) {
                        ioe.printStackTrace()
                    }
                    //Convert Kelvin en degre ℃ =K - 273.15
                    villeData.main.feels_like = villeData.main.feels_like?.minus(273.15)
                    villeData.main.temp = villeData.main.temp?.minus(273.15)
                    villeData.main.temp_min = villeData.main.temp_min?.minus(273.15)
                    villeData.main.temp_max = villeData.main.temp_max?.minus(273.15)

                    villeData.wind.speed = villeData.wind.speed?.times(3.6)

                    /**
                     * Temperature
                     */
                    if (villeData.main.temp.toString().length > 5)
                        villeData.temperature = villeData.main.temp.toString().substring(0,5) + " °C"
                    else
                        villeData.temperature = villeData.main.temp.toString() + " °C"

                    /**
                     * Ressenti
                     */
                    if (villeData.main.feels_like.toString().length > 5)
                        villeData.ressenti = villeData.main.feels_like.toString().substring(0,5) + " °C"
                    else
                        villeData.ressenti = villeData.main.feels_like.toString() + " °C"

                    /**
                     * Minimal
                     */
                    if (villeData.main.temp_max.toString().length > 5)
                        villeData.maximal = villeData.main.temp_max.toString().substring(0,5) + " °C"
                    else
                        villeData.maximal = villeData.main.temp_max.toString() + " °C"

                    /**
                     * Maximal
                     */
                    if (villeData.main.temp_min.toString().length > 5)
                        villeData.minimal = villeData.main.temp_min.toString().substring(0,5) + " °C"
                    else
                        villeData.minimal = villeData.main.temp_min.toString() + " °C"

                    villeData.description = villeData.weather[0]?.description

                    villeData.pression = villeData.main.pressure.toString() + " Pa"
                    villeData.humidite = villeData.main.humidity.toString() + " g/m3"

                    var icon: Bitmap?
                    try {
                        val input = java.net.URL("http://openweathermap.org/img/wn/" + villeData.weather[0]?.icon + "@2x.png").openStream()
                        icon = BitmapFactory.decodeStream(input)
                        val stream = ByteArrayOutputStream()
                        icon.compress(Bitmap.CompressFormat.PNG, 100, stream)
                        val byteArray: ByteArray = stream.toByteArray()
                        villeData.icon = byteArray
                    } catch (e: Exception) {
                        Log.e("Error", e.message)
                        e.printStackTrace();
                    }
                    resultVille.add(villeData)
                }
            }

            try {
                Realm.getDefaultInstance().use { realm2 ->
                    realm2.executeTransaction { realm2.copyToRealmOrUpdate(resultVille) }
                }
            } catch (ioe: Exception) {
                ioe.printStackTrace()
            }
        }
    }
}
