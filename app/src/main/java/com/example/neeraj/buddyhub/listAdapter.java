package com.example.neeraj.buddyhub;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ankitgarg on 29/09/17.
 */

public class listAdapter extends RecyclerView.Adapter<listAdapter.ViewHolder>{
    private RecyclerViewClickListener mListener;
    private ArrayList<cities> Cities;
    private Context context;
    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
    }


    public listAdapter(Context context,ArrayList<cities> Cities,RecyclerViewClickListener listener) {
        this.context = context;
        this.Cities = Cities;
        this.mListener=listener;

    }

    @Override
    public listAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout, viewGroup, false);
        return new ViewHolder(view,mListener );
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.city_name.setText(Cities.get(i).getCity_name());
        Picasso.with(context).load(Cities.get(i).getCity_image()).placeholder(R.drawable.ppp).resize(120, 60).into(viewHolder.city_image);
    }

    @Override
    public int getItemCount() {
        return Cities.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView city_name;
        ImageView city_image;
        private RecyclerViewClickListener mListener;
        public ViewHolder(View view,RecyclerViewClickListener listener) {
            super(view);

            city_name = (TextView)view.findViewById(R.id.tv_android);
            city_image = (ImageView)view.findViewById(R.id.img_android);
            mListener = listener;
            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {
            mListener.onClick(view, getAdapterPosition());
        }

    }
}
