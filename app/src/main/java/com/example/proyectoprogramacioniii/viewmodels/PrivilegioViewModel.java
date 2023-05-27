package com.example.proyectoprogramacioniii.viewmodels;

import android.arch.lifecycle.ViewModel;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Privilegio;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.PrivilegioConRol;
import com.example.proyectoprogramacioniii.repositories.PrivilegioRepo;

import java.util.List;

import javax.inject.Inject;

public class PrivilegioViewModel extends ViewModel {
    private PrivilegioRepo privilegioRepo;
    private LiveData<List<Privilegio>> obtenerPrivilegios;

    @Inject
    public PrivilegioViewModel(PrivilegioRepo privilegioRepo) {
        this.privilegioRepo = privilegioRepo;
        this.obtenerPrivilegios = privilegioRepo.getAllPrivilegios();
    }

    public void agregarPrivilegio(int id, String nombre){
        privilegioRepo.insertarPrivilegio(id, nombre);
    }

    public LiveData<List<PrivilegioConRol>> obtenerRolesConPrivilegio(int id_privilegio){
        return privilegioRepo.obtenerPrivilegios(id_privilegio);
    }

    public void deletePrivilegio(int id_privilegio){
        privilegioRepo.eliminarPrivilegio(id_privilegio);
    }
}
