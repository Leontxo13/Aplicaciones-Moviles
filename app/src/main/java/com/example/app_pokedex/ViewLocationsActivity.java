package com.example.app_pokedex;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.app_pokedex.Services.LocationService;
import com.example.app_pokedex.entities.Location;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ViewLocationsActivity extends AppCompatActivity {

    private TextView tvPokemonInfo;
    private TextView tvNoLocations;
    private MapView mapView;
    private LocationService locationService;
    private int pokemonId;
    private String pokemonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Configuración de OSMDroid
        Configuration.getInstance().load(getApplicationContext(),
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));

        setContentView(R.layout.activity_view_locations);

        setupViews();
        setupApiService();
        setupMap();
        getIntentData();
        loadLocations();
    }

    private void setupViews() {
        tvPokemonInfo = findViewById(R.id.tvPokemonInfo);
        tvNoLocations = findViewById(R.id.tvNoLocations);
        mapView = findViewById(R.id.mapView);
    }

    private void setupApiService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://649a4c5a79fbe9bcf8403742.mockapi.io/") // Updated MockAPI URL
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        locationService = retrofit.create(LocationService.class);
    }

    private void setupMap() {
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setMultiTouchControls(true);
        mapView.getController().setZoom(5.0);
    }

    private void getIntentData() {
        pokemonId = getIntent().getIntExtra("pokemon_id", 0);
        pokemonName = getIntent().getStringExtra("pokemon_name");

        if (pokemonName != null) {
            tvPokemonInfo.setText("Ubicaciones de " + pokemonName);
        }
    }

    private void loadLocations() {
        Log.d("POKEDEX", "Cargando ubicaciones para pokemonId: " + pokemonId);

        locationService.getLocations(pokemonId).enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(Call<List<Location>> call, Response<List<Location>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Location> locations = response.body();
                    Log.d("POKEDEX", "Ubicaciones recibidas: " + locations.size());

                    if (locations.isEmpty()) {
                        showNoLocationsMessage();
                    } else {
                        displayLocationsOnMap(locations);
                    }
                } else {
                    int statusCode = response.code();
                    Log.e("POKEDEX", "Error en la respuesta: " + statusCode);
                    Log.e("POKEDEX", "URL llamada: " + call.request().url());

                    Toast.makeText(ViewLocationsActivity.this, "Error al cargar las ubicaciones (Código: " + statusCode + ")", Toast.LENGTH_SHORT).show();
                    showNoLocationsMessage();
                }
            }

            @Override
            public void onFailure(Call<List<Location>> call, Throwable t) {
                Toast.makeText(ViewLocationsActivity.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("POKEDEX", "Error de conexión: " + t.getMessage());
                Log.e("POKEDEX", "URL llamada: " + call.request().url());
                showNoLocationsMessage();
            }
        });
    }

    private void showNoLocationsMessage() {
        mapView.setVisibility(View.GONE);
        tvNoLocations.setVisibility(View.VISIBLE);
    }

    private void displayLocationsOnMap(List<Location> locations) {
        mapView.setVisibility(View.VISIBLE);
        tvNoLocations.setVisibility(View.GONE);

        // Limpiar marcadores anteriores
        mapView.getOverlays().clear();

        if (!locations.isEmpty()) {
            // Centrar el mapa en la primera ubicación
            GeoPoint firstPoint = new GeoPoint(locations.get(0).getLatitude(), locations.get(0).getLongitude());
            mapView.getController().setCenter(firstPoint);

            // Añadir todas las ubicaciones como marcadores
            for (Location location : locations) {
                GeoPoint point = new GeoPoint(location.getLatitude(), location.getLongitude());
                Marker marker = new Marker(mapView);
                marker.setPosition(point);
                marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                marker.setTitle(pokemonName);
                marker.setSnippet("Lat: " + location.getLatitude() + ", Long: " + location.getLongitude());
                mapView.getOverlays().add(marker);
            }
        }

        mapView.invalidate(); // Refrescar el mapa
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }
}