package ride.happyy.driver.model;

import com.google.gson.annotations.SerializedName;

public class OutofDhakaServerresponse {
    @SerializedName("status")
    boolean statusString;
    @SerializedName("message")
    String messageString;

    public boolean isStatusString() {
        return statusString;
    }

    public String getMessageString() {
        return messageString;
    }
}
