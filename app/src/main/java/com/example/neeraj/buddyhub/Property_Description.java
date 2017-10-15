package com.example.neeraj.buddyhub;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class Property_Description extends AppCompatActivity {

    Home_Description_FragmentAdapter customAdapter;
    ViewPager viewPager;
    static int  totalItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_description);
        List<Image_Fragment_Object> getData = dataSource();
        totalItems=getData.size();

        customAdapter = new Home_Description_FragmentAdapter(getSupportFragmentManager(), Property_Description.this,dataSource());

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(customAdapter);
    }
    public List<Image_Fragment_Object> dataSource(){
        List<Image_Fragment_Object> data = new ArrayList<Image_Fragment_Object>();
        data.add(new Image_Fragment_Object(R.drawable.image1, "Ferrari"));
        data.add(new Image_Fragment_Object(R.drawable.image2, "BMW"));
        data.add(new Image_Fragment_Object(R.drawable.image3,"AAA"));
        return data;
    }
}
