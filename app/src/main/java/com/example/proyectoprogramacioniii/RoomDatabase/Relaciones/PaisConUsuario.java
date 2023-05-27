package com.example.proyectoprogramacioniii.RoomDatabase.Relaciones;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Pais;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Usuario;

import java.util.List;

public class PaisConUsuario {
    @Embedded public Pais pais;
    @Relation(
            parentColumn = "id",
            entityColumn = "id_pais"
    )
    public List<Usuario> usuariosPorP;
}
