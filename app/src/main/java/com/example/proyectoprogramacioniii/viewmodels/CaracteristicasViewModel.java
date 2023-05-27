package com.example.proyectoprogramacioniii.viewmodels;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Caracteristica;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Departamento;
import com.example.proyectoprogramacioniii.repositories.CaracteristicasRepo;
import com.example.proyectoprogramacioniii.repositories.DepartamentoRepo;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CaracteristicasViewModel {

    private CaracteristicasRepo caracteristicasRepo;
    private LiveData<List<Departamento>> obtenerDepartamento;

    @Inject
    public CaracteristicasViewModel(CaracteristicasRepo departamentoRepo) {
        this.caracteristicasRepo = departamentoRepo;
    }

    public LiveData<List<Caracteristica>> obtenerCaracteristicas(int id, int page){
        return caracteristicasRepo.obtenerCaracteristicas(id, page);
    }

    public LiveData<List<Caracteristica>> obtener(int id){
        return caracteristicasRepo.getAll(id);
    }

    public void eliminarFavorito(String request){
        caracteristicasRepo.borrarCaracteristica(request);
    }

    public void eliminarFavoritosUsuario(int id){
        caracteristicasRepo.deleteFavorites(id);
    }
}
