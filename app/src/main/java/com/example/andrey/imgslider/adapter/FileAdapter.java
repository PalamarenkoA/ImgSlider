package com.example.andrey.imgslider.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andrey.imgslider.R;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by andrey on 15.01.16.
 */
public class FileAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<String> name;


    public FileAdapter(Context context, ArrayList name) {
        ctx = context;
        this.name = name;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }



    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.itemfile, parent, false);
        }
        File file = new File(name.get(position));
        ((TextView) view.findViewById(R.id.textView)).setText(file.getName());
        if(file.isDirectory()){
            ((ImageView) view.findViewById(R.id.imageView2)).setImageResource(R.drawable.ic_file);
        }else{
            if( file.getName().regionMatches(file.getName().length()-3,"jpg", 0, 3)) {
                ((ImageView) view.findViewById(R.id.imageView2)).setImageResource(R.drawable.ic_jpg);}
            else {
                ((ImageView) view.findViewById(R.id.imageView2)).setImageResource(R.drawable.ic_ather);}
        }


        return view;
    }
    // пункт списка






}
