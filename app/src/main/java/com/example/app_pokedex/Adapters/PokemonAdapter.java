package com.example.app_pokedex.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_pokedex.PokemonDetailActivity;
import com.example.app_pokedex.R;
import com.example.app_pokedex.entities.Pokemon;

import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> {

    private List<Pokemon> data;

    public PokemonAdapter(List<Pokemon> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pokemon, parent, false);

        return new PokemonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
        Pokemon pokemon = data.get(position);

        TextView tvPokemonName = holder.itemView.findViewById(R.id.tvPokemonName);
        tvPokemonName.setText(pokemon.name);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Pokémon: " + pokemon.name, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), PokemonDetailActivity.class);

                intent.putExtra("pokemon_id", pokemon.getId());
                intent.putExtra("pokemon_name", pokemon.name);

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setPokemonList(List<Pokemon> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }

    class PokemonViewHolder extends RecyclerView.ViewHolder {
        public PokemonViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}