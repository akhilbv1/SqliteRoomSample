package com.swayam.sqliteroomsample.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.swayam.sqliteroomsample.ChangePasswordAuthModel;
import com.swayam.sqliteroomsample.ChangePasswordModel;
import com.swayam.sqliteroomsample.LoginResponse;
import com.swayam.sqliteroomsample.RefreshTokenBodyModel;
import com.swayam.sqliteroomsample.RefreshTokenResponseModel;
import com.swayam.sqliteroomsample.RestClient;
import com.swayam.sqliteroomsample.RoomDatabase.AppDatabase;
import com.swayam.sqliteroomsample.R;
import com.swayam.sqliteroomsample.Activities.SqliteRoomLoginActivity;
import com.swayam.sqliteroomsample.Utils.CommonUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**Created by akhil on 2/2/18.
 */

public class SqliteRoomChangePasswordFragment extends Fragment {

    //private Toolbar toolbar;
    private TextInputEditText etOldPassword,etNewPassword,etConfNewPassword;
    private ProgressDialog dialog;
    private String userId;
    AppDatabase appDatabase;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private String getRefreshToken(){
      CommonUtils.getPreferences(getContext());
      return CommonUtils.refreshToken;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.firebase_changepassword_fragment,container,false);
       /* toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActio`Bar(toolbar);
        toolbar.setTitle(R.string.change_password);*/
        etOldPassword = view.findViewById(R.id.etOldPassword);
        etNewPassword = view.findViewById(R.id.etNewPassword);
        etConfNewPassword = view.findViewById(R.id.etConfNewPassword);
        dialog = new ProgressDialog(getContext());
        dialog.setMessage(getString(R.string.dialog_loading));
        userId = getArguments().getString("userid");
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        appDatabase = AppDatabase.getAppDatabase(context);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_savepassword,menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menusave){
            validationPassword();
            return  true;
        }
        else
        return super.onOptionsItemSelected(item);
    }

    private void validationPassword(){
        if(TextUtils.isEmpty(etOldPassword.getText().toString().trim())){
            toastMessage(getString(R.string.empty_password));
        }
        else if(etOldPassword.getText().toString().trim().length() < 6){
            toastMessage(getString(R.string.validation_password));
        }
        else if(TextUtils.isEmpty(etNewPassword.getText().toString().trim())){
            toastMessage(getString(R.string.empty_new_password));
        }
        else if(etNewPassword.getText().toString().trim().length() < 6){
          toastMessage(getString(R.string.validation_password));
        }
        else if(TextUtils.isEmpty(etConfNewPassword.getText().toString().trim())){
          toastMessage(getString(R.string.empty_password));
        }
        else if(etNewPassword.getText().toString().trim().equals(etConfNewPassword.getText().toString().trim())){
           // new Asynctask().execute();
         // changePasswordRetrofit();
          refreshToken(getRefreshToken());
        }
        else {
            toastMessage(getString(R.string.check_passwords));
        }
    }


    private void toastMessage(String message){
        Toast.makeText(getContext()," "+message,Toast.LENGTH_SHORT).show();
    }

    private void changePasswordRetrofit(){
      final RestClient restClient = new RestClient();

      Call<Void> changePasswordCall = restClient.getApiService().updatePassword(getChangePasswordAuthModel());
      changePasswordCall.enqueue(new Callback<Void>() {
        @Override public void onResponse(Call<Void> call, Response<Void> response) {
          if(response.code()==200){
            changePasswordDb();
          }
          else if(response.code()==400){
            CommonUtils.toastMessage("updated failed",getContext());
          }
        }

        @Override public void onFailure(Call<Void> call, Throwable t) {
          CommonUtils.toastMessage("updated failed",getContext());
          Log.i("message",""+t.getMessage());
        }
      });
    }

    private void changePasswordDb(){
      RestClient restClient = new RestClient();
      final String userid = userId+".json";
      ChangePasswordModel changePasswordModel = new ChangePasswordModel();
      changePasswordModel.setPassword(etNewPassword.getText().toString().trim());
      Call<Void> changePasswordDbCall = restClient.getUserApiService().updatePassword(userid,changePasswordModel);
      changePasswordDbCall.enqueue(new Callback<Void>() {
        @Override public void onResponse(Call<Void> call, Response<Void> response) {
          if(response.code()==200)
            CommonUtils.toastMessage("updated successful",getContext());
          etOldPassword.setText("");
          etNewPassword.setText("");
          etConfNewPassword.setText("");
        }
        @Override public void onFailure(Call<Void> call, Throwable t) {
         Log.i("update_failed_db",""+t.getLocalizedMessage());
        }
      });
    }
    private ChangePasswordAuthModel getChangePasswordAuthModel(){
      CommonUtils.getPreferences(getContext());
      String idToken = CommonUtils.idToken;
      String refreshToken = CommonUtils.refreshToken;
      String password = etNewPassword.getText().toString().trim();
      ChangePasswordAuthModel changePasswordAuthModel = new ChangePasswordAuthModel();
      changePasswordAuthModel.setIdtoken(idToken);
      changePasswordAuthModel.setPassword(password);
      changePasswordAuthModel.setReturnSecureToken(true);
      return changePasswordAuthModel;
    }

  private void refreshToken(String token){
    RestClient restClient = new RestClient();
    RefreshTokenBodyModel refreshTokenBodyModel = new RefreshTokenBodyModel();
    refreshTokenBodyModel.setGrant_type("refresh_token");
    refreshTokenBodyModel.setRefreshToken(token);
    retrofit2.Call<RefreshTokenResponseModel> refreshTokenCall = restClient.getRefreshTokenService().refreshToken(refreshTokenBodyModel);
    refreshTokenCall.enqueue(new Callback<RefreshTokenResponseModel>() {
      @Override public void onResponse(Call<RefreshTokenResponseModel> call,
          Response<RefreshTokenResponseModel> response) {
        if(response.code()==200){
          SharedPreferences sharedPreferences = getContext().getApplicationContext().getSharedPreferences("login",MODE_PRIVATE);
          SharedPreferences.Editor editor = sharedPreferences.edit();
          editor.putString("idToken",response.body().getTokenId());
          editor.putString("refreshToken",response.body().getRefreshToken());
          editor.apply();
          changePasswordRetrofit();
        }
      }

      @Override public void onFailure(Call<RefreshTokenResponseModel> call, Throwable t) {
         Log.i("refresh_failed",""+t.getLocalizedMessage());
      }
    });
  }




    private  class Asynctask extends AsyncTask<Void,Void,Void>
    {
        int count;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
           count = appDatabase.userDao().changePassword(etNewPassword.getText().toString().trim(),userId);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.cancel();
            Log.i("count",""+count);
            if(count==1){
                Toast.makeText(getActivity(),"password updated successfully",Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(),"please login with updated password",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), SqliteRoomLoginActivity.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(getActivity(),"password update failure",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        dialog.dismiss();
    }
}
