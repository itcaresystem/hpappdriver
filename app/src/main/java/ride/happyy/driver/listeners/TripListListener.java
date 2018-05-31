package ride.happyy.driver.listeners;

import ride.happyy.driver.model.TripListBean;



public interface TripListListener {

    void onLoadCompleted(TripListBean tripListBean);

    void onLoadFailed(String error);

}
