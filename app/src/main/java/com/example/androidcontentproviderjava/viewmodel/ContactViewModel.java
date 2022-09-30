package com.example.androidcontentproviderjava.viewmodel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidcontentproviderjava.model.Contact;
import com.example.androidcontentproviderjava.provider.ContactContentProvider;

import java.util.ArrayList;
import java.util.List;

public class ContactViewModel extends ViewModel {

    private Cursor cursor;
    private ContactContentProvider contentProvider = new ContactContentProvider();

    private ArrayList<Contact> contactArrayList = new ArrayList<>();
    private MutableLiveData<List<Contact>> mutableContactList = new MutableLiveData<>();
    public LiveData<List<Contact>> getData() {
        return mutableContactList;
    }

    public void initializeData(Context context) {
        String[] arrCol = {ContactContentProvider._ID, ContactContentProvider._ContactName, ContactContentProvider._ContactNumber};
        cursor = context.getContentResolver().query(ContactContentProvider.CONTENT_URI, arrCol, null, null, null);

        try {
            while(cursor.moveToNext()) {
                contactArrayList.add(new Contact(cursor.getString(1), cursor.getString(2)));
            }
            mutableContactList.postValue(contactArrayList);
        } catch (Exception e) {
            mutableContactList.postValue(null);
        }
    }

    public void insertData(Context context, String contactName, String contactNumber) {
        ContentValues cv = new ContentValues();
        cv.put(ContactContentProvider._ContactName, contactName);
        cv.put(ContactContentProvider._ContactNumber, contactNumber);
        context.getContentResolver().insert(ContactContentProvider.CONTENT_URI, cv);
    }

    public void updateData(Context context, String contactName, String contactNumber) {
        ContentValues cv = new ContentValues();
        cv.put(ContactContentProvider._ContactNumber, contactNumber);
        context.getContentResolver().update(ContactContentProvider.CONTENT_URI, cv, ContactContentProvider._ContactName + " = ?", new String[]{contactName});
    }

    public void deleteData(Context context, String contactName) {
        context.getContentResolver().delete(ContactContentProvider.CONTENT_URI, ContactContentProvider._ContactName + " = ?", new String[]{contactName});
    }

}
