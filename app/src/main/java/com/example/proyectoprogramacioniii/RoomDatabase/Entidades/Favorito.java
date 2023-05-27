package com.example.proyectoprogramacioniii.RoomDatabase.Entidades;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Favorito {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public int id_usuario;
    public String requestUrl;
    public int id_caracteristica;

    public Favorito(int id_usuario, String requestUrl, int id_caracteristica) {
        this.id_usuario = id_usuario;
        this.requestUrl = requestUrl;
        this.id_caracteristica = id_caracteristica;
    }
}
