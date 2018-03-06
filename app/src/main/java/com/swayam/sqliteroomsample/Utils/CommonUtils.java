package com.swayam.sqliteroomsample.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/*Created by akhil on 12/2/18.*/

/**
 * common methods used throught out the Application
 */

public class CommonUtils {

    static String Firebase_Database_Table_Name = "users";
    static String Firebase_Database_MobileNum = "mobileNum";
    static String Firebase_Database_Username = "userName";
    public static String Firebase_database_Email = "email";
    public static String Firebase_database_Password =  "password";
    public static String Intent_Extra_Email = "userid";
    public static boolean isLogin;
    public static String currentUser;
   public static String idToken;
   public static String refreshToken;
    public static  List<EditText> fields = new ArrayList<>();

    public static void toastMessage(int stringid, Context context){
        Toast.makeText(context," "+ context.getString(stringid),Toast.LENGTH_SHORT).show();
    }

    public static void toastMessage(String stringid, Context context){
        Toast.makeText(context," "+ stringid,Toast.LENGTH_SHORT).show();
    }

    static void emptyField(EditText editText){
        editText.setText("");
    }

    public static void addFields(EditText editText){
        fields.add(editText);
    }

    public static void emptyAllFields(){
        if(fields.size()!=0){
        for(int i=0;i<fields.size();i++){
            fields.get(i).setText("");
        }}
        fields.clear();
    }

    /**
     * generates otp for recovery of user.
     * @param length
     * @return
     */
    public static  String randomString(int length) {
        final String OTPCharacters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random RANDOM = new Random();
        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            sb.append(OTPCharacters.charAt(RANDOM.nextInt(OTPCharacters.length())));
        }
        return sb.toString();
    }

    /**
     * generates unique user id(uid)
     * @return
     */
    public static  String generateUID() {
        final String OTPCharacters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random RANDOM = new Random();
        StringBuilder sb = new StringBuilder(4);
        for (int i = 0; i < 5; i++) {
            sb.append(OTPCharacters.charAt(RANDOM.nextInt(OTPCharacters.length())));
        }
        return sb.toString();
    }

    /**
     * used to create shared preferences
     * @param context
     */
     public static void savePreferences(Context context){
        SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("login",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLogin",false);
        editor.putString("currentuser",currentUser);
         editor.putString("idToken",idToken);
         editor.putString("refreshToken",refreshToken);
         editor.commit();
    }


    /**
     *used to get preferences from shared preferences
     * @param context
     */
    public static void getPreferences(Context context){
         SharedPreferences sharedPreferences = context.getApplicationContext().getSharedPreferences("login",MODE_PRIVATE);
         isLogin = sharedPreferences.getBoolean("isLogin",false);
         currentUser = sharedPreferences.getString("currentuser",null);
         idToken = sharedPreferences.getString("idToken",null);
         refreshToken = sharedPreferences.getString("refreshToken",null);
    }


}



