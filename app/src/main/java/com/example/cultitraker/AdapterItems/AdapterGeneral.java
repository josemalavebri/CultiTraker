package com.example.cultitraker.AdapterItems;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultitraker.Models.Usuario;
import com.example.cultitraker.R;

import java.util.ArrayList;

public class AdapterGeneral extends RecyclerView.Adapter<AdapterGeneral.ViewHolder>{

    private ArrayList<AdapterModel> listaDatos = new ArrayList<>();
    private Context context;
    public AdapterGeneral(ArrayList<AdapterModel> listaDatos, Context context) {
        this.listaDatos = listaDatos;
        this.context = context;
    }

    @NonNull
    @Override
    public AdapterGeneral.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.card_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AdapterModel adapterModel = listaDatos.get(position);
        holder.txt_titulo.setText(adapterModel.getTitulo());
        holder.txt_name.setText(adapterModel.getSubTitulo());
        holder.txt_subName.setText(adapterModel.getParrafo());
        holder.txt_detail.setText(adapterModel.getDetail());
    }

    @Override
    public int getItemCount() {
        return listaDatos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_titulo,txt_name,txt_subName,txt_detail;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_titulo = itemView.findViewById(R.id.txt_titulo);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_subName = itemView.findViewById(R.id.txt_subName);
            txt_detail = itemView.findViewById(R.id.txt_detail);
        }
    }
}
