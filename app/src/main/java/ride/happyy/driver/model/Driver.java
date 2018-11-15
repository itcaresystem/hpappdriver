package ride.happyy.driver.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Driver {
    @SerializedName("sl_number")
    private String slNumber;
    @SerializedName("name")
    private String name;
    @SerializedName("total_earn")
    private String totalEarning;
    @SerializedName("total_trips")
    private String totalTrips;
    @SerializedName("status")
    boolean statusString;
    @SerializedName("message")
    String messageString;
    @SerializedName("driverlist")
    ArrayList<Driver> driverlist;

    public Driver(){

    }

    public Driver(String slNumber, String name, String totalEarning) {
        this.slNumber = slNumber;
        this.name = name;
        this.totalEarning = totalEarning;

    }

    public Driver(String name, String totalEarning) {
        this.name = name;
        this.totalEarning = totalEarning;
    }

    public String getSlNumber() {
        return slNumber;
    }

    public void setSlNumber(String slNumber) {
        this.slNumber = slNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTotalEarning() {
        return totalEarning;
    }

    public void setTotalEarning(String totalEarning) {
        this.totalEarning = totalEarning;
    }

    public String getTotalTrips() {
        return totalTrips;
    }

    public void setTotalTrips(String totalTrips) {
        this.totalTrips = totalTrips;
    }
    public boolean isSuccess(){
        return statusString;
    }

    public String getMessage() {
        return messageString;
    }
    public ArrayList<Driver> getDriverlist() {
        return driverlist;
    }

    public void setDriverlist(ArrayList<Driver> driverlist) {
        this.driverlist = driverlist;
    }


}
