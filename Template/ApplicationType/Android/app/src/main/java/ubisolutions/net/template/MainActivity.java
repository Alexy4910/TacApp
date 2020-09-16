package ubisolutions.net.template;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.FrameLayout;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import ubisolutions.net.template.configuration.Config;
import ubisolutions.net.template.fragment.StartFragment;
import ubisolutions.net.template.fragment.dialogFragment.LoadScreenDialogFragment;

public class MainActivity extends AppCompatActivity {
    public static int PERMISSION = 10;

    @BindView(R.id.main_container)
    public FrameLayout mainContainer;

    public static Config config;
    private LoadScreenDialogFragment loadScreenDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        requestPermission();

        File configFile = new File(getExternalFilesDir(null), "conf.json");
        config = Config.Deserialize(configFile);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    public void requestPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION);
        } else {
            StartFragment startFragment = new StartFragment();
            showFragment(startFragment, StartFragment.TAG);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        boolean permission_granted = true;
        for (int perm : grantResults) {
            if (perm != PackageManager.PERMISSION_GRANTED) {
                permission_granted = false;
            }
        }
        if (permission_granted) {
            StartFragment startFragment = new StartFragment();
            showFragment(startFragment, StartFragment.TAG);
        }
    }

    public void showFragment(final Fragment fragment, String tag) {
        if (fragment == null) return;
        // Begin a fragment transaction.
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        // We can also animate the changing of fragment.
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
        // Replace current fragment by the new one.
        fragmentTransaction.replace(R.id.main_container, fragment, tag);
        // Null on the back stack to return on the previous fragment when user
        // press on back button.
        fragmentTransaction.addToBackStack(tag);
        // Commit changes.
        fragmentTransaction.commitAllowingStateLoss();
    }

    public void backToHome() {
        boolean homeFound = false;
        FragmentManager manager = getSupportFragmentManager();
        while (!homeFound) {
            int count = manager.getBackStackEntryCount() - 1;
            String fragmentName = manager.getBackStackEntryAt(count).getName();
            int fragmentId = manager.getBackStackEntryAt(count).getId();
//            if (fragmentName.equals(HomePageFragment.TAG)) {
//                homeFound = true;
//            } else {
//                manager.popBackStackImmediate(fragmentId, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//            }
        }
    }

    public void installUpdate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
            File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "shared");
            if (!path.exists()) {
                path.mkdirs();
            }
            File file = new File(path, "update.apk");
            Uri uri = FileProvider.getUriForFile(this, "ubisolutions.net.apppsa.fileprovider", file);
            //Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, 1);
        } else {
            File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "shared");
            if (!path.exists()) {
                path.mkdirs();
            }
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(Uri.fromFile(new File(path, "update.apk")), "application/vnd.android.package-archive");
            startActivityForResult(intent, 1);
        }
    }

    public void displayProgressDialog() {
        if (loadScreenDialogFragment == null || !loadScreenDialogFragment.isVisible()) {
            loadScreenDialogFragment = new LoadScreenDialogFragment();
            this.runOnUiThread(() -> loadScreenDialogFragment.show(MainActivity.this.getSupportFragmentManager(), LoadScreenDialogFragment.TAG));
        }
    }

    public void dismissProgressDialog() {
        this.runOnUiThread(() -> loadScreenDialogFragment.dismiss());
    }

}