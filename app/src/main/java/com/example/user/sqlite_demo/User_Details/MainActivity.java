package com.example.user.sqlite_demo.User_Details;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.user.sqlite_demo.LogInActivity;
import com.example.user.sqlite_demo.LogInMethod;
import com.example.user.sqlite_demo.R;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    DBManager manager;
    BDHelper helper;
    String searchName;


    EditText email_editText,password_editText;
    private String name,password;
    private int rowId;
    private Button saveButton,click;
    private Spinner spinner;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    public static final  int REQUEST_CAPTURE = 100,SELECT_FILE = 0;
    ImageView result_photo;
    private String mPhotoData;
    private Bitmap secondBitmap;
    private String name_set;
    ArrayList <String>nameList = new ArrayList<>();


    LogInMethod method;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Somon Hossain");


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.icare);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        method = new LogInMethod(this);
        manager = new DBManager(this);
        helper = new BDHelper(this);


        getCameraPermission();
        nameList = manager.DoctorName();



        spinner = findViewById(R.id.Spanner_id);


        ArrayAdapter adapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,nameList);
        spinner.setAdapter(adapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               name_set = nameList.get(position) ;
                Toast.makeText(MainActivity.this, " Doctor Name "+name_set, Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        email_editText = findViewById(R.id.email_id);
        password_editText = findViewById(R.id.password_id);
        saveButton = findViewById(R.id.Button_id);
        click = findViewById(R.id.takePhoto_id);

        result_photo = findViewById(R.id.image_id);
        if (!hasCamara()){
             click.setEnabled(false);

        }

        name = getIntent().getStringExtra("name");
        password = getIntent().getStringExtra("password");
        rowId = getIntent().getIntExtra("id",0);


        email_editText.setText(name);
        password_editText.setText(password);

        password_editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this,android.R.style.
                        Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth + "/" +month+ "/" +year;

                password_editText.setText(date);


            }
        };

        if (rowId > 0 ){
            saveButton.setText("Update");
        }


    }
    public void Save(View view) {


            String email = email_editText.getText().toString();
            String password = password_editText.getText().toString();

            if (rowId > 0) {

               try{


                   Contact contact = new Contact(email,password);
                   Boolean status = manager.Update_Data_By_Id(contact,rowId);
                   if (status){
                       Toast.makeText(this, "Update is Successful", Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(MainActivity.this,Show_Activity.class));

                   }else {
                       Toast.makeText(this, "Update is not Successful", Toast.LENGTH_SHORT).show();
                   }

               }catch (Exception e){
                   Toast.makeText(this, "Exception is Created ", Toast.LENGTH_SHORT).show();

               }


            } else {

               try{
                   Contact contact = new Contact(email, password);
                   Boolean isInsert = manager.AddToContact(contact);

                   if (isInsert) {
                       Toast.makeText(this, "Successful insert", Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(MainActivity.this, Show_Activity.class));
                   }else {
                       Toast.makeText(this, "insert Data Field", Toast.LENGTH_SHORT).show();
                   }
               }catch (Exception e){
                   Toast.makeText(this, "Exception"+e, Toast.LENGTH_SHORT).show();

               }

            }

        }

    public void Show_Data(View view) {

        startActivity(new Intent(MainActivity.this,Show_Activity.class));
    }

    public void TakePhoto(View view) {
        String email = email_editText.getText().toString();

        Contact contact = new Contact();

        manager.getDataByName(email);
        String newpassword = contact.getPhoneNo();
        password_editText.setText(newpassword);



    }

    public Boolean hasCamara(){


        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);

    }
    public void SelectImage(){
        final CharSequence [] items = {"Camera","Gallery","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Camera")){

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,REQUEST_CAPTURE);

                }else if (items[i].equals("Gallery")){

                    Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Select File"),SELECT_FILE);
                }else if (items[i].equals("Cancel")){
                    dialog.dismiss();

                }
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK ){

            if (requestCode ==REQUEST_CAPTURE) {
                Bundle bundle = data.getExtras();
                Bitmap photo = (Bitmap) bundle.get("data");
                mPhotoData = encodeToBase64(photo,Bitmap.CompressFormat.JPEG,100);
                //------- Before Database
                secondBitmap = decodeBase64(mPhotoData);
                //------- After Database
                result_photo.setImageBitmap(secondBitmap);


            }else if (requestCode == SELECT_FILE){

                Uri selectImage_Uri = data.getData();
                result_photo.setImageURI(selectImage_Uri);
            }
        }


    }
    public static String encodeToBase64(Bitmap image, Bitmap.CompressFormat compressFormat, int quality)
    {
        ByteArrayOutputStream byteArrayOS = new ByteArrayOutputStream();
        image.compress(compressFormat, quality, byteArrayOS);
        return Base64.encodeToString(byteArrayOS.toByteArray(), Base64.DEFAULT);
    }

    public static Bitmap decodeBase64(String input)
    {
        byte[] decodedBytes = Base64.decode(input, 0);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }

    public void getCameraPermission()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, REQUEST_CAPTURE);
            }
        }
    }



}










