package com.example.hotelmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RoomAdapter extends BaseAdapter {
    private ArrayList<Room> rooms;
    Context context;
    private int resource;

    public RoomAdapter(ArrayList<Room> rooms, Context context, int resource) {
        this.rooms = rooms;
        this.context = context;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return rooms.size();
    }

    @Override
    public Object getItem(int position) {
        return rooms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resource,null);
            viewHolder= new ViewHolder();
            viewHolder.tv_number = convertView.findViewById(R.id.tv_number);
             viewHolder.tv_status = convertView.findViewById(R.id.tv_status);
             viewHolder.tv_type = convertView.findViewById(R.id.tv_type);
             viewHolder.tv_price = convertView.findViewById(R.id.tv_price);
                convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Room room = rooms.get(position);
        if(room!=null){
            viewHolder.tv_number.setText(""+room.getNumber());
            viewHolder.tv_type.setText(room.getType());
            viewHolder.tv_status.setText(room.getStatus());
            viewHolder.tv_price.setText(""+room.getPrice());
        }
        return convertView;
    }
    public class ViewHolder{
        TextView tv_number, tv_status, tv_type, tv_price, tv_amount;
    }
}
