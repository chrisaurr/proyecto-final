package com.example.proyectoprogramacioniii.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Tienda;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Usuario;

import java.util.List;

@Dao
public interface TiendaDao {
    @Query("SELECT * FROM tienda")
    LiveData<List<Tienda>> obtenerTiendas();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Tienda> tiendas);


    @Transaction
    @Query("SELECT * FROM tienda WHERE id= :id")
    Tienda obtenerTienda(int id);
}
