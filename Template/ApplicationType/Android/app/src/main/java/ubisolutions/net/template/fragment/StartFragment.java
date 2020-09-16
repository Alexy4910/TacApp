package ubisolutions.net.template.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.io.File;

import ubisolutions.net.template.MainActivity;
import ubisolutions.net.template.R;
import ubisolutions.net.template.configuration.Config;
import ubisolutions.net.template.configuration.SilentUpdateListener;
import ubisolutions.net.template.configuration.SilentUpdateTask;
import ubisolutions.net.template.utils.UbiUtil;

public class StartFragment extends Fragment implements SilentUpdateListener {

    public static final String TAG = StartFragment.class.getSimpleName();
    public static String ARG_DISPLAY_UPDATE = "DISPLAY_UPDATE";
    public static String ARG_DISPLAY_UPDATE_VERSION = "VERSION_APP";


    private boolean silentTaskFinished = false;
    private boolean updateAvailable = false;
    private String version = null;
    private ActionBar actionBar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.start_fragment, container, false);

        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        SilentUpdateTask updateTask = new SilentUpdateTask(getContext(), this, false);
        updateTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        return view;
    }


    @Override
    public void onUpdateFailed() {
        String serialNumber = UbiUtil.getDeviceIdentifier(getContext());
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> Toast.makeText(getContext(), "Erreur de configuration, numéro de série : " + serialNumber, Toast.LENGTH_LONG).show());
        }
    }

    @Override
    public void onUpdateAvailable(String version) {
        updateAvailable = true;
        this.version = version;
    }

    @Override
    public void onConfigUpdated() {
        File configFile = new File(getActivity().getExternalFilesDir(null), "conf.json");
        MainActivity.config = Config.Deserialize(configFile);
    }

    @Override
    public void onUpdateFinished(Boolean bool) {
        if (bool) {
            silentTaskFinished = true;
            if (getActivity() != null && silentTaskFinished) {
                LoginFragment loginFragment = new LoginFragment();
                Bundle args = loginFragment.getArguments();
                if (args == null) {
                    args = new Bundle();
                }
                args.putBoolean(ARG_DISPLAY_UPDATE, updateAvailable);
                args.putString(ARG_DISPLAY_UPDATE_VERSION, version);
                loginFragment.setArguments(args);

                ((MainActivity)getActivity()).showFragment(loginFragment, LoginFragment.TAG);

                getActivity().getSupportFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
            }
        } else {
            silentTaskFinished = false;
        }
    }

    @Override
    public void onNotConnected() {
        silentTaskFinished = true;
        if (getActivity() != null && silentTaskFinished) {
            LoginFragment loginFragment = new LoginFragment();
            Bundle args = loginFragment.getArguments();
            if (args == null) {
                args = new Bundle();
            }
            args.putBoolean(ARG_DISPLAY_UPDATE, updateAvailable);
            args.putString(ARG_DISPLAY_UPDATE_VERSION, version);
            loginFragment.setArguments(args);

            ((MainActivity)getActivity()).showFragment(loginFragment, LoginFragment.TAG);

            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commitAllowingStateLoss();
        }
    }

}
