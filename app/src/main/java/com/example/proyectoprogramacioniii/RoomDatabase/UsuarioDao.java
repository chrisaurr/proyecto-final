package com.example.proyectoprogramacioniii.RoomDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Usuario;
import com.example.proyectoprogramacioniii.RoomDatabase.retrofit.RespuestaUsuario;

import java.util.List;

@Dao
public interface UsuarioDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarUsuario(Usuario usuario);

    @Query("SELECT * FROM usuario")
    LiveData<List<Usuario>> obtenerUsuarios();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Usuario> users);


    @Transaction
    @Query("SELECT * FROM usuario WHERE correo= :correo and contra= :contra")
    Usuario obtenerUsuario(String correo, String contra);

    @Query("DELETE FROM usuario WHERE id= :id")
    void borrarUsuario(int id);

    @Query("DELETE FROM usuario")
    void borrarUsuarios();

    @Query("UPDATE usuario SET nombre= :nombre, correo= :correo, contra= :contra WHERE id= :id")
    void actualizarUsuario(int id, String nombre, String correo, String contra);
}
