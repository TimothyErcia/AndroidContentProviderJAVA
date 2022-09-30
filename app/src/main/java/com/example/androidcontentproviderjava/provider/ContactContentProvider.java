package com.example.androidcontentproviderjava.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.androidcontentproviderjava.model.Contact;
import com.example.androidcontentproviderjava.repository.DBHelper;

public class ContactContentProvider extends ContentProvider {

    private DBHelper helper;
    private SQLiteDatabase db;

    private static final String table = "CONTACTS";
    public static final String URL = "content://com.example.androidcontentproviderjava.provider/ContactContentProvider/"+table;
    public static final Uri CONTENT_URI = Uri.parse(URL);

    public static final String _ID = "ID";
    public static final String _ContactName = "CONTACT_NAME";
    public static final String _ContactNumber = "CONTACT_NUMBER";

    public ContactContentProvider() {
    }

    @Override
    public boolean onCreate() {
        helper = new DBHelper(getContext());
        db = helper.getWritableDatabase();
        if(db != null) {
            return true;
        }
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        return db.query(
                table,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        db.insert(table, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return uri;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int count = db.update(table, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = db.delete(table, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}