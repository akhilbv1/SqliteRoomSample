package com.swayam.sqliteroomsample;

import com.google.gson.annotations.SerializedName;

/**
 Created by akhil on 5/3/18.
 */

public class ChangeUserDetailsBodyModel {
  /*{"userName":"B.v.akhil",
      "mobileNum":"8341770556"}*/

  @SerializedName("userName")
  private String username;

  @SerializedName("mobileNum")
  private String mobileNumber;

  public String getUsername() {
    return username;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }
}
