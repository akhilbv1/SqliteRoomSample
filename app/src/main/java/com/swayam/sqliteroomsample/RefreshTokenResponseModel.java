package com.swayam.sqliteroomsample;

import com.google.gson.annotations.SerializedName;

/**
 Created by akhil on 5/3/18.
 */

public class RefreshTokenResponseModel {
/*
  {
    "expires_in": "3600",
      "token_type": "Bearer",
      "refresh_token": "[REFRESH_TOKEN]",
      "id_token": "[ID_TOKEN]",
      "user_id": "tRcfmLH7o2XrNELi...",
      "project_id": "1234567890"
  }*/

  @SerializedName("expires_in")
  private String tokenExpireTime;

  @SerializedName("token_type")
  private String tokenType;

  @SerializedName("id_token")
  private String tokenId;

  @SerializedName("refresh_token")
  private String refreshToken;

  public String getRefreshToken() {
    return refreshToken;
  }

  public void setRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

  @SerializedName("user_id")
  private String userId;

  @SerializedName("project_id")
  private String projectId;

  public String getTokenExpireTime() {
    return tokenExpireTime;
  }

  public void setTokenExpireTime(String tokenExpireTime) {
    this.tokenExpireTime = tokenExpireTime;
  }

  public String getTokenType() {
    return tokenType;
  }

  public void setTokenType(String tokenType) {
    this.tokenType = tokenType;
  }

  public String getTokenId() {
    return tokenId;
  }

  public void setTokenId(String tokenId) {
    this.tokenId = tokenId;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getProjectId() {
    return projectId;
  }

  public void setProjectId(String projectId) {
    this.projectId = projectId;
  }
}
