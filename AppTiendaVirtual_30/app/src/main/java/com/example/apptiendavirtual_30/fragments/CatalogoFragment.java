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

        /*SharedPreferences preferences = this.getActivity().getSharedPreferences("appBodega", Context.MODE_PRIVATE);
        textView6.setText(preferences.getString("name","usuario"));*/

        rvCatalogo.setLayoutManager(new GridLayoutManager(getContext(),2));
        catalogoAdapter = new CatalogoAdapter(getContext());
        rvCatalogo.setAdapter(catalogoAdapter);
        listaProductos= new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        WS();

        return view;
    }
    public void WS()
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
                                            jsonObject.getString("name"),
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