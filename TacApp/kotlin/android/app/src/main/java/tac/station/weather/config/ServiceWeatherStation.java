package tac.station.weather.config;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import tac.station.weather.model.Ville;

public interface ServiceWeatherStation {
    String API_KEY = "52d3c776f917b95602b8cda0d1facc2a";
    String API_LANG = "fr";

    /**
     * @param ville nom de la ville
     * @param API_KEY clé d'identification à l'API
     * @param API_LANG langue dans lequel la réponse sera envoyé, utile pour les description nottament
     * @return un objet ville avec les données correctement cast
     */
    @GET("weather")
    Call<Ville> getWeatherByCity(@Query("q") String ville, @Query("appid") String API_KEY,
                                 @Query("lang") String API_LANG);
}
