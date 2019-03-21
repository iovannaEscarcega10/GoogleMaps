package com.example.googlemaps;

public class Mapa {

    private double Latitud;
    private double Longitud;

    public Mapa() {
    }

    public Mapa(double latitud, double longitud) {
        Latitud = latitud;
        Longitud = longitud;
    }

    public double getLatitud() {
        return Latitud;
    }

    public void setLatitud(double latitud) {
        Latitud = latitud;
    }

    public double getLongitud() {
        return Longitud;
    }

    public void setLongitud(double longitud) {
        Longitud = longitud;
    }
}
