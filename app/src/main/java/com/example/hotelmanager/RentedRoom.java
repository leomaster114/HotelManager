package com.example.hotelmanager;

public class RentedRoom {
    private String time_in,time_out;
    private int id;
    Room room;
    private int tien;

    public RentedRoom(String time_in, String time_out, Room room) {
        this.time_in = time_in;
        this.time_out = time_out;
        this.room = room;
    }

    public String getTime_in() {
        return time_in;
    }

    public void setTime_in(String time_in) {
        this.time_in = time_in;
    }

    public String getTime_out() {
        return time_out;
    }

    public void setTime_out(String time_out) {
        this.time_out = time_out;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getTien() {
        return tien;
    }

    public void setTien(int tien) {
        this.tien = tien;
    }
}
