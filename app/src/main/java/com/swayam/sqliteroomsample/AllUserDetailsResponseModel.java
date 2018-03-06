package com.swayam.sqliteroomsample;

import com.google.gson.annotations.SerializedName;

/**Created by akhil on 5/3/18.
 */

public class AllUserDetailsResponseModel {

 /* "email": "akhilbv1@gmail.com",
      "mobileNum": "8341770556",
      "password": "1234567",
      "username": "akhil"*/

 @SerializedName("email")
  private String email;

 @SerializedName("mobileNum")
  private String mobilenum;

 @SerializedName("password")
  private String password;

 @SerializedName("userName")
  private String username;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobilenum() {
    return mobilenum;
  }

  public void setMobilenum(String mobilenum) {
    this.mobilenum = mobilenum;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
