package projet.master.weatherapp.config;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherStationApi {

    private static Retrofit retrofit = null;



    public static ServiceWeatherStation getService() {
        try {
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
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
