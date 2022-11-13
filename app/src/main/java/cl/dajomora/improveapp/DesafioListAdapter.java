package cl.dajomora.improveapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class DesafioListAdapter extends RecyclerView.Adapter<DesafioListAdapter.ViewHolder>{

    private ArrayList<DesafioData> desafios = new ArrayList<>();
    private DesafioOnClickListener listener;

    public DesafioListAdapter(ArrayList<DesafioData> desafios, DesafioOnClickListener listener) {
        this.desafios = desafios;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.desafio_card, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final DesafioData desafio = desafios.get(position);
        holder.titulo.setText(desafio.getTitle());
        holder.descripcion.setText(desafio.getDescription());
        holder.icono.setImageResource(desafio.getImgId());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClicked(desafio);
            }
        });
    }

    @Override
    public int getItemCount() {
        return desafios.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titulo;
        public TextView descripcion;
        public ImageView icono;
        public CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            this.titulo = (TextView) itemView.findViewById(R.id.tituloDesafio);
            this.descripcion = (TextView) itemView.findViewById(R.id.descripcionDesafio);
            this.icono = (ImageView) itemView.findViewById(R.id.iconoDesafio);
            cardView = (CardView) itemView.findViewById(R.id.desafioCardView);
        }
    }
}
