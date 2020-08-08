package com.example.apptiendavirtual_30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apptiendavirtual_30.model.DetallePedido;
import com.example.apptiendavirtual_30.model.Mensajes;
import com.example.apptiendavirtual_30.model.URI;
import com.example.apptiendavirtual_30.model.Usuario;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnInicioSesion, btnRegistrarse;
    private TextView txtUsuario, txtContraseña;
    private String url_Base1 = "/user/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnInicioSesion = findViewById(R.id.btnInicioSesion);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        txtUsuario = findViewById(R.id.txtUsuario);
        txtContraseña = findViewById(R.id.txtContraseña);
        btnInicioSesion.setOnClickListener(this);
        btnRegistrarse.setOnClickListener(this);

        SharedPreferences preferences = getSharedPreferences("appBodega",MODE_PRIVATE);
        SharedPreferences preferences1 = getSharedPreferences("appCarrito",MODE_PRIVATE);
        String data = preferences1.getString("listDetallePedido","");
        String name = preferences.getString("name","");
        String phone = preferences.getString("phone","");
        List<DetallePedido> listObjetos = new ArrayList<>();

        if (data !=null)
        {
            try {
                // String json = new Gson().toJson(jsonLisDetallePedido);
                //  JSONArray jsonArray = new JSONArray(json);

                //Convierte JSONArray a Lista de Objetos!
                //Type listType = new TypeToken<ArrayList<DetallePedido>>(){}.getType();
                listObjetos = new Gson().fromJson(data,
                        new TypeToken<ArrayList<DetallePedido>>(){}.getType());

            }catch (Exception e)
            {

            }

        }
        if (preferences.contains("name") && preferences.contains("phone") && listObjetos==null)
        {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }else if (preferences.contains("name") && preferences.contains("phone") && listObjetos.size()>0 &&
                !name.equals("usuario") && !phone.equals("Celular")
                && preferences1.getString("carrito","").equals("true"))
        {
            startActivity(new Intent(this,FormaPagoActivity.class));
        }else if (preferences.contains("name") && preferences.contains("phone") && listObjetos.size()>0 &&
                !name.equals("usuario") && !phone.equals("Celular")
                && preferences1.getString("carrito","").equals("false"))
        {
            startActivity(new Intent(this,MainActivity.class));
        }
        else if (preferences.contains("name") && preferences.contains("phone") && listObjetos.size()==0 &&
                !name.equals("usuario") && !phone.equals("Celular")
                && preferences1.getString("carrito","").equals("false"))
        {
            startActivity(new Intent(this,MainActivity.class));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnInicioSesion: {
                if (txtUsuario.getText().toString().equals("")) {
                    txtUsuario.setError("Ingrese su usuario por favor");
                } else if (txtContraseña.getText().toString().equals("")) {
                    txtContraseña.setError("Ingrese su contraseña por favor");
                } else if (txtUsuario.getText().toString().equals("") & txtContraseña.getText().toString().equals(""))
                {
                    txtContraseña.setError("Ingrese su contraseña");
                    txtUsuario.setError("Ingrese su usario");
                }else {

                    String user = txtUsuario.getText().toString();
                    String pwd = txtContraseña.getText().toString();

                    Usuario usuario = new Usuario(user,pwd);
                    Mensajes mensajes = Login(usuario);

                    if (mensajes.isRequestSuccess()==true && mensajes.getObject()!=null)
                    {
                        Toast.makeText(this,"Login correcto",Toast.LENGTH_LONG).show();
                        SharedPreferences.Editor editor = getSharedPreferences("appBodega",MODE_PRIVATE).edit();
                        Gson g = new Gson();

                        Usuario usuario1 = mensajes.getObject();

                        System.out.println("IMPRIMIENDO: "+usuario1);

                        editor.putString("name",mensajes.getObject().getName());
                        editor.putString("id",String.valueOf(mensajes.getObject().getId()));
                        editor.putString("phone",mensajes.getObject().getPhone());
                        editor.putString("typeUser",mensajes.getObject().getTypeUser());
                        editor.apply();
                        Intent menu = new Intent(this, LoginActivity.class);
                        startActivity(menu);
                        finish();
                    }else
                    {
                        Toast.makeText(this,"Su usuario y/o contraseña no son los correctos, intente nuevamente",Toast.LENGTH_LONG).show();
                        txtUsuario.setText("");
                        txtContraseña.setText("");
                    }

                }
            }break;
            case R.id.btnRegistrarse:{
                Intent registro = new Intent(this, RegistroActivity.class);
                startActivity(registro);
            }break;
        }
    }
    public Mensajes Login(Usuario usuario)
    {
        Mensajes mensajes = new Mensajes();

        try {
            URI uri = new URI();
            URL url = new URL(uri.getUrl()+url_Base1);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setDoOutput(true);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Gson g = new Gson();
            Usuario usuario1 = new Usuario();
            usuario1.setPhone(usuario.getPhone());
            usuario1.setPassword(usuario.getPassword());
            String input = g.toJson(usuario1,Usuario.class);

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

            JsonElement jsonElement = JsonParser.parseString(respuesta.toString());

            mensajes =g.fromJson(jsonElement, Mensajes.class);
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mensajes;
    }
}