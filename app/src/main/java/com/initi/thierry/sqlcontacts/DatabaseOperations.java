package com.initi.thierry.sqlcontacts;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.initi.thierry.sqlcontacts.ContactData.ContactTable;

/**
 * Created by Thierry on 19/10/2016.
 */
public class DatabaseOperations extends SQLiteOpenHelper {
    public final static int databaseVersion = 1;
    public String CREATE_QUERY = "CREATE TABLE IF NOT EXISTS" + ContactData.ContactTable.TABLE_NAME +
            "("+ ContactTable.FIRSTNAME + "TEXT, "+ ContactTable.LASTNAME+ "TEXT, "+ ContactTable.NUMBER + " TEXT, " +
            ContactTable.PHOTO + " BLOB, "+ ContactTable.OTHERS + " TEXT, "+ "PRIMARY KEY ("+ContactTable.NUMBER+") );";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERY);
        Log.d("Database Operation ", " Table created");
    }

    public DatabaseOperations(Context context) {
        super(context, ContactTable.DATABASE_NAME, null, databaseVersion);
        Log.d("Database Operation ", " Database created");
    }

    public void insertContact(DatabaseOperations dop, String name, String firstname, String number){
        SQLiteDatabase sqlDB = dop.getWritableDatabase();
        ContentValues cv = new ContentValues();
        if(!number.isEmpty())cv.put(ContactTable.NUMBER, number);
        if(!name.isEmpty())cv.put(ContactTable.LASTNAME, name);
        if(!firstname.isEmpty())cv.put(ContactTable.FIRSTNAME, firstname);

        long k = sqlDB.insert(ContactTable.TABLE_NAME, null,cv);
        Log.d("Database Operation "," One row inserted");
        //Toast.makeText(this, "One row inserted", Toast.).show();
    }

    public Cursor fecthContact(DatabaseOperations dop){
        SQLiteDatabase sqlDB = dop.getReadableDatabase();
        String []columns = {ContactTable.LASTNAME, ContactTable.FIRSTNAME,ContactTable.NUMBER};
        Cursor c = sqlDB.query(ContactTable.TABLE_NAME,columns,null,null,null,null,null);
        Log.d("Database Operation "," Data Loaded");
        return c;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
