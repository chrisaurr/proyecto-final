package com.example.proyectoprogramacioniii.repositories.impl;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Privilegio;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Rol;
import com.example.proyectoprogramacioniii.RoomDatabase.PrivilegioDao;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.PrivilegioConRol;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RetrofiClientFlight;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosPrivilegio;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosRol;
import com.example.proyectoprogramacioniii.repositories.PrivilegioRepo;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivilegioRepoImpl implements PrivilegioRepo {

    private PrivilegioDao privilegioDao;
    private LiveData<List<Privilegio>> allPrivilegios;

    @Inject
    public PrivilegioRepoImpl(PrivilegioDao dao) {
        privilegioDao = dao;
        allPrivilegios = privilegioDao.obtenerAllPrivilegios();
    }

    @Override
    public void insertarPrivilegio(int id, String nombre) {
        Privilegio privilegio = new Privilegio(id, nombre);
        privilegioDao.insertarPrivilegio(privilegio);
    }

    @Override
    public LiveData<List<PrivilegioConRol>> obtenerPrivilegios(int id_privilegio) {
        return privilegioDao.obtenerRolesConPrivilegio(id_privilegio);
    }

    @Override
    public void eliminarPrivilegio(int id_privilegio) {
        privilegioDao.borrarPrivilegio(id_privilegio);
    }

    @Override
    public LiveData<List<Privilegio>> getAllPrivilegios() {

        serviciosPrivilegio servicios = RetrofiClientFlight.getRetrofitInstance().create(serviciosPrivilegio.class);

        Call<List<Privilegio>> call = servicios.allPrivilegios();

        call.enqueue(new Callback<List<Privilegio>>() {
            @Override
            public void onResponse(Call<List<Privilegio>> call, Response<List<Privilegio>> response) {
                privilegioDao.insertAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Privilegio>> call, Throwable t) {

            }
        });

        return allPrivilegios;
    }
}
