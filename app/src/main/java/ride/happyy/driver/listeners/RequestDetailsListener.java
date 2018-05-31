package ride.happyy.driver.listeners;

import ride.happyy.driver.model.RequestDetailsBean;



public interface RequestDetailsListener {

    void onLoadCompleted(RequestDetailsBean requestDetailsBean);

    void onLoadFailed(String error);
}
