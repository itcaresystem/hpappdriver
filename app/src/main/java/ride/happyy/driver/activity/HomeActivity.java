package ride.happyy.driver.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.nex3z.notificationbadge.NotificationBadge;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.driver.R;
import ride.happyy.driver.adapter.HomePagerAdapter;
import ride.happyy.driver.app.App;
import ride.happyy.driver.config.Config;
import ride.happyy.driver.dialogs.PopupMessage;
import ride.happyy.driver.fragments.AccountsFragment;
import ride.happyy.driver.fragments.EarningsFragment;
import ride.happyy.driver.fragments.HomeFragment;
import ride.happyy.driver.fragments.LeaderBordFragmentNew;
import ride.happyy.driver.fragments.RatingsFragment;
import ride.happyy.driver.listeners.BasicListener;
import ride.happyy.driver.model.BasicBean;
import ride.happyy.driver.net.DataManager;
import ride.happyy.driver.net.WSAsyncTasks.FCMRegistrationTask;
import ride.happyy.driver.util.AppConstants;
import ride.happyy.driver.util.FileOp;
import ride.happyy.driver.widgets.CustomTextView;

import static android.provider.Settings.Secure.LOCATION_MODE_HIGH_ACCURACY;

public class HomeActivity extends BaseAppCompatActivity implements HomeFragment.HomeFragmentListener,
        EarningsFragment.EarningsFragmentListener, RatingsFragment.RatingsFragmentListener,
        AccountsFragment.AccountsFragmentListener, LeaderBordFragmentNew.LeaderBordNewFragmentListener {

    private FileOp fop = new FileOp(this);

    private static final String TAG = "HomeA";

    private static final int FRAGMENT_HOME = 0;
    private static final int FRAGMENT_EARNING = 1;
    private static final int FRAGMENT_RATING = 2;
    private static final int FRAGMENT_ACCOUNTS = 3;
    private ViewPager pager;
    private TabLayout tabLayout;
    private LayoutInflater inflater;
    private HomePagerAdapter adapterPager;
    private MenuItem menuOnlineSwitchItem;
    private SwitchCompat switchOnline,mySwitchOnlineNew;
    private CustomTextView customTab;
    private String requestID;
    private TextView onlineOffLine_status;
    private ImageButton notificatinImageButton;
    private NotificationBadge mNotificationBadge;
    private int countNotification =0;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (!checkForReadWritePermissions()) {
            getReadWritePermissions();
        }

      //  getSupportActionBar().setHomeButtonEnabled(true);
        // getSupportActionBar().setTitle("HAPPYY DRIVE");
      //  getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
     //   Configuration config = getResources().getConfiguration();

       // if(config.screenWidthDp <= 380) {
        //    getSupportActionBar().setCustomView(R.layout.layout_actionbar_title_extra_w);
          //  getSupportActionBar().setCustomView(R.layout.layout_actionbar_title);

      //  }else {
        ///    getSupportActionBar().setCustomView(R.layout.layout_actionbar_title);

       // }
      //  toolbar.setVisibility(View.GONE);

        getSupportActionBar().hide();
        mySwitchOnlineNew = (SwitchCompat) findViewById(R.id.settings_online_offline_switchHome);

        mySwitchOnlineNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);

                setDriverTitle();
                if (App.isNetworkAvailable()) {
                    performDriverStatusChange();
                } else {
                    switchOnline.setChecked(!switchOnline.isChecked());
                    Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                            .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                }

            }
        });


        initViews();
        initFCM();

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
       // getSupportActionBar().setHomeButtonEnabled(true);
       // getSupportActionBar().setTitle(R.string.label_offline);
        //getSupportActionBar().setDisplayShowTitleEnabled(true);

       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);


      // getSupportActionBar().setIcon(R.drawable.driverappbarfinal);
        // init notification view
       notificatinImageButton = findViewById(R.id.driverNotificationImageBtn);
        mNotificationBadge      = findViewById(R.id.notificationBadge);
        countNotification = 4;
       mNotificationBadge.setNumber(countNotification);
       // checkHighAcouracy();

    }

   public void onClickNotification(View view){
       view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
      // mNotificationBadge.setBackgroundColor(Color.TRANSPARENT);
      // Toast.makeText(getApplicationContext(),"befor notification !!",Toast.LENGTH_LONG).show();
       mNotificationBadge.setNumber(1);
      // notificatinImageButton.setVisibility(View.GONE);
     // Toast.makeText(this,"Notification",Toast.LENGTH_SHORT).show();
       Intent mIntent = new Intent(this,MyNotificationsActivity.class);
       startActivity(mIntent);
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (switchOnline != null) {
            ((HomeFragment) adapterPager.getItem(0)).setDriverStatus(switchOnline.isChecked());

            if (Config.getInstance().isOnline()) {
                switchOnline.setChecked(true);
            } else {
                switchOnline.setChecked(false);
            }
        }

        if (mySwitchOnlineNew != null) {
            ((HomeFragment) adapterPager.getItem(0)).setDriverStatus(mySwitchOnlineNew.isChecked());

            if (Config.getInstance().isOnline()) {
                mySwitchOnlineNew.setChecked(true);
            } else {
                mySwitchOnlineNew.setChecked(false);
            }
        }
        onRefresh();

        checkHighAcouracy();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
/*        menuProgress = menu.findItem(R.id.action_progress);
        // Extract the action-view from the menu item
        ProgressBar progressActionBar = (ProgressBar) MenuItemCompat.getActionView(menuProgress);
        progressActionBar.setIndeterminate(true);*/
        menuOnlineSwitchItem = menu.findItem(R.id.action_online_offline_switch);
//        MenuItemCompat.setActionView(menuOnlineSwitchItem, R.layout.custom_action_online);
        switchOnline = (SwitchCompat) MenuItemCompat.getActionView(menuOnlineSwitchItem);
        switchOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);

                setDriverTitle();
                if (App.isNetworkAvailable()) {
                    performDriverStatusChange();
                } else {
                    switchOnline.setChecked(!switchOnline.isChecked());
                    Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                            .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                }
            }
        });
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                mVibrator.vibrate(25);
                if (drawerLayout.isDrawerOpen(leftDrawer))
                    drawerLayout.closeDrawer(leftDrawer);
            /*			else if(drawerLayout.isDrawerOpen(rightDrawer))
                drawerLayout.closeDrawer(rightDrawer);*/
                else if (!drawerLayout.isDrawerOpen(leftDrawer))
                    drawerLayout.openDrawer(leftDrawer);
                return true;
           /* case R.id.action_search:
                drawerLayout.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                mVibrator.vibrate(25);

                startActivity(new Intent(this, SearchActivity.class));
//                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                return true;*/

        }
        return super.onOptionsItemSelected(item);
    }

    private void onRefresh() {
        if (App.isNetworkAvailable()) {
            fetchDriverStatus();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }
    }

    public void checkHighAcouracy(){
        int locationMode = 0;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                locationMode = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        if(locationMode == LOCATION_MODE_HIGH_ACCURACY) {
            //request location updates
            // initViews();
        } else { //redirect user to settings page
            PopupMessage popupMessage = new PopupMessage(this);
            popupMessage.show(getString(R.string.message_please_enable_location_service_from_the_settings),
                    0, getString(R.string.btn_open_settings)," ");
            popupMessage.setPopupActionListener(new PopupMessage.PopupActionListener() {
                @Override
                public void actionCompletedSuccessfully(boolean result) {
                    Log.d(TAG, "actionCompletedSuccessfully: Settings Button clicked : ");
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                    // isLocationServiceEnableRequestShown = false;
                }

                @Override
                public void actionFailed() {
                    // isLocationServiceEnableRequestShown = false;
                }
            });
            //startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }

    private void initViews() {
        onlineOffLine_status = findViewById(R.id.status_txv);
        pager = (ViewPager) findViewById(R.id.pager_home);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout_home);
        if (inflater == null)
            inflater = getLayoutInflater();
        customTab = (CustomTextView) inflater.inflate(R.layout.custom_tab, null);
        customTab.setText(R.string.btn_home);
        customTab.setBackgroundResource(R.drawable.btn_click_app_rectangle_with_semicircle_edge);
        tabLayout.addTab(tabLayout.newTab()
                .setText(R.string.btn_home)
               .setIcon(R.drawable.ic_home_white_24dp)
               .setCustomView(customTab));

        customTab = (CustomTextView) inflater.inflate(R.layout.custom_tab, null);
        customTab.setText(R.string.btn_earnings);
        tabLayout.addTab(tabLayout.newTab()
                .setText(R.string.btn_earnings)
                //                .setIcon(R.drawable.ic_action_popular)
                .setCustomView(customTab));
        customTab = (CustomTextView) inflater.inflate(R.layout.custom_tab, null);
        customTab.setText(R.string.btn_leaderbord);
        tabLayout.addTab(tabLayout.newTab()
                .setText(R.string.btn_leaderbord)
                //                .setIcon(R.drawable.ic_action_popular)
                .setCustomView(customTab));

        customTab = (CustomTextView) inflater.inflate(R.layout.custom_tab, null);
        customTab.setText(R.string.btn_ratings);
        tabLayout.addTab(tabLayout.newTab()
                .setText(R.string.btn_ratings)
                //                .setIcon(R.drawable.ic_action_popular)
                .setCustomView(customTab));

        customTab = (CustomTextView) inflater.inflate(R.layout.custom_tab, null);
        customTab.setText(R.string.btn_accounts);
        tabLayout.addTab(tabLayout.newTab()
                .setText(R.string.btn_accounts)
                //                .setIcon(R.drawable.ic_action_popular)
                .setCustomView(customTab));
//        tab_layout.addTab(tab_layout.newTab()/*.setText("Stream")*/.setIcon(R.drawable.ic_action_stream));


      //  tabLayout.setTabGravity(Gravity.BOTTOM);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
                try {
                    tab.getCustomView().setBackgroundResource(R.drawable.btn_click_app_rectangle_with_semicircle_edge);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /*try {
                    ((ActivityBase) getActivity()).getSupportActionBar().setTitle(getTabTitle(tab.getPosition()));
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                try {
                    tab.getCustomView().setBackgroundResource(selectableItemBackground);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
               /* try {
                    tab.getCustomView().setBackgroundResource(R.drawable.btn_click_shadow_white_with_semicircle_edge);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/

            }

        });
        adapterPager = new HomePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapterPager);

        pager.setCurrentItem(0);
        tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getApplicationContext(), R.color.transparent));
//        tabLayout.setSelectedTabIndicatorHeight((int) mActionBarHeight);
        pager.setOffscreenPageLimit(5);

        swipeView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                int pos = pager.getCurrentItem();
                switch (pos) {
                    case 0:
                        ((HomeFragment) adapterPager.getItem(0)).onRefresh();
                        break;
                    case 1:
                        ((EarningsFragment) adapterPager.getItem(1)).onRefresh();
                        break;
                    case 2:
                        ((RatingsFragment) adapterPager.getItem(2)).onRefresh();
                        break;

                }

            }
        });
    }


    private void initFCM() {

        FCMRegistrationTask fcmRegistrationTask = new FCMRegistrationTask();
        fcmRegistrationTask.setFCMRegistrationTaskListener(new FCMRegistrationTask.FCMRegistrationTaskListener() {
            @Override
            public void dataDownloadedSuccessfully(String fcmToken) {

                Log.i(TAG, "dataDownloadedSuccessfully: FCM TOKEN : " + fcmToken);

                JSONObject postData = getUpdateFCMTokenJSObj(fcmToken);

                DataManager.performUpdateFCMToken(postData, new BasicListener() {
                    @Override
                    public void onLoadCompleted(BasicBean basicBean) {

                    }

                    @Override
                    public void onLoadFailed(String error) {

                    }
                });


            }

            @Override
            public void dataDownloadFailed() {

            }
        });
        fcmRegistrationTask.execute();

    }

    private JSONObject getUpdateFCMTokenJSObj(String fcmToken) {
        JSONObject postData = new JSONObject();

        try {
            postData.put("fcm_token", fcmToken);
            postData.put("phone", Config.getInstance().getPhone());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    private void fetchDriverStatus() {

        HashMap<String, String> urlParams = new HashMap<>();
        JSONObject postData = new JSONObject();
        try {
            postData.put("phone",Config.getInstance().getPhone());
            postData.put("driver_id",Config.getInstance().getUserID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DataManager.fetchDriverStatus(postData, new BasicListener() {
            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                setDriverStatus(basicBean.isDriverOnline());
            }

            @Override
            public void onLoadFailed(String error) {

            }
        });

    }

    public void setDriverStatus(boolean isOnline) {
        Config.getInstance().setOnline(isOnline);
        if (switchOnline!=null) {
            switchOnline.setChecked(isOnline);
        }
        if (mySwitchOnlineNew!=null) {
            mySwitchOnlineNew.setChecked(isOnline);
        }

        setDriverTitle();
    }

    private void performDriverStatusChange() {

        swipeView.setRefreshing(true);
        JSONObject postData = getDriverStatusChangeJSObj();

        DataManager.performDriverStatusChange(postData, new BasicListener() {
            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                swipeView.setRefreshing(false);
                setDriverTitle();
                if (mySwitchOnlineNew.isChecked()) {

                  //  Snackbar.make(coordinatorLayout, R.string.message_you_are_online, Snackbar.LENGTH_LONG)
                         //   .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                    Toast.makeText(HomeActivity.this,R.string.message_you_are_online,Toast.LENGTH_LONG).show();
                } else {
                  //  Snackbar.make(coordinatorLayout, R.string.message_you_are_offline_now, Snackbar.LENGTH_LONG)
                          //  .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                    Toast.makeText(HomeActivity.this,R.string.message_you_are_offline_now,Toast.LENGTH_LONG).show();
                }
                ((HomeFragment) adapterPager.getItem(0)).setDriverStatus(mySwitchOnlineNew.isChecked());
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                switchOnline.setChecked(!switchOnline.isChecked());
                setDriverTitle();
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                if (App.getInstance().isDemo()) {
                    switchOnline.setChecked(!switchOnline.isChecked());
                    setDriverTitle();
                    ((HomeFragment) adapterPager.getItem(0)).setDriverStatus(switchOnline.isChecked());
                }
            }
        });

    }

    private JSONObject getDriverStatusChangeJSObj() {
        JSONObject postData = new JSONObject();
        try {
           // postData.put("driver_status", switchOnline.isChecked());
            postData.put("phone",Config.getInstance().getPhone());
            postData.put("is_online",mySwitchOnlineNew.isChecked());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    private void setDriverTitle() {
        /*
        if (switchOnline != null) {
            if (switchOnline.isChecked()) {
                Config.getInstance().setOnline(true);
               // getSupportActionBar().setTitle(R.string.label_online);
                onlineOffLine_status.setText(R.string.label_online);
                onlineOffLine_status.setTextColor(Color.GREEN);

            } else {
                Config.getInstance().setOnline(false);
               // getSupportActionBar().setTitle(R.string.label_offline);
                onlineOffLine_status.setText(R.string.label_offline);
                onlineOffLine_status.setTextColor(Color.RED);
            }
        }
        */

        if (mySwitchOnlineNew != null) {
            if (mySwitchOnlineNew.isChecked()) {
                Config.getInstance().setOnline(true);
                // getSupportActionBar().setTitle(R.string.label_online);
                onlineOffLine_status.setText(R.string.label_online);
                onlineOffLine_status.setTextColor(Color.GREEN);

            } else {
                Config.getInstance().setOnline(false);
                // getSupportActionBar().setTitle(R.string.label_offline);
                onlineOffLine_status.setText(R.string.label_offline);
                onlineOffLine_status.setTextColor(Color.RED);
            }
        }
    }


    @Override
    public void onSwipeRefreshChange(boolean isRefreshing) {
        swipeView.setRefreshing(isRefreshing);
    }

    @Override
    public void onSwipeEnabled(boolean isEnabled) {
        swipeView.setEnabled(isEnabled);

    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
