package com.example.proyectoprogramacioniii.repositories;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Departamento;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Pais;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.PaisConDepartamento;

import java.util.List;

public interface DepartamentoRepo {
    void insertarDepartamento(int id, String nombre, int id_pais);

    LiveData<List<PaisConDepartamento>> obtenerDepartamentosdePais(int id_pais);

    void eliminarDepartamento(int id);

    LiveData<List<Departamento>> obtenerDepartamentos(int id);

    LiveData<List<Departamento>> getAllDepartamentos();
}
