package ride.happyy.driver.model;

public class Driver {
    private String slNumber;
    private String name;
    private String totalEarning;
    private String totalTrips;



    public Driver(String slNumber, String name, String totalEarning) {
        this.slNumber = slNumber;
        this.name = name;
        this.totalEarning = totalEarning;

    }

    public String getSlNumber() {
        return slNumber;
    }

    public void setSlNumber(String slNumber) {
        this.slNumber = slNumber;
    }

    public Driver(String name, String totalEarning) {
        this.name = name;
        this.totalEarning = totalEarning;
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
}
