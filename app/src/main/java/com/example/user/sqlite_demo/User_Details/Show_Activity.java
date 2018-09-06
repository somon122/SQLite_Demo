package com.example.user.sqlite_demo.User_Details;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;


import com.example.user.sqlite_demo.R;

import java.util.ArrayList;

public class Show_Activity extends AppCompatActivity {
    ListView listView;
    DBManager manager;
    ArrayList<Contact> contactList;
    ContactAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_);

        setTitle("Show All Data ");

        manager = new DBManager(getApplicationContext());


            listView = findViewById(R.id.list_View_id);


            contactList = manager.getAllContact();

            adapter = new ContactAdapter(Show_Activity.this, contactList);
            listView.setAdapter(adapter);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String user_name = contactList.get(position).getEmail();
                String user_password = contactList.get(position).getPhoneNo();
                int rowId = contactList.get(position).getId();
                Intent intent = new Intent(Show_Activity.this,DataUpdate_Activity.class);
                intent.putExtra("name",user_name);
                intent.putExtra("password",user_password);
                intent.putExtra("id",rowId);
                startActivity(intent);

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_layout,menu);
        MenuItem menuItem = menu.findItem(R.id.searchView_id);
        SearchView searchView = (SearchView) menuItem.getActionView();

       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String query) {
               return false;
           }

           @Override
           public boolean onQueryTextChange(String newText) {

               adapter.getFilter().filter(newText);

               return false;
           }
       });



        return super.onCreateOptionsMenu(menu);
    }
}
