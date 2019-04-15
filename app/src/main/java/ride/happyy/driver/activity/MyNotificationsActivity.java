package ride.happyy.driver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ride.happyy.driver.R;
import ride.happyy.driver.adapter.NotificationAddapter;
import ride.happyy.driver.app.App;
import ride.happyy.driver.config.Config;
import ride.happyy.driver.model.MyNotification;
import ride.happyy.driver.net.NetworkCall;
import ride.happyy.driver.net.ResponseCallback;

public class MyNotificationsActivity extends BaseAppCompatNoDrawerActivity {
    private Button invite_friend_btn;
    private TextView codeTv;
    private String code;
    private ListView notificationListView;
    private NetworkCall networkCall;
    private ArrayList<MyNotification> myNotifications = new ArrayList<>();
    //private ArrayList<MyNotification> myNotificationArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        networkCall = new NetworkCall();

        getSupportActionBar().setTitle(R.string.label_notification);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        notificationListView = findViewById(R.id.notificationsListView);


      //  getAllMessage();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAllMessage();
if(myNotifications==null)


        if(myNotifications.size()>0){
          //  Toast.makeText(getApplicationContext(),"before array adapter!",Toast.LENGTH_LONG).show();
            NotificationAddapter notificationAddapter = new NotificationAddapter(this, R.layout.adapter_notification, myNotifications);
            notificationListView.setAdapter(notificationAddapter);
          //  Toast.makeText(getApplicationContext(),"After arrayyadapter",Toast.LENGTH_LONG).show();
        }else {
            MyNotification myNotification1 = new MyNotification("Welcome to Happyy Drive ! Earn with Happyy Drive. It is the most powerfull and very easy to use ride sharing apps.");
            MyNotification myNotification2 = new MyNotification("Earn With Happyy Drive With the best commision in Bangladesh");
            MyNotification myNotification3 = new MyNotification("Earn Extra To Share The Apps.");
            MyNotification myNotification4 = new MyNotification("Have a nice day.");
            myNotifications = new ArrayList<>();
            myNotifications.add(myNotification1);
            myNotifications.add(myNotification2);
            myNotifications.add(myNotification3);
            myNotifications.add(myNotification4);

           // Toast.makeText(getApplicationContext(),"before  arrayyadapter 1",Toast.LENGTH_LONG).show();
            NotificationAddapter notificationAddapter = new NotificationAddapter(this, R.layout.adapter_notification, myNotifications);
            notificationListView.setAdapter(notificationAddapter);
          //  Toast.makeText(getApplicationContext(),"After arrayyadapter 1",Toast.LENGTH_LONG).show();
        }
    }

    public void getAllMessage(){
        if(networkCall==null){
            networkCall = new NetworkCall();

        }

        if(App.isNetworkAvailable()){
            networkCall.getMyAllMessage(Config.getInstance().getPhone(), new ResponseCallback<ArrayList<MyNotification>>() {
                @Override
                public void onSuccess(ArrayList<MyNotification> data) {
                    myNotifications =data;
                  //  Toast.makeText(getApplicationContext(),"After get data success!!!",Toast.LENGTH_LONG).show();

                }

                @Override
                public void onError(Throwable th) {
                   // Toast.makeText(getApplicationContext(),th.getMessage(),Toast.LENGTH_LONG).show();

                }
            });
        }

    }
}
