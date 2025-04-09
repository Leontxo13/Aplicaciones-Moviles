package com.example.actividad_semana03;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actividad_semana03.Adaptadores.ColorAdaptador;
import com.example.actividad_semana03.Clases.ColorClass;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<ColorClass> listaColores = new ArrayList<>();
        listaColores.add(new ColorClass("Blue", "#0000FF"));
        listaColores.add(new ColorClass("Indigo", "#4B0082"));
        listaColores.add(new ColorClass("Red", "#FF0000"));
        listaColores.add(new ColorClass("Green", "#008000"));
        listaColores.add(new ColorClass("Orange", "#FFA500"));
        listaColores.add(new ColorClass("Grey", "#808080"));
        listaColores.add(new ColorClass("Amber", "#13828d"));
        listaColores.add(new ColorClass("Deep Purple", "#800080"));


        RecyclerView rvColores = findViewById(R.id.rvColores);
        rvColores.setLayoutManager(new LinearLayoutManager(this));
        ColorAdaptador adaptador = new ColorAdaptador(listaColores);
        rvColores.setAdapter(adaptador);
    }
}