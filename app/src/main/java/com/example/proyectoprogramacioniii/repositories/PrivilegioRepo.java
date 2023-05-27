package com.example.proyectoprogramacioniii.repositories;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Pais;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Privilegio;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.PrivilegioConRol;

import java.util.List;

public interface PrivilegioRepo {
    void insertarPrivilegio(int id, String nombre);
    LiveData<List<PrivilegioConRol>> obtenerPrivilegios(int id_privilegio);
    void eliminarPrivilegio(int id_privilegio);

    LiveData<List<Privilegio>> getAllPrivilegios();
}
