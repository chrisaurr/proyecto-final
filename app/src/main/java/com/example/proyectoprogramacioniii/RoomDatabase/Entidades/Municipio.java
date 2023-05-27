package com.example.proyectoprogramacioniii.RoomDatabase.Entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Municipio {
    @PrimaryKey
    @NonNull
    public int id;
    public String nombre;
    public int id_departamentos;

    public Municipio(int id, String nombre, int id_departamentos) {
        this.id = id;
        this.nombre = nombre;
        this.id_departamentos = id_departamentos;
    }

    @Override
    public String toString() {
        return id +"-"+nombre;
    }
}
