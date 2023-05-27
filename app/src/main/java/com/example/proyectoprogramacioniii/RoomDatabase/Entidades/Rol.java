package com.example.proyectoprogramacioniii.RoomDatabase.Entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Rol {
    @PrimaryKey
    @NonNull
    public int id_rol;
    public String nombre;

    public Rol(int id_rol, String nombre) {
        this.id_rol = id_rol;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return id_rol +"-"+nombre;
    }
}
