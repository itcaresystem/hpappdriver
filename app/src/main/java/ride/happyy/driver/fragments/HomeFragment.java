package ride.happyy.driver.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import ride.happyy.driver.R;
import ride.happyy.driver.activity.TripHistoryActivity;
import ride.happyy.driver.adapter.HomeTripHistoryRecyclerAdapter;
import ride.happyy.driver.app.App;
import ride.happyy.driver.config.Config;
import ride.happyy.driver.listeners.BasicListener;
import ride.happyy.driver.listeners.TripListListener;
import ride.happyy.driver.model.BasicBean;
import ride.happyy.driver.model.MapBean;
import ride.happyy.driver.model.PlaceBean;
import ride.happyy.driver.model.TripBean;
import ride.happyy.driver.model.TripListBean;
import ride.happyy.driver.net.DataManager;
import ride.happyy.driver.util.AppConstants;

import static ride.happyy.driver.app.App.getStatusBarHeight;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragmentListener} interface
 * to handle interaction events.
 */
public class HomeFragment extends BaseFragment implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMyLocationButtonClickListener, com.google.android.gms.location.LocationListener,
        android.location.LocationListener {

    private static final String TAG = "HomeFrag";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 100;
    protected LocationManager locationManager;
    protected LocationListener locationListener;
    private GoogleApiClient mGoogleApiClient;
    private static final LocationRequest mLocationRequest = LocationRequest.create()
            .setInterval(5000)         // 5 seconds
            .setFastestInterval(16)    // 16ms = 60fps
            .setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);


    private static GoogleMapOptions options = new GoogleMapOptions()
            .mapType(GoogleMap.MAP_TYPE_NORMAL)
            .compassEnabled(true)
            .rotateGesturesEnabled(true)
            .tiltGesturesEnabled(true)
            .zoomControlsEnabled(true)
            .scrollGesturesEnabled(true)
            .mapToolbarEnabled(true);

    private GoogleMap mMap;
    private static SupportMapFragment myMapFragment = SupportMapFragment.newInstance(options);
    private FragmentManager myFragmentManager;

    private Handler mHandler = new Handler();

    private HomeFragmentListener mListener;
    private FloatingActionButton fabMyLocation;
    private CardView cardBottomSheet;
    private View lytCardOffline;
    private LatLng current;
    private double dLatitude;
    private double dLongitude;
    private LatLng center;
    private LatLng newLatLng;
    private MapBean mapBean;
    private boolean isInit = true;
    private boolean isMarkerClicked;
    private LatLng latLngClickedMarker;
    private int selectedPosition = -1;
    private ArrayList<PlaceBean> placesList;
    private ArrayList<String> plotList;
    private HashMap<String, Integer> markerMap;
    private Bitmap mapPin;
    private Bitmap frame;
    private String pathIn;
    private LinearLayout llCard1;
    private BottomSheetBehavior<CardView> bottomSheetBehavior;
    private View lytCardOnline;
    private boolean isOnline;
    private TripListBean tripListBean;
    private TextView txtNoTrips;
    private boolean isCameraMoved;
    private RecyclerView rvTodayTrips;
    private LinearLayoutManager linearLayoutManager;
    private HomeTripHistoryRecyclerAdapter adapter;
    private TextView txtTripsCount;
    private TextView txtPartnerMessage;
    private TextView txtFare;
    private TextView txtTime;
    private View.OnClickListener snackBarRefreshOnClickListener;
    private Button btnTripHistory;
    private boolean isDriverLocationUpdated;
    private Marker CurrentMarker;
    private Bitmap smallMarker;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initBase(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_home, null);
        lytContent.addView(rootView);
        BitmapDrawable bitmapDrawableIcon = (BitmapDrawable) getResources().getDrawable(R.drawable.marker_location_p);
        Bitmap mylocIc = bitmapDrawableIcon.getBitmap();
        smallMarker = Bitmap.createScaledBitmap(mylocIc, 25, 25, false);


       /* if (getArguments().containsKey("mapBean"))
            mapBean = (MapBean) getArguments().getSerializable("mapBean");*/

        intiView(rootView);

       // before
      //  initMap(rootView);


        //after
        initMap1(rootView);

//        mGoogleApiClient = App.getInstance().getGoogleApiClient();
       /* mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
*/
//        mHandler.postDelayed(locationTask, 1000);

        return lytBase;
    }

    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int result = googleApiAvailability.isGooglePlayServicesAvailable(App.getInstance().getApplicationContext());
        if (result != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(result)) {
                googleApiAvailability.getErrorDialog(getActivity(), result,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!checkForLocationPermissions())
            getLocationPermissions();
        if (checkLocationSettingsStatus()) {
            getCurrentLocation();
        }

        setDriverStatus(Config.getInstance().isOnline());
        onRefresh();
    }

    public void onRefresh() {
        setProgressScreenVisibility(false, false);
        getData(true);
    }

/*
    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient != null) {
//            mGoogleApiClient.stopAutoManage(getActivity());
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mGoogleApiClient != null) {
//            mGoogleApiClient.stopAutoManage(getActivity());
            mGoogleApiClient.disconnect();
        }
    }
*/

    private void getData(boolean isSwipeRefreshing) {
        if (getActivity() != null)
            mListener.onSwipeRefreshChange(isSwipeRefreshing);
        if (App.isNetworkAvailable()) {
            fetchTodayTripList(false, 0);
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
        }
    }

    private void intiView(View rootView) {

        snackBarRefreshOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                mVibrator.vibrate(25);
                setProgressScreenVisibility(false, false);
                getData(true);
            }
        };


        fabMyLocation = (FloatingActionButton) rootView.findViewById(R.id.fab_home_my_location);

        btnTripHistory = (Button) rootView.findViewById(R.id.btn_home_card_1_trip_history);

        cardBottomSheet = (CardView) rootView.findViewById(R.id.card_home_details);
        llCard1 = (LinearLayout) rootView.findViewById(R.id.ll_home_card_1);
        lytCardOffline = rootView.findViewById(R.id.lyt_home_card_offline);
        lytCardOnline = rootView.findViewById(R.id.lyt_home_card_online);

        txtTripsCount = (TextView) cardBottomSheet.findViewById(R.id.txt_home_card_online_trips_count);
        txtPartnerMessage = (TextView) cardBottomSheet.findViewById(R.id.txt_home_card_online_partner_message);

        txtFare = (TextView) cardBottomSheet.findViewById(R.id.txt_home_card_offline_fare);
        txtTime = (TextView) cardBottomSheet.findViewById(R.id.txt_home_card_offline_time);

        txtNoTrips = (TextView) cardBottomSheet.findViewById(R.id.txt_home_card_1_no_trips_taken);

        rvTodayTrips = (RecyclerView) cardBottomSheet.findViewById(R.id.rv_home_trip_history);

        llCard1.setVisibility(View.GONE);
        txtNoTrips.setVisibility(View.VISIBLE);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTodayTrips.setLayoutManager(linearLayoutManager);
        rvTodayTrips.addItemDecoration(
                new DividerItemDecoration(App.getInstance().getApplicationContext(), LinearLayoutManager.VERTICAL));

        bottomSheetBehavior = BottomSheetBehavior.from(cardBottomSheet);
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


        lytCardOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);

                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        lytCardOnline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);

                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                } else {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        fabMyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);
               moveToCurrentLocation();

                if (mGoogleApiClient == null || mGoogleApiClient.isConnected() || !mGoogleApiClient.isConnecting()) {
                    getCurrentLocation();

                } else {
                    mGoogleApiClient.connect();
                }

            }
        });

        btnTripHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);

                startActivity(new Intent(getActivity(), TripHistoryActivity.class));
            }
        });

    }

    //from ride app init map

    private void initMap1(View view) {
        myMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_map);


        myMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setPadding(0,0/*(int) ((100 * px) + mActionBarHeight + getStatusBarHeight())*/, 0, (int) (100 * px));

                initMapLoad1();


            }
        });
    }

    private void initMapLoad1() {

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED || bottomSheetBehavior.getPeekHeight() == 100 * px) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {

            @Override
            public void onCameraMove() {

                /*if (sourceBean != null & destinationBean != null) {
                    fetchTotalfare();
                    txtFare.setText(fareBean.getTotalFare());
                }*/
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED || bottomSheetBehavior.getPeekHeight() == 100 * px) {
                    bottomSheetBehavior.setPeekHeight(0);
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }


                    mMap.getUiSettings().setScrollGesturesEnabled(true);
                    mMap.setMaxZoomPreference(18f);
                    // framePickup.setVisibility(View.INVISIBLE);


               isCameraMoved = true;
                }

        });

        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {





                    CameraPosition postion = mMap.getCameraPosition();
                    LatLng center = postion.target;

                    // framePickup.setVisibility(View.VISIBLE);


                    if (bottomSheetBehavior.getPeekHeight() == 0) {
                        bottomSheetBehavior.setPeekHeight((int) (100 * px));
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
//                        llLandingBottomBar.animate().translationY(00*px).setDuration(1000).start();
                    }

                    Log.i(TAG, "onCameraIdle: GetLocationName Called : " + center);

                    isCameraMoved = false;
                }

        });
    }


    private void initMap(View rootView) {

        plotList = new ArrayList<>();
        markerMap = new HashMap();
/*
        frame = BitmapFactory.decodeResource(getResources(), R.drawable.bg_map_pin);
        mapPin = BitmapFactory.decodeResource(getResources(), R.drawable.ic_hunt_green);
        mapPinRed = BitmapFactory.decodeResource(getResources(), R.drawable.ic_hunt_red);
*/

        mapPin = BitmapFactory.decodeResource(getResources(), R.drawable.car_logo_for_curren_location);


        myFragmentManager = getChildFragmentManager();
        myMapFragment = (SupportMapFragment) myFragmentManager.findFragmentById(R.id.fragment_map);
        //	mMap = myMapFragment.getMap();

        myMapFragment.getMapAsync(new OnMapReadyCallback() {

            @Override
            public void onMapReady(GoogleMap mapTemp) {

                Log.i(TAG, "onMapReady: MAP IS LOADED");

                mMap = mapTemp;
                mMap.setPadding(0, 0/*(int) ((100 * px) + mActionBarHeight + getStatusBarHeight())*/, 0, (int) (140 * px));
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
        if (ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (!checkForLocationPermissions()) {
                getLocationPermissions();
            }
            checkLocationSettingsStatus();
        } else {
            mMap.setMyLocationEnabled(true);
        }
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);

        //myMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        //myMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);


//        moveToCurrentLocation();
        /*		mMap.addMarker(new MarkerOptions()
        .position(current)
		.title("Me At Here"));
		 */

/*
        if (placesBean != null) {
            dLatitude = Double.parseDouble(placesBean.getLatitude());
            dLongitude = Double.parseDouble(placesBean.getLongitude());

            newLatLng = new LatLng(dLatitude, dLongitude);

            mMap.addMarker(new MarkerOptions()
                    .position(newLatLng)
                    .title(placesBean.getName())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 12));
        }*/

        if (mapBean != null && mapBean.getPlaces() != null && !mapBean.getPlaces().isEmpty()) {
            dLatitude = mapBean.getPlaces().get(0).getDLatitude();
            dLongitude = mapBean.getPlaces().get(0).getDLongitude();

            /*
             if(CurrentMarker != null){
        CurrentMarker.remove();
    }

    LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
    MarkerOptions markerOption = new MarkerOptions();
    markerOption.position(latLng);
    markerOption.title("Current Position");
    markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
    CurrentMarker = mMap.addMarker(markerOption);
    Toast.makeText(this,"Location changed",Toast.LENGTH_SHORT).show();
    CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(13).build();
    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

             */


            newLatLng = new LatLng(dLatitude, dLongitude);
            if (dLatitude != 0.0 && dLongitude != 0.0)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(newLatLng, 15));
        }
        center = mMap.getCameraPosition().target;

        mMap.setOnCameraMoveListener(new GoogleMap.OnCameraMoveListener() {

            @Override
            public void onCameraMove() {

                if (!isInit) {
                    center = mMap.getCameraPosition().target;
                    if (isMarkerClicked) {

                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                                + "\n Center : " + center + "\n Marker : " + latLngClickedMarker);
                        if (("" + center.latitude).substring(0, 4).equals(("" + latLngClickedMarker.latitude).substring(0, 4))
                                && ("" + center.longitude).substring(0, 4).equals(("" + latLngClickedMarker.longitude).substring(0, 4))) {
                            isMarkerClicked = false;
                        }
                    } else {
                        /*if (llMapDetails.isShown()) {
                            llMapDetails.setVisibility(View.GONE);
                        }*/
                        /*if (!isLoadMore) {
                            fetchMap();
						}*/
                        isInit = false;
                    }
                }
            }

        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {

                if (mapBean != null && mapBean.getPlaces() != null && !mapBean.getPlaces().isEmpty()) {
/*
                    if (llMapDetails.isShown()) {
                        llMapDetails.setVisibility(View.GONE);
                    }
*/
                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
                            + "\nMarker Clicked : Place : " + marker.getTitle()
                            + "\n\t Position : " + marker.getPosition());
                    latLngClickedMarker = marker.getPosition();
                    isMarkerClicked = true;
                    setDetails(marker);
                }
                return false;
            }
        });

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

        try {
            populateMapBean(mapBean);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setDriverStatus(boolean isOnline) {
        try {
            if (isOnline) {
                lytCardOnline.setVisibility(View.VISIBLE);
                lytCardOffline.setVisibility(View.GONE);
            } else {
                lytCardOnline.setVisibility(View.GONE);
                lytCardOffline.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void fetchTodayTripList(final boolean isLoadMore, int currentPage) {

        HashMap<String, String> urlParams = new HashMap<>();

        if (isLoadMore) {
            urlParams.put("page", String.valueOf(currentPage + 1));
        }

        DataManager.fetchTodayTripList(urlParams, new TripListListener() {
            @Override
            public void onLoadCompleted(TripListBean tripListBeanWS) {

                setTripListBean(tripListBeanWS, isLoadMore);
                if (getActivity() != null)
                    populateTripList();

            }

            @Override
            public void onLoadFailed(String error) {
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
                if (getActivity() != null) {
                    mListener.onSwipeRefreshChange(false);
                }

                if (App.getInstance().isDemo()) {
                    tripListBean = new TripListBean();
                    tripListBean.setTrips(new ArrayList<TripBean>());
                    tripListBean.setTotalFare(AppConstants.INR + " 00");
                    tripListBean.setTotalRidesTaken(0);
                    tripListBean.setTotalTimeOnline("2:30 Hours Online");
                    populateTripList();
                }
            }
        });

    }

    private void populateTripList() {

        if (adapter == null) {
            adapter = new HomeTripHistoryRecyclerAdapter(getActivity(), tripListBean);
            adapter.setHomeTripHistoryRecyclerAdapterListener(new HomeTripHistoryRecyclerAdapter.HomeTripHistoryRecyclerAdapterListener() {
                @Override
                public void onRequestNextPage(boolean isLoadMore, int currentPageNumber) {
                    mListener.onSwipeRefreshChange(true);
                    fetchTodayTripList(isLoadMore, currentPageNumber);
                }

                @Override
                public void onRefresh() {
                    fetchTodayTripList(false, 0);
                }

                @Override
                public void onSwipeRefreshingChange(boolean isSwipeRefreshing) {
                    if (getActivity() != null)
                        mListener.onSwipeRefreshChange(isSwipeRefreshing);
                }

                @Override
                public void onSnackBarShow(String message) {
                    Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
                            .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                }
            });
            rvTodayTrips.setAdapter(adapter);
        } else {
            adapter.setLoadMore(false);
            adapter.setTripListBean(tripListBean);
            adapter.notifyDataSetChanged();
        }

        if (tripListBean != null && tripListBean.getTrips() != null && !tripListBean.getTrips().isEmpty()) {
            llCard1.setVisibility(View.VISIBLE);
            txtNoTrips.setVisibility(View.GONE);
        } else {
            llCard1.setVisibility(View.GONE);
            txtNoTrips.setVisibility(View.VISIBLE);
        }

        txtTripsCount.setText(tripListBean.getTotalRidesTaken() + " " + getString(R.string.label_trips_taken));
//        txtPartnerMessage.setText(tripListBean.get);
        txtFare.setText(tripListBean.getTotalFare());
        txtTime.setText(tripListBean.getTotalTimeOnline() + getString(R.string.label_hours_online));

        setDriverStatus(Config.getInstance().isOnline());

        if (getActivity() != null)
            mListener.onSwipeRefreshChange(false);


    }

    private void setTripListBean(TripListBean tripListBeanWS, boolean isLoadMore) {

        if (isLoadMore && tripListBean != null && tripListBean.getTrips() != null) {

            ArrayList<TripBean> listExisting = tripListBean.getTrips();
            ArrayList<TripBean> listFromWS = tripListBeanWS.getTrips();

            listExisting.addAll(listFromWS);
            tripListBean = tripListBeanWS;
            tripListBean.setTrips(listExisting);
        } else {
            tripListBean = tripListBeanWS;
        }

    }

    private void moveToCurrentLocation() {
        try {
            if (Config.getInstance().getCurrentLatitude() != null && !Config.getInstance().getCurrentLatitude().equals("")
                    && Config.getInstance().getCurrentLongitude() != null && !Config.getInstance().getCurrentLongitude().equals("")) {
                dLatitude = Double.parseDouble(Config.getInstance().getCurrentLatitude());
                dLongitude = Double.parseDouble(Config.getInstance().getCurrentLongitude());

                FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
             //   double lat = fusedLocationProviderClient.
                current = new LatLng(dLatitude, dLongitude);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 15));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateMapBean(MapBean mapBean) {
        if (mapBean != null && mapBean.getPlaces() != null && !mapBean.getPlaces().isEmpty()) {
            for (int i = 0; i < mapBean.getPlaces().size(); i++) {
                //	new LoadImage(i).execute(mapBean.getPlaces().get(i).getBannerImage());
                try {
                    addPlot(mapBean, i);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void onGetDirectionClick(View v) {
        v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        // mVibrator.vibrate(25);

        Intent navigation = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" +
                String.valueOf(Config.getInstance().getCurrentLatitude()) + "," +
                String.valueOf(Config.getInstance().getCurrentLongitude()) + "&daddr=" +
                String.valueOf(mapBean.getPlaces().get(selectedPosition).getLatitude())
                + "," + String.valueOf(mapBean.getPlaces().get(selectedPosition).getLongitude())));
        navigation.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(navigation);
    }

    protected void populatePlotList() throws Exception {
        placesList = mapBean.getPlaces();

        populateMapBean(mapBean);
        for (int i = 0; i < mapBean.getPlaces().size(); i++) {
            //	new LoadImage(i).execute(mapBean.getPlaces().get(i).getBannerImage());
            try {
                addPlot(mapBean, i);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void setDetails(Marker marker) {

        int id = markerMap.get(marker.getId());
        int pos = 0;
        for (int i = 0; i < mapBean.getPlaces().size(); i++) {
            if (mapBean.getPlaces().get(i).getId() == id) {
                pos = i;
                break;
            }
        }
        selectedPosition = pos;
        PlaceBean bean = mapBean.getPlaces().get(pos);

        //llMapDetails.setVisibility(View.VISIBLE);

    }

    private void addPlot(int pos, Bitmap image) throws Exception {

        PlaceBean bean = mapBean.getPlaces().get(pos);
        //		pathIn=mContext.getFilesDir()+"/"+bean.getId()+".bmp";

        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(bean.getLatLng())
                .title(bean.getName())
                .icon(BitmapDescriptorFactory.fromBitmap(combineImages(frame, image))));

        //					combineImages(frame, thumb);
        markerMap.put(marker.getId(), bean.getId());
    }


    private void addPlot(MapBean mapBean, int pos) throws Exception {

        PlaceBean bean = mapBean.getPlaces().get(pos);

        Log.i(TAG, "addPlot: Pos : " + pos + " Lat : " + bean.getLatitude()
                + "\n Long : " + bean.getLongitude());
        if (bean.getLongitude() != null && !bean.getLongitude().equalsIgnoreCase("")
                && !bean.getLongitude().equalsIgnoreCase("null")
                && bean.getLatitude() != null && !bean.getLatitude().equalsIgnoreCase("")
                && !bean.getLatitude().equalsIgnoreCase("null")) {
            //		pathIn=mContext.getFilesDir()+"/"+bean.getId()+".bmp";

            Marker marker;

            marker = mMap.addMarker(new MarkerOptions()
                    .position(bean.getLatLng())
                    .title(bean.getName())
                    .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                   // .icon(BitmapDescriptorFactory.fromBitmap(mapPin)));

            //					combineImages(frame, thumb);
            markerMap.put(marker.getId(), bean.getId());
        }
    }


    public Bitmap combineImages(Bitmap frame, Bitmap image) {
        Bitmap cs = null;
        Bitmap rs = null;

        rs = Bitmap.createScaledBitmap(frame, (int) px,
                (int) px, true);
        image = Bitmap.createScaledBitmap(image, (int) px - 20,
                (int) px - 26, true);
        cs = Bitmap.createBitmap(rs.getWidth(), rs.getHeight(),
                Bitmap.Config.ARGB_8888);

        Canvas comboImage = new Canvas(cs);

        comboImage.drawBitmap(rs, 0, 0, null);
        comboImage.drawBitmap(image, 9, 10, null);
        if (rs != null) {
            rs.recycle();
            rs = null;
        }
        Runtime.getRuntime().gc();
        return cs;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setListener(activity);
        }
    }

    private void setListener(Context context) {
        if (getActivity() instanceof HomeFragmentListener) {
            mListener = (HomeFragmentListener) getActivity();
        } else if (getParentFragment() instanceof HomeFragmentListener) {
            mListener = (HomeFragmentListener) getParentFragment();
        } else if (context instanceof HomeFragmentListener) {
            mListener = (HomeFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HomeFragmentListener");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof HomeFragmentListener) {
            mListener = (HomeFragmentListener) getActivity();
        } else if (getParentFragment() instanceof HomeFragmentListener) {
            mListener = (HomeFragmentListener) getParentFragment();
        } else if (context instanceof HomeFragmentListener) {
            mListener = (HomeFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HomeFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface HomeFragmentListener {

        void onSwipeRefreshChange(boolean isRefreshing);

        void onSwipeEnabled(boolean isEnabled);
    }


    private void performDriverLocationUpdate(Location location) {

        Config.getInstance().setLastUpdate(Calendar.getInstance().getTimeInMillis());
        JSONObject postData = getUpdateDriverLocationJSObj(location);

        DataManager.performUpdateDriverLocation(postData, new BasicListener() {
            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                isDriverLocationUpdated = true;
            }

            @Override
            public void onLoadFailed(String error) {

            }
        });


    }

    private JSONObject getUpdateDriverLocationJSObj(Location location) {
        JSONObject postData = new JSONObject();

        try {
            postData.put("latitude", location.getLatitude());
            postData.put("longitude", location.getLongitude());
            postData.put("phone",Config.getInstance().getPhone());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    public void setUpLocationClientIfNeeded() {
        if (App.getInstance().getGoogleApiClient() == null) {

            mGoogleApiClient = new GoogleApiClient.Builder(App.getInstance().getApplicationContext())
                    .addApi(LocationServices.API)
                    .enableAutoManage(getActivity(), this)
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
int onece =0;

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

        if(CurrentMarker!=null){
            CurrentMarker.remove();
        }


        LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(latLng);
        markerOption.title("Current Position");
       // markerOption.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        markerOption.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        CurrentMarker = mMap.addMarker(markerOption);
        if(onece==0) {
            CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            onece = 1;
        }

        Log.i(TAG, "onLocationChanged: LATITUDE : " + location.getLatitude());
        Log.i(TAG, "onLocationChanged: LONGITUDE : " + location.getLongitude());

        // moveToCurrentLocation();
/*
        if (Config.getInstance().isOnline()) {
            performDriverLocationUpdate(location);
        }*/

        if (Config.getInstance().isOnline()
                && (Config.getInstance().getCurrentLatitude() == null
                || Config.getInstance().getCurrentLongitude() == null
                || Config.getInstance().getCurrentLatitude().equalsIgnoreCase("")
                || Config.getInstance().getCurrentLongitude().equalsIgnoreCase("")
                || !Config.getInstance().getCurrentLatitude().equalsIgnoreCase(String.valueOf(location.getLatitude()))
                || !Config.getInstance().getCurrentLongitude().equalsIgnoreCase(String.valueOf(location.getLongitude()))
                || !isDriverLocationUpdated)) {

            Log.i(TAG, "onLocationChanged: Config : Latitude : " + Config.getInstance().getCurrentLatitude());
            Log.i(TAG, "onLocationChanged: Config : Longitude : " + Config.getInstance().getCurrentLongitude());
            Log.i(TAG, "onLocationChanged: Location Change : Latitude : " + location.getLatitude());
            Log.i(TAG, "onLocationChanged: Location Change : Longtitude : " + location.getLongitude());

            if ((Calendar.getInstance().getTimeInMillis() - Config.getInstance().getLastUpdate()) > 5000)
                performDriverLocationUpdate(location);
        }

        Config.getInstance().setCurrentLatitude("" + location.getLatitude());
        Config.getInstance().setCurrentLongitude("" + location.getLongitude());
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getActivity(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();
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
