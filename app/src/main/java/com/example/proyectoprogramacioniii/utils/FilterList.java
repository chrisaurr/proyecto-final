package com.example.proyectoprogramacioniii.utils;

public class FilterList {
    public String url;
    public String marca;
    public String descripcion;
    public Float precio;
    public String link;

    public FilterList(String url, String marca, String descripcion, Float precio, String link) {
        this.url = url;
        this.marca = marca;
        this.descripcion = descripcion;
        this.precio = precio;
        this.link = link;
    }

    public FilterList() {
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
