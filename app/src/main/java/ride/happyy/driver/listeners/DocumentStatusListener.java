package ride.happyy.driver.listeners;

import ride.happyy.driver.model.DocumentStatusBean;

public interface DocumentStatusListener {

    void onLoadCompleted(DocumentStatusBean documentStatusBean);

    void onLoadFailed(String error);
}
