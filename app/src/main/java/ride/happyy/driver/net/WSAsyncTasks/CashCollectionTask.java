package ride.happyy.driver.net.WSAsyncTasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import ride.happyy.driver.model.BasicBean;
import ride.happyy.driver.net.invokers.CashCollectionInvoker;



public class CashCollectionTask extends AsyncTask<String, Integer, BasicBean> {

    private CashCollectionTaskListener cashCollectionTaskListener;

    private JSONObject postData;

    public CashCollectionTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    @Override
    protected BasicBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        CashCollectionInvoker cashCollectionInvoker = new CashCollectionInvoker(null, postData);
        return cashCollectionInvoker.invokeCashCollectionWS();
    }

    @Override
    protected void onPostExecute(BasicBean result) {
        super.onPostExecute(result);
        if (result != null)
            cashCollectionTaskListener.dataDownloadedSuccessfully(result);
        else
            cashCollectionTaskListener.dataDownloadFailed();
    }

    public static interface CashCollectionTaskListener {
        void dataDownloadedSuccessfully(BasicBean basicBean);

        void dataDownloadFailed();
    }

    public CashCollectionTaskListener getCashCollectionTaskListener() {
        return cashCollectionTaskListener;
    }

    public void setCashCollectionTaskListener(CashCollectionTaskListener cashCollectionTaskListener) {
        this.cashCollectionTaskListener = cashCollectionTaskListener;
    }
}
