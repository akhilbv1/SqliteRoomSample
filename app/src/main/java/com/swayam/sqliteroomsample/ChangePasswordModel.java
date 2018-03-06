package com.swayam.sqliteroomsample;

import com.google.gson.annotations.SerializedName;

/**
 Created by akhil on 5/3/18.
 */

public class ChangePasswordModel {
 // {"mobileNum":"8341770556"}

  @SerializedName("password")
  public String password;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
