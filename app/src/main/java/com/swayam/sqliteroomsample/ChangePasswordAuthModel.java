package com.swayam.sqliteroomsample;

import com.google.gson.annotations.SerializedName;

/**
 Created by akhil on 5/3/18.
 */

public class ChangePasswordAuthModel {

  /*{"idToken":"eyJhbGciOiJSUzI1NiIsImtpZCI6IjIzZTIyYTQ3NjZmYjYyNzMxN2ZjOGRjN2M5YjRkM2M3ZmY4YTg1NjcifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vc2FtcGxlLWQ4OWY4IiwiYXVkIjoic2FtcGxlLWQ4OWY4IiwiYXV0aF90aW1lIjoxNTIwMjMwMjEzLCJ1c2VyX2lkIjoiQkVHREhHMGw4aFJRQTQ0R0gyTzNyU1pJcHpZMiIsInN1YiI6IkJFR0RIRzBsOGhSUUE0NEdIMk8zclNaSXB6WTIiLCJpYXQiOjE1MjAyMzAyMTMsImV4cCI6MTUyMDIzMzgxMywiZW1haWwiOiJha2hpbEBnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsImZpcmViYXNlIjp7ImlkZW50aXRpZXMiOnsiZW1haWwiOlsiYWtoaWxAZ21haWwuY29tIl19LCJzaWduX2luX3Byb3ZpZGVyIjoicGFzc3dvcmQifX0.jtYSUqRU42zkUY49WIASvsjBw0ZWwqcvN54_H4W-TWCAaa-fsGh3ezQ1xuVZzOO9YykrvSzhhnyfBG-MoqSJswUGprdwxLtMg1uivIwylQaAeQs24Hwu22byZk8m1DdwV5-WwXP9rSZ__205JbIwu8H4xi-_rGhs-FMXhQ7TMGLWY0Ke5NURaqGAG7Uj6JSHsrdQTbiLnAUMpbIDeSW8bTr4WsRk2Yix0t9R0jRwC7UDMwIuyUy25lJ9wWSA13criT7vxn4aHeGcv2OKzzx1X1SpvJNhg2ZLNAJDRaCMGIOjhYMKxGC6ClJqfdkAuAj5gNJg9ir3xiNDhOB5UdYmpQ",
      "password":"1234567",
      "returnSecureToken":true}*/

  @SerializedName("idToken")
  private String Idtoken;

  @SerializedName("password")
  private String password;

  @SerializedName("returnSecureToken")
  private boolean returnSecureToken;

  public void setIdtoken(String idtoken) {
    Idtoken = idtoken;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setReturnSecureToken(boolean returnSecureToken) {
    this.returnSecureToken = returnSecureToken;
  }
}
