package com.example.neeraj.buddyhub;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ankitgarg on 18/09/17.
 */

public class httpCalls {

    public void whenAsynchronousGetRequest_thenCorrect() {
        OkHttpClient httpClient=new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://52.77.1.30:8000/fetchdata/getcitylist")
                .build();

        Call call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            public void onResponse(Call call, Response response)
                    throws IOException {
                Log.d("Response", response.toString());

            }

            public void onFailure(Call call, IOException e) {

            }
        });
    }
}
