package com.example.neeraj.buddyhub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {


    private final String city_names[] = {
            "Donut",
            "Eclair",
            "Froyo",
            "Gingerbread",
            "Honeycomb",
            "Ice Cream Sandwich",
            "Jelly Bean",
            "KitKat",
            "Lollipop",
            "Marshmallow"
    };

    private final String city_image_urls[] = {
            "https://api.learn2crack.com/android/images/donut.png",
            "https://api.learn2crack.com/android/images/eclair.png",
            "https://api.learn2crack.com/android/images/froyo.png",
            "https://api.learn2crack.com/android/images/ginger.png",
            "https://api.learn2crack.com/android/images/honey.png",
            "https://api.learn2crack.com/android/images/icecream.png",
            "https://api.learn2crack.com/android/images/jellybean.png",
            "https://api.learn2crack.com/android/images/kitkat.png",
            "https://api.learn2crack.com/android/images/lollipop.png",
            "https://api.learn2crack.com/android/images/marshmallow.png"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        final ArrayList property = prepareData();
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(80));
         Areacardviewadapter.RecyclerViewCardClickListener recyclerViewCardClickListener=new Areacardviewadapter.RecyclerViewCardClickListener() {
         @Override
         public void onClick(View v, int position) {
             Log.d("a","a");
             Intent intent=new Intent(SearchActivity.this,Property_Description.class);
             startActivity(intent);
         }
          };
        Areacardviewadapter areacardviewadapter=new Areacardviewadapter(getApplicationContext(),property,recyclerViewCardClickListener);
        recyclerView.setAdapter(areacardviewadapter);

    }
    private ArrayList prepareData(){

        ArrayList property = new ArrayList<>();
        for(int i=0;i<city_names.length;i++){
            property propertyObj = new property();
            propertyObj.setHouse_image(city_image_urls[i]);
            propertyObj.setHouse_bhk(city_names[i]);
            property.add(propertyObj);
        }
        return property;
    }
}
