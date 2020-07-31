package com.example.apptiendavirtual_30.model;

import java.util.List;

public class Mensajes {


    private String message;
    private boolean requestSuccess;
    private Usuario object;
    private List<Usuario> list;

    public List<Usuario> getList() {
        return list;
    }
    public void setList(List<Usuario> list) {
        this.list = list;
    }
    public Usuario getObject() {
        return object;
    }

    public void setObject(Usuario object) {
        this.object = object;
    }
    public Mensajes() {
        // TODO Auto-generated constructor stub
    }

    public void setParametersResponse( String message, boolean requestSuccess ) {

        this.message = message;
        this.requestSuccess = requestSuccess;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRequestSuccess() {
        return requestSuccess;
    }

    public void setRequestSuccess(boolean requestSuccess) {
        this.requestSuccess = requestSuccess;
    }

    @Override
    public String toString() {
        return "ResponseAjax [message=" + message + ", requestSuccess=" + requestSuccess + "]";
    }
}
