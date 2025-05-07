package com.example.semana7;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.semana7.Adapters.ContactoAdapter; // Ruta corregida
import com.example.semana7.entities.Contacto; // Ruta corregida
import com.example.semana7.repository.ContactoRepository;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {

    private TextInputEditText editTextNombre, editTextTelefono, editTextDireccion, editTextBuscar;
    private RadioGroup radioGroupGenero;
    private Button buttonGuardar;
    private RecyclerView recyclerViewContactos;

    private ContactoAdapter contactoAdapter;
    private ContactoRepository contactoRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar vistas
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        editTextDireccion = findViewById(R.id.editTextDireccion);
        editTextBuscar = findViewById(R.id.editTextBuscar);
        radioGroupGenero = findViewById(R.id.radioGroupGenero);
        buttonGuardar = findViewById(R.id.buttonGuardar);
        recyclerViewContactos = findViewById(R.id.recyclerViewContactos);

        // Configurar RecyclerView
        recyclerViewContactos.setLayoutManager(new LinearLayoutManager(this));
        contactoAdapter = new ContactoAdapter();
        recyclerViewContactos.setAdapter(contactoAdapter);

        // Inicializar repositorio
        contactoRepository = new ContactoRepository();

        // Configurar eventos
        buttonGuardar.setOnClickListener(v -> guardarContacto());

        // Configurar búsqueda
        editTextBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contactoAdapter.filtrar(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Cargar contactos
        cargarContactos();
    }

    private void guardarContacto() {
        String nombre = editTextNombre.getText().toString().trim();
        String telefono = editTextTelefono.getText().toString().trim();
        String direccion = editTextDireccion.getText().toString().trim();

        // Validar campos
        if (nombre.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener género seleccionado
        int radioButtonId = radioGroupGenero.getCheckedRadioButtonId();
        if (radioButtonId == -1) {
            Toast.makeText(this, "Por favor seleccione un género", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton radioButton = findViewById(radioButtonId);
        String genero = radioButton.getText().toString();

        // Crear objeto Contacto
        Contacto contacto = new Contacto(null, nombre, telefono, genero, direccion);

        // Guardar en Firebase
        contactoRepository.guardarContacto(contacto, exito -> {
            if (exito) {
                Toast.makeText(MainActivity.this, "Contacto guardado con éxito", Toast.LENGTH_SHORT).show();
                limpiarCampos();
                cargarContactos();
            } else {
                Toast.makeText(MainActivity.this, "Error al guardar contacto", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cargarContactos() {
        contactoRepository.obtenerContactos(contactos -> {
            contactoAdapter.setContactos(contactos);
        });
    }

    private void limpiarCampos() {
        editTextNombre.setText("");
        editTextTelefono.setText("");
        editTextDireccion.setText("");
        radioGroupGenero.clearCheck();
        editTextNombre.requestFocus();
    }
}