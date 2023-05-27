package com.example.proyectoprogramacioniii.RoomDatabase;

//import androidx.lifecycle.LiveData;
//import android.arch.lifecycle.LiveData;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Caracteristica;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Departamento;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Favorito;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Municipio;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Pais;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Privilegio;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Rol;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.RolAcceso;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Tienda;
import com.example.proyectoprogramacioniii.RoomDatabase.Entidades.Usuario;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.CaracteristicaYFavorito;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.DepartamentoConMunicipio;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.DepartamentoConUsuario;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.MunicipioConUsuario;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.PaisConDepartamento;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.PaisConUsuario;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.PrivilegioConRol;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.RolConPrivilegio;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.RolConUsuario;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.TiendaConCaracteristica;
import com.example.proyectoprogramacioniii.RoomDatabase.Relaciones.UsuarioConFavorito;
//import com.example.proyectoprogramacioniii.RoomDatabase.Usuarios.Usuario;

import java.util.List;

@Dao
public interface DaoUsuario {
/*
    @Query("SELECT * FROM usuario")
    List<Usuario> obtenerUsuarios();

    @Query("SELECT * FROM usuario WHERE id= :id")
    Usuario obtenerUsuario(int id);

    @Insert
    void insertarUsuario(Usuario...usuarios);

    @Query("UPDATE usuario SET nombre= :nombre, correo= :correo, contra= :contra WHERE id= :id")
    void actualizarUsuario(int id, String nombre, String correo, String contra);

    @Query("DELETE FROM usuario WHERE id= :id")
    void borrarUsuario(int id);*/

    //one to many
    @Insert
    void insertarCaracteristica(Caracteristica caracteristica);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarDepartamento(Departamento departamento);

    @Insert
    void insertarFavorito(Favorito favorito);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarMunicipio(Municipio municipio);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarPais(Pais pais);

    @Insert
    void insertarPrivilegio(Privilegio privilegio);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarRol(Rol rol);

    @Insert
    void insertarRolAcceso(RolAcceso rolAcceso);

    @Insert
    void insertarTienda(Tienda tienda);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertarUsuario(Usuario usuario);



    @Transaction
    @Query("SELECT * FROM caracteristica WHERE id= :id")
    List<CaracteristicaYFavorito> obtenerCaracteristicaYFavoritoConIdCaracteristica(int id);

    @Transaction
    @Query("SELECT * FROM departamento WHERE id= :id")
    LiveData<List<DepartamentoConMunicipio>> obtenerMunicipiosConDepartamento(int id);

    @Transaction
    @Query("SELECT * FROM departamento WHERE id= :id")
    List<DepartamentoConUsuario> obtenerUsuariosConDepartamento(int id);

    @Transaction
    @Query("SELECT * FROM municipio WHERE id= :id")
    List<MunicipioConUsuario> obtenerUsuariosConMunicipio(int id);

    @Transaction
    @Query("SELECT * FROM pais WHERE id= :id")
    LiveData<List<PaisConDepartamento>> obtenerDepartamentosConPais(int id);

    @Transaction
    @Query("SELECT * FROM pais WHERE id= :id")
    List<PaisConUsuario> obtenerUsuariosConPais(int id);


    @Transaction
    @Query("SELECT * FROM privilegio WHERE id_privilegio= :id")
    LiveData<List<PrivilegioConRol>> obtenerRolesConPrivilegio(int id);

    @Transaction
    @Query("SELECT * FROM rol WHERE id_rol= :id")
    LiveData<List<RolConPrivilegio>> obtenerPrivilegiosConRol(int id);


    @Transaction
    @Query("SELECT * FROM rol WHERE id_rol= :id")
    List<RolConUsuario> obtenerUsuariosConRol(int id);

    @Transaction
    @Query("SELECT * FROM tienda WHERE id= :id")
    List<TiendaConCaracteristica> obtenerCaracteristicasConTienda(int id);

    @Transaction
    @Query("SELECT * FROM usuario WHERE id= :id")
    List<UsuarioConFavorito> obtenerFavoritosConUsuario(int id);




    @Query("SELECT * FROM usuario")
    List<Usuario> obtenerUsuarios();

    @Query("SELECT * FROM pais")
    LiveData<List<Pais>> obtenerPaises();

    @Query("DELETE FROM pais")
    void borrarPaises();

    @Transaction
    @Query("DELETE FROM pais WHERE id= :id")
    void borrarPais(int id);

    @Transaction
    @Query("DELETE FROM departamento WHERE id= :id")
    void borrarDepartamento(int id);

    @Transaction
    @Query("DELETE FROM municipio WHERE id= :id")
    void borrarMunicipio(int id);

    @Transaction
    @Query("DELETE FROM rol WHERE id_rol= :id")
    void borrarRol(int id);

    @Transaction
    @Query("DELETE FROM privilegio WHERE id_privilegio= :id")
    void borrarPrivilegio(int id);

    @Transaction
    @Query("DELETE FROM rolacceso WHERE id_rol= :id")
    void borrarRolAcceso(int id);



    //one to one
  /*  @Insert
    void insertarCaracteristica(Caracteristica caracteristica);

    @Insert
    void insertarFavorito(Favorito favorito);

    @Transaction
    @Query("SELECT * FROM caracteristica WHERE id= :id")
    List<CaracteristicaYFavorito> obtenerCaracteristicaYFavoritoConIdCaracteristica(int id);*/

}
