package com.example.proyectoprogramacioniii.RoomDatabase.Entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"id_rol", "id_privilegio"})
public class RolAcceso {
    public int id_rol;
    public int id_privilegio;

    public RolAcceso(int id_rol, int id_privilegio) {
        this.id_rol = id_rol;
        this.id_privilegio = id_privilegio;
    }
}
