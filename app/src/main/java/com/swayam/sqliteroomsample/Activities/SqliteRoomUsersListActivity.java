package com.swayam.sqliteroomsample.Activities;

/*
 *activity is used to show list of all the users
  * respective layout:- users_list_activity
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.swayam.sqliteroomsample.AllUserDetailsResponseModel;
import com.swayam.sqliteroomsample.RestClient;
import com.swayam.sqliteroomsample.RoomDatabase.AppDatabase;
import com.swayam.sqliteroomsample.Entities.EntityUserPojo;
import com.swayam.sqliteroomsample.R;
import com.swayam.sqliteroomsample.Sqlite.SqliteUserPojo;
import com.swayam.sqliteroomsample.Sqlite.SqliteUsersListDatabaseHandler;
import com.swayam.sqliteroomsample.Adapters.UsersListAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/*Created by akhil on 14/2/18.
 */

public class SqliteRoomUsersListActivity extends AppCompatActivity implements View.OnClickListener {

   private List<EntityUserPojo> mList = new ArrayList<>();
   private List<SqliteUserPojo> usersList = new ArrayList<>();
   private RecyclerView recyclerView;
   private UsersListAdapter usersListAdapter;
   AppDatabase appDatabase;
   SqliteUsersListDatabaseHandler databaseHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.users_list_activity);

        recyclerView = findViewById(R.id.recyclerviewUsersList);
       /* Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.toolbar_title_userslist);
        setSupportActionBar(toolbar);
*/

        RecyclerView.LayoutManager LayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(LayoutManager);
        recyclerView.setHasFixedSize(true);

        appDatabase = AppDatabase.getAppDatabase(this);

        databaseHandler = new SqliteUsersListDatabaseHandler(this);

       // new AsyncTask().execute();
        //loadUsersList();
     // getALlUsersRetrofit();
           }

  /*  private void loadUsersList(){
        usersList =   databaseHandler.getAllUsers();
        usersListAdapter = new UsersListAdapter(usersList);
        recyclerView.setAdapter(usersListAdapter);
    }*/

    /**
     * Class Asynctask is used to run database operations on the background and not on the main ui thread
     */
/*
    private void getALlUsersRetrofit(){
      RestClient restClient = new RestClient();
      Call<AllUserDetailsResponseModel> getAllUsersCall = restClient.getGetAllUsersService().getALlUserDetails();

      getAllUsersCall.enqueue(new Callback<AllUserDetailsResponseModel>() {
        @Override public void onResponse(Call<AllUserDetailsResponseModel> call,
            Response<AllUserDetailsResponseModel> response) {

             Log.i("users",""+response.body());

        }

        @Override public void onFailure(Call<AllUserDetailsResponseModel> call, Throwable t) {

        }
      });
    }*/

  @Override public void onClick(View v) {
    //getALlUsersRetrofit();
  }

  private class AsyncTask extends android.os.AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            mList = appDatabase.userDao().getAllUsers();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
          //  usersListAdapter= new UsersListAdapter(mList);
            recyclerView.setAdapter(usersListAdapter);
            super.onPostExecute(aVoid);
        }
    }

}
