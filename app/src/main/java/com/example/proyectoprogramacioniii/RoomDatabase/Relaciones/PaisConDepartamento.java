package com.example.proyectoprogramacioniii.RoomDatabase.Relaciones;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Departamento;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Pais;

import java.util.List;

public class PaisConDepartamento {
    @Embedded public Pais pais;
    @Relation(
            parentColumn = "id",
            entityColumn = "id_pais"
    )
    public List<Departamento> departamentos;
}
