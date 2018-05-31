package ride.happyy.driver.listeners;


import ride.happyy.driver.model.BasicBean;

public interface BasicListener {

    void onLoadCompleted(BasicBean basicBean);

    void onLoadFailed(String error);
}
