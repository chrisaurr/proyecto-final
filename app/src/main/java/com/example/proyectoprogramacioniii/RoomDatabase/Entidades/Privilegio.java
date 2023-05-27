package com.example.proyectoprogramacioniii.RoomDatabase.Entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Privilegio {
    @PrimaryKey
    @NonNull
    public int id_privilegio;
    public String nombre;

    public Privilegio(int id_privilegio, String nombre) {
        this.id_privilegio = id_privilegio;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return id_privilegio +"-"+nombre;
    }
}
