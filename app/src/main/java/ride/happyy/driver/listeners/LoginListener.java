package ride.happyy.driver.listeners;

import ride.happyy.driver.model.AuthBean;



public interface LoginListener {

    void onLoadCompleted(AuthBean authBean);

    void onLoadFailed(String error);

}
