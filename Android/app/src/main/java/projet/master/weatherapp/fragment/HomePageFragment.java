package projet.master.weatherapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import projet.master.weatherapp.MainActivity;
import projet.master.weatherapp.R;
import projet.master.weatherapp.adapter.GridViewHomePageAdapter;
import projet.master.weatherapp.adapter.RecyclerViewHomePageAdapter;
import projet.master.weatherapp.config.ServiceWeatherStation;
import projet.master.weatherapp.config.WeatherStationApi;
import projet.master.weatherapp.listener.GoToDetailViewHolderListener;
import projet.master.weatherapp.model.Parametre;
import projet.master.weatherapp.model.Ville;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomePageFragment extends Fragment implements GoToDetailViewHolderListener {
    public static final String TAG = HomePageFragment.class.getSimpleName();

    private static final String CITY_NOT_FOUND = "city not found";


    @BindView(R.id.recycler_view_search_city)
    public RecyclerView recyclerView;
    @BindView(R.id.grid_view_search_city)
    public GridView gridView;


    private RecyclerViewHomePageAdapter recyclerViewHomePageAdapter;
    private GridViewHomePageAdapter gridViewHomePageAdapter;

    private ArrayList<Ville> villes;

    private Parametre parametre;
    private int nbVillesSync = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page_fragment, container, false);

        ButterKnife.bind(this, view);

        getInfoWS();
        return view;
    }

    private void getInfoWS(){
        villes = new ArrayList<>(parametre.getVilles());
        for (Ville ville: villes){

            ServiceWeatherStation service = WeatherStationApi.getService();
            Call<Ville> call = service.getWeatherByCity(ville.getName(), ServiceWeatherStation.API_KEY);
            call.enqueue(new Callback<Ville>() {
                @Override
                public void onResponse(Call<Ville> call, Response<Ville> response) {
                    if (response.isSuccessful()){
                        Ville villeBody = response.body();
                        ville.setTimezone(villeBody.getTimezone());
                    }else{
                        getActivity().runOnUiThread(() -> Toast.makeText(getContext(), ville.getName() + " n'existe pas...", Toast.LENGTH_SHORT).show());
                    }
                    nbVillesSync++;
                    if (nbVillesSync == villes.size()){
                        initList();
                    }
                }

                @Override
                public void onFailure(Call<Ville> call, Throwable t) {
                    getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show());
                }
            });
        }


    }

    private void initList(){
        recyclerViewHomePageAdapter =  new RecyclerViewHomePageAdapter(getActivity(), this);
        recyclerViewHomePageAdapter.setVilles(villes);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewHomePageAdapter);


        gridViewHomePageAdapter = new GridViewHomePageAdapter(getContext(), villes, this);
        gridView.setAdapter(gridViewHomePageAdapter);
    }

    @OnCheckedChanged(R.id.switch_test)
    public void onSwitchView(){
        if (recyclerView.getVisibility() == View.VISIBLE){
            recyclerView.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
        }else{
            recyclerView.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onVilleClicked(Ville ville) {
        Toast.makeText(getContext(), ville.getName(), Toast.LENGTH_LONG).show();
        DetailWeatherFragment detailWeatherFragment = new DetailWeatherFragment();
        detailWeatherFragment.setVille(ville);
        ((MainActivity) getActivity()).showFragment(detailWeatherFragment, DetailWeatherFragment.TAG);
    }

    public void setParametre(Parametre parametre) {
        this.parametre = parametre;
    }
}
