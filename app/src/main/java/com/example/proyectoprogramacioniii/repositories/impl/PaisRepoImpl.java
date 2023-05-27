package com.example.proyectoprogramacioniii.repositories.impl;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Pais;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Usuario;
import com.example.proyectoprogramacioniii.RoomDatabase.PaisDao;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RetrofiClientFlight;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RetrofitCLient;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosPais;
import com.example.proyectoprogramacioniii.repositories.PaisRepo;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaisRepoImpl implements PaisRepo {

    private PaisDao paisDao;
    private LiveData<List<Pais>> allPaises;

    @Inject
    public PaisRepoImpl(PaisDao dao) {
        paisDao = dao;
        allPaises = paisDao.obtenerPaises();
    }

    @Override
    public void deletePaises() {
        paisDao.borrarPaises();
    }

    @Override
    public LiveData<List<Pais>> getAllPaises() {

        serviciosPais servicios = RetrofiClientFlight.getRetrofitInstance().create(serviciosPais.class);

        Call<List<Pais>> call = servicios.allPaises();

        call.enqueue(new Callback<List<Pais>>() {
            @Override
            public void onResponse(Call<List<Pais>> call, Response<List<Pais>> response) {
                paisDao.insertAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Pais>> call, Throwable t) {

            }

        });
        return allPaises;
    }

    @Override
    public void insertarPais(int id, String nombre) {
        Pais pais = new Pais(id, nombre);
        paisDao.insertarPais(pais);
    }

    @Override
    public void eliminarPais(int id) {
        paisDao.borrarPais(id);
    }
}
