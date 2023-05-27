package com.example.proyectoprogramacioniii.RoomDatabase.Relaciones;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Favorito;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Usuario;

import java.util.List;

public class UsuarioConFavorito {
    @Embedded public Usuario usuario;
    @Relation(
            parentColumn = "id",
            entityColumn = "id_usuario"
    )
    public List<Favorito> favoritos;
}
