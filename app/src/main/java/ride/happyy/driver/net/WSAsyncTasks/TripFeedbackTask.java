package ride.happyy.driver.net.WSAsyncTasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import ride.happyy.driver.model.TripFeedbackBean;
import ride.happyy.driver.net.invokers.TripFeedbackInvoker;


public class TripFeedbackTask extends AsyncTask<String, Integer, TripFeedbackBean> {

    private TripFeedbackTaskListener tripFeedbackTaskListener;

    private JSONObject postData;

    public TripFeedbackTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    @Override
    protected TripFeedbackBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        TripFeedbackInvoker tripFeedbackInvoker = new TripFeedbackInvoker(null, postData);
        return tripFeedbackInvoker.invokeTripFeedbackWS();
    }

    @Override
    protected void onPostExecute(TripFeedbackBean result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        if (result != null)
            tripFeedbackTaskListener.dataDownloadedSuccessfully(result);
        else
            tripFeedbackTaskListener.dataDownloadFailed();
    }

    public static interface TripFeedbackTaskListener {

        void dataDownloadedSuccessfully(TripFeedbackBean tripFeedbackBean);

        void dataDownloadFailed();
    }

    public TripFeedbackTaskListener getTripFeedbackTaskListener() {
        return tripFeedbackTaskListener;
    }

    public void setTripFeedbackTaskListener(TripFeedbackTaskListener tripFeedbackTaskListener) {
        this.tripFeedbackTaskListener = tripFeedbackTaskListener;
    }
}
