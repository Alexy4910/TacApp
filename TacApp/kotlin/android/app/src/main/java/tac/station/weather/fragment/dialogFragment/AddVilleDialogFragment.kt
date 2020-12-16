package tac.station.weather.fragment.dialogFragment

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tac.station.weather.MainActivity
import tac.station.weather.config.ServiceWeatherStation
import tac.station.weather.config.WeatherStationApi
import tac.station.weather.databinding.AddVilleDialogFragmentBinding
import tac.station.weather.databinding.RechercheDialogFragmentBinding
import tac.station.weather.listener.AddVilleListener
import tac.station.weather.listener.RechercheListener
import tac.station.weather.model.Ville
import java.io.ByteArrayOutputStream


class AddVilleDialogFragment() : DialogFragment() {

    private lateinit var binding: AddVilleDialogFragmentBinding

    var listener: AddVilleListener? = null

    override fun onCreateDialog(@Nullable savedInstanceState: Bundle?): Dialog {
        binding = AddVilleDialogFragmentBinding.inflate(LayoutInflater.from(context))
        binding.fragment = this
        val builder = AlertDialog.Builder(context)
        builder.setView(binding.root)
        return builder.create()
    }

    fun onAddVilleClick() {
        (activity as MainActivity).displayProgressDialog()

        val ville = binding.addVilleName.text.toString()
        var isPresente = false
        try {
            Realm.getDefaultInstance().use { realm ->
                val managedVilles = realm.where(Ville::class.java).findAll()
                if (managedVilles != null) {
                    val villes = realm.copyFromRealm(managedVilles)
                    for (vi in villes) {
                        if (vi.name == ville) {
                            Toast.makeText(context, "Ville déjà présente", Toast.LENGTH_LONG).show()
                            isPresente = true
                        }
                    }
                    if (!isPresente) {
                        val service: ServiceWeatherStation = WeatherStationApi.getService()
                        val call: Call<Ville> = service.getWeatherByCity(ville, ServiceWeatherStation.API_KEY, ServiceWeatherStation.API_LANG)
                        call.enqueue(object : Callback<Ville> {
                            override fun onResponse(call: Call<Ville>, response: Response<Ville>) {
                                val villeData = response.body()
                                if (villeData != null) {
                                    setupData(villeData)
                                }
                            }

                            override fun onFailure(call: Call<Ville>, t: Throwable) {
                                Log.e("FAIL", "Error : $t")
                                (activity as MainActivity).dismissProgressDialog()

                            }
                        })
                    }
                }
            }
        } catch (ioe: Exception) {
            ioe.printStackTrace()
        }
    }

    private fun setupData(villeData: Ville) {
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
            villeData.temperature = villeData.main.temp.toString().substring(0, 5) + " °C"
        else
            villeData.temperature = villeData.main.temp.toString() + " °C"

        /**
         * Ressenti
         */
        if (villeData.main.feels_like.toString().length > 5)
            villeData.ressenti = villeData.main.feels_like.toString().substring(0, 5) + " °C"
        else
            villeData.ressenti = villeData.main.feels_like.toString() + " °C"

        /**
         * Minimal
         */
        if (villeData.main.temp_max.toString().length > 5)
            villeData.maximal = villeData.main.temp_max.toString().substring(0, 5) + " °C"
        else
            villeData.maximal = villeData.main.temp_max.toString() + " °C"

        /**
         * Maximal
         */
        if (villeData.main.temp_min.toString().length > 5)
            villeData.minimal = villeData.main.temp_min.toString().substring(0, 5) + " °C"
        else
            villeData.minimal = villeData.main.temp_min.toString() + " °C"

        villeData.description = villeData.weather[0]?.description

        villeData.pression = villeData.main.pressure.toString() + " Pa"
        villeData.humidite = villeData.main.humidity.toString() + " g/m3"

        val thread = Thread {
            try {
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
                Realm.getDefaultInstance().use { realm2 ->
                    realm2.executeTransaction { realm2.copyToRealmOrUpdate(villeData) }
                }
                (activity as MainActivity).dismissProgressDialog()
                dismiss()
                listener?.villeAdded()
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
        thread.start()
    }
}
