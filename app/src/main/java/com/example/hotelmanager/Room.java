package com.example.hotelmanager;

public class Room {
    private int id_room;
    private  int number;
    private String status;
    private String type;
    private int tang;
    private int price;

    public Room(int number, String status, String type, int tang, int price) {
        this.number = number;
        this.status = status;
        this.type = type;
        this.tang = tang;
        this.price = price;
    }

    public Room(int number, String status, String type, int price) {
        this.number = number;
        this.status = status;
        this.type = type;
        this.price = price;
    }

    public Room() {

    }

    public int getId_room() {
        return id_room;
    }

    public void setId_room(int id_room) {
        this.id_room = id_room;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTang() {
        return tang;
    }

    public void setTang(int tang) {
        this.tang = tang;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
