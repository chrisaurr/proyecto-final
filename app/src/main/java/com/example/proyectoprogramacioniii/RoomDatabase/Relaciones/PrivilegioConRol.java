package com.example.proyectoprogramacioniii.RoomDatabase.Relaciones;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Privilegio;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Rol;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.RolAcceso;

import java.util.List;

public class PrivilegioConRol {
    @Embedded public Privilegio privilegio;
    @Relation(
            parentColumn = "id_privilegio",
            entityColumn = "id_rol",
            associateBy = @Junction(RolAcceso.class)
    )
    public List<Rol> roles;

}
