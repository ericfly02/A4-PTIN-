package com.example.transmed;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.transmed.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity {
    private RequestQueue requestQueue;

    String apiUrl = "http://10.0.2.2:3000";
    String getEndpoint = apiUrl + "/medicaments";
    EditText inputcorreu, input_contrassenya;

    // Declarar variables globales
    GoogleSignInClient googleSignInClient;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        requestQueue = Volley.newRequestQueue(this);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Inicializar el cliente de inicio de sesión de Google
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
    }

    public void login(View view) {

        // get user and password
        inputcorreu = (EditText) findViewById(R.id.correu);
        input_contrassenya = (EditText) findViewById(R.id.contrassenya);
        String _correu = (inputcorreu.getText()).toString();
        String _contrassenya = (input_contrassenya.getText()).toString();

        // Aqui fem la consulta amb la API a la base de dades per veure si existeix el usuari
        //Dani: La direccio i port de la API es bona practica definirla a /res/values/strings.xml
        if(_correu != "admin@1234" && _contrassenya != "1234"){
            //Dani: Verificar que l'usuari existeix i rebre token per mantindre la connexió
            checkUser(_correu);
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

    public void showerror(EditText input, String s){
        input.setError(s);
        input.requestFocus();
    }

    public void loginWithGoogle(View view) {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private static final int RC_SIGN_IN = 9001;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Resultado del inicio de sesión de Google
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Inicio de sesión exitoso
                GoogleSignInAccount account = task.getResult(ApiException.class);

                // Aquí se puede obtener el nombre, correo electrónico y otros datos del usuario de la cuenta de Google
                // y utilizarlos para el inicio de sesión en la aplicación
                SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("email", account.getEmail());
                editor.putString("name", account.getDisplayName());
                editor.apply();

                // Guardamos en variables el correo y el password para acceder a la base de datos
                String email = preferences.getString("email", "");



                // Cambiamos de actividad
                // Aquí se puede obtener el nombre, correo electrónico y otros datos del usuario de la cuenta de Google
                // y utilizarlos para el inicio de sesión en la aplicación
                Intent intent = new Intent(this, Welcome_popup.class); // Reemplaza NuevaActividad con el nombre de la actividad a la que quieres ir
                startActivity(intent);

            } catch (ApiException e) {
                // Inicio de sesión fallido
                Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            }
        }
    }

    private void sendGetRequest(String url) {
        System.out.println("Test2");
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Procesa la respuesta del servidor aquí
                        System.out.println("Respuesta del servidor recibida");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Maneja el error aquí
                        System.out.println(error);
                    }
                });

        requestQueue.add(getRequest);
    }

    private void checkUser(String email) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:3000/checkuser"; // Reemplaza con la dirección de tu API

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean exists = response.getBoolean("exists");
                            String message = response.getString("message");
                            // Realiza acciones según el resultado
                            if (exists) {
                                // El usuario existe
                                System.out.println("L'usuari existeix");
                            } else {
                                // El usuario no existe
                                System.out.println("L'usuari NO existeix");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Error al realizar la solicitud
                        error.printStackTrace();
                    }
                });

        queue.add(jsonObjectRequest);
    }
}