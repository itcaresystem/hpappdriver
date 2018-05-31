package ride.happyy.driver.listeners;

import ride.happyy.driver.model.CommentListBean;


public interface CommentListListener {

    void onLoadCompleted(CommentListBean commentListBean);

    void onLoadFailed(String error);
}
