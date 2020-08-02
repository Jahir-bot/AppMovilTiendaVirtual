package com.example.apptiendavirtual_30.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Producto implements Parcelable {

    private int id;
    private String nombre;
    private String categoria;
    private double costoUnitario;
    private int stock;
    private String descripcion;
    private String enlaceImagen;

    public Producto(int id, String nombre, String categoria, double costoUnitario, int stock, String enlaceImagen)
    {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.costoUnitario = costoUnitario;
        this.stock = stock;
        this.enlaceImagen = enlaceImagen;
    }

    public Producto(String nombre, double costoUnitario, String enlaceImagen)
    {
        this.nombre = nombre;
        this.costoUnitario = costoUnitario;
        this.enlaceImagen = enlaceImagen;
    }

    public Producto()
    {

    }

    public Producto( int id, String nombre,
                    int stock) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;

    }

    public Producto(String nombre, String categoria, double costoUnitario,
                    int stock, String descripcion, String enlaceImagen) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.costoUnitario = costoUnitario;
        this.stock = stock;
        this.descripcion = descripcion;
        this.enlaceImagen = enlaceImagen;
    }

    protected Producto(Parcel in) {
        id = in.readInt();
        nombre = in.readString();
        categoria = in.readString();
        costoUnitario = in.readDouble();
        stock = in.readInt();
        descripcion = in.readString();
        enlaceImagen = in.readString();
    }

    public static final Creator<Producto> CREATOR = new Creator<Producto>() {
        @Override
        public Producto createFromParcel(Parcel in) {
            return new Producto(in);
        }

        @Override
        public Producto[] newArray(int size) {
            return new Producto[size];
        }
    };

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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(double costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEnlaceImagen() {
        return enlaceImagen;
    }

    public void setEnlaceImagen(String enlaceImagen) {
        this.enlaceImagen = enlaceImagen;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(nombre);
        dest.writeString(categoria);
        dest.writeDouble(costoUnitario);
        dest.writeInt(stock);
        dest.writeString(descripcion);
        dest.writeString(enlaceImagen);
    }
}
