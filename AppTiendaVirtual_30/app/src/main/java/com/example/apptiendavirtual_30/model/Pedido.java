package com.example.apptiendavirtual_30.model;


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
    private String numberGenerated;

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

    public Pedido(String numberGenerated, double total, String name)
    {
        this.numberGenerated = numberGenerated;
        this.total = total;
        this.user = new Usuario(name);
    }
    public Pedido(String numberGenerated, double total, int id_user)
    {
        this.numberGenerated = numberGenerated;
        this.total = total;
        this.user = new Usuario(id_user);
    }

    public Pedido(int id, double total) {
        this.id = id;
        this.total = total;
    }

    public String getNumberGenerated() {
        return numberGenerated;
    }

    public void setNumberGenerated(String numberGenerated) {
        this.numberGenerated = numberGenerated;
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

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", paymentType='" + paymentType + '\'' +
                ", dateEmision='" + dateEmision + '\'' +
                ", nameBanco='" + nameBanco + '\'' +
                ", codeVoucher='" + codeVoucher + '\'' +
                ", state='" + state + '\'' +
                ", total=" + total +
                ", subtotal=" + subtotal +
                ", igv=" + igv +
                ", user=" + user +
                '}';
    }
}
