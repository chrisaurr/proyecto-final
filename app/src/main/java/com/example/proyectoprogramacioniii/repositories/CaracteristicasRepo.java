package com.example.proyectoprogramacioniii.repositories;

import androidx.lifecycle.LiveData;


import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Caracteristica;

import java.util.ArrayList;
import java.util.List;

public interface CaracteristicasRepo {

    LiveData<List<Caracteristica>> obtenerCaracteristicas(int id, int page);

    void insertAll(List<Caracteristica> caracteristicas);

    public void borrarCaracteristica(String request);

    public LiveData<List<Caracteristica>> getAll(int id);

    public void deleteFavorites(int id);
}
