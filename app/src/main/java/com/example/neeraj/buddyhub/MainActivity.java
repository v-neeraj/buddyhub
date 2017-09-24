package com.example.neeraj.buddyhub;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {


    SharedPreferences sharedpreferences;
    private ProgressBar spinner;
    public static final String MY_PREFS_NAME="City";
    MyResultReceiver resultReceiver;
    public  Context mcontext=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        //checkForDataStorage();
        setContentView(R.layout.activity_main);
        spinner = (ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE);
        whenAsynchronousGetRequest_thenCorrect();
    }

    public void  checkForDataStorage(){
        sharedpreferences = getSharedPreferences("MyPref", MODE_PRIVATE);

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
    public void setselectList(){
        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String restoredText = prefs.getString("CityObj", null);
        if (restoredText != null) {


            try {

                JSONObject obj = new JSONObject(restoredText);
                JSONObject obj2=obj.getJSONObject("data");
                JSONArray arr = obj2.getJSONArray("cities");
                final ArrayList<String> listdata = new ArrayList<String>();

                if (arr != null) {
                    for (int i=0;i<arr.length();i++){
                        listdata.add(arr.getString(i));
                    }
                }
                //List<String> wordList = new ArrayList<String>(Arrays.asList(arr));

                BuddyGps bps=new BuddyGps(this);
                Location lc=bps.getLocation();
                Intent intent=new Intent(this,MyGeolocationService.class);

                intent.putExtra("reciever", new ResultReceiver(new Handler()) {
                    @Override
                    protected void onReceiveResult(int resultCode, Bundle resultData) {
                        super.onReceiveResult(resultCode, resultData);

                        if (resultCode == Activity.RESULT_OK) {
                            String val = resultData.getString("city");
                            if(listdata.contains(val)){
                                Spinner spinnerk=(Spinner)findViewById(R.id.spinner1);
                                ArrayAdapter adapter=new ArrayAdapter(mcontext,R.layout.support_simple_spinner_dropdown_item, (List) listdata);
                                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                                spinnerk.setAdapter(adapter);

                            }
                            else{
                                Spinner spinnerk=(Spinner)findViewById(R.id.spinner1);
                                ArrayAdapter adapter=new ArrayAdapter(mcontext,R.layout.support_simple_spinner_dropdown_item, (List) listdata);
                                adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

                                spinnerk.setAdapter(adapter);
                                int a=listdata.indexOf("Noida");
                                spinnerk.setSelection(a);
                            }
                        } else {
                            Toast.makeText(mcontext,"Some Error",Toast.LENGTH_SHORT);
                        }
                    }
                });

                intent.putExtra("lat",lc.getLatitude());
                intent.putExtra("long",lc.getLongitude());
                startService(intent);
                Log.d("aa","ss");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private class MyResultReceiver {
    }
}
