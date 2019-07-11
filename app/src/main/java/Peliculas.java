import android.os.AsyncTask;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.example.tp5.MainActivity;
import com.example.tp5.R;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Peliculas {
    ArrayList<String> ListaADevolver;






    EditText Nombre;
    Button Boton;
    ArrayAdapter miAdaptador;
    String nombre;

    public View onCreateView(LayoutInflater inflador, ViewGroup grupo, Bundle datos) {
        View VistaADevolver;
        VistaADevolver = inflador.inflate(R.layout.buscar_pelicula, grupo, false);
        Nombre = VistaADevolver.findViewById(R.id.nombreabuscar);
        Boton = VistaADevolver.findViewById(R.id.BtnBuscar);
        Boton.setOnClickListener(this);
        return VistaADevolver;
        ListaADevolver = new ArrayList<>();
    }

    public void onClick(View Vista) {
        tareaAsincronica tarea = new tareaAsincronica();
        tarea.execute();
        nombre = Nombre.getText().toString();
        Log.d("Fragments","Entra al fragment");
    }

    public void procesarJSONleido(InputStreamReader streamLeido) {
        JsonReader JSONleido = new JsonReader(streamLeido);
        try {
            JSONleido.beginObject();
            while (JSONleido.hasNext()) {
                String nombreElementoActual = JSONleido.nextName();
                Log.d("LecturaJSON", nombreElementoActual);
                if (nombreElementoActual.equals("Title")) {
                    JSONleido.beginArray();
                    while (JSONleido.hasNext()) {
                        JSONleido.beginObject();
                        while(JSONleido.hasNext()) {
                            nombreElementoActual = JSONleido.nextName();
                            Log.d("LecturaJSON", nombreElementoActual);
                            if (nombreElementoActual.equals("imbdID")) {
                                String valorElementoActual = JSONleido.nextString();
                                Log.d("LecturaJson", "valor " + valorElementoActual);
                                Log.d("LecturaJSON", "SE ACERCA AL IF");
                                ListaADevolver.add(valorElementoActual);
                            } else {
                                JSONleido.skipValue();
                            }
                        }
                        JSONleido.endObject();
                    }
                    JSONleido.endArray();
                } else {
                    JSONleido.skipValue();
                }
            }
        } catch (Exception error) {
            Log.d("LecturaJSON", "hubo un error" + error.getMessage());
        }
    }


    class tareaAsincronica extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL miRuta = new URL("http://epok.buenosaires.gob.ar/buscar/?texto=" + nombre);
                Log.d("Julian", "El nombre de la cat es " + nombre);
                HttpURLConnection miConexion = (HttpURLConnection) miRuta.openConnection();
                Log.d("AccesoApi", "Me Conecto");
                if (miConexion.getResponseCode() == 200) {

                    Log.d("AccesoApi", "Conexion Ok");
                    InputStream cuerpoRespuesta = miConexion.getInputStream();
                    InputStreamReader lectorRespuesta = new InputStreamReader(cuerpoRespuesta, "UTF-8");
                    procesarJSONleido(lectorRespuesta);
                } else {

                    Log.d("AccesoApi", "error");
                }
                miConexion.disconnect();

            } catch (Exception Error) {

                Log.d("AccesoApi", "Hubo un error al conectarse " + Error.getMessage());

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ((MainActivity)getActivity()).cambiarFragmentNombre(new FragmentBuscarPeliculas());
        }
    }




}
