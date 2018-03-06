package com.swayam.sqliteroomsample.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.swayam.sqliteroomsample.ChangeUserDetailsBodyModel;
import com.swayam.sqliteroomsample.RestClient;
import com.swayam.sqliteroomsample.RoomDatabase.AppDatabase;
import com.swayam.sqliteroomsample.UserDetailsModel;
import com.swayam.sqliteroomsample.Utils.CommonUtils;
import com.swayam.sqliteroomsample.R;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * activity is used to update user details.
 * respective layout:-firebase_editinfo_activity
 */

/*Created by akhil on 7/2/18*/

public class SqliteRoomEditInfoActivity extends AppCompatActivity {
    EditText etUserName,etEmail,etMobileNum;
    private ProgressDialog dialog;
   AppDatabase appDatabase;
    String userid,email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firebase_editinfo_activity);
        etUserName = findViewById(R.id.etUserName);
        CommonUtils.addFields(etUserName);
        etEmail = findViewById(R.id.etEmail);
        etMobileNum = findViewById(R.id.etMobileNum);
        CommonUtils.addFields(etMobileNum);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.toolbar_title_edit_user_details);
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.dialog_loading));
         userid = getIntent().getStringExtra("userid");
         email = getIntent().getStringExtra("email");
         etEmail.setText(email);
         etUserName.setText(getIntent().getStringExtra("username"));
         etMobileNum.setText(getIntent().getStringExtra("mobile"));
         Log.i("user",""+getIntent().getParcelableExtra("user"));
        Log.i("userid",""+getIntent().getStringExtra("userid"));
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_savepassword,menu);
        return true;
    }

    /**
     * onmenuitems clicked:-
     * save:-updates data to database
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       /* switch (item.getItemId()){

            case R.id.ad_container:

                return true;
        }*/

        if(item.getItemId()==R.id.menusave){
           validateFields();
           return true;
        }
        else if(item.getItemId()==android.R.id.home)
        {
            finish();
            return true;
        }
        else
        return super.onOptionsItemSelected(item);
    }

    /**
     * validates all the fields before inserting into database
     */
    private void validateFields(){
        if(TextUtils.isEmpty(etUserName.getText().toString().trim())){
            CommonUtils.toastMessage(R.string.empty_username,this);
        }
        else if(TextUtils.isEmpty(etMobileNum.getText().toString().trim())){
            CommonUtils.toastMessage(R.string.empty_mobile_num,this);
        }
        else if(etMobileNum.getText().toString().trim().length() < 10){
            CommonUtils.toastMessage(R.string.validation_mobilenum,this);
        }
        else {
          updateUserDetailsRetrofit();
                //new AsyncTask().execute();
        }
    }


    private void updateUserDetailsRetrofit(){
      RestClient restClient = new RestClient();
      ChangeUserDetailsBodyModel changeUserDetailsBody = new ChangeUserDetailsBodyModel();
      changeUserDetailsBody.setUsername(etUserName.getText().toString().trim());
      changeUserDetailsBody.setMobileNumber(etMobileNum.getText().toString().trim());
      String userId = userid+".json";
      retrofit2.Call<Void> changeUserDetailsCall = restClient.getUserApiService().updateUserDetails(userId,changeUserDetailsBody);
      changeUserDetailsCall.enqueue(new Callback<Void>() {
        @Override public void onResponse(retrofit2.Call<Void> call, Response<Void> response) {
          if(response.code()==200){
            CommonUtils.toastMessage("update success",SqliteRoomEditInfoActivity.this);
          }
        }

        @Override public void onFailure(retrofit2.Call<Void> call, Throwable t) {
               Log.i("failed",""+t.getLocalizedMessage());
        }
      });
    }
    /**
     * Class Asynctask is used to run database operations on the background and not on the main ui thread
     */
    private class AsyncTask extends android.os.AsyncTask<Void,Void,Void>
    {
          int count;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            appDatabase= AppDatabase.getAppDatabase(SqliteRoomEditInfoActivity.this);
            count = appDatabase.userDao().updateUserDetails(etUserName.getText().toString().trim(),
                                                            etMobileNum.getText().toString().trim(),
                                                            userid);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.cancel();
            Log.i("count",""+count);
            if(count==1){
                CommonUtils.toastMessage(R.string.message_udpdate_username_mobile_success,SqliteRoomEditInfoActivity.this);
            }
            else {
                CommonUtils.toastMessage(R.string.message_udpdate_username_mobile_failure,SqliteRoomEditInfoActivity.this);
            }
        }
    }


    }