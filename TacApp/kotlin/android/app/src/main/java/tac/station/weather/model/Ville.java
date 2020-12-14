package tac.station.weather.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Classe général englobant toute les données
 */
public class Ville extends RealmObject {

    /**
     * Id pour la bdd realm
     */
    @PrimaryKey
    private int id;

    /**
     * Les coordonnées
     */
    private Coordonne coord;
    /**
     * La liste des météo pour cette ville
     */
    private RealmList<Weather> weather;
    /**
     * Le main
     */
    private Main main;
    /**
     * La vitesse du vent
     */
    private Wind wind;
    /**
     * Nom de la ville
     */
    private String name;
    /**
     * Timezone de la ville
     */
    private int timezone;

    /**
     * Savoir si la ville est dans les favoris
     */
    private boolean favorite = false;
    /**
     * Tableau de byte représentant l'icon
     */
    private byte[] icon;

    //Affichage list zone
    private String temperature = "";
    private String description = "";

    private String ressenti = "";
    private String minimal = "";
    private String maximal = "";

    private String pression = "";
    private String humidite = "";

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

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRessenti() {
        return ressenti;
    }

    public void setRessenti(String ressenti) {
        this.ressenti = ressenti;
    }

    public String getMinimal() {
        return minimal;
    }

    public void setMinimal(String minimal) {
        this.minimal = minimal;
    }

    public String getMaximal() {
        return maximal;
    }

    public void setMaximal(String maximal) {
        this.maximal = maximal;
    }

    public String getPression() {
        return pression;
    }

    public void setPression(String pression) {
        this.pression = pression;
    }

    public String getHumidite() {
        return humidite;
    }

    public void setHumidite(String humidite) {
        this.humidite = humidite;
    }
}
