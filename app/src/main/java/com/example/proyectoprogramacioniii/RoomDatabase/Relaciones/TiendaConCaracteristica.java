package com.example.proyectoprogramacioniii.RoomDatabase.Relaciones;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Caracteristica;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Tienda;

import java.util.List;

public class TiendaConCaracteristica {
    @Embedded public Tienda tienda;
    @Relation(
            parentColumn = "id",
            entityColumn = "id_tienda"
    )
    public List<Caracteristica> caracteristicas;
}
