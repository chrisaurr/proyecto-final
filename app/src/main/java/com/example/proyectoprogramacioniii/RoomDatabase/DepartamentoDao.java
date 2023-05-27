package com.example.proyectoprogramacioniii.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Departamento;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Pais;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.PaisConDepartamento;

import java.util.List;

@Dao
public interface DepartamentoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarDepartamento(Departamento departamento);

    @Transaction
    @Query("SELECT * FROM pais WHERE id= :id")
    LiveData<List<PaisConDepartamento>> obtenerDepartamentosConPais(int id);

    @Transaction
    @Query("SELECT * FROM departamento WHERE id_pais= :id")
    LiveData<List<Departamento>> obtenerDepartamentos(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Departamento> departamentos);

    @Query("SELECT * FROM Departamento")
    LiveData<List<Departamento>> obtenerAllDepartamentos();

    @Transaction
    @Query("DELETE FROM departamento WHERE id= :id")
    void borrarDepartamento(int id);
}
