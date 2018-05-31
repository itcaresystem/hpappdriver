package ride.happyy.driver.net.WSAsyncTasks;

import android.os.AsyncTask;

import java.util.HashMap;

import ride.happyy.driver.model.HelpListBean;
import ride.happyy.driver.net.invokers.HelpListInvoker;



public class HelpListTask extends AsyncTask<String, Integer, HelpListBean> {

    private HelpListTaskListener helpListTaskListener;

    private HashMap<String, String> urlParams;

    public HelpListTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected HelpListBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        HelpListInvoker helpListInvoker = new HelpListInvoker(urlParams, null);
        return helpListInvoker.invokeHelpListWS();
    }

    @Override
    protected void onPostExecute(HelpListBean result) {
        super.onPostExecute(result);
        if (result != null)
            helpListTaskListener.dataDownloadedSuccessfully(result);
        else
            helpListTaskListener.dataDownloadFailed();
    }

    public static interface HelpListTaskListener {
        void dataDownloadedSuccessfully(HelpListBean helpListBean);

        void dataDownloadFailed();
    }

    public HelpListTaskListener getHelpListTaskListener() {
        return helpListTaskListener;
    }

    public void setHelpListTaskListener(HelpListTaskListener helpListTaskListener) {
        this.helpListTaskListener = helpListTaskListener;
    }
}
