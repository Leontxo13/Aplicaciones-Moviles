package com.example.semana7.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.semana7.R;
import com.example.semana7.entities.Contacto;
// Eliminar esta importación redundante ya que hay conflicto:
// import com.example.semana7.model.Contacto;

import java.util.ArrayList;
import java.util.List;

public class ContactoAdapter extends RecyclerView.Adapter<ContactoAdapter.ContactoViewHolder> {

    private List<Contacto> listaContactos;
    private List<Contacto> listaOriginal;

    public ContactoAdapter() {
        this.listaContactos = new ArrayList<>();
        this.listaOriginal = new ArrayList<>();
    }

    @NonNull
    @Override
    public ContactoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contacto, parent, false);
        return new ContactoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactoViewHolder holder, int position) {
        Contacto contacto = listaContactos.get(position);
        holder.textViewNombre.setText(contacto.getNombre());
        holder.textViewTelefono.setText("Tel: " + contacto.getTelefono());
        holder.textViewGenero.setText("Género: " + contacto.getGenero());
        holder.textViewDireccion.setText("Dir: " + contacto.getDireccion());
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }

    public void setContactos(List<Contacto> contactos) {
        this.listaContactos = contactos;
        this.listaOriginal = new ArrayList<>(contactos);
        notifyDataSetChanged();
    }

    public void filtrar(String texto) {
        if (texto.isEmpty()) {
            listaContactos = new ArrayList<>(listaOriginal);
        } else {
            List<Contacto> listaFiltrada = new ArrayList<>();
            String textoLower = texto.toLowerCase();

            for (Contacto contacto : listaOriginal) {
                if (contacto.getNombre().toLowerCase().contains(textoLower) ||
                        contacto.getTelefono().toLowerCase().contains(textoLower)) {
                    listaFiltrada.add(contacto);
                }
            }

            listaContactos = listaFiltrada;
        }
        notifyDataSetChanged();
    }

    public static class ContactoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombre, textViewTelefono, textViewGenero, textViewDireccion;

        public ContactoViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
            textViewTelefono = itemView.findViewById(R.id.textViewTelefono);
            textViewGenero = itemView.findViewById(R.id.textViewGenero);
            textViewDireccion = itemView.findViewById(R.id.textViewDireccion);
        }
    }
}