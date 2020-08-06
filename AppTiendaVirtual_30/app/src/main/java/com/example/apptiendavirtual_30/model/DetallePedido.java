package com.example.apptiendavirtual_30.model;

import java.io.Serializable;

public class DetallePedido implements Serializable {

    private int id;
    private Pedido order;
    private Producto product;
    private int cant;
    private double cost;

    @Override
    public String toString() {
        return "DetallePedido{" +
                "id=" + id +
                ", order=" + order +
                ", product=" + product +
                ", cant=" + cant +
                ", cost=" + cost +
                '}';
    }

    public DetallePedido(Producto product, int cant, double cost) {
        this.product = product;
        this.cant = cant;
        this.cost = cost;
    }

    public DetallePedido(int cant, String name, double cost)
    {
        this.cant = cant;
        this. product = new Producto(name);
        this.cost = cost;
    }

    public DetallePedido(Pedido order, Producto product, int cant, double cost) {
        this.order = order;
        this.product = product;
        this.cant = cant;
        this.cost = cost;
    }
    public DetallePedido()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pedido getOrder() {
        return order;
    }

    public void setOrder(Pedido order) {
        this.order = order;
    }

    public Producto getProduct() {
        return product;
    }

    public void setProduct(Producto product) {
        this.product = product;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
