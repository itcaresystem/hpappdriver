package ride.happyy.driver.listeners;

import ride.happyy.driver.model.HelpBean;


public interface HelpListener {
    void onLoadCompleted(HelpBean helpBean);

    void onLoadFailed(String error);
}
