package ride.happyy.driver.net;

import java.util.ArrayList;

import ride.happyy.driver.model.Driver;
import ride.happyy.driver.model.OutOfDhakaServiceModel;
import ride.happyy.driver.model.User;

public interface MyApiService {
    void userValidityCheck(User userLoginCredential, ResponseCallback<String> callback);
    void getJokeFromServer(String userId, ResponseCallback<String> callback);
    void sendOutOfdhakaRequest(OutOfDhakaServiceModel outOfDhakaService, ResponseCallback<String> callback);
    void getLeaderBordToday(String phone, String reqforleaderbord, ResponseCallback<ArrayList<Driver>> callback);
}
