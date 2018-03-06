package com.swayam.sqliteroomsample;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

public class UserDetailsModel implements Parcelable {
  /*"email": "akhilbv1@gmail.com",
      "mobileNum": "8341770556",
      "password": "1234567",
      "username": "akhil"*/

  @SerializedName("email")
  private String emailid;

  @SerializedName("mobileNum")
 private String mobilenumber;

  @SerializedName("password")
 private String password;

  @SerializedName("userName")
 private String username;

 public String getEmailid() {
  return emailid;
 }

 public void setEmailid(String emailid) {
  this.emailid = emailid;
 }

 public String getMobilenumber() {
  return mobilenumber;
 }

 public void setMobilenumber(String mobilenumber) {
  this.mobilenumber = mobilenumber;
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

 @Override public int describeContents() {
  return 0;
 }

 @Override public void writeToParcel(Parcel dest, int flags) {
  dest.writeString(this.emailid);
  dest.writeString(this.mobilenumber);
  dest.writeString(this.password);
  dest.writeString(this.username);
 }

 public UserDetailsModel() {
 }

 protected UserDetailsModel(Parcel in) {
  this.emailid = in.readString();
  this.mobilenumber = in.readString();
  this.password = in.readString();
  this.username = in.readString();
 }

 public static final Parcelable.Creator<UserDetailsModel> CREATOR =
     new Parcelable.Creator<UserDetailsModel>() {
      @Override public UserDetailsModel createFromParcel(Parcel source) {
       return new UserDetailsModel(source);
      }

      @Override public UserDetailsModel[] newArray(int size) {
       return new UserDetailsModel[size];
      }
     };
}
