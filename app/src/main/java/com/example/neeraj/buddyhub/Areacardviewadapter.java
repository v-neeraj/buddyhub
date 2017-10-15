package com.example.neeraj.buddyhub;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ankitgarg on 04/10/17.
 */

public class Areacardviewadapter extends RecyclerView.Adapter<Areacardviewadapter.ViewHolder> {


    private Context context;
    private ArrayList<property> Properties;
    private RecyclerViewCardClickListener mlistener;
    public interface RecyclerViewCardClickListener{
         void onClick(View v,int position);
    }

    public Areacardviewadapter(Context context, ArrayList<property> properties,RecyclerViewCardClickListener mlistener) {
        this.context = context;
        this.Properties = properties;
        this.mlistener=mlistener;
    }
    @Override
    public Areacardviewadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.home_searchpage,parent,false);

        return new ViewHolder(view,mlistener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
          holder.house_bhk.setText(Properties.get(position).getHouse_bhk());
        Picasso.Builder builder=new Picasso.Builder(context);
        builder.listener(new Picasso.Listener()
        {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
            {
                exception.printStackTrace();
            }
        });
        builder.build().load(Properties.get(position).getHouse_image()).placeholder(R.drawable.ppp).into(holder.house_image);

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
       TextView house_bhk;
        ImageView house_image;
        private RecyclerViewCardClickListener mlistener;
        public ViewHolder(View itemView,RecyclerViewCardClickListener listener) {
            super(itemView);
            house_bhk = (TextView)itemView.findViewById(R.id.house_bhk);
            house_image = (ImageView)itemView.findViewById(R.id.img_home);
            mlistener=listener;
            itemView.setOnClickListener(this);
        }



        @Override
        public void onClick(View view) {
            mlistener.onClick(view, getAdapterPosition());
        }
    }

    @Override
    public int getItemCount() {
        return Properties.size();
    }
}
