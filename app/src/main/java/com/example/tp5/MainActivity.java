package com.example.tp5;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.List;

public class MainActivity extends Activity {
    EditText ingreso;
    String titulo;
    String idpelicula;
    adaptadorpeliculas adaptadorPeliculas;
    FragmentManager adminFragment;
    FragmentTransaction transacFragment;
    Fragment fragmentPeliculaDetalle;
    Fragment fragmentListaPelicula;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ingreso = findViewById(R.id.nombreabuscar);


        adminFragment = getFragmentManager();
        fragmentListaPelicula = new FragmentLista_Peliculas();
        fragmentPeliculaDetalle = new FragmentPeliculas_Detalle();


    }
    public void Buscar (View vista){
        titulo = ingreso.getText().toString();
        transacFragment = adminFragment.beginTransaction();
        transacFragment.replace(R.id.FragmentHolder, fragmentListaPelicula);
        transacFragment.commit();
    }

    public String LeeteEsteTitulo (){ return titulo; }

    public void ProcesarDatosRecibidos (String id){
        idpelicula = id;
        transacFragment = adminFragment.beginTransaction();
        transacFragment.replace(R.id.FragmentHolder, fragmentPeliculaDetalle);
        transacFragment.commit();
    }

    public String LeeteEsteId (){ return idpelicula; }

    }


