package com.example.proyectoprogramacioniii.repositories.impl;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Privilegio;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Rol;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.RolAcceso;
import com.example.proyectoprogramacioniii.RoomDatabase.RolAccesoDao;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RetrofiClientFlight;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosPrivilegio;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosRolAcceso;
import com.example.proyectoprogramacioniii.repositories.RolAccesoRepo;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RolAccesoRepoImpl implements RolAccesoRepo {

    private RolAccesoDao rolAccesoDao;
    private LiveData<List<RolAcceso>> allRolesAcceso;

    @Inject
    public RolAccesoRepoImpl(RolAccesoDao dao) {
        rolAccesoDao = dao;
        allRolesAcceso = rolAccesoDao.obtenerAllRolesAcceso();
    }

    @Override
    public void insertarRolAcceso(int id_rol, int id_privilegio) {
        RolAcceso rolAcceso = new RolAcceso(id_rol, id_privilegio);
        rolAccesoDao.insertarRolAcceso(rolAcceso);
    }

    @Override
    public void eliminarRolAcceso(int id_rol) {
        rolAccesoDao.borrarRolAcceso(id_rol);

    }

    @Override
    public LiveData<List<RolAcceso>> getAllRolesAcceso() {

        serviciosRolAcceso servicios = RetrofiClientFlight.getRetrofitInstance().create(serviciosRolAcceso.class);

        Call<List<RolAcceso>> call = servicios.allRolesAcceso();

        call.enqueue(new Callback<List<RolAcceso>>() {
            @Override
            public void onResponse(Call<List<RolAcceso>> call, Response<List<RolAcceso>> response) {
                rolAccesoDao.insertAll(response.body());

            }

            @Override
            public void onFailure(Call<List<RolAcceso>> call, Throwable t) {

            }
        });

        return allRolesAcceso;
    }
}
