package com.example.proyectoprogramacioniii.RoomDatabase.retrofit;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Favorito;
import com.example.proyectoprogramacioniii.utils.FavoritosClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface serviciosFavorito {

    @GET("favoritos/{idUser}")
    Call<List<FavoritosClass>> checkProduct(@Path(value = "idUser", encoded = false) int idUser);

    @HTTP(method = "DELETE", path = "favoritos", hasBody = true)
    Call<FavoritosClass> deleteProduct(@Body FavoritosClass requestUrl);

    @POST("favoritos")
    Call<FavoritosClass> registerFavorito(@Body FavoritosClass favorite);

    @GET("favoritos/{idUser}/{page}")
    Call<List<FavoritosClass>> checkProductPage(@Path(value = "idUser", encoded = false) int idUser, @Path(value = "page", encoded = false) int page);
}
