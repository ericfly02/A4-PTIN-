package com.example.transmed;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.transmed.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class login extends AppCompatActivity {

    EditText inputcorreu, input_contrassenya;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {

        // get user and password
        inputcorreu = (EditText) findViewById(R.id.correu);
        input_contrassenya = (EditText) findViewById(R.id.contrassenya);
        String _correu = (inputcorreu.getText()).toString();
        String _contrassenya = (input_contrassenya.getText()).toString();

        // Aqui fem la consulta amb la API a la base de dades per veure si existeix el usuari

        if(_correu != "admin@1234" && _contrassenya != "1234"){
            Intent intent = new Intent(this, Welcome_popup.class);
            startActivity(intent);
        }
        //!_correu.contains("@")
        else if(_correu.isEmpty() || _correu != "admin@1234"){
            showerror(inputcorreu, "El correu es incorrecte o està buit");
        }

        else if(_contrassenya.isEmpty() || _contrassenya != "1234"){
            showerror(input_contrassenya, "La contrassenya està buida o es incorrecta");
        }
        else {
            Toast.makeText(this, "Call Registration Method", Toast.LENGTH_SHORT).show();
        }
    }

    public void google (){
        googleBtn = findViewById(R.id.boton_google);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this, gso);

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    void signIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

    }
    void navigateToSecondActivity(){
        finish();
        Intent intent = new Intent(login.this, Welcome_popup.class);
        startActivity(intent);
    }

    public void showerror(EditText input, String s){
        input.setError(s);
        input.requestFocus();
    }
}