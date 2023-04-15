package com.example.ptin.medico.fragments.aprobarPeticion;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ptin.R;
import com.example.ptin.medico.fragments.historialPeticion.HistorialPeticionFragment;
import com.example.ptin.medico.fragments.historialPeticion.PeticionAdapter;
import com.example.ptin.medico.fragments.historialPeticion.PeticionClass;

import java.util.ArrayList;


public class AprobarFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<String> medicamentos =new ArrayList<String>();
    TextView textView_nombre;
    TextView textView_apellido;
    TextView textView_dni;

    Button btn_aceptar;
    Button btn_rechazar;
    int posicion;

    public AprobarFragment() {
        // Required empty public constructor
        //Añadir medicamentos
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
                posicion = result.getInt("posicion_paciente");

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
        View view = inflater.inflate(R.layout.fragment_aprobar, container, false);
        Lista_medicamentos(view);
        return view;
    }

    public void Lista_medicamentos(View view) {
        //Asociación de los objetos creados en el XML (diseño)
        recyclerView = view.findViewById(R.id.recyclerView_aprobar);

        textView_nombre = view.findViewById(R.id.txt_peticion_nombre);
        textView_apellido = view.findViewById(R.id.txt_peticion_apellido);
        textView_dni = view.findViewById(R.id.txt_peticion_dni);

        btn_aceptar = view.findViewById(R.id.btn_aceptar_peticion);
        btn_rechazar = view.findViewById(R.id.btn_rechazar_peticion);


        //Creación de LayoutManager que se encarga de la disposición de los elementos del RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //Asociación de adapter que se encarga de adaptar los datos a lo que finalmente verá el usuario.
        // Es el encargado de traducir datos en UI.
        MedicamentosAdapter medicamentosAdapter = new MedicamentosAdapter(getActivity(), medicamentos);
        recyclerView.setAdapter(medicamentosAdapter);

        btn_aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Petició aceptada",Toast.LENGTH_SHORT).show();

                //Fragment anterior
                HistorialPeticionFragment peticionFragment = new HistorialPeticionFragment("Peticions per aprovar",3,posicion,true);

                //Regresar al Fragment anterior
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                //Cambio de Fragment
                fragmentTransaction.replace(R.id.frame_container,peticionFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btn_rechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Petició Rebutjada",Toast.LENGTH_SHORT).show();

                //Fragment anterior
                HistorialPeticionFragment peticionFragment = new HistorialPeticionFragment("Peticions per aprovar",3,posicion,true);

                //Regresar al Fragment anterior
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                //Cambio de Fragment
                fragmentTransaction.replace(R.id.frame_container,peticionFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }
}