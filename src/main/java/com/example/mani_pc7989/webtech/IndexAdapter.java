package com.example.mani_pc7989.webtech;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by MANI-PC on 11/29/2016.
 */


public class IndexAdapter extends ArrayAdapter<String> {

    Activity context;
    List<String> itemname;



    public IndexAdapter(Activity context, List<String> itemname) {
        super(context, R.layout.legislators_list_item, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;

        this.itemname=itemname;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.indexlayout, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id._char);
        txtTitle.setText(itemname.get(position));
        return rowView;

    };
}