package ride.happyy.driver.services;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import ride.happyy.driver.app.App;
import ride.happyy.driver.config.Config;
import ride.happyy.driver.model.CurentLocation;
import ride.happyy.driver.model.ServerResponse;
import ride.happyy.driver.net.DataManager;
import ride.happyy.driver.net.NetworkCall;
import ride.happyy.driver.net.ResponseCallback;
import ride.happyy.driver.util.AppConstants;

/**
 * Created by Rajib Hossain on 27/11/2018.
 */

public class GoogleService extends Service implements LocationListener {

    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude, longitude;
    LocationManager locationManager;
    Location location;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    long notify_interval = 15000;
    public static String str_receiver = "servicetutorial.service.receiver";
    Intent intent;
    String phone="";
    NetworkCall mNetworkCall;
/*
    DatabaseRef userStatus =
            FirebaseDatabase.getInstance().getReference("users/<user_id>/status");
            userStatus.setValue("online");
            userStatus.onDisconnect.setValue("offline"); */

    public GoogleService() {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Context context = getApplicationContext();
        SharedPreferences prfs = context.getSharedPreferences("session", Context.MODE_PRIVATE);
         phone =prfs.getString("phone","");

      //  mTimer = new Timer();
      //  mTimer.schedule(new TimerTaskToGetLocation(), 10, notify_interval);
     //   intent = new Intent(str_receiver);
       fn_getlocation();
    }

    @Override
    public void onLocationChanged(Location location) {

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

     void fn_getlocation() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable) {

        } else {

            if (isNetworkEnable) {
                location = null;
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, this);
                if (locationManager!=null){
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location!=null){

                        Log.e("latitude",location.getLatitude()+"");
                        Log.e("longitude",location.getLongitude()+"");

                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        fn_update(location);
                    }
                }

            }


            if (isGPSEnable){
                location = null;
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,this);
                if (locationManager!=null){
                    location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    if (location!=null){
                        Log.e("latitude",location.getLatitude()+"");
                        Log.e("longitude",location.getLongitude()+"");
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        fn_update(location);
                    }
                }
            }


        }

    }

    private class TimerTaskToGetLocation extends TimerTask{
        @Override
        public void run() {

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    fn_getlocation();
                }
            });

        }
    }

    private void fn_update(Location location){
        mNetworkCall = new NetworkCall();
        Log.e("Test Phone from pref",phone+"lat"+String.valueOf(location.getLongitude()));
        Log.e("Test Phone from config",Config.getInstance().getPhone()+"lat"+String.valueOf(location.getLongitude()));

       // Toast.makeText(getApplicationContext(),"Test Phone from pref"+phone+"lat"+String.valueOf(location.getLongitude()),Toast.LENGTH_LONG).show();
        Config.getInstance().setCurrentLatitude(String.valueOf(location.getLongitude()));
        Config.getInstance().setCurrentLongitude(String.valueOf(location.getLongitude()));
        CurentLocation curentLocation=new CurentLocation(phone,String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));

        if(App.isNetworkAvailable()){
            mNetworkCall.updateDriverCurrentLocation(curentLocation, new ResponseCallback<ServerResponse>() {
                @Override
                public void onSuccess(ServerResponse data) {
             //  Toast.makeText(getApplicationContext(),"Test Lon"+Config.getInstance().getCurrentLongitude(),Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onError(Throwable th) {
                   // Toast.makeText(getApplicationContext(),"Test Phone from pref"+phone,Toast.LENGTH_LONG).show();
                }
            });
        }


      //  intent.putExtra("latutide",location.getLatitude()+"");
      //  intent.putExtra("longitude",location.getLongitude()+"");
      //  sendBroadcast(intent);

    }



}