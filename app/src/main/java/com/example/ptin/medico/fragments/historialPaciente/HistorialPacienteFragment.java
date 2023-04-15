package com.example.ptin.medico.fragments.historialPaciente;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ptin.R;


public class HistorialPacienteFragment extends Fragment {
    TextView textView_nombre;
    TextView textView_apellido;
    TextView textView_dni;

    public HistorialPacienteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getParentFragmentManager().setFragmentResultListener("key_aprobar_peticion", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                //Obtener resultados del fragment
                String nombre_recibido = result.getString("nombre_paciente");
                String apellido_paciente = result.getString("apellido-paciente");
                String dni_paciente = result.getString("dni_paciente");

                //Asociar los valores con los elementos del layout
                textView_nombre.setText(nombre_recibido);
                textView_apellido.setText(apellido_paciente);
                textView_dni.setText(dni_paciente);

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_historial_paciente, container, false);

        // Asociar elementos del layout
        textView_nombre = view.findViewById(R.id.txt_nombre_informe);
        textView_apellido = view.findViewById(R.id.txt_apellido_informe);
        textView_dni = view.findViewById(R.id.txt_dni_informe);

        return view;
    }
}