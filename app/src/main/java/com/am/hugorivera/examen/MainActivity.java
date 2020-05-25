package com.am.hugorivera.examen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener {

    EditText cajaTexto;
    Button boton;
    Double[] lat;
    Double[] lng;
    Database database;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = new Database(getApplicationContext());

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        cajaTexto = findViewById(R.id.editText);
        boton = findViewById(R.id.button);

        boton.setOnClickListener(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (database.Verificar()) {
            //existe base
            Toast.makeText(getApplicationContext(), "BD existente", Toast.LENGTH_SHORT).show();
        } else {
            database.insertarUsuario("Miguel Cervantes", "08-Dic-1990", "Desarrollador");
            database.insertarUsuario("Juan Morales", "03-Jul-1990", "Desarrollador");
            database.insertarUsuario("Roberto Méndez", "14-Oct-1990", "Desarrollador");
            database.insertarUsuario("Miguel Cuevas", "08-Dic-1990", "Desarrollador");
            Toast.makeText(getApplicationContext(), "BD creada", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                splitText(cajaTexto.getText().toString().trim());
                break;
        }
    }

    private void splitText(String texto) {
        boolean pintar = false;
        lat = null;
        lng = null;
//        String textValue = "20.523,-100.895_20.874,-100.658";
        String expresionRegular = "-?[0-9]*(\\.[0-9]+)?,\\s*-?[0-9]*(\\.[0-9]+)?";

        String[] splitted = texto.split("_");
        lat = new Double[splitted.length];
        lng = new Double[splitted.length];
        for (int i = 0; i < splitted.length; i++) {
            if (splitted[i].matches(expresionRegular)) {
                String[] coordSplit = splitted[i].split(",");
                lat[i] = Double.parseDouble(coordSplit[0]);
                lng[i] = Double.parseDouble(coordSplit[1]);
                Log.println(Log.ASSERT, "si", "lo acepta");
                pintar = true;
            } else {
                Log.println(Log.ASSERT, "no", "acepta");
                pintar = false;
                cajaTexto.setError("Cadena no válida");
                break;
            }
        }
        if (pintar) {
            mMap.clear();
            UpdateMarkers(lat, lng);
        }
    }

    private void UpdateMarkers(Double[] latitude, Double[] longitude) {
        for (int i = 0; i < latitude.length; i++) {
            MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude[i], longitude[i]));
            mMap.addMarker(marker);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.consumo:
                Intent intentConsumo = new Intent(getBaseContext(), Consumo.class);
                startActivity(intentConsumo);
                return true;
            case R.id.hilos:
                Intent intentHilos = new Intent(getBaseContext(), Hilos.class);
                startActivity(intentHilos);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
