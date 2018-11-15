package ride.happyy.driver.model;

import com.google.gson.annotations.SerializedName;

public class OutOfDhakaServiceModel {
    @SerializedName("user_id")
    String userId ;
    @SerializedName("service_type")
    String serviceType ;
    @SerializedName("pickupp_address")
    String pickupAddress ;
    @SerializedName("dropoff_address")
    String dropOffAddress;
    @SerializedName("from_address")
    String fromAddress;
    @SerializedName("division_name")
    String division ;
    @SerializedName("district_name")
    String district;
    @SerializedName("phone_number")
    String phoneNumber;
    @SerializedName("car_type")
    String carType;
    @SerializedName("gross_rent")
    String rent ;
    @SerializedName("document")
    String document;
    @SerializedName("reques_time_location")
    String requestTimeLocationAddres;

    public OutOfDhakaServiceModel() {
    }

    public OutOfDhakaServiceModel(String userId, String serviceType, String pickupAddress, String dropOffAddress, String fromAddress, String division, String district, String phoneNumber, String carType, String rent, String document, String requestTimeLocationAddres) {
        this.userId = userId;
        this.serviceType = serviceType;
        this.pickupAddress = pickupAddress;
        this.dropOffAddress = dropOffAddress;
        this.fromAddress = fromAddress;
        this.division = division;
        this.district = district;
        this.phoneNumber = phoneNumber;
        this.carType = carType;
        this.rent = rent;
        this.document = document;
        this.requestTimeLocationAddres = requestTimeLocationAddres;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getDropOffAddress() {
        return dropOffAddress;
    }

    public void setDropOffAddress(String dropOffAddress) {
        this.dropOffAddress = dropOffAddress;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public String getRequestTimeLocationAddres() {
        return requestTimeLocationAddres;
    }

    public void setRequestTimeLocationAddres(String requestTimeLocationAddres) {
        this.requestTimeLocationAddres = requestTimeLocationAddres;
    }
}
