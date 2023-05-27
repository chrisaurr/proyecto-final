package com.example.proyectoprogramacioniii.RoomDatabase.Relaciones;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Caracteristica;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Favorito;

public class CaracteristicaYFavorito {
    @Embedded public Caracteristica caracteristica;
    @Relation(
            parentColumn = "id",
            entityColumn = "id_caracteristica"
    )
    public Favorito infoFavorito;
}
