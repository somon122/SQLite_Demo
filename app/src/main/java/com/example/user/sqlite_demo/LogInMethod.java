package com.example.user.sqlite_demo;

import android.content.Context;
import android.content.SharedPreferences;

public class LogInMethod {
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    public LogInMethod (Context context){

        this.context = context;
        preferences = context.getSharedPreferences("UserLogin",Context.MODE_PRIVATE);
        editor = preferences.edit();


    }
    public  void UserLogIn(String email,String password){

        editor.putString("Email", email);
        editor.putString("Password", password);
        editor.commit();

    }
    public void Delete(){
        editor.clear();
        editor.commit();
    }

    public boolean isUserLogin(){

        boolean isEmailEmpty = preferences.getString("Email","").isEmpty();
        boolean isPassordEmpty = preferences.getString("Password","").isEmpty();
        return isEmailEmpty || isPassordEmpty;

    }

}
