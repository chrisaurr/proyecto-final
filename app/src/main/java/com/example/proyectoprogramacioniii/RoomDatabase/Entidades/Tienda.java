package com.example.proyectoprogramacioniii.RoomDatabase.Entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Tienda {
    @PrimaryKey
    @NonNull
    public int id;
    public String nombre;

    public Tienda(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
