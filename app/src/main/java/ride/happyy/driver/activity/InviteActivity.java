package ride.happyy.driver.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ride.happyy.driver.R;

public class InviteActivity extends BaseAppCompatNoDrawerActivity {
    private Button invite_friend_btn;
    private TextView codeTv;
    private String code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        getSupportActionBar().setTitle(R.string.label_share);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        // init view
        codeTv = findViewById(R.id.code_tv);
      //  String dCode = "HRIN2";

        invite_friend_btn = findViewById(R.id.btn_invite_friends);
        if(getIntent().hasExtra("code")){
            code = getIntent().getStringExtra("code");
            codeTv.setText(code);

        }
        invite_friend_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String sAux = "Get 100% off  on your first trip! To accept, signup and use code"+"'"+code+"'"+"\n\n";
                    sAux = sAux +"Download now:"+ "https://play.google.com/store/apps/details?id=ride.happyy.driver \n";
                    i.putExtra(Intent.EXTRA_TEXT, sAux);
                    startActivity(Intent.createChooser(i, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });



    }



}
