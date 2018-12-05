package ride.happyy.driver.model;

import com.google.gson.annotations.SerializedName;

public class CurentLocation {
    @SerializedName("phone")
    String phone;
    @SerializedName("curent_location")
    String curentLocation;
    @SerializedName("latitude")
    String latitude;
    @SerializedName("longitude")
    String longitude;

    public CurentLocation(String phone, String latitude, String longitude) {
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCurentLocation(String curentLocation) {
        this.curentLocation = curentLocation;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
