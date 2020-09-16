package ubisolutions.net.template;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AppTemplate extends Application {

    public static final int REALM_DB_VERSION = 1;
    //Changer la version de realm va créer une nouvelle instance de la bdd dans le mobile

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .schemaVersion(REALM_DB_VERSION)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
    }
}
