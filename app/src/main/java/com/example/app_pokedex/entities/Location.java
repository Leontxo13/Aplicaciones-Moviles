package com.example.app_pokedex.entities;

public class Location {
    private int id;
    private int pokemonId;
    private String pokemonName;
    private double latitude;
    private double longitude;

    public Location() {
    }

    public Location(int pokemonId, String pokemonName, double latitude, double longitude) {
        this.pokemonId = pokemonId;
        this.pokemonName = pokemonName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPokemonId() {
        return pokemonId;
    }

    public void setPokemonId(int pokemonId) {
        this.pokemonId = pokemonId;
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public void setPokemonName(String pokemonName) {
        this.pokemonName = pokemonName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", pokemonId=" + pokemonId +
                ", pokemonName='" + pokemonName + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}