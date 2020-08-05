package com.example.apptiendavirtual_30;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EligeAutentificacion extends AppCompatActivity implements View.OnClickListener{
    private Button btnConAuten, btnSinAuten;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elige_autentificacion);
        btnConAuten = findViewById(R.id.btnConAuten);
        btnSinAuten = findViewById(R.id.btnSinAuten);
        btnConAuten.setOnClickListener(this);
        btnSinAuten.setOnClickListener(this);
        SharedPreferences preferences = getSharedPreferences("appBodega",MODE_PRIVATE);
        if (preferences.contains("name") && preferences.contains("phone"))
        {
            startActivity(new Intent(EligeAutentificacion.this, LoginActivity.class));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnConAuten:{
                startActivity(new Intent(this,LoginActivity.class));
            }break;
            case R.id.btnSinAuten:{
                startActivity(new Intent(this,MainActivity.class));
            }break;
        }
    }
}