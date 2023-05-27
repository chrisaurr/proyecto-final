package com.example.proyectoprogramacioniii.viewmodels;

import android.arch.lifecycle.ViewModel;

//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.ViewModel;

//import android.arch.lifecycle.LiveData;

//import android.arch.lifecycle.LiveData;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Pais;
import com.example.proyectoprogramacioniii.repositories.PaisRepo;

import java.util.List;

import javax.inject.Inject;

public class MainActivityViewModel extends ViewModel {

    private PaisRepo paisRepo;
    private LiveData<List<Pais>> obtenerPaises;


    @Inject
    public MainActivityViewModel(PaisRepo paisRepo) {
        this.paisRepo = paisRepo;
        this.obtenerPaises = paisRepo.getAllPaises();

    }

    public void crearPais(int id, String nombre){
        paisRepo.insertarPais(id, nombre);
    }

    public LiveData<List<Pais>> obtenerPaises(){
        return obtenerPaises;
    }

    public void deletePais(int id){
        paisRepo.eliminarPais(id);
    }

    public void limpiarPaises(){
        paisRepo.deletePaises();
    }

}
