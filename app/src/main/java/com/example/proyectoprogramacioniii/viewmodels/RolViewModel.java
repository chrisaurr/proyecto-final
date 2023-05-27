package com.example.proyectoprogramacioniii.viewmodels;

import android.arch.lifecycle.ViewModel;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Rol;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.RolConPrivilegio;
import com.example.proyectoprogramacioniii.repositories.RolRepo;

import java.util.List;

import javax.inject.Inject;

public class RolViewModel extends ViewModel {
    private RolRepo rolRepo;
    private LiveData<List<Rol>> obtenerRoles;

    @Inject
    public RolViewModel(RolRepo rolRepo) {
        this.rolRepo = rolRepo;
        this.obtenerRoles = rolRepo.obtenerRoles2();
    }

    public void agregarRol(int id, String nombre){
        rolRepo.insertarRol(id, nombre);
    }

    public LiveData<List<RolConPrivilegio>> obtenerPrivilegiosConRol(int id_rol){
        return rolRepo.obtenerRoles(id_rol);
    }

    public void deleteRol(int id_rol){
        rolRepo.eliminarRol(id_rol);
    }

    public LiveData<List<Rol>> obtenerRol(){
        return rolRepo.obtenerRoles2();
    }
}
