package com.example.proyectoprogramacioniii.RoomDatabase.Relaciones;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Municipio;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Usuario;

import java.util.List;

public class MunicipioConUsuario {
    @Embedded public Municipio municipio;
    @Relation(
            parentColumn = "id",
            entityColumn = "id_municipios"
    )
    public List<Usuario> usuariosPorM;
}
