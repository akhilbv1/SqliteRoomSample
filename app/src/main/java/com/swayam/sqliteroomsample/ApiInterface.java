package com.swayam.sqliteroomsample;

import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 Created by akhil on 1/3/18.
 */

public interface ApiInterface {


  @POST("verifyPassword?key=AIzaSyCden39_px6Pjc6EYEO8GNcMpv-SY7RJqI")
  Call<LoginResponse> loginUser(@Body LoginBodyModel user);

  @GET("{uid}")
  Call<UserDetailsModel> getUserDetails(@Path("uid") String uid);

  @POST("setAccountInfo?key=AIzaSyCden39_px6Pjc6EYEO8GNcMpv-SY7RJqI")
  Call<Void> updatePassword(@Body ChangePasswordAuthModel ChangePasswordAuthModel);

  @PATCH("{uid}")
  Call<Void> updatePassword(@Path("uid") String uid,@Body ChangePasswordModel changePassword);

  @POST("token?key=AIzaSyCden39_px6Pjc6EYEO8GNcMpv-SY7RJqI")
  Call<RefreshTokenResponseModel> refreshToken(@Body RefreshTokenBodyModel refreshTokenBodyModel);

  @GET("users.json")
  Call<AllUserDetailsKeyModel> getALlUserDetails();


  @PATCH("{uid}")
  Call<Void> updateUserDetails(@Path("uid") String uid,@Body ChangeUserDetailsBodyModel changeUserDetailsBodyModel);
}
