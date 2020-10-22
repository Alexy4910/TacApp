package projet.master.weatherapp.config;


import java.util.ArrayList;

import projet.master.weatherapp.model.Ville;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServiceWeatherStation {
    String API_KEY = "52d3c776f917b95602b8cda0d1facc2a";

    @GET("weather")
    Call<Ville> getWeatherByCity(@Query("q") String ville, @Query("appid") String API_KEY);
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
