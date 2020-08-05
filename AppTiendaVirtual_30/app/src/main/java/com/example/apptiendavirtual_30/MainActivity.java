package com.example.apptiendavirtual_30;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private TextView txtMenuUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences preferences = getSharedPreferences("appBodega", Context.MODE_PRIVATE);

        View header = ((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0);
        ((TextView)header.findViewById(R.id.txtMenuUser)).setText(preferences.getString("name","usuario"));
        ((TextView)header.findViewById(R.id.txtMenuPhone)).setText(preferences.getString("phone","Celular"));
        
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton btnVerificaCarrito = (FloatingActionButton)findViewById(R.id.btnVerificaCarrito);
        btnVerificaCarrito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               SharedPreferences preferences = getSharedPreferences("appCarrito",MODE_PRIVATE);
               System.out.println("AQUI PE CAUSA "+preferences.getString("listDetallePedido",""));


                if (!preferences.getString("listDetallePedido","").equals(""))
                {
                    startActivity(new Intent(v.getContext(),CarritoActivity.class));
                }else
                {
                    Toast.makeText(v.getContext(),"Compra pe causa",Toast.LENGTH_LONG).show();
                }
                //startActivity(new Intent(v.getContext(),CarritoActivity.class));
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.stockFragment,R.id.catalogoFragment,R.id.pedidosFragment)
                .setDrawerLayout(drawer)
                .build();




        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);


        return true;
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_settings:{
                SharedPreferences.Editor editor1 = getSharedPreferences("appBodega",MODE_PRIVATE).edit();
                editor1.clear();
                editor1.apply();
                SharedPreferences.Editor editor2 = getSharedPreferences("appCarrito",MODE_PRIVATE).edit();
                editor2.clear();
                editor2.apply();
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}