package com.example.proyectoprogramacioniii.repositories;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Usuario;

import java.util.List;

public interface UsuarioRepo {

    void insertarUsuario(String nombre, String correo, String contra, String creacion, int id_pais, int id_departamento, int id_municipio, int id_rol);
    LiveData<List<Usuario>> obtenerUsuarios();
    Usuario obtenerUsuario(String correo, String contra);
    void borrarUsuario(int id);
    void actualizarUsuario(int id, String nombre, String correo, String contra);
    void insertAll(LiveData<List<Usuario>> users);
    void deleteAll();
}
