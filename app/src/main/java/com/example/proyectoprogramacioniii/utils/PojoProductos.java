package com.example.proyectoprogramacioniii.utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class PojoProductos implements Parcelable {
    public String url;
    public String marca;
    public String descripcion;
    public String precio;
    public String link;
    public int numTienda;

    public PojoProductos(String url, String marca, String descripcion, String precio, String link, int numTienda) {
        this.url = url;
        this.marca = marca;
        this.descripcion = descripcion;
        this.precio = precio;
        this.link = link;
        this.numTienda = numTienda;
    }

    public PojoProductos() {
    }

    protected PojoProductos(Parcel in) {
        url = in.readString();
        marca = in.readString();
        descripcion = in.readString();
        precio = in.readString();
        link = in.readString();
        numTienda = in.readInt();
    }

    public static final Creator<PojoProductos> CREATOR = new Creator<PojoProductos>() {
        @Override
        public PojoProductos createFromParcel(Parcel in) {
            return new PojoProductos(in);
        }

        @Override
        public PojoProductos[] newArray(int size) {
            return new PojoProductos[size];
        }
    };

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPrecio() {
        String[] l = precio.split(" ");

        String result1 = "";
        if (l.length == 2) {
            result1 = l[1];
        } else {
            result1 = l[0];
        }
        //Log.v("Prueba", result1.replaceAll("Q", ""));

        NumberFormat nf = NumberFormat.getInstance(Locale.US);
        Integer val1 = null;

        try {
            val1 = nf.parse(result1.replaceAll("Q", "").replaceAll(",", "")).intValue();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return val1;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getNumTienda() {
        return numTienda;
    }

    public void setNumTienda(int numTienda) {
        this.numTienda = numTienda;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeString(marca);
        dest.writeString(descripcion);
        dest.writeString(precio);
        dest.writeString(link);
        dest.writeInt(numTienda);
    }

    @Override
    public String toString() {
        return "PojoProductos{" +
                "url='" + url + '\'' +
                ", marca='" + marca + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio='" + precio + '\'' +
                ", link='" + link + '\'' +
                ", numTienda=" + numTienda +
                '}';
    }
}
