package com.swayam.sqliteroomsample;

import com.google.gson.annotations.SerializedName;

/**
 Created by akhil on 5/3/18.
 */

public class RefreshTokenBodyModel {

 // 'grant_type=refresh_token&refresh_token=[REFRESH_TOKEN]'

  @SerializedName("grant_type")
  private String grant_type;

  @SerializedName("refresh_token")
  private String refreshToken;

  public void setGrant_type(String grant_type) {
    this.grant_type = grant_type;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }
}
