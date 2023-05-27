package com.example.proyectoprogramacioniii.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Municipio;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Rol;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.RolConPrivilegio;

import java.util.List;

@Dao
public interface RolDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarRol(Rol rol);

    @Transaction
    @Query("SELECT * FROM rol WHERE id_rol= :id")
    LiveData<List<RolConPrivilegio>> obtenerPrivilegiosConRol(int id);

    @Transaction
    @Query("SELECT * FROM rol")
    LiveData<List<Rol>> obtenerRoles();

    @Transaction
    @Query("DELETE FROM rol WHERE id_rol= :id")
    void borrarRol(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Rol> rol);
}
