package com.example.proyectoprogramacioniii.RoomDatabase.retrofit;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Usuario;
import com.example.proyectoprogramacioniii.utils.FavoritosClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface serviciosUsuarios {
    @GET("usuarios")
    Call<List<Usuario>> allUsers();


    @GET("usuarios/{correo}/{contra}")
    Call<List<Usuario>> userExist(@Path(value = "correo", encoded = false) String correo, @Path(value = "contra", encoded = false) String contra);
    /*@GET("usuarios")
    Call<Usuario> userExist(@Query("correo") String correo, @Query("contra") String contra);*/

    @POST("usuarios")
    Call<Usuario> registerUser(@Body Usuario login);

    @PUT("usuarios")
    Call<Usuario> modifyUser(@Body Usuario login);

    @HTTP(method = "DELETE", path = "usuarios", hasBody = true)
    Call<Usuario> deleteUser(@Body Usuario id);
}
