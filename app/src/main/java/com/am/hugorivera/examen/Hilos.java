package com.am.hugorivera.examen;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Hilos extends AppCompatActivity implements Task {

    Espera espera = new Espera();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hilos);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayout linearLayout = findViewById(R.id.linear);

        Button botonHilo = new Button(this);
        botonHilo.setText("Ejecutar");
        botonHilo.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.addView(botonHilo);

        botonHilo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ejecutarTask();
            }
        });

    }

    private void ejecutarTask(){
        if (espera.getStatus() != AsyncTask.Status.RUNNING){
            Toast.makeText(this,"Inicio", Toast.LENGTH_SHORT).show();
            espera.cancel(true);
            espera = new Espera();
            espera.listener = this;
            espera.execute();
        }
    }

    @Override
    public void taskComplete(boolean status) {
        if (status) {
            Log.println(Log.ASSERT, "termino", "hilo");
            Toast.makeText(this,"Término", Toast.LENGTH_SHORT).show();
        }
    }

    public class Espera extends AsyncTask<Void, Void, Void> {

        public Task listener = null;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            listener.taskComplete(true);

        }
    }

}
