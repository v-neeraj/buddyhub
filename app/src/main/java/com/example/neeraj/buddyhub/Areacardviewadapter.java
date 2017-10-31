package com.example.neeraj.buddyhub;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ankitgarg on 04/10/17.
 */

public class Areacardviewadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private ArrayList<property> Properties;
    private RecyclerViewCardClickListener mlistener;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    public interface RecyclerViewCardClickListener{
         void onClick(View v,int position);
    }

    public Areacardviewadapter(Context context, ArrayList<property> properties,RecyclerViewCardClickListener mlistener) {
        this.context = context;
        this.Properties = properties;
        this.mlistener=mlistener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {



        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_searchpage,parent,false);
            return new UserViewHolder(view,mlistener);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progress_bar_recycleview,parent,false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            userViewHolder.house_bhk.setText(Properties.get(position).getHouse_bhk());
            Picasso.Builder builder = new Picasso.Builder(context);
            builder.listener(new Picasso.Listener() {
                @Override
                public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                    exception.printStackTrace();
                }
            });
            builder.build().load(Properties.get(position).getHouse_image()).fit().placeholder(R.drawable.ppp).into(userViewHolder.house_image);
        }
        else{
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
       TextView house_bhk;
        ImageView house_image;
        private RecyclerViewCardClickListener mlistener;
        public UserViewHolder(View itemView,RecyclerViewCardClickListener listener) {
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

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressSpinner);
        }
    }
    @Override
    public int getItemViewType(int position) {
        Log.i("current position=>", "property size=>"+String.valueOf(Properties.size()) + " position=>"+String.valueOf(position));
        if(Properties.get(position) == null)
            return VIEW_TYPE_LOADING;
        return ( position >= Properties.size()+5 ) ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
       // return Properties.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }
    @Override
    public int getItemCount() {
        return Properties.size();
    }
}
