import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tp5.R;

public class FragmentBuscarPeliculas extends Fragment {
    @Override
    public View onCreateView(LayoutInflater Inflador, ViewGroup GrupoDeLaVista, Bundle Datos) {
        View VistaADevolver;
        VistaADevolver=Inflador.inflate(R.id.FragmentNombre, GrupoDeLaVista, false);
        return VistaADevolver;
    }
}
