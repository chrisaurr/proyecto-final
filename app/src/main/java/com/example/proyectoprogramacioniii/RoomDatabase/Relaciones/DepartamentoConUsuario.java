package com.example.proyectoprogramacioniii.RoomDatabase.Relaciones;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Departamento;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Usuario;

import java.util.List;

public class DepartamentoConUsuario {
    @Embedded public Departamento departamento;
    @Relation(
            parentColumn = "id",
            entityColumn = "id_departamentos"
    )
    public List<Usuario> usuariosPorD;
}
