package com.example.app_pokedex.Services;

import com.example.app_pokedex.entities.Location;
import com.example.app_pokedex.entities.PokemonDetail;
import com.example.app_pokedex.entities.PokemonResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PokemonService {
    @GET("pokemon")
    Call<PokemonResponse> getPokemonList(@Query("offset") int offset, @Query("limit") int limit);

    @GET("pokemon/{id}")
    Call<PokemonDetail> getPokemonDetail(@Path("id") int pokemonId);
}