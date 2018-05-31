package ride.happyy.driver.listeners;



public interface ProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
