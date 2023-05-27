package com.example.proyectoprogramacioniii.repositories;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Rol;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.RolConPrivilegio;

import java.util.List;

public interface RolRepo {
    void insertarRol(int id, String nombre);
    LiveData<List<RolConPrivilegio>> obtenerRoles(int id_rol);
    void eliminarRol(int id_rol);

    LiveData<List<Rol>> obtenerRoles2();
}
