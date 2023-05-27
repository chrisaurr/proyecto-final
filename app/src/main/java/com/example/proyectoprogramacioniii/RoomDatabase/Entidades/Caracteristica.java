package com.example.proyectoprogramacioniii.RoomDatabase.Entidades;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Caracteristica {
    @PrimaryKey
    public int id;
    public String imageUrl;
    public String marca;
    public String descripcion;
    public String precio;
    public String requestUrl;
    public int id_tienda;
    public int id_usuario;


    public Caracteristica(int id, String imageUrl,String marca, String descripcion, String precio, String requestUrl,int id_tienda, int id_usuario) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.marca = marca;
        this.descripcion = descripcion;
        this.precio = precio;
        this.requestUrl = requestUrl;
        this.id_tienda = id_tienda;
        this.id_usuario = id_usuario;
    }

    @Ignore
    public Caracteristica(String imageUrl, String marca, String descripcion, String precio, String requestUrl, int id_tienda) {
        this.imageUrl = imageUrl;
        this.marca = marca;
        this.descripcion = descripcion;
        this.precio = precio;
        this.requestUrl = requestUrl;
        this.id_tienda = id_tienda;

    }
}
