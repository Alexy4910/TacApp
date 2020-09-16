package ubisolutions.net.template.fragment;

import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ubisolutions.net.template.MainActivity;
import ubisolutions.net.template.R;
import ubisolutions.net.template.utils.UbiUtil;

public class LoginFragment extends Fragment {
    public static final String TAG = LoginFragment.class.getSimpleName();

    public static final String LOGIN_PREF_FILE = "LOGIN";
    public static final String LOGIN_PREF_KEY = "LOGIN";
    public static final String PWD_PREF_KEY = "PWD";

    public static final String ARG_DISPLAY_UPDATE = "DISPLAY_UPDATE";

    @BindView(R.id.login_login)
    public EditText loginView;
    @BindView(R.id.tv_version)
    public TextView version;
    @BindView(R.id.name_phone)
    public TextView namePhone;
    @BindView(R.id.bt_install_update)
    public Button installUpdateBt;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        ButterKnife.bind(this, view);


        Bundle args = getArguments();
        if(args != null){
            boolean displayUpdate = args.getBoolean(ARG_DISPLAY_UPDATE);
            if(displayUpdate){
                installUpdateBt.setVisibility(View.VISIBLE);
            }
        }
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

    }

    @OnClick(R.id.bt_install_update)
    public void onInstallUpdateClick(){
        ((MainActivity) getActivity()).installUpdate();
    }
}
