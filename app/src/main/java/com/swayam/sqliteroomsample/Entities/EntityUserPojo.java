package com.swayam.sqliteroomsample.Entities;

/*Created by akhil on 13/2/18.*/
/*
 *it represents user table.
 */

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "users")
public class EntityUserPojo {

    /*@PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;*/

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "user_id")
    private String user_id;

    @NonNull
    @ColumnInfo(name = "username")
    private String Username;

    @NonNull
    @ColumnInfo(name = "email")
    private String Emailid;

    @NonNull
    @ColumnInfo(name = "mobile")
    private String MobileNum;

    @NonNull
    @ColumnInfo(name = "password")
    private String Password;


   /* public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
*/

    @NonNull
    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(@NonNull String user_id) {
        this.user_id = user_id;
    }

    public String getEmailid() {
        return Emailid;
    }

    public void setEmailid(String emailid) {
        Emailid = emailid;
    }

    public String getMobileNum() {
        return MobileNum;
    }

    public void setMobileNum(String mobileNum) {
        MobileNum = mobileNum;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
