package com.example.semana7.repository;

import androidx.annotation.NonNull;

import com.example.semana7.entities.Contacto; // Cambiamos a la ruta correcta

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContactoRepository {

    private final DatabaseReference contactosRef;

    public ContactoRepository() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        contactosRef = database.getReference("contactos");
    }

    public interface OnContactoGuardadoListener {
        void onContactoGuardado(boolean exito);
    }

    public interface OnContactosObtenidosListener {
        void onContactosObtenidos(List<Contacto> contactos);
    }

    public void guardarContacto(Contacto contacto, OnContactoGuardadoListener listener) {
        String id = contactosRef.push().getKey();
        contacto.setId(id);

        if (id != null) {
            contactosRef.child(id).setValue(contacto).addOnCompleteListener(task -> {
                if (listener != null) {
                    listener.onContactoGuardado(task.isSuccessful());
                }
            });
        } else if (listener != null) {
            listener.onContactoGuardado(false);
        }
    }

    public void obtenerContactos(OnContactosObtenidosListener listener) {
        contactosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Contacto> contactos = new ArrayList<>();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Contacto contacto = ds.getValue(Contacto.class);
                    if (contacto != null) {
                        contactos.add(contacto);
                    }
                }

                if (listener != null) {
                    listener.onContactosObtenidos(contactos);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                if (listener != null) {
                    listener.onContactosObtenidos(new ArrayList<>());
                }
            }
        });
    }
}