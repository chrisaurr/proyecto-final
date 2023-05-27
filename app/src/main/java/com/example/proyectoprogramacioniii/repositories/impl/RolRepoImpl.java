package com.example.proyectoprogramacioniii.repositories.impl;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Pais;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Rol;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.RolConPrivilegio;
import com.example.proyectoprogramacioniii.RoomDatabase.RolDao;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RetrofiClientFlight;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosPais;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosRol;
import com.example.proyectoprogramacioniii.repositories.RolRepo;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RolRepoImpl implements RolRepo {

    private RolDao rolDao;

    private LiveData<List<Rol>> allRoles;

    @Inject
    public RolRepoImpl(RolDao dao) {
        rolDao = dao;
        allRoles = rolDao.obtenerRoles();
    }

    @Override
    public void insertarRol(int id, String nombre) {
        Rol rol = new Rol(id, nombre);
        rolDao.insertarRol(rol);
    }

    @Override
    public LiveData<List<RolConPrivilegio>> obtenerRoles(int id_rol) {
        return rolDao.obtenerPrivilegiosConRol(id_rol);
    }

    @Override
    public void eliminarRol(int id_rol) {
        rolDao.borrarRol(id_rol);
    }

    @Override
    public LiveData<List<Rol>> obtenerRoles2() {
        serviciosRol servicios = RetrofiClientFlight.getRetrofitInstance().create(serviciosRol.class);

        Call<List<Rol>> call = servicios.allRoles();

        call.enqueue(new Callback<List<Rol>>() {
            @Override
            public void onResponse(Call<List<Rol>> call, Response<List<Rol>> response) {
                rolDao.insertAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Rol>> call, Throwable t) {

            }
        });
        return allRoles;
    }
}
