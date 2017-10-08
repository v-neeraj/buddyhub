package com.example.neeraj.buddyhub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.neeraj.buddyhub.BuddyGps.MY_PERMISSIONS_REQUEST_LOCATION;
import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {


    SharedPreferences sharedpreferences;
    private ProgressBar spinner;
    private Spinner spinnerk;
    public static final String MY_PREFS_NAME="City";
    MyResultReceiver resultReceiver;
    public  Context mcontext=this;
    public static  ArrayList listdata;
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
            "http://api.learn2crack.com/android/images/donut.png",
            "http://api.learn2crack.com/android/images/eclair.png",
            "http://api.learn2crack.com/android/images/froyo.png",
            "http://api.learn2crack.com/android/images/ginger.png",
            "http://api.learn2crack.com/android/images/honey.png",
            "http://api.learn2crack.com/android/images/icecream.png",
            "http://api.learn2crack.com/android/images/jellybean.png",
            "http://api.learn2crack.com/android/images/kitkat.png",
            "http://api.learn2crack.com/android/images/lollipop.png",
            "http://api.learn2crack.com/android/images/marshmallow.png"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        //checkForDataStorage();
        setContentView(R.layout.activity_main);
         spinnerk=(Spinner)findViewById(R.id.spinner1);
        spinnerk.setVisibility(View.GONE);
        spinner = (ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);
        whenAsynchronousGetRequest_thenCorrect();
    }

    public void  checkForDataStorage(){
        sharedpreferences = getSharedPreferences("MyPref", MODE_PRIVATE);

    }
    public void trasferActivities(View view){
        Intent intent=new Intent(getApplicationContext(),SearchActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void whenAsynchronousGetRequest_thenCorrect() {
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();


       if(activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting()){
           OkHttpClient httpClient=new OkHttpClient();
           final Request request = new Request.Builder()
                   .url("http://52.77.1.30:8000/fetchdata/getcitylist")
                   .build();

           Call call = httpClient.newCall(request);
           call.enqueue(new Callback() {
               public void onResponse(Call call, final Response response)
                       throws IOException {
                   //Log.d("Response",response.body().string());
                   SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                   try {
                       String jsonData = response.body().string();
                       JSONObject Jobject = new JSONObject(jsonData);
                       editor.putString("CityObj",Jobject.toString());
                   } catch (JSONException e){
                       e.printStackTrace();
                   }


                   editor.apply();
                   runOnUiThread(new Runnable() {
                       public void run() {

                           // String jsonData = null;

                           setselectList();
                           spinner.setVisibility(View.GONE);

                       }
                   });

               }

               public void onFailure(Call call, IOException e) {
                   runOnUiThread(new Runnable() {
                       public void run() {
                           spinner.setVisibility(View.GONE);

                       }
                   });
               }
           });

       }
       else{
           Toast.makeText(this,"No network",Toast.LENGTH_SHORT);
           finish();
       }

    }
    public void processingAfterLocation(Location location) {
        Intent intent=new Intent(this,MyGeolocationService.class);
        intent.putExtra("reciever", new ResultReceiver(new Handler()) {
            @Override
            protected void onReceiveResult(int resultCode, Bundle resultData) {
                super.onReceiveResult(resultCode, resultData);

                if (resultCode == Activity.RESULT_OK) {
                    String val = resultData.getString("city");
                    if(listdata.contains(val)){
                       // Spinner spinnerk=(Spinner)findViewById(R.id.spinner1);
                        //ArrayAdapter adapter=new ArrayAdapter(mcontext,R.layout.support_simple_spinner_dropdown_item, (List) listdata);
                        //adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                       // spinnerk.setAdapter(adapter);
                        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
                        recyclerView.setHasFixedSize(true);
                        final ArrayList cities = prepareData();
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                        listAdapter.RecyclerViewClickListener lss=new listAdapter.RecyclerViewClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                Log.d("a","a");
                                PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                                        getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

                                AutocompleteFilter typeFilter = new AutocompleteFilter.Builder().setCountry("IN")
                                        .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                                        .build();
                                autocompleteFragment.setFilter(typeFilter);

                                autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                                    @Override
                                    public void onPlaceSelected(Place place) {
                                        // TODO: Get info about the selected place.
                                        Log.i("pp", "Place: " + place.getName());//get place details here
                                    }

                                    @Override
                                    public void onError(Status status) {
                                        // TODO: Handle the error.
                                        Log.i("pp", "An error occurred: " + status);
                                    }
                                });
                                cities cityObj = new cities();
                                cityObj= (com.example.neeraj.buddyhub.cities) cities.get(position);
                                String city=cityObj.getCity_name();
                                 spinnerk=(Spinner)findViewById(R.id.spinner1);
                                ArrayAdapter adapter=new ArrayAdapter(mcontext,R.layout.support_simple_spinner_dropdown_item, (List) listdata);
                                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                spinnerk.setVisibility(View.VISIBLE);
                                spinnerk.setAdapter(adapter);
                            }
                        };

                        recyclerView.setLayoutManager(layoutManager);


                        listAdapter myadapter = new listAdapter(getApplicationContext(),cities,lss);
                        recyclerView.setAdapter(myadapter);
                    }
                    else{
                       // Spinner spinnerk=(Spinner)findViewById(R.id.spinner1);
                        //ArrayAdapter adapter=new ArrayAdapter(mcontext,R.layout.support_simple_spinner_dropdown_item, (List) listdata);
                        //adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

                       // spinnerk.setAdapter(adapter);
                        //int a=listdata.indexOf("Noida");
                        //spinnerk.setSelection(a);
                    }
                } else {
                    Toast.makeText(mcontext,"Some Error",Toast.LENGTH_SHORT);
                }
            }
        });

        intent.putExtra("lat",location.getLatitude());
        intent.putExtra("long",location.getLongitude());
        startService(intent);
        Log.d("aa","ss");


    }
    private ArrayList prepareData(){

        ArrayList cities = new ArrayList<>();
        for(int i=0;i<city_names.length;i++){
            cities cityObj = new cities();
            cityObj.setCity_name(city_names[i]);
            cityObj.setCity_image(city_image_urls[i]);
            cities.add(cityObj);
        }
        return cities;
    }
    public void setselectList(){
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("CityObj", null);
        if (restoredText != null) {


            try {

                JSONObject obj = new JSONObject(restoredText);
                JSONObject obj2=obj.getJSONObject("data");
                JSONArray arr = obj2.getJSONArray("cities");
                 listdata = new ArrayList<String>();

                if (arr != null) {
                    for (int i=0;i<arr.length();i++){
                        listdata.add(arr.getString(i));
                    }
                }
                //List<String> wordList = new ArrayList<String>(Arrays.asList(arr));

                BuddyGps bps=new BuddyGps(this);
                Location lc=bps.getLocation();


            } catch (JSONException e) {
                e.printStackTrace();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    BuddyGps bps=new BuddyGps(this);
                    bps.getLocation();
                    // return location;
                } else {


                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }

            }


        }
    }

    private class MyResultReceiver {
    }
}
