package com.example.user.sqlite_demo.User_Details;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.sqlite_demo.R;
import com.example.user.sqlite_demo.User_Details.Contact;

import java.util.ArrayList;

public class ContactAdapter extends ArrayAdapter<Contact> {
    ArrayList<Contact>contactList = new ArrayList<>();
    Context mContext;



    public ContactAdapter(@NonNull Context context, @NonNull ArrayList<Contact>contactList) {
        super(context, R.layout.view_model, contactList);
        mContext = context;
        this.contactList = contactList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.view_model,parent,false);

        Contact contact = contactList.get(position);

        TextView email_textView = convertView.findViewById(R.id.email_TV_id);
        TextView password_textView = convertView.findViewById(R.id.password_TV_id);
        ImageView imageView =convertView.findViewById(R.id.view_image_id);


        email_textView.setText(contact.getEmail());
        password_textView.setText(contact.getPhoneNo());



        return convertView;
    }


}
