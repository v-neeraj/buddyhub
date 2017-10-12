package com.example.neeraj.buddyhub;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ankitgarg on 08/10/17.
 */

public class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable{



    ArrayList<String> resultList;
    Context context;
    int mResource;

     CityPlacesApi cityPlacesApi=new CityPlacesApi();
    public PlacesAutoCompleteAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
        mResource=resource;
        this.context = context;
    }
    @Override
    public int getCount() {
        return resultList.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return resultList.get(position);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(getContext()).inflate(R.layout.autocomplete_list_view,null);
        TextView textView=(TextView) view.findViewById(R.id.autocompleteText);
        textView.setText(resultList.get(position));
        return view;
    }
}
