package com.example.proyectoprogramacioniii.repositories.impl;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.DaoUsuario;
import com.example.proyectoprogramacioniii.RoomDatabase.DepartamentoDao;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Departamento;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Pais;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.PaisConDepartamento;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RetrofiClientFlight;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosDepartamentos;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosPais;
import com.example.proyectoprogramacioniii.repositories.DepartamentoRepo;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepartamentoRepoImpl implements DepartamentoRepo {

    private DepartamentoDao departamentoDao;
    private LiveData<List<Departamento>> allDepartamentos;

    @Inject
    public DepartamentoRepoImpl(DepartamentoDao dao) {
        departamentoDao = dao;
        allDepartamentos = departamentoDao.obtenerAllDepartamentos();
    }

    @Override
    public void insertarDepartamento(int id, String nombre, int id_pais) {
        Departamento departamento = new Departamento(id, nombre,id_pais);
        departamentoDao.insertarDepartamento(departamento);
    }

    @Override
    public LiveData<List<PaisConDepartamento>> obtenerDepartamentosdePais(int id_pais) {
        return departamentoDao.obtenerDepartamentosConPais(id_pais);
    }

    @Override
    public void eliminarDepartamento(int id) {
        departamentoDao.borrarDepartamento(id);
    }

    @Override
    public LiveData<List<Departamento>> obtenerDepartamentos(int id) {
        return departamentoDao.obtenerDepartamentos(id);
    }

    @Override
    public LiveData<List<Departamento>> getAllDepartamentos() {

        serviciosDepartamentos servicios = RetrofiClientFlight.getRetrofitInstance().create(serviciosDepartamentos.class);

        Call<List<Departamento>> call = servicios.allDepartamentos();

        call.enqueue(new Callback<List<Departamento>>() {
            @Override
            public void onResponse(Call<List<Departamento>> call, Response<List<Departamento>> response) {
                departamentoDao.insertAll(response.body());
            }

            @Override
            public void onFailure(Call<List<Departamento>> call, Throwable t) {

            }
        });
        return allDepartamentos;
    }
}
