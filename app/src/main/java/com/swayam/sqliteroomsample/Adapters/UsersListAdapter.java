package com.swayam.sqliteroomsample.Adapters;

/*Created by akhil on 14/2/18.*/

/*
 * adapter file for the userlist.
 * respective layout:-list_users
 */

import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import android.widget.Toast;
import com.swayam.sqliteroomsample.Activities.SampleNavigationDrawer;
import com.swayam.sqliteroomsample.AllUserDetailsResponseModel;
import com.swayam.sqliteroomsample.Entities.EntityUserPojo;
import com.swayam.sqliteroomsample.R;

import com.swayam.sqliteroomsample.fragments.SqliteRoomChangePasswordFragment;
import com.swayam.sqliteroomsample.fragments.SqliteRoomUsersListFragment;
import java.util.List;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.MyViewHolder>{

  private List<EntityUserPojo> mList;
  private List<AllUserDetailsResponseModel> usersList;

  SampleNavigationDrawer mListener;
  SampleNavigationDrawer context;

   /* public UsersListAdapter(SampleNavigationDrawer context,List<EntityUserPojo> mlist){
        this.mList = mlist;
        this.context = context;
    }
    */
  public UsersListAdapter(SampleNavigationDrawer context,List<AllUserDetailsResponseModel> usersList){
    this.usersList = usersList;
    this.context = context;
  }

  void Update(List<EntityUserPojo> mlist){
        this.mList = mlist;
        Log.i("list_size",""+mlist.size());
        notifyDataSetChanged();

   }

    @Override
    public UsersListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View list_view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_users,parent,
                false);
        return new MyViewHolder(list_view);
    }

    @Override
    public void onBindViewHolder(UsersListAdapter.MyViewHolder holder, int position) {
       Log.i("position",""+position);
        holder.UpdateUI(position);
    }


    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtUserName,txtEmail,txtMobileNum,txtUserid;
        CardView cradview;
        int position;
      EntityUserPojo user;
      AllUserDetailsResponseModel userList;
      public MyViewHolder(View itemView) {
            super(itemView);
            txtUserName = itemView.findViewById(R.id.txtUserName);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtMobileNum = itemView.findViewById(R.id.txtMobileNum);
            txtUserid =itemView.findViewById(R.id.txtUserid);
            cradview = itemView.findViewById(R.id.cardview);
        }

     private void UpdateUI(int position){
       userList = usersList.get(position);
             txtUserName.setText(userList.getUsername());
             txtEmail.setText(userList.getEmail());
             txtMobileNum.setText(userList.getMobilenum());
             cradview.setOnClickListener(this);

        }

      @Override public void onClick(View v) {
        Log.i("listitem","clicked");
        context.listitemclicked(userList);
      }
    }


}
