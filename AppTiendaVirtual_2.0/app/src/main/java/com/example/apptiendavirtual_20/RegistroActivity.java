package com.example.apptiendavirtual_20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.apptiendavirtual_20.BaseDeDatos.BaseDatos_TiendaVirtual;

import java.util.ArrayList;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnRegistrarse;
    private BaseDatos_TiendaVirtual BaseDatos;
    private SQLiteDatabase operacionesBD;
    private EditText tvNombre, tvApellidos, tvCelular, tvCorreo, tvUsuario, tvContra;
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
                String nombre = tvNombre.getText().toString();
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
                }
            }break;
        }
    }
}