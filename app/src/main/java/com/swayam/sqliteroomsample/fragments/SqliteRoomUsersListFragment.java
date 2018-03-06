package com.swayam.sqliteroomsample.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.Toast;
import com.swayam.sqliteroomsample.Activities.SampleNavigationDrawer;
import com.swayam.sqliteroomsample.AllUserDetailsKeyModel;
import com.swayam.sqliteroomsample.AllUserDetailsResponseModel;
import com.swayam.sqliteroomsample.RestClient;
import com.swayam.sqliteroomsample.RoomDatabase.AppDatabase;
import com.swayam.sqliteroomsample.Entities.EntityUserPojo;
import com.swayam.sqliteroomsample.R;
import com.swayam.sqliteroomsample.Adapters.UsersListAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*Created by akhil on 15/2/18.
 */

public class SqliteRoomUsersListFragment extends Fragment {
    private List<EntityUserPojo> mList = new ArrayList<>();
    private List<AllUserDetailsResponseModel> usersList = new ArrayList<>();
    private RecyclerView recyclerView;
    private UsersListAdapter usersListAdapter;
    AppDatabase appDatabase;
    Toolbar toolbar;
    SampleNavigationDrawer listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.users_list_activity,container,
                false);
        recyclerView = view.findViewById(R.id.recyclerviewUsersList);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(activity.getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

      return view;
    }


  public interface onClickListitem{
      public void listitemclicked(AllUserDetailsResponseModel user);
  }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
      //  new AsyncTask().execute();
      getALlUsersRetrofit();
        super.onViewCreated(view, savedInstanceState);

    }


    @Override
    public void onAttach(Context context) {
        appDatabase = AppDatabase.getAppDatabase(context);
         Log.i("context",""+context);
         listener = (SampleNavigationDrawer) context;


      super.onAttach(context);
    }

  @Override public void onAttach(Activity activity) {
    super.onAttach(activity);

  }

    private void getALlUsersRetrofit(){
      RestClient restClient = new RestClient();
      Call<AllUserDetailsKeyModel> getAllUsersCall = restClient.getGetAllUsersService().getALlUserDetails();
      getAllUsersCall.enqueue(new Callback<AllUserDetailsKeyModel>() {
        @Override public void onResponse(Call<AllUserDetailsKeyModel> call,
            Response<AllUserDetailsKeyModel> response) {
          Log.i("data",""+response.body().getResult());
          for(String key :response.body().getResult().keySet()){
            AllUserDetailsResponseModel userDetails  = response.body().getResult().get(key);
            Log.i("username",""+userDetails.getUsername());
            usersList.add(userDetails);
          }
          usersListAdapter= new UsersListAdapter(listener,usersList);
          recyclerView.setAdapter(usersListAdapter);

         /* Set<String> keys = response.body().getResult().keySet();
          for(String key: keys){
             Log.i("keys",""+key);
          }*/

        }

        @Override public void onFailure(Call<AllUserDetailsKeyModel> call, Throwable t) {

        }
      });
    }


    private class AsyncTask extends android.os.AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            mList = appDatabase.userDao().getAllUsers();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
          /*usersListAdapter= new UsersListAdapter(listener,mList);
          recyclerView.setAdapter(usersListAdapter);*/
            super.onPostExecute(aVoid);
        }
    }


}
