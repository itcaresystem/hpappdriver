package ride.happyy.driver.model;

import com.google.gson.annotations.SerializedName;

public class RequestTransferData {
    @SerializedName("phone")
    String phone;
    @SerializedName("request_id")
    String request_id;
    @SerializedName("request_status")
    String request_status;

public RequestTransferData(){

}
    public RequestTransferData(String phone, String request_id, String request_status) {
        this.phone = phone;
        this.request_id = request_id;
        this.request_status = request_status;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public String getRequest_status() {
        return request_status;
    }

    public void setRequest_status(String request_status) {
        this.request_status = request_status;
    }
}
