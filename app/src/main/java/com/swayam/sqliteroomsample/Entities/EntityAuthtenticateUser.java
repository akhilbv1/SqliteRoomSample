package com.swayam.sqliteroomsample.Entities;

/*Created by akhil on 14/2/18.
 */

/*
 *this entity represents table user which returns only emailid,password,userid
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "users")
public class EntityAuthtenticateUser {

    @NonNull
    @ColumnInfo(name = "email")
    private String email;

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    private String userid;


    @ColumnInfo(name = "password")
    private String password;

    @NonNull
    public String getUserid() {
        return userid;
    }

    public void setUserid(@NonNull String userid) {
        this.userid = userid;
    }

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
}
