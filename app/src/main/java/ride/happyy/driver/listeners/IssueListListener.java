package ride.happyy.driver.listeners;

import ride.happyy.driver.model.IssueListBean;



public interface IssueListListener {

    void onLoadCompleted(IssueListBean issueListBean);

    void onLoadFailed(String error);

}
