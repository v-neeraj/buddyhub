package com.example.neeraj.buddyhub;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Splashcreen extends AppCompatActivity {

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashcreen);
        Showprogressbar();
     }

    public void Showprogressbar(){
        progressBar=(ProgressBar)findViewById(R.id.progressBar3);
        progressBar.setVisibility(View.VISIBLE);

        if(isWorkingInternetPersent()){
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {

                /* Create an Intent that will start the Menu-Activity. */
                    progressBar.setVisibility(View.INVISIBLE);
                    Intent mainIntent = new Intent(Splashcreen.this,MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
            }, 3000);
        }
        else{
            new Handler().postDelayed(new Runnable(){
                @Override
                public void run() {

                    progressBar.setVisibility(View.INVISIBLE);
                    showAlertDialog(Splashcreen.this, "Internet Connection",
                            "You don't have internet connection", false);
                }
            }, 3000);

        }
    }

    public void showAlertDialog(Context context, String title, String message, Boolean status){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);

        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        System.exit(0);
                    }
                }).setNegativeButton("Retry",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Showprogressbar();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }
    public Boolean isWorkingInternetPersent(){
        ConnectivityManager cm =(ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if(activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting()){
            return true;
        }
        else
            return false;




    }



}
