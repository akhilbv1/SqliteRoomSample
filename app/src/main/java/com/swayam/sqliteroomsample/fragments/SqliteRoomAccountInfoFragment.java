package com.swayam.sqliteroomsample.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.widget.Toast;
import com.swayam.sqliteroomsample.ApiInterface;
import com.swayam.sqliteroomsample.LoginResponse;
import com.swayam.sqliteroomsample.RestClient;
import com.swayam.sqliteroomsample.RoomDatabase.AppDatabase;
import com.swayam.sqliteroomsample.UserDetailsModel;
import com.swayam.sqliteroomsample.Utils.CommonUtils;
import com.swayam.sqliteroomsample.Entities.EntityUserPojo;
import com.swayam.sqliteroomsample.R;
import com.swayam.sqliteroomsample.Activities.SqliteRoomEditInfoActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**Created by akhil on 2/2/18.*/

public class SqliteRoomAccountInfoFragment extends Fragment implements View.OnClickListener {

    TextView txtEmail, txtMobile;
    ProgressDialog dialog;
    String userID,mobileNumber,email,userName;
    AppDatabase appDatabase;

    EntityUserPojo user ;

    // Toolbar toolbar;

/*    public static SqliteRoomAccountInfoFragment newInstance(String emailId) {
        SqliteRoomAccountInfoFragment fragment = new SqliteRoomAccountInfoFragment();
        Bundle args = new Bundle();
        args.putString(CommonUtils.Intent_Extra_Email,emailId.trim());
        return fragment;
    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.firebase_account_activity, container, false);
        txtEmail = view.findViewById(R.id.txtEmail);
        txtMobile = view.findViewById(R.id.txtMobileNum);
        userID = getArguments().getString("userid");
        Log.i("userid",""+getArguments().getString("userid"));
        dialog = new ProgressDialog(getContext());
        dialog.setMessage(getString(R.string.dialog_loading));
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fragments,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menuedit){
          openEditInfoActivity();
            return true;
        }
        else
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dialog = new ProgressDialog(getContext());

        txtEmail.setOnClickListener(this);
        txtMobile.setOnClickListener(this);
        getUserDetails();
        //new Asynctask().execute();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        appDatabase = AppDatabase.getAppDatabase(context);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtEmail:
               openEmailClient();
                break;
            case R.id.txtMobileNum:
                openDialer();
                break;

        }
    }

    private void openEmailClient() {
        Intent Email = new Intent(Intent.ACTION_SEND);
        Email.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        Email.setType("message/rfc822");
        startActivity(Intent.createChooser(Email, getString(R.string.choose_intent_email)));
    }

    private void openDialer(){
        Intent Dialer = new Intent(Intent.ACTION_DIAL);
        Dialer.setData(Uri.parse("tel:" + mobileNumber));
        startActivity(Dialer);
    }

    private void getUserDetails(){
      dialog.show();

        RestClient restClient = new RestClient();
        String userid;
          userid = userID+".json";
          Log.i("uid",""+userid);
       final Call<UserDetailsModel> userDetails = restClient.getUserApiService().getUserDetails(userid);

        userDetails.enqueue(new Callback<UserDetailsModel>() {
        @Override
        public void onResponse(Call<UserDetailsModel> call, Response<UserDetailsModel> response) {
           Log.i("body",""+response.body());
          UserDetailsModel userdetails = response.body();
          txtEmail.setText(userdetails.getEmailid());
          txtMobile.setText(userdetails.getMobilenumber());
          dialog.cancel();
          email = userdetails.getEmailid();
          mobileNumber = userdetails.getMobilenumber();
          userName = userdetails.getUsername();
          Log.i("username",""+userName);
        }

        @Override public void onFailure(Call<UserDetailsModel> call, Throwable t) {
          CommonUtils.toastMessage(t.getMessage(),getContext());
        }
      });
    }
    private class Asynctask extends AsyncTask<Void,Void,Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(userID==null){
                user = appDatabase.userDao().getUser(CommonUtils.currentUser);
            }
            else {
            user = appDatabase.userDao().getUser(userID);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            if(user!=null)
            mobileNumber = user.getMobileNum();
            txtEmail.setText(String.format("Email id: %s", user.getEmailid()));
            txtMobile.setText(String.format("Mobile number: %s", user.getMobileNum()));
        }
    }

    private void openEditInfoActivity( ){
        Intent intent = new Intent(getContext(),SqliteRoomEditInfoActivity.class);
        intent.putExtra("userid",userID);
         intent.putExtra("email",email);
         intent.putExtra("username",userName);
         intent.putExtra("mobile",mobileNumber);
        startActivity(intent);
    }


    @Override
    public void onStop() {
        super.onStop();
        dialog.dismiss();
    }
}
