package tac.station.weather.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import tac.station.weather.adapter.viewHolder.RecyclerViewViewHolder
import tac.station.weather.databinding.WeatherRecyclerViewItemBinding
import tac.station.weather.model.Ville

/**
 * Simple adapter to display Email's in MainActivity.
 */
class RecyclerViewAdapter(
        private val listener: RecyclerViewAdapterListener,
        private var listeMeteo: List<Ville>
) : RecyclerView.Adapter<RecyclerViewViewHolder>() {

    interface RecyclerViewAdapterListener {
        fun onVilleClicked(cardView: View, ville: Ville)
        fun onAddFavorite(ville: Ville, newValue: Boolean)
        fun onGoDetailVilleGrid(ville: Ville)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewViewHolder {
        return RecyclerViewViewHolder(
                WeatherRecyclerViewItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                ),
                listener
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewViewHolder, position: Int) {
        holder.bind(listeMeteo[position])
    }

    override fun getItemCount(): Int {
        return listeMeteo.size
    }

    fun setVilles(listeMeteoNew: ArrayList<Ville>){
        listeMeteo = listeMeteoNew
    }
}