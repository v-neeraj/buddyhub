package com.example.neeraj.buddyhub;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class MyGeolocationService extends IntentService {

    public MyGeolocationService() {
        super("");

    }
    public MyGeolocationService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Double latitude=intent.getDoubleExtra("lat",0);
        Double longitude=intent.getDoubleExtra("long",0);
        ResultReceiver receiver = intent.getParcelableExtra("reciever");

        Geocoder geocoder;
        List<Address> addresses=null;
        geocoder = new Geocoder(this, Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName();// Here 1 represent max location result to returned, by documents it recommended 1 to 5
            Bundle bundle = new Bundle();
            bundle.putString("city", city);
            receiver.send(Activity.RESULT_OK, bundle);
            //return Service.START_NOT_STICKY;
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
}
