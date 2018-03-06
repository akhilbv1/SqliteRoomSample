package com.swayam.sqliteroomsample.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;
import com.swayam.sqliteroomsample.Adapters.UsersListAdapter;
import com.swayam.sqliteroomsample.AllUserDetailsResponseModel;
import com.swayam.sqliteroomsample.Entities.EntityUserPojo;
import com.swayam.sqliteroomsample.R;
import com.swayam.sqliteroomsample.Utils.CommonUtils;
import com.swayam.sqliteroomsample.fragments.AboutUsFragment;
import com.swayam.sqliteroomsample.fragments.SqliteRoomAccountInfoFragment;
import com.swayam.sqliteroomsample.fragments.SqliteRoomChangePasswordFragment;
import com.swayam.sqliteroomsample.fragments.SqliteRoomUsersListFragment;

/**Created by akhil on 2/2/18.
 */

/*
  navigation drawer
  respective layout:-sample_navigation_drawer
 */

public class SampleNavigationDrawer extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,SqliteRoomUsersListFragment.onClickListitem {

    private DrawerLayout drawerLayout;
    Toolbar toolbar;
    String userid,email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sample_navigation_drawer);
        setLoginPreference();
        toolbar = findViewById(R.id.toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawerLayout = findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
        setupDrawerToggle();
        userid = getUid();

        Log.i("userid",""+userid);
        defaultFragment();
    }

    /**
     * sets shared preference islogin to true
     */
    private void setLoginPreference(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("login",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("isLogin",true);
        editor.commit();
    }

    private String getUid(){
      CommonUtils.getPreferences(this);
      String uid = CommonUtils.currentUser;
      Log.i("current_user",""+uid);
      return uid;
    }

    /**
     * opens default fragment.
     */
    private void defaultFragment() {
        Fragment fragment = new SqliteRoomAccountInfoFragment();
        Bundle args = new Bundle();
        args.putString("userid",userid);
        fragment.setArguments(args);
        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.add(R.id.container, fragment);
        tx.commit();
    }


    @Override
    public void onBackPressed() {
        finish();
    }

    /**
     * invoked when navigation item is clicked
     * @param item
     * @return
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.nav_profile:
                setToolbarTitle(getString(R.string.toolbar_title_account));
                fragment = new SqliteRoomAccountInfoFragment();
                break;
            case R.id.nav_chng_pass:
                setToolbarTitle(getString(R.string.toolbar_title_change_password));
                fragment = new SqliteRoomChangePasswordFragment();
                break;
            case R.id.nav_users_list:
                setToolbarTitle(getString(R.string.toolbar_title_users_list));
                fragment = new SqliteRoomUsersListFragment();
                break;
            case R.id.nav_about:
                setToolbarTitle(getString(R.string.toolbar_title_aboutus));
                fragment = new AboutUsFragment();
                break;
            case R.id.nav_signout:
                setSignOutPreference();
                Intent intent = new Intent(SampleNavigationDrawer.this, SqliteRoomLoginActivity.class);
                startActivity(intent);
                break;
            default:
                fragment = new SqliteRoomAccountInfoFragment();
                break;
        }
        if (fragment != null) {
            Bundle args = new Bundle();
            args.putString("userid",userid);
            fragment.setArguments(args);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.container, fragment);
            ft.addToBackStack("fragments");
            ft.commit();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
//        invalidateOptionsMenu();
        return true;
    }

    /**
     * sets islogin in shared preference to false on user signout
     */
    private void setSignOutPreference(){
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("login",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean("isLogin",false);
        editor.putString("current",null);
        editor.clear();
        editor.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * navigation drawertoggle button.
     */
    private void setupDrawerToggle() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    /**
     * sets respective toolbar title for each activity
     * @param title
     */
    private void setToolbarTitle(String title) {
        toolbar.setTitle(title);
    }

  @Override public void listitemclicked(AllUserDetailsResponseModel user) {
    Toast.makeText(this,""+user.getEmail(),Toast.LENGTH_SHORT).show();

  }


}