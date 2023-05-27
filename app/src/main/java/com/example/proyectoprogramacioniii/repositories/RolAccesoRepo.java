package com.example.proyectoprogramacioniii.repositories;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Pais;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.RolAcceso;

import java.util.List;

public interface RolAccesoRepo {
    void insertarRolAcceso(int id_rol, int id_privilegio);
    void eliminarRolAcceso(int id_rol);

    LiveData<List<RolAcceso>> getAllRolesAcceso();
}
