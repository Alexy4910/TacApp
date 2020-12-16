package tac.station.weather.fragment

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.realm.Realm
import tac.station.weather.R
import tac.station.weather.databinding.WeatherDetailFragmentBinding
import tac.station.weather.model.Ville
import java.lang.Exception


class DetailVilleFragment(private var ville: Ville) : Fragment() {

    private lateinit var binding: WeatherDetailFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = WeatherDetailFragmentBinding.inflate(inflater, container, false)
        binding.ville = ville
        binding.fragment = this
        val bitmap = BitmapFactory.decodeByteArray(ville.icon, 0, ville.icon.size)
        binding.imgVille.setImageBitmap(bitmap)

        return binding.root
    }

    fun onBackPressed(){
        activity?.onBackPressed()
    }

    fun addFavori(){
        if (ville.isFavorite){
            ville.isFavorite = false
            binding.favoriButton.text = getString(R.string.ajout_favori)
        }else{
            ville.isFavorite = true
            binding.favoriButton.text = getString(R.string.retirer_des_favoris)
        }

        val realm = Realm.getDefaultInstance()
        realm.use { realm1 ->
            realm1.executeTransaction { realm1.copyToRealmOrUpdate(ville) }
        }
    }
}
