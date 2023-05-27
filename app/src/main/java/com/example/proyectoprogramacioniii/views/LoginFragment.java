package com.example.proyectoprogramacioniii.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.proyectoprogramacioniii.ActivityCategorias;
import com.example.proyectoprogramacioniii.ActivityPrincipal;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Usuario;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RespuestaUsuario;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RetrofiClientFlight;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RetrofitCLient;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosUsuario;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosUsuarios;
import com.example.proyectoprogramacioniii.databinding.FragmentLoginBinding;
import com.example.proyectoprogramacioniii.utils.CommonMethod;
import com.example.proyectoprogramacioniii.utils.LoadingDialog;
import com.example.proyectoprogramacioniii.viewmodels.DepartamentoViewModel;
import com.example.proyectoprogramacioniii.viewmodels.MunicipioViewModel;
import com.example.proyectoprogramacioniii.viewmodels.UsuarioViewModel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class LoginFragment extends Fragment {

    LoadingDialog loadingDialog;

    String correo, contra;

    @Inject
    public UsuarioViewModel mUsuarioViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

   /* @Inject
    public UsuarioViewModel mUsuarioViewModel;
    @Inject
    public DepartamentoViewModel mDepartamentoViewModel;
    @Inject
    public MunicipioViewModel mMunicipioViewModel;*/

    private boolean result;

    public int id;

    Intent intent;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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

    private FragmentLoginBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        loadingDialog = new LoadingDialog(getActivity());

        List<Usuario> users = new ArrayList<>();


        leerArchivo("archivo.txt");

        userIsLoged(correo, contra);

        loadingDialog.iniciarLoadingDialog();
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Your Code
                if(result){
                    String data = binding.correoLoginEt.getText().toString() + " " + binding.contraLoginEt.getText().toString();
                    Intent intent = new Intent(getActivity(), ActivityPrincipal.class);

                    intent.putExtra("id", id);
                    startActivity(intent);
                    getActivity().finish();

                }else{
                    Toast.makeText(getContext(), "Usuario no existe", Toast.LENGTH_SHORT).show();
                }
                loadingDialog.ocultarLoginDialog();
            }
        }, 3000L);

        binding.LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.iniciarLoadingDialog();

                users.clear();




                if(CommonMethod.isOnline(requireContext())){
                    Log.v("Internet", "SI");
                }else{
                    Log.v("Internet", "NO");
                }

                userIsLoged(binding.correoLoginEt.getText().toString(), binding.contraLoginEt.getText().toString());
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Your Code
                        if(result){
                            String data = binding.correoLoginEt.getText().toString() + " " + binding.contraLoginEt.getText().toString();
                            escribirFile(data, "archivo.txt");
                            Intent intent = new Intent(getActivity(), ActivityPrincipal.class);

                            intent.putExtra("id", id);
                            //startActivity(new Intent(getActivity(), ActivityPrincipal.class));
                            startActivity(intent);
                            getActivity().finish();

                        }else{
                            Toast.makeText(getContext(), "Usuario no existe", Toast.LENGTH_SHORT).show();
                        }
                        loadingDialog.ocultarLoginDialog();
                    }
                }, 3000L);

            }
        });


        return view;
    }


    private void userIsLoged(String correo, String contra){
        serviciosUsuarios servicios = RetrofiClientFlight.getRetrofitInstance().create(serviciosUsuarios.class);
        Call<List<Usuario>> call = servicios.userExist(correo, contra);
        result = false;

        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                List<Usuario> user = response.body();

                //System.out.println(response.body().get(0).nombre);
               try{
                   if(user.get(0).nombre.length() > 0){
                       result = true;
                       id = user.get(0).id;
                   }else{
                       result = false;
                   }
               }catch (IndexOutOfBoundsException e){
                   result = false;
               }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                System.out.println(call.toString());
                t.printStackTrace();
            }
        });
    }

    public void escribirFile(String fileString, String Adress){
        File directory = getActivity().getFilesDir(); //or getExternalFilesDir(null); for external storage
        String file1 = String.valueOf(new File(directory, Adress));
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            //Cada vez que se introduzca un dato nuevo se añade un salto de linea para separarlos
            String data = fileString + "\n";

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


            //System.out.println("información agregada!");
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

    public void leerArchivo(String nomArchivo) {

        try {

            //Variables para leer información y separar palabras segun los espacios
            InputStreamReader archivo = new InputStreamReader(getActivity().openFileInput(nomArchivo));
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

}