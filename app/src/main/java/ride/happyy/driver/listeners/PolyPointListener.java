package ride.happyy.driver.listeners;

import ride.happyy.driver.model.PolyPointBean;



public interface PolyPointListener {
    void onLoadCompleted(PolyPointBean polyPointBean);

    void onLoadFailed(String error);
}
