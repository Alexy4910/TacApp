package projet.master.weatherapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmList;
import projet.master.weatherapp.MainActivity;
import projet.master.weatherapp.R;
import projet.master.weatherapp.adapter.RecyclerViewFirstLunchAdapter;
import projet.master.weatherapp.adapter.RecyclerViewHomePageAdapter;
import projet.master.weatherapp.listener.FirstPageViewHolderListener;
import projet.master.weatherapp.model.Parametre;
import projet.master.weatherapp.model.Ville;

public class FirstLunchAppFragment extends Fragment implements FirstPageViewHolderListener {
    public static final String TAG = FirstLunchAppFragment.class.getSimpleName();

    @BindView(R.id.recycler_view_city)
    public RecyclerView recyclerView;
    @BindView(R.id.city_new)
    public EditText addCityToList;


    private RecyclerViewFirstLunchAdapter recyclerViewFirstLunchAdapter;

    private ArrayList<Ville> villes;
    private int id = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_lunch_fragment, container, false);

        ButterKnife.bind(this, view);

        initTest();
        return view;
    }


    private void initTest(){
        villes = new ArrayList<>();
        recyclerViewFirstLunchAdapter =  new RecyclerViewFirstLunchAdapter(getActivity(), this);
        recyclerViewFirstLunchAdapter.setVilles(villes);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerViewFirstLunchAdapter);
    }



    @Override
    public void onVilleClicked(Ville ville) {

    }

    @OnClick(R.id.add_city_plus)
    public void onAddClick(){
        //TODO CHECK SI NON PRESENT ET SUPPRIMER UNE LIGNE ET CHANGER COULEUR UNE LIGNE SUR DEUX ET IMAGE
        Ville ville = new Ville();
        ville.setName(addCityToList.getText().toString());
        ville.setId(id);
        id++;
        addCityToList.setText(null);
        villes.add(ville);
        recyclerViewFirstLunchAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.valider_liste)
    public void onValideClick(){
        try (Realm realm = Realm.getDefaultInstance()){
            Parametre parametre = new Parametre();
            RealmList<Ville> realmList = new RealmList<>();
            realmList.addAll(villes);
            parametre.setVilles(realmList);
            realm.executeTransaction(realm1 -> realm.copyToRealm(parametre));

            HomePageFragment homePageFragment = new HomePageFragment();
            homePageFragment.setParametre(parametre);
            ((MainActivity) getActivity()).showFragment(homePageFragment, HomePageFragment.TAG);
        }catch (Exception ioe){
            ioe.printStackTrace();
        }
    }
}
