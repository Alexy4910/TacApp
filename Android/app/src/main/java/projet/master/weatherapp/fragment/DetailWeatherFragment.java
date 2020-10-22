package projet.master.weatherapp.fragment;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import projet.master.weatherapp.MainActivity;
import projet.master.weatherapp.R;
import projet.master.weatherapp.model.Ville;
import projet.master.weatherapp.utils.UbiUtil;

public class DetailWeatherFragment extends Fragment {
    public static final String TAG = DetailWeatherFragment.class.getSimpleName();

    @BindView(R.id.nom_ville)
    public TextView nomVille;

    private Ville ville;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detail_fragment, container, false);

        ButterKnife.bind(this, view);

        if (ville != null){
            nomVille.setText(ville.getName());
        }

        return view;
    }


    public void setVille(Ville ville) {
        this.ville = ville;
    }

    @OnClick(R.id.detail_back)
    public void onBackPressed(){
        getActivity().onBackPressed();
    }
}
