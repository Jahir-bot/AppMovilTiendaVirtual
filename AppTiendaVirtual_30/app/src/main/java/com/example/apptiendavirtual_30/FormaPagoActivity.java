package com.example.apptiendavirtual_30;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptiendavirtual_30.fragments.PagoBlancoFragment;
import com.example.apptiendavirtual_30.fragments.PagoCreditoFragment;
import com.example.apptiendavirtual_30.fragments.PagoEfectivoFragment;
import com.example.apptiendavirtual_30.fragments.PagoTransferenciaFragment;
import com.example.apptiendavirtual_30.model.DetallePedido;
import com.example.apptiendavirtual_30.model.Mensajes;
import com.example.apptiendavirtual_30.model.Pedido;
import com.example.apptiendavirtual_30.model.URI;
import com.example.apptiendavirtual_30.model.Usuario;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class FormaPagoActivity extends AppCompatActivity implements View.OnClickListener{
    FragmentTransaction transaction;
    Fragment fragmentTransferencia, fragmentEfectivo, fragmentBlanco, fragmentCredito;
    private Spinner cboMetodoPago, spinner2;
    private TextInputLayout textInputLayout;
    private EditText etTransferencia;
    private TextView textView19, tvMensajePago, textView21;
    private String metodoPago, banco, numeroOperacion;
    private int id;
    private Button btnRealizarPagoFin;
    private String fecha;
    private DetallePedido detallePedido=new DetallePedido();
    private Usuario usuario = new Usuario();
    private double total = 0.0;
    private ArrayList<DetallePedido> listDetallePedidos = new ArrayList<>();
    public static final String url_base = "/order/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forma_pago);

        fragmentBlanco = new PagoBlancoFragment();
        fragmentTransferencia = new PagoTransferenciaFragment();
        fragmentEfectivo = new PagoEfectivoFragment();
        fragmentCredito = new PagoCreditoFragment();
        spinner2 = findViewById(R.id.spinner2);
        textView19 = findViewById(R.id.textView19);
        textInputLayout = findViewById(R.id.textInputLayout);
        cboMetodoPago = findViewById(R.id.cboMetodoPago);
        tvMensajePago = findViewById(R.id.tvMensajePago);
        textView21 = findViewById(R.id.textView21);
        btnRealizarPagoFin = findViewById(R.id.btnRealizarPagoFin);
        etTransferencia = findViewById(R.id.etTransferencia);
        btnRealizarPagoFin.setOnClickListener(this);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.combo_metodoPago,R.layout.support_simple_spinner_dropdown_item);
        cboMetodoPago.setAdapter(adapter1);
        cboMetodoPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                metodoPago = parent.getItemAtPosition(position).toString();
                System.out.println("Selección de Spinner"+metodoPago);
                if (parent.getItemAtPosition(position).toString().equals("Transferencia"))
                {
                    textView19.setVisibility(view.VISIBLE);
                    spinner2.setVisibility(view.VISIBLE);
                    textInputLayout.setVisibility(view.VISIBLE);
                    tvMensajePago.setVisibility(view.GONE);
                    textView21.setVisibility(view.GONE);
                }else if (parent.getItemAtPosition(position).toString().equals("Efectivo"))
                {
                    textView19.setVisibility(view.GONE);
                    spinner2.setVisibility(view.GONE);
                    textInputLayout.setVisibility(view.GONE);
                    tvMensajePago.setVisibility(view.VISIBLE);
                    textView21.setVisibility(view.GONE);
                }else if (parent.getItemAtPosition(position).toString().equals("Fiadito"))
                {
                    textView19.setVisibility(view.GONE);
                    spinner2.setVisibility(view.GONE);
                    textInputLayout.setVisibility(view.GONE);
                    tvMensajePago.setVisibility(view.VISIBLE);
                    textView21.setVisibility(view.GONE);
                }else if (parent.getItemAtPosition(position).toString().equals("Seleccione:"))
                {
                    textView19.setVisibility(view.GONE);
                    spinner2.setVisibility(view.GONE);
                    textInputLayout.setVisibility(view.GONE);
                    tvMensajePago.setVisibility(view.GONE);
                    textView21.setVisibility(view.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.combo_bancos,R.layout.support_simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                banco = parent.getItemAtPosition(position).toString();
                if (parent.getItemAtPosition(position).toString().equals("BANCO DE CRÉDITO"))
                {
                    textView21.setVisibility(view.VISIBLE);
                    textView21.setText("N° de cuenta:\n"
                                            +"      191-7894552-00-01\n"
                                            +" N° cci:\n"
                                            +"      191-00007894552-00-01");
                }else if (parent.getItemAtPosition(position).toString().equals("BANCO CONTINENTAL BBVA"))
                {
                    textView21.setVisibility(view.VISIBLE);
                    textView21.setText("N° de cuenta:\n"
                            +"      201-7894552-00-01\n"
                            +" N° cci:\n"
                            +"      201-00007894552-00-01");
                }else if (parent.getItemAtPosition(position).toString().equals("INTERBANK"))
                {
                    textView21.setVisibility(view.VISIBLE);
                    textView21.setText("N° de cuenta:\n"
                            +"      301-7894552-00-01\n"
                            +" N° cci:\n"
                            +"      301-00007894552-00-01");
                }
                else if (parent.getItemAtPosition(position).toString().equals("Seleccione:"))
                {
                    textView21.setVisibility(view.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

      /*  getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragments,fragmentBlanco).commit();

        cboMetodoPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                transaction = getSupportFragmentManager().beginTransaction();
                String Transferencia = parent.getItemAtPosition(position).toString();
                System.out.println("Selección de Spinner"+Transferencia);
                if (parent.getItemAtPosition(position).toString().equals("Transferencia"))
                {
                    transaction.replace(R.id.contenedorFragments,fragmentTransferencia).commit();
                }else if (parent.getItemAtPosition(position).toString().equals("Efectivo"))
                {
                    transaction.replace(R.id.contenedorFragments,fragmentEfectivo).commit();
                    Bundle bundle = new Bundle();
                    bundle.putString("spinner",Transferencia);
                }else if (parent.getItemAtPosition(position).toString().equals("Fiadito"))
                {
                    transaction.replace(R.id.contenedorFragments,fragmentCredito).commit();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/

      System.out.println(metodoPago);
      System.out.println(banco);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnRealizarPagoFin:{

                System.out.println("Metodo de Pago: "+metodoPago);
                System.out.println("Banco: "+banco);

                if (!metodoPago.equals("Seleccione:") && metodoPago.equals("Transferencia"))
                {
                    SharedPreferences preferences1 = getSharedPreferences("appBodega",MODE_PRIVATE);
                    System.out.println("ID Usuario: "+preferences1.getString("id",""));
                    SharedPreferences preferences2 = getSharedPreferences("appCarrito",MODE_PRIVATE);
                    System.out.println("Pedido Productos: "+preferences2.getString("listDetallePedido",""));

                    numeroOperacion = etTransferencia.getText().toString();

                    JsonElement element = JsonParser.parseString(preferences2.getString("listDetallePedido",""));
                    Gson g = new Gson();

                    JsonArray jsonArray = element.getAsJsonArray();

                    System.out.println("AQUI JSONARRAY: "+jsonArray);

                    for (int i = 0; i < jsonArray.size(); i++) {
                        // desconvertir a mi clase DetallePedido
                        detallePedido = g.fromJson( jsonArray.get(i) , DetallePedido.class);
                        total +=detallePedido.getCant()*detallePedido.getCost();
                        listDetallePedidos.add( detallePedido );
                    }

                    double igv = total*0.18;
                    double subTotal = total-igv;

                    id = Integer.parseInt(preferences1.getString("id",""));

                    usuario = new Usuario(id);

                    Calendar c = Calendar.getInstance();

                    Date fecha2=c.getTime();

                    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
                    fecha= dateFormat.format( fecha2  );

                    Pedido pedido = new Pedido(metodoPago,fecha,banco,numeroOperacion,
                            "Pendiente",total,subTotal,igv,usuario,listDetallePedidos);

                    System.out.println("AQUI LOS DATOS---------: "+pedido);

                    realizaPedido(pedido);

                    SharedPreferences.Editor editor2 = getSharedPreferences("appCarrito",MODE_PRIVATE).edit();
                    editor2.clear();
                    editor2.apply();
                    finish();

                    Toast.makeText(this,"Su pedido fue enviado de forma exitosa",Toast.LENGTH_LONG).show();

                    startActivity(new Intent(this,MainActivity.class));

                }else
                {
                    banco="";
                }
            }break;
        }
    }

    public static Pedido realizaPedido(Pedido pedido)
    {

        try {
            URI uri = new URI();
            URL url = new URL(uri.getUrl()+url_base);
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