package ride.happyy.driver.net.WSAsyncTasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import ride.happyy.driver.model.BasicBean;
import ride.happyy.driver.net.invokers.ArrivalConfirmationInvoker;



public class ArrivalConfirmationTask extends AsyncTask<String, Integer, BasicBean> {

    private ArrivalConfirmationTaskListener arrivalConfirmationTaskListener;

    private JSONObject postData;

    public ArrivalConfirmationTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    @Override
    protected BasicBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        ArrivalConfirmationInvoker arrivalConfirmationInvoker = new ArrivalConfirmationInvoker(null, postData);
        return arrivalConfirmationInvoker.invokeArrivalConfirmationWS();
    }

    @Override
    protected void onPostExecute(BasicBean result) {
        super.onPostExecute(result);
        if (result != null)
            arrivalConfirmationTaskListener.dataDownloadedSuccessfully(result);
        else
            arrivalConfirmationTaskListener.dataDownloadFailed();
    }

    public static interface ArrivalConfirmationTaskListener {
        void dataDownloadedSuccessfully(BasicBean arrivalConfirmationBean);

        void dataDownloadFailed();
    }

    public ArrivalConfirmationTaskListener getArrivalConfirmationTaskListener() {
        return arrivalConfirmationTaskListener;
    }

    public void setArrivalConfirmationTaskListener(ArrivalConfirmationTaskListener arrivalConfirmationTaskListener) {
        this.arrivalConfirmationTaskListener = arrivalConfirmationTaskListener;
    }
}
