package com.example.proyectoprogramacioniii.repositories.impl;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.CaracteristicasDao;
import com.example.proyectoprogramacioniii.RoomDatabase.DepartamentoDao;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Caracteristica;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Departamento;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Usuario;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RetrofiClientFlight;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosFavorito;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosUsuarios;
import com.example.proyectoprogramacioniii.repositories.CaracteristicasRepo;
import com.example.proyectoprogramacioniii.utils.FavoritosClass;
import com.example.proyectoprogramacioniii.utils.PojoProductos;
import com.google.gson.internal.bind.util.ISO8601Utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaracteristicasRepoImpl implements CaracteristicasRepo {

    private CaracteristicasDao caracteristicasDao;
    FavoritosClass element = new FavoritosClass();
    List<PojoProductos> list;
   private LiveData<List<Caracteristica>> allCaracteristicas;
   private List<Caracteristica> allCaracteristicas1;

    @Inject
    public CaracteristicasRepoImpl(CaracteristicasDao dao) {
        caracteristicasDao = dao;
        allCaracteristicas = caracteristicasDao.obtenerCaracteristicas(1);
    }


    @Override
    public LiveData<List<Caracteristica>> obtenerCaracteristicas(int id, int page) {
        serviciosFavorito servicios = RetrofiClientFlight.getRetrofitInstance().create(serviciosFavorito.class);
        Call<List<FavoritosClass>> call = servicios.checkProductPage(id, page);

        call.enqueue(new Callback<List<FavoritosClass>>() {
            @Override
            public void onResponse(Call<List<FavoritosClass>> call, Response<List<FavoritosClass>> response) {
                List<FavoritosClass> lista = response.body();
                List<Caracteristica> listaFinal = new ArrayList<>();

                new java.util.Timer().schedule(
                        new java.util.TimerTask() {

                            @Override
                            public void run() {
                                for(int i = 0; i < lista.size(); i++){
                                    if(lista.get(i).id_usuarios == id){
                                        element = lista.get(i);

                                        //System.out.println(element.idTienda);
                                        Log.v("Test", String.valueOf(element.idTienda));
                                        FavoritosClass finalElement = element;

                        /*new java.util.Timer().schedule(
                                new java.util.TimerTask() {

                                    @Override
                                    public void run() {*/
                                        //listaFinal.addAll(getInfoProductLaCuracao(finalElement.requestUrl, 1));
                                        switch (finalElement.idTienda) {
                                            case 1:
                                                //listaFinal.addAll(getInfoProductLaCuracao(finalElement.requestUrl, 1));
                                                listaFinal.addAll(getInfoProductLaCuracao(finalElement.requestUrl, 1, finalElement.imageUrl));
                                                break;

                                            case 2:
                                                listaFinal.addAll(getInfoProductAgenciasWay(finalElement.requestUrl, 2, finalElement.imageUrl));
                                                break;

                                            case 3:
                                                listaFinal.addAll(getInfoProductTiendasMax(finalElement.requestUrl, 3, finalElement.imageUrl));
                                                break;

                                            case 4:
                                                listaFinal.addAll(getInfoProductTecnoFacil(finalElement.requestUrl, 4, finalElement.imageUrl));
                                                break;
                                        }

                                    }
                                }
                                List<Caracteristica> list1 = new ArrayList<>();
                                int i = 1;
                                for (Caracteristica c : listaFinal){
                                    list1.add(new Caracteristica(i, c.imageUrl, c.marca, c.descripcion, c.precio, c.requestUrl, c.id_tienda, id));
                                    i++;
                                }

                                if(list1.size() > 0){
                                    caracteristicasDao.borrarCaracteristicas(id);
                                }

                                list1.forEach(l -> System.out.println(l.descripcion));
                                caracteristicasDao.insertAll(list1);

                            }
                        }, 3000);


            }

            @Override
            public void onFailure(Call<List<FavoritosClass>> call, Throwable t) {

            }
        });

        return caracteristicasDao.obtenerCaracteristicas(id);
    }

    @Override
    public void insertAll(List<Caracteristica> caracteristicas) {


    }

    @Override
    public LiveData<List<Caracteristica>> getAll(int id) {
        allCaracteristicas = caracteristicasDao.obtenerCaracteristicas(id);
       return allCaracteristicas;
    }

    @Override
    public void deleteFavorites(int id) {
        caracteristicasDao.borrarCaracteristicas(id);
    }

    @Override
    public void borrarCaracteristica(String request) {
        caracteristicasDao.borrarCaracteristica(request);
    }

    public List<Caracteristica> getInfoProductLaCuracao(String requestUrl, int numTienda, String imageUrl){
        List<Caracteristica> laCuracaoList = new ArrayList<>();

        try{

            Document doc = Jsoup.connect(requestUrl).get();

            Elements data = doc.getElementsByClass("page-main");
            String precio = "";
           for(Element d : data){
               if(!d.select("span.special-price > span.price-container > span.price-wrapper > span.price").hasText()){
                   precio = d.select("div.price-box > span.price-container > span.price-wrapper > span.price").text();
               }else{
                   precio = d.select("span.special-price > span.price-container > span.price-wrapper > span.price").text();
               }

                laCuracaoList.add(new Caracteristica(imageUrl,
                        d.select("div.page-title-wrapper > h2.product-item-brand-title").text(),
                        d.select("div.page-title-wrapper > h1.page-title > span").text(),
                        precio,
                        requestUrl, numTienda));
            }

           //laCuracaoList.forEach(l -> System.out.println(l.toString()));

        } catch (IOException e){
            e.printStackTrace();
        }

        return laCuracaoList;
    }

    public List<Caracteristica> getInfoProductAgenciasWay(String requestUrl, int numTienda, String imageUrl){
        List<Caracteristica> agenciasWayList = new ArrayList<>();

        try{

            Document doc = Jsoup.connect(requestUrl).get();

            Elements data = doc.select("div.detail-product");
            for(Element d : data){

                agenciasWayList.add(new Caracteristica(imageUrl,
                        "",
                        d.select("div.detail-product__content > div.detail-product__header > h1").text(),
                        d.select("div.block-item__content > h6").text(),
                        requestUrl, numTienda));
            }

            //agenciasWayList.forEach(l -> System.out.println(l.toString()));

        } catch (IOException e){
            e.printStackTrace();
        }

        return agenciasWayList;
    }

    public List<Caracteristica> getInfoProductTiendasMax(String requestUrl, int numTienda, String imageUrl){
        List<Caracteristica> tiendasMaxList = new ArrayList<>();

        try{

            Document doc = Jsoup.connect(requestUrl).get();

            Elements data = doc.select("main.page-main");
            String precio = "";
            for(Element d : data){
//"div.product-info-main > span.special-price > span.price-container > span.price-wrapper > span.price"
                if(!d.select("span.special-price > span.price-container > span.price-wrapper > span.price").hasText()){
                    precio = d.select("div.product-info-main > div.product-info-price > div.price-box > span.price-container > span.price-wrapper > span.price").text();
                }else{
                    precio = d.select("span.special-price > span.price-container > span.price-wrapper > span.price").text();
                }

                System.out.println("es: "+ d.select("span.special-price > span.price-container > span.price-wrapper > span.price").text());
                tiendasMaxList.add(new Caracteristica(imageUrl,
                        d.selectFirst("table.data > tbody > tr > td.data").text(),
                        d.select("div.product-info-main > div.page-title-wrapper > h1.page-title > span").text(),
                        precio,
                        requestUrl, numTienda));
            }

            //tiendasMaxList.forEach(l -> System.out.println(l.toString()));

        } catch (IOException e){
            e.printStackTrace();
        }

        return tiendasMaxList;
    }

    public List<Caracteristica> getInfoProductTecnoFacil(String requestUrl, int numTienda, String imageUrl){
        List<Caracteristica> tiendasMaxList = new ArrayList<>();

        try{

            Document doc = Jsoup.connect(requestUrl).get();

            Elements data = doc.select("div.product-shop-inner");

            String precio = "";
            for(Element d : data){
                if(!d.select("div.price-box > p.special-price > span.price").hasText()){
                    precio = d.select("div.price-box > span.regular-price > span.price").text();
                }else{
                    precio = d.select("div.price-box > p.special-price > span.price").text();
                }

                tiendasMaxList.add(new Caracteristica(imageUrl,
                        "",
                        d.select("div.product-name > h1").text(),
                        precio,
                        requestUrl, numTienda));
            }

            //tiendasMaxList.forEach(l -> System.out.println(l.toString()));

        } catch (IOException e){
            e.printStackTrace();
        }

        return tiendasMaxList;
    }
}
