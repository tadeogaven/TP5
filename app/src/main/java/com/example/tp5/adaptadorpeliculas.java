package com.example.tp5;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class adaptadorpeliculas extends BaseAdapter {

    private ArrayList<Peliculas> ListPeliculas;
    private Context Contexto;


    public adaptadorpeliculas(ArrayList<Peliculas> Lista, Context Contexto) {
        ListPeliculas = Lista;
        this.Contexto = Contexto;
    }

    public int getCount() {
        return ListPeliculas.size();
    }

    public Peliculas getItem(int Position) {
        Peliculas Pelicula;
        Pelicula = ListPeliculas.get(Position);
        return Pelicula;
    }

    public long getItemId(int Position) {
        return 148024;
    }

    public View getView(int Position, View view, ViewGroup GrupoActual) {
        View VistaADevolver;
        VistaADevolver = null;

        LayoutInflater infladorDeLayout;
        infladorDeLayout = (LayoutInflater) Contexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        VistaADevolver = infladorDeLayout.inflate(R.layout.caracteristicaspeliculas, GrupoActual, false);

        Peliculas PeliculaActual = getItem(Position);
        String Title = PeliculaActual._titulo;
        String Año = PeliculaActual._fechalanz;
        String Poster = PeliculaActual._imagen;

        TextView Titulo;
        Titulo = (TextView) VistaADevolver.findViewById(R.id.title);
        Titulo.setText(Title);
        TextView FechaLanzamiento;
        FechaLanzamiento = (TextView) VistaADevolver.findViewById(R.id.fecha);
        FechaLanzamiento.setText(Año+"");

        final ImageView Imagen;
        Imagen = (ImageView) VistaADevolver.findViewById(R.id.imagen);

        final class descargarImagen extends AsyncTask<String, Void, Bitmap> {
            protected Bitmap doInBackground(String... ruta){
                Bitmap imagen;
                imagen = null;
                try{
                    URL miRuta;
                    miRuta = new URL(ruta[0]);
                    HttpURLConnection conexion;
                    conexion = (HttpURLConnection) miRuta.openConnection();
                    if(conexion.getResponseCode()==200){
                        InputStream cuerpoDatos = conexion.getInputStream();
                        BufferedInputStream lectorEntrada = new BufferedInputStream(cuerpoDatos);
                        imagen = BitmapFactory.decodeStream(lectorEntrada);
                        conexion.disconnect();
                    }
                } catch(Exception e){
                    Log.d("error", "Error " + e.getMessage());
                }
                return imagen;
            }
            protected void onPostExecute (Bitmap imagen){
                if (imagen!=null){
                    Imagen.setImageBitmap(imagen);
                } else{
                    Imagen.setImageResource(android.R.drawable.ic_dialog_alert);
                }
            }
        }
        descargarImagen tarea = new descargarImagen();
        tarea.execute(Poster);

        return VistaADevolver;
    }
}
