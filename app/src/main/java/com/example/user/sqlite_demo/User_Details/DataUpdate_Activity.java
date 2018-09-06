package com.example.user.sqlite_demo.User_Details;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.sqlite_demo.R;

public class DataUpdate_Activity extends AppCompatActivity {

    private EditText show_email_editText;
    TextView textView;
    DBManager manager;
    private String name,phoneNo;
     private int rowId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_update_);
        setTitle("Data Update ");

        manager = new DBManager(getApplicationContext());

        show_email_editText = findViewById(R.id.show_email_id);
        textView = findViewById(R.id.show_PhoneNo_id);

        name = getIntent().getStringExtra("name");
        phoneNo = getIntent().getStringExtra("password");
        rowId = getIntent().getIntExtra("id",0);

         show_email_editText.setText(name);
         textView.setText(phoneNo);
         


    }

    private void call(String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 456);
            return;
        }
        if (intent.resolveActivity(this.getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void Call(View view) {
        call(phoneNo);
    }

    public void Update_Data(View view) {
        Intent intent = new Intent(DataUpdate_Activity.this,MainActivity.class);
        intent.putExtra("name",name);
        intent.putExtra("password",phoneNo);
        intent.putExtra("id",rowId);
        startActivity(intent);


    }

    public void Delete_Data(View view) {

        AlertDialog .Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Delete List");
        alert.setMessage("Are you sure ?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean isDelete = manager.Delete(rowId);
                if (isDelete){
                    Toast.makeText(DataUpdate_Activity.this, "Successfully Deleted ", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DataUpdate_Activity.this,Show_Activity.class));
                }else {
                    Toast.makeText(DataUpdate_Activity.this , "Couldn't Deleted ", Toast.LENGTH_SHORT).show();
                }
            }
        });
        alert.setNegativeButton("Cancel",null);
        alert.show();




    }

    public void phoneCall(View view) {
        call(phoneNo);

    }
}
