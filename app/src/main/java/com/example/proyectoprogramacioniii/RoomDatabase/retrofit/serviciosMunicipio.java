package com.example.proyectoprogramacioniii.RoomDatabase.retrofit;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Departamento;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Municipio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface serviciosMunicipio {

    @GET("municipios")
    Call<List<Municipio>> allMunicipios();
}
