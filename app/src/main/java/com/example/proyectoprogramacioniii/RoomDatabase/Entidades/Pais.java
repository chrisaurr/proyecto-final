package com.example.proyectoprogramacioniii.RoomDatabase.Entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Pais {
    @PrimaryKey
    @NonNull
    public int id;
    public String nombre;

    public Pais(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return id +"-"+nombre;
    }
}
