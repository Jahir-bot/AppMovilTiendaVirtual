package com.example.apptiendavirtual_30.model;

import java.util.Date;

public class Pedido {

    private int id;
    private String tipoPago;
    private Date fechaEmisión;
    private String nombreBanco;
    private String codigoVoucher;
    private String estado;
    private double total;
    private double subTotal;
    private double igv;
    private Usuario usuario;

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", tipoPago='" + tipoPago + '\'' +
                ", fechaEmisión=" + fechaEmisión +
                ", nombreBanco='" + nombreBanco + '\'' +
                ", codigoVoucher='" + codigoVoucher + '\'' +
                ", estado='" + estado + '\'' +
                ", total=" + total +
                ", subTotal=" + subTotal +
                ", igv=" + igv +
                ", usuario=" + usuario +
                '}';
    }

    public Pedido()
    {

    }

    public Pedido(String tipoPago, Date fechaEmisión, String nombreBanco,
                  String codigoVoucher, String estado, double total,
                  double subTotal, double igv, Usuario usuario) {
        this.tipoPago = tipoPago;
        this.fechaEmisión = fechaEmisión;
        this.nombreBanco = nombreBanco;
        this.codigoVoucher = codigoVoucher;
        this.estado = estado;
        this.total = total;
        this.subTotal = subTotal;
        this.igv = igv;
        this.usuario = usuario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(String tipoPago) {
        this.tipoPago = tipoPago;
    }

    public Date getFechaEmisión() {
        return fechaEmisión;
    }

    public void setFechaEmisión(Date fechaEmisión) {
        this.fechaEmisión = fechaEmisión;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public String getCodigoVoucher() {
        return codigoVoucher;
    }

    public void setCodigoVoucher(String codigoVoucher) {
        this.codigoVoucher = codigoVoucher;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getIgv() {
        return igv;
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
