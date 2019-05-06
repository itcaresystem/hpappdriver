package ride.happyy.driver.net;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ride.happyy.driver.model.CurentLocation;
import ride.happyy.driver.model.Driver;
import ride.happyy.driver.model.DriverList;
import ride.happyy.driver.model.MyNotification;
import ride.happyy.driver.model.OutOfDhakaServiceModel;
import ride.happyy.driver.model.RequestTransferData;
import ride.happyy.driver.model.ServerResponse;
import ride.happyy.driver.model.User;
import ride.happyy.driver.model.VehicleInfo;


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
    @Override
    public void getLeaderBordTodayTrips(String phone, String reqforleaderbord, final ResponseCallback<ArrayList<Driver>> getDriversList) {
        RetrofitApiInterface retrofitApiInterface   = RetrofitApiClient.getClient().create(RetrofitApiInterface.class);
        Call<ArrayList<Driver>> call = retrofitApiInterface.getLeaderBordTodayTrips(phone,reqforleaderbord);
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

    @Override
    public void request_transfer(RequestTransferData postData, final ResponseCallback<ServerResponse> respnseCallBack) {
        RetrofitApiInterface retrofitApiInterface   = RetrofitApiClient.getClient().create(RetrofitApiInterface.class);
        Call<ServerResponse> call = retrofitApiInterface.requestTransferToOthers(postData);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse validity= response.body();
                if (validity!=null){
                    respnseCallBack.onSuccess(validity);
                }else {
                    respnseCallBack.onError(new Exception(response.message()));
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                respnseCallBack.onError(new Exception(t));

            }
        });
    }

    @Override
    public void updateDriverCurrentLocation(CurentLocation curentLocation, final ResponseCallback<ServerResponse> responseCallback) {
        RetrofitApiInterface retrofitApiInterface=RetrofitApiClient.getClient().create(RetrofitApiInterface.class);
        Call<ServerResponse> call =retrofitApiInterface.updateDriverLocation(curentLocation);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                responseCallback.onSuccess(serverResponse);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                responseCallback.onError(new Exception(t));
            }
        });
    }

    @Override
    public void updateDriverVehicleInfo(VehicleInfo vehicleInfo, final ResponseCallback<ServerResponse> responseCallback) {
        RetrofitApiInterface retrofitApiInterface=RetrofitApiClient.getClient().create(RetrofitApiInterface.class);
        Call<ServerResponse> call =retrofitApiInterface.updateDriverVehicleInfo(vehicleInfo);
        call.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, Response<ServerResponse> response) {
                ServerResponse serverResponse=response.body();
                responseCallback.onSuccess(serverResponse);

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                responseCallback.onError(new Exception(t));

            }
        });

    }

    @Override
    public void getMyAllMessage(String phone, final ResponseCallback<ArrayList<MyNotification>> responseCallback) {
        RetrofitApiInterface retrofitApiInterface = RetrofitApiClient.getClient().create(RetrofitApiInterface.class);
        Call<ArrayList<MyNotification>> call = retrofitApiInterface.getMyAllMessage(phone);
        call.enqueue(new Callback<ArrayList<MyNotification>>() {
            @Override
            public void onResponse(Call<ArrayList<MyNotification>> call, Response<ArrayList<MyNotification>> response) {
                ArrayList<MyNotification> myNotifications =response.body();
                if(myNotifications!=null){
                    responseCallback.onSuccess(myNotifications);

                }else {
                    responseCallback.onError(new Exception(response.message()));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<MyNotification>> call, Throwable t) {

            }
        });
    }


}
