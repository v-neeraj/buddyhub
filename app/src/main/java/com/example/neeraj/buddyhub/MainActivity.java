package com.example.neeraj.buddyhub;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    SharedPreferences sharedpreferences;
    private ProgressBar spinner;
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
            public void onResponse(Call call, Response response)
                    throws IOException {
                Log.d("Response",response.body().string());
                runOnUiThread(new Runnable() {
                    public void run() {
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
}
