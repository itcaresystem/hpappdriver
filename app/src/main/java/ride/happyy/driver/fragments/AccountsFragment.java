package ride.happyy.driver.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.driver.R;
import ride.happyy.driver.activity.AboutActivity;
import ride.happyy.driver.activity.DocumentsActivity;
import ride.happyy.driver.activity.EditVehicleDetailsActivity;
import ride.happyy.driver.activity.HelpListActivity;
import ride.happyy.driver.activity.InviteActivity;
import ride.happyy.driver.activity.ProfileActivity;
import ride.happyy.driver.activity.SettingsActivity;
import ride.happyy.driver.activity.SplashActivity;
import ride.happyy.driver.app.App;
import ride.happyy.driver.config.Config;
import ride.happyy.driver.listeners.BasicListener;
import ride.happyy.driver.listeners.ProfileListener;
import ride.happyy.driver.model.BasicBean;
import ride.happyy.driver.model.ProfileBean;
import ride.happyy.driver.net.DataManager;


public class AccountsFragment extends BaseFragment {

    private AccountsFragmentListener mListener;
    private LinearLayout llHelp;
    private LinearLayout llWayBill;
    private LinearLayout llDocuments;
    private LinearLayout llSettings;
    private LinearLayout llAbout, invite, logOut;
    private ImageButton ibEditProfile;
    private ImageButton ibEditVehicle;
    private Button btnLogout;
    private TextView txtName,txtPhone,txtAcountInactive,txtAcountActive;
    private TextView txtVehicle,txtVehicleTypeName,txt_accounts_vehicle_number;
    private ImageView ivVehicle;
    private ImageView ivProfilePhoto;
    private ProfileBean profileBean;
    private View.OnClickListener snackBarRefreshOnClickListener;
    private TextView lifetimeEarnTv, lifetimeTripTv, accountReferralBonusTv, accountTotalDueTv, accountRatingTv, accountCommissionRateTv;

    public AccountsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initBase(inflater, container, savedInstanceState);

        View rootView = inflater.inflate(R.layout.fragment_accounts, null);
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
            fetchProfile();
        }

    }

    private void intiView(View rootView) {

        snackBarRefreshOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                mVibrator.vibrate(25);
                fetchProfile();
            }
        };

        lifetimeEarnTv = rootView.findViewById(R.id.accountLifeTimeEarningTv);
        lifetimeTripTv = rootView.findViewById(R.id.accountLifetimeTripTv);
        accountReferralBonusTv = rootView.findViewById(R.id.accountReferralBonusTv);
        accountTotalDueTv = rootView.findViewById(R.id.accountTotalDueTv);
        accountRatingTv = rootView.findViewById(R.id.accountRatingTv);
        accountCommissionRateTv = rootView.findViewById(R.id.accounCommRateTv);


        llHelp = (LinearLayout) rootView.findViewById(R.id.ll_accounts_help);
        llWayBill = (LinearLayout) rootView.findViewById(R.id.ll_accounts_way_bill);
        llDocuments = (LinearLayout) rootView.findViewById(R.id.ll_accounts_documents);
        llSettings = (LinearLayout) rootView.findViewById(R.id.ll_accounts_settings);
        llAbout = (LinearLayout) rootView.findViewById(R.id.ll_accounts_about);
        invite = rootView.findViewById(R.id.ll_share_invite);
        logOut    = rootView.findViewById(R.id.ll_logout);

        txtName = (TextView) rootView.findViewById(R.id.txt_accounts_driver_name);
        txtPhone = rootView.findViewById(R.id.txt_accounts_driver_mobile);
        txtAcountInactive = rootView.findViewById(R.id.activeInactiveTv);
        txtAcountActive = rootView.findViewById(R.id.activeTv);
        txtVehicle = (TextView) rootView.findViewById(R.id.txt_accounts_vehicle_name);
        txt_accounts_vehicle_number = (TextView) rootView.findViewById(R.id.txt_accounts_vehicle_number);

        txtVehicleTypeName = rootView.findViewById(R.id.vehicle_brand_name);
        ivVehicle = (ImageView) rootView.findViewById(R.id.iv_accounts_driver_car_photo);
        ivProfilePhoto = (ImageView) rootView.findViewById(R.id.iv_accounts_profile_photo);

        ibEditProfile = (ImageButton) rootView.findViewById(R.id.ib_accounts_edit_account);
        ibEditVehicle = (ImageButton) rootView.findViewById(R.id.ib_accounts_edit_car_details);

        btnLogout = (Button) rootView.findViewById(R.id.btn_accounts_logout);


        btnLogout.setTypeface(typeface);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);
                if (getActivity() != null) {
                    App.logout();
                    startActivity(new Intent(getActivity(), SplashActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                    getActivity().finish();
                }

            }
        });

        ivProfilePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);

                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });

        ibEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);

                startActivity(new Intent(getActivity(), ProfileActivity.class));
            }
        });

        ibEditVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);

                startActivity(new Intent(getActivity(), EditVehicleDetailsActivity.class));
            }
        });

        llHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);

                startActivity(new Intent(getActivity(), HelpListActivity.class));
            }
        });
        llWayBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);

//                startActivity(new Intent(getActivity(), HelpListActivity.class));
            }
        });
        llDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);

                startActivity(new Intent(getActivity(), DocumentsActivity.class));
            }
        });
        llSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);

                startActivity(new Intent(getActivity(), SettingsActivity.class));
            }
        });
        llAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                //mVibrator.vibrate(25);

                startActivity(new Intent(getActivity(), AboutActivity.class));
            }
        });

        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);

                Intent mIntent = new Intent(getActivity(), InviteActivity.class);
                mIntent.putExtra("code",profileBean.getDriver_ref_code());

                startActivity(mIntent);

            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                Toast.makeText(getContext(),"Please Wait!!!",Toast.LENGTH_LONG).show();

                if (getActivity() != null) {

                    JSONObject postData = new JSONObject();
                    try {
                        postData.put("phone",Config.getInstance().getPhone());
                        postData.put("is_online",false);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if(App.isNetworkAvailable()) {
                        DataManager.performDriverStatusChange(postData, new BasicListener() {
                            @Override
                            public void onLoadCompleted(BasicBean basicBean) {
                                Toast.makeText(getContext(),"Logout!!!",Toast.LENGTH_LONG).show();
                                App.logout();
                                startActivity(new Intent(getActivity(), SplashActivity.class)
                                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
                                getActivity().finish();
                            }

                            @Override
                            public void onLoadFailed(String error) {
                                Toast.makeText(getContext(),"Please Try Again!!!",Toast.LENGTH_LONG).show();
                            }
                        });
                    }else {
                        Toast.makeText(getContext(),"No network available!!!",Toast.LENGTH_LONG).show();
                    }

                }


            }
        });


    }

    private void populateAccounts() {

        txtName.setText(profileBean != null ? profileBean.getName() : Config.getInstance().getName());
        txtPhone.setText(profileBean != null ? profileBean.getPhone() : Config.getInstance().getPhone());
        //Accout status define
        if(profileBean.is_active().equals("1")) {
            txtAcountActive.setVisibility(View.VISIBLE);
            txtAcountInactive.setVisibility(View.GONE);
        }else {
            txtAcountActive.setVisibility(View.GONE);
            txtAcountInactive.setVisibility(View.VISIBLE);
        }
        //init vehicle type and vehicle no
        if(profileBean !=null && ! profileBean.getVehicle_no().equals("")) {
            txt_accounts_vehicle_number.setText(profileBean.getVehicle_no().toString());
        }
        if(profileBean.getVehicle_type().equals("1")){
            txtVehicle.setText("BIKE");
            txtVehicleTypeName.setText("BIKE");
            Glide.with(getActivity())
                    .load(profileBean != null ? profileBean.getCoverPhoto() : Config.getInstance().getCoverPhoto())
                    .apply(new RequestOptions()
                            .error(R.drawable.bikeperfectlogoselected)
                            .fallback(R.drawable.bikeperfectlogoselected)
                            .centerCrop()
                            .circleCrop())
                    .into(ivVehicle);
        }
        if(profileBean.getVehicle_type().equals("2")){
            txtVehicle.setText("CNG");
            txtVehicleTypeName.setText("CNG");
            Glide.with(getActivity())
                    .load(profileBean != null ? profileBean.getCoverPhoto() : Config.getInstance().getCoverPhoto())
                    .apply(new RequestOptions()
                            .error(R.drawable.cng)
                            .fallback(R.drawable.cng)
                            .centerCrop()
                            .circleCrop())
                    .into(ivVehicle);
        }
        if(profileBean.getVehicle_type().equals("3")){
            txtVehicle.setText("CAR");
            txtVehicleTypeName.setText("CAR");
            Glide.with(getActivity())
                    .load(profileBean != null ? profileBean.getCoverPhoto() : Config.getInstance().getCoverPhoto())
                    .apply(new RequestOptions()
                            .error(R.drawable.ic_vehicle_dummy)
                            .fallback(R.drawable.ic_vehicle_dummy)
                            .centerCrop()
                            .circleCrop())
                    .into(ivVehicle);
        }
        if(profileBean.getVehicle_type().equals("4")){
            txtVehicle.setText("Ambulance");
            txtVehicleTypeName.setText("Ambulance");
            Glide.with(getActivity())
                    .load(profileBean != null ? profileBean.getCoverPhoto() : Config.getInstance().getCoverPhoto())
                    .apply(new RequestOptions()
                            .error(R.drawable.ambulancetestimagepng)
                            .fallback(R.drawable.ambulancetestimagepng)
                            .centerCrop()
                            .circleCrop())
                    .into(ivVehicle);
        }



        Glide.with(getActivity())
                .load(profileBean != null ? profileBean.getProfilePhoto() : Config.getInstance().getProfilePhoto())
                .apply(new RequestOptions()
                        .error(R.drawable.ic_profile_photo_default)
                        .fallback(R.drawable.ic_profile_photo_default)
                        .centerCrop()
                        .circleCrop())
                .into(ivProfilePhoto);
        Integer totalEarnInt =profileBean.getTotal_earn();
        String lifeTimeEarn =totalEarnInt.toString();
        Integer totalTripsInt =profileBean.getTotal_trips();
        String lifeTimeTrips =totalTripsInt.toString();
        lifetimeTripTv.setText(lifeTimeTrips);
        lifetimeEarnTv.setText("৳ "+lifeTimeEarn);
        accountTotalDueTv.setText("৳ "+String.valueOf(profileBean.getTotal_due()));
        accountReferralBonusTv.setText("৳ "+String.valueOf(profileBean.getRef_bonus()));
        accountRatingTv.setText(String.valueOf(profileBean.getDriver_rating()));
        accountCommissionRateTv.setText(String.valueOf(profileBean.getCommission_rate()));


    }


    private void fetchProfile() {

        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("phone", Config.getInstance().getPhone());
        JSONObject postData = getJsonObject();


/*        if (isLoadMore) {
            urlParams.put("page", String.valueOf(currentPage + 1));
        }*/

        DataManager.fetchProfile(postData, new ProfileListener() {
            @Override
            public void onLoadCompleted(ProfileBean profileBeanWS) {

                profileBean = profileBeanWS;
                if (getActivity() != null)
                    populateAccounts();

            }

            @Override
            public void onLoadFailed(String error) {
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
                if (getActivity() != null)
                    mListener.onSwipeRefreshChange(false);
            }
        });

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
        if (getActivity() instanceof AccountsFragmentListener) {
            mListener = (AccountsFragmentListener) getActivity();
        } else if (getParentFragment() instanceof AccountsFragmentListener) {
            mListener = (AccountsFragmentListener) getParentFragment();
        } else if (context instanceof AccountsFragmentListener) {
            mListener = (AccountsFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AccountsFragmentListener");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() instanceof AccountsFragmentListener) {
            mListener = (AccountsFragmentListener) getActivity();
        } else if (getParentFragment() instanceof AccountsFragmentListener) {
            mListener = (AccountsFragmentListener) getParentFragment();
        } else if (context instanceof AccountsFragmentListener) {
            mListener = (AccountsFragmentListener) context;
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

    public JSONObject getJsonObject() {
        JSONObject jsonData= new JSONObject();
        try {
            jsonData.put("phone",Config.getInstance().getPhone());
            jsonData.put("test","test");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonData;
    }

    public interface AccountsFragmentListener {

        void onSwipeRefreshChange(boolean isRefreshing);

        void onSwipeEnabled(boolean isEnabled);

    }
}
