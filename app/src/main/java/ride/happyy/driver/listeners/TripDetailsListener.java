package ride.happyy.driver.listeners;

import ride.happyy.driver.model.TripBean;



public interface TripDetailsListener {

    void onLoadCompleted(TripBean tripBean);

    void onLoadFailed(String error);
}
