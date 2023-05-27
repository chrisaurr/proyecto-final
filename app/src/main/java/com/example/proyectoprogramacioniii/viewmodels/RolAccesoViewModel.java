package com.example.proyectoprogramacioniii.viewmodels;

import android.arch.lifecycle.ViewModel;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.RolAcceso;
import com.example.proyectoprogramacioniii.repositories.RolAccesoRepo;

import java.util.List;

import javax.inject.Inject;

public class RolAccesoViewModel extends ViewModel {
    private RolAccesoRepo rolAccesoRepo;
    private LiveData<List<RolAcceso>> obtenerRolAccesos;

    @Inject
    public RolAccesoViewModel(RolAccesoRepo rolAccesoRepo) {
        this.rolAccesoRepo = rolAccesoRepo;
        this.obtenerRolAccesos = rolAccesoRepo.getAllRolesAcceso();
    }

    public void agregarRolAcceso(int id_rol, int id_privilegio){
        rolAccesoRepo.insertarRolAcceso(id_rol, id_privilegio);
    }

    public void deleteRolAcceso(int id_rol){
        rolAccesoRepo.eliminarRolAcceso(id_rol);
    }
}
