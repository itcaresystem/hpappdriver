package ride.happyy.driver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ride.happyy.driver.activity.BaseAppCompatNoDrawerActivity;
import ride.happyy.driver.activity.RegistrationActivity;
import ride.happyy.driver.activity.WelcomeActivity;

public class SelectVehicle extends BaseAppCompatNoDrawerActivity {
    Button carbtn, bikebtn,mycngbtn,ambulancebtn;
    EditText ref_code_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vehicle);
      //  getSupportActionBar().setTitle(R.string.label_selectvehicle);
        ref_code_et = findViewById(R.id.ref_code);
        carbtn = findViewById(R.id.carbtn);
        bikebtn = findViewById(R.id.bikebtn);
        mycngbtn = findViewById(R.id.cngbtn);
        ambulancebtn = findViewById(R.id.ambulance);

        carbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(SelectVehicle.this,RegistrationActivity.class);
                loginIntent.putExtra("Car","Car");
                loginIntent.putExtra("ref_code",ref_code_et.getText().toString());
                startActivity(loginIntent);
              //  finish();

            }
        });

        bikebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(SelectVehicle.this,RegistrationActivity.class);
                loginIntent.putExtra("Bike","Bike");
                loginIntent.putExtra("ref_code",ref_code_et.getText().toString());
                startActivity(loginIntent);
             //   finish();

            }
        });

        mycngbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(SelectVehicle.this,RegistrationActivity.class);
                loginIntent.putExtra("CNG","CNG");
                loginIntent.putExtra("ref_code",ref_code_et.getText().toString());
                startActivity(loginIntent);
              //  finish();

            }
        });

        ambulancebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(SelectVehicle.this,RegistrationActivity.class);
                loginIntent.putExtra("Ambulance","Ambulance");
                loginIntent.putExtra("ref_code",ref_code_et.getText().toString());
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
