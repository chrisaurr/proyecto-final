package com.example.proyectoprogramacioniii.viewmodels;

import android.arch.lifecycle.ViewModel;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Pais;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Usuario;
import com.example.proyectoprogramacioniii.repositories.UsuarioRepo;

import java.util.List;

import javax.inject.Inject;

public class UsuarioViewModel extends ViewModel {
    private UsuarioRepo usuarioRepo;
    private LiveData<List<Usuario>> obtenerUsuarios;

    @Inject
    public UsuarioViewModel(UsuarioRepo usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
        this.obtenerUsuarios = usuarioRepo.obtenerUsuarios();
    }

    public void agregarUsuario(String nombre, String correo, String contra, String creacion, int id_pais, int id_departamento, int id_municipio, int id_rol){
        usuarioRepo.insertarUsuario(nombre, correo, contra, creacion, id_pais, id_departamento, id_municipio, id_rol);
    }

    public LiveData<List<Usuario>> obtenerUsuarios(){
        return usuarioRepo.obtenerUsuarios();
    }

    public Usuario buscarUsuario(String correo, String contra){
        return usuarioRepo.obtenerUsuario(correo, contra);
    }

    public void deleteUsuario(int id){
        usuarioRepo.borrarUsuario(id);
    }

    public void updateUsuario(int id, String nombre, String correo, String contra){
        usuarioRepo.actualizarUsuario(id, nombre, correo, contra);
    }
}
