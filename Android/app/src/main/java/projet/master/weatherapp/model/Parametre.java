package projet.master.weatherapp.model;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Parametre extends RealmObject {
    @PrimaryKey
    private int id;

    private RealmList<Ville> villes = new RealmList<>();

    public RealmList<Ville> getVilles() {
        return villes;
    }

    public void setVilles(RealmList<Ville> villes) {
        this.villes = villes;
    }
}
