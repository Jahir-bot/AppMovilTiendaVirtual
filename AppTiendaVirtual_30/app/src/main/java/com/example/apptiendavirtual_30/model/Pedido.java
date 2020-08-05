package com.example.apptiendavirtual_30.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Parcelable {

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

    public Pedido (int cant, String name, double cost)
    {
        this.details = new  DetallePedido(cant, name, cost);
    }

    public Pedido(int id, String paymentType, String dateEmision, String nameBanco,
                  String codeVoucher, String state, String numberGenerated, double total, double subtotal,
                  double igv, int id_user, String name)
    {
        this.paymentType = paymentType;
        this.dateEmision = dateEmision;
        this.nameBanco = nameBanco;
        this.codeVoucher = codeVoucher;
        this.state = state;
        this.id = id;
        this.numberGenerated = numberGenerated;
        this.total = total;
        this.subtotal = subtotal;
        this.igv = igv;
        this.user = new Usuario(id_user, name);
//        this.user = new Usuario(name);
    }
    public Pedido(int id, String paymentType, String dateEmision, String nameBanco,
                  String codeVoucher, String state, double total, double subtotal,
                  double igv, int id_user)
    {
        this.id = id;
        this.paymentType = paymentType;
        this.dateEmision = dateEmision;
        this.nameBanco = nameBanco;
        this.codeVoucher = codeVoucher;
        this.state = state;
        this.total = total;
        this.subtotal = subtotal;
        this.igv = igv;
        this.user = new Usuario(id_user);
//        this.user = new Usuario(name);
    }
    public Pedido(int id, String paymentType, String dateEmision, String nameBanco,
                  String codeVoucher, String state, String numberGenerated, double total, double subtotal,
                  double igv, int id_user)
    {
        this.id = id;
        this.paymentType = paymentType;
        this.dateEmision = dateEmision;
        this.nameBanco = nameBanco;
        this.codeVoucher = codeVoucher;
        this.state = state;
        this.numberGenerated = numberGenerated;
        this.total = total;
        this.subtotal = subtotal;
        this.igv = igv;
        this.user = new Usuario(id_user);
//        this.user = new Usuario(name);
    }
    public Pedido (int id, String state)
    {
        this.id = id;
        this.state = state;
    }
    public Pedido(int id, String paymentType, String dateEmision, String nameBanco,
                  String codeVoucher, String state, String numberGenerated, double total, int id_user)
    {
        this.paymentType = paymentType;
        this.dateEmision = dateEmision;
        this.nameBanco = nameBanco;
        this.codeVoucher = codeVoucher;
        this.state = state;
        this.id = id;
        this.numberGenerated = numberGenerated;
        this.total = total;
        this.user = new Usuario(id_user);
    }

    public Pedido(int id, double total) {
        this.id = id;
        this.total = total;
    }

    protected Pedido(Parcel in) {
        id = in.readInt();
        paymentType = in.readString();
        dateEmision = in.readString();
        nameBanco = in.readString();
        codeVoucher = in.readString();
        state = in.readString();
        total = in.readDouble();
        subtotal = in.readDouble();
        igv = in.readDouble();
        numberGenerated = in.readString();
    }

    public static final Creator<Pedido> CREATOR = new Creator<Pedido>() {
        @Override
        public Pedido createFromParcel(Parcel in) {
            return new Pedido(in);
        }

        @Override
        public Pedido[] newArray(int size) {
            return new Pedido[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(paymentType);
        dest.writeString(dateEmision);
        dest.writeString(nameBanco);
        dest.writeString(codeVoucher);
        dest.writeString(state);
        dest.writeDouble(total);
        dest.writeDouble(subtotal);
        dest.writeDouble(igv);
        dest.writeString(numberGenerated);
    }
}
