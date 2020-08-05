package com.example.apptiendavirtual_30.Detalles;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.apptiendavirtual_30.Adapters.CatalogoAdapter;
import com.example.apptiendavirtual_30.Adapters.DetallePedidoAdapter;
import com.example.apptiendavirtual_30.R;
import com.example.apptiendavirtual_30.fragments.PedidosFragment;
import com.example.apptiendavirtual_30.model.DetallePedido;
import com.example.apptiendavirtual_30.model.Pedido;
import com.example.apptiendavirtual_30.model.URI;
import com.example.apptiendavirtual_30.model.Usuario;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class DetallePedidoActivity extends AppCompatActivity implements View.OnClickListener{
    Pedido pedido = new Pedido();
    Usuario usuario = new Usuario();
    private Button btnConfirmar, btnRechazar, btnPreparar;
    private TextView tvNumeroPedido, tvFecha, tvUnidad, tvPagoTotal, tvTipoPago, tvNroOperacion;
    private RecyclerView rvDetallePedido;
    private DetallePedidoAdapter detallePedidoAdapter;
    private ArrayList<DetallePedido> listPedido;
    private RequestQueue requestQueue;
    private String urlBase="/order/", typeUser;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pedido);
        tvNumeroPedido = findViewById(R.id.tvNumeroPedido);
        tvFecha = findViewById(R.id.tvFecha);
        rvDetallePedido = findViewById(R.id.rvDetallePedido);
        tvUnidad = findViewById(R.id.tvUnidad);
        tvPagoTotal = findViewById(R.id.tvPagoTotal);
        tvTipoPago = findViewById(R.id.tvTipoPago);
        tvNroOperacion = findViewById(R.id.tvNroOperacion);
        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnRechazar = findViewById(R.id.btnRechazar);
        btnPreparar = findViewById(R.id.btnPreparar);
        btnConfirmar.setOnClickListener(this);
        btnRechazar.setOnClickListener(this);
        btnPreparar.setOnClickListener(this);

        if (getIntent().hasExtra("listaPedido"))
        {
            pedido = getIntent().getParcelableExtra("listaPedido");
        }
        if (getIntent().hasExtra("name"))
        {
            usuario = getIntent().getParcelableExtra("name");
        }
        tvNumeroPedido.setText(pedido.getNumberGenerated());
        tvFecha.setText(pedido.getDateEmision());
        tvUnidad.setText(String.valueOf(usuario.getName()));
        //System.out.println("NOMBRE: "+pedido.getUser().getName());
        tvPagoTotal.setText(String.valueOf(pedido.getTotal()));
        tvTipoPago.setText(pedido.getPaymentType());
        tvNroOperacion.setText(pedido.getCodeVoucher());
        id = pedido.getId();

        rvDetallePedido.setLayoutManager(new LinearLayoutManager(this));
        detallePedidoAdapter = new DetallePedidoAdapter(this);
        rvDetallePedido.setAdapter(detallePedidoAdapter);
        listPedido= new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);

        //ArrayList<DetallePedido> detallePedidos =
         WS(id);

        System.out.println("DATOSSSS "+listPedido);


        SharedPreferences preferences = getSharedPreferences("appBodega", Context.MODE_PRIVATE);
        typeUser = preferences.getString("typeUser","");



        if (!typeUser.equals("Cliente"))
        {
            btnPreparar.setVisibility(View.VISIBLE);
            btnRechazar.setVisibility(View.VISIBLE);
            btnConfirmar.setVisibility(View.VISIBLE);
        }

    }

    public void WS(final int id)
    {
        URI url = new URI();

        final JsonObjectRequest jsonObjectRequest=new
                JsonObjectRequest(Request.Method.GET, url.getUrl()+urlBase+id, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONObject jsonObject = response.getJSONObject("object");

                            JSONArray jsonArray = jsonObject.getJSONArray("details");

                            if (jsonArray.length()>0)
                            {
                                ArrayList<DetallePedido> lstPedido = new ArrayList<>();

                                for (int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                                    listPedido.add(new DetallePedido(
                                            jsonObject1.getInt("cant"),
                                            jsonObject1.getJSONObject("product").getString("name"),
                                            jsonObject1.getDouble("cost")
                                    ));
                                }
                                detallePedidoAdapter.listPedido(listPedido);
                                System.out.println("LISTA DE PEDIDOS"+listPedido);
                               // listPedido = lstPedido;
                            }

                        }catch (JSONException e)
                        {
                        }

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }

        });
        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnConfirmar:{
                String status = "Confirmado";
                Pedido pedidos = new Pedido(pedido.getId(),pedido.getPaymentType(),pedido.getDateEmision(),
                        pedido.getNameBanco(),pedido.getCodeVoucher(),status,pedido.getTotal(),pedido.getSubtotal(),
                        pedido.getIgv(),usuario.getId());
                actualizarPedido(pedidos);
              /*  startActivity(new Intent(this, PedidosFragment.class));*/
                finish();
            }break;
            case R.id.btnPreparar:{
                String status = "Preparando";
                Pedido pedidos = new Pedido(pedido.getId(),pedido.getPaymentType(),pedido.getDateEmision(),
                        pedido.getNameBanco(),pedido.getCodeVoucher(),status,pedido.getTotal(),pedido.getSubtotal(),
                        pedido.getIgv(),usuario.getId());
                actualizarPedido(pedidos);
             /*   startActivity(new Intent(this, PedidosFragment.class));
                finish();*/
            }break;
            case R.id.btnRechazar:{
                String status = "Rechazado";
                Pedido pedidos = new Pedido(pedido.getId(),pedido.getPaymentType(),pedido.getDateEmision(),
                        pedido.getNameBanco(),pedido.getCodeVoucher(),status,pedido.getTotal(),pedido.getSubtotal(),
                        pedido.getIgv(),usuario.getId());
                actualizarPedido(pedidos);
               /* startActivity(new Intent(this, PedidosFragment.class));
                finish();*/
            }break;
        }
    }

    public Pedido actualizarPedido(Pedido pedido)
    {
        try {
            URI uri = new URI();
            URL url = new URL(uri.getUrl()+urlBase);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setDoOutput(true);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Gson g = new Gson();
            String input = g.toJson(pedido,Pedido.class);

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if(conn.getResponseCode() !=200)
            {
                System.out.println("DATA DE CONN: "+conn.getResponseMessage());
                throw new RuntimeException("Error de Conexion"+conn.getResponseCode());

            }

            BufferedReader tecla2 = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            StringBuilder respuesta = new StringBuilder();

            String out;

            while((out=tecla2.readLine())!=null)
            {
                respuesta.append(out);
            }

            pedido =g.fromJson(respuesta.toString(), Pedido.class);
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pedido;
    }
}