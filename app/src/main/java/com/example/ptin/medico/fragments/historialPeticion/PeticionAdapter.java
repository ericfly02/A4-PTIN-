package com.example.ptin.medico.fragments.historialPeticion;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ptin.R;

import com.example.ptin.medico.fragments.aprobarPeticion.AprobarFragment;
import com.example.ptin.medico.fragments.detallesPeticion.DestallesPeticionFragment;
import com.example.ptin.medico.fragments.historialPaciente.HistorialPacienteFragment;

import java.util.ArrayList;

public class PeticionAdapter extends RecyclerView.Adapter<PeticionAdapter.MyHolder> {
    Context context;
    FragmentManager activity;
    ArrayList<PeticionClass> arrayList;
    LayoutInflater layoutInflater;
    int codi;

    //Constructor
    public PeticionAdapter(Context context, ArrayList<PeticionClass> arrayList,int codigo,FragmentManager activity) {
        this.context = context;
        this.activity = activity;
        this.arrayList = arrayList;
        this.codi = codigo;
        layoutInflater=LayoutInflater.from(context); //Obtener el contexto del activity
    }

    // Crear el objeto "View" a partir del diseño item_file.xml (representa el diseño xml)
    // Devuelve la instáncia de la clase MyHolder
    // El layoutManager invoca este método para redenrizar cada elemento del RecyclerView
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=layoutInflater.inflate(R.layout.contenedor_cliente,parent,false);

        return new MyHolder(view);
    }

    // Asignar valores para cada elemento de la lista
    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.dni.setText(arrayList.get(position).getDni());
        holder.nombre.setText(arrayList.get(position).getNombre());
        holder.apellido.setText(arrayList.get(position).getApellidos());

        // Evento sobre cada Elemento
        holder.contenedorElem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dni = arrayList.get(position).getDni();
                String nombre = arrayList.get(position).getNombre();
                String apellido = arrayList.get(position).getApellidos();

                //Toast.makeText(context,"HOLAAAAAAA!",Toast.LENGTH_SHORT).show();

                //creo el bundle que contendrá el texto a pasar a los fragments
                Bundle result = new Bundle();

                //Mensaje a enviar al fragment
                result.putString("dni_paciente", dni);
                result.putString("nombre_paciente", nombre);
                result.putString("apellido-paciente", apellido);
                result.putInt("posicion_paciente",position);

                activity.setFragmentResult("key_aprobar_peticion",result);

                Fragment cambiofragment = null;

                // carga la ventana del fragment según el Nº de código
                if(codi==1) cambiofragment= new DestallesPeticionFragment();
                else if (codi==2) cambiofragment= new HistorialPacienteFragment();
                else if (codi==3) cambiofragment= new AprobarFragment();


                FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                //Cambio de Fragment
                fragmentTransaction.replace(R.id.frame_container,cambiofragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

    }

    // Devuelve la cantidad de elementos del ReclyclerView
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    // Obtener referencias de los componentes visuales para cada elemento
    // Es decir, referencias de los TextViews correspondientes al DNI y Nombre
    public class MyHolder extends RecyclerView.ViewHolder {
        TextView dni, nombre, apellido;
        View contenedorElem;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            dni =itemView.findViewById(R.id.txt_dni);
            nombre =itemView.findViewById(R.id.txt_nombre);
            apellido =itemView.findViewById(R.id.txt_apellido);

            contenedorElem=itemView.findViewById(R.id.layout_elementos);
        }
    }
}
