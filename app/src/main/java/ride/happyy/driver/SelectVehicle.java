package ride.happyy.driver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ride.happyy.driver.activity.BaseAppCompatNoDrawerActivity;
import ride.happyy.driver.activity.RegistrationActivity;
import ride.happyy.driver.activity.WelcomeActivity;

public class SelectVehicle extends BaseAppCompatNoDrawerActivity {
    Button carbtn, bikebtn,mycngbtn,ambulancebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vehicle);
      //  getSupportActionBar().setTitle(R.string.label_selectvehicle);
        carbtn = findViewById(R.id.carbtn);
        bikebtn = findViewById(R.id.bikebtn);
        mycngbtn = findViewById(R.id.cngbtn);
        ambulancebtn = findViewById(R.id.ambulance);

        carbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(SelectVehicle.this,RegistrationActivity.class);
                loginIntent.putExtra("Car","Car");
                startActivity(loginIntent);
              //  finish();

            }
        });

        bikebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(SelectVehicle.this,RegistrationActivity.class);
                loginIntent.putExtra("Bike","Bike");
                startActivity(loginIntent);
             //   finish();

            }
        });

        mycngbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(SelectVehicle.this,RegistrationActivity.class);
                loginIntent.putExtra("CNG","CNG");
                startActivity(loginIntent);
              //  finish();

            }
        });

        ambulancebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(SelectVehicle.this,RegistrationActivity.class);
                loginIntent.putExtra("Ambulance","Ambulance");
                startActivity(loginIntent);
             //   finish();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
