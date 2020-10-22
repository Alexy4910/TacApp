package tac.station.weather.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tac.station.weather.adapter.viewHolder.RecyclerViewViewHolder
import tac.station.weather.databinding.WeatherRecyclerViewItemBinding
import tac.station.weather.model.Meteo

/**
 * Simple adapter to display Email's in MainActivity.
 */
class RecyclerViewAdapter(
        private val listener: RecyclerViewAdapterListener,
        private var listeMeteo: ArrayList<Meteo>
) : RecyclerView.Adapter<RecyclerViewViewHolder>() {

    interface RecyclerViewAdapterListener {
        fun onEmailClicked(cardView: View, meteo: Meteo)
        fun onEmailStarChanged(meteo: Meteo, newValue: Boolean)
        fun onEmailArchived(meteo: Meteo)
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
}