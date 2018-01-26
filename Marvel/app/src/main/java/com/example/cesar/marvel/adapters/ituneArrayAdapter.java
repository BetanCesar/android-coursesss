package com.example.cesar.marvel.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import  com.example.cesar.marvel.pojo.itune;

/**
 * Created by Cesar on 26/01/18.
 */

public class ituneArrayAdapter extends ArrayAdapter<itune> {

    private ArrayList<itune> arrayList;

    public ituneArrayAdapter(Context context, int resource, List<itune> objects) {
        super(context, resource, objects);
        arrayList = (ArrayList<itune>) objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
