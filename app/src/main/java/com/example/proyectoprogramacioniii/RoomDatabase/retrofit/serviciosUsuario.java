package com.example.proyectoprogramacioniii.RoomDatabase.retrofit;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Usuario;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface serviciosUsuario {

    @FormUrlEncoded
    @POST("insertar.php")
    Call<RespuestaUsuario> getUserInformation(@Field("nombre") String nombre,
                                              @Field("correo") String correo,
                                              @Field("contra") String contra,
                                              @Field("creacion") String creacion,
                                              @Field("id_pais") int pais,
                                              @Field("id_departamento") int departamento,
                                              @Field("id_municipio") int municipio,
                                              @Field("id_rol") int rol);

    @GET("registro.php")
    Call<RespuestaUsuario> obtenerUsuarioById(@Query("id") int id);

    @GET("login.php")
    Call<RespuestaUsuario> userExist(@Query("correo") String correo, @Query("contra") String contra);

    @GET("usuarios.php")
    Call<List<Usuario>> allUsers();

}
