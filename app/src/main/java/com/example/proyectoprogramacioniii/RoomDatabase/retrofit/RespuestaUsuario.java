package com.example.proyectoprogramacioniii.RoomDatabase.retrofit;

public class RespuestaUsuario {
    public int id;
    public String nombre;
    public String correo;
    public String contra;
    public String creacion;
    public int id_pais;
    public int id_departamento;
    public int id_municipio;
    public int id_rol;

    public RespuestaUsuario(String nombre, String correo, String contra, String creacion, int id_pais, int id_departamento, int id_municipio, int id_rol) {
        this.nombre = nombre;
        this.correo = correo;
        this.contra = contra;
        this.creacion = creacion;
        this.id_pais = id_pais;
        this.id_departamento = id_departamento;
        this.id_municipio = id_municipio;
        this.id_rol = id_rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getCreacion() {
        return creacion;
    }

    public void setCreacion(String creacion) {
        this.creacion = creacion;
    }

    public int getId_pais() {
        return id_pais;
    }

    public void setId_pais(int id_pais) {
        this.id_pais = id_pais;
    }

    public int getId_departamento() {
        return id_departamento;
    }

    public void setId_departamento(int id_departamento) {
        this.id_departamento = id_departamento;
    }

    public int getId_municipio() {
        return id_municipio;
    }

    public void setId_municipio(int id_municipio) {
        this.id_municipio = id_municipio;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    @Override
    public String toString() {
        return "RespuestaUsuario{" +
                "nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", contra='" + contra + '\'' +
                ", creacion='" + creacion + '\'' +
                ", id_pais=" + id_pais +
                ", id_departamento=" + id_departamento +
                ", id_municipio=" + id_municipio +
                ", id_rol=" + id_rol +
                '}';
    }
}
