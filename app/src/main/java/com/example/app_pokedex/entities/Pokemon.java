package com.example.app_pokedex.entities;

import com.google.gson.annotations.SerializedName;

public class Pokemon {
    public int id;
    public String name;
    public String url;


    public Pokemon(String name, String url) {
        this.name = name;
        this.url = url;
    }

    //asi extraemos la url
    public int getId() {
        String[] urlParts = url.split("/");
        return Integer.parseInt(urlParts[urlParts.length - 1]);
    }
}