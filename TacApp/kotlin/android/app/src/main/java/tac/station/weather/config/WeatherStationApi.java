package tac.station.weather.config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherStationApi {

    private static Retrofit retrofit = null;

    private static String API_URL = "https://api.openweathermap.org/data/2.5/";

    /**
     * Méthode qui permet la connexion à l'API grâce à retrofit
     * @return un objet retrofit
     */
    public static ServiceWeatherStation getService() {
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ServiceWeatherStation service = retrofit.create(ServiceWeatherStation.class);
            return service;
        }catch (Exception ioe){
            ioe.printStackTrace();
        }
        return null;
    }

}
