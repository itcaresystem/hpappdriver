package ride.happyy.driver.services;
import android.Manifest;
import android.app.*;
import android.content.*;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.*;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.concurrent.RunnableFuture;

import ride.happyy.driver.app.App;
import ride.happyy.driver.config.Config;
import ride.happyy.driver.model.CurentLocation;
import ride.happyy.driver.model.ServerResponse;
import ride.happyy.driver.net.NetworkCall;
import ride.happyy.driver.net.ResponseCallback;

public class BackgroundService extends Service implements LocationListener {

    private boolean isRunning;
    private Context context;
    private Thread backgroundThread;
    private String phone="";
    boolean isGPSEnable = false;
    boolean isNetworkEnable = false;
    double latitude, longitude;
    LocationManager locationManager;
    Location location;
    NetworkCall mNetworkCall;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        Context context = getApplicationContext();
        SharedPreferences prfs = context.getSharedPreferences("session", Context.MODE_PRIVATE);
        phone =prfs.getString("phone","");
        this.context = this;
        this.isRunning = false;
        this.backgroundThread = new Thread(myTask);
    }

    private Runnable myTask = new Runnable() {
        public void run() {
            // Do something here
            fn_getlocation();
            stopSelf();
        }
    };

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

    private void fn_update(Location location){
        mNetworkCall = new NetworkCall();
        Log.e("Test Phone from pref",phone+"lat"+String.valueOf(location.getLongitude()));
        Log.e("Test Phone from config", Config.getInstance().getPhone()+"lat"+String.valueOf(location.getLongitude()));

        // Toast.makeText(getApplicationContext(),"Test Phone from pref"+phone+"lat"+String.valueOf(location.getLongitude()),Toast.LENGTH_LONG).show();
        Config.getInstance().setCurrentLatitude(String.valueOf(location.getLongitude()));
        Config.getInstance().setCurrentLongitude(String.valueOf(location.getLongitude()));
        CurentLocation curentLocation=new CurentLocation(phone,String.valueOf(location.getLatitude()),String.valueOf(location.getLongitude()));

        if(App.isNetworkAvailable()){
            mNetworkCall.updateDriverCurrentLocation(curentLocation, new ResponseCallback<ServerResponse>() {
                @Override
                public void onSuccess(ServerResponse data) {
                    //  Toast.makeText(getApplicationContext(),"Test Lon"+Config.getInstance().getCurrentLongitude(),Toast.LENGTH_SHORT).show();
                    Log.e("Test Phone from pref","Curent Location Updated...");
                    Toast.makeText(getApplicationContext(),"updated success",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable th) {
                    Log.e("Test Phone from pref","Curent Location not Updated...");
                    Toast.makeText(getApplicationContext(),"updated Fail",Toast.LENGTH_SHORT).show();
                    // Toast.makeText(getApplicationContext(),"Test Phone from pref"+phone,Toast.LENGTH_LONG).show();
                }
            });
        }


        //  intent.putExtra("latutide",location.getLatitude()+"");
        //  intent.putExtra("longitude",location.getLongitude()+"");
        //  sendBroadcast(intent);

    }

    @Override
    public void onDestroy() {
        this.isRunning = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!this.isRunning) {
            this.isRunning = true;
            this.backgroundThread.start();
        }
        return START_STICKY;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
