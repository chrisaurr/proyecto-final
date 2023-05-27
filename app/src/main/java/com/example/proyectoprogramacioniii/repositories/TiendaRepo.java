package com.example.proyectoprogramacioniii.repositories;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Pais;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Tienda;

import java.util.List;

public interface TiendaRepo {
    LiveData<List<Tienda>> getAllTiendas();
}
