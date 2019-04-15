package ride.happyy.driver.net;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ride.happyy.driver.model.CurentLocation;
import ride.happyy.driver.model.Driver;
import ride.happyy.driver.model.MyNotification;
import ride.happyy.driver.model.OutOfDhakaServiceModel;
import ride.happyy.driver.model.RequestTransferData;
import ride.happyy.driver.model.ServerResponse;
import ride.happyy.driver.model.User;
public interface RetrofitApiInterface {
    @POST("server_side_code.php")
    Call<ServerResponse> getUserValidity(@Body User userLoginCredential);
    @GET("outofdhakacervice.php")
    Call<ServerResponse> getJoke(@Query("user_id") String userId);
    @POST("outofdhakacervice.php")
    Call<ServerResponse> sendOutOfdhakaRequest(@Body OutOfDhakaServiceModel outOfDhakaService);
    @GET("leaderbord.php")
    Call<ArrayList<Driver>> getLeaderBordToday(@Query("phone") String phone, @Query("reqforleaderbord") String reqforleaderbord);
    @GET("leaderbord_trips.php")
    Call<ArrayList<Driver>> getLeaderBordTodayTrips(@Query("phone") String phone, @Query("reqforleaderbord_trip") String reqforleaderbord);
    @POST("request_transfer.php")
    Call<ServerResponse> requestTransferToOthers(@Body RequestTransferData requestTransferData);
    @POST("update_driver_location.php")
    Call<ServerResponse> updateDriverLocation(@Body CurentLocation curentLocation);
    @GET("get_all_message.php")
    Call<ArrayList<MyNotification>> getMyAllMessage(@Query("driver_phone") String phone);
}



