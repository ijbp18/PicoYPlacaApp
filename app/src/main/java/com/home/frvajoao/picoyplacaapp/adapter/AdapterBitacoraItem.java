package com.home.frvajoao.picoyplacaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.home.frvajoao.picoyplacaapp.R;
import com.home.frvajoao.picoyplacaapp.model.Bitacora;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class AdapterBitacoraItem extends RecyclerView.Adapter<AdapterBitacoraItem.MyViewHolder> {

    Context context;
    List<Bitacora> bitacoraList;


    public AdapterBitacoraItem(Context context, List<Bitacora> bitacoras) {

        this.context = context;
        this.bitacoraList = bitacoras;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.layout_bitacora_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.txt_nombre_vacuna.setText(new StringBuilder(bitacoraList.get(position).getPlaca()));
        holder.txt_fecha_registro.setText(new StringBuilder("Fecha registro: ")
                .append(bitacoraList.get(position).getFechaRegistro()));
        holder.txt_fecha_consulta.setText(new StringBuilder("Fecha consulta: ")
                .append(bitacoraList.get(position).getFechaConsulta()));

        String infraccion = bitacoraList.get(position).getInfraccion() == 1 ? "SÍ": "NO";

            holder.txt_dosis.setText(new StringBuilder("Infracción: ")
                .append(infraccion));

    }

    @Override
    public int getItemCount() {
        return bitacoraList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {



        Unbinder unbinder;


        @BindView(R.id.txt_placa)
        TextView txt_nombre_vacuna;
        @BindView(R.id.txt_fecha_registro)
        TextView txt_fecha_registro;
        @BindView(R.id.txt_fecha_consulta)
        TextView txt_fecha_consulta;
        @BindView(R.id.txt_infraccion)
        TextView txt_dosis;




        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            unbinder = ButterKnife.bind(this, itemView);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
