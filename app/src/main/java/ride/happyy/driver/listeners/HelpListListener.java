package ride.happyy.driver.listeners;

import ride.happyy.driver.model.HelpListBean;



public interface HelpListListener {
    void onLoadCompleted(HelpListBean helpListBean);

    void onLoadFailed(String error);
}
