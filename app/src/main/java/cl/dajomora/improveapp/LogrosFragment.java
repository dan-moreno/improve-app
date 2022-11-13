package cl.dajomora.improveapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import java.util.ArrayList;

public class LogrosFragment extends Fragment {

    private LogroGridAdapter adapter;
    private GridView gridView;
    private ArrayList<LogroData> logros = new ArrayList<>();
    private LogroData hidratado = new LogroData("Hidratado", R.drawable.ic_baseline_water);
    private LogroData descansado = new LogroData("Descansado", R.drawable.ic_baseline_sleep);
    private LogroData nutrido = new LogroData("Nutrido", R.drawable.ic_baseline_restaurant);
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_logros, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        gridView = view.findViewById(R.id.gridLogros);
        adapter = new LogroGridAdapter(getContext(), logros);
        gridView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        bundle = getActivity().getIntent().getExtras();

        if(bundle.getString("desafio_completado").equals("Tomar agua")) {
            if(!(logros.contains(hidratado))){
                logros.add(hidratado);
            }
        }

        if(bundle.getString("desafio_completado").equals("Comer bien")) {
            if(!(logros.contains(hidratado))){
                logros.add(hidratado);
                logros.add(nutrido);
            }
        }

        if(bundle.getString("desafio_completado").equals("Descansar")){
            if(!(logros.contains(hidratado))){
                logros.add(hidratado);
                logros.add(nutrido);
                logros.add(descansado);
            }
        }
    }
}
