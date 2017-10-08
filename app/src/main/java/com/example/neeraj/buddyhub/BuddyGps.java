/**
 * Created by ankitgarg on 21/09/17.
 */

package com.example.neeraj.buddyhub;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by ankit.garg on 9/21/2017.
 */
public class BuddyGps  implements LocationListener {

    private Context mContext;

    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;


    public BuddyGps(Context context) {
        this.mContext = context;
        //getLocation();
    }

    public BuddyGps() {

    }

    public Location getLocation() {
        try {

            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);


            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true;
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    if (ContextCompat.checkSelfPermission((Activity) mContext,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            != PackageManager.PERMISSION_GRANTED) {


                        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext,
                                Manifest.permission.ACCESS_FINE_LOCATION)) {

                           Toast.makeText(mContext,"It is required for App function",Toast.LENGTH_SHORT).show();

                        } else {

                            ActivityCompat.requestPermissions((Activity)mContext,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},MY_PERMISSIONS_REQUEST_LOCATION);
                            /*ActivityCompat.requestPermissions((Activity) mContext,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_LOCATION
                            );*/
                       }
                    }
                    else{
                        locatioExtractor();
                    }
                }

            }

        }

     catch(Exception e)

    {
        e.printStackTrace();
    }
return location;

}




    public void locatioExtractor(){
        if (ActivityCompat.checkSelfPermission((Activity)mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission((Activity)mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(mContext, "permission denied", Toast.LENGTH_LONG).show();
        }
        else{
            List<String> providers = locationManager.getProviders(true);
            Location bestLocation = null;
            locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    MIN_TIME_BW_UPDATES,
                    MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
            Log.d("Network", "Network");
            if (locationManager != null) {
                for (String provider : providers) {
                    Location l = locationManager.getLastKnownLocation(provider);

                    if (l == null) {
                        continue;
                    }
                    if (bestLocation == null
                            || l.getAccuracy() < bestLocation.getAccuracy()) {

                        bestLocation = l;
                    }
                }
                if (bestLocation != null) {
                    ((MainActivity)this.mContext).processingAfterLocation(bestLocation);
                }
            }


        }

        //((MainActivity)this.mContext).processingAfterLocation(location);
    }



    @Override
    public void onLocationChanged(Location location) {
        ((MainActivity)this.mContext).processingAfterLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


}