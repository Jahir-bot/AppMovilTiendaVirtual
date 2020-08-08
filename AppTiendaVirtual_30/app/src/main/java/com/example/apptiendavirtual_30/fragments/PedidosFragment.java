package com.example.apptiendavirtual_30.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.apptiendavirtual_30.Adapters.CatalogoAdapter;
import com.example.apptiendavirtual_30.Adapters.PedidoAdapter;
import com.example.apptiendavirtual_30.R;
import com.example.apptiendavirtual_30.model.Pedido;
import com.example.apptiendavirtual_30.model.Producto;
import com.example.apptiendavirtual_30.model.URI;
import com.example.apptiendavirtual_30.model.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PedidosFragment extends Fragment {
    private RecyclerView rvPedidos;
    private PedidoAdapter pedidoAdapter;
    private ArrayList<Pedido> listPedido = new ArrayList<>();
    private RequestQueue requestQueue;
    private TextView textView4;
    private Spinner spinner4;
    private String urlBase ="/order/find?state=";
    private String estado ="", typeUser="";
    private int user2;
    public PedidosFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pedidos, container, false);
        rvPedidos = view.findViewById(R.id.rvPedidos);

        SharedPreferences preferences = getContext().getSharedPreferences("appBodega", Context.MODE_PRIVATE);
        //user1 = preferences.getString("name","");
        user2= Integer.parseInt(preferences.getString("id",""));
        typeUser = preferences.getString("typeUser","");
        rvPedidos.setLayoutManager(new LinearLayoutManager(getContext()));
        pedidoAdapter = new PedidoAdapter(getContext());
        rvPedidos.setAdapter(pedidoAdapter);
        listPedido= new ArrayList<>();
        textView4 = view.findViewById(R.id.textView4);
        spinner4 = view.findViewById(R.id.spinner4);
        requestQueue = Volley.newRequestQueue(getContext());


        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.combo_estatoPedido,R.layout.support_simple_spinner_dropdown_item);
        spinner4.setAdapter(adapter2);

        spinner4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Pendiente"))
                {
                    estado = parent.getItemAtPosition(position).toString();
                    if (!typeUser.equals("Cliente"))
                    {
                        textView4.setText("Cliente");
                        WS2(estado);
                    }else
                    {
                        WS1(user2,estado);
                    }
                }else if (parent.getItemAtPosition(position).toString().equals("Confirmado"))
                {
                    estado = parent.getItemAtPosition(position).toString();
                    if (!typeUser.equals("Cliente"))
                    {
                        textView4.setText("Cliente");
                        WS2(estado);
                    }else
                    {
                        WS1(user2,estado);
                    }
                }
                else if (parent.getItemAtPosition(position).toString().equals("Preparando"))
                {
                    estado = parent.getItemAtPosition(position).toString();
                    if (!typeUser.equals("Cliente"))
                    {
                        textView4.setText("Cliente");
                        WS2(estado);
                    }else
                    {
                        WS1(user2,estado);
                    }
                }else if (parent.getItemAtPosition(position).toString().equals("Rechazado"))
                {
                    estado = parent.getItemAtPosition(position).toString();
                    if (!typeUser.equals("Cliente"))
                    {
                        textView4.setText("Cliente");
                        WS2(estado);
                    }else
                    {
                        WS1(user2,estado);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    public void WS1(final int data, final String estados)
    {
        URI url = new URI();

        final JsonObjectRequest jsonObjectRequest=new
                JsonObjectRequest(Request.Method.GET, url.getUrl()+urlBase+estados, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("list");
                            ArrayList<Pedido> lstPedido = new ArrayList<>();
                            if (jsonArray.length()>0)
                            {

                                for (int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    lstPedido.add(new Pedido(
                                            jsonObject.getInt("id"),
                                            jsonObject.getString("paymentType"),
                                            jsonObject.getString("dateEmision"),
                                            jsonObject.getString("nameBanco"),
                                            jsonObject.getString("codeVoucher"),
                                            jsonObject.getString("state"),
                                            jsonObject.getString("numberGenerated"),
                                            jsonObject.getDouble("total"),
                                            jsonObject.getDouble("subtotal"),
                                            jsonObject.getDouble("igv"),
                                            jsonObject.getJSONObject("user").getInt("id"),
                                            jsonObject.getJSONObject("user").getString("name")

                                    ));

                                            for (int e=0;e<lstPedido.size();e++)
                                            {
                                                if (lstPedido.get(e).getUser().getId()!=data)
                                                {
                                                    lstPedido.remove(e);
                                                    System.out.println("New Data: "+lstPedido);
                                                }
                                            }


                                }
                                pedidoAdapter.listaPedido(lstPedido,true);
                            } else
                            {
                                pedidoAdapter.listaPedido(lstPedido,true);
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
    public void WS2(final String estados)
    {
        URI url = new URI();

        final JsonObjectRequest jsonObjectRequest=new
                JsonObjectRequest(Request.Method.GET, url.getUrl()+urlBase+estados, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("list");
                            ArrayList<Pedido> lstPedido = new ArrayList<>();
                            if (jsonArray.length()>0)
                            {

                                for (int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    lstPedido.add(new Pedido(
                                            jsonObject.getInt("id"),
                                            jsonObject.getString("paymentType"),
                                            jsonObject.getString("dateEmision"),
                                            jsonObject.getString("nameBanco"),
                                            jsonObject.getString("codeVoucher"),
                                            jsonObject.getString("state"),
                                            jsonObject.getString("numberGenerated"),
                                            jsonObject.getDouble("total"),
                                            jsonObject.getDouble("subtotal"),
                                            jsonObject.getDouble("igv"),
                                            jsonObject.getJSONObject("user").getInt("id"),
                                            jsonObject.getJSONObject("user").getString("name")
                                    ));
                                }
                                pedidoAdapter.listaPedido(lstPedido,false);

                                System.out.println("2da lista: "+lstPedido);
                            }
                            else
                            {
                                pedidoAdapter.listaPedido(lstPedido,false);
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
}