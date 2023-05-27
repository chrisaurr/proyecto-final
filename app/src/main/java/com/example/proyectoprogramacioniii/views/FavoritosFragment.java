package com.example.proyectoprogramacioniii.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.proyectoprogramacioniii.ActivityCategorias;
import com.example.proyectoprogramacioniii.R;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Caracteristica;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RetrofiClientFlight;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosFavorito;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosUsuarios;
import com.example.proyectoprogramacioniii.adapters.FavProductRvAdapter;
import com.example.proyectoprogramacioniii.adapters.MyRvAdapter;
import com.example.proyectoprogramacioniii.adapters.ProductsRvAdapter;
import com.example.proyectoprogramacioniii.databinding.FragmentBusquedaBinding;
import com.example.proyectoprogramacioniii.databinding.FragmentFavoritosBinding;
import com.example.proyectoprogramacioniii.utils.FavoritosClass;
import com.example.proyectoprogramacioniii.utils.PojoProductos;
import com.example.proyectoprogramacioniii.viewmodels.CaracteristicasViewModel;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.zip.Inflater;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FavoritosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
public class FavoritosFragment extends Fragment {

    LinearLayoutManager linearLayoutManager;
    FavProductRvAdapter favProductsRvAdapter;
    int id;

    private ItemTouchHelper itemTouchHelper;

    ArrayList<Caracteristica> lista;

    int page = 1;

    @Inject
    CaracteristicasViewModel mCaracteristicasViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FavoritosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FavoritosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritosFragment newInstance(String param1, String param2) {
        FavoritosFragment fragment = new FavoritosFragment();
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

    FragmentFavoritosBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentFavoritosBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        id = getArguments().getInt("id");
        mCaracteristicasViewModel.obtenerCaracteristicas(id, page);

        Log.v("Probando", String.valueOf(id));

        String[] myArray = {"Normal", "Menor a mayor", "Mayor a menor"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item, myArray);
        binding.filtrosSp.setAdapter(adapter);


        binding.filtrosSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 0:

                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                addReycler(lista);
                            }
                        }, 3000);
                        break;

                    case 1:
                        addReycler(ordenamiento(lista, true));
                        break;

                    case 2:
                        addReycler(ordenamiento(lista, false));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.verticalRv.setVisibility(View.GONE);

        LayoutInflater inflater1 = (LayoutInflater) requireActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater1.inflate(R.layout.rv_favorite_product_shimmer, binding.linear, false);
        View layout2 = inflater1.inflate(R.layout.rv_favorite_product_shimmer, binding.linear, false);

        System.out.println("Pasa por aquí");
        binding.linear.removeAllViews();
        binding.linear.addView(layout);
        binding.linear.addView(layout2);


        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmer();


        mCaracteristicasViewModel.obtener(id).observe(getViewLifecycleOwner(), new Observer<List<Caracteristica>>() {
            @Override
            public void onChanged(List<Caracteristica> caracteristicas) {
                //System.out.println(caracteristicas.toString());
                lista = (ArrayList<Caracteristica>) caracteristicas;

                //Log.v("Caracteristics", " "+caracteristicas.get(0).descripcion);

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                            addReycler(caracteristicas);
                    }
                }, 3000);

            }
        });


        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
              //  System.out.println(mCaracteristicasViewModel.obtener());
            }
        }, 3000);


        binding.scrollViewFavorite.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    page++;
                    //binding.progressBar.setVisibility(View.VISIBLE);
                    mCaracteristicasViewModel.obtenerCaracteristicas(id, page);
                    binding.progressBarFavorite.setVisibility(View.VISIBLE);
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            addReycler(lista);
                        }
                    }, 3000);

                }
            }
        });

        return view;
    }

    public void addReycler(List<Caracteristica> caracteristicas){

        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        favProductsRvAdapter = new FavProductRvAdapter((ArrayList<Caracteristica>) caracteristicas, new FavProductRvAdapter.ItemClickListener() {
            @Override
            public void onItemClick(String url, View view, int pos) {

                if (view.getId() == R.id.visitarBtnFavorite){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
                if(view.getId() == R.id.eliminar){
                    caracteristicas.remove(pos);
                    mCaracteristicasViewModel.eliminarFavorito(url);
                    System.out.println(url);

                    serviciosFavorito servicios = RetrofiClientFlight.getRetrofitInstance().create(serviciosFavorito.class);
                    Call<FavoritosClass> call = servicios.deleteProduct(new FavoritosClass(id, url));

                    call.enqueue(new Callback<FavoritosClass>() {
                        @Override
                        public void onResponse(Call<FavoritosClass> call, Response<FavoritosClass> response) {

                        }

                        @Override
                        public void onFailure(Call<FavoritosClass> call, Throwable t) {

                        }
                    });
                    binding.verticalRv.setLayoutManager(linearLayoutManager);
                    binding.verticalRv.setAdapter(favProductsRvAdapter);
                }
                }

        });
        binding.verticalRv.setLayoutManager(linearLayoutManager);
        binding.verticalRv.setAdapter(favProductsRvAdapter);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                binding.progressBarFavorite.setVisibility(View.GONE);
                binding.verticalRv.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);
            }
        }, 5000);

    }


    public ArrayList<Caracteristica> ordenamiento(ArrayList<Caracteristica> p, boolean creciente) {
        p.removeIf(l -> l.precio.length() < 2);
        Collections.sort(p, new Comparator<Caracteristica>() {
            @Override
            public int compare(Caracteristica o1, Caracteristica o2) {
                String[] l = o1.precio.split(" ");
                String[] l1 = o2.precio.split(" ");

                System.out.println(o1.precio + o2.precio);
                String result1 = "";
                String result2 = "";
                if (l.length == 2 && l1.length == 2) {
                    result1 = l[1];
                    result2 = l1[1];

                    Log.v("PrimerIf", result1 + result2);
                } else if(l.length == 2 && l1.length == 1){
                    result1 = l[1];
                    result2 = l1[0];
                    Log.v("SegundoIf", result1 + result2);
                }else if(l.length == 1 && l1.length == 2){
                    result1 = l[0];
                    result2 = l1[1];
                    Log.v("SegundoIf", result1 + result2);
                }else{
                    result1 = l[0];
                    result2 = l1[0];
                    Log.v("TercerIf", result1 + result2);
                }

                NumberFormat nf = NumberFormat.getInstance(Locale.US);
                Integer val1 = null;
                Integer val2 = null;
                try {
                    val1 = nf.parse(result1.replaceAll("Q", "").replaceAll(",", "")).intValue();
                    val2 = nf.parse(result2.replaceAll("Q", "").replaceAll(",", "")).intValue();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                } catch(RuntimeException e){

                }

                if (creciente) {
                    return val1.compareTo(val2);
                } else {
                    return val2.compareTo(val1);
                }

            }
        });
        return p;
    }

}