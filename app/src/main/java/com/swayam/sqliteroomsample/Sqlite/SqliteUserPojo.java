package com.swayam.sqliteroomsample.Sqlite;

/*Created by akhil on 15/2/18.*/

/*
 *userpojo to user insert into sqlite database using sqlite api not room.
 */

public class SqliteUserPojo {

    private int id;

    private String Username,Emailid,MobileNum,Password;

    public SqliteUserPojo(){}

    public SqliteUserPojo(int id, String username, String email,String mobilenum,String password){
        this.id = id;
        this.Username = username;
        this.Emailid = email;
        this.MobileNum = mobilenum;
        this.Password = password;
    }

    public SqliteUserPojo(int id, String email,String password){
        this.id = id;
        this.Emailid = email;
        this.Password = password;
    }


    public SqliteUserPojo(String username, String email,String mobilenum,String password){
        this.Username = username;
        this.Emailid = email;
        this.MobileNum = mobilenum;
        this.Password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
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

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
