package com.example.proyectoprogramacioniii;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RetrofiClientFlight;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosFavorito;
import com.example.proyectoprogramacioniii.adapters.MyRvAdapter;
import com.example.proyectoprogramacioniii.adapters.ProductsRvAdapter;
import com.example.proyectoprogramacioniii.databinding.ActivityCategoriasBinding;
import com.example.proyectoprogramacioniii.databinding.ActivityResultadosBusquedaBinding;
import com.example.proyectoprogramacioniii.utils.FavoritosClass;
import com.example.proyectoprogramacioniii.utils.PaginationScrollListener;
import com.example.proyectoprogramacioniii.utils.PojoProductos;

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
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityResultadosBusqueda extends AppCompatActivity {

    LinearLayoutManager linearLayoutManager;
    ProductsRvAdapter productsRvAdapter;
    ActivityResultadosBusquedaBinding binding;
    String laCuracaoSearch;
    int id;

    boolean findProductsLaCuracao, findProductsAgenciasWay, findProductsTiendasMax, findProductsTecnoFacil;
    String minPrice = "", maxPrice = "";

    String urlPagSearchLaCuracao = "https://www.lacuracaonline.com/guatemala/catalogsearch/result/index/?p=";
    String getUrlPagSearchAgenciasWay = "https://agenciaswayonline.com/page/";
    String getUrlPagSearchMax = "https://www.max.com.gt/catalogsearch/result/index/?p=";
    String getUrlPagSearchTecnoFacil = "https://www.tecnofacil.com.gt/catalogsearch/result/index/?p=";

    int page = 1;
    boolean exist;

    public static final int PAGE_START = 1;
    private int CURRENT_PAGE = PAGE_START;
    private boolean isLoading = false, isLastPage = false;

    ArrayList<PojoProductos> lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultadosBusquedaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        id = getIntent().getIntExtra("id", 0);

        //Set<PojoProductos> lista = (Set<PojoProductos>) getIntent().getSerializableExtra("miSet");
        Bundle bundle = getIntent().getExtras();
        lista = bundle.getParcelableArrayList("miSet");
        laCuracaoSearch = bundle.getString("laCuracaoUrl");
        findProductsLaCuracao = bundle.getBoolean("LaCuracao");
        findProductsAgenciasWay = bundle.getBoolean("AgenciasWay");
        findProductsTiendasMax = bundle.getBoolean("Max");
        findProductsTecnoFacil = bundle.getBoolean("TecnoFacil");
        minPrice = bundle.getString("precioMin");
        maxPrice = bundle.getString("precioMax");


        lista.forEach(l -> System.out.println(l.numTienda));

        String[] myArray = {"Normal", "Menor a mayor", "Mayor a menor"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, myArray);
        binding.filtrosSp.setAdapter(adapter);

        binding.filtrosSp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {

                    case 0:

                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                filtrarDatos(lista);
                            }
                            }, 3000);
                        break;

                        case 1:
                            filtrarDatos(ordenamiento(lista, true));
                            break;

                            case 2:
                                filtrarDatos(ordenamiento(lista, false));
                                break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        linearLayoutManager = new LinearLayoutManager(ActivityResultadosBusqueda.this, LinearLayoutManager.VERTICAL, false);


        binding.scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if(scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    page++;
                    binding.progressBar.setVisibility(View.VISIBLE);

                    addData(page);

                }
            }
        });



    }

    public void mostrarDatos() {

        binding.verticalRv.setVisibility(View.GONE);

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.rv_product_item_shrimmer, binding.linear, false);
        View layout2 = inflater.inflate(R.layout.rv_product_item_shrimmer, binding.linear, false);

        binding.linear.removeAllViews();
        binding.linear.addView(layout);
        binding.linear.addView(layout2);


        binding.shimmerView.setVisibility(View.VISIBLE);
        binding.shimmerView.startShimmer();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Your Code
                linearLayoutManager = new LinearLayoutManager(ActivityResultadosBusqueda.this, LinearLayoutManager.VERTICAL, false);
                productsRvAdapter = new ProductsRvAdapter(lista, new ProductsRvAdapter.ItemClickListener() {
                    @Override
                    public void onItemClick(String link, View view, int tienda, String imageUrl) {
                        Log.v("Tag", "CLick");

                        if (view.getId() == R.id.visitarBtn) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                            startActivity(intent);
                        }

                        if (view.getId() == R.id.star) {
                            guardarDataProduct(id, link, tienda, imageUrl);


                        }
                    }

                });

                binding.verticalRv.setLayoutManager(linearLayoutManager);
                binding.verticalRv.setAdapter(productsRvAdapter);
                binding.verticalRv.setVisibility(View.VISIBLE);
                binding.shimmerView.stopShimmer();
                binding.shimmerView.setVisibility(View.GONE);


            }
        }, 3000);

    }

    public void filtrarDatos(ArrayList<PojoProductos> listo) {
        linearLayoutManager = new LinearLayoutManager(ActivityResultadosBusqueda.this, LinearLayoutManager.VERTICAL, false);
        productsRvAdapter = new ProductsRvAdapter(listo, new ProductsRvAdapter.ItemClickListener() {
            @Override
            public void onItemClick(String link, View view, int tienda, String imageUrl) {
                if (view.getId() == R.id.visitarBtn) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                    startActivity(intent);
                }

                if (view.getId() == R.id.star) {
                    guardarDataProduct(id, link, tienda, imageUrl);
                }
            }

        });
        binding.verticalRv.setLayoutManager(linearLayoutManager);
        binding.verticalRv.setAdapter(productsRvAdapter);
    }

    public ArrayList<PojoProductos> ordenamiento(ArrayList<PojoProductos> p, boolean creciente) {
        p.removeIf(l -> l.precio.length() < 2);
        Collections.sort(p, new Comparator<PojoProductos>() {
            @Override
            public int compare(PojoProductos o1, PojoProductos o2) {
                String[] l = o1.precio.split(" ");
                String[] l1 = o2.precio.split(" ");

                //Log.v("PrimerIf", o1.precio.split(" ") + o2.precio.split(" "));
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
                    //throw new RuntimeException(e);
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

    public void addData(int page){
        Set<PojoProductos> data = new HashSet<>();
        data.clear();


        new java.util.Timer().schedule(
                new java.util.TimerTask() {


                    @Override
                    public void run() {
                        System.out.println(page);
                        if(thereAreNextPage(urlPagSearchLaCuracao + page +"&q=" + laCuracaoSearch) && findProductsLaCuracao){
                            data.addAll(getInfoProducts(urlPagSearchLaCuracao + page +"&q=" + laCuracaoSearch));
                        }

                        if(thereAreNextPageAgenciasWay(getUrlPagSearchAgenciasWay + page + "/?s=" + laCuracaoSearch) && findProductsAgenciasWay){
                            data.addAll(datosAgenciasWay(getUrlPagSearchAgenciasWay + page + "/?s=" + laCuracaoSearch));
                        }

                        if(thereAreNextPageMax(getUrlPagSearchMax + page +"&q=" + laCuracaoSearch) && findProductsTiendasMax){
                            data.addAll(getInfoProductsMax(getUrlPagSearchMax + page +"&q=" + laCuracaoSearch));
                        }

                        if(thereAreNextPageTecnoFacil(getUrlPagSearchTecnoFacil + page +"&q=" + laCuracaoSearch) && findProductsTecnoFacil){
                            data.addAll(getInfoProductsTecnoFacil(getUrlPagSearchTecnoFacil + page +"&q=" + laCuracaoSearch));
                        }
                        Set<PojoProductos> dataOrdered = new HashSet<>();

                        if(minPrice.isEmpty() && maxPrice.isEmpty()){
                            lista.addAll(data);
                        }else{
                            dataOrdered.addAll(ordenar(data, minPrice, maxPrice));
                            lista.addAll(dataOrdered);
                        }


                        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                binding.progressBar.setVisibility(View.GONE);
                                linearLayoutManager = new LinearLayoutManager(ActivityResultadosBusqueda.this, LinearLayoutManager.VERTICAL, false);
                                productsRvAdapter = new ProductsRvAdapter(lista, new ProductsRvAdapter.ItemClickListener() {
                                    @Override
                                    public void onItemClick(String link, View view, int tienda, String imageUrl) {

                                        if (view.getId() == R.id.visitarBtn) {
                                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                                            startActivity(intent);
                                        }

                                        if (view.getId() == R.id.star) {
                                            guardarDataProduct(id, link, tienda, imageUrl);

                                        }

                                    }

                                });

                                binding.verticalRv.setLayoutManager(linearLayoutManager);
                                binding.verticalRv.setAdapter(productsRvAdapter);
                            }
                        }, 4000);
                    }
                }, 5000);



    }


    public Set<PojoProductos> getInfoProducts(String urlR) {
        Set<PojoProductos> lista = new HashSet<>();
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

        lista.removeIf(l -> l.url.isEmpty());

        return lista;
    }

    public Set<PojoProductos> datosAgenciasWay(String urlR) {
        Set<PojoProductos> agenciasWayProductos = new HashSet<>();
        Set<String> links = new HashSet<>();

        try {
            String url = urlR;

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
        agenciasWayProductos.removeIf(l -> l.url.isEmpty());

        return agenciasWayProductos;
    }

    public Set<PojoProductos> getInfoProductsMax(String urlR) {
        Set<PojoProductos> lista = new HashSet<>();
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

        lista.removeIf(l -> l.url.isEmpty());

        return lista;
    }

    public Set<PojoProductos> getInfoProductsTecnoFacil(String urlR) {
        Set<PojoProductos> lista = new HashSet<>();

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
        lista.removeIf(l -> l.url.isEmpty());

        return lista;
    }

    public boolean thereAreNextPage(String urlR) {
        try{
            String url = urlR;

            Document doc = Jsoup.connect(url).get();

            Elements data = doc.getElementsByClass("product-item");

            String dato = "";
            for(Element d : data){
                dato = d.getElementsByClass("product-item-photo").attr("href");
            }

            for(PojoProductos l : lista){
                if(l.link.contentEquals(dato)){
                    return false;
                }
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return true;
    }

    public boolean thereAreNextPageAgenciasWay(String urlR) {
        try{
            String url = urlR;

            Document doc = Jsoup.connect(url).get();

        } catch (HttpStatusException ex){
            if(ex.getStatusCode() == 404){
                return false;
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        return true;
    }

    public boolean thereAreNextPageMax(String urlR) {
        try{
            String url = urlR;

            Document doc = Jsoup.connect(url).get();

            Elements data = doc.select("ol.products > li.item > div");

            if(!data.hasClass("product-item-info")){
                return false;
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return true;
    }

    public boolean thereAreNextPageTecnoFacil(String urlR) {
        try{
            String url = urlR;

            Document doc = Jsoup.connect(url).get();

            Elements data = doc.select("div.pages > ol > li.current");

            String[] dato = data.text().split(" ");

            if(!dato[0].equals(String.valueOf(page))){
                return false;
            }

        } catch (IOException e){
            e.printStackTrace();
        }

        return true;
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
                    Toast.makeText(ActivityResultadosBusqueda.this, "Producto ya estaba añadido anteriormente", Toast.LENGTH_SHORT).show();
                }



            }

            @Override
            public void onFailure(Call<List<FavoritosClass>> call, Throwable t) {

            }
        });

    }
}