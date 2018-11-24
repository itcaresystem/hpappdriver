package ride.happyy.driver.model;

import com.google.gson.annotations.SerializedName;

public class ServerResponse {
    @SerializedName("status")
    boolean statusString;
    @SerializedName("message")
    String messageString;

    public boolean isSuccess(){
        return statusString;
    }

    public String getMessage() {
        return messageString;
    }

    public void setStatusString(boolean statusString) {
        this.statusString = statusString;
    }

    public void setMessageString(String messageString) {
        this.messageString = messageString;
    }
}
