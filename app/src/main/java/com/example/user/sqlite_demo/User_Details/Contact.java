package com.example.user.sqlite_demo.User_Details;

public class Contact {
    private int id;
    private String email;
    private String phoneNo;
    private String image;


    public Contact(String email, String phoneNo,String image) {
        this.email = email;
        this.phoneNo = phoneNo;
        this.image = image;
    }

    public Contact(String email) {
        this.email = email;
    }

    public Contact(int id, String email, String phoneNo) {
        this.id = id;
        this.email = email;
        this.phoneNo = phoneNo;

    }

    public Contact(String email, String phoneNo) {
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public Contact() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }



    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
