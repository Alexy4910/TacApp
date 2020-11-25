package tac.station.weather.fragment

import android.os.Bundle
import android.view.*
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import io.realm.Realm
import kotlinx.android.synthetic.main.weather_product_grid_fragment.*
import tac.station.weather.NavigationHost
import tac.station.weather.NavigationIconClickListener
import tac.station.weather.R
import tac.station.weather.adapter.GridViewHomePageAdapter
import tac.station.weather.adapter.RecyclerViewAdapter
import tac.station.weather.databinding.RechercheDialogFragmentBinding
import tac.station.weather.databinding.WeatherProductGridFragmentBinding
import tac.station.weather.fragment.dialogFragment.RechercheVilleDialogFragment
import tac.station.weather.listener.RechercheListener
import tac.station.weather.model.Ville
import tac.station.weather.setupRecyclerView.ReboundingSwipeActionCallback


class ProductGridFragment : Fragment(), RecyclerViewAdapter.RecyclerViewAdapterListener, RechercheListener {

    private lateinit var binding: WeatherProductGridFragmentBinding

    private var meteoAdapter: RecyclerViewAdapter? = null
    private var gridViewHomePageAdapter: GridViewHomePageAdapter? = null

    private var villes: List<Ville>? = null

    private var villesFiltered: ArrayList<Ville>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                binding.gridViewSearchCity,
                AccelerateDecelerateInterpolator(),
                ContextCompat.getDrawable(context!!, R.drawable.weahter_menu), // Menu open icon
                ContextCompat.getDrawable(context!!, R.drawable.weather_close_menu))) // Menu close icon

        binding.modeGrille.setOnClickListener {
            if (recycler_view.visibility == View.VISIBLE) {
                recycler_view.visibility = View.GONE
                grid_view_search_city.visibility = View.VISIBLE
                binding.modeGrille.text = getString(R.string.mode_grille)
            } else {
                recycler_view.visibility = View.VISIBLE
                grid_view_search_city.visibility = View.GONE
                binding.modeGrille.text = getString(R.string.mode_list)
            }
        }

        binding.favori.setOnClickListener {
            //TODO
        }

        initAdapter()
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.weather_toolbar_menu, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onVilleClicked(cardView: View, ville: Ville) {
        val detailVilleFragment = DetailVilleFragment(ville)
        (activity as NavigationHost).navigateTo(detailVilleFragment, true)
    }

    override fun onAddFavorite(ville: Ville, newValue: Boolean) {
        ville.isFavorite = newValue
        meteoAdapter?.notifyDataSetChanged()
        val realm = Realm.getDefaultInstance()
        realm.use { realm1 ->
            realm1.executeTransaction { realm1.copyToRealmOrUpdate(ville) }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search -> {
                val rechercheVilleDialogFragment = RechercheVilleDialogFragment(this)
                rechercheVilleDialogFragment.show(activity?.supportFragmentManager!!, "TEST")
            }
        }
        return true
    }


    private fun initAdapter(){
        try {
            Realm.getDefaultInstance().use { realm ->
                val managedVilles = realm.where(Ville::class.java).findAll()
                if (managedVilles != null){
                    villes = realm.copyFromRealm(managedVilles)
                }
            }
        } catch (ioe: Exception) {
            ioe.printStackTrace()
        }
        binding.recyclerView.apply {
            val itemTouchHelper = ItemTouchHelper(ReboundingSwipeActionCallback())
            itemTouchHelper.attachToRecyclerView(this)
        }
        villesFiltered = ArrayList()
        villesFiltered?.addAll(villes!!)

        meteoAdapter = RecyclerViewAdapter(this, villes!!)
        gridViewHomePageAdapter = GridViewHomePageAdapter(context, villes!!, this)

        grid_view_search_city.adapter = gridViewHomePageAdapter
        recycler_view.adapter = meteoAdapter
    }

    override fun onGoDetailVilleGrid(ville: Ville) {
        val detailVilleFragment = DetailVilleFragment(ville)
        (activity as NavigationHost).navigateTo(detailVilleFragment, true)
    }

    override fun onTextChanged(villeName: String) {
        villesFiltered?.clear()
        for (ville in villes!!){
            if (ville.name.contains(villeName)){
                villesFiltered?.add(ville)
            }
        }
        meteoAdapter?.setVilles(villesFiltered!!)
        gridViewHomePageAdapter?.setVilles(villesFiltered)
        meteoAdapter?.notifyDataSetChanged()
        gridViewHomePageAdapter?.notifyDataSetChanged()
    }
}
