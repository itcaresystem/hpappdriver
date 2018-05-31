package ride.happyy.driver.net.WSAsyncTasks;

import android.os.AsyncTask;

import java.util.HashMap;

import ride.happyy.driver.model.PolyPointBean;
import ride.happyy.driver.net.invokers.PolyPointInvoker;



public class PolyPointTask extends AsyncTask<String, Integer, PolyPointBean> {

    private PolyPointTaskListener polyPointTaskListener;

    private HashMap<String, String> urlParams;

    public PolyPointTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected PolyPointBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        PolyPointInvoker polyPointInvoker = new PolyPointInvoker(urlParams, null);
        return polyPointInvoker.invokePolyPointWS();
    }

    @Override
    protected void onPostExecute(PolyPointBean result) {
        super.onPostExecute(result);
        if (result != null)
            polyPointTaskListener.dataDownloadedSuccessfully(result);
        else
            polyPointTaskListener.dataDownloadFailed();
    }

    public static interface PolyPointTaskListener {
        void dataDownloadedSuccessfully(PolyPointBean polyPointBean);

        void dataDownloadFailed();
    }

    public PolyPointTaskListener getPolyPointTaskListener() {
        return polyPointTaskListener;
    }

    public void setPolyPointTaskListener(PolyPointTaskListener polyPointTaskListener) {
        this.polyPointTaskListener = polyPointTaskListener;
    }
}
