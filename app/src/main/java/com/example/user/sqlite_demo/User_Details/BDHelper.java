package com.example.user.sqlite_demo.User_Details;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.widget.Toast;

public class BDHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "UserDataBase_db";
    public static final int VERSION = 1;
    public static final String TABLE_NAME = "User_details";
    public static final String KEY_ID = "Id";
    public static final String KEY_EMAIL = "Email";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_IMAGE = "Image";
    public Context context;



    public static final String CREATE_TABLE = "CREATE TABLE "+ TABLE_NAME +"( " + KEY_ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_EMAIL +" TEXT ,"+ KEY_PASSWORD +" TEXT ,"+ KEY_IMAGE +"BLOB ); ";



    public BDHelper(Context context) {

        super(context, DB_NAME, null, VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
            db.execSQL(CREATE_TABLE);

            Toast.makeText(context, "onCreate is Called", Toast.LENGTH_SHORT).show();


        }catch (Exception e){

            Toast.makeText(context, "onCreate is Not  Called", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
