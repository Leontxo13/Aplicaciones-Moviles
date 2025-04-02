package com.example.am_sem1;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    int contador = 2000;
//cambio de 0 a 2000

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnShowMessage = findViewById(R.id.btnShowMessage);
        TextView tvMessage = findViewById(R.id.tvMessage);
        TextView contadorMessage = findViewById(R.id.contadorMessage);

        btnShowMessage.setOnClickListener(v -> {
            contador++;

            Log.i("MAIN_APP","CLIC EN BOTON");
            tvMessage.setText("Hola clase 2025-1");
            contadorMessage.setText(String.valueOf(contador));

            //Intent intent = new Intent(MainActivity.this, MainActivity2.class);
          //startActivity(intent);

        });
    }
}