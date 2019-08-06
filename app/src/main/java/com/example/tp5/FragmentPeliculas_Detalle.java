package com.example.tp5;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class FragmentPeliculas_Detalle extends Fragment {
    Peliculas pelicula = new Peliculas();
    TextView titulo;
    ImageView imagenPelicula;
    TextView director;
    TextView fechalanz;
    TextView sinopsis;
    String idPelicula;
    MainActivity mainactivity;

    public View onCreateView (LayoutInflater infladorDeLayout, ViewGroup grupo, Bundle Datos){

        View vistaADevolver;
        vistaADevolver = infladorDeLayout.inflate(R.layout.caracteristicaspeliculas, grupo, false);;

        mainactivity = (MainActivity) getActivity();
        idPelicula = mainactivity.LeeteEsteId();
        imagenPelicula = vistaADevolver.findViewById(R.id.imagen);
        titulo = vistaADevolver.findViewById(R.id.title);
        fechalanz = vistaADevolver.findViewById(R.id.fecha);
        director = vistaADevolver.findViewById(R.id.director);
        sinopsis = vistaADevolver.findViewById(R.id.sinopsis);




        return vistaADevolver;
    }

}
