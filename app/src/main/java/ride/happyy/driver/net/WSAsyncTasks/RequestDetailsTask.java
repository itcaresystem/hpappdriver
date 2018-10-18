package ride.happyy.driver.net.WSAsyncTasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.driver.model.RequestDetailsBean;
import ride.happyy.driver.net.invokers.RequestDetailsInvoker;



public class RequestDetailsTask extends AsyncTask<String, Integer, RequestDetailsBean> {

    private RequestDetailsTaskListener requestDetailsTaskListener;

    private HashMap<String, String> urlParams;
    private JSONObject postData;

    public RequestDetailsTask(JSONObject postData) {
        super();
       // this.urlParams = urlParams;
        this.postData = postData;
    }

    @Override
    protected RequestDetailsBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        RequestDetailsInvoker requestDetailsInvoker = new RequestDetailsInvoker(null, postData);
        return requestDetailsInvoker.invokeRequestDetailsWS();
    }

    @Override
    protected void onPostExecute(RequestDetailsBean result) {
        super.onPostExecute(result);
        if (result != null)
            requestDetailsTaskListener.dataDownloadedSuccessfully(result);
        else
            requestDetailsTaskListener.dataDownloadFailed();
    }

    public static interface RequestDetailsTaskListener {
        void dataDownloadedSuccessfully(RequestDetailsBean requestDetailsBean);

        void dataDownloadFailed();
    }

    public RequestDetailsTaskListener getRequestDetailsTaskListener() {
        return requestDetailsTaskListener;
    }

    public void setRequestDetailsTaskListener(RequestDetailsTaskListener requestDetailsTaskListener) {
        this.requestDetailsTaskListener = requestDetailsTaskListener;
    }
}
