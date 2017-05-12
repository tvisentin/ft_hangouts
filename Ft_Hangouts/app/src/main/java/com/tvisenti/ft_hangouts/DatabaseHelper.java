package com.tvisenti.ft_hangouts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tvisenti on 5/9/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper sInstance;

    public static final String DATABASE_NAME = "contact.db";
    public static final String CONTACT_TABLE = "contact_table";
    public static final String CONTACT_ID = "ID";
    public static final String CONTACT_FIRSTNAME = "FIRSTNAME";
    public static final String CONTACT_LASTNAME = "LASTNAME";
    public static final String CONTACT_PHONE = "PHONE";
    public static final String CONTACT_EMAIL = "EMAIL";
    public static final String CONTACT_ADDRESS = "ADDRESS";

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CONTACT_TABLE + " (" + CONTACT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CONTACT_FIRSTNAME + " TEXT NOT NULL, " + CONTACT_LASTNAME + " TEXT NOT NULL, " + CONTACT_PHONE +
                " TEXT NOT NULL, " + CONTACT_EMAIL + " TEXT NOT NULL, " + CONTACT_ADDRESS + " TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CONTACT_TABLE);
        onCreate(db);
    }

    public boolean insertDataContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CONTACT_FIRSTNAME, contact.getFirstName());
        contentValues.put(CONTACT_LASTNAME, contact.getLastName());
        contentValues.put(CONTACT_PHONE, contact.getPhone());
        contentValues.put(CONTACT_EMAIL, contact.getMail());
        contentValues.put(CONTACT_ADDRESS, contact.getAddress());
        long result = db.insert(CONTACT_TABLE, null, contentValues);
        db.close();
        if (result == -1)
            return false;
        return true;
    }

    Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(CONTACT_TABLE, new String[] { CONTACT_ID, CONTACT_FIRSTNAME,
                        CONTACT_LASTNAME, CONTACT_PHONE, CONTACT_EMAIL, CONTACT_ADDRESS }, CONTACT_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contact contact = new Contact(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
        // return contact
        return contact;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<Contact>();
        String selectQuery = "SELECT  * FROM " + CONTACT_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));

                String name = cursor.getString(1)+ " " + cursor.getString(2) + "\n" + cursor.getString(3);
                MainActivity.ArrayofContact.add(name);
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public int updateContact(Contact contact, Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CONTACT_FIRSTNAME, contact.getFirstName());
        values.put(CONTACT_LASTNAME, contact.getLastName());
        values.put(CONTACT_PHONE, contact.getPhone());
        values.put(CONTACT_EMAIL, contact.getMail());
        values.put(CONTACT_ADDRESS, contact.getAddress());

        // updating row
        return db.update(CONTACT_TABLE, values, CONTACT_ID + " = ?",
                new String[] { String.valueOf(id) });
    }

    public void deleteContact(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(CONTACT_TABLE, CONTACT_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }

    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + CONTACT_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }
}
