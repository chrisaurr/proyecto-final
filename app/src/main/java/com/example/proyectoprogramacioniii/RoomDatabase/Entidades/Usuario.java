package com.example.proyectoprogramacioniii.RoomDatabase.Entidades;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.Relation;

import java.sql.Date;

@Entity
public class Usuario {
    @PrimaryKey
    public int id;
    public String nombre;
    public String correo;
    public String contra;
    public String creacion;
    public int id_pais;
    public int id_departamentos;
    public int id_municipios;
    public int id_rol;
    public boolean isLogued;

    @Ignore
    public Usuario(String nombre, String correo, String contra, String creacion, int id_pais, int id_departamentos, int id_municipios, int id_rol, boolean isLogued) {
        this.nombre = nombre;
        this.correo = correo;
        this.contra = contra;
        this.creacion = creacion;
        this.id_pais = id_pais;
        this.id_departamentos = id_departamentos;
        this.id_municipios = id_municipios;
        this.id_rol = id_rol;
        this.isLogued = isLogued;
    }

    public Usuario(int id, String nombre, String correo, String contra, String creacion, int id_pais, int id_departamentos, int id_municipios, int id_rol, boolean isLogued) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contra = contra;
        this.creacion = creacion;
        this.id_pais = id_pais;
        this.id_departamentos = id_departamentos;
        this.id_municipios = id_municipios;
        this.id_rol = id_rol;
        this.isLogued = isLogued;
    }

    @Ignore
    public Usuario(int id) {
        this.id = id;
    }

    @Ignore
    public Usuario(String nombre, String correo, String contra, int id_pais, int id_departamentos, int id_municipios, int id_rol, boolean isLogued, int id) {
        this.nombre = nombre;
        this.correo = correo;
        this.contra = contra;
        this.id_pais = id_pais;
        this.id_departamentos = id_departamentos;
        this.id_municipios = id_municipios;
        this.id_rol = id_rol;
        this.isLogued = isLogued;
        this.id = id;


    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", contra='" + contra + '\'' +
                ", creacion='" + creacion + '\'' +
                ", id_pais=" + id_pais +
                ", id_departamento=" + id_departamentos +
                ", id_municipio=" + id_municipios +
                ", id_rol=" + id_rol +
                '}';
    }
}
