package ubisolutions.net.template.configuration;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Environment;

import com.google.firebase.crashlytics.internal.common.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import ubisolutions.net.template.utils.UbiUtil;

/**
 * Created by Alexy on 20/04/2018.
 */

public class SilentUpdateTask extends AsyncTask<Object, Void, Boolean> {

    private boolean forceConfigUpdate;

    private Context context;
    private SoftwareProduct product = null;
    private Proxy prx;

    private SilentUpdateListener listener;

    public SilentUpdateTask(Context context, SilentUpdateListener listener, boolean forceConfigUpdate){
        this.context = context;
        this.listener = listener;
        this.forceConfigUpdate = forceConfigUpdate;
    }

    @Override
    protected Boolean doInBackground(Object[] params) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            try {
                // Get proxy parameters
                ProxySelector defaultProxySelector = ProxySelector.getDefault();
                try {
                    List<Proxy> proxyList = defaultProxySelector.select(new URI(Config.URL_UPDATE));
                    if (proxyList.size() > 0 && proxyList.get(0).type() == Proxy.Type.HTTP)
                        prx = new Proxy(Proxy.Type.HTTP, proxyList.get(0).address());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                // Declare device id
                URL url = new URL(Config.URL_UPDATE + "?deviceId=" + UbiUtil.getDeviceIdentifier(context) + "&cmd=DECLAREDEVICEID");
                HttpURLConnection urlConnection = (prx != null) ? (HttpURLConnection) url.openConnection(prx) : (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                    String responseString = "";
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseString += line;
                    }
                    reader.close();
                    in.close();

                    if (!responseString.equalsIgnoreCase("OK")) {
                        throw new Exception("DECLAREDEVICEID FAIL");
                    }
                } else {
                    throw new Exception("DECLAREDEVICEID " + urlConnection.getResponseMessage());
                }
                urlConnection.disconnect();

                // Get product list
                url = new URL(Config.URL_UPDATE + "?deviceId=" + UbiUtil.getDeviceIdentifier(context) + "&cmd=GETPRODUCTLIST");
                urlConnection = (prx != null) ? (HttpURLConnection) url.openConnection(prx) : (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                    String responseString = "";
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseString += line;
                    }
                    reader.close();
                    in.close();

                    product = null;
                    try {
                        product = new SoftwareProduct(responseString);
                    } catch (Exception ex) {
                        throw new Exception("GETPRODUCTLIST EMPTY");
                    }

                } else {
                    throw new Exception("GETPRODUCTLIST " + urlConnection.getResponseMessage());
                }
                urlConnection.disconnect();

                // Set product list
                PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                url = new URL(Config.URL_UPDATE + "?deviceId=" + UbiUtil.getDeviceIdentifier(context) + "&cmd=SETPRODUCTLIST&productId=" + product.ProductId + "&productVersion=" + pInfo.versionName);
                urlConnection = (prx != null) ? (HttpURLConnection) url.openConnection(prx) : (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream in = urlConnection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                    String responseString = "";
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseString += line;
                    }
                    reader.close();
                    in.close();

                    if (!responseString.equalsIgnoreCase("OK")) {
                        throw new Exception("SETPRODUCTLIST FAIL");
                    }

                } else {
                    throw new Exception("SETPRODUCTLIST " + urlConnection.getResponseMessage());
                }
                urlConnection.disconnect();

                if (forceConfigUpdate || product.updateConfig) {
                    // Get product conf
                    File configFile = new File(context.getExternalFilesDir(null), "conf.json");
                    url = new URL(Config.URL_UPDATE + "?deviceId=" + UbiUtil.getDeviceIdentifier(context) + "&cmd=GETPRODUCTCONF&productId=" + product.ProductId);
                    urlConnection = (prx != null) ? (HttpURLConnection) url.openConnection(prx) : (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("Accept-Encoding", "identity");
                    urlConnection.connect();
                    if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        FileOutputStream out = new FileOutputStream(configFile);
                        InputStream in = urlConnection.getInputStream();

                        int size = urlConnection.getContentLength();
                        byte[] buffer = new byte[size];
                        int length;
                        while ((length = in.read(buffer)) != -1) {
                            out.write(buffer, 0, length);
                        }

                        in.close();
                        out.close();
                    } else {
                        throw new Exception("GETPRODUCTCONF " + urlConnection.getResponseMessage());
                    }
                    urlConnection.disconnect();

                    listener.onConfigUpdated();

                    forceConfigUpdate = false;
                }

                if (!pInfo.versionName.equals(product.ProductVersion)) {
                    // Get product conf
                    File configFile = new File(context.getExternalFilesDir(null), "conf.json");
                    url = new URL(Config.URL_UPDATE + "?deviceId=" + UbiUtil.getDeviceIdentifier(context) + "&cmd=GETPRODUCTCONF&productId=" + product.ProductId);
                    urlConnection = (prx != null) ? (HttpURLConnection) url.openConnection(prx) : (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("Accept-Encoding", "identity");
                    urlConnection.connect();
                    if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        FileOutputStream out = new FileOutputStream(configFile);

                        InputStream in = urlConnection.getInputStream();
                        int size = urlConnection.getContentLength();
                        byte[] buffer = new byte[size];
                        int length;
                        while ((length = in.read(buffer)) != -1) {
                            out.write(buffer, 0, length);
                        }
                        in.close();
                        out.close();
                    } else {
                        throw new Exception("GETPRODUCTCONF " + urlConnection.getResponseMessage());
                    }
                    urlConnection.disconnect();

                    // Get product
                    File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "shared");
                    if(!path.exists()){
                        path.mkdirs();
                    }
                    File apkFile = new File(path, "update.apk");
                    url = new URL(Config.URL_UPDATE + "?deviceId=" + UbiUtil.getDeviceIdentifier(context) + "&cmd=GETPRODUCT&productId=" + product.ProductId);
                    urlConnection = (prx != null) ? (HttpURLConnection) url.openConnection(prx) : (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestProperty("Accept-Encoding", "identity");
                    urlConnection.connect();
                    if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        FileOutputStream out = new FileOutputStream(apkFile);

                        InputStream in = urlConnection.getInputStream();
                        int size = urlConnection.getContentLength();
                        byte[] buffer = new byte[size];
                        int length;
                        while ((length = in.read(buffer)) != -1) {
                            out.write(buffer, 0, length);
                        }
                        in.close();
                        out.close();
                    } else {
                        throw new Exception("GETPRODUCT " + urlConnection.getResponseMessage());
                    }
                    urlConnection.disconnect();

                    listener.onUpdateAvailable(product.ProductVersion);
                }
            } catch (Exception e) {
                e.printStackTrace();
                listener.onUpdateFailed();
                return false;
            }

        } else {
            //Pas de connectivité, on lance quand même l'application
            listener.onNotConnected();
            return true;
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean bool) {
        listener.onUpdateFinished(bool);
        super.onPostExecute(bool);
    }
}