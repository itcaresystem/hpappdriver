package ride.happyy.driver.net;

public interface ResponseCallback<T> {
    void onSuccess(T data);
    void onError(Throwable th);
}
