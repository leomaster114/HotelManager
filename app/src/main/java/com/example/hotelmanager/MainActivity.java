package com.example.hotelmanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    GridView gridView;
    ArrayList<Room> rooms;
    RoomAdapter adapter;
    Spinner spinner;
    TextView tv_KS_status, tv_logout, tv_addFloor, tv_thongke;
    Sharedpref sharedpref;
    int num_fl, num_room;
    boolean saveInDB;
    MyDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout = findViewById(R.id.main_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_draw_open, R.string.nav_draw_close);
        drawerLayout.addDrawerListener(drawerToggle);
        gridView = findViewById(R.id.gridview);
        spinner = findViewById(R.id.spinner);
        tv_addFloor = findViewById(R.id.tv_addFloor);
        tv_KS_status = findViewById(R.id.tv_status_hotel);
        tv_logout = findViewById(R.id.tv_logout);
        tv_thongke = findViewById(R.id.tv_thongke);
        sharedpref = Sharedpref.getInstances(this);
        tv_addFloor.setOnClickListener(this);
        tv_KS_status.setOnClickListener(this);
        database = new MyDatabase(this);
        final ArrayList<String> sotang = new ArrayList<>();
        num_fl = sharedpref.getFloor();
        num_room = sharedpref.getRoom();

        if(num_fl == -1 ||num_room == -1) {
            num_fl = 1;
            num_room = 2;
        }{
            for (int i = 1; i <= num_fl; i++) sotang.add("" + i);
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, sotang);
            spinner.setAdapter(adapter1);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    int tang = Integer.parseInt(sotang.get(position));
                    rooms = new ArrayList<>();
                    for (int j = 1;j<=num_room;j++){
                        Room room = new Room(tang* 100 + j, "Trống", "vip", tang,100);
                        rooms.add(room);
                        database.addRoom(room);
                    }
                    adapter = new RoomAdapter(rooms, MainActivity.this, R.layout.room_item);
                    gridView.setAdapter(adapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }



        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // dialog show detail
                Toast.makeText(MainActivity.this, "Xem chi tiet", Toast.LENGTH_SHORT).show();
            }
        });
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //show dialog edit and delete room
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                final Room room = rooms.get(position);
                final String action;
                if(room.getStatus().equals("Trống")) action = "Cho thuê";
                else action = "Trả phòng";
                CharSequence[] items = {"Sửa", "Xoá",action};
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0: {
                                //show dialog edit
                                showDialogEdit(MainActivity.this,room);
                                break;
                            }
                            case 1: {
                                //show dialog delete
                                final AlertDialog.Builder deleteDialog = new AlertDialog.Builder(MainActivity.this);
                                deleteDialog.setTitle("Xoá phòng");
                                deleteDialog.setMessage("Bạn có muốn xoá phòng?");
                                deleteDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //
                                        Toast.makeText(MainActivity.this, "Xoa", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                deleteDialog.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                deleteDialog.show();
                                break;
                            }
                            case 2:{
                                if(action.equals("Cho thuê")){
                                    //show dilaog cho thue
                                    showDialogRent(MainActivity.this,room);
                                }else{
                                    //show dialog tra phong
                                    showDialogReturn(room);
                                }
                                break;
                            }
                        }
                    }
                });
                dialog.show();
                return true;
            }
        });


    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_addFloor:
                showDialogAddFloor(this);
                break;
            case R.id.tv_status_hotel:
                showDialogStatus(this);
                break;
            case R.id.tv_thongke:
                break;
            case R.id.tv_logout:
                break;
        }
    }
    private void showDialogEdit(Activity activity, final Room room) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_edit);
       final EditText edt_nr, edt_status,edt_type, edt_price;
       Button btn_ok, btn_cancel;

       edt_nr = dialog.findViewById(R.id.edt_nr);
       edt_status = dialog.findViewById(R.id.edt_room_status);
       edt_price = dialog.findViewById(R.id.edt_room_price);
       edt_type = dialog.findViewById(R.id.edt_room_type);
       btn_cancel = dialog.findViewById(R.id.btn_cancel);
       btn_ok = dialog.findViewById(R.id.btn_ok);
       edt_nr.setText(""+room.getNumber());
       edt_status.setText(room.getStatus());
       edt_price.setText(""+room.getPrice());
       edt_type.setText(room.getType());
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.5);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                room.setPrice(Integer.parseInt(edt_price.getText().toString()));
                room.setType(edt_type.getText().toString());
                //save to database
                database.updateRoom(room);
                dialog.dismiss();
            }
        });
    }

    private void showDialogReturn(Room room) {
        Toast.makeText(MainActivity.this, "Đã trả phòng", Toast.LENGTH_SHORT).show();
        room.setStatus("Trống");
        database.updateRoom(room);
    }

    private void showDialogRent(Activity activity, final Room room) {
        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_rent);
        final EditText edt_time_in, edt_time_out,edt_price,edt_nr;
        Button btn_ok, btn_cancel;
        edt_time_in = dialog.findViewById(R.id.edt_time_in);
        edt_time_out = dialog.findViewById(R.id.edt_time_out);
        edt_nr = dialog.findViewById(R.id.edt_nr);
        edt_price = dialog.findViewById(R.id.edt_room_price);
        btn_cancel = dialog.findViewById(R.id.btn_cancel);
        btn_ok = dialog.findViewById(R.id.btn_ok);
        edt_nr.setText(""+room.getNumber());
        edt_price.setText(""+room.getPrice());
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.5);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String timeIn = edt_time_in.getText().toString();
                String timeOut = edt_time_out.getText().toString();
               RentedRoom rentedRoom = new RentedRoom(timeIn,timeOut,room);
               room.setStatus("Bận");
                //save to database
                database.updateRoom(room);
                database.addRentedRoom(rentedRoom);
            }
        });
    }
    private void showDialogStatus(Activity activity) {
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_status);
        TextView tv_num_fl, tv_num_room, tv_num_free, tv_num_rent;
        tv_num_fl = dialog.findViewById(R.id.tv_num_fl);
        tv_num_room = dialog.findViewById(R.id.tv_num_nr);
        tv_num_free = findViewById(R.id.tv_nr_free);
        tv_num_rent = findViewById(R.id.tv_nr_rent);

        tv_num_fl.setText(""+num_fl);
        tv_num_room.setText(""+num_room);
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.5);
        dialog.getWindow().setLayout(width, height);
        dialog.show();
    }

    public void showDialogAddFloor(Activity activity) {
        Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_addfloor);
        final EditText edt_numberfloor, edt_number_room;
        edt_numberfloor = dialog.findViewById(R.id.edt_numberfloor);
        edt_number_room = dialog.findViewById(R.id.edt_numberroom);
        Button btn_ok;

        btn_ok = dialog.findViewById(R.id.btn_ok);
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels);
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.5);
        dialog.getWindow().setLayout(width, height);
        dialog.show();
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int number_fl = Integer.parseInt(String.valueOf(edt_numberfloor.getText()));
                int number_room = Integer.parseInt(String.valueOf(edt_number_room.getText()));

                Toast.makeText(MainActivity.this, "" + edt_numberfloor.getText().toString() + "-" + edt_number_room.getText().toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                //save in sharedpreference
                sharedpref.saveFloor(number_fl,number_room);
                startActivity(intent);
            }
        });
    }
}