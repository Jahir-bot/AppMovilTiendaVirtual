package com.example.apptiendavirtual_30.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apptiendavirtual_30.R;
import com.example.apptiendavirtual_30.model.Producto;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Producto> productos;

    public StockAdapter(Context context)
    {
        this.context = context;
        productos = new ArrayList<>();
    }

    @NonNull
    @Override
    public StockAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_stock,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockAdapter.ViewHolder holder, int position) {
        final Producto producto = productos.get(position);
        holder.txtCodigo.setText(String.valueOf(producto.getId()));
        holder.txtDescripcion.setText(producto.getNombre());
        holder.txtStock.setText(String.valueOf(producto.getStock()));
    }
    public void addProductos(ArrayList<Producto> newLista)
    {
        productos.clear();
        productos.addAll(newLista);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtCodigo, txtDescripcion, txtStock;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCodigo = itemView.findViewById(R.id.txtCodigo);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            txtStock = itemView.findViewById(R.id.txtStock);
        }
    }
}
