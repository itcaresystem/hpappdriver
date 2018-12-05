package ride.happyy.driver.services;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class Timerservice  extends BroadcastReceiver {
    public static final int REQUEST_CODE = 12345;
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
       // Intent background = new Intent(context, BackgroundService.class);
       // context.startService(background);
       // System.out.println("Thi is 40 second Services...........................iio!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

      //  Toast.makeText(context,"Alarm service 40Second",Toast.LENGTH_SHORT).show();
    }

}
