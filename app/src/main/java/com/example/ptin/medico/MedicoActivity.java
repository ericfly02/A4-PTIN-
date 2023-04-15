package com.example.ptin.medico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.ptin.R;
import com.example.ptin.medico.fragments.aprobarPeticion.AprobarFragment;
import com.example.ptin.medico.fragments.historialPaciente.HistorialPacienteFragment;
import com.example.ptin.medico.fragments.historialPeticion.HistorialPeticionFragment;
import com.example.ptin.medico.fragments.perfilMedico.PerfilMedicoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MedicoActivity extends AppCompatActivity {

    //AprobarFragment aprobarFragment = new AprobarFragment();
    HistorialPeticionFragment aprobarFragment;
    HistorialPeticionFragment pacienteFragment;
    HistorialPeticionFragment peticionFragment;
    PerfilMedicoFragment medicoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medico);

        BottomNavigationView navigation = findViewById(R.id.bottom_navegation);
        navigation.setOnNavigationItemSelectedListener(nOnNavigationItemSelectedListener);



        aprobarFragment = new HistorialPeticionFragment("Peticions per aprovar",3,0,false);
        pacienteFragment = new HistorialPeticionFragment("Historial Pacients",2,0,false);
        peticionFragment = new HistorialPeticionFragment("Historial Peticions",1,0,false);
        medicoFragment = new PerfilMedicoFragment();

        loadFragment(aprobarFragment);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener nOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.menu_aprobarPeticion:
                    loadFragment(aprobarFragment);
                    return true;
                case R.id.menu_historialPeticion:
                    loadFragment(peticionFragment);
                    return true;
                case R.id.menu_historialPaciente:
                    loadFragment(pacienteFragment);
                    return true;
                case R.id.menu_perfilMedico:
                    loadFragment(medicoFragment);
                    return true;
            }
            return false;
        }
    };

    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();

    }
}