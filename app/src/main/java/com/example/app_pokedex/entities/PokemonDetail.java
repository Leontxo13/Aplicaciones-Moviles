package com.example.app_pokedex.entities;

import java.util.ArrayList;

public class PokemonDetail {

    public String name;
    public ArrayList<TypeSlot> types;
    public Sprites sprites;

    public static class TypeSlot {

        public Type type;
    }
}