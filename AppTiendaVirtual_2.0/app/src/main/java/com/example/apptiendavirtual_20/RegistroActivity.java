package com.example.apptiendavirtual_20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apptiendavirtual_20.BaseDeDatos.BaseDatos_TiendaVirtual;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnRegistrarse;
    private BaseDatos_TiendaVirtual BaseDatos;
    private SQLiteDatabase operacionesBD;
    private EditText tvNombre, tvApellidos, tvCelular, tvCorreo, tvUsuario, tvContra;
    public static final String url_base ="http://192.168.1.24:8190/WS_TiendaVirtual/prueba/go/";
    public static final String url_base2 ="http://192.168.1.23:8093/usuario";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        tvNombre = findViewById(R.id.tvNombre);
        tvApellidos = findViewById(R.id.tvApellidos);
        tvCelular = findViewById(R.id.tvCelular);
        tvCorreo = findViewById(R.id.tvCorreo);
        tvUsuario = findViewById(R.id.tvUsuario);
        tvContra = findViewById(R.id.tvContra);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        btnRegistrarse.setOnClickListener(this);

        if (BaseDatos==null)
        {
            BaseDatos = new BaseDatos_TiendaVirtual(getApplicationContext());
            operacionesBD = BaseDatos.getWritableDatabase();
            BaseDatos.asignarSQLiteDatBase(operacionesBD);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnRegistrarse:{

                System.out.println("------------------");
                System.out.println("");
                System.out.println("Creación de empleado");
               /* Usuarios registro=new Usuarios("a1715389","75440415","Jahir","San Roman Moriano","932132988","jhairsanroman@yahoo.com");
                registro=crear(registro);*/
                Usuario regis = new Usuario("Nayha","Roman Guerreros","991697521"
                        ,"Nroman","Cercado de Lima");
                regis = save(regis);



               /* System.out.println("ID: "+registro.getIdUsuario());
                System.out.println("Usuario: "+registro.getUsuario());
                System.out.println("Contraseña: "+registro.getContra());
                System.out.println("Nombres: "+registro.getNombres());
                System.out.println("Apellidos: "+registro.getApellidos());
                System.out.println("Celular: "+registro.getCelular());
                System.out.println("Correo: "+registro.getCorreo());*/

                System.out.println("ID: "+regis.getId());
                System.out.println("Nombres: "+regis.getNombres());
                System.out.println("Apellidos: "+regis.getApellidos());
                System.out.println("Celular: "+regis.getCelular());
                System.out.println("Clave: "+regis.getClave());
                System.out.println("Dirección: "+regis.getDireccion());





               /* String nombre = tvNombre.getText().toString();
                String apellidos = tvApellidos.getText().toString();
                String celular = tvCelular.getText().toString();
                String correo = tvCorreo.getText().toString();
                String usuario = tvUsuario.getText().toString();
                String contra = tvContra.getText().toString();
                ArrayList<String> allNamesU= new ArrayList<>();
                ArrayList<String> allPass = new ArrayList<>();
                ArrayList<Integer> allId = new ArrayList<>();
                String name,pass;
                int id,id1=1,idPost;
                Cursor cursor = operacionesBD.rawQuery("select idUsuario, nombre, contra from Usuarios",null);
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        id =cursor.getInt(0);
                        allId.add(id);
                        name = cursor.getString(1);
                        allNamesU.add(name);
                        pass = cursor.getString(2);
                        allPass.add(pass);
                        cursor.moveToNext();
                    }
                }
                if(usuario.length()!=0 && contra.length()!=0 && nombre.length()!=0 && apellidos.length()!=0 &&
                        correo.length()!=0 || usuario.length()!=0 && contra.length()!=0 && nombre.length()!=0 &&
                        apellidos.length()!=0 && correo.length()!=0) {
                    if (allNamesU.contains(usuario)) {
                        Toast.makeText(this, "El usuario introducido ya existe." +
                                " Inténtelo de nuevo con otro nombre", Toast.LENGTH_LONG).show();
                        tvUsuario.setText("");
                        tvContra.setText("");
                    } else {

                        idPost = allId.size() + id1;
                        ContentValues nuevoRegistro = new ContentValues();
                        nuevoRegistro.put("idUsuario", idPost);
                        nuevoRegistro.put("usuario", usuario);
                        nuevoRegistro.put("contra", contra);
                        nuevoRegistro.put("nombre", nombre);
                        nuevoRegistro.put("apellido", apellidos);
                        nuevoRegistro.put("correo", correo);
                        nuevoRegistro.put("celular", celular);
                        operacionesBD.insert("Usuarios", null, nuevoRegistro);

                        Intent intent = new Intent(this, LoginActivity.class);
                        Toast.makeText(this, "¡El usuario -"+ usuario+"- ha sido creado con éxito!"
                                ,Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }
                }*/
            }break;
        }
    }
    public static Usuario save (Usuario user)
    {
        try {

            URL url = new URL(url_base2);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setDoOutput(true);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Gson g = new Gson();
            String input = g.toJson(user,Usuario.class);

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

            user=g.fromJson(respuesta.toString(),Usuario.class);
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public static Usuarios crear (Usuarios usuario)
    {
        try {

            URL url = new URL(url_base+"crear");
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setDoOutput(true);
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Gson g = new Gson();
            String input = g.toJson(usuario,Usuarios.class);

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

            usuario=g.fromJson(respuesta.toString(),Usuarios.class);
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }

}