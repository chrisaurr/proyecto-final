package com.example.proyectoprogramacioniii.RoomDatabase.retrofit;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Municipio;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.RolAcceso;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface serviciosRolAcceso {

    @GET("rolesacceso")
    Call<List<RolAcceso>> allRolesAcceso();
}
