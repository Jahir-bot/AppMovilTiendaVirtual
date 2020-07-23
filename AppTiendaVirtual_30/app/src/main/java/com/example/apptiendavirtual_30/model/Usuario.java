package com.example.apptiendavirtual_30.model;

public class Usuario {

    private int id;
    private String nombres;
    private String apellidos;
    private String celular;
    private String clave;
    private String direccion;
    private String message;
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombres='" + nombres + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", celular='" + celular + '\'' +
                ", clave='" + clave + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }

    public Usuario()
    {

    }

    public Usuario(String nombres, String apellidos, String celular, String clave, String direccion) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.celular = celular;
        this.clave = clave;
        this.direccion = direccion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
