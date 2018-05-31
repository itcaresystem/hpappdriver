package ride.happyy.driver.net.WSAsyncTasks;

import android.os.AsyncTask;

import java.util.HashMap;

import ride.happyy.driver.model.DocumentStatusBean;
import ride.happyy.driver.net.invokers.DocumentStatusInvoker;



public class DocumentStatusTask extends AsyncTask<String, Integer, DocumentStatusBean> {

    private DocumentStatusTaskListener documentStatusTaskListener;

    private HashMap<String, String> urlParams;

    public DocumentStatusTask(HashMap<String, String> urlParams) {
        super();
        this.urlParams = urlParams;
    }

    @Override
    protected DocumentStatusBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        DocumentStatusInvoker documentStatusInvoker = new DocumentStatusInvoker(urlParams, null);
        return documentStatusInvoker.invokeDocumentStatusWS();
    }

    @Override
    protected void onPostExecute(DocumentStatusBean result) {
        super.onPostExecute(result);
        if (result != null)
            documentStatusTaskListener.dataDownloadedSuccessfully(result);
        else
            documentStatusTaskListener.dataDownloadFailed();
    }

    public static interface DocumentStatusTaskListener {
        void dataDownloadedSuccessfully(DocumentStatusBean documentStatusBean);

        void dataDownloadFailed();
    }

    public DocumentStatusTaskListener getDocumentStatusTaskListener() {
        return documentStatusTaskListener;
    }

    public void setDocumentStatusTaskListener(DocumentStatusTaskListener documentStatusTaskListener) {
        this.documentStatusTaskListener = documentStatusTaskListener;
    }
}
