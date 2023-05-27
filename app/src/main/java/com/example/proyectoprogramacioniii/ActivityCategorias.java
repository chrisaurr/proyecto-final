package com.example.proyectoprogramacioniii;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Caracteristica;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RetrofiClientFlight;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosFavorito;
import com.example.proyectoprogramacioniii.adapters.MyRvAdapter;
import com.example.proyectoprogramacioniii.adapters.ProductsRvAdapter;
import com.example.proyectoprogramacioniii.databinding.ActivityCategoriasBinding;
import com.example.proyectoprogramacioniii.utils.FilterList;
import com.example.proyectoprogramacioniii.utils.PojoProductos;
import com.example.proyectoprogramacioniii.utils.FavoritosClass;

//import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCategorias extends AppCompatActivity {

    ArrayList<String> dataSource;
    ArrayList<PojoProductos> dataSourceProducts;
    LinearLayoutManager linearLayoutManager;
    MyRvAdapter myRvAdapter;
    ProductsRvAdapter productsRvAdapter;

    String urlGlobal;

    List<FavoritosClass> lista;

    boolean exist = false;

    int id;

    ActivityCategoriasBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoriasBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ArrayList<String> lista = (ArrayList<String>) getIntent().getSerializableExtra("miLista");
        ArrayList<String> lista2 = (ArrayList<String>) getIntent().getSerializableExtra("miLista2");
        String tienda = getIntent().getStringExtra("tienda");
        id = getIntent().getIntExtra("id", 0);


        String[] myArray = {"Normal", "Menor a mayor", "Mayor a menor"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, myArray);
        binding.filtrosSp.setAdapter(adapter);


        dataSource = new ArrayList<>();

        dataSource = lista;


        linearLayoutManager = new LinearLayoutManager(ActivityCategorias.this, LinearLayoutManager.HORIZONTAL, false);
        myRvAdapter = new MyRvAdapter(dataSource, new MyRvAdapter.ItemClickListener() {
            @Override
            public void onItemClick(String category, int i) {

                Toast.makeText(getApplicationContext(), category, Toast.LENGTH_SHORT).show();

                switch (tienda){
                    case "laCuracao":
                        binding.noPaginasSp.setAdapter(null);
                        mostrarCardView(lista2.get(i), tienda);
                        urlGlobal = lista2.get(i);
                        binding.filtrosSp.setSelection(0);

                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                llenar(lista2.get(i), dataSourceProducts.get(0).link, tienda);
                            }
                        },5000);
                        break;

                    case "Max":
                        binding.noPaginasSp.setAdapter(null);
                        mostrarCardView(lista2.get(i), tienda);
                        urlGlobal = lista2.get(i);
                        binding.filtrosSp.setSelection(0);

                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                llenar(lista2.get(i), "", tienda);
                            }
                        },5000);
                        break;


                    case "agenciasWay":
                        binding.noPaginasSp.setAdapter(null);
                        mostrarCardView(lista2.get(i), tienda);
                        urlGlobal = lista2.get(i);
                        binding.filtrosSp.setSelection(0);

                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                llenar(lista2.get(i), "a", tienda);
                            }
                        },5000);
                        break;

                    case "TecnoFacil":
                        binding.noPaginasSp.setAdapter(null);
                        mostrarCardView(lista2.get(i), tienda);
                        urlGlobal = lista2.get(i);
                        binding.filtrosSp.setSelection(0);

                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                llenar(lista2.get(i), "e", tienda);
                            }
                        },5000);
                        break;

                }

            }
        });
        binding.horizontalRv.setLayoutManager(linearLayoutManager);
        binding.horizontalRv.setAdapter(myRvAdapter);

        try {
            mostrarCardView(lista2.get(0), tienda);
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    llenar(lista2.get(0), "e", tienda);

                }
            },5000);
        }catch (IndexOutOfBoundsException e){
            finish();
        }



        binding.noPaginasSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                binding.shimmerView.startShimmer();
                if(position == 0){

                }else if(tienda.equals("agenciasWay")){
                    mostrarCardView(urlGlobal+"page/"+binding.noPaginasSp.getItemAtPosition(position)+"/", tienda);
                    binding.filtrosSp.setSelection(0);

                }else{
                    mostrarCardView(urlGlobal+"?p="+binding.noPaginasSp.getItemAtPosition(position), tienda);
                    binding.filtrosSp.setSelection(0);
                }

                binding.shimmerView.stopShimmer();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.v("ningun", "seleccionado");
            }
        });

        binding.filtrosSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position){

                    case 0:

                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                filtrarDatos(dataSourceProducts);
                            }
                        },3000);
                        break;

                    case 1:
                        filtrarDatos(ordenamiento(dataSourceProducts, true));
                        break;

                    case 2:
                        filtrarDatos(ordenamiento(dataSourceProducts, false));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.v("Nothing", "Nada seleccionado");
            }
        });

    }

    public List<PojoProductos> getInfoProducts(String urlR) {
        List<PojoProductos> lista = new ArrayList<>();
        try{
            String url = urlR;

            Document doc = Jsoup.connect(url).get();

            Elements data = doc.getElementsByClass("product-item");

            for(Element d : data){

                lista.add(new PojoProductos(d.getElementsByClass("product-image-photo").attr("src"),
                        d.getElementsByClass("product-item-category").text(),
                        d.getElementsByClass("product-item-link").text(),
                        d.getElementsByClass("price").text(),
                        d.getElementsByClass("product-item-photo").attr("href"), 1));
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return lista;
    }

    public List<PojoProductos> getInfoProductsMax(String urlR) {
        List<PojoProductos> lista = new ArrayList<>();
        try{
            String url = urlR;

            Document doc = Jsoup.connect(url).get();

            Elements data = doc.getElementsByClass("product-item");

            for(Element d : data){

                lista.add(new PojoProductos(d.getElementsByClass("product-image-photo").attr("src"),
                        "",
                        d.getElementsByClass("product-item-link").text(),
                        d.getElementsByClass("price").text(),
                        d.getElementsByClass("product-item-photo").attr("href"), 3));
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return lista;
    }

    public List<PojoProductos> getInfoProductsAgenciasWay(String urlR) {
        List<PojoProductos> lista = new ArrayList<>();

        try{
            String url = urlR;

            Document doc = Jsoup.connect(url).get();

            Elements data = doc.getElementsByClass("block-item");

            for(Element d : data){
                lista.add(new PojoProductos(d.select("div.block-item__thumb > a > img").attr("data-lazy-src"),
                        d.select("div.block-item__header > h2 > a").text(),
                        d.select("div.block-item__header > h4").text(),
                        d.select("div.block-item__content > h4").text(),
                        d.select("div.block-item__thumb > a").attr("href"), 2));
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return lista;
    }

    public List<PojoProductos> getInfoProductsTecnoFacil(String urlR) {
        List<PojoProductos> lista = new ArrayList<>();

        try{
            String url = urlR;

            Document doc = Jsoup.connect(url).get();

            Elements data = doc.getElementsByClass("item-inner");

            String precio = "";
            for(Element d : data){
                //System.out.println(d.select("div.price-box > p > span.price").hasText());
               if(!d.select("div.price-box > p > span.price").hasText()){
                    precio = d.select("div.price-box > span.regular-price > span.price").text();
                }else{
                   precio = d.select("p.special-price > span.price").text();
               }
                lista.add(new PojoProductos(d.select("span.product-image > img").attr("src"),
                        "",
                        d.select("div.content-box > h2 > a").text(),
                        precio,
                        d.select("a.product-image").attr("href"), 4));
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return lista;
    }

    public void mostrarCardView(String link, String tienda){
        binding.verticalRv.setVisibility(View.GONE);

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.rv_product_item_shrimmer, binding.linear, false);
        View layout2 = inflater.inflate(R.layout.rv_product_item_shrimmer, binding.linear, false);

        binding.linear.removeAllViews();
        binding.linear.addView(layout);
        binding.linear.addView(layout2);


        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmer();
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        // your code here
                        dataSourceProducts = new ArrayList<>();

                        switch(tienda){
                            case "laCuracao":
                                dataSourceProducts = (ArrayList<PojoProductos>) getInfoProducts(link);
                                dataSourceProducts.remove(0);
                               break;

                            case "Max":
                                dataSourceProducts = (ArrayList<PojoProductos>) getInfoProductsMax(link);
                                break;

                            case "agenciasWay":
                                    dataSourceProducts = (ArrayList<PojoProductos>) getInfoProductsAgenciasWay(link);
                                break;

                            case "TecnoFacil":
                                dataSourceProducts = (ArrayList<PojoProductos>) getInfoProductsTecnoFacil(link);
                                break;
                        }

                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                // Your Code
                                linearLayoutManager = new LinearLayoutManager(ActivityCategorias.this, LinearLayoutManager.VERTICAL, false);
                                productsRvAdapter = new ProductsRvAdapter(dataSourceProducts, new ProductsRvAdapter.ItemClickListener() {
                                    @Override
                                    public void onItemClick(String link, View view, int tienda, String imageUrl) {

                                        if (view.getId() == R.id.visitarBtn){
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                                            startActivity(intent);
                                        }

                                        if (view.getId() == R.id.star){
                                            guardarDataProduct(id, link, tienda, imageUrl);
                                        }
                                    }
                                }, id);
                                binding.verticalRv.setLayoutManager(linearLayoutManager);
                                binding.verticalRv.setAdapter(productsRvAdapter);
                                binding.verticalRv.setVisibility(View.VISIBLE);
                                binding.shimmerView.stopShimmer();
                                binding.shimmerView.setVisibility(View.INVISIBLE);
                            }
                        }, 100);
                    }
                }, 3000
        );
    }


    public void filtrarDatos(ArrayList<PojoProductos> list){
        linearLayoutManager = new LinearLayoutManager(ActivityCategorias.this, LinearLayoutManager.VERTICAL, false);
        productsRvAdapter = new ProductsRvAdapter(list, new ProductsRvAdapter.ItemClickListener() {
            @Override
            public void onItemClick(String link, View view, int tienda, String imageUrl) {
                if (view.getId() == R.id.visitarBtn){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(intent);
                }

                if (view.getId() == R.id.star){
                    guardarDataProduct(id, link, tienda, imageUrl);
                }
            }
        }, id);
        binding.verticalRv.setLayoutManager(linearLayoutManager);
        binding.verticalRv.setAdapter(productsRvAdapter);
    }

    public ArrayList<PojoProductos> ordenar() {
        Float f = null;
        List<FilterList> list1 = new ArrayList<>();
        for (PojoProductos l : dataSourceProducts) {
            String[] separator = l.precio.split(" ");
            NumberFormat nf = NumberFormat.getInstance(Locale.US);
            if (separator.length == 2) {

                try {
                    f = nf.parse(separator[1].replaceAll("Q", "")).floatValue();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            } else {

                try {
                    f = nf.parse(l.precio.replaceAll("Q", "")).floatValue();
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }

            FilterList list = new FilterList(l.url, l.marca, l.descripcion, f, l.link);
            list1.add(list);
        }

        ArrayList<PojoProductos> list2 = new ArrayList<>();


        Collections.sort(list1, (FilterList a, FilterList b) -> a.precio.compareTo(b.precio));

        return list2;

    }

    public int obtenerPaginas(String urlR, String comparator){
        int contador = 2;
        String finalLink = "";
        String link = "";

        while(!comparator.equals(finalLink)){
            String url1 = urlR+"?p="+contador;


            Document doc1 = null;
            try {
                doc1 = Jsoup.connect(url1).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Elements data2 = doc1.getElementsByClass("product-item");

            for(Element d : data2){
                //Log.v("Datos", d.getElementsByClass("product-item-photo").attr("href"));
                link = d.getElementsByClass("product-item-photo").attr("href");
                //Log.v("link", link);
                if (link != "") {
                    break; // este bucle for no sigue iterando
                }
            }

            finalLink = link;

            contador++;
            if(contador == 22){
                break;
            }
            System.out.println(contador);
        }


        return (contador - 2);
    }

    public int obtenerMax(String urlR){
        int contador = 2;
        String finalLink = "a";
        String link = "";
        boolean f = false;

        while(!finalLink.isEmpty()){
            String url1 = urlR+"?p="+contador;


            Document doc1 = null;
            try {
                doc1 = Jsoup.connect(url1).timeout(200000).get();
            } catch(HttpStatusException ex){
                break;
                //ex.getStatusCode();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Elements data2 = doc1.getElementsByClass("product-item");

            for(Element d : data2){
                //Log.v("Datos", d.getElementsByClass("product-item-photo").attr("href"));
                link = d.getElementsByClass("product-item-photo").attr("href");
                Log.v("link", link);
                f = true;

                if (link != "") {
                    break; // este bucle for no sigue iterando
                }
            }

            Log.v("link", String.valueOf(contador));



            if(!f){
                finalLink = "";
            }

            f = false;

            contador++;
            if(contador == 22){
                break;
            }

        }

        Log.v("Salir", "retornando");

        return (contador - 2);
    }

    public int obtenerPaginasAgenciasWay(String urlR){
        List<Integer> lista = new ArrayList<>();
        int noPaginas = 0;

        try{
            String url = urlR;

            Document doc = Jsoup.connect(url).get();

            Elements data = doc.select("div.paginator > ul > li");

            if(data.first() == null){
                return 1;
            }

            for(Element d : data){
                try{
                    lista.add(Integer.parseInt(d.text()));

                }catch (NumberFormatException e){

                }

            }

            noPaginas = Collections.max(lista);

        } catch (IOException e){
            e.printStackTrace();
        }


        return noPaginas;
    }

    public int obtenerPaginasTecnoFacil(String urlR){
        int contador = 2;
        String finalLink;
        finalLink = String.valueOf(contador-1);
        String link = "";

        while(finalLink.equals(String.valueOf(contador-1))){
            String url1 = urlR+"?p="+contador;

            Document doc1 = null;
            try {
                doc1 = Jsoup.connect(url1).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Elements data2 = doc1.select("div.pages > ol > li.current");

            for(Element d : data2){
                link = d.text();
            }

            finalLink = link;

            contador++;
            if(contador == 22){
                break;
            }
        }

        return (contador - 2);
    }

    int noPaginas = 0;
    public void llenar(String url, String elemento, String tienda){

        new java.util.Timer().schedule(
                new java.util.TimerTask() {

                    @Override
                    public void run() {

                        switch (tienda){
                            case "laCuracao":
                                noPaginas = obtenerPaginas(url, elemento);
                                break;

                            case "Max":
                                noPaginas = obtenerMax(url);
                                break;

                            case "agenciasWay":
                                noPaginas = obtenerPaginasAgenciasWay(url);
                                break;

                            case "TecnoFacil":
                                noPaginas = obtenerPaginasTecnoFacil(url);
                                break;

                        }

                        Log.v("aaa", String.valueOf(noPaginas));
                        ArrayList<String> lista = new ArrayList<String>();
                        lista.clear();
                        for(int i=1; i <= noPaginas; i++){
                            lista.add(String.valueOf(i));
                        }
                        String[] myArray = new String[lista.size()];
                        myArray = lista.toArray(myArray);
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, myArray);
                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable(){

                            @Override
                            public void run() {
                                binding.noPaginasSp.setAdapter(adapter);
                                Toast.makeText(getApplicationContext(), "Son iguales", Toast.LENGTH_SHORT).show();
                            }
                        }, 100);

                    }
                }, 3000);
    }

    public ArrayList<PojoProductos> ordenamiento(ArrayList<PojoProductos> lista, boolean creciente){
        Collections.sort(lista, new Comparator<PojoProductos>() {
            @Override
            public int compare(PojoProductos o1, PojoProductos o2) {
                String[] l = o1.precio.split(" ");
                String[] l1 = o2.precio.split(" ");

                String result1 = "";
                String result2 = "";
                if (l.length == 2 && l1.length == 2) {
                    result1 = l[1];
                    result2 = l1[1];
                } else {
                    result1 = l[0];
                    result2 = l1[0];
                }
                Log.v("Prueba", result1.replaceAll("Q", ""));
                Log.v("Prueba1", result2.replaceAll("Q", ""));

                NumberFormat nf = NumberFormat.getInstance(Locale.US);
                Integer val1 = null;
                Integer val2 = null;
                try {
                    val1 = nf.parse(result1.replaceAll("Q", "").replaceAll(",", "")).intValue();
                    val2 = nf.parse(result2.replaceAll("Q", "").replaceAll(",", "")).intValue();
                } catch (ParseException e) {
                    //throw new RuntimeException(e);
                }

                if(creciente){
                    return val1.compareTo(val2);
                }else{
                    return val2.compareTo(val1);
                }

                //return val1.compareTo(val2);
                //return val2.compareTo(val1);

            }
        });
        return lista;
    }

    public void guardarDataProduct(int idUser, String requestUrl, int tienda, String imageUrl){
        Log.v("Prueba", idUser + " " + requestUrl + " " + tienda);
        isExistProductInFavoriteList(new FavoritosClass(idUser, requestUrl, tienda, imageUrl));
    }

    private void postFavorite(FavoritosClass favorito){
        serviciosFavorito servicios = RetrofiClientFlight.getRetrofitInstance().create(serviciosFavorito.class);
        Call<FavoritosClass> call = servicios.registerFavorito(favorito);
        call.enqueue(new Callback<FavoritosClass>() {
            @Override
            public void onResponse(Call<FavoritosClass> call, Response<FavoritosClass> response) {
//               Log.e("Tag", "onResponse: "+response.body().getNombre());
            }

            @Override
            public void onFailure(Call<FavoritosClass> call, Throwable t) {
                Log.e("Tag", "On failure: "+t.getMessage());
            }
        });
    }

    private void isExistProductInFavoriteList(FavoritosClass favorito) {
        serviciosFavorito servicios = RetrofiClientFlight.getRetrofitInstance().create(serviciosFavorito.class);
        Call<List<FavoritosClass>> call = servicios.checkProduct(favorito.id_usuarios);
        exist = false;

        call.enqueue(new Callback<List<FavoritosClass>>() {
            @Override
            public void onResponse(Call<List<FavoritosClass>> call, Response<List<FavoritosClass>> response) {
                List<FavoritosClass> list = response.body();

                for(FavoritosClass l : list){
                    if(l.requestUrl.equals(favorito.requestUrl)){
                        exist = true;
                        break;
                    }
                }

                if(!exist){
                    postFavorite(favorito);
                }else{
                    Toast.makeText(ActivityCategorias.this, "Producto ya estaba añadido anteriormente", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<List<FavoritosClass>> call, Throwable t) {

            }
        });

    }

}

