package com.example.apptiendavirtual_30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apptiendavirtual_30.model.URI;
import com.example.apptiendavirtual_30.model.Usuario;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText txtNombre, txtApellido, txtCelular, txtContraRegistro, txtDireccion;
    private Button btnRegistro;
    public static final String url_base ="/usuario";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtCelular = findViewById(R.id.txtCelular);
        txtContraRegistro = findViewById(R.id.txtContraRegistro);
        txtDireccion = findViewById(R.id.txtDireccion);
        btnRegistro = findViewById(R.id.btnRegistro);
        btnRegistro.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnRegistro:{
                if (txtNombre.getText().toString().equals(""))
                {
                    txtNombre.setError("Ingrese su nombre");
                }else if (txtApellido.getText().toString().equals(""))
                {
                    txtApellido.setError("Ingrese su Apellido");
                }else if (txtCelular.getText().toString().equals("") || txtCelular.getText().toString().length()>9)
                {
                    txtCelular.setError("Ingrese un Celular de 9 dígitos");
                }else if (txtContraRegistro.getText().toString().equals("")
                        || txtContraRegistro.getText().toString().length()<5)
                {
                    txtContraRegistro.setError("Ingrese una contraseña");
                }
                else if (txtContraRegistro.getText().toString().length()>5)
                {
                    Pattern p = Pattern.compile("[^a-zA-Z0-9.]*[^0-9a-zA-Z.]");
                    Matcher m = p.matcher(txtContraRegistro.getText().toString());
                    if (!m.find())
                    {
                        txtContraRegistro.setError("Ingrese una contraseña que contenga 'Letras, Números y caracteres'");
                    }else {
                        if (txtDireccion.getText().toString().equals(""))
                        {
                            txtDireccion.setError("Ingrese su dirección");
                        }else
                        {
                            String nombres = txtNombre.getText().toString();
                            String apellidos = txtApellido.getText().toString();
                            String celular = txtCelular.getText().toString();
                            String contraseña = txtContraRegistro.getText().toString();
                            String direccion = txtDireccion.getText().toString();

                            Usuario registro = new Usuario(nombres,apellidos,celular,contraseña,direccion);
                            registro = save(registro);

                            Toast.makeText(this,registro.getMessage(),Toast.LENGTH_LONG).show();
                            startActivity(new Intent(this,LoginActivity.class));
                        }
                    }


                }
            }break;
        }
    }

    public static Usuario save(Usuario user)
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
}