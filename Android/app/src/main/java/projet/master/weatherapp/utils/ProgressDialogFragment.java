package projet.master.weatherapp.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class ProgressDialogFragment extends DialogFragment {

    public static final String TAG = ProgressDialogFragment.class.getSimpleName();

    private ProgressDialog dialog;

    public ProgressDialogFragment() {
        // Required empty constructor
    }

    public static ProgressDialogFragment newInstance(String title, String message) {
        ProgressDialogFragment dialog = new ProgressDialogFragment();
        Bundle args = new Bundle();
        args.putString("TITLE", title);
        args.putString("MESSAGE", message);
        dialog.setArguments(args);
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        dialog = new ProgressDialog(getActivity());
        String title = getArguments().getString("TITLE");
        if (TextUtils.isEmpty(title)) {
            dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        } else {
            dialog.setTitle(title);
        }
        dialog.setMessage(getArguments().getString("MESSAGE"));
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        return dialog;
    }

    public void setMessage(String message) {
        dialog.setMessage(message);
    }
}
