package com.example.proyectoprogramacioniii.repositories;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Pais;

import java.util.List;

public interface PaisRepo {
    void deletePaises();
    LiveData<List<Pais>> getAllPaises();
    void insertarPais(int id, String nombre);
    void eliminarPais(int id);
}
