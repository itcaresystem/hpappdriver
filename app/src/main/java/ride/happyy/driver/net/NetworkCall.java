package ride.happyy.driver.net;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ride.happyy.driver.model.Driver;
import ride.happyy.driver.model.DriverList;
import ride.happyy.driver.model.OutOfDhakaServiceModel;
import ride.happyy.driver.model.ServerResponse;
import ride.happyy.driver.model.User;


public class NetworkCall implements MyApiService {
    @Override
    public void userValidityCheck(User userLoginCredential, final ResponseCallback<String> userValidityCheckListener) {
      //  Logger.addLogAdapter(new AndroidLogAdapter());

        RetrofitApiInterface retrofitApiInterface = RetrofitApiClient.getClient().create(RetrofitApiInterface.class);
        Call<ServerResponse> call = retrofitApiInterface.getUserValidity(userLoginCredential);

        call.enqueue(new Callback<ServerResponse>() {

            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {

              //  Logger.d("Network layer. User validity Raw response: " + response.raw());

                ServerResponse validity = response.body();
                if(validity!=null){
                    if(validity.isSuccess())
                        userValidityCheckListener.onSuccess(validity.getMessage());
                    else
                        userValidityCheckListener.onError(new Exception(validity.getMessage()));
                }
                else
                    userValidityCheckListener.onError(new Exception(response.message()));

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                userValidityCheckListener.onError(t);
            }
        });
    }

    @Override
    public void getJokeFromServer(String userId, final ResponseCallback<String> getJokeListener) {
        RetrofitApiInterface retrofitApiInterface = RetrofitApiClient.getClient().create(RetrofitApiInterface.class);
        Call<ServerResponse> call = retrofitApiInterface.getJoke(userId);

        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
              //  Logger.d("Network layer. get Joke Raw response: " + response.raw());
                ServerResponse validity = response.body();
                if(validity!=null){
                    if(validity.isSuccess())
                        getJokeListener.onSuccess(validity.getMessage());
                    else
                        getJokeListener.onError(new Exception(validity.getMessage()));
                }
                else
                    getJokeListener.onError(new Exception(response.message()));
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                getJokeListener.onError(t);
            }
        });
    }

    @Override
    public void sendOutOfdhakaRequest(OutOfDhakaServiceModel outOfDhakaService, final ResponseCallback<String> callback) {
        RetrofitApiInterface retrofitApiInterface   = RetrofitApiClient.getClient().create(RetrofitApiInterface.class);
        Call<ServerResponse> call = retrofitApiInterface.sendOutOfdhakaRequest(outOfDhakaService);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse validity = response.body();
                if(validity!=null){
                    if(validity.isSuccess())
                        callback.onSuccess(validity.getMessage());

                    else
                        callback.onError(new Exception(validity.getMessage()));
                }
                else
                    callback.onError(new Exception(response.message()));
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void getLeaderBordToday(String phone, String reqforleaderbord, final ResponseCallback<ArrayList<Driver>> getDriversList) {
        RetrofitApiInterface retrofitApiInterface   = RetrofitApiClient.getClient().create(RetrofitApiInterface.class);
        Call<ArrayList<Driver>> call = retrofitApiInterface.getLeaderBordToday(phone,reqforleaderbord);
        call.enqueue(new Callback<ArrayList<Driver>>() {
            @Override
            public void onResponse(Call<ArrayList<Driver>> call, Response<ArrayList<Driver>> response) {
                ArrayList<Driver> driverArrayList=response.body();
                if(driverArrayList!=null){
                    getDriversList.onSuccess(driverArrayList);
                }else {
                    getDriversList.onError(new Exception(response.message()));
                }

            }

            @Override
            public void onFailure(Call<ArrayList<Driver>> call, Throwable t) {

            }
        });

    }


}
