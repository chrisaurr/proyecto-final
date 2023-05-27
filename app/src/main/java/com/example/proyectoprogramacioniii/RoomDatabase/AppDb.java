package com.example.proyectoprogramacioniii.RoomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

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

@Database(entities = {Caracteristica.class, Departamento.class, Favorito.class,
        Municipio.class, Pais.class, Privilegio.class, Rol.class, RolAcceso.class,
        Tienda.class, Usuario.class}, version = 6, exportSchema = false)
public abstract class AppDb extends RoomDatabase {

    public abstract DaoUsuario daoUsuario();
    public abstract PaisDao paisDao();
    public abstract DepartamentoDao departamentoDao();
    public abstract MunicipioDao municipioDao();
    public abstract RolDao rolDao();
    public abstract PrivilegioDao privilegioDao();
    public abstract RolAccesoDao rolAccesoDao();
    public abstract UsuarioDao usuarioDao();
    public abstract TiendaDao tiendaDao();
    public abstract CaracteristicasDao caracteristicasDao();

}
