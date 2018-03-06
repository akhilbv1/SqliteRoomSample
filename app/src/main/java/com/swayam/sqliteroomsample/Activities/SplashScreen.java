package com.swayam.sqliteroomsample.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.swayam.sqliteroomsample.Utils.CommonUtils;
import com.swayam.sqliteroomsample.R;

/*Created by akhil on 16/2/18.
 */

/*
 *splash screen for the app
  * respective layout:-splashscreen
 */

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        int SPLASH_TIME_OUT = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity();
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    /**
     * starts respective activity
     */
    private void startActivity(){
        CommonUtils.getPreferences(SplashScreen.this);
        if(CommonUtils.isLogin){
            Intent intent = new Intent(this,SampleNavigationDrawer.class);
            startActivity(intent);
        }

        else {
            Intent intent = new Intent(this,SqliteRoomLoginActivity.class);
            startActivity(intent);
        }
    }

}
