package ride.happyy.driver.listeners;

import ride.happyy.driver.model.AuthBean;



public interface RegistrationListener {

    void onLoadCompleted(AuthBean authBean);

    void onLoadFailed(String error);
}
