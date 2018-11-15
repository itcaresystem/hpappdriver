package ride.happyy.driver.net.parsers;

import org.json.JSONException;
import org.json.JSONObject;

import ride.happyy.driver.app.App;
import ride.happyy.driver.model.ProfileBean;



public class ProfileParser {

    private static final String TAG = "ProfileParser";

    public ProfileBean parseProfileResponse(String wsResponseString) {

        ProfileBean profileBean = new ProfileBean();

        JSONObject jsonObj = null;

        try {
            jsonObj = new JSONObject(wsResponseString);


            if (jsonObj.has("error")) {
                JSONObject errorJSObj;
                try {
                    errorJSObj = jsonObj.getJSONObject("error");
                    if (errorJSObj != null) {
                        if (errorJSObj.has("message")) {
                            profileBean.setErrorMsg(errorJSObj.optString("message"));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                profileBean.setStatus("error");
            }
            if (jsonObj.has("status")) {
                profileBean.setStatus(jsonObj.optString("status"));
                if (jsonObj.optString("status").equals("error")) {
                    if (jsonObj.has("message")) {
                        profileBean.setErrorMsg(jsonObj.optString("message"));
                    } else {
                        profileBean.setErrorMsg("Something Went Wrong. Please Try Again Later!!!");
                    }
                }
                if (jsonObj.optString("status").equals("500")) {
                    if (jsonObj.has("error")) {
                        profileBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.optString("status").equals("404")) {
                    if (jsonObj.has("error")) {
                        profileBean.setErrorMsg(jsonObj.optString("error"));
                    }
                }
                if (jsonObj.has("message")) {
                    profileBean.setErrorMsg(jsonObj.optString("message"));
                }
                if (jsonObj.optString("status").equals("updation success")) {
                    profileBean.setStatus("success");
                }
            }
            try {
                if (jsonObj.has("message")) {
                    profileBean.setWebMessage(jsonObj.optString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (jsonObj.has("error")) {
                profileBean.setError(jsonObj.optString("error"));
            }
            if (jsonObj.has("response")) {
                profileBean.setErrorMsg(jsonObj.optString("response"));
            }
            if (jsonObj.has("message")) {
                profileBean.setErrorMsg(jsonObj.optString("message"));
            }


 /*           if (jsonObj.has("id") && jsonObj.has("auth_token") && jsonObj.has("number") && jsonObj.has("username")
                    && jsonObj.has("first_name") && jsonObj.has("last_name") && jsonObj.has("email") && jsonObj.has("city")
                    && jsonObj.has("state") && jsonObj.has("country")) {
                profileBean.setLocationStatus("success");
            }*/

            if (jsonObj.has("data")) {
                JSONObject dataObj = jsonObj.optJSONObject("data");
                if (dataObj != null) {
                    try {
                        if (dataObj.has("auth_token")) {
                            profileBean.setAuthToken(dataObj.optString("auth_token"));
                        }
                               /* if (dataObj.has("username")) {
                                    profileBean.setUserName(dataObj.optString("username"));
                                }*/
                        if (dataObj.has("id")) {
                            profileBean.setId(dataObj.optString("id"));
                        }
                        if (dataObj.has("profile_photo")) {
                            profileBean.setProfilePhoto(App.getImagePath(dataObj.optString("profile_photo")));
                        }
                        if (dataObj.has("name")) {
                            profileBean.setName(dataObj.optString("name"));
                        }
                        if (dataObj.has("first_name")) {
                            profileBean.setFirstName(dataObj.optString("first_name"));
                        }
                        if (dataObj.has("last_name")) {
                            profileBean.setLastName(dataObj.optString("last_name"));
                        }
                        if (dataObj.has("address")) {
                            profileBean.setAddress(dataObj.optString("address"));
                        }
                        if (dataObj.has("gender")) {
                            profileBean.setGender(dataObj.optString("gender"));
                        }
                        if (dataObj.has("DOB")) {
                            profileBean.setDOB(dataObj.optString("DOB"));
                        }
                               /* if (dataObj.has("religion")) {
                                    profileBean.setReligion(dataObj.optString("religion"));
                                }
                                if (dataObj.has("mother_tongue")) {
                                    profileBean.setMotherTongue(dataObj.optString("mother_tongue"));
                                }*/
                        if (dataObj.has("phone")) {
                            profileBean.setPhone(dataObj.optString("phone"));
                        }
                        if (dataObj.has("email")) {
                            profileBean.setEmail(dataObj.optString("email"));
                        }
                            /*    if (dataObj.has("marital_status")) {
                                    profileBean.setMaritalStatus(dataObj.optString("marital_status"));
                                }*/
                        if (dataObj.has("country")) {
                            profileBean.setCountry(dataObj.optString("country"));
                        }
                        if (dataObj.has("state")) {
                            profileBean.setState(dataObj.optString("state"));
                        }
                        if (dataObj.has("city")) {
                            profileBean.setCity(dataObj.optString("city"));
                        }
                        if (dataObj.has("postal_code")) {
                            profileBean.setPostalCode(dataObj.optString("postal_code"));
                        }
                        if (dataObj.has("is_phone_verified")) {
                            profileBean.setPhoneVerified(dataObj.optBoolean("is_phone_verified"));
                        }

                        if (dataObj.has("vehicle_type")) {
                            profileBean.setVehicle_type(dataObj.optString("vehicle_type"));
                        }

                        if (dataObj.has("vehicle_no")) {
                            profileBean.setVehicle_no(dataObj.optString("vehicle_no"));
                        }
                        if (dataObj.has("car_brand")) {
                            profileBean.setCarBrand(dataObj.optString("car_brand"));
                        }

                        if (dataObj.has("car_fitness_certificate")) {
                            profileBean.setCarFitnessCertificateNo(dataObj.optString("car_fitness_certificate"));
                        }

                        if (dataObj.has("is_active")) {
                            profileBean.set_active(dataObj.optString("is_active"));
                        }

                        if (dataObj.has("total_trips")) {
                            profileBean.setTotal_trips(dataObj.optInt("total_trips"));
                        }

                        if (dataObj.has("total_earn")) {
                            profileBean.setTotal_earn(dataObj.optInt("total_earn"));
                        }
                        if (dataObj.has("total_due")) {
                            profileBean.setTotal_due(dataObj.optInt("total_due"));
                        }
                        if (dataObj.has("ref_bonus")) {
                            profileBean.setRef_bonus(dataObj.optInt("ref_bonus"));
                        }

                        if (dataObj.has("driver_rating")) {
                            profileBean.setDriver_rating(dataObj.optInt("driver_rating"));
                        }
                        if (dataObj.has("commission_rate")) {
                            profileBean.setCommission_rate(dataObj.optInt("commission_rate"));
                        }




                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        return profileBean;
    }
}
