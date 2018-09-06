package com.example.user.sqlite_demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.user.sqlite_demo.User_Details.MainActivity;

public class LogInActivity extends AppCompatActivity {

    private EditText email_editText,password_editText;
    LogInMethod method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        email_editText = findViewById(R.id.logInEmail_id);
        password_editText = findViewById(R.id.logInPassword_id);
        method = new LogInMethod(this);

        if (!method.isUserLogin()){
            startActivity(new Intent(LogInActivity.this, MainActivity.class));
            finish();
        }


    }

    public void Login(View view) {

        String email = email_editText.getText().toString();
        String password = password_editText.getText().toString();

        try{
            method.UserLogIn(email,password);
            startActivity(new Intent(LogInActivity.this, MainActivity.class));
            Toast.makeText(this, "Log In Successfully ", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            Toast.makeText(this, "Exception is created ", Toast.LENGTH_SHORT).show();


        }




    }
}
