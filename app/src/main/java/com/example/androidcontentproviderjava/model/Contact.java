package com.example.androidcontentproviderjava.model;

public class Contact {
    private int id;
    private String ContactName;
    private String ContactNumber;

    public Contact(String name, String number) {
        ContactName = name;
        ContactNumber = number;
    }

    public String getContactName() {
        return ContactName;
    }

    public void setContactName(String contactName) {
        ContactName = contactName;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }
}
