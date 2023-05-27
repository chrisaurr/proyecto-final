package com.example.proyectoprogramacioniii.RoomDatabase.retrofit;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Municipio;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Rol;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface serviciosRol {

    @GET("roles")
    Call<List<Rol>> allRoles();
}
