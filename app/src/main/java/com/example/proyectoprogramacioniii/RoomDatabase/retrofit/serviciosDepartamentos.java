package com.example.proyectoprogramacioniii.RoomDatabase.retrofit;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Departamento;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Pais;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface serviciosDepartamentos {

    @GET("departamentos")
    Call<List<Departamento>> allDepartamentos();
}
