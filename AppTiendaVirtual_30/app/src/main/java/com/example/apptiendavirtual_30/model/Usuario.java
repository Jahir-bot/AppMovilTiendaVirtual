package com.example.apptiendavirtual_30.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Usuario implements Parcelable {

    private Integer id;
    private String name;
    private String lastname;
    private String phone;
    private String password;
    private String address;
    private String typeUser;

    public Usuario(String name)
    {
        this.name = name;
    }
    public Usuario(int id, String name)
    {
        this.id = id;
        this.name = name;
    }
    public Usuario(int id)
    {
        this.id = id;
    }

    public Usuario(String name, String lastname, String phone, String password, String address, String typeUser) {
        this.name = name;
        this.lastname = lastname;
        this.phone = phone;
        this.password = password;
        this.address = address;
        this.typeUser = typeUser;
    }

    public Usuario(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    protected Usuario(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readInt();
        }
        name = in.readString();
        lastname = in.readString();
        phone = in.readString();
        password = in.readString();
        address = in.readString();
        typeUser = in.readString();
    }

    public static final Creator<Usuario> CREATOR = new Creator<Usuario>() {
        @Override
        public Usuario createFromParcel(Parcel in) {
            return new Usuario(in);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(String typeUser) {
        this.typeUser = typeUser;
    }


    public Usuario()
    {

    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", address='" + address + '\'' +
                ", typeUser='" + typeUser + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(id);
        }
        dest.writeString(name);
        dest.writeString(lastname);
        dest.writeString(phone);
        dest.writeString(password);
        dest.writeString(address);
        dest.writeString(typeUser);
    }
}
