package com.example.mani_pc7989.webtech;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by MANI-PC on 11/23/2016.
 */

public class Leg_ListView {

    public ImageView Img;
    public String Name;
    public String Description;

    public Leg_ListView(ImageView img,String name, String description){
        super();
        this.Img=img;
        this.Name=name;
        this.Description =description;
    }

}

class Leg_ListView_Adapter{

    Context context;
    Leg_ListView[] list=null;
    int layoutResourceId;


    public  Leg_ListView_Adapter(Context contex,int layoutResource, Leg_ListView[] data){
        //super(contex,layoutResource,data);
        this.layoutResourceId = layoutResource;
        this.context = contex;
        this.list = data;
    }


    static class Leg_ListView_Holder{
        ImageView Img;
        TextView Name;
        TextView Description;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        Leg_ListView_Holder holder = null;

        try {
            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                //View view  = inflater.inflate(R.layout.fragment_legislators, container, false);

                row = inflater.inflate(layoutResourceId, parent, false);

                /*
                holder = new Leg_ListView_Holder();
                holder.Img = (ImageView) row.findViewById(R.id.leg_img);
                holder.Name = (TextView) row.findViewById(R.id.leg_name);
                holder.Description = (TextView) row.findViewById(R.id.leg_desc);
                */

                row.setTag(holder);
            } else {
                holder = (Leg_ListView_Holder) row.getTag();
            }

            Leg_ListView listItem = list[position];

            //holder.Img.setImageBitmap(null);
            holder.Name.setText(listItem.Name);
            holder.Description.setText(listItem.Description);

        } catch (Exception exp) {
            int x = 10;
        }
        return row;
    }


}
