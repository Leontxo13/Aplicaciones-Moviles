package com.example.semana7.entities;

public class Contacto {

    private String id;
    private String nombre;
    private String telefono;
    private String genero;
    private String direccion;
    public Contacto() {
    }

    public Contacto(String id, String nombre, String telefono, String genero, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.genero = genero;
        this.direccion = direccion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
