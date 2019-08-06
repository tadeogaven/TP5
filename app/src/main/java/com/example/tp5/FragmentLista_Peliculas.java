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

                URL Ruta = new URL("http://www.omdbapi.com/?apikey=b8ac6455&s="+ titulo);
                HttpURLConnection miConexion = (HttpURLConnection) Ruta.openConnection();
                if (miConexion.getResponseCode() == 200) {
                    InputStream cuerpoRespuesta =    miConexion.getInputStream();
                    InputStreamReader JsonCrudo = new InputStreamReader(cuerpoRespuesta, "UTF-8");
                    procesarJSONPeliculas(JsonCrudo);
                }
                miConexion.disconnect();

            } catch (Exception error){
            }
            return null;
        }
        protected void onPostExecute(Void aVoid){
            super.onPostExecute(aVoid);
            MiListaDePeliculas.setAdapter(adaptadorPeliculas);
        }
    }
    public void procesarJSONPeliculas(InputStreamReader JsonCrudo){
        listPeliculas.clear();
        try {
            JsonParser parseador = new JsonParser();
            JsonObject objJsonCrudo = parseador.parse(JsonCrudo).getAsJsonObject();
            JsonArray buscar = null;
            try{
                buscar = objJsonCrudo.get("Search").getAsJsonArray();
            }catch (Exception error){
                Log.d(FragmentLista_Peliculas.class.getSimpleName(), error.getMessage());
            }
            if(buscar != null){
                for(int i = 0; i < buscar.size(); i++){
                    Peliculas pelicula = new Peliculas();
                    JsonObject peli = buscar.get(i).getAsJsonObject();
                    pelicula._titulo=peli.get("Title").getAsString();
                    pelicula._id=peli.get("imdbID").getAsString();
                    pelicula._fechalanz=peli.get("Year").getAsInt();
                    pelicula._imagen=peli.get("Poster").getAsString();
                    listPeliculas.add(pelicula);
                }
            }else{
                objJsonCrudo.get("Error").getAsString();
            }

        } catch (Exception error) {
            Log.d("capoMain", "Error al procesar JSON: (" + error.getMessage() + ")");
        }
    }

}
