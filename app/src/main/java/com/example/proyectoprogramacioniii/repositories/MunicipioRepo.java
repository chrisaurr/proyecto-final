package com.example.proyectoprogramacioniii.repositories;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Departamento;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Municipio;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.DepartamentoConMunicipio;

import java.util.List;

public interface MunicipioRepo {
    void insertarMunicipio(int id, String nombre, int id_departamento);
    LiveData<List<DepartamentoConMunicipio>> obtenerMunicipiosdeDepartamento(int id_departamento);
    void eliminarMunicipio(int id);

    LiveData<List<Municipio>> obtenerMunicipios(int id);

    LiveData<List<Municipio>> getAllMunicipios();
}
