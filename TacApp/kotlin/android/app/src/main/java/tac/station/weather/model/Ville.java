package tac.station.weather.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Ville extends RealmObject {

    @PrimaryKey
    private int id;

    private Coordonne coord;
    private RealmList<Weather> weather;
    private Main main;
    private Wind wind;
    private String name;
    private int timezone;

    private boolean favorite = false;
    private byte[] icon;

    //Affichage list zone
    private String tempearature = "";
    private String description = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Coordonne getCoord() {
        return coord;
    }

    public void setCoord(Coordonne coord) {
        this.coord = coord;
    }

    public RealmList<Weather> getWeather() {
        return weather;
    }

    public void setWeather(RealmList<Weather> weather) {
        this.weather = weather;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public String getTempearature() {
        return tempearature;
    }

    public void setTempearature(String tempearature) {
        this.tempearature = tempearature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
