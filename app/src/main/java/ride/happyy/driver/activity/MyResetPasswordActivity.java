package ride.happyy.driver.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ride.happyy.driver.R;

public class MyResetPasswordActivity extends BaseAppCompatNoDrawerActivity {

    private EditText oldPasswordEditText, newPasswordEditText,reTypePasswordEditText;
    private Button fildCheckButton,resetPasswordButton;
    private String message="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

        getSupportActionBar().setTitle(R.string.label_reset_password);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        initViews();
    }

    private void initViews() {
        oldPasswordEditText = findViewById(R.id.oldpasswordET);
        newPasswordEditText = findViewById(R.id.newpasswordET);
        reTypePasswordEditText  = findViewById(R.id.retypenewpasswordET);
        fildCheckButton = findViewById(R.id.checkResetPasswordInputBtn);
        resetPasswordButton = findViewById(R.id.confirmResetPasswordBtn);


    }

    public void onClickResetPasswordBtn(View view){
        message = "Success!!!";
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        resetPasswordButton.setVisibility(View.GONE);
        fildCheckButton.setVisibility(View.VISIBLE);


    }

    public void onClickCheckBtn(View view){

        if (checkFields()){
            Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
            resetPasswordButton.setVisibility(View.VISIBLE);
            fildCheckButton.setVisibility(View.GONE);
            oldPasswordEditText.setText("");
            newPasswordEditText.setText("");
            reTypePasswordEditText.setText("");

            oldPasswordEditText.setHint("Old Password");
            oldPasswordEditText.setHintTextColor(Color.GRAY);
            oldPasswordEditText.setTextColor(Color.BLACK);
            newPasswordEditText.setHint("New Password");
            newPasswordEditText.setHintTextColor(Color.GRAY);
            newPasswordEditText.setTextColor(Color.BLACK);
            reTypePasswordEditText.setHint("New Password");
            reTypePasswordEditText.setHintTextColor(Color.GRAY);
            reTypePasswordEditText.setTextColor(Color.BLACK);

        }else {
            Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        }


    }
    private boolean checkFields(){
        if (oldPasswordEditText.getText().equals("") || oldPasswordEditText.getText().toString().length()<8){
            message ="The Field Value Wrong!";
            oldPasswordEditText.setHint("Wrong input!!!");
            oldPasswordEditText.setHintTextColor(Color.RED);
            oldPasswordEditText.setTextColor(Color.RED);
            return false;
        }else {
            oldPasswordEditText.setTextColor(Color.GREEN);
        }
        if (newPasswordEditText.getText().equals("") || newPasswordEditText.getText().toString().length()<8){
            message ="The Field Value Wrong!";
            newPasswordEditText.setHint("Wrong input!!!");
            newPasswordEditText.setHintTextColor(Color.RED);
            newPasswordEditText.setTextColor(Color.RED);
            return false;
        }else {
            newPasswordEditText.setTextColor(Color.GREEN);
        }
        if (reTypePasswordEditText.getText().equals("") || !reTypePasswordEditText.getText().toString().equals(newPasswordEditText.getText().toString())){
            message ="The Field Value Wrong!";
            reTypePasswordEditText.setHint("Wrong input!!!");
            reTypePasswordEditText.setHintTextColor(Color.RED);
            reTypePasswordEditText.setTextColor(Color.RED);
            return false;
        }else {
            reTypePasswordEditText.setTextColor(Color.GREEN);
        }
        message ="Checking Succsess!!!";
        return true;
    }


}
