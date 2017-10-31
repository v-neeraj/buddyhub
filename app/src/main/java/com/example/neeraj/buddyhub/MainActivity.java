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
import android.os.HandlerThread;
import android.os.Message;
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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
import java.util.Iterator;
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
    Boolean check=false;
    private ProgressBar spinner;
    private Spinner spinnerk;
    public static final String MY_PREFS_NAME="City";
    MyResultReceiver resultReceiver;
    public  Context mcontext=this;
    Handler mHandler;
    HandlerThread mHandlerThread;
    PlacesAutoCompleteAdapter placesAutoCompleteAdapter;
    private static String TAG = MainActivity.class.getSimpleName();
    public static  ArrayList listdata;
    public long delay = 1000; // 1 seconds after user stops typing
    public long last_text_edit = 0;
    public static Message m;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        //checkForDataStorage();
        setContentView(R.layout.activity_main);
         spinnerk=(Spinner)findViewById(R.id.spinner1);
        spinnerk.setVisibility(View.GONE);
        spinner = (ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);
        ProfileFragment();
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
    public void processingAfterLocation() {

                        // Spinner spinnerk=(Spinner)findViewById(R.id.spinner1);
                        //ArrayAdapter adapter=new ArrayAdapter(mcontext,R.layout.support_simple_spinner_dropdown_item, (List) listdata);
                        //adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                       // spinnerk.setAdapter(adapter);
                        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
                        recyclerView.setHasFixedSize(true);
                        final ArrayList cities = listdata;
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
                        listAdapter.RecyclerViewClickListener lss=new listAdapter.RecyclerViewClickListener() {
                            @Override
                            public void onClick(View view, int position) {
                                Log.d("a","a");
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
                        AutoCompleteTextView autocompleteView = (AutoCompleteTextView) findViewById(R.id.autocomplete);
                        autocompleteView.setThreshold(1);
                        placesAutoCompleteAdapter=new PlacesAutoCompleteAdapter(mcontext, R.layout.autocomplete_list_view);
                        autocompleteView.setAdapter(placesAutoCompleteAdapter);
                        autocompleteView.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                final String type=charSequence.toString();
                                mHandler.removeCallbacksAndMessages(null);
                                mHandler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        if(check==false){
                                            check=true;
                                            CityPlacesApi placeAPI=new CityPlacesApi();
                                            placesAutoCompleteAdapter.resultList =placeAPI.autocomplete(type);
                                            check=false;
                                            mHandler.sendEmptyMessage(1);

                                        }

                                    }
                                },500);


                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });

                        recyclerView.setLayoutManager(layoutManager);


                        listAdapter myadapter = new listAdapter(getApplicationContext(),cities,lss);
                        recyclerView.setAdapter(myadapter);





        /*intent.putExtra("lat",location.getLatitude());
        intent.putExtra("long",location.getLongitude());
        startService(intent);*/
        Log.d("aa","ss");


    }

    public void ProfileFragment() {
        // Required empty public constructor

        if (mHandler == null) {
            // Initialize and start the HandlerThread
            // which is basically a Thread with a Looper
            // attached (hence a MessageQueue)
            mHandlerThread = new HandlerThread(TAG, android.os.Process.THREAD_PRIORITY_BACKGROUND);
            mHandlerThread.start();
            //Thread c = Thread.currentThread();

            // Initialize the Handler
            mHandler = new Handler(mHandlerThread.getLooper()) {
                @Override
                public void handleMessage(final Message msg) {

                    final int whatvar = msg.what;



                    MainActivity.this.runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            if (whatvar == 1) {
                                ArrayList<String> results = placesAutoCompleteAdapter.resultList;

                                if (results != null && results.size() > 0) {
                                    placesAutoCompleteAdapter.notifyDataSetChanged();
                                }
                                else {

                                    placesAutoCompleteAdapter.notifyDataSetInvalidated();
                                }
                            }
                        }
                    });


                }
            };}}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandlerThread.quit();
        }
    }

    public void setselectList(){
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("CityObj", null);
        if (restoredText != null) {


            try {

                JSONObject obj = new JSONObject(restoredText);

                JSONObject obj2=obj.getJSONObject("data");
                JSONArray arr = obj2.getJSONArray("cities");
                listdata = new ArrayList<>();
                for (int i=0;i<arr.length();i++){
                    JSONObject jsonObject= (JSONObject) arr.get(i);
                    String url= (String) jsonObject.get("url");
                    String city= (String) jsonObject.get("name");
                    cities cityObj = new cities();
                    cityObj.setCity_name(city);
                    cityObj.setCity_image(url);
                    listdata.add(cityObj);
                }






                processingAfterLocation();
                //List<String> wordList = new ArrayList<String>(Arrays.asList(arr));

                /*BuddyGps bps=new BuddyGps(this);
                Location lc=bps.getLocation();*/


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
