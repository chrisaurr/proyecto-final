package com.example.proyectoprogramacioniii.utils;

public class FavoritosClass {
    public int id_usuarios;
    public String requestUrl;
    public int idTienda;
    public String imageUrl;

    /*public favoritos(int id, int id_usuarios, String requestUrl, int idTienda) {
        this.id = id;
        this.id_usuarios = id_usuarios;
        this.requestUrl = requestUrl;
        this.idTienda = idTienda;
    }*/

    public FavoritosClass(int id_usuarios, String requestUrl, int idTienda, String imageUrl) {
        this.id_usuarios = id_usuarios;
        this.requestUrl = requestUrl;
        this.idTienda = idTienda;
        this.imageUrl = imageUrl;
    }

    public FavoritosClass() {
    }

    public FavoritosClass(int id_usuarios, String requestUrl) {
        this.id_usuarios = id_usuarios;
        this.requestUrl = requestUrl;
    }

}
