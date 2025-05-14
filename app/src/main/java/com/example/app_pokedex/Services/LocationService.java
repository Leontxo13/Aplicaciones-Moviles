package com.example.app_pokedex.Services;

import com.example.app_pokedex.entities.Location;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LocationService {
    @GET("pokemon-locations")
    Call<List<Location>> getLocations(@Query("pokemonId") int pokemonId);

    @POST("pokemon-locations")
    Call<Location> saveLocation(@Body Location location);
}