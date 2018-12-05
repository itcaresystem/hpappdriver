package ride.happyy.driver.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import ride.happyy.driver.R;
import ride.happyy.driver.app.App;
import ride.happyy.driver.listeners.BasicListener;
import ride.happyy.driver.model.BasicBean;
import ride.happyy.driver.net.DataManager;
import ride.happyy.driver.util.AppConstants;
import ride.happyy.driver.widgets.OTPEditText;

public class ForgotPasswordActivity extends BaseAppCompatNoDrawerActivity {
    private static final String TAG = "ForgotpasswordAC";

    private Button btnReset;
    private EditText etxt_registration_phone, etxtEmail,etxPassword;
    private String vPhone;
    private OTPEditText etxtOne;
    private OTPEditText etxtTwo;
    private OTPEditText etxtThree;
    private OTPEditText etxtFour;
    private OTPEditText etxtFive;
    private OTPEditText etxtSix;
    private String mVerificationId;
    private String otpCode;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private FirebaseAuth mAuth;
    private LinearLayout phoneverificationLinearLayout,newpasswordLinearlayout;
    private CountDownTimer cdt;
    private FloatingActionButton RegistrationFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        getSupportActionBar().hide();
        swipeView.setPadding(0, 0, 0, 0);

        initViews();
        timeCountDown();

    }

    public void timeCountDown(){

        if(cdt!=null){
            cdt.cancel();
            cdt=null;

        }


        cdt = new CountDownTimer(65* 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                RegistrationFAB.setEnabled(false);
                Log.i(TAG, millisUntilFinished +" millis left");
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "Timer finished");
                if(App.isNetworkAvailable()) {
                    RegistrationFAB.setEnabled(true);
                    RegistrationFAB.setBackgroundColor(Color.GRAY);
                }
            }
        };

        cdt.start();
    }


    private void initViews() {
        etxt_registration_phone =findViewById(R.id.etxt_registration_phone );
        if(getIntent().hasExtra("phone")){
            vPhone = getIntent().getStringExtra("phone");
            etxt_registration_phone.setText(vPhone);
          //  Toast.makeText(getApplicationContext(),vPhone,Toast.LENGTH_LONG).show();


        }
        //liniar layout
        phoneverificationLinearLayout = findViewById(R.id.phoneverificationLinearLayout);
        newpasswordLinearlayout = findViewById(R.id.newpasswordLinearlayout);
        RegistrationFAB=findViewById(R.id.RegistrationFAB);





        mAuth = FirebaseAuth.getInstance();
        btnReset = (Button) findViewById(R.id.btn_forgot_password_reset);
        etxtEmail = (EditText) findViewById(R.id.etxt_forgot_password_email);
        etxPassword = findViewById(R.id.etxt_forgot_password);

        etxtEmail.setTypeface(typeface);
        btnReset.setTypeface(typeface);
        etxtOne = (OTPEditText) findViewById(R.id.etxt_registration_mobile_one);
        etxtTwo = (OTPEditText) findViewById(R.id.etxt_registration_mobile_two);
        etxtThree = (OTPEditText) findViewById(R.id.etxt_registration_mobile_three);
        etxtFour = (OTPEditText) findViewById(R.id.etxt_registration_mobile_four);
        etxtFive = (OTPEditText) findViewById(R.id.etxt_registration_mobile_five);
        etxtSix = (OTPEditText) findViewById(R.id.etxt_registration_mobile_six);


        etxtOne.setTypeface(typeface);
        etxtTwo.setTypeface(typeface);
        etxtThree.setTypeface(typeface);
        etxtFour.setTypeface(typeface);
        etxtFive.setTypeface(typeface);
        etxtSix.setTypeface(typeface);

        etxtOne.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength1 = etxtOne.getText().length();

                if (textlength1 >= 1) {
                    etxtOne.setBackgroundResource(R.drawable.circle_white_with_app_edge);
                    etxtTwo.requestFocus();
                } else {
                    etxtOne.setBackgroundResource(R.drawable.circle_white_with_gray_edge);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }
        });

        etxtTwo.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength2 = etxtTwo.getText().length();

                if (textlength2 >= 1) {
                    etxtTwo.setBackgroundResource(R.drawable.circle_white_with_app_edge);
                    etxtThree.requestFocus();
                } else {
                    etxtTwo.setBackgroundResource(R.drawable.circle_white_with_gray_edge);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
        });
        etxtThree.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength3 = etxtThree.getText().length();

                if (textlength3 >= 1) {
                    etxtThree.setBackgroundResource(R.drawable.circle_white_with_app_edge);
                    etxtFour.requestFocus();
                } else {
                    etxtThree.setBackgroundResource(R.drawable.circle_white_with_gray_edge);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }
        });
        etxtFour.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength4 = etxtFour.getText().toString().length();

                if (textlength4 == 1) {
                    etxtFour.setBackgroundResource(R.drawable.circle_white_with_app_edge);
                    etxtFive.requestFocus();
                } else {
                    etxtFour.setBackgroundResource(R.drawable.circle_white_with_gray_edge);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });
        etxtFive.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength4 = etxtFive.getText().toString().length();

                if (textlength4 == 1) {
                    etxtFive.setBackgroundResource(R.drawable.circle_white_with_app_edge);
                    etxtSix.requestFocus();
                } else {
                    etxtFive.setBackgroundResource(R.drawable.circle_white_with_gray_edge);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });
        etxtSix.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Integer textlength4 = etxtSix.getText().toString().length();

                if (textlength4 == 1) {
                    etxtSix.setBackgroundResource(R.drawable.circle_white_with_app_edge);
                } else {
                    etxtSix.setBackgroundResource(R.drawable.circle_white_with_gray_edge);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
        });

        etxtSix.setOnDeleteKeyClick(new OTPEditText.OnDeleteKeyClick() {
            @Override
            public void onDeleteKeyClick(boolean isPressed) {

                int i = etxtSix.getText().toString().length();
                if (i == 0) {
                    etxtFive.setText("");
                    etxtFive.requestFocus();
                }
            }
        });
        etxtFive.setOnDeleteKeyClick(new OTPEditText.OnDeleteKeyClick() {
            @Override
            public void onDeleteKeyClick(boolean isPressed) {

                int i = etxtFive.getText().toString().length();
                if (i == 0) {
                    etxtFour.setText("");
                    etxtFour.requestFocus();
                }
            }
        });
        etxtFour.setOnDeleteKeyClick(new OTPEditText.OnDeleteKeyClick() {
            @Override
            public void onDeleteKeyClick(boolean isPressed) {

                int i = etxtFour.getText().toString().length();
                if (i == 0) {
                    etxtThree.setText("");
                    etxtThree.requestFocus();
                }
            }
        });

        etxtThree.setOnDeleteKeyClick(new OTPEditText.OnDeleteKeyClick() {
            @Override
            public void onDeleteKeyClick(boolean isPressed) {

                int i = etxtThree.getText().toString().length();
                if (i == 0) {
                    etxtTwo.setText("");
                    etxtTwo.requestFocus();
                }
            }
        });

        etxtTwo.setOnDeleteKeyClick(new OTPEditText.OnDeleteKeyClick() {
            @Override
            public void onDeleteKeyClick(boolean isPressed) {

                int i = etxtTwo.getText().toString().length();
                if (i == 0) {
                    etxtOne.setText("");
                    etxtOne.requestFocus();
                }
            }
        });

        etxtSix.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    if (etxtOne.getText().toString().length() == 0) {
                        etxtOne.requestFocus();
                    } else if (etxtTwo.getText().toString().length() == 0) {
                        etxtTwo.requestFocus();
                    } else if (etxtThree.getText().toString().length() == 0) {
                        etxtThree.requestFocus();
                    } else if (etxtFour.getText().toString().length() == 0) {
                        etxtFour.requestFocus();
                    } else if (etxtFour.getText().toString().length() == 0) {
                        etxtFive.requestFocus();
                    }
                }
            }
        });

        etxtFive.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    if (etxtOne.getText().toString().length() == 0) {
                        etxtOne.requestFocus();
                    } else if (etxtTwo.getText().toString().length() == 0) {
                        etxtTwo.requestFocus();
                    } else if (etxtThree.getText().toString().length() == 0) {
                        etxtThree.requestFocus();
                    } else if (etxtFour.getText().toString().length() == 0) {
                        etxtFour.requestFocus();
                    }
                }
            }
        });

        etxtFour.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    if (etxtOne.getText().toString().length() == 0) {
                        etxtOne.requestFocus();
                    } else if (etxtTwo.getText().toString().length() == 0) {
                        etxtTwo.requestFocus();
                    } else if (etxtThree.getText().toString().length() == 0) {
                        etxtThree.requestFocus();
                    }
                }
            }
        });

        etxtThree.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    if (etxtOne.getText().toString().length() == 0) {
                        etxtOne.requestFocus();
                    } else if (etxtTwo.getText().toString().length() == 0) {
                        etxtTwo.requestFocus();
                    }

                }
            }
        });

        etxtTwo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {

                    if (etxtOne.getText().toString().length() == 0) {
                        etxtOne.requestFocus();
                    }
                }
            }
        });


        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                Log.d(TAG, "onVerificationCompleted:" + phoneAuthCredential);

                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Log.i(TAG, "onVerificationFailed: " + e);
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Log.i(TAG, "onVerificationFailed: " + e);
                }

                /*Snackbar.make(coordinatorLayout, R.string.message_phone_verification_failed, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
*/
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                Snackbar.make(coordinatorLayout, getString(R.string.message_verification_code_sent_to) + " " + vPhone,
                        Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();

                swipeView.setRefreshing(false);

            }
        };

        if(!vPhone.equals("")) {
            initiatePhoneVerification();
        }

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        swipeView.setRefreshing(true);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            swipeView.setRefreshing(false);
                            // Sign in success, update UI with the signed-in user's information
                            Log.i(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();


                            Log.i(TAG, "onComplete: " + new Gson().toJson(task));

                        //    viewFlipper.setInAnimation(slideLeftIn);
                        //    viewFlipper.setOutAnimation(slideLeftOut);
                       //     viewFlipper.showNext();
                          //  getSupportActionBar().show();
                            phoneverificationLinearLayout.setVisibility(View.GONE);
                            newpasswordLinearlayout.setVisibility(View.VISIBLE);





                        } else {
                            phoneverificationLinearLayout.setVisibility(View.VISIBLE);
                            newpasswordLinearlayout.setVisibility(View.GONE);

                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid

                                Snackbar.make(coordinatorLayout, R.string.message_invalid_verification_code, Snackbar.LENGTH_LONG)
                                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
                            }
                        }
                    }
                });
    }

    public void onForgotPasswordResetClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//        mVibrator.vibrate(25);

        if (collectForgotPasswordData()) {

            if (App.isNetworkAvailable()) {
                performForgotPassword();
            }else{
                Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }
        }

    }

    private void performForgotPassword() {

        JSONObject postData = getForgotPasswordJSObj();

        DataManager.performForgotPassword(postData, new BasicListener() {
            @Override
            public void onLoadCompleted(BasicBean basicBean) {
                Toast.makeText(getBaseContext(),"Passward Reset Successfully!!",Toast.LENGTH_SHORT).show();
                Intent loginActivityIntent = new Intent(getBaseContext(),LoginActivity.class);
                startActivity(loginActivityIntent);
                finish();
            }

            @Override
            public void onLoadFailed(String error) {
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_LONG)
                        .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            }
        });


    }

    private JSONObject getForgotPasswordJSObj() {

        JSONObject postData = new JSONObject();

        try {
           // String  validphone = getIntent().getStringExtra("phone");
            postData.put("forgetpass","forgetpass");
            postData.put("phone",vPhone);
            postData.put("newpassword", etxPassword.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return postData;
    }

    private boolean collectForgotPasswordData() {


        if (etxPassword.getText().toString().equals("") || etxPassword.getText().length()<4) {
            Snackbar.make(coordinatorLayout, "Password Field Requred!! and min 4 char!!", Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }
/*
        if (etxtEmail.getText().toString().equals("")) {
            Snackbar.make(coordinatorLayout, R.string.message_enter_email_to_recover_password, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etxtEmail.getText().toString()).matches()) {
            Snackbar.make(coordinatorLayout, R.string.message_enter_a_valid_email_address, Snackbar.LENGTH_LONG)
                    .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
            return false;
        }
        */

        return true;
    }

    public void onResendFabButton(View view) {
       Intent intentResend=new Intent(this,ForgotPasswordActivity.class);
       intentResend.putExtra("phone",vPhone);
       startActivity(intentResend);

    }
    public void onPasswordResetnMobileNumberSubmitClick(View view) {
        otpCode = "" + etxtOne.getText().toString() + etxtTwo.getText().toString()
                + etxtThree.getText().toString() + etxtFour.getText().toString()
                + etxtFive.getText().toString() + etxtSix.getText().toString();

        if (!otpCode.equalsIgnoreCase("")) {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, otpCode);
            signInWithPhoneAuthCredential(credential);
        } else {
            Snackbar.make(coordinatorLayout, getString(R.string.message_invalid_verification_code), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.btn_dismiss), snackBarDismissOnClickListener).show();
        }

    }

    private void initiatePhoneVerification() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                vPhone,        // Phone number to verify
                1,                 // Timeout duration
                TimeUnit.MINUTES,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);


      //  Snackbar.make(coordinatorLayout, R.string.message_sending_verification_code, Snackbar.LENGTH_LONG)
              //  .setAction(R.string.btn_dismiss, snackBarDismissOnClickListener).show();
        swipeView.setRefreshing(true);

    }


}
