package com.example.apptiendavirtual_30.model;

public class DetallePedido{

    private int id;
    private Pedido pedido;
    private Producto producto;
    private int cantidad;
    private double costo;

    @Override
    public String toString() {
        return "DetallePedido{" +
                "id=" + id +
                ", pedido=" + pedido +
                ", producto=" + producto +
                ", cantidad=" + cantidad +
                ", costo=" + costo +
                '}';
    }

    public DetallePedido()
    {

    }

    public DetallePedido(Pedido pedido, Producto producto, int cantidad, double costo) {
        this.pedido = pedido;
        this.producto = producto;
        this.cantidad = cantidad;
        this.costo = costo;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }
}
