package com.swayam.sqliteroomsample.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**Created by akhil on 15/2/18.
 */

@Entity(tableName = "users")
public class EntityUserUpdatePasswordPojo {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ColumnInfo(name = "email")

    private String Emailid;

    @ColumnInfo(name = "password")
    private String Password;

    public String getEmailid() {
        return Emailid;
    }

    public void setEmailid(String emailid) {
        Emailid = emailid;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
