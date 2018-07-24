package ride.happyy.driver.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.HashMap;

import ride.happyy.driver.R;
import ride.happyy.driver.activity.AboutActivity;
import ride.happyy.driver.activity.DocumentsActivity;
import ride.happyy.driver.activity.EditVehicleDetailsActivity;
import ride.happyy.driver.activity.HelpListActivity;
import ride.happyy.driver.activity.ProfileActivity;
import ride.happyy.driver.activity.SettingsActivity;
import ride.happyy.driver.activity.SplashActivity;
import ride.happyy.driver.adapter.DriverListAdapter;
import ride.happyy.driver.adapter.DriverListAdapterTrip;
import ride.happyy.driver.app.App;
import ride.happyy.driver.config.Config;
import ride.happyy.driver.listeners.ProfileListener;
import ride.happyy.driver.model.Driver;
import ride.happyy.driver.model.ProfileBean;
import ride.happyy.driver.net.DataManager;


public class LeaderBordFragmentNew extends BaseFragment {

    private LeaderBordNewFragmentListener mListener;
    private LinearLayout llHelp;
    private LinearLayout llWayBill;
    private LinearLayout llDocuments;
    private LinearLayout llSettings;
    private LinearLayout llAbout;
    private ImageButton ibEditProfile;
    private ImageButton ibEditVehicle;
    private Button btnLogout;
    private TextView txtName;
    private TextView txtVehicle;
    private ImageView ivVehicle;
    private ImageView ivProfilePhoto;
    private ProfileBean profileBean;
    private View.OnClickListener snackBarRefreshOnClickListener;
    private TextView lifetimeEarnTv, lifetimeTripTv, accountReferralBonusTv, accountTotalDueTv, accountRatingTv, accountCommissionRateTv;
    private ListView topTenEarneLv, topTenTripsLv;
    private Button topEarnerBtn, topTripBtn;
    private LinearLayout topEarnLinearLayout, topTripLinearLayout, topEarnerTitleLinearLayout, topTripsTitleLinearLayout;
    public LeaderBordFragmentNew() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initBase(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_leader_bord_fragment_new, null);
        lytContent.addView(rootView);

       /* if (getArguments().containsKey("mapBean"))
            mapBean = (MapBean) getArguments().getSerializable("mapBean");*/

        intiView(rootView);



        return lytBase;
    }



    @Override
    public void onResume() {
        super.onResume();

        if (App.isNetworkAvailable()) {
            // fetchProfile();
        }

    }

    private void intiView(View rootView) {
        topEarnLinearLayout = rootView.findViewById(R.id.earnLinearLayout);
        topTripLinearLayout = rootView.findViewById(R.id.tripLinearLayout);
        topEarnerTitleLinearLayout = rootView.findViewById(R.id.dailyTopEarnersTitleLL);
        topTripsTitleLinearLayout = rootView.findViewById(R.id.dailytripsHeadingLL);
        topEarnerBtn = rootView.findViewById(R.id.topEarnerBtn);
        topTripBtn = rootView.findViewById(R.id.topTripBtn);

        topTenEarneLv = (ListView) rootView.findViewById(R.id.topEarnerList);
        topTenTripsLv = (ListView) rootView.findViewById(R.id.topTripsList);
//top earn
        Driver driver1 = new Driver("1","Rajib Hossain","10000");
        Driver driver2 = new Driver("2","Kamrul Hossain","9000");
        Driver driver3 = new Driver("3","Hossain Imran","8500");
        Driver driver4 = new Driver("4","Rana Rayhan","7000");
        Driver driver5 = new Driver("5","Dipu Number2","5000");
        Driver driver6 = new Driver("6","Tarek Ahossan","5000");
        Driver driver7 = new Driver("7","Mridul Hasan","4000");
        Driver driver8 = new Driver("8","Rasel Rohan","3000");
        Driver driver9 = new Driver("9","Kamal Islam","2000");
        Driver driver10 = new Driver("10","Arman Rahman","1000");

        //top trip

        ArrayList<Driver> driverArrayListTrip = new ArrayList<>();

        Driver drivert1 = new Driver("1","Kajol Ahosan","15");
        Driver drivert12 = new Driver("2","Rana Raj","14");
        Driver drivert13 = new Driver("3","Kamrul Hasan","13");
        Driver drivert14 = new Driver("4","Biplob Hasan","12");
        Driver drivert15 = new Driver("5","Rahman Shaikh","11");
        Driver drivert16 = new Driver("6","Rana Ahmed","10");
        Driver drivert17 = new Driver("7","Raju Shaikh","9");
        Driver drivert18 = new Driver("8","Ariful Islam","9");
        Driver drivert19 = new Driver("9","Lalin Miya","8");
        Driver drivert10 = new Driver("10","Jubayer Hasan","8");

        driverArrayListTrip.add(drivert1);
        driverArrayListTrip.add(drivert12);
        driverArrayListTrip.add(drivert13);
        driverArrayListTrip.add(drivert14);
        driverArrayListTrip.add(drivert15);
        driverArrayListTrip.add(drivert16);
        driverArrayListTrip.add(drivert17);
        driverArrayListTrip.add(drivert18);
        driverArrayListTrip.add(drivert19);
        driverArrayListTrip.add(drivert10);







        ArrayList<Driver> driverArrayList = new ArrayList<>();

        driverArrayList.add(driver1);
        driverArrayList.add(driver2);
        driverArrayList.add(driver3);
        driverArrayList.add(driver4);
        driverArrayList.add(driver5);
        driverArrayList.add(driver6);
        driverArrayList.add(driver7);
        driverArrayList.add(driver8);
        driverArrayList.add(driver9);
        driverArrayList.add(driver10);



        //  ArrayAdapter arrayAdapter = new ArrayAdapter();

        //textViewEarn
//Context mContext = getContext();
        //for top ernerlist
        DriverListAdapter driverListAdapter = new DriverListAdapter(getContext(), R.layout.adapter_top_earning_list_view, driverArrayList);
        topTenEarneLv.setAdapter(driverListAdapter);
//for top trip List
        DriverListAdapterTrip driverListAdapterTrip = new DriverListAdapterTrip(getContext(), R.layout.adapter_top_trip_list_view, driverArrayListTrip);
        topTenTripsLv.setAdapter(driverListAdapterTrip);



        snackBarRefreshOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                mVibrator.vibrate(25);
                //  fetchProfile();
            }
        };

        topEarnerBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                topEarnLinearLayout.setVisibility(View.VISIBLE);
                topEarnerTitleLinearLayout.setVisibility(View.VISIBLE);
                topTripLinearLayout.setVisibility(View.GONE);
                topTripsTitleLinearLayout.setVisibility(View.GONE);
                //#CCCCCC
                // topEarnerBtn.setBackgroundColor(Color.GRAY);
                // topTripBtn.setBackgroundColor(Color.RED);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    topEarnerBtn.setBackground(getResources().getDrawable(R.drawable.bg_black_rectangle_with_semicircle_edge));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    topTripBtn.setBackground(getResources().getDrawable(R.drawable.btn_click_app_rectangle_with_semicircle_edge));
                }

            }
        });
        //topTripBtn

        topTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                topEarnLinearLayout.setVisibility(View.GONE);
                topEarnerTitleLinearLayout.setVisibility(View.GONE);
                topTripLinearLayout.setVisibility(View.VISIBLE);
                topTripsTitleLinearLayout.setVisibility(View.VISIBLE);
                //#CCCCCC
                // topTripBtn.setBackgroundColor(Color.GRAY);
                // topEarnerBtn.setBackgroundColor(Color.RED);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    topEarnerBtn.setBackground(getResources().getDrawable(R.drawable.btn_click_app_rectangle_with_semicircle_edge));
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    topTripBtn.setBackground(getResources().getDrawable(R.drawable.bg_black_rectangle_with_semicircle_edge));
                }


            }
        });








    }

    private void populateAccounts() {

        txtName.setText(profileBean != null ? profileBean.getName() : Config.getInstance().getName());

        Glide.with(getActivity())
                .load(profileBean != null ? profileBean.getProfilePhoto() : Config.getInstance().getProfilePhoto())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_profile_photo_default)
                        .fallback(R.drawable.ic_profile_photo_default)
                        .centerCrop()
                        .circleCrop())
                .into(ivProfilePhoto);

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
        if (getActivity() instanceof LeaderBordNewFragmentListener) {
            mListener = (LeaderBordNewFragmentListener) getActivity();
        } else if (getParentFragment() instanceof LeaderBordNewFragmentListener) {
            mListener = (LeaderBordNewFragmentListener) getParentFragment();
        } else if (context instanceof LeaderBordNewFragmentListener) {
            mListener = (LeaderBordNewFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AccountsFragmentListener");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof LeaderBordNewFragmentListener) {
            mListener = (LeaderBordNewFragmentListener) getActivity();
        } else if (getParentFragment() instanceof LeaderBordNewFragmentListener) {
            mListener = (LeaderBordNewFragmentListener) getParentFragment();
        } else if (context instanceof LeaderBordNewFragmentListener) {
            mListener = (LeaderBordNewFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AccountsFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface LeaderBordNewFragmentListener {

        void onSwipeRefreshChange(boolean isRefreshing);

        void onSwipeEnabled(boolean isEnabled);

    }
}
