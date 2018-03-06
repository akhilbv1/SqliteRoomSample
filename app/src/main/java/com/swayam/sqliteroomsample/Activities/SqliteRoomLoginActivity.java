package com.swayam.sqliteroomsample.Activities;

/*Created by akhil on 29/1/18.*/

/*
 * login activity of the user
 * respective layout:-sqlite_room_login_activity
 */

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.swayam.sqliteroomsample.ApiInterface;
import com.swayam.sqliteroomsample.LoginBodyModel;
import com.swayam.sqliteroomsample.LoginResponse;
import com.swayam.sqliteroomsample.RefreshTokenBodyModel;
import com.swayam.sqliteroomsample.RestClient;
import com.swayam.sqliteroomsample.RoomDatabase.AppDatabase;
import com.swayam.sqliteroomsample.Utils.CommonUtils;
import com.swayam.sqliteroomsample.Entities.EntityAuthtenticateUser;
import com.swayam.sqliteroomsample.R;
import com.swayam.sqliteroomsample.Sqlite.SqliteUsersListDatabaseHandler;
import es.dmoral.toasty.Toasty;
import retrofit2.Callback;
import retrofit2.Response;

public class SqliteRoomLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText  etEmail,etPassword, alrtEmail;
    ProgressDialog dialog;
    Button Submit;
    AlertDialog.Builder alertDialogBuilder;
    AlertDialog alertDialog;
    AppDatabase appDatabase;
    String userid;
    SqliteUsersListDatabaseHandler databaseHandler;
    String refreshToken;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlite_room_login_activity);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);

        TextView txtNewUser = findViewById(R.id.txtNewUser);
        txtNewUser.setOnClickListener(this);

        TextView txtForgotPass = findViewById(R.id.txtForgotPass);
        txtForgotPass.setOnClickListener(this);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_title_login);
        setSupportActionBar(toolbar);

        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.dialog_loading));

        alertDialogBuilder = new AlertDialog.Builder(this);

        appDatabase = AppDatabase.getAppDatabase(this);

        databaseHandler = new SqliteUsersListDatabaseHandler(this);

        CommonUtils.savePreferences(this);

        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.dialog_loading));

    }

    /**
     * on click of buttons:login
     * Textview:newuser,forgot password
     * button:submit for the dialog
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:
                sqliteRoomLogin();
                break;

            case R.id.txtNewUser:
                Intent intent = new Intent(this, SqliteRoomRegistrationActivity.class);
                startActivity(intent);
                break;

            case R.id.txtForgotPass:
                Log.i("otp",""+CommonUtils.randomString(8));
                loadDialog();
                break;

            case R.id.btnSubmit:
                validateEmail();
                break;
        }
    }

    /**
     * it validates email for the forgot password dialog
     */
    private void validateEmail() {
        if (TextUtils.isEmpty(alrtEmail.getText().toString().trim())) {
            CommonUtils.toastMessage(R.string.empty_email,this);
        } else if (!Patterns.EMAIL_ADDRESS.matcher(alrtEmail.getText().toString().trim()).matches()) {
            CommonUtils.toastMessage(R.string.validation_email,this);
        } else {
            emailClient();
        }
    }

   /*
   all the email clients are opened to send recovery email
    */
    private void emailClient() {
        String body = getString(R.string.message_reset_password) + CommonUtils.randomString(8);
        Intent Email = new Intent(Intent.ACTION_SEND);
        Email.putExtra(Intent.EXTRA_EMAIL, new String[]{alrtEmail.getText().toString().trim()});
        Email.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.email_subject));
        Email.putExtra(Intent.EXTRA_TEXT, body);
        Email.setType("message/rfc822");
        startActivity(Intent.createChooser(Email, getString(R.string.choose_intent_email)));
        alertDialog.cancel();
    }


    /**
     * it logins user using async task which runs in background
     */

    private void sqliteRoomLogin() {
        if (TextUtils.isEmpty(etEmail.getText().toString().trim())) {
            etEmail.setError(getString(R.string.empty_email));
            Toast.makeText(this, R.string.empty_email, Toast.LENGTH_SHORT).show();

        }
        else if (TextUtils.isEmpty(etPassword.getText().toString().trim())) {
             etPassword.setError(getString(R.string.empty_password));
            Toast.makeText(this, R.string.empty_password, Toast.LENGTH_SHORT).show();

        } else {
           // loginUser();
           // new AsynTask().execute();
          loginwithFirebase();

        }
    }

/*    private void loginUser(){
        SqliteUserPojo user = databaseHandler.checkUser(etEmail.getText().toString());

        if(user==null){
            CommonUtils.toastMessage(R.string.message_login_failed,this);
        }
        else if(user.getPassword().trim().equals(etPassword.getText().toString().trim())){
            loginSuccess();
            CommonUtils.toastMessage(R.string.message_login_success,this);
        }

        else {
            CommonUtils.toastMessage(R.string.message_login_failed,this);
        }
    }*/


    /**
     * it builds dialog forgot password.
      */
  private void loadDialog(){
    View alerdialogview = getLayoutInflater().inflate(R.layout.alertdialog_forgotpassword,null);
    alrtEmail = alerdialogview.findViewById(R.id.alrtdialog_Email);
    Submit = alerdialogview.findViewById(R.id.btnSubmit);
    Submit.setOnClickListener(this);
    alertDialogBuilder.setView(alerdialogview);
    alertDialog = alertDialogBuilder.create();
    alertDialog.show();
  }

    /**
     * Class Asynctask is used to run database operations on the background and not on the main ui thread
     */
    private   class AsynTask extends AsyncTask<Void,Void,Void>{
        EntityAuthtenticateUser entity_authtenticate_user;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            entity_authtenticate_user = new EntityAuthtenticateUser();
            entity_authtenticate_user = appDatabase.userDao().checkUser(etEmail.getText().toString().trim());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(entity_authtenticate_user==null){
                CommonUtils.toastMessage(R.string.message_login_failed,SqliteRoomLoginActivity.this);
            }
            else if (entity_authtenticate_user.getPassword().trim().equals(etPassword.getText().toString().trim()))
            {
                userid = entity_authtenticate_user.getUserid();
                 //loginSuccess(entity_authtenticate_user.getUserid());
                CommonUtils.toastMessage(R.string.message_login_success, SqliteRoomLoginActivity.this);
            }
            else
                {
                    CommonUtils.toastMessage(R.string.message_login_failed,SqliteRoomLoginActivity.this);

                }

            super.onPostExecute(aVoid);
        }
    }

    private void loginwithFirebase(){
      dialog.show();
      RestClient restClient = new RestClient();

      LoginBodyModel user = new LoginBodyModel();
      user.setEmail(etEmail.getText().toString().trim());
      user.setPassword(etPassword.getText().toString().trim());
      user.setReturnSecureToken(true);
      retrofit2.Call<LoginResponse> userLogin = restClient.getApiService().loginUser(user);

      userLogin.enqueue(new Callback<LoginResponse>() {
        @Override public void onResponse(retrofit2.Call<LoginResponse> call,
            Response<LoginResponse> response) {
          if(response.code()==200){
            dialog.cancel();

            Toasty.success(SqliteRoomLoginActivity.this,"login success").show();
          //Toast.makeText(SqliteRoomLoginActivity.this,"login success",Toast.LENGTH_SHORT).show();
          LoginResponse loginResponse = response.body();
          userid = loginResponse.getUid();
          Log.i("uid",""+loginResponse.getUid());

          loginSuccess(loginResponse.getUid(),loginResponse.getIdToken(),loginResponse.getRefreshToken());
          }
          else {
            Toasty.error(SqliteRoomLoginActivity.this,getString(R.string.login_invalid)).show();
            //CommonUtils.toastMessage("invalid credentials",SqliteRoomLoginActivity.this);
          }
        }

        @Override public void onFailure(retrofit2.Call<LoginResponse> call, Throwable t) {
          Toasty.error(SqliteRoomLoginActivity.this,"login failed").show();
          //Toast.makeText(SqliteRoomLoginActivity.this,""+t.getMessage(),Toast.LENGTH_SHORT).show();
        }
      });
    }




    /**
     * if the login is success then open samplenavigationdrawer class.
     * @param uid,idToken,refreshToken
     */
    private void loginSuccess(String uid,String idToken,String refreshToken){
        setPreferences(uid,idToken,refreshToken);
        etEmail.setText("");
        etPassword.setText("");
        Intent intent = new Intent(this,SampleNavigationDrawer.class);
        intent.putExtra("userid",userid);
        Log.i("SqliteRoomLoginActivity",userid);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * it sets current user to check if the user is logged in throughout the application
     */
    private void setPreferences(String uid,String idToken,String refreshToken){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("login",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("currentuser",uid);
      editor.putString("idToken",idToken);
      editor.putString("refreshToken",refreshToken);
        editor.apply();
    }
}
