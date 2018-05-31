package ride.happyy.driver.listeners;


import ride.happyy.driver.model.TripSummaryBean;

public interface TripSummaryListener {

    void onLoadCompleted(TripSummaryBean tripSummaryBean);

    void onLoadFailed(String error);
}
