package com.example.apptiendavirtual_30.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apptiendavirtual_30.LoginActivity;
import com.example.apptiendavirtual_30.MainActivity;
import com.example.apptiendavirtual_30.R;
import com.example.apptiendavirtual_30.model.Mensajes;
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

public class RegistrarAdminFragment extends Fragment implements View.OnClickListener{
    private EditText txtNombreAdmin, txtApellidoAdmin, txtCelularAdmin, txtContraRegistroAdmin, txtDireccionAdmin;
    private Button btnRegistroAdmin;
    public static final String url_base ="/user";
    public RegistrarAdminFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_registrar_admin, container, false);
        txtNombreAdmin = view.findViewById(R.id.txtNombreAdmin);
        txtApellidoAdmin = view.findViewById(R.id.txtApellidoAdmin);
        txtCelularAdmin = view.findViewById(R.id.txtCelularAdmin);
        txtContraRegistroAdmin = view.findViewById(R.id.txtContraRegistroAdmin);
        txtDireccionAdmin = view.findViewById(R.id.txtDireccionAdmin);
        btnRegistroAdmin = view.findViewById(R.id.btnRegistroAdmin);
        btnRegistroAdmin.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnRegistroAdmin:{
                if (txtNombreAdmin.getText().toString().equals(""))
                {
                    txtNombreAdmin.setError("Ingrese su nombre");
                }else if (txtApellidoAdmin.getText().toString().equals(""))
                {
                    txtApellidoAdmin.setError("Ingrese su Apellido");
                }else if (txtCelularAdmin.getText().toString().equals("") || txtCelularAdmin.getText().toString().length()>9
                        || txtCelularAdmin.getText().toString().length()<9)
                {
                    txtCelularAdmin.setError("Ingrese un Celular de 9 dígitos");
                }else if (txtContraRegistroAdmin.getText().toString().equals("")
                        || txtContraRegistroAdmin.getText().toString().length()<5)
                {
                    txtContraRegistroAdmin.setError("Ingrese una contraseña");
                }
                else if (txtContraRegistroAdmin.getText().toString().length()>5)
                {
                    Pattern p = Pattern.compile("[^a-zA-Z0-9.]*[^0-9a-zA-Z.]");
                    Matcher m = p.matcher(txtContraRegistroAdmin.getText().toString());
                    if (!m.find())
                    {
                        txtContraRegistroAdmin.setError("Ingrese una contraseña que contenga 'Letras, Números y caracteres'");
                    }else {
                        if (txtDireccionAdmin.getText().toString().equals(""))
                        {
                            txtDireccionAdmin.setError("Ingrese su dirección");
                        }else
                        {
                            String nombres = txtNombreAdmin.getText().toString();
                            String apellidos = txtApellidoAdmin.getText().toString();
                            String celular = txtCelularAdmin.getText().toString();
                            String contraseña = txtContraRegistroAdmin.getText().toString();
                            String direccion = txtDireccionAdmin.getText().toString();

                            Usuario registro = new Usuario(nombres,apellidos,celular,contraseña,direccion,"Admin");
                            Mensajes mensajes = save(registro);

                            if(mensajes.isRequestSuccess()==true)
                            {
                                Toast.makeText(getContext(),mensajes.getMessage(),Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getContext(), MainActivity.class));
                            }else
                            {
                                Toast.makeText(getContext(),mensajes.getMessage(),Toast.LENGTH_LONG).show();
                                txtCelularAdmin.setText("");
                            }

                        }
                    }
                }
            }break;
        }
    }
    public static Mensajes save(Usuario user)
    {
        Mensajes mensajes = new Mensajes();

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

            mensajes =g.fromJson(respuesta.toString(), Mensajes.class);
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mensajes;
    }
}