package com.example.proyectoprogramacioniii.RoomDatabase.Relaciones;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Privilegio;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Rol;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.RolAcceso;

import java.util.List;

public class RolConPrivilegio {
    @Embedded public Rol rol;
    @Relation(
            parentColumn = "id_rol",
            entityColumn = "id_privilegio",
            associateBy = @Junction(RolAcceso.class)
    )
    public List<Privilegio> privilegios;

}
