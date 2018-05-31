package ride.happyy.driver.net.WSAsyncTasks;

import android.os.AsyncTask;

import java.util.HashMap;

import ride.happyy.driver.model.TripListBean;
import ride.happyy.driver.net.invokers.TripHistoryInvoker;



public class TripHistoryTask extends AsyncTask<String, Integer, TripListBean> {

    private TripHistoryTaskListener tripHistoryTaskListener;

    private HashMap<String, String> urlParams;

    public TripHistoryTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected TripListBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        TripHistoryInvoker tripHistoryInvoker = new TripHistoryInvoker(urlParams, null);
        return tripHistoryInvoker.invokeTripHistoryWS();
    }

    @Override
    protected void onPostExecute(TripListBean result) {
        super.onPostExecute(result);
        if (result != null)
            tripHistoryTaskListener.dataDownloadedSuccessfully(result);
        else
            tripHistoryTaskListener.dataDownloadFailed();
    }

    public static interface TripHistoryTaskListener {
        void dataDownloadedSuccessfully(TripListBean tripListBean);

        void dataDownloadFailed();
    }

    public TripHistoryTaskListener getTripHistoryTaskListener() {
        return tripHistoryTaskListener;
    }

    public void setTripHistoryTaskListener(TripHistoryTaskListener tripHistoryTaskListener) {
        this.tripHistoryTaskListener = tripHistoryTaskListener;
    }
}
