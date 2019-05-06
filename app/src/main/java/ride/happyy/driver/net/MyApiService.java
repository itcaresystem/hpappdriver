package ride.happyy.driver.net;
import java.util.ArrayList;

import ride.happyy.driver.model.CurentLocation;
import ride.happyy.driver.model.Driver;
import ride.happyy.driver.model.MyNotification;
import ride.happyy.driver.model.OutOfDhakaServiceModel;
import ride.happyy.driver.model.RequestTransferData;
import ride.happyy.driver.model.ServerResponse;
import ride.happyy.driver.model.User;
import ride.happyy.driver.model.VehicleInfo;

public interface MyApiService {
    void userValidityCheck(User userLoginCredential, ResponseCallback<String> callback);
    void getJokeFromServer(String userId, ResponseCallback<String> callback);
    void sendOutOfdhakaRequest(OutOfDhakaServiceModel outOfDhakaService, ResponseCallback<String> callback);
    void getLeaderBordToday(String phone, String reqforleaderbord, ResponseCallback<ArrayList<Driver>> callback);
    void getLeaderBordTodayTrips(String phone, String reqforleaderbord, ResponseCallback<ArrayList<Driver>> callback);
    void request_transfer(RequestTransferData requestTransferData, ResponseCallback<ServerResponse> respnse);
    void updateDriverCurrentLocation(CurentLocation curentLocation,ResponseCallback<ServerResponse> responseCallback);
    void updateDriverVehicleInfo(VehicleInfo vehicleInfo, ResponseCallback<ServerResponse> responseCallback);
    void getMyAllMessage(String phone, ResponseCallback<ArrayList<MyNotification>> responseCallback);

}
