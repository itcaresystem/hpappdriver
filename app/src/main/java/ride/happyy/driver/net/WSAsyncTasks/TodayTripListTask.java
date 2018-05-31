package ride.happyy.driver.net.WSAsyncTasks;

import android.os.AsyncTask;

import java.util.HashMap;

import ride.happyy.driver.model.TripListBean;
import ride.happyy.driver.net.invokers.TodayTripListInvoker;



public class TodayTripListTask extends AsyncTask<String, Integer, TripListBean> {

    private TodayTripListTaskListener todayTripListTaskListener;

    private HashMap<String, String> urlParams;

    public TodayTripListTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected TripListBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        TodayTripListInvoker todayTripListInvoker = new TodayTripListInvoker(urlParams, null);
        return todayTripListInvoker.invokeTodayTripListWS();
    }

    @Override
    protected void onPostExecute(TripListBean result) {
        super.onPostExecute(result);
        if (result != null)
            todayTripListTaskListener.dataDownloadedSuccessfully(result);
        else
            todayTripListTaskListener.dataDownloadFailed();
    }

    public static interface TodayTripListTaskListener {
        void dataDownloadedSuccessfully(TripListBean tripListBean);

        void dataDownloadFailed();
    }

    public TodayTripListTaskListener getTodayTripListTaskListener() {
        return todayTripListTaskListener;
    }

    public void setTodayTripListTaskListener(TodayTripListTaskListener todayTripListTaskListener) {
        this.todayTripListTaskListener = todayTripListTaskListener;
    }
}
