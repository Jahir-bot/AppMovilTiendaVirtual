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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.apptiendavirtual_30.Adapters.CatalogoAdapter;
import com.example.apptiendavirtual_30.Adapters.StockAdapter;
import com.example.apptiendavirtual_30.R;
import com.example.apptiendavirtual_30.model.Producto;
import com.example.apptiendavirtual_30.model.URI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CatalogoFragment extends Fragment {
    private RecyclerView rvCatalogo;
    private CatalogoAdapter catalogoAdapter;
    private RequestQueue requestQueue;
    private ArrayList<Producto> listaProductos;
    private String urlBase = "/product/stock_low?numberMin=10000";
    private TextView textView6;
    private Spinner spinner;
    private String categoria;
    public CatalogoFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_catalogo, container, false);

        textView6 = view.findViewById(R.id.textView6);
        rvCatalogo = view.findViewById(R.id.rvCatalogo);
        spinner = view.findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getContext(),
                R.array.combo_categoriaProducto,R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter2);

        rvCatalogo.setLayoutManager(new GridLayoutManager(getContext(),2));
        catalogoAdapter = new CatalogoAdapter(getContext());
        rvCatalogo.setAdapter(catalogoAdapter);
        listaProductos= new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).toString().equals("Todos"))
                {
                    WS1();
                }else
                {
                  if (parent.getItemAtPosition(position).toString().equals("Desayuno"))
                  {
                      categoria = parent.getItemAtPosition(position).toString();
                      WS2(categoria);
                  }else if (parent.getItemAtPosition(position).toString().equals("Lacteos"))
                  {
                      categoria = parent.getItemAtPosition(position).toString();
                      WS2(categoria);
                  }else if (parent.getItemAtPosition(position).toString().equals("Abarrotes"))
                  {
                      categoria = parent.getItemAtPosition(position).toString();
                      WS2(categoria);
                  }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /*SharedPreferences preferences = this.getActivity().getSharedPreferences("appBodega", Context.MODE_PRIVATE);
        textView6.setText(preferences.getString("name","usuario"));*/



        return view;
    }
    public void WS2(final String categoria)
    {
        URI url = new URI();

        final JsonObjectRequest jsonObjectRequest=new
                JsonObjectRequest(Request.Method.GET, url.getUrl()+urlBase, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("list");
                            if (jsonArray.length()>0)
                            {
                                ArrayList<Producto> lstProducto = new ArrayList<>();
                                for (int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    lstProducto.add(new Producto(
                                            jsonObject.getInt("id"),
                                            jsonObject.getString("name"),
                                            jsonObject.getString("category"),
                                            jsonObject.getDouble("unitCost"),
                                            jsonObject.getInt("stock"),
                                            jsonObject.getString("imageLink")
                                    ));
                                    for (int e=0;e<lstProducto.size();e++)
                                    {
                                        Producto producto = lstProducto.get(e);
                                        if (!producto.getCategoria().equals(categoria))
                                        {
                                            lstProducto.remove(e);
                                        }
                                    }
                                }
                                catalogoAdapter.addCatalogo(lstProducto);
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
    public void WS1()
    {
        URI url = new URI();

        JsonObjectRequest jsonObjectRequest=new
                JsonObjectRequest(Request.Method.GET, url.getUrl()+urlBase, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("list");
                            if (jsonArray.length()>0)
                            {
                                ArrayList<Producto> lstProducto = new ArrayList<>();
                                for (int i=0;i<jsonArray.length();i++)
                                {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    lstProducto.add(new Producto(
                                            jsonObject.getInt("id"),
                                            jsonObject.getString("name"),
                                            jsonObject.getString("category"),
                                            jsonObject.getDouble("unitCost"),
                                            jsonObject.getInt("stock"),
                                            jsonObject.getString("imageLink")
                                    ));
                                }
                                catalogoAdapter.addCatalogo(lstProducto);
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