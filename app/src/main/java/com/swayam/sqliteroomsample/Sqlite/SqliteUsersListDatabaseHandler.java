package com.swayam.sqliteroomsample.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.swayam.sqliteroomsample.Dao.User;

import java.util.ArrayList;
import java.util.List;

/*Created by akhil on 15/2/18.
 */

/*
 *sqlite database helper to create and perform database oeprations
 */

public class SqliteUsersListDatabaseHandler extends SQLiteOpenHelper implements User {

    private static final int Database_Version = 5;

    private static final String Database = "sqliteusers";

    private static final String Table = "swayamusers";

    private static final String Key_id = "id";

    private static final String Key_Email = "email";

    private static final String Key_Username = "username";

    private static final String Key_Mobile = "mobile";

    private static final String Key_Password = "password";

    public SqliteUsersListDatabaseHandler(Context context) {
        super(context, Database, null, Database_Version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + Table + "("
                + Key_id + " INTEGER PRIMARY KEY," + Key_Username + " VARCHAR,"
                + Key_Email + " VARCHAR," + Key_Mobile + " VARCHAR, "+ Key_Password + " VARCHAR " + ")";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Table);
        onCreate(db);
    }


    /*
     *it adds the user to the database i.e register
     * @param sqliteUserPojo
     * @return
     */
   @Override
    public long adduser(SqliteUserPojo sqliteUserPojo){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Key_Username,sqliteUserPojo.getUsername());
        values.put(Key_Email,sqliteUserPojo.getEmailid());
        values.put(Key_Mobile,sqliteUserPojo.getMobileNum());
        values.put(Key_Password,sqliteUserPojo.getPassword());

       return  db.insert(Table, null, values);
    }

    /**
     * it checks user for credentials in login
     * @param email
     * @return
     */
    @Override
    public SqliteUserPojo checkUser(String email){

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(Table, new String[] { Key_id,Key_Email,Key_Password },
                Key_Email + "=?", new String[] { email },null,null,null,null);
        if (cursor != null){
        cursor.moveToFirst();
            SqliteUserPojo  sqliteUserPojo = new SqliteUserPojo(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        return sqliteUserPojo;}
        else return null;
    }

    /**
     * it returns the list of all users
     * @return
     */
    @Override
    public List<SqliteUserPojo> getAllusers(){
        List<SqliteUserPojo> userList = new ArrayList<SqliteUserPojo>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + Table;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SqliteUserPojo sqliteUserPojo = new SqliteUserPojo();
                sqliteUserPojo.setId(Integer.parseInt(cursor.getString(0)));
                sqliteUserPojo.setUsername(cursor.getString(1));
                sqliteUserPojo.setEmailid(cursor.getString(2));
                sqliteUserPojo.setMobileNum(cursor.getString(3));
                sqliteUserPojo.setPassword(cursor.getString(4));

                userList.add(sqliteUserPojo);
            } while (cursor.moveToNext());
        }

        return userList;
    }
}
