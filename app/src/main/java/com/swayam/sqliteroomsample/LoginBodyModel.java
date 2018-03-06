package com.swayam.sqliteroomsample;

import com.google.gson.annotations.SerializedName;

/**
 Created by akhil on 1/3/18.
 */

public class LoginBodyModel {

 /*"email":"akhil@gmail.com",
     "password":"123456",
     "returnSecureToken":true*/

 @SerializedName("email")
  public String email;

 @SerializedName("password")
  public String password;

 @SerializedName("returnSecureToken")
  public boolean returnSecureToken;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isReturnSecureToken() {
    return returnSecureToken;
  }

  public void setReturnSecureToken(boolean returnSecureToken) {
    this.returnSecureToken = returnSecureToken;
  }
}
