package com.example.proyectoprogramacioniii.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Municipio;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Privilegio;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.PrivilegioConRol;

import java.util.List;

@Dao
public interface PrivilegioDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertarPrivilegio(Privilegio privilegio);

    @Transaction
    @Query("SELECT * FROM privilegio WHERE id_privilegio= :id")
    LiveData<List<PrivilegioConRol>> obtenerRolesConPrivilegio(int id);

    @Transaction
    @Query("DELETE FROM privilegio WHERE id_privilegio= :id")
    void borrarPrivilegio(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Privilegio> privilegios);

    @Query("SELECT * FROM Privilegio")
    LiveData<List<Privilegio>> obtenerAllPrivilegios();
}
