package com.example.transmed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class registre extends AppCompatActivity {

    EditText inputcorreu, input_contrassenya, input_repetir_contrassenya, input_usuari;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registre);
    }

    public void registre(View view) {
        // get user and password

        inputcorreu = (EditText) findViewById(R.id.correu);
        input_usuari = (EditText) findViewById(R.id.usuari);
        input_contrassenya = (EditText) findViewById(R.id.contrassenya);
        input_repetir_contrassenya = (EditText) findViewById(R.id.repetir_contrassenya);
        String _usuari = (input_usuari.getText()).toString();
        String _correu = (inputcorreu.getText()).toString();
        String _contrassenya = (input_contrassenya.getText()).toString();
        String _repetir_contrassenya = (input_repetir_contrassenya.getText()).toString();

        // Aqui fem la consulta amb la API a la base de dades per veure si existeix el usuari
        if(!_correu.contains("@")){
            showerror(inputcorreu, "El correu es incorrecte o està buit");
        }
        else{
            Intent intent = new Intent(this, Welcome_popup.class);
            startActivity(intent);
        }
    }

    public void showerror(EditText input, String s){
        input.setError(s);
        input.requestFocus();
    }
}