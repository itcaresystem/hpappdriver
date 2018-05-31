package ride.happyy.driver.listeners;


import ride.happyy.driver.model.ProfileBean;


public interface ProfileListener {

    void onLoadCompleted(ProfileBean profileBean);

    void onLoadFailed(String error);
}
