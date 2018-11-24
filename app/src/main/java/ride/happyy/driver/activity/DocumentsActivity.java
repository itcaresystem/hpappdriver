package ride.happyy.driver.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.HashMap;

import ride.happyy.driver.R;
import ride.happyy.driver.app.App;
import ride.happyy.driver.config.Config;
import ride.happyy.driver.listeners.DocumentStatusListener;
import ride.happyy.driver.model.DocumentBean;
import ride.happyy.driver.model.DocumentStatusBean;
import ride.happyy.driver.net.DataManager;
import ride.happyy.driver.util.AppConstants;

public class DocumentsActivity extends BaseAppCompatNoDrawerActivity {

    private static final int REQUEST_DOCUMENT_UPLOAD = 1;
    private static final int TYPE_ERROR = 0;
    private static final int TYPE_ACCEPTED = 1;
    private static final String TAG = "DriverA";

    private int documentColor[] = {R.color.text_red_1, R.color.yellow_2, R.color.text_green_1, R.color.text_red_1};

    private View.OnClickListener snackBarRefreshOnClickListener;
    private DocumentStatusBean documentStatusBean;
    private ImageView ivDriverLicenceError;
    private ImageView ivDriverLicenceAccepted;
    private ImageView ivPoliceClearanceCertificateError;
    private ImageView ivPoliceClearanceCertificateAccepted;
    private ImageView ivFitnessCertificateError;
    private ImageView ivFitnessCertificateAccepted;
    private ImageView ivVehicleRegistrationError;
    private ImageView ivVehicleRegistrationAccepted;
    private ImageView ivVehiclePermitError;
    private ImageView ivVehiclePermitAccepted;
    private ImageView ivCommercialInsuranceError;
    private ImageView ivCommercialInsuranceAccepted;
    private ImageView ivTaxReceiptError;
    private ImageView ivTaxReceiptAccepted;
    private ImageView ivBankPassbookError;
    private ImageView ivBankPassbookAccepted;
    private ImageView ivDriverLicenceWithBadgeError;
    private ImageView ivDriverLicenceWithBadgeAccepted;
    private ImageView ivBackgroundCheckConsentFormError;
    private ImageView ivBackgroundCheckConsentFormAccepted;
    private ImageView ivPanCardError;
    private ImageView ivPanCardAccepted;
    private ImageView ivNoObjectionCeritificateError;
    private ImageView ivNoObjectionCeritificateAccepted;
    private TextView txtDriverLicenceStatus;
    private TextView txtPoliceClearanceCertificateStatus;
    private TextView txtFitnessCertificateStatus;
    private TextView txtVehicleRegistrationStatus;
    private TextView txtVehiclePermitStatus;
    private TextView txtCommercialInsuranceStatus;
    private TextView txtTaxReceiptStatus;
    private TextView txtBankPassbookStatus;
    private TextView txtDriverLicenceWithBadgeStatus;
    private TextView txtBackgroundCheckConsentFormStatus;
    private TextView txtPanCardStatus;
    private TextView txtNoObjectionCeritificateStatus;

    private ImageView nidordrivinglicense_photo,driving_license_photo,vehicle_certificate_photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_documents);


        initViews();

        getSupportActionBar().setTitle(R.string.label_documents);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_DOCUMENT_UPLOAD && resultCode == RESULT_OK) {

            int type = data.getExtras().getInt("type");
            String documentUrl = data.getExtras().getString("imagepath");


            updateDocuments(type, AppConstants.DOCUMENT_STATUS_PENDING_APPROVAL,documentUrl);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (documentStatusBean == null) {
            setProgressScreenVisibility(true, true);
            getData(false);
        } else {
            getData(true);
        }
    }


    private void getData(boolean isSwipeRefreshing) {
        swipeView.setRefreshing(isSwipeRefreshing);
        if (App.isNetworkAvailable()) {
            fetchDocumentStatus();
        } else {
            Snackbar.make(coordinatorLayout, AppConstants.NO_NETWORK_AVAILABLE, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();
        }
    }

    private void initViews() {

        snackBarRefreshOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
//                mVibrator.vibrate(25);
                setProgressScreenVisibility(true, true);
                getData(false);
            }
        };



        nidordrivinglicense_photo = findViewById(R.id.nidordrivinglicense_photo);
        vehicle_certificate_photo = findViewById(R.id.vehicle_certificate_photo);
        driving_license_photo =findViewById(R.id.driving_license_photo);


        ivDriverLicenceError = (ImageView) findViewById(R.id.iv_documents_driver_licence_error);
        ivDriverLicenceAccepted = (ImageView) findViewById(R.id.iv_documents_driver_licence_accepted);

        ivPoliceClearanceCertificateError = (ImageView) findViewById(R.id.iv_documents_police_clearance_certificate_error);
        ivPoliceClearanceCertificateAccepted = (ImageView) findViewById(R.id.iv_documents_police_clearance_certificate_accepted);

        ivFitnessCertificateError = (ImageView) findViewById(R.id.iv_documents_fitness_certificate_error);
        ivFitnessCertificateAccepted = (ImageView) findViewById(R.id.iv_documents_fitness_certificate_accepted);

        ivVehicleRegistrationError = (ImageView) findViewById(R.id.iv_documents_vehicle_registration_error);
        ivVehicleRegistrationAccepted = (ImageView) findViewById(R.id.iv_documents_vehicle_registration_accepted);

        ivVehiclePermitError = (ImageView) findViewById(R.id.iv_documents_vehicle_permit_error);
        ivVehiclePermitAccepted = (ImageView) findViewById(R.id.iv_documents_vehicle_permit_accepted);

        ivCommercialInsuranceError = (ImageView) findViewById(R.id.iv_documents_commercial_insurance_error);
        ivCommercialInsuranceAccepted = (ImageView) findViewById(R.id.iv_documents_commercial_insurance_accepted);

        ivTaxReceiptError = (ImageView) findViewById(R.id.iv_documents_tax_receipt_error);
        ivTaxReceiptAccepted = (ImageView) findViewById(R.id.iv_documents_tax_receipt_accepted);

        ivBankPassbookError = (ImageView) findViewById(R.id.iv_documents_bank_passbook_error);
        ivBankPassbookAccepted = (ImageView) findViewById(R.id.iv_documents_bank_passbook_accepted);

        ivDriverLicenceWithBadgeError = (ImageView) findViewById(R.id.iv_documents_driver_licence_with_badge_number_error);
        ivDriverLicenceWithBadgeAccepted = (ImageView) findViewById(R.id.iv_documents_driver_licence_with_badge_number_accepted);

        ivBackgroundCheckConsentFormError = (ImageView) findViewById(R.id.iv_documents_background_check_consent_form_error);
        ivBackgroundCheckConsentFormAccepted = (ImageView) findViewById(R.id.iv_documents_background_check_consent_form_accepted);

        ivPanCardError = (ImageView) findViewById(R.id.iv_documents_pan_card_error);
        ivPanCardAccepted = (ImageView) findViewById(R.id.iv_documents_pan_card_accepted);

        ivNoObjectionCeritificateError = (ImageView) findViewById(R.id.iv_documents_no_objection_certificate_error);
        ivNoObjectionCeritificateAccepted = (ImageView) findViewById(R.id.iv_documents_no_objection_certificate_accepted);

        txtDriverLicenceStatus = (TextView) findViewById(R.id.txt_documents_driver_licence_status);
        txtPoliceClearanceCertificateStatus = (TextView) findViewById(R.id.txt_documents_police_clearance_certificate_status);
        txtFitnessCertificateStatus = (TextView) findViewById(R.id.txt_documents_fitness_certificate_status);
        txtVehicleRegistrationStatus = (TextView) findViewById(R.id.txt_documents_vehicle_registration_status);
        txtVehiclePermitStatus = (TextView) findViewById(R.id.txt_documents_vehicle_permit_status);
        txtCommercialInsuranceStatus = (TextView) findViewById(R.id.txt_documents_commercial_insurance_status);
        txtTaxReceiptStatus = (TextView) findViewById(R.id.txt_documents_tax_receipt_status);
        txtBankPassbookStatus = (TextView) findViewById(R.id.txt_documents_bank_passbook_status);
        txtDriverLicenceWithBadgeStatus = (TextView) findViewById(R.id.txt_documents_driver_licence_with_badge_number_status);
        txtBackgroundCheckConsentFormStatus = (TextView) findViewById(R.id.txt_documents_background_check_consent_form_status);
        txtPanCardStatus = (TextView) findViewById(R.id.txt_documents_pan_card_status);
        txtNoObjectionCeritificateStatus = (TextView) findViewById(R.id.txt_documents_no_objection_certificate_status);
    }

    private void updateDocuments(int type, int status, String documentURL) {

        switch (type) {
            case AppConstants.DOCUMENT_TYPE_NID_CARD:
                ivDriverLicenceError.setVisibility(getVisibility(TYPE_ERROR, status));
                ivDriverLicenceAccepted.setVisibility(getVisibility(TYPE_ACCEPTED, status));
                txtDriverLicenceStatus.setText(getDocumentStatus(status));
                txtDriverLicenceStatus.setTextColor(ContextCompat.getColor(getApplicationContext(), documentColor[status]));
                Glide.with(getApplicationContext())
                        .load(documentURL)
                        .apply(new RequestOptions()
                                .error(R.drawable.ic_profile_photo_default)
                                .fallback(R.drawable.ic_profile_photo_default)
                                .centerCrop()
                                .fitCenter())
                        .into(nidordrivinglicense_photo);
                break;
            case AppConstants.DOCUMENT_TYPE_DRIVING_LICENSE:
                ivBankPassbookError.setVisibility(getVisibility(TYPE_ERROR, status));
                ivBankPassbookAccepted.setVisibility(getVisibility(TYPE_ACCEPTED, status));
                txtBankPassbookStatus.setText(getDocumentStatus(status));
                txtBankPassbookStatus.setTextColor(ContextCompat.getColor(getApplicationContext(), documentColor[status]));
                Glide.with(getApplicationContext())
                        .load(documentURL)
                        .apply(new RequestOptions()
                                .error(R.drawable.ic_profile_photo_default)
                                .fallback(R.drawable.ic_profile_photo_default)
                                .centerCrop()
                                .fitCenter())
                        .into(vehicle_certificate_photo);
                break;
            case AppConstants.DOCUMENT_TYPE_VEHICLE_REGISTRATION_NEW:
                ivPoliceClearanceCertificateError.setVisibility(getVisibility(TYPE_ERROR, status));
                ivPoliceClearanceCertificateAccepted.setVisibility(getVisibility(TYPE_ACCEPTED, status));
                txtPoliceClearanceCertificateStatus.setText(getDocumentStatus(status));
                txtPoliceClearanceCertificateStatus.setTextColor(ContextCompat.getColor(getApplicationContext(), documentColor[status]));
                Glide.with(getApplicationContext())
                        .load(documentURL)
                        .apply(new RequestOptions()
                                .error(R.drawable.ic_profile_photo_default)
                                .fallback(R.drawable.ic_profile_photo_default)
                                .centerCrop()
                                .fitCenter())
                        .into(driving_license_photo);

                break;

        }

    }

    private int getVisibility(int type, int status) {
        switch (status) {
            case AppConstants.DOCUMENT_STATUS_NOT_UPLOADED:
                return type == TYPE_ERROR ? View.VISIBLE : View.GONE;

            case AppConstants.DOCUMENT_STATUS_PENDING_APPROVAL:
                return type == TYPE_ERROR ? View.VISIBLE : View.GONE;

            case AppConstants.DOCUMENT_STATUS_APPROVED:
                return type == TYPE_ERROR ? View.GONE : View.VISIBLE;

            case AppConstants.DOCUMENT_STATUS_REJECTED:
                return type == TYPE_ERROR ? View.VISIBLE : View.GONE;

            default:
                return type == TYPE_ERROR ? View.VISIBLE : View.GONE;
        }

    }

    private String getDocumentStatus(int status) {

        switch (status) {
            case AppConstants.DOCUMENT_STATUS_NOT_UPLOADED:
                return getString(R.string.label_missing);

            case AppConstants.DOCUMENT_STATUS_PENDING_APPROVAL:
                return getString(R.string.label_pending_approval);

            case AppConstants.DOCUMENT_STATUS_APPROVED:
                return getString(R.string.label_approved);

            case AppConstants.DOCUMENT_STATUS_REJECTED:
                return getString(R.string.label_rejected);

            default:
                return getString(R.string.label_missing);
        }

    }

    private int getDocumentColor(int status) {

        switch (status) {
            case AppConstants.DOCUMENT_STATUS_NOT_UPLOADED:
                return R.color.text_red_1;

            case AppConstants.DOCUMENT_STATUS_PENDING_APPROVAL:
                return R.color.yellow_2;

            case AppConstants.DOCUMENT_STATUS_APPROVED:
                return R.color.text_green_1;

            case AppConstants.DOCUMENT_STATUS_REJECTED:
                return R.color.text_red_1;

            default:
                return R.color.text_red_1;
        }
    }


    private void fetchDocumentStatus() {

        HashMap<String, String> urlParams = new HashMap<>();
        urlParams.put("phone", Config.getInstance().getPhone());

        DataManager.fetchDocumentStatus(urlParams, new DocumentStatusListener() {
            @Override
            public void onLoadCompleted(DocumentStatusBean documentStatusBeanWS) {
                documentStatusBean = documentStatusBeanWS;
                populateDocumentStatus();
            }

            @Override
            public void onLoadFailed(String error) {
                swipeView.setRefreshing(false);
                setProgressScreenVisibility(true, false);
                Snackbar.make(coordinatorLayout, error, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.btn_retry, snackBarRefreshOnClickListener).show();

                if (App.getInstance().isDemo()) {
                    setProgressScreenVisibility(false, false);
                }
            }
        });

    }

    private void populateDocumentStatus() {

        for (DocumentBean bean : documentStatusBean.getDocuments()) {

             updateDocuments(bean.getType(), bean.getDocumentStatus(), bean.getDocumentURL());
        }

        swipeView.setRefreshing(false);
        setProgressScreenVisibility(false, false);
    }


    public void onDocumentsItemClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        //mVibrator.vibrate(25);

        int type = Integer.parseInt(String.valueOf(view.getTag()));

        Log.i(TAG, "onDocumentsItemClick: Document Type : " + type);

        startActivityForResult(new Intent(this, DocumentUploadActivity.class)
                .putExtra("type", type), REQUEST_DOCUMENT_UPLOAD);

    }
}
