package com.example.actividad_semana03.Adaptadores;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.actividad_semana03.Clases.ColorClass;
import com.example.actividad_semana03.R;

import java.util.List;

public class ColorAdaptador extends RecyclerView.Adapter<ColorAdaptador.ViewHolder> {

    private List<ColorClass> colores;

    public ColorAdaptador(List<ColorClass> colores) {
        this.colores = colores;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_color, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ColorClass color = colores.get(position);
        holder.tvNombreColor.setText(color.getNombre());
        holder.tvCodigoColor.setText(color.getCodigoHex());
        holder.vistaMuestraColor.setBackgroundColor(color.getValorColor());
    }

    @Override
    public int getItemCount() {
        return colores.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreColor;
        TextView tvCodigoColor;
        View vistaMuestraColor;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreColor = itemView.findViewById(R.id.tvNombreColor);
            tvCodigoColor = itemView.findViewById(R.id.tvCodigoColor);
            vistaMuestraColor = itemView.findViewById(R.id.vistaMuestraColor);
        }
    }
}