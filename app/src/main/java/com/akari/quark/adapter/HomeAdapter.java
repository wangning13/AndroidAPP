package com.akari.quark.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by Akari on 16/5/4.
 */
public class HomeAdapter<T> extends ArrayAdapter<T>{


    public HomeAdapter(Context context, int resource, List<T> objects) {
        super(context, resource, objects);
    }
}
