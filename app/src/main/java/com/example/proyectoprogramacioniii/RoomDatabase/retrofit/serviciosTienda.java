package com.example.proyectoprogramacioniii.RoomDatabase.retrofit;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.RolAcceso;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Tienda;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface serviciosTienda {
    @GET("tiendas")
    Call<List<Tienda>> allTiendas();
}
