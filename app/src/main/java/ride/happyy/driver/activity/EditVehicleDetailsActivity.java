package ride.happyy.driver.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ride.happyy.driver.R;

public class EditVehicleDetailsActivity extends BaseAppCompatNoDrawerActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vehicle_details);

        getSupportActionBar().setTitle(R.string.label_vehicle_info);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        initView();
    }


    private void initView(){

    }
}
