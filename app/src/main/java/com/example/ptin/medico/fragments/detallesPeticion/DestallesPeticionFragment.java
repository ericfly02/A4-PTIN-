package com.example.ptin.medico.fragments.detallesPeticion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ptin.R;

import java.util.ArrayList;

public class DestallesPeticionFragment extends Fragment {

    ArrayList<String> medicamentos;
    TextView textView_nombre;
    TextView textView_apellido;
    TextView textView_dni;
    public DestallesPeticionFragment() {
        medicamentos = new ArrayList<>();
        medicamentos.add("Actafarma Oxicol 28caps");
        medicamentos.add("Cicatridina Supositorios 10uds");
        medicamentos.add("Aquilea Omega 3 Forte 90caps");
        medicamentos.add("Formag Magnesio Marino 90comp");
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

        View view = inflater.inflate(R.layout.fragment_destalles_peticion, container, false);

        // Asociar elementos del layout
        textView_nombre = view.findViewById(R.id.txt_nombre_historial);
        textView_apellido = view.findViewById(R.id.txt_apellido_historial);
        textView_dni = view.findViewById(R.id.txt_dni_historial);

        AdaptadorListView(view);
        return view;
    }

    public void AdaptadorListView(View view){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, medicamentos);
        ListView listView = (ListView) view.findViewById(R.id.ListView_Peticion);
        listView.setAdapter(adapter);
    }
}