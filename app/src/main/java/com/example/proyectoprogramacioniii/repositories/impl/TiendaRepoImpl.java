package com.example.proyectoprogramacioniii.repositories.impl;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Tienda;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Usuario;
import com.example.proyectoprogramacioniii.RoomDatabase.TiendaDao;
import com.example.proyectoprogramacioniii.RoomDatabase.UsuarioDao;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RetrofiClientFlight;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosTienda;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosUsuarios;
import com.example.proyectoprogramacioniii.repositories.TiendaRepo;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TiendaRepoImpl implements TiendaRepo {

    private TiendaDao tiendaDao;
    private LiveData<List<Tienda>> allTiendas;

    @Inject
    public TiendaRepoImpl(TiendaDao dao) {
        tiendaDao = dao;
        allTiendas = tiendaDao.obtenerTiendas();
    }

    @Override
    public LiveData<List<Tienda>> getAllTiendas() {

        serviciosTienda servicios = RetrofiClientFlight.getRetrofitInstance().create(serviciosTienda.class);
        Call<List<Tienda>> call = servicios.allTiendas();

        call.enqueue(new Callback<List<Tienda>>() {
            @Override
            public void onResponse(Call<List<Tienda>> call, Response<List<Tienda>> response) {
                tiendaDao.insertAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Tienda>> call, Throwable t) {

            }
        });

        return allTiendas;
    }
}
