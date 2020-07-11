package com.example.apptiendavirtual_20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.example.apptiendavirtual_20.BaseDeDatos.BaseDatos_TiendaVirtual;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnIngresar, btnRegistrar;
    private BaseDatos_TiendaVirtual BaseDatos;
    private SQLiteDatabase operacionesBD;
    private EditText etUser, etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        etUser = findViewById(R.id.etUser);
        etPassword = findViewById(R.id.etPassword);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnIngresar = findViewById(R.id.btnIngresar);
        btnIngresar.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);
        if (BaseDatos==null)
        {
            BaseDatos = new BaseDatos_TiendaVirtual(getApplicationContext());
            operacionesBD = BaseDatos.getWritableDatabase();
            BaseDatos.asignarSQLiteDatBase(operacionesBD);
        }
    }

    @Override
    protected void onDestroy() {
        BaseDatos.close();
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnIngresar:{
                String user = etUser.getText().toString();
                String password = etPassword.getText().toString();
                String nameUser;
                ArrayList<String> allNamesU= new ArrayList<String>();
                String contra;
                ArrayList<String> allPass = new ArrayList<String>();
                ArrayList<String> allId = new ArrayList<String>();
                String id;

                Cursor cursor = operacionesBD.rawQuery("select idUsuario, usuario, contra from Usuarios", null);
                if (cursor.moveToFirst()) {
                    while (!cursor.isAfterLast()) {
                        id =cursor.getString(0);
                        allId.add(id);
                        nameUser = cursor.getString(1);
                        allNamesU.add(nameUser);
                        contra = cursor.getString(2);
                        allPass.add(contra);
                        cursor.moveToNext();
                    }
                }
                if(allNamesU.contains(user) && password.equals(allPass.get(allNamesU.indexOf(user)))){
                    Toast.makeText(this, "Login correcto.", Toast.LENGTH_LONG).show();
                    Intent menu = new Intent(this, MainActivity.class);
                    startActivity(menu);
                }else
                {
                    Toast.makeText(this,"Usuario y/o Contraseña incorrecto",Toast.LENGTH_LONG).show();
                    etPassword.setText("");
                    etUser.setText("");
                }

            }break;
            case R.id.btnRegistrar:{
                Intent registrar = new Intent(this,RegistroActivity.class);
                startActivity(registrar);
            }break;
        }
    }
}