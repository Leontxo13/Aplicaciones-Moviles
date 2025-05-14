package com.example.app_pokedex;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_pokedex.Services.LocationService;
import com.example.app_pokedex.entities.Location;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddLocationActivity extends AppCompatActivity {

    private TextView tvPokemonInfo;
    private EditText etLatitude;
    private EditText etLongitude;
    private Button btnSaveLocation;
    private LocationService locationService;
    private int pokemonId;
    private String pokemonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_location);

        setupViews();
        setupApiService();
        getIntentData();
        setupButton();
    }

    private void setupViews() {
        tvPokemonInfo = findViewById(R.id.tvPokemonInfo);
        etLatitude = findViewById(R.id.etLatitude);
        etLongitude = findViewById(R.id.etLongitude);
        btnSaveLocation = findViewById(R.id.btnSaveLocation);
    }

    private void setupApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://649a4c5a79fbe9bcf8403742.mockapi.io/") // Updated MockAPI URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        locationService = retrofit.create(LocationService.class);
    }

    private void getIntentData() {
        pokemonId = getIntent().getIntExtra("pokemon_id", 0);
        pokemonName = getIntent().getStringExtra("pokemon_name");

        if (pokemonName != null) {
            tvPokemonInfo.setText("Añadir ubicación para: " + pokemonName);
        }
    }

    private void setupButton() {
        btnSaveLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLocation();
            }
        });
    }

    private void saveLocation() {
        String latitudeText = etLatitude.getText().toString();
        String longitudeText = etLongitude.getText().toString();

        if (latitudeText.isEmpty() || longitudeText.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double latitude = Double.parseDouble(latitudeText);
            double longitude = Double.parseDouble(longitudeText);

            if (latitude < -90 || latitude > 90) {
                Toast.makeText(this, "La latitud debe estar entre -90 y 90", Toast.LENGTH_SHORT).show();
                return;
            }

            if (longitude < -180 || longitude > 180) {
                Toast.makeText(this, "La longitud debe estar entre -180 y 180", Toast.LENGTH_SHORT).show();
                return;
            }

            Location location = new Location(pokemonId, pokemonName, latitude, longitude);

            Log.d("POKEDEX", "Enviando ubicación: " + location.getPokemonName() + ", lat: " + location.getLatitude() + ", long: " + location.getLongitude());

            locationService.saveLocation(location).enqueue(new Callback<Location>() {
                @Override
                public void onResponse(Call<Location> call, Response<Location> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(AddLocationActivity.this, "Ubicación guardada correctamente", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        int statusCode = response.code();
                        Log.e("POKEDEX", "Error en la respuesta: " + statusCode);
                        Log.e("POKEDEX", "URL llamada: " + call.request().url());
                        try {
                            String errorBody = response.errorBody() != null ? response.errorBody().string() : "Sin detalle";
                            Log.e("POKEDEX", "Error body: " + errorBody);
                        } catch (Exception e) {
                            Log.e("POKEDEX", "No se pudo leer el error body");
                        }

                        Toast.makeText(AddLocationActivity.this, "Error al guardar la ubicación (Código: " + statusCode + ")", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Location> call, Throwable t) {
                    Toast.makeText(AddLocationActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("POKEDEX", "Error de conexión: " + t.getMessage());
                    Log.e("POKEDEX", "URL llamada: " + call.request().url());
                }
            });

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Por favor, ingresa valores numéricos válidos", Toast.LENGTH_SHORT).show();
        }
    }
}