package ubisolutions.net.template.fragment.dialogFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import butterknife.ButterKnife;
import ubisolutions.net.template.R;

/**
 * Created by ALexy LEDAIN
 */
public class LoadScreenDialogFragment extends DialogFragment {
    public static final String TAG = DialogFragment.class.getSimpleName();


    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_chargement_screen, container, false);

        this.setCancelable(false);
        ButterKnife.bind(this, view);

        FragmentManager fm = getActivity().getSupportFragmentManager();


        return view;
    }
}
