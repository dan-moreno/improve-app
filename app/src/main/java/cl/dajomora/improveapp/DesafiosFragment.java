package cl.dajomora.improveapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class DesafiosFragment extends Fragment implements DesafioOnClickListener {

    DesafioListAdapter adapter;
    RecyclerView recyclerView;
    private Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_desafios, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bundle = getActivity().getIntent().getExtras();
        ArrayList<DesafioData> desafiosList = new ArrayList<>();

        if(bundle.getString("desafio_completado").equals("")){
            desafiosList.add( new DesafioData("Tomar agua", "Toma 2 litros de agua", R.drawable.ic_baseline_water,
                    "Para superar este desafío deberás tomar al menos 2 litros de agua al día. Si " +
                            "logras mantener esta rutina por tres días, desbloquearás un logro"));
            desafiosList.add( new DesafioData("Comer bien", "Come tres veces", R.drawable.ic_baseline_restaurant,
                    "Para superar este desafío deberás comer tres veces al día, respetando los horarios" +
                            "de cada comida. Si logras mantener esta rutina por tres días, desbloquearás un logro"));
            desafiosList.add( new DesafioData("Descansar", "Duerme 8 horas", R.drawable.ic_baseline_sleep,
                    "Para superar este desafío deberás dormir durante al menos ocho horas. Si " +
                            "logras mantener esta rutina por tres días, desbloquearás un logro"));
        }

        if(bundle.getString("desafio_completado").equals("Tomar agua")){
            desafiosList.add( new DesafioData("Comer bien", "Come tres veces", R.drawable.ic_baseline_restaurant,
                    "Para superar este desafío deberás comer tres veces al día, respetando los horarios" +
                            "de cada comida. Si logras mantener esta rutina por tres días, desbloquearás un logro"));
            desafiosList.add( new DesafioData("Descansar", "Duerme 8 horas", R.drawable.ic_baseline_sleep,
                    "Para superar este desafío deberás dormir durante al menos ocho horas. Si " +
                            "logras mantener esta rutina por tres días, desbloquearás un logro"));
        }

        if(bundle.getString("desafio_completado").equals("Comer bien")){
            desafiosList.add( new DesafioData("Descansar", "Duerme 8 horas", R.drawable.ic_baseline_sleep,
                    "Para superar este desafío deberás dormir durante al menos ocho horas. Si " +
                            "logras mantener esta rutina por tres días, desbloquearás un logro"));
        }

        recyclerView = view.findViewById(R.id.desafioRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new DesafioListAdapter(desafiosList, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(DesafioData desafioData) {
        Intent intent = new Intent(getActivity(), DesafioDetalleActivity.class);
        intent.putExtra("titulo",desafioData.getTitle());
        intent.putExtra("icono",desafioData.getImgId());
        intent.putExtra("descripcion",desafioData.getDetails());
        intent.putExtra("cuenta_nombre", bundle.getString("cuenta_nombre"));
        intent.putExtra("cuenta_correo", bundle.getString("cuenta_correo"));
        intent.putExtra("cuenta_ocupacion", bundle.getString("cuenta_ocupacion"));
        startActivity(intent);
    }
}
