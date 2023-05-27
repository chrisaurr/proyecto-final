package com.example.proyectoprogramacioniii;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectoprogramacioniii.databinding.ActivityPrincipalBinding;
import com.example.proyectoprogramacioniii.viewmodels.CaracteristicasViewModel;
import com.example.proyectoprogramacioniii.viewmodels.UsuarioViewModel;
import com.example.proyectoprogramacioniii.views.BusquedaFragment;
import com.example.proyectoprogramacioniii.views.FavoritosFragment;
import com.example.proyectoprogramacioniii.views.InicioFragment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ActivityPrincipal extends AppCompatActivity {
    @Inject
    public UsuarioViewModel mUsuarioViewModel;

    @Inject
    public CaracteristicasViewModel mCaracteristicasViewModel;
    ActivityPrincipalBinding binding;
    public int id;

    private boolean isClicked = false;

    Animation rotateOpen, rotateClose, fromBottom, toBottom;
    Bundle args = new Bundle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrincipalBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        leerArchivo("archivo.txt");

        rotateOpen = AnimationUtils.loadAnimation(ActivityPrincipal.this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(ActivityPrincipal.this, R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(ActivityPrincipal.this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(ActivityPrincipal.this, R.anim.to_bottom_anim);

        id = getIntent().getIntExtra("id", 0);

        binding.floating1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSettingButtonClick();
            }
        });

        binding.floating2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Salir", Toast.LENGTH_SHORT).show();
                escribirFile("archivo.txt");
                mCaracteristicasViewModel.eliminarFavoritosUsuario(id);
                startActivity(new Intent(ActivityPrincipal.this, MainActivity.class));
                finish();
            }
        });

        binding.floating3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getApplicationContext(), "Gestionar Usuario", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ActivityPrincipal.this, ActivityGestionUsuarios.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });


        cambiarFragment(new InicioFragment());

        mCaracteristicasViewModel.obtenerCaracteristicas(id, 1);
        System.out.println(id);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){

                case R.id.incio:
                    cambiarFragment(new InicioFragment());
                    break;

                case R.id.buscar:
                    cambiarFragment(new BusquedaFragment());
                    break;

                case R.id.favoritos:
                    cambiarFragment(new FavoritosFragment());
                    break;
            }

            return true;
        });
    }

    private void cambiarFragment(Fragment fragment){
        args.putInt("id", id);
        fragment.setArguments(args);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void onSettingButtonClick(){
        setVisiblity(isClicked);
        setAnimation(isClicked);
        isClicked = !isClicked;
    }

    private void setAnimation(boolean isClicked) {
        if(!isClicked){
           binding.floating2.startAnimation(fromBottom);
           binding.floating3.startAnimation(fromBottom);
           binding.floating1.startAnimation(rotateOpen);
        }else{
          binding.floating2.startAnimation(toBottom);
          binding.floating3.startAnimation(toBottom);
          binding.floating1.startAnimation(rotateClose);
        }
    }

    private void setVisiblity(boolean isClicked) {
        if(!isClicked){
            binding.floating2.setVisibility(View.VISIBLE);
            binding.floating3.setVisibility(View.VISIBLE);
        }else{
            binding.floating2.setVisibility(View.INVISIBLE);
            binding.floating3.setVisibility(View.INVISIBLE);
        }
    }

    public void escribirFile(String Adress){
        File directory = this.getFilesDir(); //or getExternalFilesDir(null); for external storage
        String file1 = String.valueOf(new File(directory, Adress));
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {
            //Cada vez que se introduzca un dato nuevo se añade un salto de linea para separarlos
            String data = "";

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

            //Toast.makeText(this, lista1[0] + " " + lista1[1], Toast.LENGTH_SHORT).show();
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