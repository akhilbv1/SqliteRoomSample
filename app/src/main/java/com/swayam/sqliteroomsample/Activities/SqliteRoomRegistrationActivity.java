package com.swayam.sqliteroomsample.Activities;

/*
 *this activity registers the user.
  * repective layout:-sqlite_room_registration_activity
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.swayam.sqliteroomsample.RoomDatabase.AppDatabase;
import com.swayam.sqliteroomsample.Utils.CommonUtils;
import com.swayam.sqliteroomsample.Entities.EntityUserPojo;
import com.swayam.sqliteroomsample.R;
import com.swayam.sqliteroomsample.Sqlite.SqliteUserPojo;
import com.swayam.sqliteroomsample.Sqlite.SqliteUsersListDatabaseHandler;

public class SqliteRoomRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText etUsername, etEmail, etMobileNum, etPassword, etConfPassword;
    TextView txtExistingAccount;
    Toolbar toolbar;
    ProgressDialog dialog;
    private AppDatabase appDatabase;
    Button btnSubmit;
    SqliteUsersListDatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlite_room_registration_activity);

        etUsername = findViewById(R.id.etUserName);
        etEmail = findViewById(R.id.etEmail);
        etMobileNum = findViewById(R.id.etMobileNum);
        etPassword = findViewById(R.id.etPassword);
        etConfPassword = findViewById(R.id.etConfPassword);

        txtExistingAccount = findViewById(R.id.txtExistingAccount);
        txtExistingAccount.setOnClickListener(this);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_title_Registration);
        //toolbar.setNavigationIcon(R.drawable.a);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        appDatabase = AppDatabase.getAppDatabase(this);

        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(this);

        dialog = new ProgressDialog(this);
        dialog.setMessage("loading");

        databaseHandler = new SqliteUsersListDatabaseHandler(this);
    }

    /**
     * onclick of
     * submit:inserts data into database
     * existing account:-open login screen
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSubmit:;
                registerByValidation();
                          break;

            case R.id.txtExistingAccount:
                Intent intent = new Intent(this, SqliteRoomLoginActivity.class);
                startActivity(intent);                break;
        }
    }

    /**
     * registerbyvalidation first validates the entire registration screen and the calls Asyntask
     * to perform database operations on the background.
     */
    private void registerByValidation() {

        if (TextUtils.isEmpty(etUsername.getText().toString().trim())) {
            CommonUtils.toastMessage(R.string.empty_username, this);
        } else if (TextUtils.isEmpty(etEmail.getText().toString().trim())) {
            CommonUtils.toastMessage(R.string.empty_email, this);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.getText().toString().trim()).matches()) {
            CommonUtils.toastMessage(R.string.validation_email, this);
        } else if (TextUtils.isEmpty(etMobileNum.getText().toString().trim())) {
            CommonUtils.toastMessage(R.string.empty_mobile_num, this);
        } else if (etMobileNum.getText().toString().trim().length() < 10) {
            CommonUtils.toastMessage(R.string.validation_mobilenum, this);
        } else if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
            CommonUtils.toastMessage(R.string.empty_password, this);
        } else if (etPassword.getText().toString().trim().length() < 6) {
            CommonUtils.toastMessage(R.string.validation_password, this);

        } else if (TextUtils.isEmpty(etConfPassword.getText().toString().trim())) {
            CommonUtils.toastMessage(R.string.check_passwords, this);

        } else if (!etPassword.getText().toString().trim().equals(etConfPassword.getText().toString().trim())) {
            CommonUtils.toastMessage(R.string.check_passwords, this);

        } else {
           new DatabaseSync().execute();
            //registerUser();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return  true;
        }
        else
        return super.onOptionsItemSelected(item);
    }

    /**
     * registers user using sqlite API not room
     */
    private void registerUser(){
        SqliteUserPojo sqliteUserPojo = new SqliteUserPojo();

        sqliteUserPojo.setUsername(etUsername.getText().toString().trim());
        sqliteUserPojo.setEmailid(etEmail.getText().toString().trim());
        sqliteUserPojo.setMobileNum(etMobileNum.getText().toString().trim());
        sqliteUserPojo.setPassword(etPassword.getText().toString().trim());

        databaseHandler.adduser(sqliteUserPojo);

        clearForm();
    }


    /**
     * Class Asynctask is used to run database operations on the background and not on the main ui thread
     */
    private class DatabaseSync extends AsyncTask<Void, Void, Void> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            EntityUserPojo entityUser = new EntityUserPojo();
            entityUser.setUsername(etUsername.getText().toString().trim());
            entityUser.setEmailid(etEmail.getText().toString().trim());
            entityUser.setMobileNum(etMobileNum.getText().toString().trim());
            entityUser.setPassword(etPassword.getText().toString().trim());
            entityUser.setUser_id(CommonUtils.generateUID());

            appDatabase.userDao().addUser(entityUser);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            clearForm();
            CommonUtils.toastMessage(R.string.user_reigistered_success,SqliteRoomRegistrationActivity.this);
            Intent intent = new Intent(SqliteRoomRegistrationActivity.this,SqliteRoomLoginActivity.class);
            startActivity(intent);
            dialog.cancel();

            super.onPostExecute(aVoid);
        }
    }

    /*
     *it clears all the edit texts
      */
    private void clearForm(){
        etUsername.setText("");
        etEmail.setText("");
        etPassword.setText("");
        etConfPassword.setText("");
        etMobileNum.setText("");
    }

}
