package ride.happyy.driver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import ride.happyy.driver.R;
import ride.happyy.driver.adapter.NotificationAddapter;
import ride.happyy.driver.model.MyNotification;

public class MyNotificationsActivity extends BaseAppCompatNoDrawerActivity {
    private Button invite_friend_btn;
    private TextView codeTv;
    private String code;
    private ListView notificationListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);

        getSupportActionBar().setTitle(R.string.label_notification);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        notificationListView = findViewById(R.id.notificationsListView);
        MyNotification myNotification1 = new MyNotification("Welcome to Happyy Drive ! Earn with Happyy Drive. It is the most powerfull ride sharing apps.");
        MyNotification myNotification2 = new MyNotification("Earn With Happyy Drive With the best commision in Bangladesh");

        MyNotification myNotification3 = new MyNotification("Earn Extra To Share The Apps.");
        MyNotification myNotification4 = new MyNotification("Have a nice day.");
        ArrayList<MyNotification> myNotificationArrayList = new ArrayList<>();
        myNotificationArrayList.add(myNotification1);
        myNotificationArrayList.add(myNotification2);
        myNotificationArrayList.add(myNotification3);
        myNotificationArrayList.add(myNotification4);

        NotificationAddapter notificationAddapter = new NotificationAddapter(this, R.layout.adapter_notification, myNotificationArrayList);
        notificationListView.setAdapter(notificationAddapter);



    }



}
