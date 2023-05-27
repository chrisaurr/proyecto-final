package com.example.proyectoprogramacioniii;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Departamento;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Municipio;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Pais;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Rol;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Usuario;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RetrofiClientFlight;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosFavorito;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosUsuarios;
import com.example.proyectoprogramacioniii.databinding.ActivityCategoriasBinding;
import com.example.proyectoprogramacioniii.databinding.ActivityGestionUsuariosBinding;
import com.example.proyectoprogramacioniii.utils.FavoritosClass;
import com.example.proyectoprogramacioniii.viewmodels.DepartamentoViewModel;
import com.example.proyectoprogramacioniii.viewmodels.MainActivityViewModel;
import com.example.proyectoprogramacioniii.viewmodels.MunicipioViewModel;
import com.example.proyectoprogramacioniii.viewmodels.PrivilegioViewModel;
import com.example.proyectoprogramacioniii.viewmodels.RolAccesoViewModel;
import com.example.proyectoprogramacioniii.viewmodels.RolViewModel;
import com.example.proyectoprogramacioniii.viewmodels.TiendaViewModel;
import com.example.proyectoprogramacioniii.viewmodels.UsuarioViewModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@AndroidEntryPoint
public class ActivityGestionUsuarios extends AppCompatActivity {

    List<Municipio> muni;

    @Inject
    public MainActivityViewModel mMainActivityViewModel;
    @Inject
    public DepartamentoViewModel mDepartamentoViewModel;
    @Inject
    public MunicipioViewModel mMunicipioViewModel;
    @Inject
    public RolViewModel mRolViewModel;
    @Inject
    public PrivilegioViewModel mPrivilegioViewModel;
    @Inject
    public RolAccesoViewModel mRolAccesoViewModel;

    @Inject
    public UsuarioViewModel mUsuarioViewModel;

    @Inject
    public TiendaViewModel mTiendaViewModel;

    int id;
    String correo, contra;

    ActivityGestionUsuariosBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGestionUsuariosBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        iniciarSpinner();
        leerArchivo("archivo.txt");

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Your Code
                userIsLoged(correo, contra);
            }
        }, 1000L);

        id = getIntent().getIntExtra("id", 0);

        binding.editarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                putUser(new Usuario(binding.nombreRegistroEt.getText().toString(), binding.correoRegistroEt.getText().toString(), binding.contraRegistroEt.getText().toString(), ((Pais)binding.paisSp.getSelectedItem()).id, ((Departamento)binding.departamentoSp.getSelectedItem()).id, ((Municipio)binding.municipioSp.getSelectedItem()).id, ((Rol)binding.rolSp.getSelectedItem()).id_rol, false, id));
                String data = binding.correoRegistroEt.getText().toString() + " " + binding.contraRegistroEt.getText().toString();
                escribirFile(data, "archivo.txt");
            }
        });

        binding.eliminarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser(id);
                escribirFile("", "archivo.txt");
                finish();
                startActivity(new Intent(ActivityGestionUsuarios.this, MainActivity.class));
            }
        });

    }

    public void iniciarSpinner(){
        mMainActivityViewModel.obtenerPaises().observe(this, new Observer<List<Pais>>() {
            @Override
            public void onChanged(List<Pais> pais) {
                for(Pais p : pais) {
                    //Log.v("Test", p.nombre);
                    ArrayAdapter<Pais> arrayAdapter = new ArrayAdapter<>(ActivityGestionUsuarios.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, pais);
                    binding.paisSp.setAdapter(arrayAdapter);
                }
            }
        });

        mRolViewModel.obtenerRol().observe(this, new Observer<List<Rol>>() {
            @Override
            public void onChanged(List<Rol> rols) {
                ArrayAdapter<Rol> arrayAdapter = new ArrayAdapter<>(ActivityGestionUsuarios.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, rols);
                binding.rolSp.setAdapter(arrayAdapter);
            }
        });

        binding.paisSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                llenarSpinnerDep(((Pais)parent.getSelectedItem()).id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void llenarSpinnerDep(int id){
        mDepartamentoViewModel.mostrarDeps(id).observe(this, new Observer<List<Departamento>>() {
            @Override
            public void onChanged(List<Departamento> departamentos) {
                ArrayAdapter<Departamento> arrayAdapter = new ArrayAdapter<>(ActivityGestionUsuarios.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, departamentos);
                binding.departamentoSp.setAdapter(arrayAdapter);
            }
        });

        binding.departamentoSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                llenarSpinnerMuni(((Departamento) parent.getSelectedItem()).id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.v("Prueba", "entra aqui");
                llenarSpinnerMuni(0);
            }
        });

        llenarSpinnerMuni(0);
    }

    public void llenarSpinnerMuni(int id){
        mMunicipioViewModel.obtenerMunicipios(id).observe(this, new Observer<List<Municipio>>() {
            @Override
            public void onChanged(List<Municipio> municipios) {
                ArrayAdapter<Municipio> arrayAdapter = new ArrayAdapter<>(ActivityGestionUsuarios.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, municipios);
                binding.municipioSp.setAdapter(arrayAdapter);

                muni = municipios;
            }
        });
    }

    private void userIsLoged(String correo, String contra){
        serviciosUsuarios servicios = RetrofiClientFlight.getRetrofitInstance().create(serviciosUsuarios.class);
        Call<List<Usuario>> call = servicios.userExist(correo, contra);


        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                List<Usuario> user = response.body();

                //System.out.println(response.body().get(0).nombre);
                try{
                    if(user.get(0).nombre.length() > 0){
                        binding.nombreRegistroEt.setText(user.get(0).nombre);
                        binding.correoRegistroEt.setText(user.get(0).correo);
                        binding.contraRegistroEt.setText(user.get(0).contra);
                        binding.paisSp.setSelection(user.get(0).id_pais - 1);
                        binding.departamentoSp.setSelection(user.get(0).id_departamentos - 1);
                        //binding.municipioSp.setSelection(user.get(0).id_municipios - 1);

                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                // Your Code
                                for(int i = 0; i < muni.size(); i++){
                                    Log.v("prueba", binding.municipioSp.getItemAtPosition(i).toString());
                                    if(muni.get(i).id == user.get(0).id_municipios){
                                        binding.municipioSp.setSelection(i);
                                    }
                                }
                            }
                        }, 1000L);



                        binding.rolSp.setSelection(user.get(0).id_rol - 1);
                    }else{

                    }
                }catch (IndexOutOfBoundsException e){

                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                System.out.println(call.toString());
                t.printStackTrace();
                //System.out.println(response.body());
            }
        });
    }

    public void leerArchivo(String nomArchivo) {

        try {

            //Variables para leer información y separar palabras segun los espacios
            InputStreamReader archivo = new InputStreamReader(this.openFileInput(nomArchivo));
            BufferedReader br = new BufferedReader(archivo);
            String linea = br.readLine();
            String valor1 = linea;
            String[] lista1 = valor1.split(" ");
            String busqueda = lista1[0].toString();
            String contenido = "";

            br.close();
            archivo.close();

            correo = lista1[0];
            contra = lista1[1];
            Log.v("prueba", contenido);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("a");
        }catch(NullPointerException ex){
            System.out.println("null");
        }

    }

    public void escribirFile(String fileString, String Adress){
        File directory = ActivityGestionUsuarios.this.getFilesDir(); //or getExternalFilesDir(null); for external storage
        String file1 = String.valueOf(new File(directory, Adress));
        BufferedWriter bw = null;
        FileWriter fw = null;
        String data;

        try {
            //Cada vez que se introduzca un dato nuevo se añade un salto de linea para separarlos
            data = fileString + "\n";

            //Creamos archivo
            File file = new File(file1);
            // Si el archivo no existe, se crea!
            if (!file.exists()) {//Si no existe creamos uno nuevo
                file.createNewFile();
            }

            fw = new FileWriter(file.getAbsoluteFile(), false);
            bw = new BufferedWriter(fw);

            //Obtenemos cada uno de los elementos separados por espacios
            String[] lista1 = data.split(" ");

            bw.write(data); //Añadimos la nueva información al archivo

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //Cierra instancias de FileWriter y BufferedWriter
                if (bw != null)
                    bw.close();
                if (fw != null)
                    fw.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void putUser(Usuario user){
        serviciosUsuarios servicios = RetrofiClientFlight.getRetrofitInstance().create(serviciosUsuarios.class);
        Call<Usuario> call = servicios.modifyUser(user);
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
//               Log.e("Tag", "onResponse: "+response.body().getNombre());
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Log.e("Tag", "On failure: "+t.getMessage());
            }
        });
    }

    private void deleteUser(int id){
        serviciosUsuarios servicios = RetrofiClientFlight.getRetrofitInstance().create(serviciosUsuarios.class);
        Call<Usuario> call = servicios.deleteUser(new Usuario(id));

        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {

            }
        });
    }
}