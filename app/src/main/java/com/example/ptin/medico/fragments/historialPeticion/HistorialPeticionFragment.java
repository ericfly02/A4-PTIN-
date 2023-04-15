package com.example.ptin.medico.fragments.historialPeticion;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ptin.R;

import java.util.ArrayList;


public class HistorialPeticionFragment extends Fragment {
    // Variables necesarias
    SearchView searchView;
    RecyclerView recyclerView;
    ArrayList<PeticionClass> arrayList=new ArrayList<>();
    ArrayList<PeticionClass> searchList;
    String[] dniList;
    String[] nombreList;
    String[] apellidoList;
    TextView titulo;
    String campo;
    // Codigo para diferenciar entre los diferentes fragments
    int codi;
    int posicion;
    boolean borrar;
    public HistorialPeticionFragment(String texto, int codigo,int posicion,boolean borrar) {
        this.campo = texto;
        this.codi = codigo;
        this.posicion = posicion;
        this.borrar = borrar;

        dniList =new String[]{"64061153N","75214665L","43978377P","01571240H","14633715B","25048160X","24937213S","02751483Q"};
        nombreList =new String[]{"Ignasio","Carla","Pablo","Joel","Anna","Juan","Luis","Carlos"};
        apellidoList = new String[]{"Segovia Bueno","Figueras Barrera","Baena Cervera","Hoz Reguera","Exposito Perera","Acedo Fiol","Alcaraz Salvador","Cáceres Reguera"};

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_historial_peticion, container, false);
        Lista(view);

        return view;
    }

    public void Lista(View view) {
        //Asociación de los obejtos creados en el XML (diseño)
        recyclerView = view.findViewById(R.id.recyclerView);
        searchView = view.findViewById(R.id.searchView);
        titulo = view.findViewById(R.id.textView_historial);

        titulo.setText(campo);

        //Crear la primera vez
        if(arrayList.size()==0){
            //Creación de la lista de Peticiones pendientes
            for (int i = 0; i < dniList.length; i++) {
                PeticionClass modelClass = new PeticionClass();
                modelClass.setDni(dniList[i]);
                modelClass.setNombre(nombreList[i]);
                modelClass.setApellidos(apellidoList[i]);
                arrayList.add(modelClass);
            }
        }

        if(borrar) arrayList.remove(posicion);

        //Creación de LayoutManager que se encarga de la disposición de los elementos del RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //Asociación de adapter que se encarga de adaptar los datos a lo que finalmente verá el usuario.
        // Es el encargado de traducir datos en UI.
        PeticionAdapter peticionAdapter = new PeticionAdapter(getActivity(),arrayList,codi,getParentFragmentManager());
        recyclerView.setAdapter(peticionAdapter);

        //Listener del searView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            //Acción al realizar una búsqueda al presionar  el botón
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Guardar los Objetos de la clase PeticionClass relacionados a la búsqueda
                searchList = new ArrayList<>();
                if (query.length() > 0) {
                    //Recorrer todos los Objetos (los elementos de la lista)
                    for (int i = 0; i < arrayList.size(); i++) {
                        // Comprobar si coincide el texto con algún elemento ya sea por DNI o Nombre
                        if (arrayList.get(i).getDni().toUpperCase().contains(query.toUpperCase())
                                || arrayList.get(i).getNombre().toUpperCase().contains(query.toUpperCase())) {
                            //Creo el objecto (Elemento localizado)
                            PeticionClass modelClass = new PeticionClass();
                            modelClass.setDni(arrayList.get(i).getDni());
                            modelClass.setNombre(arrayList.get(i).getNombre());
                            // Guardo el elemento en la lista
                            searchList.add(modelClass);
                        }
                    }
                    //Creación de LayoutManager que se encarga de la disposición de los elementos del RecyclerView
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);

                    // Creación del adapter con la nueva lista de elementos búscados
                    PeticionAdapter peticionAdapter = new PeticionAdapter(getActivity(), searchList,codi,getParentFragmentManager());

                    recyclerView.setAdapter(peticionAdapter);
                }
                //En caso de no localzarse ningún objeto (elementos) se carga la lista de objetos completos
                else {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);

                    PeticionAdapter peticionAdapter = new PeticionAdapter(getActivity(), arrayList,codi,getParentFragmentManager());
                    recyclerView.setAdapter(peticionAdapter);
                }
                return false;
            }

            //Acción al cambiar el texto de búsqueda
            @Override
            public boolean onQueryTextChange(String newText) {
                searchList = new ArrayList<>();

                if (newText.length() > 0) {
                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList.get(i).getDni().toUpperCase().contains(newText.toUpperCase())
                                || arrayList.get(i).getNombre().toUpperCase().contains(newText.toUpperCase())) {
                            PeticionClass modelClass = new PeticionClass();
                            modelClass.setDni(arrayList.get(i).getDni());
                            modelClass.setNombre(arrayList.get(i).getNombre());
                            searchList.add(modelClass);
                        }
                    }
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);

                    PeticionAdapter peticionAdapter = new PeticionAdapter(getActivity(), searchList,codi,getParentFragmentManager());

                    recyclerView.setAdapter(peticionAdapter);
                } else {
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(layoutManager);

                    PeticionAdapter peticionAdapter = new PeticionAdapter(getActivity(), arrayList,codi,getParentFragmentManager());

                    recyclerView.setAdapter(peticionAdapter);
                }
                return false;
            }
        });

    }
}