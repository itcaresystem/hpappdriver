package ride.happyy.driver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ride.happyy.driver.activity.RegistrationActivity;
import ride.happyy.driver.activity.WelcomeActivity;

public class SelectVehicle extends AppCompatActivity {
    Button carbtn, bikebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vehicle);
        carbtn = findViewById(R.id.carbtn);
        bikebtn = findViewById(R.id.bikebtn);

        carbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(SelectVehicle.this,RegistrationActivity.class);
                loginIntent.putExtra("Car","Car");
                startActivity(loginIntent);
                finish();

            }
        });

        bikebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(SelectVehicle.this,RegistrationActivity.class);
                loginIntent.putExtra("Bike","Bike");
                startActivity(loginIntent);
                finish();

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
