package myandroidninja.wordpress.simplisqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kavitha on 31-07-2017.
 */

public class SQLiteOpenHelper extends android.database.sqlite.SQLiteOpenHelper {
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Contacts table name
    private static final String TABLE_CONTACTS = "contacts";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";

    public SQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + "  TEXT,"
                + KEY_PH_NO + " TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    void addContact(Contacts contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, contact.get_name());
        contentValues.put(KEY_PH_NO, contact.get_phone_number());

        db.insert(TABLE_CONTACTS, null, contentValues);
        db.close();
    }

    Contacts getContact(int id) {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.query(TABLE_CONTACTS, new String[]{KEY_ID, KEY_NAME, KEY_PH_NO}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Contacts contacts = new Contacts(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        return contacts;
    }


    public List<Contacts> getAllContacts() {
        List<Contacts> contactList = new ArrayList<Contacts>();

        String selectQuery = "SELECT * FROM " + TABLE_CONTACTS;

        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Contacts contact = new Contacts();
                contact.set_id(Integer.parseInt(cursor.getString(0)));
                contact.set_name(cursor.getString(1));
                contact.set_phone_number(cursor.getString(2));
                // Adding contact to list
                contactList.add(contact);
            }
            while (cursor.moveToNext());
        }

        return contactList;
    }


    public int updateContact(Contacts contact) {
        Log.i("SDgdsg","DSg "+contact.get_id());
        Log.i("SDgdsg","DSg "+contact.get_phone_number());
        Log.i("SDgdsg","DSg "+contact.get_name());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.get_name());
        values.put(KEY_PH_NO, contact.get_phone_number());

        return  db.update(TABLE_CONTACTS,values,KEY_ID+ " = ?",new String[]{String.valueOf(contact.get_id())});


    }


    // Deleting single contact
    public void deleteContact(Contacts contact) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete(TABLE_CONTACTS,KEY_ID+"=?",new String[]{String.valueOf(contact.get_id())});
        sqLiteDatabase.close();
    }

    // Getting contacts Count
    public int getContactsCount() {
        String count = "SELECT * FROM "+TABLE_CONTACTS;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(count,null);
        cursor.close();
        return  cursor.getCount();
    }
}