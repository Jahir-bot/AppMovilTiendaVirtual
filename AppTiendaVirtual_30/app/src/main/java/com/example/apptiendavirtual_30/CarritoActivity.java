package com.example.apptiendavirtual_30;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptiendavirtual_30.Adapters.CarritoAdapter;
import com.example.apptiendavirtual_30.model.DetallePedido;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class CarritoActivity extends AppCompatActivity {
    private CarritoAdapter carritoAdapter;
    private RecyclerView rvCarrito;
    private ArrayList<DetallePedido> listDetallePedidos = new ArrayList<>();
    private TextView txtCantidadProducto, txtTotal;
    private Button btnCompra;
    private DetallePedido detallePedido = new DetallePedido();
    private double total = 0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrito);
        btnCompra = findViewById(R.id.btnCompra);
        txtCantidadProducto = findViewById(R.id.txtCantidadProducto);
        txtTotal = findViewById(R.id.txtTotal);
        rvCarrito = findViewById(R.id.rvCarrito);
        carritoAdapter = new CarritoAdapter(this);
        rvCarrito.setAdapter(carritoAdapter);
        rvCarrito.setLayoutManager(new LinearLayoutManager(this));



        SharedPreferences preferences = getSharedPreferences("appCarrito",MODE_PRIVATE);
        System.out.println("AQUI LOS PEDIDOS: "+preferences.getString("listDetallePedido","JSON"));


        // capturar el array/Lista de asientos.
        JsonElement element = JsonParser.parseString(preferences.getString("listDetallePedido","JSON"));
        System.out.println("AQUI EL ELEMENT : "+element);

        // Objeto GSON que sirve para convertir o desconvertir datos/informacion.
        Gson gson = new Gson();

        // getAsJsonArray() = sirve para obtener la lista en forma de Array.
        JsonArray jsonArray = element.getAsJsonArray();

        System.out.println("AQUI JSONARRAY: "+jsonArray);



        for (int i = 0; i < jsonArray.size(); i++) {
            // desconvertir a mi clase DetallePedido
            detallePedido = gson.fromJson( jsonArray.get(i) , DetallePedido.class);
            total +=detallePedido.getCantidad()*detallePedido.getCosto();
            listDetallePedidos.add( detallePedido );
        }

        carritoAdapter.listarCarrito(listDetallePedidos);


            System.out.println("AQUI EL ARRAY :"+listDetallePedidos);



            txtCantidadProducto.setText(listDetallePedidos.size()+" Productos");
            txtTotal.setText("S/. "+String.valueOf(total));


            btnCompra.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
    }


}