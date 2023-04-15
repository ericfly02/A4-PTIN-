package com.example.ptin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.ptin.medico.MedicoActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Mensaje recibido cuando cierro sesion desde la cuenta de perfil
        Intent intent_sesion = getIntent();
        int valor = intent_sesion.getIntExtra("mensaje",0);

        //Abrir actividad - Médico
        if(valor!=1){
            Intent intent = new Intent(this, MedicoActivity.class);
            startActivity(intent);
        }

    }
}