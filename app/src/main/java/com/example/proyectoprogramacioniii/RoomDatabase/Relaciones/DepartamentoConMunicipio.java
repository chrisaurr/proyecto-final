package com.example.proyectoprogramacioniii.RoomDatabase.Relaciones;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Departamento;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Municipio;

import java.util.List;

public class DepartamentoConMunicipio {
    @Embedded public Departamento departamento;
    @Relation(
            parentColumn = "id",
            entityColumn = "id_departamentos"
    )
    public List<Municipio> municipios;
}
