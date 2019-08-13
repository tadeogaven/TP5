package com.example.tp5;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FragmentLista_Peliculas extends Fragment {
    ListView MiListaDePeliculas;
    String titulo;
    ArrayList<Peliculas> listPeliculas = new ArrayList<Peliculas>();
    adaptadorpeliculas adaptadorPeliculas;
    MainActivity mainActivity;

    public View onCreateView (LayoutInflater infladorDeLayout, ViewGroup grupo, Bundle Datos){

        View vistaADevolver;
        vistaADevolver = infladorDeLayout.inflate(R.layout.lv_peliculas, grupo, false);

        mainActivity=(MainActivity) getActivity();
        titulo = mainActivity.LeeteEsteTitulo();
        adaptadorPeliculas = new adaptadorpeliculas(listPeliculas, vistaADevolver.getContext());


        MiListaDePeliculas = vistaADevolver.findViewById(R.id.MiListaDePeliculas);
        Log.d("eskere", "" + MiListaDePeliculas);
        MiListaDePeliculas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Peliculas peliculas = listPeliculas.get(position);
                mainActivity.ProcesarDatosRecibidos(peliculas._id);
            }
        });

        tareaAsincronicaPeliculas buscarPeliculas = new tareaAsincronicaPeliculas(titulo);
        buscarPeliculas.execute();

        return vistaADevolver;
    }

    public class tareaAsincronicaPeliculas extends AsyncTask<Void, Void, Void> {
        String titulo;

        public tareaAsincronicaPeliculas(String title){


            titulo=title;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            try{
                Log.d("eskere", titulo);
                URL Ruta = new URL("http://www.omdbapi.com/?apikey=b8ac6455&s="+ titulo);
                HttpURLConnection miConexion = (HttpURLConnection) Ruta.openConnection();
                if (miConexion.getResponseCode() == 200) {
                    InputStream stream = miConexion.getInputStream();
                    InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
                    JsonParser parser = new JsonParser();
                    JsonObject json = parser.parse(reader).getAsJsonObject();

                    JsonArray jsonMovies = json.getAsJsonArray("Search");
                    Log.d("eskere", "" + jsonMovies.size());
                    for (int i = 0; i < jsonMovies.size(); i++) {
                        JsonObject jsonMovie = jsonMovies.get(i).getAsJsonObject();
                        Peliculas peli = new Peliculas();
                        peli._titulo = jsonMovie.get("Title").getAsString();
                        peli._fechalanz = jsonMovie.get("Year").getAsString();
                        peli._imagen = jsonMovie.get("Poster").getAsString();
                        peli._id = jsonMovie.get("imdbID").getAsString();
                        listPeliculas.add(peli);
                    }
                }
                miConexion.disconnect();
                Log.d("Estado", "se desconecto");

            } catch (Exception error){
                Log.d("error", "es" + error.getMessage());
            }
            return null;
        }



        protected void onPostExecute(Void aVoid){
            Log.d("eskere", ""+listPeliculas.size());
            super.onPostExecute(aVoid);
            MiListaDePeliculas.setAdapter(adaptadorPeliculas);
        }
    }

}
