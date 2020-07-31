package com.example.hotelmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MyDatabase extends SQLiteOpenHelper {
    Context context;
    private static String DBName = "HotelManager";
    private String id = "id";
    private String tableRoom = "Room";
    private String number = "number";
    private String type = "type";
    String tang = "tang";
    private String price = "price";
    private String jointime = "jointTime";
    private String quitTime = "quitTime";
    private String status = "status";

    private String tableRentedRoom = "RentedRoom";
    private String id_room = "id_room";
    private String timeIn = "time_in";
    private String timeOut = "time_out";
    private String tien = "tien";

    public MyDatabase(Context context) {
        super(context, DBName, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createRoom = String.format("CREATE TABLE %s(%s INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT,%s text,%s text,%s text,%s integer)",
                tableRoom, id, number, status, type, tang, price);
        String createRentedRoom = String.format("CREATE TABLE %s(%s integer primary key autoincrement,%s text,%s text,%s integer,%s integer,FOREIGN KEY (%s) REFERENCES %s(%s))", tableRentedRoom, id, timeIn, timeOut, id_room,tien, id_room, tableRoom, id_room);
        db.execSQL(createRoom);
        db.execSQL(createRentedRoom);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String droptableRoom = String.format("Drop table if exist %s", tableRoom);
        db.execSQL(droptableRoom);
    }

    public void addRoom(Room room) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(number, room.getNumber());
        values.put(status, room.getStatus());
        values.put(type, room.getTang());
        values.put(tang, room.getTang());
        values.put(price, room.getPrice());
        database.insert(tableRoom, null, values);
    }
    public ArrayList<Room> getRoomByStatus(String status){
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<Room> rooms = new ArrayList<>();
        String sql = "select * from Room where status = ?";
        Cursor cursor = database.rawQuery(sql,new String[]{status});
        if(cursor.moveToFirst()){
            do{
              Room room = new Room();
              room.setId_room(cursor.getInt(0));
              room.setNumber(cursor.getInt(1));
              room.setStatus(cursor.getString(2));
              room.setType(cursor.getString(3));
              room.setTang(cursor.getShort(4));
              room.setPrice(cursor.getInt(5));
              rooms.add(room);
            }while (cursor.moveToNext());
        }
        return rooms;
    }
    public boolean updateRoom(Room room) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(number, room.getNumber());
        values.put(status, room.getStatus());
        values.put(type, room.getTang());
        values.put(tang, room.getTang());
        values.put(price, room.getPrice());
        return database.update(tableRoom, values, id + "=" + room.getId_room(), null) > 0;
    }
    public void addRentedRoom(RentedRoom rentedRoom){
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(timeIn,rentedRoom.getTime_in());
        values.put(timeOut,rentedRoom.getTime_out());
        values.put(id_room,rentedRoom.getRoom().getId_room());
        values.put(tien,rentedRoom.getTien());
        database.insert(tableRentedRoom,null,values);
    }

    public ArrayList<Room> getRoomByTang(int tang) {
        SQLiteDatabase database = getReadableDatabase();
        ArrayList<Room> rooms = new ArrayList<>();
        String sql = "select * from Room where tang = ?";
        Cursor cursor = database.rawQuery(sql,new String[]{String.valueOf(tang)});
        if(cursor.moveToFirst()){
            do{
                Room room = new Room();
                room.setId_room(cursor.getInt(0));
                room.setNumber(cursor.getInt(1));
                room.setStatus(cursor.getString(2));
                room.setType(cursor.getString(3));
                room.setTang(cursor.getShort(4));
                room.setPrice(cursor.getInt(5));
                rooms.add(room);
            }while (cursor.moveToNext());
        }
        return rooms;
    }
}
