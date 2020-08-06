package com.example.apptiendavirtual_30.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.apptiendavirtual_30.Detalles.DetalleCatalogoActivity;
import com.example.apptiendavirtual_30.R;
import com.example.apptiendavirtual_30.model.Producto;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CatalogoAdapter extends RecyclerView.Adapter<CatalogoAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Producto> listProducto;

    public CatalogoAdapter (Context context)
    {
        this.context = context;
        listProducto = new ArrayList<>();
    }

    @NonNull
    @Override
    public CatalogoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_catalogo,parent,false);
        return new ViewHolder(view);
    }

    public void addCatalogo(ArrayList<Producto> lstProducto)
    {
        listProducto.clear();
        listProducto.addAll(lstProducto);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull final CatalogoAdapter.ViewHolder holder, int position) {
        final Producto producto = listProducto.get(position);
        holder.txtProducto.setText(producto.getNombre());
        holder.txtCantidad.setText(String.valueOf(producto.getStock()));
        holder.txtPrecio.setText(String.valueOf(producto.getCostoUnitario()));
        Picasso.get().load(producto.getEnlaceImagen()).into(holder.imgProducto);
        SharedPreferences preferences = context.getSharedPreferences("appBodega",Context.MODE_PRIVATE);
        if (preferences.getString("typeUser","").equals("Admin"))
        {
            holder.btnAgregar.setVisibility(View.GONE);
        }
        holder.btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DetalleCatalogoActivity.class);
                intent.putExtra("enlaceProducto",producto);
                context.startActivity(intent);



            }
        });
    }

    @Override
    public int getItemCount() {
        return listProducto.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgProducto;
        private TextView txtProducto, txtPrecio, txtCantidad;
        private Button btnAgregar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgProducto = itemView.findViewById(R.id.imgDetaProducto);
            txtProducto = itemView.findViewById(R.id.txtDetaProducto);
            txtPrecio = itemView.findViewById(R.id.txtDetaPrecio);
            txtCantidad = itemView.findViewById(R.id.txtDetaCantidad);
            btnAgregar = itemView.findViewById(R.id.btnAgregar);
        }
    }
}
