package com.example.proyectoprogramacioniii.RoomDatabase.Relaciones;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Rol;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Usuario;

import java.util.List;

public class RolConUsuario {
    @Embedded public Rol rol;
    @Relation(
            parentColumn = "id_rol",
            entityColumn = "id_rol"
    )
    public List<Usuario> usuariosPorRol;
}
