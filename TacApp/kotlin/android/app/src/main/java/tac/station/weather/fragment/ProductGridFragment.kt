package tac.station.weather.fragment

import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialFadeThrough
import kotlinx.android.synthetic.main.weather_product_grid_fragment.*
import kotlinx.android.synthetic.main.weather_product_grid_fragment.view.*
import tac.station.weather.NavigationIconClickListener
import tac.station.weather.R
import tac.station.weather.adapter.RecyclerViewAdapter
import tac.station.weather.databinding.WeatherProductGridFragmentBinding
import tac.station.weather.model.Meteo
import tac.station.weather.setupRecyclerView.ReboundingSwipeActionCallback

class ProductGridFragment : Fragment(), RecyclerViewAdapter.RecyclerViewAdapterListener {

    private lateinit var binding: WeatherProductGridFragmentBinding

    private var meteoAdapter:  RecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        binding = WeatherProductGridFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set up the tool bar
        (activity as AppCompatActivity).setSupportActionBar(binding.appBar)
        binding.appBar.setNavigationOnClickListener(NavigationIconClickListener(
                activity!!,
                binding.productGrid,
                AccelerateDecelerateInterpolator(),
                ContextCompat.getDrawable(context!!, R.drawable.weather_branded_menu), // Menu open icon
                ContextCompat.getDrawable(context!!, R.drawable.weather_close_menu))) // Menu close icon


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.productGrid.background = context?.getDrawable(R.drawable.weather_product_grid_background_shape)
        }


        var meteo = Meteo()
        meteo.setMessage("LOULOULOU")
        var list = ArrayList<Meteo>()
        list.add(meteo)
        binding.recyclerView.apply {
            val itemTouchHelper = ItemTouchHelper(ReboundingSwipeActionCallback())
            itemTouchHelper.attachToRecyclerView(this)
        }
        meteoAdapter = RecyclerViewAdapter(this, list)
        recycler_view.adapter = meteoAdapter
    }
    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.weather_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onEmailClicked(cardView: View, meteo: Meteo) {

    }

    override fun onEmailStarChanged(meteo: Meteo, newValue: Boolean) {

    }

    override fun onEmailArchived(meteo: Meteo) {

    }
}
