package com.example.apptiendavirtual_30.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apptiendavirtual_30.R;
import com.example.apptiendavirtual_30.model.DetallePedido;
import com.example.apptiendavirtual_30.model.Pedido;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DetallePedidoAdapter extends RecyclerView.Adapter<DetallePedidoAdapter.ViewHolder> {

    private Context context;
    private ArrayList<DetallePedido> listPedido;

    public DetallePedidoAdapter (Context context)
    {
        this.context = context;
        listPedido = new ArrayList<>();
    }

    @NonNull
    @Override
    public DetallePedidoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_detalle_pedido,parent,false);
        return new ViewHolder(view);
    }

    public void listPedido(ArrayList<DetallePedido> lstPedido)
    {
        listPedido.clear();
        listPedido.addAll(lstPedido);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull DetallePedidoAdapter.ViewHolder holder, int position) {
        final DetallePedido detallePedido = listPedido.get(position);
        holder.tvDescripcionProducto.setText(detallePedido.getProduct().getNombre());
        holder.tvUnidad.setText(String.valueOf(detallePedido.getCant()));
        holder.tvPrecio.setText(String.valueOf(detallePedido.getCost()));
    }

    @Override
    public int getItemCount() {
        return listPedido.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDescripcionProducto, tvUnidad, tvPrecio;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             tvDescripcionProducto = itemView.findViewById(R.id.tvDescripcionProducto);
             tvUnidad = itemView.findViewById(R.id.tvUnidad);
             tvPrecio = itemView.findViewById(R.id.tvPrecio);
        }
    }
}
