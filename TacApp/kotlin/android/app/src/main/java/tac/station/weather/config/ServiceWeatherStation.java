package tac.station.weather.config;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import tac.station.weather.model.Ville;

public interface ServiceWeatherStation {
    String API_KEY = "52d3c776f917b95602b8cda0d1facc2a";
    String API_LANG = "fr";

    @GET("weather")
    Call<Ville> getWeatherByCity(@Query("q") String ville, @Query("appid") String API_KEY,
                                 @Query("lang") String API_LANG);
/*
    @GET("getUsers")
    Call<ArrayList<User>> getUsers();

    @GET("getPalette")
    Call<PaletteResponse> getPalette(@Query("numPalette") String numPalette);

    @GET("getInventaires")
    Call<ArrayList<InventaireResponse>> getInventaires(@Query("idUser") long idUser);

    @GET("getRecherches")
    Call<ArrayList<RechercheResponse>> getRecherches(@Query("idUser") long idUser);

    @POST("createReport")
    Call<Void> sendPreparationReport(@Body Preparation preparation);

    @POST("createReportInventaire")
    Call<Void> createReportInventaire(@Body InventaireRequest inventaireRequest);
    */
}
