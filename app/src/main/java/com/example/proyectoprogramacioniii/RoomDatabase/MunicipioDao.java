package com.example.proyectoprogramacioniii.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Departamento;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Municipio;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.DepartamentoConMunicipio;

import java.util.List;

@Dao
public interface MunicipioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarMunicipio(Municipio municipio);

    @Transaction
    @Query("SELECT * FROM departamento WHERE id= :id")
    LiveData<List<DepartamentoConMunicipio>> obtenerMunicipiosConDepartamento(int id);

    @Transaction
    @Query("SELECT * FROM municipio WHERE id_departamentos= :id")
    LiveData<List<Municipio>> obtenerMunicipios(int id);
    @Transaction
    @Query("DELETE FROM municipio WHERE id= :id")
    void borrarMunicipio(int id);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Municipio> municipios);

    @Query("SELECT * FROM Municipio")
    LiveData<List<Municipio>> obtenerAllMunicipios();
}
