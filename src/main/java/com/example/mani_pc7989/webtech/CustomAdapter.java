package com.example.mani_pc7989.webtech;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class CustomAdapter extends ArrayAdapter<String> {

     Activity context;
     String[] itemname;
     String[] imgid;
    String[] itemdescription;


    public CustomAdapter(Activity context, String[] itemname, String[] itemdescription, String[] imgid) {
        super(context, R.layout.legislators_list_item, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.itemname=itemname;
        this.itemdescription = itemdescription;
        this.imgid=imgid;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.legislators_list_item, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.leg_name);
        TextView extratxt = (TextView) rowView.findViewById(R.id.leg_desc);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.leg_img);

        txtTitle.setText(itemname[position]);
        Picasso.with(getContext())
                .load(imgid[position])
                .resize(250,250)
                .into(imageView);

        extratxt.setText(itemdescription[position]);
        return rowView;

    };
}