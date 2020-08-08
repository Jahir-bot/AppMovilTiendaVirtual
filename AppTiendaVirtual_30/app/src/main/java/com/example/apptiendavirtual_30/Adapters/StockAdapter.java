package com.example.apptiendavirtual_30.Adapters;

import android.content.Context;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptiendavirtual_30.R;
import com.example.apptiendavirtual_30.model.Mensajes;
import com.example.apptiendavirtual_30.model.Producto;
import com.example.apptiendavirtual_30.model.URI;
import com.example.apptiendavirtual_30.model.Usuario;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Producto> productos;
    private String  url_base="/product/update/stock";

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
    public void onBindViewHolder(@NonNull final StockAdapter.ViewHolder holder, final int position) {
        final Producto producto = productos.get(position);
        holder.txtCodigo.setText(String.valueOf(producto.getId()));
        holder.txtDescripcion.setText(producto.getNombre());
        holder.txtStock.setText(String.valueOf(producto.getStock()));
        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (holder.etAnadirStock.getText().toString().equals(""))
                {
                    Toast.makeText(context,"Ingrese las unidades de stock que desea añadir",Toast.LENGTH_SHORT).show();
                }else
                {
                    int stock = Integer.parseInt(holder.etAnadirStock.getText().toString());

                    Producto producto1 = new Producto (productos.get(position).getId(),stock);

                    //anadirStock(producto1);

                    Mensajes mensajes = new Mensajes();

                    try {
                        URI uri = new URI();
                        URL url = new URL(uri.getUrl()+url_base);
                        HttpURLConnection conn= (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("PUT");
                        conn.setRequestProperty("Content-Type","application/json");
                        conn.setDoOutput(true);
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);

                        Gson g = new Gson();
                        String input = g.toJson(producto1, Producto.class);

                        OutputStream os = conn.getOutputStream();
                        os.write(input.getBytes());
                        os.flush();

                        if(conn.getResponseCode() !=200)
                        {
                            throw new RuntimeException("Error de Conexion"+conn.getResponseCode());
                        }

                        BufferedReader tecla2 = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                        StringBuilder respuesta = new StringBuilder();

                        String out;

                        while((out=tecla2.readLine())!=null)
                        {
                            respuesta.append(out);
                        }

                        mensajes =g.fromJson(respuesta.toString(), Mensajes.class);
                        conn.disconnect();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    holder.etAnadirStock.setText("");
                    int suma = producto.getStock()+stock;
                    holder.txtStock.setText(String.valueOf(suma));
                    Toast.makeText(context,"Se añadió a su stock",Toast.LENGTH_SHORT).show();
                    System.out.println("AQUI DATA PRODUCTO "+producto.getStock());
                    System.out.println("aquita data stock "+stock);

                    if (suma>=10)
                    {
                        productos.remove(position);
                        notifyDataSetChanged();
                    }

                }

            }
        });
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
        private EditText etAnadirStock;
        private Button btnView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCodigo = itemView.findViewById(R.id.txtCodigo);
            txtDescripcion = itemView.findViewById(R.id.txtDescripcion);
            txtStock = itemView.findViewById(R.id.txtStock);
            etAnadirStock = itemView.findViewById(R.id.etAnadirStock);
            btnView = itemView.findViewById(R.id.btnView);
        }
    }

    public Mensajes anadirStock(Producto producto)
    {
        Mensajes mensajes = new Mensajes();

        try {
            URI uri = new URI();
            URL url = new URL(uri.getUrl()+url_base);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setDoOutput(true);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Gson g = new Gson();
            String input = g.toJson(producto, Producto.class);

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if(conn.getResponseCode() !=200)
            {
                throw new RuntimeException("Error de Conexion"+conn.getResponseCode());
            }

            BufferedReader tecla2 = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder respuesta = new StringBuilder();

            String out;

            while((out=tecla2.readLine())!=null)
            {
                respuesta.append(out);
            }

            mensajes =g.fromJson(respuesta.toString(), Mensajes.class);
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mensajes;
    }
}
