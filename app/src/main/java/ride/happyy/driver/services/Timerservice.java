package ride.happyy.driver.services;

import android.Manifest;
import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Timer;

import ride.happyy.driver.app.App;
import ride.happyy.driver.config.Config;
import ride.happyy.driver.model.CurentLocation;
import ride.happyy.driver.model.ServerResponse;
import ride.happyy.driver.net.NetworkCall;
import ride.happyy.driver.net.ResponseCallback;

import static android.content.Context.LOCATION_SERVICE;

public class Timerservice  extends WakefulBroadcastReceiver implements LocationListener {
    public static final int REQUEST_CODE = 12345;
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
    PowerManager.WakeLock screenWakeLock;
    //Context mContext;
    /*
    public static final int REQUEST_CODE = 12345;
    Context mContext;
  //  public static final String ACTION = "com.codepath.example.servicesdemo.alarm";

    // Triggered by the Alarm periodically (starts the service to run task)
    @Override
    public void onReceive(Context context, Intent intent) {


        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if ( !manager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {
           // Intent i1 = new Intent(context, AlertDialogActivity.class);
          //  i1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
           // context.startActivity(i1);
            //   buildAlertMessageNoGps();
        }else{
            Intent i = new Intent(context, GoogleService.class);
            context.startService(i);
        }



    }
    */
    /*

    // Restart service every 30 seconds
    private static final long REPEAT_TIME = 1000 * 60 * 5;
    private static final long REPEAT_DAILY = 1000 * 60 * 60 * 24;
    @Override
    public void onReceive(Context context, Intent intent)
    {

        Log.v("TEST", "Service loaded at start");
        AlarmManager service = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 0);
        Intent i = new Intent(context.getApplicationContext(), GoogleService.class);
       // GoogleService googleService = new GoogleService();
       // googleService.fn_getlocation();
       // context.startService();
       // context.startService(i);
        Toast.makeText(context,"Alarm service 30Second",Toast.LENGTH_SHORT).show();
        PendingIntent pending = PendingIntent.getBroadcast(context, 0, i,PendingIntent.FLAG_CANCEL_CURRENT);
        service.setInexactRepeating(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(), REPEAT_TIME, pending);

    }
    */
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences prfs = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        phone =prfs.getString("phone","");
        // Intent background = new Intent(context, BackgroundService.class);
        // context.startService(background);
        // System.out.println("Thi is 40 second Services...........................iio!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        // Toast.makeText(context,"Alarm service 40Second",Toast.LENGTH_SHORT).show();
        // fn_getlocation(context);

        if (screenWakeLock == null)
        {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            screenWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK | PowerManager.ACQUIRE_CAUSES_WAKEUP,
                    "ScreenLock tag from AlarmListener");
            screenWakeLock.acquire();
        }
       // Intent service = new Intent(context, BackgroundService.class);
       // startWakefulService(context, service);
        fn_getlocation(context);
        if (screenWakeLock != null)
            screenWakeLock.release();
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

    void fn_getlocation(Context context) {
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        isGPSEnable = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetworkEnable = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!isGPSEnable && !isNetworkEnable) {

        } else {

            if (isNetworkEnable) {
                location = null;
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
                        fn_update(location,context);
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
                        fn_update(location,context);
                    }
                }
            }


        }

    }

    private void fn_update(Location location , final Context context){
        mNetworkCall = new NetworkCall();
        Log.e("Test Phone from pref",phone+"lat"+String.valueOf(location.getLongitude()));
        Log.e("Test Phone from config", Config.getInstance().getPhone()+"lat"+String.valueOf(location.getLongitude()));

        // Toast.makeText(getApplicationContext(),"Test Phone from pref"+phone+"lat"+String.valueOf(location.getLongitude()),Toast.LENGTH_LONG).show();
        Config.getInstance().setCurrentLatitude(String.valueOf(location.getLongitude()));
        Config.getInstance().setCurrentLongitude(String.valueOf(location.getLongitude()));
        CurentLocation curentLocation=new CurentLocation(phone,String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));


            mNetworkCall.updateDriverCurrentLocation(curentLocation, new ResponseCallback<ServerResponse>() {
                @Override
                public void onSuccess(ServerResponse data) {
                  //  Toast.makeText(context,"Test Lon"+Config.getInstance().getCurrentLongitude(),Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onError(Throwable th) {
                   // Toast.makeText(context,"Test Phone from pref"+phone,Toast.LENGTH_LONG).show();
                }
            });





        //  intent.putExtra("latutide",location.getLatitude()+"");
        //  intent.putExtra("longitude",location.getLongitude()+"");
        //  sendBroadcast(intent);

    }
}
