package com.example.user.sqlite_demo.User_Details;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import com.example.user.sqlite_demo.User_Details.BDHelper;
import com.example.user.sqlite_demo.User_Details.Contact;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    BDHelper bdHelper;

    SQLiteDatabase db;

    public DBManager(Context context) {
        bdHelper = new BDHelper(context);

    }
        public Boolean AddToContact(Contact contact){
            db = bdHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(BDHelper.KEY_EMAIL,contact.getEmail());
            values.put(BDHelper.KEY_PASSWORD,contact.getPhoneNo());
            long isInsert = db.insert(BDHelper.TABLE_NAME, null, values);
            db.close();
            if (isInsert>0){
                return true;
            }else {
                return false;
            }
       }




    public Cursor getDataByName(String name) {

        db = bdHelper.getReadableDatabase();

        Cursor cursor;

       Contact contact = new Contact();

        String query = "SELECT "+ BDHelper.KEY_PASSWORD +" FROM " + BDHelper.TABLE_NAME + " WHERE " + BDHelper.KEY_EMAIL +
                " LIKE '%" + name + "%';";

        cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {

            int doctorId = cursor.getInt(cursor.getColumnIndex(BDHelper.KEY_ID));
            String doctorDetails = cursor.getString(cursor.getColumnIndex(BDHelper.KEY_PASSWORD));


            contact = new Contact(doctorId,doctorDetails);



            db.close();
        }

        return contact;
    }





    public Boolean Update_Data_By_Id(Contact contact,int rawId){

        db = bdHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(BDHelper.KEY_EMAIL,contact.getEmail());
        contentValues.put(BDHelper.KEY_PASSWORD,contact.getPhoneNo());

        int isUpdate = db.update(BDHelper.TABLE_NAME,contentValues,BDHelper.KEY_ID+" = ?",new String[]{Integer.toString(rawId)});
           db.close();
        if (isUpdate> 0 ){
            return true;
        }else {
            return false;
        }

       }

       public  ArrayList<Contact> getAllContact(){

           ArrayList<Contact>contactList = new ArrayList<>();

        db = bdHelper.getReadableDatabase();

        String quary = "SELECT * FROM "+BDHelper.TABLE_NAME;

           Cursor cursor = db.rawQuery(quary,null);

           if (cursor.moveToFirst()){

               do{
                   int id = cursor.getInt(cursor.getColumnIndex(BDHelper.KEY_ID));
                   String email = cursor.getString(cursor.getColumnIndex(BDHelper.KEY_EMAIL));
                   String password = cursor.getString(cursor.getColumnIndex(BDHelper.KEY_PASSWORD));
                   Contact contact = new Contact(id,email,password);
                   contactList.add(contact);

               }while (cursor.moveToNext());
               db.close();
           }
           return contactList;
       }


       public  ArrayList<String> DoctorName(){

           ArrayList<String>doctorNameList = new ArrayList<>();

        db = bdHelper.getReadableDatabase();

        String quary = "SELECT "+BDHelper.KEY_EMAIL+" FROM "+BDHelper.TABLE_NAME;

           Cursor cursor = db.rawQuery(quary,null);

           if (cursor.moveToFirst()){

               do{
                   String email = cursor.getString(cursor.getColumnIndex(BDHelper.KEY_EMAIL));
                   doctorNameList.add(email);

               }while (cursor.moveToNext());

           }
           db.close();
           return doctorNameList;
       }




    public boolean Delete(int rowId) {
        SQLiteDatabase db = bdHelper.getWritableDatabase();

        int isDelete = db.delete(BDHelper.TABLE_NAME,BDHelper.KEY_ID +" = ?",new String[]{Integer.toString(rowId)});

        db.close();
        if (isDelete> 0 ){
            return true;
        }else {
            return false;
        }
    }
}
