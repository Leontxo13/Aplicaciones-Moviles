package com.example.app_pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.app_pokedex.entities.PokemonDetail;
import com.example.app_pokedex.Services.PokemonService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonDetailActivity extends AppCompatActivity {

    private TextView tvPokemonName;
    private TextView tvPokemonType;
    private ImageView ivPokemonImage;
    private Button btnAddLocation;
    private Button btnViewLocations;
    private PokemonService service;
    private int pokemonId;
    private String pokemonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pokemon_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupViews();
        setupApiService();
        getIntentData();
        setupButtons();
        loadPokemonDetail();
    }

    private void setupViews() {
        tvPokemonName = findViewById(R.id.tvPokemonName);
        tvPokemonType = findViewById(R.id.tvPokemonType);
        ivPokemonImage = findViewById(R.id.ivPokemonImage);
        btnAddLocation = findViewById(R.id.btnAddLocation);
        btnViewLocations = findViewById(R.id.btnViewLocations);
    }

    private void setupApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(PokemonService.class);
    }

    private void getIntentData() {
        pokemonId = getIntent().getIntExtra("pokemon_id", 0);
        pokemonName = getIntent().getStringExtra("pokemon_name");

        if (pokemonName != null) {
            tvPokemonName.setText(pokemonName);
        }
    }

    private void setupButtons() {
        btnAddLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a la actividad para añadir ubicación
                Intent intent = new Intent(PokemonDetailActivity.this, AddLocationActivity.class);
                intent.putExtra("pokemon_id", pokemonId);
                intent.putExtra("pokemon_name", pokemonName);
                startActivity(intent);
            }
        });

        btnViewLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a la actividad para ver ubicaciones
                Intent intent = new Intent(PokemonDetailActivity.this, ViewLocationsActivity.class);
                intent.putExtra("pokemon_id", pokemonId);
                intent.putExtra("pokemon_name", pokemonName);
                startActivity(intent);
            }
        });
    }

    private void loadPokemonDetail() {
        if (pokemonId == 0) {
            Toast.makeText(this, "Error: ID de Pokémon no válido", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        service.getPokemonDetail(pokemonId).enqueue(new Callback<PokemonDetail>() {
            @Override
            public void onResponse(Call<PokemonDetail> call, Response<PokemonDetail> response) {
                if (response.isSuccessful() && response.body() != null) {
                    PokemonDetail pokemon = response.body();
                    displayPokemonDetails(pokemon);
                } else {
                    Toast.makeText(PokemonDetailActivity.this, "Error al cargar los detalles", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PokemonDetail> call, Throwable t) {
                Toast.makeText(PokemonDetailActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("POKEDEX", "Error: " + t.getMessage());
            }
        });
    }

    private void displayPokemonDetails(PokemonDetail pokemon) {
        tvPokemonName.setText(pokemon.name);

        StringBuilder typeBuilder = new StringBuilder();
        for (int i = 0; i < pokemon.types.size(); i++) {
            typeBuilder.append(pokemon.types.get(i).type.name);
            if (i < pokemon.types.size() - 1) {
                typeBuilder.append(", ");
            }
        }
        tvPokemonType.setText(typeBuilder.toString());

        Glide.with(this)
                .load(pokemon.sprites.frontDefault)
                .into(ivPokemonImage);
    }
}