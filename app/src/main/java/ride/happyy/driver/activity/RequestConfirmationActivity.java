package ride.happyy.driver.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.Voice;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import ride.happyy.driver.R;
import ride.happyy.driver.app.App;
import ride.happyy.driver.config.Config;
import ride.happyy.driver.listeners.PolyPointListener;
import ride.happyy.driver.listeners.RequestDetailsListener;
import ride.happyy.driver.listeners.TripDetailsListener;
import ride.happyy.driver.model.PolyPointBean;
import ride.happyy.driver.model.RequestDetailsBean;
import ride.happyy.driver.model.TripBean;
import ride.happyy.driver.net.DataManager;
import ride.happyy.driver.util.AppConstants;

public class RequestConfirmationActivity extends BaseAppCompatNoDrawerActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMyLocationButtonClickListener, com.google.android.gms.location.LocationListener,
        android.location.LocationListener {


    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 100;
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

    private static final String TAG = "RequestConfA";
    private String requestID;
    private View.OnClickListener snackBarRefreshOnClickListener;
    private RequestDetailsBean requestDetailsBean;
    private TextView txtETA;
    private TextView txtDistance;
    private TextView txtCarType;
    private TextView pickup_addressTV,destination_addressTV;
    private Button btnConfirm;
    private ImageView ivCarType;
    private HashMap markerMap;
    private ArrayList<Object> plotList;
    private Bitmap mapPin;
    private FragmentManager myFragmentManager;
    private SupportMapFragment myMapFragment;
    private ViewGroup.LayoutParams param;
    private GoogleMap mMap;
    private LatLng current;
    private double dLatitude;
    private double dLongitude;
    private LatLng center;
    private Polyline polyLine;
    private PolyPointBean polyPointBean;
    private boolean isInit;
    public MediaPlayer voice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_confirmation);


        getSupportActionBar().hide();
        swipeView.setPadding(0, 0, 0, 0);

        if (getIntent().hasExtra("request_id")) {
            requestID = getIntent().getStringExtra("request_id");
        }

        initViews();
        initMap();

        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        for(int i=0;i<3;i++) {
            PlayVoice(this, R.raw.happyriderequesttone);
        }

    }
    public  void PlayVoice(final Context context, int rawVoice) {

        voice = MediaPlayer.create(context, rawVoice);
        voice.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (voice != null) {
                    voice.release();
                }
            }
        });
        voice.start();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (requestDetailsBean == null) {
            setProgressScreenVisibility(true, true);
            getData(false);
        } else {
            getData(true);
        }
    }

    private void getData(boolean isSwipeRefreshing) {

//        swipeView.setRefreshing(isSwipeRefreshing);
        if (App.isNetworkAvailable()) {
            fetchRequestDetails();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
        }
    }

    private void initViews() {

        snackBarRefreshOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                mVibrator.vibrate(25);
                setProgressScreenVisibility(true, true);
                getData(false);
            }
        };

        txtETA = (TextView) findViewById(R.id.txt_request_confirmation_eta);
        txtDistance = (TextView) findViewById(R.id.txt_request_confirmation_distance);
        txtCarType = (TextView) findViewById(R.id.txt_request_confirmation_car_type);
        pickup_addressTV =findViewById(R.id.pick_addressTV);
        destination_addressTV = findViewById(R.id.destination_addressTV);
        ivCarType = (ImageView) findViewById(R.id.iv_request_confirmation_car_type);

        btnConfirm = (Button) findViewById(R.id.btn_request_confirmation_confirm);

        btnConfirm.setTypeface(typeface);


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
        myMapFragment = (SupportMapFragment) myFragmentManager.findFragmentById(R.id.fragment_map);
        //	mMap = myMapFragment.getMap();

        param = myMapFragment.getView().getLayoutParams();
        param.height = (int) (height - getStatusBarHeight() - mActionBarHeight - (80 * px));
        Log.i(TAG, "onSlide: Param Height : " + param.height);
        myMapFragment.getView().setLayoutParams(param);

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
                    //	fetchMap();
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
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

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


    public void onRequestConfirmationConfirmClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        if (App.isNetworkAvailable()) {
            performConfirmTrip();
        }else{
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }

    }


    public void onRequestCancelBtnClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

        Toast.makeText(this,"Cancel is not acceptable.Please swich onto Offline",Toast.LENGTH_LONG).show();
        //mVibrator.vibrate(25);
/*
        if (App.isNetworkAvailable()) {
            performConfirmTrip();
        }else{
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        }
*/
    }

    public void fetchRequestDetails() {

        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("request_id", requestID);
        JSONObject postData = new JSONObject();
        try {
            postData.put("request_id" , requestID);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        DataManager.fetchRequestDetails(postData, new RequestDetailsListener() {

            @Override
            public void onLoadCompleted(RequestDetailsBean requestDetailsBeanWS) {

                requestDetailsBean = requestDetailsBeanWS;
                populateRequestDetails();
            }

            @Override
            public void onLoadFailed(String errorMsg) {
                Snackbar.make(coordinatorLayout, errorMsg, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
                setProgressScreenVisibility(true, false);
                swipeView.setRefreshing(false);

            }
        });
    }

    private void populateRequestDetails() {
        pickup_addressTV.setText(requestDetailsBean.getCustomerLocation());
        destination_addressTV.setText(requestDetailsBean.getDestination_location());
        txtETA.setText(requestDetailsBean.getEta());
        txtCarType.setText(requestDetailsBean.getCarType());
        txtDistance.setText(requestDetailsBean.getDistance());

        Glide.with(getApplicationContext())
                .load(requestDetailsBean.getCarTypeImage())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_car_la_landing_page)
                        .fallback(R.drawable.ic_car_la_landing_page))
                .into(ivCarType);

        if (Config.getInstance().getCurrentLatitude() != null && !Config.getInstance().getCurrentLatitude().equals("")
                && Config.getInstance().getCurrentLongitude() != null && !Config.getInstance().getCurrentLongitude().equals("")) {
            populateMap();
        }

        swipeView.setRefreshing(false);
        setProgressScreenVisibility(false, false);

    }

    private void performConfirmTrip() {

        JSONObject postData = getTripAcceptJSObj();

        DataManager.performTripAccept(postData, new TripDetailsListener() {

            @Override
            public void onLoadCompleted(TripBean tripBean) {
                swipeView.setRefreshing(false);
                Toast.makeText(RequestConfirmationActivity.this, R.string.message_trip_confirmed, Toast.LENGTH_SHORT).show();

                startActivity(new Intent(RequestConfirmationActivity.this, OnTripActivity.class)
                        .putExtra("bean", tripBean));
                finish();
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }
        });


    }

    private JSONObject getTripAcceptJSObj() {

        JSONObject postData = new JSONObject();

        try {
            postData.put("request_id", requestID);
            postData.put("phone", Config.getInstance().getPhone());
            postData.put("driver_id", Config.getInstance().getUserID());
            postData.put("driver_name", Config.getInstance().getName());
            postData.put("driver_photo", Config.getInstance().getProfilePhoto());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    private void populateMap() {
        mMap.clear();
        onPlotLatLng(Double.parseDouble(Config.getInstance().getCurrentLatitude()),
                Double.parseDouble(Config.getInstance().getCurrentLongitude()),
                requestDetailsBean.getDDestinationLatitude(), requestDetailsBean.getDDestinationLongitude());
        mapAutoZoom();
    }


    private void onPlotLatLng(double sourceLatitude, double sourceLongitude, double destinationLatitude, double destinationLongitude) {

        fetchPolyPoint();

        LatLng newLatLng = null;
        try {
            newLatLng = new LatLng(sourceLatitude, sourceLongitude);

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 18));

//            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//            mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_driver_location)));


            newLatLng = new LatLng(destinationLatitude, destinationLongitude);
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 11));
            mMap.addMarker(new MarkerOptions().position(newLatLng).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_pickup)));

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void mapAutoZoom() {

        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(new LatLng(Double.parseDouble(Config.getInstance().getCurrentLatitude()),
                Double.parseDouble(Config.getInstance().getCurrentLongitude())));
        builder.include(requestDetailsBean.getDestinationLatLng());
        LatLngBounds bounds = builder.build();
//        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (50 * px)));
        if (myMapFragment.getView().getHeight() > 150 * px)
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (50 * px)));
        else
            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, (int) (5 * px)));

    }

    public void fetchPolyPoint() {

        HashMap<String, String> urlParams = new HashMap<>();

        urlParams.put("origin", Config.getInstance().getCurrentLatitude() + "," + Config.getInstance().getCurrentLongitude());
        urlParams.put("destination", requestDetailsBean.getCustomerLatitude() + "," + requestDetailsBean.getCustomerLongitude());
        urlParams.put("mode", "driving");
        urlParams.put("key", getString(R.string.browser_api_key));

        DataManager.fetchPolyPoints(urlParams, new PolyPointListener() {

            @Override
            public void onLoadCompleted(PolyPointBean polyPointBeanWS) {
                swipeView.setRefreshing(false);

                polyPointBean = polyPointBeanWS;
                populatePath();

            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }
        });
    }

    private void populatePath() {

        txtDistance.setText(polyPointBean.getDistanceText());
        txtETA.setText(polyPointBean.getTimeText());

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
            polyLineOptions.width(8);
            polyLineOptions.color(ContextCompat.getColor(getApplicationContext(), R.color.map_path));

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


    @Override
    public void onLocationChanged(Location location) {
/*        if ((Config.getInstance().getCurrentLatitude() == null || Config.getInstance().getCurrentLongitude() == null)
                || (Config.getInstance().getCurrentLatitude().equals("") || Config.getInstance().getCurrentLatitude().equals(""))) {
            Config.getInstance().setCurrentLatitude("" + location.getLatitude());
            Config.getInstance().setCurrentLongitude("" + location.getLongitude());
            moveToCurrentLocation();
        } else {
            if (mGoogleApiClient != null) {
                mGoogleApiClient.disconnect();
            }
        }*/
        Config.getInstance().setCurrentLatitude("" + location.getLatitude());
        Config.getInstance().setCurrentLongitude("" + location.getLongitude());

        Log.i(TAG, "onLocationChanged: LATITUDE : " + location.getLatitude());
        Log.i(TAG, "onLocationChanged: LONGITUDE : " + location.getLongitude());


        if (requestDetailsBean != null && requestDetailsBean.getCustomerLatitude() != null
                && !requestDetailsBean.getCustomerLatitude().equalsIgnoreCase("")
                && requestDetailsBean.getCustomerLongitude() != null
                && !requestDetailsBean.getCustomerLongitude().equalsIgnoreCase("")) {
            populateMap();
        }

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

}
