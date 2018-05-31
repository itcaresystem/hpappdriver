package ride.happyy.driver.listeners;

import ride.happyy.driver.model.WeeklyEarningsBean;



public interface WeeklyEarningsListener {

    void onLoadCompleted(WeeklyEarningsBean weeklyEarningsBean);

    void onLoadFailed(String error);
}
