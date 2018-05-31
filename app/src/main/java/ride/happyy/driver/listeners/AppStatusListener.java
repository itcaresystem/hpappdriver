package ride.happyy.driver.listeners;

import ride.happyy.driver.model.AppStatusBean;



public interface AppStatusListener {
    void onLoadCompleted(AppStatusBean appStatusBean);

    void onLoadFailed(String error);
}
