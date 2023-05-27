package com.example.proyectoprogramacioniii.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Municipio;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.RolAcceso;

import java.util.List;

@Dao
public interface RolAccesoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertarRolAcceso(RolAcceso rolAcceso);

    @Transaction
    @Query("DELETE FROM rolacceso WHERE id_rol= :id")
    void borrarRolAcceso(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<RolAcceso> rolAccesos);

    @Query("SELECT * FROM RolAcceso")
    LiveData<List<RolAcceso>> obtenerAllRolesAcceso();
}
