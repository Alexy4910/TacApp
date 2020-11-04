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
            var ville = Ville()
            ville.name = "Lille"
            var ville2 = Ville()
            ville2.name = "Paris"
            var ville3 = Ville()
            ville3.name = "Lyon"
            var villes = ArrayList<Ville>()
            villes.add(ville)
            villes.add(ville2)
            villes.add(ville3)
            val resultVille: ArrayList<Ville> = ArrayList()
            for (villeTmp in villes) {
                val service: ServiceWeatherStation = WeatherStationApi.getService()
                val call: Call<Ville> = service.getWeatherByCity(villeTmp.name, ServiceWeatherStation.API_KEY, ServiceWeatherStation.API_LANG)
                val villeData = call.execute().body()
                if (villeData != null) {
                    //Convert Kelvin en degre ℃ =K - 273.15
                    villeData.main.feels_like = villeData.main.feels_like?.minus(273.15)
                    villeData.main.temp = villeData.main.temp?.minus(273.15)
                    villeData.main.temp_min = villeData.main.temp_min?.minus(273.15)
                    villeData.main.temp_max = villeData.main.temp_max?.minus(273.15)

                    villeData.wind.speed = villeData.wind.speed?.times(3.6)

                    villeData.tempearature = villeData.main.temp.toString() + "°C"
                    villeData.description = villeData.weather[0]?.description

                    var icon: Bitmap? = null
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
                Realm.getDefaultInstance().use { realm ->
                    realm.executeTransaction { realm.copyToRealmOrUpdate(resultVille) }
                }
            } catch (ioe: Exception) {
                ioe.printStackTrace()
            }
        }
    }

    /**
     * private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
    this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
    String urldisplay = urls[0];
    Bitmap mIcon11 = null;
    try {
    InputStream in = new java.net.URL(urldisplay).openStream();
    mIcon11 = BitmapFactory.decodeStream(in);
    } catch (Exception e) {
    Log.e("Error", e.getMessage());
    e.printStackTrace();
    }
    return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
    bmImage.setImageBitmap(result);
    }
    }
     */
}
