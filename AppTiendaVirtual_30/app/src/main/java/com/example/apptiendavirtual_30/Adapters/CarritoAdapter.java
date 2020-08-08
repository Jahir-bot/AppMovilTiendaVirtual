package com.example.apptiendavirtual_30.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptiendavirtual_30.CarritoActivity;
import com.example.apptiendavirtual_30.R;
import com.example.apptiendavirtual_30.model.DetallePedido;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ViewHolder> {

    private Context context;
    private ArrayList<DetallePedido> listDetallePedido;

    public CarritoAdapter (Context context)
    {
        this.context = context;
        listDetallePedido = new ArrayList<>();
    }

    @NonNull
    @Override
    public CarritoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_carrito,parent,false);
        return new ViewHolder(view);
    }

    public void listarCarrito (ArrayList<DetallePedido> lstDetallePedido)
    {
        listDetallePedido.clear();
        listDetallePedido.addAll(lstDetallePedido);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull CarritoAdapter.ViewHolder holder, final int position) {
        final DetallePedido detallePedido = listDetallePedido.get(position);
        DecimalFormat decimalFormat = new DecimalFormat(".00");
        holder.txtProducto.setText(detallePedido.getProduct().getNombre());
        holder.txtUnidades.setText(String.valueOf(detallePedido.getCant()));
        holder.txtCosto.setText(String.valueOf(decimalFormat.format(detallePedido.getCant()*detallePedido.getCost())));
        Picasso.get().load(detallePedido.getProduct().getEnlaceImagen()).into(holder.imgProducto);
        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDetallePedido.remove(position);
                Toast.makeText(context,"Producto Eliminado",Toast.LENGTH_SHORT).show();
                System.out.println("AQUI LA DATA:"+listDetallePedido);
                SharedPreferences.Editor editor = context.getSharedPreferences("appCarrito",Context.MODE_PRIVATE).edit();

                Gson g = new Gson();

                String jsonListObjetos = g.toJson(listDetallePedido);
                editor.putString("listDetallePedido",jsonListObjetos);
                editor.apply();
                notifyDataSetChanged();
                ((CarritoActivity)context).finish();
                context.startActivity(new Intent(context,CarritoActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDetallePedido.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtProducto, txtCosto, txtUnidades;
        private Button  btnEliminar;
        private ImageView imgProducto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProducto = itemView.findViewById(R.id.txtProducto);
            txtCosto = itemView.findViewById(R.id.txtCosto);
            txtUnidades = itemView.findViewById(R.id.txtUnidades);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
            imgProducto = itemView.findViewById(R.id.imgProducto);
        }
    }
}