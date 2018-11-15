package ride.happyy.driver.net;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ride.happyy.driver.model.Driver;
import ride.happyy.driver.model.OutOfDhakaServiceModel;
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
}



