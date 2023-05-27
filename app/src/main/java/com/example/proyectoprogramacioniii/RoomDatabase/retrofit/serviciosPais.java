package com.example.proyectoprogramacioniii.RoomDatabase.retrofit;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Pais;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Usuario;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface serviciosPais {
    @GET("paises")
    Call<List<Pais>> allPaises();
}
