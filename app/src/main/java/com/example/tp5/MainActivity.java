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

public class MainActivity extends Activity {


    FragmentManager adminFragments;
    FragmentTransaction transacFragments;

    String NombrePelicula;
    EditText Nombre = findViewById(R.id.nombreabuscar);



    List<String> listapeliculas;


    public void setAdminFragments(FragmentManager adminFragments) {
        this.adminFragments = adminFragments;
    }


    public List<String> GetList(){
        return  listapeliculas;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listapeliculas = new ArrayList();
        ListView MiListaDePeliculas;
        MiListaDePeliculas=findViewById(R.id.MiListaDePeliculas);
        ArrayAdapter miAdaptador;
        adminFragments=getFragmentManager();
        Fragment FragmentBuscarPeliculas;
        FragmentBuscarPeliculas = new Fragment();

        transacFragments=adminFragments.beginTransaction();
        transacFragments.replace(R.id.FragmentLista, FragmentBuscarPeliculas );
        transacFragments.commit();


    }

    }


