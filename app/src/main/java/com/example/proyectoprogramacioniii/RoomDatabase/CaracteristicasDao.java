package com.example.proyectoprogramacioniii.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Caracteristica;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Usuario;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.CaracteristicaYFavorito;

import java.util.List;

@Dao
public interface CaracteristicasDao {
    @Query("SELECT * FROM caracteristica WHERE id_usuario= :id")
    LiveData<List<Caracteristica>> obtenerCaracteristicas(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Caracteristica> caracteristicas);


    @Query("DELETE FROM caracteristica WHERE requestUrl= :request")
    void borrarCaracteristica(String request);

    @Query("DELETE FROM caracteristica WHERE id_usuario= :id_user")
    void borrarCaracteristicas(int id_user);

}
