package cl.dajomora.improveapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class LogroGridAdapter extends ArrayAdapter<LogroData> {

    public LogroGridAdapter(@NonNull Context context, ArrayList<LogroData> logroDataArrayList) {
        super(context, 0, logroDataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.logro_card, parent, false);
        }

        LogroData logroData = getItem(position);
        TextView tituloLogro = listItemView.findViewById(R.id.tituloLogro);
        ImageView iconoLogro = listItemView.findViewById(R.id.iconoLogro);
        tituloLogro.setText(logroData.getTitulo());
        iconoLogro.setImageResource(logroData.getIcono());
        return listItemView;
    }
}
