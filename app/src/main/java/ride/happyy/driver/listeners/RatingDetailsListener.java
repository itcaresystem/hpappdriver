package ride.happyy.driver.listeners;

import ride.happyy.driver.model.RatingDetailsBean;



public interface RatingDetailsListener {
    void onLoadCompleted(RatingDetailsBean ratingDetailsBean);

    void onLoadFailed(String error);

}
