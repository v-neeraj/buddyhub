package com.example.neeraj.buddyhub;

import android.content.Context;
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

    private Context mcontext;

    public httpCalls(Context context){
        this.mcontext=context;
    }

    public void whenAsynchronousGetRequest_thenCorrect() {
        String api=mcontext.getResources().getString(R.string.base_api);
        OkHttpClient httpClient=new OkHttpClient();
        Request request = new Request.Builder()
                .url(api+"fetchdata/getcitylist")
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
