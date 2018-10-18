package ride.happyy.driver.net.WSAsyncTasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.driver.model.AppStatusBean;
import ride.happyy.driver.net.invokers.AppStatusInvoker;



public class AppStatusTask extends AsyncTask<String, Integer, AppStatusBean> {

    private AppStatusTaskListener appStatusTaskListener;

    private HashMap<String, String> urlParams;
    private JSONObject postData;

    public AppStatusTask(JSONObject postData) {
        super();
        this.postData =postData;
    }

    @Override
    protected AppStatusBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        AppStatusInvoker appStatusInvoker = new AppStatusInvoker(null, postData);
        return appStatusInvoker.invokeAppStatusWS();
    }

    @Override
    protected void onPostExecute(AppStatusBean result) {
        super.onPostExecute(result);
        if (result != null)
            appStatusTaskListener.dataDownloadedSuccessfully(result);
        else
            appStatusTaskListener.dataDownloadFailed();
    }

    public static interface AppStatusTaskListener {
        void dataDownloadedSuccessfully(AppStatusBean appStatusBean);

        void dataDownloadFailed();
    }

    public AppStatusTaskListener getAppStatusTaskListener() {
        return appStatusTaskListener;
    }

    public void setAppStatusTaskListener(AppStatusTaskListener appStatusTaskListener) {
        this.appStatusTaskListener = appStatusTaskListener;
    }
}
