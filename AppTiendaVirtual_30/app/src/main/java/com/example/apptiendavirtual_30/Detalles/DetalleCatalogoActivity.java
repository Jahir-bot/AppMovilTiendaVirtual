package com.example.apptiendavirtual_30.Detalles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptiendavirtual_30.MainActivity;
import com.example.apptiendavirtual_30.R;
import com.example.apptiendavirtual_30.model.DetallePedido;
import com.example.apptiendavirtual_30.model.Producto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DetalleCatalogoActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView txtProducto, txtPrecio, txtCantidad, txtDetaIncrement;
    private Button btnDetaMenos, btnDetaMas, btnAnadirCarrito, btnCancelar;
    private ImageView imgProducto;
    private int id, stock;
    private String category;
    Producto producto = new Producto();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_catalogo);
        txtProducto = findViewById(R.id.txtProducto);
        txtCantidad = findViewById(R.id.txtCantidad);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtDetaIncrement = findViewById(R.id.txtDetaIncrement);
        imgProducto = findViewById(R.id.imgProducto);
        btnDetaMas = findViewById(R.id.btnDetaMas);
        btnDetaMenos = findViewById(R.id.btnDetaMenos);
        btnAnadirCarrito = findViewById(R.id.btnAnadirCarrito);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnAnadirCarrito.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);
        btnDetaMenos.setOnClickListener(this);
        btnDetaMas.setOnClickListener(this);
        if (getIntent().hasExtra("enlaceProducto"))
        {
            producto = getIntent().getParcelableExtra("enlaceProducto");
            Picasso.get().load(producto.getEnlaceImagen()).into(imgProducto);
            txtProducto.setText(producto.getNombre());
            txtPrecio.setText("Precio:   S/ "+String.valueOf(producto.getCostoUnitario()));
            txtCantidad.setText("Cantidad: "+String.valueOf(producto.getStock())+" unds");
            txtDetaIncrement.setText("1");
            id = producto.getId();
            category = producto.getCategoria();
            stock = producto.getStock();
        }
    }

    @Override
    public void onClick(View v) {
        int cantidad = Integer.parseInt( txtDetaIncrement.getText().toString() );

        switch ( v.getId() ){

            case R.id.btnDetaMas : {
                cantidad += 1;
                if (cantidad <= producto.getStock())
                {
                    txtDetaIncrement.setText( String.valueOf( cantidad ) );
                }else
                {
                    Toast.makeText(this, "Stock insuficiente",Toast.LENGTH_SHORT).show();
                }
            };break;

            case R.id.btnDetaMenos : {
                cantidad-=1;

                if( cantidad >= 1 ){
                    txtDetaIncrement.setText( String.valueOf( cantidad ) );
                }

            };break;

            case R.id.btnCancelar:{
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }break;

            case R.id.btnAnadirCarrito:{
                SharedPreferences carrito = getSharedPreferences("appCarrito",MODE_PRIVATE);

                String jsonLisDetallePedido = carrito.getString("listDetallePedido",null);

                List<DetallePedido> listObjetos = new ArrayList<>();

                if (jsonLisDetallePedido !=null)
                {
                    try {
                       // String json = new Gson().toJson(jsonLisDetallePedido);
                      //  JSONArray jsonArray = new JSONArray(json);

                        //Convierte JSONArray a Lista de Objetos!
                        //Type listType = new TypeToken<ArrayList<DetallePedido>>(){}.getType();
                        listObjetos = new Gson().fromJson(jsonLisDetallePedido,
                                new TypeToken<ArrayList<DetallePedido>>(){}.getType());

                    }catch (Exception e)
                    {

                    }

                }

                    int cantidadPedido = Integer.parseInt(txtDetaIncrement.getText().toString());

                    String nombre = txtProducto.getText().toString();
                    String url = producto.getEnlaceImagen();
                    System.out.println("AQUI NAYHA AQUI: "+txtPrecio.getText().toString()
                            .substring(txtPrecio.getText().toString().indexOf("/")+1));
                    double precio = Double.parseDouble(txtPrecio.getText().toString()
                            .substring(txtPrecio.getText().toString().indexOf("/")+1));


                    Producto producto = new Producto(id,nombre,category,precio,stock,url);

                    DetallePedido detaPedido = new DetallePedido(producto,cantidadPedido,precio);

                    listObjetos.add(detaPedido);

                    Gson g = new Gson();

                    String jsonListObjetos = g.toJson(listObjetos);

                    SharedPreferences.Editor editor = carrito.edit();
                    editor.putString("listDetallePedido",jsonListObjetos);
                    editor.apply();
                    Toast.makeText(this,"Su producto: "+producto.getNombre()+" fue añadido correctamente",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, MainActivity.class));
                    finish();

            }break;

            default: break;

        }
    }
}