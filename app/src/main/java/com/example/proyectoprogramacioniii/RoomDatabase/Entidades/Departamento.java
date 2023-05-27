package com.example.proyectoprogramacioniii.RoomDatabase.Entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Departamento {
    @PrimaryKey
    @NonNull
    public int id;
    public String nombre;
    public int id_pais;

    public Departamento(int id, String nombre, int id_pais) {
        this.id = id;
        this.nombre = nombre;
        this.id_pais = id_pais;
    }

    @Override
    public String toString() {
        return id +"-"+nombre;
    }
}
