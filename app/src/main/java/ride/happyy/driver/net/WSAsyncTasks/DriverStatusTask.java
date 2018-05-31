package ride.happyy.driver.net.WSAsyncTasks;

import android.os.AsyncTask;

import java.util.HashMap;

import ride.happyy.driver.model.BasicBean;
import ride.happyy.driver.net.invokers.DriverStatusInvoker;



public class DriverStatusTask extends AsyncTask<String, Integer, BasicBean> {

    private DriverStatusTaskListener driverStatusTaskListener;

    private HashMap<String, String> urlParams;

    public DriverStatusTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected BasicBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        DriverStatusInvoker driverStatusInvoker = new DriverStatusInvoker(urlParams, null);
        return driverStatusInvoker.invokeDriverStatusWS();
    }

    @Override
    protected void onPostExecute(BasicBean result) {
        super.onPostExecute(result);
        if (result != null)
            driverStatusTaskListener.dataDownloadedSuccessfully(result);
        else
            driverStatusTaskListener.dataDownloadFailed();
    }

    public static interface DriverStatusTaskListener {
        void dataDownloadedSuccessfully(BasicBean basicBean);

        void dataDownloadFailed();
    }

    public DriverStatusTaskListener getDriverStatusTaskListener() {
        return driverStatusTaskListener;
    }

    public void setDriverStatusTaskListener(DriverStatusTaskListener driverStatusTaskListener) {
        this.driverStatusTaskListener = driverStatusTaskListener;
    }
}
