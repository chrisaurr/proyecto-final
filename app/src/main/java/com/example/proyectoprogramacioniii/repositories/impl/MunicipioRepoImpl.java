package com.example.proyectoprogramacioniii.repositories.impl;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.DepartamentoDao;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Departamento;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Municipio;
import com.example.proyectoprogramacioniii.RoomDatabase.MunicipioDao;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.DepartamentoConMunicipio;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RetrofiClientFlight;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosDepartamentos;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosMunicipio;
import com.example.proyectoprogramacioniii.repositories.MunicipioRepo;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MunicipioRepoImpl implements MunicipioRepo {

    private MunicipioDao municipioDao;
    private LiveData<List<Municipio>> allMunicipios;

    @Inject
    public MunicipioRepoImpl(MunicipioDao dao) {
        municipioDao = dao;
        allMunicipios = municipioDao.obtenerAllMunicipios();
    }

    @Override
    public void insertarMunicipio(int id, String nombre, int id_departamento) {
        Municipio municipio = new Municipio(id, nombre, id_departamento);
        municipioDao.insertarMunicipio(municipio);
    }

    @Override
    public LiveData<List<DepartamentoConMunicipio>> obtenerMunicipiosdeDepartamento(int id_departamento) {
        return municipioDao.obtenerMunicipiosConDepartamento(id_departamento);
    }

    @Override
    public void eliminarMunicipio(int id) {
        municipioDao.borrarMunicipio(id);
    }

    @Override
    public LiveData<List<Municipio>> obtenerMunicipios(int id) {
        return municipioDao.obtenerMunicipios(id);
    }

    @Override
    public LiveData<List<Municipio>> getAllMunicipios() {

        serviciosMunicipio servicios = RetrofiClientFlight.getRetrofitInstance().create(serviciosMunicipio.class);

        Call<List<Municipio>> call = servicios.allMunicipios();

        call.enqueue(new Callback<List<Municipio>>() {
            @Override
            public void onResponse(Call<List<Municipio>> call, Response<List<Municipio>> response) {
                municipioDao.insertAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Municipio>> call, Throwable t) {

            }
        });

        return allMunicipios;
    }
}
