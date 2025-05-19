package com.example.cultitraker.AdapterItems;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cultitraker.R;

import java.util.ArrayList;

public class AdapterTareas extends RecyclerView.Adapter<AdapterTareas.ViewHolder>{

    private View.OnClickListener onItemClickListener;

    private final int layoutId;
    private ArrayList<AdapterModel> listaDatos = new ArrayList<>();
    private Context context;
    public AdapterTareas(ArrayList<AdapterModel> listaDatos, Context context, int layoutId) {
        this.listaDatos = listaDatos;
        this.context = context;
        this.layoutId = layoutId;
    }
    public void setOnItemClickListener(View.OnClickListener listener) {
        this.onItemClickListener = listener;
    }


    @NonNull
    @Override
    public AdapterTareas.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(layoutId, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AdapterModel adapterModel = listaDatos.get(position);
        holder.txt_titulo.setText(adapterModel.getTitulo());
        holder.txt_name.setText(adapterModel.getSubTitulo());
        holder.txt_subName.setText(adapterModel.getParrafo());
        holder.txt_detail.setText(adapterModel.getDetail());
        holder.itemView.setTag(position);
        if (onItemClickListener != null) {
            holder.itemView.setOnClickListener(onItemClickListener);
        }
        holder.btn_Eliminar.setTag(position);
        holder.btn_Eliminar.setOnClickListener(onItemClickListener);
    }

    @Override
    public int getItemCount() {
        return listaDatos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_titulo,txt_name,txt_subName,txt_detail;

        ImageView btn_Eliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_titulo = itemView.findViewById(R.id.txt_titulo);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_subName = itemView.findViewById(R.id.txt_subName);
            txt_detail = itemView.findViewById(R.id.txt_detail);
            btn_Eliminar = itemView.findViewById(R.id.btn_EliminarTarea);
        }
    }
}
