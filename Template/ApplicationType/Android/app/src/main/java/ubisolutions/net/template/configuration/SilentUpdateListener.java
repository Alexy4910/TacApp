package ubisolutions.net.template.configuration;

/**
 * Created by Clement on 20/04/2018.
 */

public interface SilentUpdateListener {
    void onUpdateAvailable(String version);
    void onConfigUpdated();
    void onUpdateFinished(Boolean bool);
    void onNotConnected();
    void onUpdateFailed();
}