package ride.happyy.driver.net.WSAsyncTasks;

import android.os.AsyncTask;

import java.util.HashMap;

import ride.happyy.driver.model.HelpBean;
import ride.happyy.driver.net.invokers.HelpInvoker;



public class HelpTask extends AsyncTask<String, Integer, HelpBean> {

    private HelpTaskListener helpTaskListener;

    private HashMap<String, String> urlParams;

    public HelpTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected HelpBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        HelpInvoker helpInvoker = new HelpInvoker(urlParams, null);
        return helpInvoker.invokeHelpWS();
    }

    @Override
    protected void onPostExecute(HelpBean result) {
        super.onPostExecute(result);
        if (result != null)
            helpTaskListener.dataDownloadedSuccessfully(result);
        else
            helpTaskListener.dataDownloadFailed();
    }

    public static interface HelpTaskListener {
        void dataDownloadedSuccessfully(HelpBean helpBean);

        void dataDownloadFailed();
    }

    public HelpTaskListener getHelpTaskListener() {
        return helpTaskListener;
    }

    public void setHelpTaskListener(HelpTaskListener helpTaskListener) {
        this.helpTaskListener = helpTaskListener;
    }
}
