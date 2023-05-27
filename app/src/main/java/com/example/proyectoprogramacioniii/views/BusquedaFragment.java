package com.example.proyectoprogramacioniii.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyectoprogramacioniii.ActivityCategorias;
import com.example.proyectoprogramacioniii.ActivityResultadosBusqueda;
import com.example.proyectoprogramacioniii.R;
import com.example.proyectoprogramacioniii.databinding.FragmentBusquedaBinding;
import com.example.proyectoprogramacioniii.databinding.FragmentInicioBinding;
import com.example.proyectoprogramacioniii.utils.LoadingDialog;
import com.example.proyectoprogramacioniii.utils.PojoProductos;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BusquedaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BusquedaFragment extends Fragment {
    LoadingDialog loadingDialog;

    String urlSearch;
    int id;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BusquedaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BusquedaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BusquedaFragment newInstance(String param1, String param2) {
        BusquedaFragment fragment = new BusquedaFragment();
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

    FragmentBusquedaBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBusquedaBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        id = getArguments().getInt("id");

        loadingDialog = new LoadingDialog(getActivity());

        binding.lacuracaoCheck.setChecked(true);
        binding.agenciasWayCheck.setChecked(true);
        binding.tiendaMaxCheck.setChecked(true);
        binding.tecnoFacilCheck.setChecked(true);

        binding.lacuracaoCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.lacuracaoCheck.setChecked(!binding.lacuracaoCheck.isChecked());
            }
        });

        binding.agenciasWayCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.agenciasWayCheck.setChecked(!binding.agenciasWayCheck.isChecked());
            }
        });

        binding.tiendaMaxCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tiendaMaxCheck.setChecked(!binding.tiendaMaxCheck.isChecked());
            }
        });

        binding.tecnoFacilCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.tecnoFacilCheck.setChecked(!binding.tecnoFacilCheck.isChecked());
            }
        });

        binding.buscarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.iniciarLoadingDialog();
                new java.util.Timer().schedule(
                        new java.util.TimerTask() {

                            @Override
                            public void run() {
                                Set<PojoProductos> set = new HashSet<>();
                                if(binding.precioMin.getText().toString().trim().isEmpty() && binding.precioMax.getText().toString().trim().isEmpty()){
                                    set = datosTiendas(binding.productBusqueda.getQuery().toString());
                                }else{
                                    set = ordenar(datosTiendas(binding.productBusqueda.getQuery().toString()),
                                            binding.precioMin.getText().toString(), binding.precioMax.getText().toString());
                                }
                                urlSearch = binding.productBusqueda.getQuery().toString();
                                ArrayList<PojoProductos> milista = new ArrayList<>(set);

                               // milista.forEach(l -> System.out.println(l.numTienda));
                                Intent intent = new Intent(getActivity(), ActivityResultadosBusqueda.class);
                                Bundle bundle = new Bundle();
                                bundle.putParcelableArrayList("miSet", (ArrayList<? extends Parcelable>) milista);
                                bundle.putString("laCuracaoUrl", urlSearch);
                                bundle.putBoolean("LaCuracao", binding.lacuracaoCheck.isChecked());
                                bundle.putBoolean("AgenciasWay", binding.agenciasWayCheck.isChecked());
                                bundle.putBoolean("Max", binding.tiendaMaxCheck.isChecked());
                                bundle.putBoolean("TecnoFacil", binding.tecnoFacilCheck.isChecked());
                                bundle.putString("precioMin", binding.precioMin.getText().toString());
                                bundle.putString("precioMax", binding.precioMax.getText().toString());
                                bundle.putInt("id", id);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                loadingDialog.ocultarLoginDialog();
                            }
                        }, 5000);
            }
        });

        new java.util.Timer().schedule(
                new java.util.TimerTask() {

                    @Override
                    public void run() {
                       datosAgenciasWay("laptop");
                    }
                }, 5000);



        return view;
    }

    public Set<PojoProductos> datosTiendas(String producto) {
        Set<PojoProductos> allProductos = new HashSet<>();

        if (binding.lacuracaoCheck.isChecked()) {
            allProductos.addAll(datosLaCuracao(producto));
        }

        if (binding.agenciasWayCheck.isChecked()) {
            allProductos.addAll(datosAgenciasWay(producto));
        }

        if (binding.tiendaMaxCheck.isChecked()) {
            allProductos.addAll(datosMax(producto));
        }

        if (binding.tecnoFacilCheck.isChecked()) {
            allProductos.addAll(datosTecnoFacil(producto));
        }

        allProductos.removeIf(i -> i.precio.length() == 0);

        return allProductos;
    }

    public Set<PojoProductos> datosLaCuracao(String product) {
        Set<PojoProductos> laCuracaoProductos = new HashSet<>();

        try {
            String url = "https://www.lacuracaonline.com/guatemala/catalogsearch/result/?q=" + product;
            urlSearch = url;

            Document doc = Jsoup.connect(url).get();

            Elements data = doc.getElementsByClass("product-item");

            for (Element d : data) {

                laCuracaoProductos.add(new PojoProductos(d.getElementsByClass("product-image-photo").attr("src"),
                        d.getElementsByClass("product-item-category").text(),
                        d.getElementsByClass("product-item-link").text(),
                        d.getElementsByClass("price").text(),
                        d.getElementsByClass("product-item-photo").attr("href"), 1));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return laCuracaoProductos;
    }

    public Set<PojoProductos> datosAgenciasWay(String product) {
        Set<PojoProductos> agenciasWayProductos = new HashSet<>();
        Set<String> links = new HashSet<>();

        try {
            String url = "https://agenciaswayonline.com/?s=" + product;

            Document doc = Jsoup.connect(url).get();

            Elements data = doc.select("header.entry-header > h2.entry-title > a");

            for (Element d : data) {
                links.add(d.attr("href"));
            }

            for(String l : links){
                String url1 = l;

                Document doc1 = Jsoup.connect(url1).get();

                Elements data1 = doc1.select("div.detail-product");

                for (Element d : data1) {
                agenciasWayProductos.add(new PojoProductos(d.select("img.wp-post-image").attr("src"),
                        "",
                        d.select("div.detail-product__content > div.detail-product__header > h1").text(),
                        d.select("div.block-item__content > h6").text(),
                        l, 2));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return agenciasWayProductos;
    }

    public Set<PojoProductos> datosMax(String product) {
        Set<PojoProductos> MaxProductos = new HashSet<>();

        try {
            String url = "https://www.max.com.gt/catalogsearch/result/?q=" + product;

            Document doc = Jsoup.connect(url).get();

            Elements data = doc.getElementsByClass("product-item");

            for (Element d : data) {

                MaxProductos.add(new PojoProductos(d.getElementsByClass("product-image-photo").attr("src"),
                        "",
                        d.getElementsByClass("product-item-link").text(),
                        d.getElementsByClass("price").text(),
                        d.getElementsByClass("product-item-photo").attr("href"), 3));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return MaxProductos;
    }

    public Set<PojoProductos> datosTecnoFacil(String product) {
        Set<PojoProductos> tecnoFacilProductos = new HashSet<>();

        try {
            String url = "https://www.tecnofacil.com.gt/catalogsearch/result/?q=" + product;

            Document doc = Jsoup.connect(url).get();

            Elements data = doc.getElementsByClass("item-inner");

            String precio = "";
            for (Element d : data) {
                //System.out.println(d.select("div.price-box > p > span.price").hasText());
                if (!d.select("div.price-box > p > span.price").hasText()) {
                    precio = d.select("div.price-box > span.regular-price > span.price").text();
                } else {
                    precio = d.select("p.special-price > span.price").text();
                }
                tecnoFacilProductos.add(new PojoProductos(d.select("span.product-image > img").attr("src"),
                        "",
                        d.select("div.content-box > h2 > a").text(),
                        precio,
                        d.select("a.product-image").attr("href"), 4));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return tecnoFacilProductos;
    }

    public Set<PojoProductos> ordenar(Set<PojoProductos> producto, String precio1, String precio2) {
        Set<PojoProductos> set = new HashSet<>();
        String formaDeFiltrar = "";
        if(!precio1.isEmpty() && precio2.isEmpty()){
            formaDeFiltrar = "Mayores a";
            Log.v("Entra", "Entró aquí");
        }else if(precio1.isEmpty() && !precio2.isEmpty()){
            formaDeFiltrar = "Menores a";
        }else{
            formaDeFiltrar = "Rango";
        }


      switch (formaDeFiltrar){
            case "Mayores a":
                set = producto.stream().filter(val -> val.getPrecio() >= Integer.parseInt(precio1)).collect(Collectors.toSet());
                Log.v("Entra", "Entró aquí2");
                break;

            case "Menores a":
                set = producto.stream().filter(val -> val.getPrecio() <= Integer.parseInt(precio2)).collect(Collectors.toSet());
                break;

            case "Rango":
                set = producto.stream()
                        .filter(val -> val.getPrecio() >= Integer.parseInt(precio1) && val.getPrecio() <= Integer.parseInt(precio2))
                        .collect(Collectors.toSet());
                break;
        }

        return set;
    }
}