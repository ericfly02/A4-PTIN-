package com.example.appptin.perfilMedico;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.ptin.MainActivity;
import com.example.ptin.R;

public class PerfilMedicoFragment extends Fragment {

    ImageView foto_perfil;
    Button cerrar_sesion;
    public PerfilMedicoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil_medico, container, false);

        foto_perfil = view.findViewById(R.id.imageView_profile_medico);
        foto_perfil.setImageResource(R.drawable.imagen_medico);

        cerrar_sesion = view.findViewById(R.id.btn_cerrar_sesion_medico);

        //Aqui deberá llamar al Activity de login
        cerrar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Sessió tancada",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("mensaje",1);
                startActivity(intent);
            }
        });
        return view;
    }
}