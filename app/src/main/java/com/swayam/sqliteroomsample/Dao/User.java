package com.swayam.sqliteroomsample.Dao;

/*Created by akhil on 19/2/18.
 */

import com.swayam.sqliteroomsample.Sqlite.SqliteUserPojo;

import java.util.List;

public interface User {

  long adduser(SqliteUserPojo sqliteUserPojo);

  SqliteUserPojo checkUser(String email);

  List<SqliteUserPojo> getAllusers();

}
