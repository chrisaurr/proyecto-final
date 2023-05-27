package com.example.proyectoprogramacioniii.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Pais;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Usuario;

import java.util.List;

@Dao
public interface PaisDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarPais(Pais pais);

    @Transaction
    @Query("DELETE FROM pais WHERE id= :id")
    void borrarPais(int id);

    @Query("DELETE FROM pais")
    void borrarPaises();

    @Query("SELECT * FROM pais")
    LiveData<List<Pais>> obtenerPaises();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Pais> paises);
}
