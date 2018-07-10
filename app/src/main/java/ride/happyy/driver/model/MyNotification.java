package ride.happyy.driver.model;

public class MyNotification {
    private int id;
    private String title;
    private String details;

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
