package ride.happyy.driver.model;

import com.google.gson.annotations.SerializedName;

public class MyNotification {
    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;
    @SerializedName("details")
    private String details;

    public MyNotification(){}
    public MyNotification(String details) {
        this.details = details;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
