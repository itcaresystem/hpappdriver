package ride.happyy.driver.net.WSAsyncTasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.util.HashMap;

import ride.happyy.driver.model.ProfileBean;
import ride.happyy.driver.net.invokers.ProfileInvoker;



public class ProfileTask extends AsyncTask<String, Integer, ProfileBean> {

    private ProfileTaskListener profileTaskListener;
    private JSONObject postData;

    private HashMap<String, String> urlParams;

    public ProfileTask(JSONObject postData) {
        super();
        this.postData = postData;
    }

    @Override
    protected ProfileBean doInBackground(String... params) {
        System.out.println(">>>>>>>>>doInBackground");
        ProfileInvoker profileInvoker = new ProfileInvoker(null, postData);
        return profileInvoker.invokeProfileWS();
    }

    @Override
    protected void onPostExecute(ProfileBean result) {
        super.onPostExecute(result);
        if (result != null)
            profileTaskListener.dataDownloadedSuccessfully(result);
        else
            profileTaskListener.dataDownloadFailed();
    }

    public static interface ProfileTaskListener {
        void dataDownloadedSuccessfully(ProfileBean profileBean);

        void dataDownloadFailed();
    }

    public ProfileTaskListener getProfileTaskListener() {
        return profileTaskListener;
    }

    public void setProfileTaskListener(ProfileTaskListener profileTaskListener) {
        this.profileTaskListener = profileTaskListener;
    }
}
