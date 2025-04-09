package com.example.actividad_semana03.Clases;

public class ColorClass {
    private String nombre;
    private String codigoHex;
    private int valorColor;

    public ColorClass(String nombre, String codigoHex) {
        this.nombre = nombre;
        this.codigoHex = codigoHex;
        this.valorColor = android.graphics.Color.parseColor(codigoHex);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoHex() {
        return codigoHex;
    }

    public void setCodigoHex(String codigoHex) {
        this.codigoHex = codigoHex;
        this.valorColor = android.graphics.Color.parseColor(codigoHex);
    }

    public int getValorColor() {
        return valorColor;
    }
}