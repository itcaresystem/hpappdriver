package ride.happyy.driver.listeners;


import ride.happyy.driver.model.AccessibilityBean;

public interface AccessibilityListener {

    void onLoadCompleted(AccessibilityBean accessibilityBean);

    void onLoadFailed(String error);
}
