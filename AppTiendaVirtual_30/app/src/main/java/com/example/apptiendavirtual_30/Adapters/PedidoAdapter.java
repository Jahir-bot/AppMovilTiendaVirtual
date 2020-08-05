package com.example.apptiendavirtual_30.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.apptiendavirtual_30.Detalles.DetallePedidoActivity;
import com.example.apptiendavirtual_30.R;
import com.example.apptiendavirtual_30.model.DetallePedido;
import com.example.apptiendavirtual_30.model.Pedido;
import com.example.apptiendavirtual_30.model.Usuario;

import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Pedido>  listPedido;
    private boolean typeUser = false;

    public PedidoAdapter(Context context)
    {
        this.context = context;
        listPedido = new ArrayList<>();
    }

    @NonNull
    @Override
    public PedidoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_pedido,parent,false);
        return new ViewHolder(view);
    }

    public void listaPedido(ArrayList<Pedido> lstPedido, boolean typeUser)
    {
        listPedido.clear();
        listPedido.addAll(lstPedido);
        notifyDataSetChanged();
        this.typeUser = typeUser;
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoAdapter.ViewHolder holder, int position) {
        final Pedido pedido = listPedido.get(position);
        holder.tvDescripcionProducto.setText(String.valueOf(pedido.getNumberGenerated()));
        if (typeUser==false)
        {
            holder.tvUnidad.setText(String.valueOf(pedido.getUser().getName()));
        }else
        {
            holder.tvUnidad.setText(String.valueOf(pedido.getTotal()));
        }
        holder.btnVerPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = pedido.getUser().getName();
                Usuario usuario = new Usuario( pedido.getUser().getId(),pedido.getUser().getName());
                System.out.println("Nombre Adapter: "+pedido.getUser().getName());
                Intent intent = new Intent(context, DetallePedidoActivity.class);
                intent.putExtra("listaPedido",pedido);
                intent.putExtra("name",usuario);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listPedido.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDescripcionProducto, tvUnidad;
        private Button btnVerPedido;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescripcionProducto = itemView.findViewById(R.id.tvDescripcionProducto);
            tvUnidad = itemView.findViewById(R.id.tvUnidad);
            btnVerPedido = itemView.findViewById(R.id.btnVerPedido);
        }
    }
}
