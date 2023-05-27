package com.example.proyectoprogramacioniii.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Departamento;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Municipio;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Pais;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Rol;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Usuario;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RespuestaUsuario;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RetrofiClientFlight;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RetrofitCLient;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosUsuario;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosUsuarios;
import com.example.proyectoprogramacioniii.databinding.FragmentRegistroBinding;
import com.example.proyectoprogramacioniii.viewmodels.DepartamentoViewModel;
import com.example.proyectoprogramacioniii.viewmodels.MainActivityViewModel;
import com.example.proyectoprogramacioniii.viewmodels.MunicipioViewModel;
import com.example.proyectoprogramacioniii.viewmodels.PrivilegioViewModel;
import com.example.proyectoprogramacioniii.viewmodels.RolAccesoViewModel;
import com.example.proyectoprogramacioniii.viewmodels.RolViewModel;
import com.example.proyectoprogramacioniii.viewmodels.TiendaViewModel;
import com.example.proyectoprogramacioniii.viewmodels.UsuarioViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class RegistroFragment extends Fragment {

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

    /*@Inject
    public Retrofit retrofit;*/

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<String> nombres = new ArrayList<>();

    List<RespuestaUsuario> users = new ArrayList<>();

    List<String> registro = new ArrayList<>();

    public RegistroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistroFragment newInstance(String param1, String param2) {
        RegistroFragment fragment = new RegistroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    private FragmentRegistroBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentRegistroBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        iniciarSpinner();
        //getUser();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Your Code
                for(RespuestaUsuario response : users){
                    Log.v("Prueba", response.toString());
                }
            }
        }, 3000L);


        binding.registroBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long ahora = System.currentTimeMillis();
                Date fecha = new Date(ahora);
                DateFormat df = new SimpleDateFormat("dd/MM/yy");
                String salida = df.format(fecha);

                String mensaje = "";

                if(!isValidEmail(binding.correoRegistroEt.getText().toString()) &&
                        !validatePass(binding.contraRegistroEt.getText().toString())){
                    mensaje = "Correo y contraseña no validos";
                }else if(!isValidEmail(binding.correoRegistroEt.getText().toString())){
                    mensaje = "Correo no valido";
                }else if(!validatePass(binding.contraRegistroEt.getText().toString())){
                    mensaje = "Contraseña no valida";
                }else{
                    postUser2(new Usuario(binding.nombreRegistroEt.getText().toString(), binding.correoRegistroEt.getText().toString(), binding.contraRegistroEt.getText().toString(), salida, ((Pais)binding.paisSp.getSelectedItem()).id, ((Departamento)binding.departamentoSp.getSelectedItem()).id, ((Municipio)binding.municipioSp.getSelectedItem()).id, ((Rol)binding.rolSp.getSelectedItem()).id_rol, false));
                }

                if(!mensaje.isEmpty()){
                    Toast.makeText(getActivity(), mensaje, Toast.LENGTH_SHORT).show();
                }

            }
        });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    public void iniciarSpinner(){
        mMainActivityViewModel.obtenerPaises().observe(getViewLifecycleOwner(), new Observer<List<Pais>>() {
            @Override
            public void onChanged(List<Pais> pais) {
                for(Pais p : pais) {
                    ArrayAdapter<Pais> arrayAdapter = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, pais);
                    binding.paisSp.setAdapter(arrayAdapter);
                }
            }
        });

        mRolViewModel.obtenerRol().observe(getViewLifecycleOwner(), new Observer<List<Rol>>() {
            @Override
            public void onChanged(List<Rol> rols) {
                ArrayAdapter<Rol> arrayAdapter = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, rols);
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
        mDepartamentoViewModel.mostrarDeps(id).observe(getViewLifecycleOwner(), new Observer<List<Departamento>>() {
            @Override
            public void onChanged(List<Departamento> departamentos) {
                ArrayAdapter<Departamento> arrayAdapter = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, departamentos);
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
        mMunicipioViewModel.obtenerMunicipios(id).observe(getViewLifecycleOwner(), new Observer<List<Municipio>>() {
            @Override
            public void onChanged(List<Municipio> municipios) {
                ArrayAdapter<Municipio> arrayAdapter = new ArrayAdapter<>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, municipios);
                binding.municipioSp.setAdapter(arrayAdapter);
            }
        });
    }

    private void postUser2(Usuario user){
        serviciosUsuarios servicios = RetrofiClientFlight.getRetrofitInstance().create(serviciosUsuarios.class);
        Call<Usuario> call = servicios.registerUser(user);
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

   /* private void getUser(){
        serviciosUsuario servicios = RetrofitCLient.getRetrofitInstance().create(serviciosUsuario.class);
        Call<RespuestaUsuario> call = servicios.obtenerUsuarioById(1);

        call.enqueue(new Callback<RespuestaUsuario>() {
            @Override
            public void onResponse(Call<RespuestaUsuario> call, Response<RespuestaUsuario> response) {
                RespuestaUsuario user = response.body();
                nombres.add(user.nombre);
                Log.v("tag", response.body().nombre);
            }

            @Override
            public void onFailure(Call<RespuestaUsuario> call, Throwable t) {

            }
        });
    }*/

    private boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public boolean validatePass(String contra){
        return contra.length() >= 6 && contra.length() <= 16;
    }

}