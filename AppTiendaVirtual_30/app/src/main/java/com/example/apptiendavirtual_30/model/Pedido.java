package com.example.apptiendavirtual_30.model;

import android.app.PendingIntent;

import java.io.Serializable;
import java.util.List;

public class Pedido implements Serializable {

    private int id;
    private String paymentType;
    private String dateEmision;
    private String nameBanco;
    private String codeVoucher;
    private String state;
    private double total;
    private double subtotal;
    private double igv;
    private Usuario user;
    private List<DetallePedido> details;

    public Pedido(String paymentType, String dateEmision, String nameBanco, String codeVoucher,
                  String state, double total, double subtotal, double igv,
                  Usuario user, List<DetallePedido> details) {
        this.paymentType = paymentType;
        this.dateEmision = dateEmision;
        this.nameBanco = nameBanco;
        this.codeVoucher = codeVoucher;
        this.state = state;
        this.total = total;
        this.subtotal = subtotal;
        this.igv = igv;
        this.user = user;
        this.details = details;
    }

    public List<DetallePedido> getDetails() {
        return details;
    }

    public void setDetails(List<DetallePedido> details) {
        this.details = details;
    }

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    public Pedido()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getDateEmision() {
        return dateEmision;
    }

    public void setDateEmision(String dateEmision) {
        this.dateEmision = dateEmision;
    }

    public String getNameBanco() {
        return nameBanco;
    }

    public void setNameBanco(String nameBanco) {
        this.nameBanco = nameBanco;
    }

    public String getCodeVoucher() {
        return codeVoucher;
    }

    public void setCodeVoucher(String codeVoucher) {
        this.codeVoucher = codeVoucher;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIgv() {
        return igv;
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }
}
