package ride.happyy.driver.net.WSAsyncTasks;

import android.os.AsyncTask;

import java.util.HashMap;

import ride.happyy.driver.model.RatingDetailsBean;
import ride.happyy.driver.net.invokers.RatingDetailsInvoker;



public class RatingDetailsTask extends AsyncTask<String, Integer, RatingDetailsBean> {

    private RatingDetailsTaskListener ratingDetailsTaskListener;

    private HashMap<String, String> urlParams;

    public RatingDetailsTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected RatingDetailsBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        RatingDetailsInvoker ratingDetailsInvoker = new RatingDetailsInvoker(urlParams, null);
        return ratingDetailsInvoker.invokeRatingDetailsWS();
    }

    @Override
    protected void onPostExecute(RatingDetailsBean result) {
        super.onPostExecute(result);
        if (result != null)
            ratingDetailsTaskListener.dataDownloadedSuccessfully(result);
        else
            ratingDetailsTaskListener.dataDownloadFailed();
    }

    public static interface RatingDetailsTaskListener {
        void dataDownloadedSuccessfully(RatingDetailsBean ratingDetailsBean);

        void dataDownloadFailed();
    }

    public RatingDetailsTaskListener getRatingDetailsTaskListener() {
        return ratingDetailsTaskListener;
    }

    public void setRatingDetailsTaskListener(RatingDetailsTaskListener ratingDetailsTaskListener) {
        this.ratingDetailsTaskListener = ratingDetailsTaskListener;
    }
}
