package com.example.tp5;

import android.media.Image;

public class Peliculas {
    public String _id;
    public int _fechalanz;
    public String  _titulo;
    public String _director;
    public String _imagen;
    public String _sinopsis;

    public Peliculas(String id, int fechalanz, String titulo, String director, String imagen, String sinopsis)
    {
        _id = id;
        _fechalanz = fechalanz;
        _titulo = titulo;
        _director = director;
        _imagen = imagen;
        _sinopsis = sinopsis;
    }
    public Peliculas()
    {

    }
    public Peliculas(String id, int fechalanz,String imagen, String titulo)
    {
        _id = id;
        _fechalanz = fechalanz;
        _imagen = imagen;
        _titulo = titulo;
    }
}


