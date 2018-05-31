package ride.happyy.driver.listeners;


import ride.happyy.driver.model.AuthBean;

public interface PhoneRegistrationListener {

    void onLoadCompleted(AuthBean authBean);

    void onLoadFailed(String error);
}
