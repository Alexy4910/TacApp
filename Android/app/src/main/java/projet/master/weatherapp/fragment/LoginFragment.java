package projet.master.weatherapp.fragment;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmObject;
import projet.master.weatherapp.MainActivity;
import projet.master.weatherapp.R;
import projet.master.weatherapp.config.ServiceWeatherStation;
import projet.master.weatherapp.config.WeatherStationApi;
import projet.master.weatherapp.listener.FirstPageViewHolderListener;
import projet.master.weatherapp.model.Parametre;
import projet.master.weatherapp.model.Ville;
import projet.master.weatherapp.utils.UbiUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {
    public static final String TAG = LoginFragment.class.getSimpleName();

    public static final String LOGIN_PREF_FILE = "LOGIN";
    public static final String LOGIN_PREF_KEY = "LOGIN";
    public static final String PWD_PREF_KEY = "PWD";

    @BindView(R.id.login_login)
    public EditText loginView;
    @BindView(R.id.tv_version)
    public TextView version;
    @BindView(R.id.name_phone)
    public TextView namePhone;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        ButterKnife.bind(this, view);


        try {
            PackageInfo pInfo = getActivity().getPackageManager().getPackageInfo(getContext().getPackageName(), 0);
            version.setText(String.format("v%s", pInfo.versionName));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        SharedPreferences prefs = getActivity().getSharedPreferences(LOGIN_PREF_FILE, 0);
        if (prefs != null) {
            loginView.setText(prefs.getString(LOGIN_PREF_KEY, ""));
        }


        namePhone.setText(UbiUtil.getDeviceIdentifier(getContext()));


        return view;
    }


    @OnClick(R.id.login_connection)
    public void onConnectClick(){
        try (Realm realm = Realm.getDefaultInstance()){
            RealmObject managedParametre = realm.where(Parametre.class).findFirst();
            if (managedParametre != null){
                Parametre parametre = (Parametre) realm.copyFromRealm(managedParametre);
                HomePageFragment homePageFragment = new HomePageFragment();
                homePageFragment.setParametre(parametre);
                ((MainActivity) getActivity()).showFragment(homePageFragment, HomePageFragment.TAG);
            }
            else{
                FirstLunchAppFragment firstLunchAppFragment = new FirstLunchAppFragment();
                ((MainActivity) getActivity()).showFragment(firstLunchAppFragment, FirstLunchAppFragment.TAG);
            }
        }catch (Exception ioe){
            ioe.printStackTrace();
        }
    }
}
