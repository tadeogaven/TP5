package com.example.tp5;

import android.app.Fragment;

import android.app.FragmentTransaction;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {



    FragmentActivity adminFragments;
    FragmentTransaction transacFragments;
    List<String> listapeliculas;


    public void setAdminFragments(FragmentActivity adminFragments) {
        this.adminFragments = adminFragments;
    }

    String NombreProcesar;


    public List<String> GetList(){
        return  listapeliculas;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listapeliculas = new ArrayList();
        ListView miListadeResultados;
        ArrayAdapter miAdaptador;
        String nombre;
        EditText NombrePelicula;
        NombrePelicula=findViewById(R.id.nombreabuscar);
        Button boton;

        nombre= NombrePelicula.toString();


        adminFragments=FragmentActivity();
        android.app.Fragment NombreCat;

        transacFragments=adminFragments.beginTransaction();
        transacFragments.replace(R.id.FragmentLista, NombrePelicula);
        transacFragments.commit();


    }




    }

    }

