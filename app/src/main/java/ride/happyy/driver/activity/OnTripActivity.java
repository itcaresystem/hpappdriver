package ride.happyy.driver.activity;

import android.Manifest;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import ride.happyy.driver.R;
import ride.happyy.driver.app.App;
import ride.happyy.driver.config.Config;
import ride.happyy.driver.listeners.AppStatusListener;
import ride.happyy.driver.listeners.BasicListener;
import ride.happyy.driver.listeners.PolyPointListener;
import ride.happyy.driver.listeners.TripDetailsListener;
import ride.happyy.driver.model.AppStatusBean;
import ride.happyy.driver.model.BasicBean;
import ride.happyy.driver.model.PathBean;
import ride.happyy.driver.model.PolyPointBean;
import ride.happyy.driver.model.TripBean;
import ride.happyy.driver.net.DataManager;
import ride.happyy.driver.util.AppConstants;

public class OnTripActivity extends BaseAppCompatNoDrawerActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMyLocationButtonClickListener, com.google.android.gms.location.LocationListener,
        android.location.LocationListener {


    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 100;
    private static final String TAG = "OnTripA";
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    private GoogleApiClient mGoogleApiClient;
    private static final LocationRequest mLocationRequest = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);


    private static GoogleMapOptions options = new GoogleMapOptions()
            .mapType(GoogleMap.MAP_TYPE_NORMAL)
            .compassEnabled(true)
            .rotateGesturesEnabled(true)
            .tiltGesturesEnabled(true)
            .zoomControlsEnabled(true)
            .scrollGesturesEnabled(true)
            .mapToolbarEnabled(true);
    private Bitmap mapPin;
    private ArrayList<Object> plotList;
    private HashMap markerMap;
    private FragmentManager myFragmentManager;
    private static SupportMapFragment myMapFragment = SupportMapFragment.newInstance(options);
    private ViewGroup.LayoutParams param;
    private GoogleMap mMap;
    private LatLng current;
    private double dLatitude;
    private double dLongitude;
    private LatLng center;
    private boolean isInit;
    private boolean isArrived = false;
    private boolean isTripStarted = false;
    private PolyPointBean polyPointBean;
    private Polyline polyLine;


    private LinearLayout lytBottomSheet;
    private View lytBeforeTrip;
    private ImageView ivBottomSheetProfilePhoto;
    private ImageView ivBeforeTripProfilePhoto;
    private TextView txtBottomSheetCustomerName;
    private TextView txtBottomSheetFare;
    private TextView txtDestination;
    private TextView txtBeforeTripCustomerName;
    private BottomSheetBehavior<LinearLayout> bottomSheetBehavior;
    private LinearLayout llCompleteTrip;
    private LinearLayout llConfirmArrival,llContactPhone;
    private LinearLayout llStartTrip;
    private TripBean tripBean;
    private Calendar calStart;
    private Calendar calEnd;
    private ArrayList<PathBean> pathList;
    private Handler mHandler = new Handler();
    private String customer_confirmaion_code ="";
    private ProgressBar simpleProgressBarTripEnd;
    private String etaTimeLeft="";
    private String etaDistance="";
    private Button tripCompleteBtn;
    private Bitmap smallMarker;
   private Bitmap smallMarker_pick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_trip);
           BitmapDrawable bitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_destination_new);
           BitmapDrawable bitmapDrawable_pick = (BitmapDrawable) getResources().getDrawable(R.drawable.pickupnewicone);
        Bitmap b = bitmapDrawable.getBitmap();
        smallMarker = Bitmap.createScaledBitmap(b, 185, 130, false);
        Bitmap b_pick = bitmapDrawable_pick.getBitmap();
        smallMarker_pick = Bitmap.createScaledBitmap(b_pick, 110, 110, false);

        if (getIntent().hasExtra("bean"))
            tripBean = (TripBean) getIntent().getSerializableExtra("bean");
        initMap();
        getCurrentLocation();
        initViews();


        populateTrip();
        if(App.isNetworkAvailable()) {
            fetchAppStatus();
        }
        getSupportActionBar().setTitle(R.string.title_on_trip);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                toolbar.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //                mVibrator.vibrate(25);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mHandler.postDelayed(checkAppStatusTask, 6500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(checkAppStatusTask);
    }

    private void initViews() {
        simpleProgressBarTripEnd=findViewById(R.id.simpleProgressBarTripEnd);

        lytBottomSheet = (LinearLayout) findViewById(R.id.ll_bottom_sheet_on_trip);
        lytBeforeTrip = findViewById(R.id.lyt_on_trip_before_trip);

        ivBottomSheetProfilePhoto = (ImageView) findViewById(R.id.iv_on_trip_profile_photo);
        ivBeforeTripProfilePhoto = (ImageView) findViewById(R.id.iv_on_trip_before_trip_profile_photo);

        txtBottomSheetCustomerName = (TextView) lytBottomSheet.findViewById(R.id.txt_on_trip_customer_name);
        txtBottomSheetFare = (TextView) lytBottomSheet.findViewById(R.id.txt_on_trip_total_fare);

        txtDestination = (TextView) findViewById(R.id.txt_on_trip_destination);
        txtBeforeTripCustomerName = (TextView) findViewById(R.id.txt_on_trip_before_trip_customer_name);

        llCompleteTrip = (LinearLayout) lytBottomSheet.findViewById(R.id.ll_on_trip_complete_trip);
        llConfirmArrival = (LinearLayout) findViewById(R.id.ll_on_trip_before_trip_confirm_arrival);
        llContactPhone = (LinearLayout) findViewById(R.id.ll_on_trip_before_trip_contact);
        llStartTrip = (LinearLayout) findViewById(R.id.ll_on_trip_before_trip_start);
        tripCompleteBtn =findViewById(R.id.tripCompleteBtn);


        lytBottomSheet.setVisibility(View.GONE);
        lytBeforeTrip.setVisibility(View.VISIBLE);

        llConfirmArrival.setVisibility(View.VISIBLE);
        llContactPhone.setVisibility(View.VISIBLE);
        llStartTrip.setVisibility(View.GONE);
        ivBottomSheetProfilePhoto.setVisibility(View.GONE);


        bottomSheetBehavior = BottomSheetBehavior.from(lytBottomSheet);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                /*if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_DRAGGING
                        || bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_SETTLING) {
                    param = myMapFragment.getView().getLayoutParams();
                    param.height = (int) (height - getStatusBarHeight() - mActionBarHeight - bottomSheet.getHeight());
                    Log.i(TAG, "onSlide: PAram Height : " + param.height);
                    myMapFragment.getView().setLayoutParams(param);
                } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    param = myMapFragment.getView().getLayoutParams();
                    param.height = (int) (height - getStatusBarHeight() - mActionBarHeight - bottomSheet.getHeight());
                    Log.i(TAG, "onSlide: PAram Height : " + param.height);
                    myMapFragment.getView().setLayoutParams(param);
                } else if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    param = myMapFragment.getView().getLayoutParams();
                    param.height = (int) (height - getStatusBarHeight() - mActionBarHeight - bottomSheet.getHeight());
                    Log.i(TAG, "onSlide: PAram Height : " + param.height);
                    myMapFragment.getView().setLayoutParams(param);
                }*/
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                Log.i(TAG, "onSlide: offset : " + slideOffset);
//                mapFragmentView.animate().scaleX(1 - slideOffset).scaleY(1 - slideOffset).setDuration(0).start();
            }
        });


    }

    private void initMap() {

        plotList = new ArrayList<>();
        markerMap = new HashMap();
/*
        frame = BitmapFactory.decodeResource(getResources(), R.drawable.bg_map_pin);
        mapPin = BitmapFactory.decodeResource(getResources(), R.drawable.ic_hunt_green);
        mapPinRed = BitmapFactory.decodeResource(getResources(), R.drawable.ic_hunt_red);
*/

        mapPin = BitmapFactory.decodeResource(getResources(), R.drawable.circle_app);

        myFragmentManager = getSupportFragmentManager();
        myMapFragment = (SupportMapFragment) myFragmentManager.findFragmentById(R.id.fragment_map_ontrip);
        //	mMap = myMapFragment.getMap();

        /*param = myMapFragment.getView().getLayoutParams();
        param.height = (int) (height - getStatusBarHeight() - mActionBarHeight - (80 * px));
        Log.i(TAG, "onSlide: Param Height : " + param.height);
        myMapFragment.getView().setLayoutParams(param);
*/
        myMapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap mapTemp) {

                Log.i(TAG, "onMapReady: MAP IS LOADED");

                mMap = mapTemp;
                mMap.setPadding(0, 0/*(int) ((100 * px) + mActionBarHeight + getStatusBarHeight())*/, 0, (int) (60 * px));
                initMapOnLoad();
                if (Config.getInstance().getCurrentLatitude() != null
                        && !Config.getInstance().getCurrentLatitude().equals("")
                        && Config.getInstance().getCurrentLongitude() != null
                        && !Config.getInstance().getCurrentLongitude().equals("")) {

                    populateMap(Double.parseDouble(Config.getInstance().getCurrentLatitude()),Double.parseDouble(Config.getInstance().getCurrentLongitude()));
                  //  mapAutoZoom(Double.parseDouble(Config.getInstance().getCurrentLatitude()),Double.parseDouble(Config.getInstance().getCurrentLongitude()));
                }

            }
        });

    }

    private void initMapOnLoad() {

        current = new LatLng(0.0, 0.0);

        if (Config.getInstance().getCurrentLatitude() != null && !Config.getInstance().getCurrentLatitude().equals("")
                && Config.getInstance().getCurrentLongitude() != null && !Config.getInstance().getCurrentLongitude().equals("")) {
            dLatitude = Double.parseDouble(Config.getInstance().getCurrentLatitude());
            dLongitude = Double.parseDouble(Config.getInstance().getCurrentLongitude());
            current = new LatLng(dLatitude, dLongitude);
        }

        //	mMap=mapView.getMap();
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (!checkForLocationPermissions()) {
                getLocationPermissions();
            }
            checkLocationSettingsStatus();
        }
        mMap.setMyLocationEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setMapToolbarEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        //myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //myMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);


/*        if (placesBean != null) {
            dLatitude = Double.parseDouble(placesBean.getLatitude());
            dLongitude = Double.parseDouble(placesBean.getLongitude());

            newLatLng = new LatLng(dLatitude, dLongitude);

            mMap.addMarker(new MarkerOptions()
                    .position(newLatLng)
                    .title(placesBean.getName())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 12));
        }*/

        center = mMap.getCameraPosition().target;

       /* mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng arg0) {
                if (llMapDetails.isShown()) {
                    llMapDetails.setVisibility(View.GONE);
                }

            }
        });*/
/*
        try {
            populatePlotList();
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }

    private void populateTrip() {
        txtDestination.setText(tripBean.getDestinationLocation());
        txtBeforeTripCustomerName.setText(tripBean.getCustomerName());
        txtBottomSheetCustomerName.setText(tripBean.getCustomerName());

        Glide.with(getApplicationContext())
                .load(tripBean.getCustomerPhoto())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_profile_photo_default)
                        .fallback(R.drawable.ic_profile_photo_default)
                        .centerCrop()
                        .circleCrop())
                .into(ivBeforeTripProfilePhoto);

        Glide.with(getApplicationContext())
                .load(tripBean.getCustomerPhoto())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_profile_photo_default)
                        .fallback(R.drawable.ic_profile_photo_default)
                        .centerCrop()
                        .circleCrop())
                .into(ivBottomSheetProfilePhoto);

        setCurrentDriverStatus(tripBean.getDriverStatus());

    }
    private void populateTripOntheret() {
        txtDestination.setText(tripBean.getDestinationLocation());
        txtBeforeTripCustomerName.setText(tripBean.getCustomerName());
        txtBottomSheetCustomerName.setText(tripBean.getCustomerName());
        setCurrentDriverStatus(tripBean.getDriverStatus());

    }
    private void setCurrentDriverStatus(int driverStatus) {

        switch (driverStatus) {
            case AppConstants.DRIVER_STATUS_ACCEPTED:
                llConfirmArrival.setVisibility(View.VISIBLE);
                llContactPhone.setVisibility(View.VISIBLE);
                llStartTrip.setVisibility(View.GONE);
                lytBeforeTrip.setVisibility(View.VISIBLE);
                lytBottomSheet.setVisibility(View.GONE);
                ivBottomSheetProfilePhoto.setVisibility(View.GONE);
                break;
            case AppConstants.DRIVER_STATUS_ARRIVED:
                llConfirmArrival.setVisibility(View.GONE);
                llContactPhone.setVisibility(View.GONE);
                llStartTrip.setVisibility(View.VISIBLE);
                lytBeforeTrip.setVisibility(View.VISIBLE);
                lytBottomSheet.setVisibility(View.GONE);
                ivBottomSheetProfilePhoto.setVisibility(View.GONE);
                isArrived=true;
                break;
            case AppConstants.DRIVER_STATUS_STARTED:
                llConfirmArrival.setVisibility(View.GONE);
                llContactPhone.setVisibility(View.GONE);
                llStartTrip.setVisibility(View.VISIBLE);
                lytBeforeTrip.setVisibility(View.GONE);
                lytBottomSheet.setVisibility(View.VISIBLE);
                ivBottomSheetProfilePhoto.setVisibility(View.VISIBLE);
                isArrived=true;
                break;

            case AppConstants.DRIVER_STATUS_ENDED:
                mHandler.removeCallbacks(checkAppStatusTask);
                startActivity(new Intent(OnTripActivity.this, TripSummaryActivity.class)
                        .putExtra("trip_id", tripBean.getId()));
                finish();
                break;
                /*

            case AppConstants.DRIVER_STATUS_CASH_ACCEPTED:
                startActivity(new Intent(OnTripActivity.this, TripSummaryActivity.class)
                        .putExtra("trip_id", tripBean.getId()));
                finish();
                break;
                */
            default:
                break;


        }

    }

    public void onOnTripBottomSheetClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    public void onOnTripCompleteTripClick(View view) {

        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);
        simpleProgressBarTripEnd.setVisibility(View.VISIBLE);

        if (App.isNetworkAvailable()) {
            tripCompleteBtn.setEnabled(false);
            performTripCompletion();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }

    }

    public void onContactClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        if (checkForCallPermissions()) {
            performCall(tripBean.getCustomerPhone());
        } else {
            getCallPermissions();
        }

    }

    public void performCall(String phone) {
        String url = "tel:" + phone;
        Log.i(TAG, "performCall:  PHONE : " + phone);
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse(url));
        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(intent);
        } catch (Exception ignored) {
        }
    }


    public void onOnTripConfirmArrivalClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        if (App.isNetworkAvailable()) {
            performArrivalConfirmation();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }

    }

    public void onOnTripStartTripClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);
        verifyCustomerCode();
        /*
        if(!customer_confirmaion_code.equals("")) {

            if (App.isNetworkAvailable()) {

                performTripStart();
            } else {
                Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }
        }else{
            Snackbar.make(coordinatorLayout, "Customer code invalid or null!!", Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }
        */

    }

    public boolean verifyCustomerCode(){
         final Dialog dialog;
         final EditText customer_given_code;
         Button registration;

            dialog = new Dialog(OnTripActivity.this);
            dialog.setTitle("Customer Confirmation Code");
          //  dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.create_account_layout);
            customer_given_code = dialog.findViewById(R.id.customer_code_ET);
            registration = dialog.findViewById(R.id.registration);
            registration.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(customer_given_code.getText().toString().length()==4) {
                        customer_confirmaion_code = customer_given_code.getText().toString();
                        if (App.isNetworkAvailable() && !customer_given_code.equals("")) {

                            performTripStart();
                        } else {
                            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                        }
                        dialog.dismiss();

                    }else{
                        Toast.makeText(getBaseContext(),"Please give 4 digit code from customer",Toast.LENGTH_LONG).show();
                    }


                }
            });

            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        return true;

    }

    public void onOnTripNavigateClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        Intent navigation;
        if (isArrived) {
            navigation = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" +
                    String.valueOf(Config.getInstance().getCurrentLatitude()) + "," +
                    String.valueOf(Config.getInstance().getCurrentLongitude()) + "&daddr=" +
                    String.valueOf(tripBean.getDestinationLatitude())
                    + "," + String.valueOf(tripBean.getDestinationLongitude())));
        } else {
            navigation = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" +
                    String.valueOf(Config.getInstance().getCurrentLatitude()) + "," +
                    String.valueOf(Config.getInstance().getCurrentLongitude()) + "&daddr=" +
                    String.valueOf(tripBean.getSourceLatitude())
                    + "," + String.valueOf(tripBean.getSourceLongitude())));
        }


        navigation.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

        if (navigation.resolveActivityInfo(getPackageManager(), 0) != null) {
            startActivity(navigation);
        } else {
            Toast.makeText(this, R.string.message_google_map_is_not_available, Toast.LENGTH_SHORT).show();
        }

    }


    public void onRefreshClick(View view) {
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

        /*
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
       Intent intentSplash = new Intent(OnTripActivity.this,SplashActivity.class);
       startActivity(intentSplash);
       finish();
       */

    }


    private void fetchAppStatus() {

        HashMap<String, String> urlParams = new HashMap<>();
        JSONObject postData =getJsonPostData();


        DataManager.fetchAppStatus(postData, new AppStatusListener() {
            @Override
            public void onLoadCompleted(AppStatusBean appStatusBeanWS) {

                if (appStatusBeanWS.getAppStatus() == AppConstants.APP_STATUS_ASSIGNED) {
                    tripBean = setTripBean(appStatusBeanWS);

                } else {
                    if(mHandler!=null)
                    mHandler.removeCallbacks(checkAppStatusTask);
                    startActivity(new Intent(OnTripActivity.this, SplashActivity.class)
                            .putExtra("trip_id", tripBean.getTripId())
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    finish();
                }
            }

            @Override
            public void onLoadFailed(String error) {

            }

        });
    }

    public JSONObject getJsonPostData() {
        JSONObject jsonObjectPostData=new JSONObject();
        String driver_id ="";
        if(!Config.getInstance().getUserID().equals("")){
            driver_id=Config.getInstance().getUserID();
        }else driver_id="123456";
        try {
            jsonObjectPostData.put("driver_id",driver_id);
            jsonObjectPostData.put("phone",Config.getInstance().getPhone());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObjectPostData;
    }

    Runnable checkAppStatusTask = new Runnable() {
        @Override
        public void run() {
            if(App.isNetworkAvailable()) {
                fetchAppStatus();
            }
            if (Config.getInstance().getCurrentLatitude() != null
                    && !Config.getInstance().getCurrentLatitude().equals("")
                    && Config.getInstance().getCurrentLongitude() != null
                    && !Config.getInstance().getCurrentLongitude().equals("")) {

                populateMap(Double.parseDouble(Config.getInstance().getCurrentLatitude()),Double.parseDouble(Config.getInstance().getCurrentLongitude()));
                //  mapAutoZoom(Double.parseDouble(Config.getInstance().getCurrentLatitude()),Double.parseDouble(Config.getInstance().getCurrentLongitude()));
            }
          //  performCarLocationUpade();
          //  populateMap();
           // performCarLocationUpade();
            mHandler.postDelayed(checkAppStatusTask, 6500);
        }
    };



    public void performCarLocationUpade(Location locationCar ){
        JSONObject postData = new JSONObject();
        try {
            postData.put("car_lat",String.valueOf(locationCar.getLatitude()));
            postData.put("car_long",String.valueOf(locationCar.getLongitude()));
            postData.put("phone",Config.getInstance().getPhone());
            postData.put("trip_id",tripBean.getTripId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DataManager.performUpdateDriverLocation(postData, new BasicListener() {
            @Override
            public void onLoadCompleted(BasicBean basicBean) {

            }

            @Override
            public void onLoadFailed(String error) {

            }
        });
    }


    private TripBean setTripBean(AppStatusBean appStatusBean) {
        TripBean tripBean = new TripBean();
        tripBean.setId(appStatusBean.getTripId());
        tripBean.setTripId(appStatusBean.getTripId());
        tripBean.setTripStatus(appStatusBean.getTripStatus());
        tripBean.setDriverID(appStatusBean.getDriverID());
        tripBean.setDriverName(appStatusBean.getDriverName());
        tripBean.setDriverPhoto(appStatusBean.getDriverPhoto());
        tripBean.setDriverStatus(appStatusBean.getDriverStatus());
        tripBean.setCustomerID(appStatusBean.getCustomerID());
        tripBean.setCustomerPhone(appStatusBean.getCustomerPhone());
        tripBean.setCustomerName(appStatusBean.getCustomerName());
        tripBean.setCustomerPhoto(appStatusBean.getCustomerPhoto());
        tripBean.setSourceLocation(appStatusBean.getSourceLocation());
        tripBean.setSourceLatitude(appStatusBean.getSourceLatitude());
        tripBean.setSourceLongitude(appStatusBean.getSourceLongitude());
        tripBean.setDestinationLocation(appStatusBean.getDestinationLocation());
        tripBean.setDestinationLatitude(appStatusBean.getDestinationLatitude());
        tripBean.setDestinationLongitude(appStatusBean.getDestinationLongitude());

        return tripBean;
    }

    private void performTripStart() {
      //  swipeView.setRefreshing(true);

        JSONObject postData = getTripStartJSObj();

        DataManager.performTripStart(postData, new BasicListener() {
            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                isArrived=true;
              //  swipeView.setRefreshing(false);

                calStart = Calendar.getInstance();
                calStart.setTimeZone(TimeZone.getTimeZone("UTC"));

                pathList = new ArrayList<>();
                if (Config.getInstance().getCurrentLongitude() != null
                        && !Config.getInstance().getCurrentLongitude().equalsIgnoreCase("")
                        && Config.getInstance().getCurrentLatitude() != null
                        && !Config.getInstance().getCurrentLatitude().equalsIgnoreCase("")) {
                    PathBean pathBean = new PathBean();
                    pathBean.setLatitude(Config.getInstance().getCurrentLatitude());
                    pathBean.setLongitude(Config.getInstance().getCurrentLongitude());
                    pathBean.setIndex(0);
                    if(calStart.getTimeInMillis()!=0){
                        pathBean.setTime(calStart.getTimeInMillis() / 1000);
                    }
                    pathList.add(pathBean);
                    populateMap(Double.parseDouble(Config.getInstance().getCurrentLatitude()),Double.parseDouble(Config.getInstance().getCurrentLongitude()));
                    mapAutoZoom(Double.parseDouble(Config.getInstance().getCurrentLatitude()),Double.parseDouble(Config.getInstance().getCurrentLongitude()));
                }
                if (App.isNetworkAvailable()) {
                    fetchAppStatus();
                }

                populateTripOntheret();

                lytBeforeTrip.setVisibility(View.GONE);
                lytBottomSheet.setVisibility(View.VISIBLE);

                Toast.makeText(OnTripActivity.this, R.string.message_trip_started, Toast.LENGTH_LONG).show();

             //   Snackbar.make(coordinatorLayout, R.string.message_trip_started, Snackbar.LENGTH_LONG)
                    //    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }

            @Override
            public void onLoadFailed(String error) {
                Toast.makeText(OnTripActivity.this, "Trip Code Not Maching!!!", Toast.LENGTH_LONG).show();
              //  swipeView.setRefreshing(false);
               // Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                  //      .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }
        });


    }

    private JSONObject getTripStartJSObj() {
        JSONObject postData = new JSONObject();

        try {
            postData.put("trip_id", tripBean.getId());
            postData.put("phone", Config.getInstance().getPhone());
            postData.put("customer_code", customer_confirmaion_code);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    private void performArrivalConfirmation() {
        swipeView.setRefreshing(true);

        JSONObject postData = getArrivalConfirmationJSObj();

        DataManager.performArrivalConfirmation(postData, new BasicListener() {
            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                swipeView.setRefreshing(false);
              //  Snackbar.make(coordinatorLayout, R.string.message_arrival_confirmed, Snackbar.LENGTH_LONG)
                    //    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();

                Toast.makeText(OnTripActivity.this, R.string.message_arrival_confirmed, Toast.LENGTH_LONG).show();
                if(App.isNetworkAvailable()) {
                    fetchAppStatus();
                }
                populateTripOntheret();

                llConfirmArrival.setVisibility(View.GONE);
                llContactPhone.setVisibility(View.GONE);
                llStartTrip.setVisibility(View.VISIBLE);
                isArrived = true;
                intFormap=3;

                if (Config.getInstance().getCurrentLongitude() != null
                        && !Config.getInstance().getCurrentLongitude().equalsIgnoreCase("")
                        && Config.getInstance().getCurrentLatitude() != null
                        && !Config.getInstance().getCurrentLatitude().equalsIgnoreCase("")) {

                    populateMap(Double.parseDouble(Config.getInstance().getCurrentLatitude()),Double.parseDouble(Config.getInstance().getCurrentLongitude()));
                    mapAutoZoom(Double.parseDouble(Config.getInstance().getCurrentLatitude()),Double.parseDouble(Config.getInstance().getCurrentLongitude()));
                }


            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
               // Toast.makeText(OnTripActivity.this, "Please Try Again!!!", Toast.LENGTH_LONG).show();

              //  Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                    //    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();

            }
        });
    }

    private JSONObject getArrivalConfirmationJSObj() {
        JSONObject postData = new JSONObject();

        try {
            postData.put("trip_id", tripBean.getId());
            postData.put("phone",Config.getInstance().getPhone());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }


    private void performTripCompletion() {
       // swipeView.setRefreshing(true);

        mHandler.removeCallbacks(checkAppStatusTask);

        calEnd = Calendar.getInstance();
        calEnd.setTimeZone(TimeZone.getTimeZone("UTC"));

        if (Config.getInstance().getCurrentLongitude() != null
                && !Config.getInstance().getCurrentLongitude().equalsIgnoreCase("")
                && Config.getInstance().getCurrentLatitude() != null
                && !Config.getInstance().getCurrentLatitude().equalsIgnoreCase("")) {
            PathBean pathBean = new PathBean();
            pathBean.setLatitude(Config.getInstance().getCurrentLatitude());
            pathBean.setLongitude(Config.getInstance().getCurrentLongitude());
            if(pathList!=null) {
                pathBean.setIndex(pathList.size());
            }
            if(calEnd.getTimeInMillis()!=0){
                pathBean.setTime(calEnd.getTimeInMillis() / 1000);
            }
            if(pathList!=null) {
                pathList.add(pathBean);
            }
        }

        JSONObject postData = getTripCompletionJSObj();

        DataManager.performTripCompletion(postData, new TripDetailsListener() {
            @Override
            public void onLoadCompleted(TripBean tripBean) {
               // swipeView.setRefreshing(false);
                simpleProgressBarTripEnd.setVisibility(View.GONE);
                Toast.makeText(OnTripActivity.this, R.string.message_trip_ended_please_collect_cash, Toast.LENGTH_LONG).show();
                startActivity(new Intent(OnTripActivity.this, TripSummaryActivity.class)
                        .putExtra("trip_id", tripBean.getId()));
                finish();
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                tripCompleteBtn.setEnabled(true);
                simpleProgressBarTripEnd.setVisibility(View.GONE);
               // Toast.makeText(OnTripActivity.this, "Please try again!!", Toast.LENGTH_LONG).show();
              //  Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                     //   .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }
        });

    }

    private JSONObject getTripCompletionJSObj() {
        JSONObject postData = new JSONObject();

        try {
            if(calStart==null) {
                calStart = Calendar.getInstance();
                calStart.setTimeZone(TimeZone.getTimeZone("UTC"));
            }
            if(calEnd==null) {
                calEnd = Calendar.getInstance();
                calEnd.setTimeZone(TimeZone.getTimeZone("UTC"));
            }

            postData.put("trip_id", tripBean.getId());
            if(calStart.getTimeInMillis()!=0)
            postData.put("start_time", calStart.getTimeInMillis()/1000);
            if(calEnd.getTimeInMillis()!=0)
            postData.put("end_time", calEnd.getTimeInMillis()/1000);


            JSONArray pathArray = new JSONArray();
            if (pathList != null) {
                for (PathBean bean : pathList) {
                    JSONObject path = new JSONObject();

                    try {
                        path.put("index", bean.getIndex());
                        path.put("time", bean.getTime());
                        path.put("latitude", bean.getLatitude());
                        path.put("longitude", bean.getLongitude());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    pathArray.put(path);
                }
            }

            postData.put("path", pathArray);
            postData.put("phone", Config.getInstance().getPhone());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }
int intFormap=0;
    private void populateMap(double latitude, double longitude) {
        if(intFormap==5){
            mMap.clear();
            intFormap=0;
        }


            if (isArrived) {
                onPlotLatLng1(latitude, longitude,
                        tripBean.getDDestinationLatitude(), tripBean.getDDestinationLongitude(), latitude, longitude);
            } else {
                onPlotLatLng(latitude, longitude,
                        tripBean.getDSourceLatitude(), tripBean.getDSourceLongitude(), latitude, longitude);
            }
            if (intFormap == 0) {
                // mapAutoZoom(latitude, longitude);
            }
            intFormap++;

    }
int once=1;
    private void onPlotLatLng(double sourceLatitude, double sourceLongitude, double destinationLatitude, double destinationLongitude,double latitude,double longitude) {

        fetchPolyPoint(latitude,longitude);

        LatLng newLatLng = null;
        try {
            newLatLng = new LatLng(sourceLatitude, sourceLongitude);
if(once==1) {
    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 15));
    once=0;
}

           mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//            mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_driver_location)));


            newLatLng = new LatLng(destinationLatitude, destinationLongitude);
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 11));
            mMap.addMarker(new MarkerOptions().position(newLatLng).title("Left "+etaTimeLeft).icon(BitmapDescriptorFactory.fromBitmap(smallMarker_pick)));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


    int once2=1;
    private void onPlotLatLng1(double sourceLatitude, double sourceLongitude, double destinationLatitude, double destinationLongitude,double latitude,double longitude) {

        fetchPolyPoint(latitude,longitude);

        LatLng newLatLng = null;
        try {
            newLatLng = new LatLng(sourceLatitude, sourceLongitude);
            if(once2==1) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 15));
                once2=0;
            }

            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//            mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_driver_location)));


            newLatLng = new LatLng(destinationLatitude, destinationLongitude);
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 11));
            mMap.addMarker(new MarkerOptions().position(newLatLng).title("Left "+etaTimeLeft).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void mapAutoZoom(double latitude,double longitude ) {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();

        if (isArrived) {
            builder.include(new LatLng(latitude,longitude));
            builder.include(tripBean.getDestinationLatLng());
        } else {
            builder.include(new LatLng(latitude,longitude));
            builder.include(tripBean.getSourceLatLng());
        }

        LatLngBounds bounds = builder.build();
        if(myMapFragment!=null) {

            if (myMapFragment.getView().getHeight() > 150 * px) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (50 * px)));
            }else {
                mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (5 * px)));
            }
        }

    }

    public void fetchPolyPoint(double latitude,double longitude) {

        HashMap<String, String> urlParams = new HashMap<>();

        urlParams.put("origin", String.valueOf(latitude) + "," + String.valueOf(longitude));
        if (isArrived) {
            urlParams.put("destination", tripBean.getDestinationLatitude() + "," + tripBean.getDDestinationLongitude());
        } else {
            urlParams.put("destination", tripBean.getSourceLatitude() + "," + tripBean.getSourceLongitude());
        }
        urlParams.put("mode", "driving");
        urlParams.put("key", getString(R.string.browser_api_key));

        DataManager.fetchPolyPoints(urlParams, new PolyPointListener() {

            @Override
            public void onLoadCompleted(PolyPointBean polyPointBeanWS) {
                swipeView.setRefreshing(false);

                polyPointBean = polyPointBeanWS;
                etaDistance=polyPointBean.getDistanceText();
                etaTimeLeft=polyPointBean.getTimeText();
                populatePath();

            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
               // Toast.makeText(OnTripActivity.this,"Loading..",Toast.LENGTH_SHORT).show();

               // Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                      //  .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }
        });
    }

    private void populatePath() {

        ArrayList<List<HashMap<String, String>>> routes = polyPointBean.getRoutes();

        ArrayList<LatLng> points = null;
        PolylineOptions polyLineOptions = null;

        // traversing through routes
        for (int i = 0; i < routes.size(); i++) {
            points = new ArrayList<LatLng>();
            polyLineOptions = new PolylineOptions();
            List path = routes.get(i);

            for (int j = 0; j < path.size(); j++) {
                HashMap point = (HashMap) path.get(j);

                double lat = Double.parseDouble((String) point.get("lat"));
                double lng = Double.parseDouble((String) point.get("lng"));
                LatLng position = new LatLng(lat, lng);

                points.add(position);
            }

            polyLineOptions.addAll(points);
            polyLineOptions.width(6);
            polyLineOptions.color(ContextCompat.getColor(getApplicationContext(), R.color.black));

        }

        polyLine = mMap.addPolyline(polyLineOptions);
    }

    public void setUpLocationClientIfNeeded() {
        if (App.getInstance().getGoogleApiClient() == null) {

            mGoogleApiClient = new GoogleApiClient.Builder(App.getInstance().getApplicationContext())
                    .addApi(LocationServices.API)
                    .enableAutoManage(this, this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .build();
            App.getInstance().setGoogleApiClient(mGoogleApiClient);
        } else {
            mGoogleApiClient = App.getInstance().getGoogleApiClient();
        }

        if (isInit) {
            mGoogleApiClient.registerConnectionCallbacks(this);
            mGoogleApiClient.registerConnectionFailedListener(this);
            isInit = false;
        }
    }

    public void getCurrentLocation() {
        setUpLocationClientIfNeeded();
        if (!mGoogleApiClient.isConnected()) {
            mGoogleApiClient.connect();
        }
        if (ActivityCompat.checkSelfPermission(App.getInstance(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(App.getInstance(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (!checkForLocationPermissions()) {
                getLocationPermissions();
            }
            checkLocationSettingsStatus();
        } else {
            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {

                if (LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient) != null) {
                    Config.getInstance().setCurrentLatitude(""
                            + LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLatitude());
                    Config.getInstance().setCurrentLongitude(""
                            + LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLongitude());
                }
            /*else{
                System.out.println("Last Location : " + mockLocation);
				currentLatitude = ""+mockLocation.getLatitude();
				currentLongitude = ""+mockLocation.getLongitude();
			}*/
            }
        }

        locationManager = (LocationManager) App.getInstance().getSystemService(Context.LOCATION_SERVICE);
        if (hasLocationPermissions) {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            } else {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
            }
        }
       /* if ((Config.getInstance().getCurrentLatitude() == null
                || Config.getInstance().getCurrentLongitude() == null)
                || (Config.getInstance().getCurrentLatitude().equals("")
                || Config.getInstance().getCurrentLatitude().equals(""))) {
            //Toast.makeText(MapActivity.this, "Retrieving Current Location...", Toast.LENGTH_SHORT).show();
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            if (hasLocationPermissions) {
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
                } else {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
                }
            }

            //			mHandler.postDelayed(periodicTask, 3000);
        } else {
            if (mGoogleApiClient != null) {
                mGoogleApiClient.disconnect();
            }
        }*/
    }

    @Override
    public void onLocationChanged(Location location) {
        /*
       if ((Config.getInstance().getCurrentLatitude() == null || Config.getInstance().getCurrentLongitude() == null)
                || (Config.getInstance().getCurrentLatitude().equals("") || Config.getInstance().getCurrentLatitude().equals(""))) {
            Config.getInstance().setCurrentLatitude("" + location.getLatitude());
            Config.getInstance().setCurrentLongitude("" + location.getLongitude());
          //  moveToCurrentLocation();
        } else {
            if (mGoogleApiClient != null) {
                mGoogleApiClient.disconnect();
            }
        }
        */
        Config.getInstance().setCurrentLatitude("" + location.getLatitude());
        Config.getInstance().setCurrentLongitude("" + location.getLongitude());

        Log.i(TAG, "onLocationChanged: LATITUDE : OntripActivity" + location.getLatitude());
        Log.i(TAG, "onLocationChanged: LONGITUDE : ontripActivity" + location.getLongitude());


        if (isTripStarted) {
            if (pathList == null) {
                pathList = new ArrayList<>();
            }
            PathBean bean = new PathBean();
            bean.setIndex(pathList.size());
            bean.setTime(Calendar.getInstance(TimeZone.getTimeZone("UTC")).getTimeInMillis() / 1000);
            bean.setLatitude(Config.getInstance().getCurrentLatitude());
            bean.setLongitude(Config.getInstance().getCurrentLongitude());

            pathList.add(bean);

        }

        if ((Calendar.getInstance().getTimeInMillis() - Config.getInstance().getLastUpdate()) > 6500) {
            performDriverLocationUpdate(location);
           // performCarLocationUpade(location);
          //  populateMap(location.getLatitude(),location.getLongitude());
          //  fetchAppStatus();
        }

/*
        if ((Calendar.getInstance().getTimeInMillis() - Config.getInstance().getLastUpdate()) > 10000) {
           // performDriverLocationUpdate(location);
           // performCarLocationUpade(location);
           // populateMap(location.getLatitude(),location.getLongitude());
            fetchAppStatus();
        }
        */

    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onConnectionFailed(ConnectionResult status) {
    }

    @Override
    public void onConnected(Bundle arg0) {

        try {
            if (ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(App.getInstance().getApplicationContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                if (!checkForLocationPermissions()) {
                    getLocationPermissions();
                }
                checkLocationSettingsStatus();
            } else {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
                if (LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient) != null) {
                    Config.getInstance().setCurrentLatitude(""
                            + LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLatitude());
                    Config.getInstance().setCurrentLongitude(""
                            + LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient).getLongitude());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //	mGoogleApiClient.requestLocationUpdates(mLocationRequest,MapActivity.this);
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


    @Override
    public void onConnectionSuspended(int arg0) {

    }
/*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //            onBackPressed();
        }
        if (keyCode == KeyEvent.KEYCODE_APP_SWITCH) {
            //            onBackPressed();
        }
        if (keyCode == KeyEvent.KEYCODE_VOICE_ASSIST) {
            //            onBackPressed();
        }

        if (keyCode == KeyEvent.KEYCODE_HOME) {
            //            onBackPressed();
        }

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            openOptionsMenu();
        }
        return true;
    }
    */
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
