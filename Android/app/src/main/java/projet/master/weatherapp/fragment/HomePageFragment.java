package projet.master.weatherapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

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
import projet.master.weatherapp.R;
import projet.master.weatherapp.adapter.GridViewHomePageAdapter;
import projet.master.weatherapp.adapter.RecyclerViewHomePageAdapter;
import projet.master.weatherapp.model.Ville;

public class HomePageFragment extends Fragment {
    public static final String TAG = HomePageFragment.class.getSimpleName();

    @BindView(R.id.recycler_view_search_city)
    public RecyclerView recyclerView;
    @BindView(R.id.grid_view_search_city)
    public GridView gridView;


    private RecyclerViewHomePageAdapter recyclerViewHomePageAdapter;
    private GridViewHomePageAdapter gridViewHomePageAdapter;

    private ArrayList<Ville> villes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page_fragment, container, false);

        ButterKnife.bind(this, view);

        initTest();
        return view;
    }


    private void initTest(){
        Ville ville = new Ville();
        ville.setName("Lille");
        Ville ville2 = new Ville();
        ville2.setName("Lyon");
        Ville ville3 = new Ville();
        ville3.setName("Paris");

        villes = new ArrayList<>();
        villes.add(ville);
        villes.add(ville2);
        villes.add(ville3);

        recyclerViewHomePageAdapter = new RecyclerViewHomePageAdapter(getActivity());
        recyclerViewHomePageAdapter.setVilles(villes);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewHomePageAdapter);


        gridViewHomePageAdapter = new GridViewHomePageAdapter(getContext(), villes);
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
}
