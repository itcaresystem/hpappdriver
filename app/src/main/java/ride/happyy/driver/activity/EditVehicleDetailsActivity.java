package ride.happyy.driver.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.driver.R;
import ride.happyy.driver.app.App;
import ride.happyy.driver.config.Config;
import ride.happyy.driver.listeners.ProfileListener;
import ride.happyy.driver.model.ProfileBean;
import ride.happyy.driver.net.DataManager;
import ride.happyy.driver.util.AppConstants;

public class EditVehicleDetailsActivity extends BaseAppCompatNoDrawerActivity {
    private LinearLayout editForCarInfoLL,displayCarInfoLL;
    private TextView carRegNumberTV,drivinLicenseNoTV,carFitnessNumberTV,carBrandNameTV;
    private EditText carRegNumberET,drivinLicenseNoET,carFitnessNumberET,carBrandNameET;
    private Spinner brandNameSP;
    private Button saveBtn;
    private ImageButton editBtn;
    private boolean isEditing = false;
    private View.OnClickListener snackBarRefreshOnClickListener;
    private ProfileBean profileBean;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vehicle_details);
        getSupportActionBar().setTitle(R.string.label_vehicle_info);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        initView();
        setEditable(isEditing);
    }
    @Override
    protected void onResume() {
        super.onResume();
/*
        if (menuProfileSave != null && menuProfileEdit != null) {
            if (isEditing) {
                menuProfileSave.setVisible(true);
                menuProfileEdit.setVisible(false);

            } else {
                menuProfileSave.setVisible(false);
                menuProfileEdit.setVisible(true);
            }
        }
*/
        setEditable(isEditing);


        if (!isEditing) {
            if (profileBean == null) {
                setProgressScreenVisibility(true, true);
                getData(false);
            } else {
                getData(true);
            }
        }

    }

    private void initView(){
        snackBarRefreshOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                mVibrator.vibrate(25);
                setProgressScreenVisibility(true, true);
                getData(false);
            }
        };
        saveBtn=findViewById(R.id.btn_car_info_save);
        editBtn=findViewById(R.id.editCarInfoBtn);
        editForCarInfoLL = findViewById(R.id.editForCarInfoLL);
        displayCarInfoLL=findViewById(R.id.displayCarInfoLL);
        brandNameSP=findViewById(R.id.brand_name_SP);
        carBrandNameET=findViewById(R.id.brandNameET);
        carRegNumberET=findViewById(R.id.vehicle_registration_number);
        drivinLicenseNoET=findViewById(R.id.driving_license_ET);
        carFitnessNumberET=findViewById(R.id.fitness_certificate_ET);

    }

    private void setEditable(boolean isEditing) {
        carBrandNameET.setEnabled(isEditing);
        carRegNumberET.setEnabled(isEditing);
        drivinLicenseNoET.setEnabled(isEditing);
        drivinLicenseNoET.setEnabled(isEditing);
        carFitnessNumberET.setEnabled(isEditing);

    }

    public void onClickEditCarInfoBtn(View view){
        setEditable(true);
        saveBtn.setVisibility(View.VISIBLE);
        editBtn.setVisibility(View.GONE);

    }

    public void onClickCarInfoSaveBtn(View view){
        saveBtn.setVisibility(View.GONE);
        editBtn.setVisibility(View.GONE);
    }

    private void getData(boolean isSwipeRefreshing) {
        swipeView.setRefreshing(isSwipeRefreshing);
        if (App.isNetworkAvailable()) {
            fetchProfile();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
        }
    }

    private void fetchProfile() {

        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("phone", Config.getInstance().getPhone());
        JSONObject postData =getJsonData();

/*        if (isLoadMore) {
            urlParams.put("page", String.valueOf(currentPage + 1));
        }*/

        DataManager.fetchProfile(postData, new ProfileListener() {
            @Override
            public void onLoadCompleted(ProfileBean profileBeanWS) {

                profileBean = profileBeanWS;
                populateProfile();

            }

            @Override
            public void onLoadFailed(String error) {
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
                swipeView.setRefreshing(false);
                setProgressScreenVisibility(true, false);
                if (App.getInstance().isDemo()) {
                    profileBean = new ProfileBean();
                    populateProfile();
                }
            }
        });

    }

    public JSONObject getJsonData() {
        JSONObject jsonData= new JSONObject();
        try {
            jsonData.put("phone",Config.getInstance().getPhone());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonData;
    }
    private void populateProfile() {
        carBrandNameET.setText(profileBean.getCarBrand());
        carRegNumberET.setText(profileBean.getVehicle_no());
       // drivinLicenseNoET.setEnabled(isEditing);
       // drivinLicenseNoET.setEnabled(isEditing);
        carFitnessNumberET.setText(profileBean.getCarFitnessCertificateNo());

        swipeView.setRefreshing(false);
        setProgressScreenVisibility(false, false);
    }
}
