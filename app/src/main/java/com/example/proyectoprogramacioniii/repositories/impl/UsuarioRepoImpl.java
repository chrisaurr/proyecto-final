package com.example.proyectoprogramacioniii.repositories.impl;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.proyectoprogramacioniii.RoomDatabase.AppDb;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Rol;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Usuario;
import com.example.proyectoprogramacioniii.RoomDatabase.UsuarioDao;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RespuestaUsuario;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RetrofiClientFlight;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RetrofitCLient;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosUsuario;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.serviciosUsuarios;
import com.example.proyectoprogramacioniii.repositories.UsuarioRepo;
import com.example.proyectoprogramacioniii.utils.CommonMethod;

import java.security.AccessController;
import java.util.List;
import java.util.concurrent.Future;

import javax.inject.Inject;

import dagger.hilt.android.qualifiers.ApplicationContext;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioRepoImpl implements UsuarioRepo {

    private UsuarioDao usuarioDao;
    private LiveData<List<Usuario>> allUsuarios;

    @Inject
    public UsuarioRepoImpl(UsuarioDao dao) {
        usuarioDao = dao;
        allUsuarios = usuarioDao.obtenerUsuarios();
    }

    @Override
    public void insertarUsuario(String nombre, String correo, String contra, String creacion, int id_pais, int id_departamento, int id_municipio, int id_rol) {
        Usuario user = new Usuario(nombre, correo, contra, creacion, id_pais, id_departamento, id_municipio, id_rol, false);
        usuarioDao.insertarUsuario(user);
    }

    @Override
    public LiveData<List<Usuario>> obtenerUsuarios() {

            serviciosUsuarios servicios = RetrofiClientFlight.getRetrofitInstance().create(serviciosUsuarios.class);
            Call<List<Usuario>> call = servicios.allUsers();

            call.enqueue(new Callback<List<Usuario>>() {
                @Override
                public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                    deleteAll();
                    usuarioDao.insertAll(response.body());

                }

                @Override
                public void onFailure(Call<List<Usuario>> call, Throwable t) {

                }
            });

        return allUsuarios;
    }

    @Override
    public Usuario obtenerUsuario(String correo, String contra) {
        return usuarioDao.obtenerUsuario(correo, contra);
    }

    @Override
    public void borrarUsuario(int id) {
        usuarioDao.borrarUsuario(id);
    }

    @Override
    public void actualizarUsuario(int id, String nombre, String correo, String contra) {

    }

    @Override
    public void insertAll(LiveData<List<Usuario>> users) {

    }

    @Override
    public void deleteAll() {
        usuarioDao.borrarUsuarios();
    }
}
