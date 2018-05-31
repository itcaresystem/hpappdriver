package ride.happyy.driver.net.WSAsyncTasks;

import android.os.AsyncTask;

import com.google.firebase.iid.FirebaseInstanceId;



public class FCMRegistrationTask extends AsyncTask<String, Integer, String> {
    private FCMRegistrationTaskListener fcmRegistrationTaskListener;

    @Override
    protected String doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        String regID = "";
        regID = FirebaseInstanceId.getInstance().getToken();
        return regID;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result != null)
            fcmRegistrationTaskListener.dataDownloadedSuccessfully(result);
        else
            fcmRegistrationTaskListener.dataDownloadFailed();
    }


    public interface FCMRegistrationTaskListener {
        void dataDownloadedSuccessfully(String result);

        void dataDownloadFailed();
    }

    public FCMRegistrationTaskListener getFCMRegistrationTaskListener() {
        return fcmRegistrationTaskListener;
    }

    public void setFCMRegistrationTaskListener(FCMRegistrationTaskListener fcmRegistrationTaskListener) {
        this.fcmRegistrationTaskListener = fcmRegistrationTaskListener;
    }
}