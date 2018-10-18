package ride.happyy.driver.net;


public class ServiceNames {

    /*Set BASE URL here*/
   // private static final String PRODUCTION_API = "http://taxi.smarttamizhans.in";
    private static final String PRODUCTION_API = "http://happyyride.com";
  
    /* Set API VERSION here*/
   // public static final String API_VERSION = "/Webservices_driver";
    public static final String API_VERSION = "/happyyrideapi/v1/driverapi";


    /*Set UPLOAD PATH. DO NOT CHANGE THIS UNLESS YOU KNOW WHAT YOU ARE DOING.*/
   // public static final String PATH_UPLOADS = "/";
    public static final String PATH_UPLOADS = "/";

    /*Set API URL here*/
  //  private static final String API = PRODUCTION_API + API_VERSION;
    private static final String API = PRODUCTION_API + API_VERSION;

    /*Set IMAGE UPLOAD URL here.  DO NOT CHANGE THIS UNLESS YOU KNOW WHAT YOU ARE DOING.*/
  //  public static final String API_UPLOADS = PRODUCTION_API + PATH_UPLOADS;
    public static final String API_UPLOADS = PRODUCTION_API + PATH_UPLOADS;


    /* API END POINTS*/


    public static final String DUMMY = API + "/dummy?";

    public static final String POLY_POINTS = "https://maps.googleapis.com/maps/api/directions/json?";

    public static final String REGISTRATION = API + "/driver_signup.php";
   // public static final String AUTH_EMAIL = API + "/login?";
    public static final String AUTH_PHONE = API + "/login.php";
   // public static final String FORGOT_PASSWORD = API + "/forgot_password?";
    public static final String FORGOT_PASSWORD = API + "/forgot_password.php";

    //public static final String FCM_UPDATE = API + "/update_fcm_token?";
    public static final String FCM_UPDATE = API + "/update_fcm_token.php";

    public static final String PHONE_REGISTRATION = API + "/driver_signup.php";
    public static final String MOBILE_NUMBER_AVAILABILITY = API + "/phone_available_check.php";
    public static final String OTP_VERIFICATION = API + "/verify_otp?";
    //public static final String OTP_SEND = API + "/login?";
    public static final String OTP_SEND = API + "/login_otp.php";
    public static final String OTP_RESEND_CODE = API + "/resend_otp.php";

    //public static final String DOCUMENT_UPDATE = API + "/document_upload?";
    public static final String DOCUMENT_UPDATE = "http://happyyride.com/happyyrideapi/v1/images/myapi/upload_document.php";
    //public static final String DOCUMENT_STATUS = API + "/document_status?";
    public static final String DOCUMENT_STATUS = API + "/document_status.php?";

    public static final String DRIVER_DETAILS_REGISTRATION = API + "/registration?";
    //public static final String DRIVER_TYPE_REGISTRATION = API + "/update_driver_type?";
    public static final String DRIVER_TYPE_REGISTRATION = API + "/update_driver_type.php";
    //public static final String DRIVER_STATUS_CHANGE = API + "/update_driver_status?";
    public static final String DRIVER_STATUS_CHANGE = API + "/update_driver_status.php";
    //public static final String DRIVER_STATUS = API + "/get_driver_status?";
    public static final String DRIVER_STATUS = API + "/get_driver_status.php";
   // public static final String DRIVER_LOCATION_UPDATE = API + "/update_driver_location?";
    public static final String DRIVER_LOCATION_UPDATE = API + "/update_driver_location.php";

    //public static final String TODAY_TRIP_LIST = API + "/trip_list_for_today?";
    public static final String TODAY_TRIP_LIST = API + "/trip_list_for_today.php";
    //public static final String TRIP_HISTORY = API + "/trip_history?";
    public static final String TRIP_HISTORY = API + "/trip_history.php?";
    //public static final String TRIP_DETAILS = API + "/trip_details?";
    public static final String TRIP_DETAILS = API + "/trip_details.php?";

    //public static final String WEEKLY_EARNINGS = API + "/weekly_earnings?";
    public static final String WEEKLY_EARNINGS = API + "/weekly_earnings.php?";
    //public static final String RATING_DETAILS = API + "/rating_details?";
    public static final String RATING_DETAILS = API + "/rating_details.php";

    //public static final String TRIP_FEEDBACK = API + "/tripfeedback?";
    public static final String TRIP_FEEDBACK = API + "/tripfeedback.php";
    //public static final String TRIP_SUMMARY = API + "/trip_summary?";
    public static final String TRIP_SUMMARY = API + "/trip_summary.php?";
    //public static final String REQUEST_DETAILS = API + "/request_details?";
    public static final String REQUEST_DETAILS = API + "/request_details.php";

    //public static final String TRIP_ACCEPT = API + "/trip_accept?";
    public static final String TRIP_ACCEPT = API + "/trip_accept.php";
    //public static final String TRIP_CONFIRM_CAR_ARRIVAL = API + "/confirm_car_arrival?";
    public static final String TRIP_CONFIRM_CAR_ARRIVAL = API + "/confirm_car_arrival.php";
   // public static final String TRIP_START = API + "/trip_start?";
    public static final String TRIP_START = API + "/trip_start.php";
    //public static final String TRIP_END = API + "/trip_end?";
    public static final String TRIP_END = API + "/trip_end.php";
    //public static final String TRIP_CONFIRM_CASH_COLLECTION = API + "/confirm_cash_collection?";
    public static final String TRIP_CONFIRM_CASH_COLLECTION = API + "/confirm_cash_collection.php";


  //  public static final String ISSUE_LIST = API + "/rider_feedback_issues?";
   // public static final String COMMENT_LIST = API + "/rider_feedback_comments?";

    public static final String ISSUE_LIST = API + "/rider_feedback_issues.php";
    public static final String COMMENT_LIST = API + "/rider_feedback_comments.php";

   // public static final String HELP_LIST = API + "/help_page_list?";
  //  public static final String HELP_PAGE = API + "/help?";
  //  public static final String HELP_PAGE_REVIEW = API + "/help_page_review?";

    public static final String HELP_LIST = API + "/help_page_list.php";
    public static final String HELP_PAGE = API + "/help.php?";
    public static final String HELP_PAGE_REVIEW = API + "/help_page_review.php";

   // public static final String APP_STATUS = API + "/app_status?";
    public static final String APP_STATUS = API + "/app_status.php";

   // public static final String UPLOAD_PROFILE_PHOTO = API + "/profile_photo_upload?";
    public static final String UPLOAD_PROFILE_PHOTO = "http://happyyride.com/happyyrideapi/v1/images/myapi/profile_photo_upload.php";

   // public static final String PROFILE = API + "/get_profile?";
    public static final String PROFILE = API + "/get_profile.php";
  //  public static final String PROFILE_UPDATE = API + "/update_profile?";
    public static final String PROFILE_UPDATE = "http://happyyride.com/happyyrideapi/v1/images/myapi/profile_photo_upload.php";

    //public static final String DRIVER_ACCESSIBILITY_FETCH = API + "/fetch_accesibility_settings?";
    //public static final String DRIVER_ACCESSIBILITY_POST = API + "/update_accesibility_settings?";

    public static final String DRIVER_ACCESSIBILITY_FETCH = API + "/fetch_accesibility_settings.php";
    public static final String DRIVER_ACCESSIBILITY_POST = API + "/update_accesibility_settings.php";


}
