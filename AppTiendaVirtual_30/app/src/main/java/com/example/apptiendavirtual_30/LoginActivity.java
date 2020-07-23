package com.example.apptiendavirtual_30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnInicioSesion, btnRegistrarse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnInicioSesion = findViewById(R.id.btnInicioSesion);
        btnRegistrarse = findViewById(R.id.btnRegistrarse);
        btnInicioSesion.setOnClickListener(this);
        btnRegistrarse.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnInicioSesion:{
                Intent menu = new Intent(this, MainActivity.class);
                startActivity(menu);
            }break;
            case R.id.btnRegistrarse:{
                Intent registro = new Intent(this, RegistroActivity.class);
                startActivity(registro);
            }break;
        }
    }
}