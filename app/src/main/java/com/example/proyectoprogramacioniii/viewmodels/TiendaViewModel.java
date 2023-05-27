package com.example.proyectoprogramacioniii.viewmodels;

import android.arch.lifecycle.ViewModel;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Municipio;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Rol;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Tienda;
import com.example.proyectoprogramacioniii.repositories.RolRepo;
import com.example.proyectoprogramacioniii.repositories.TiendaRepo;

import java.util.List;

import javax.inject.Inject;

public class TiendaViewModel extends ViewModel {
    private TiendaRepo tiendaRepo;
    private LiveData<List<Tienda>> obtenerTiendas;

    @Inject
    public TiendaViewModel(TiendaRepo tiendaRepo) {
        this.tiendaRepo = tiendaRepo;
        this.obtenerTiendas = tiendaRepo.getAllTiendas();
    }

    public LiveData<List<Tienda>> obtenerTienda(){
        return obtenerTiendas;
    }
}
