package com.initi.thierry.sqlcontacts;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener{
    private  ListView lstView, lstViewDB;
    private Button ldPhone, ldDB, insertDB;
    private EditText fnameField, lnameField, numberField;
   List<String> contactList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lstView = (ListView)findViewById(R.id.listView);
        lstViewDB = (ListView)findViewById(R.id.listViewDb);

        ldPhone = (Button) findViewById(R.id.ld_phone_bt);
        ldDB = (Button)findViewById(R.id.ld_db_bt);
        insertDB = (Button)findViewById(R.id.insert_db_bt);
        lnameField = (EditText) findViewById(R.id.name_field);
        fnameField = (EditText) findViewById(R.id.fname_field);
        numberField = (EditText) findViewById(R.id.number_field);

        ldPhone.setOnClickListener(this);
        insertDB.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void getContacts(){
        ContentResolver cr = getContentResolver();
        Cursor cursor= null;
        Cursor cursor2 = null;
        try{
            cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        }catch(Exception e){
            Log.e("Contact =>", e.getMessage());
        }

        int nbContact = cursor.getCount();

        Log.d("NB Contact =>", " test " + nbContact);
        if(nbContact > 0){
            while(cursor.moveToNext()){
               String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if(Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0){
                    try{
                        cursor2 = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "= ?", new String[]{id}, null);
                    }catch(Exception e){
                        Log.e("Contact =>", e.getMessage());
                    }
                    while (cursor2.moveToNext()){
                        String number = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String name = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                        Log.d("Contact =>", " number " + number);
                        Log.d("Contact =>", " name " + name);
                        contactList.add("Name :" + name + " Number :" + number);

                       lstView.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,contactList));

                    }

                }
            }
        }

    }

    public void insertIntoDatabase(){
        String number = numberField.getText().toString();
        String firstName =  fnameField.getText().toString();
        String lastName = lnameField.getText().toString();
        DatabaseOperations dop = new DatabaseOperations(this);
        if(!number.isEmpty()){
            if(!firstName.isEmpty() && !lastName.isEmpty()) dop.insertContact(dop,lastName, firstName, number);
            else if(!firstName.isEmpty()) dop.insertContact(dop,null, firstName, number);
            else if(!lastName.isEmpty()) dop.insertContact(dop,lastName,null, number);
            else Log.d("INSERT_IN_DB", "Fistname or Lastname is empty");
        }else
            Log.d("INSERT_IN_DB", "Number is empty");
    }

    public void fecthDatabaseContact(){
        DatabaseOperations dop = new DatabaseOperations(this);
        Cursor c = dop.fecthContact(dop);
        if(c.getCount() > 0){
            Log.d("FETCH_FROM_DB", "DB contacts fetched");
        }else{
            Log.d("FETCH_FROM_DB", "Database is empty");
        }
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.ld_phone_bt){
            getContacts();
        }else if(v.getId() == R.id.insert_db_bt){
            insertIntoDatabase();
        }else if(v.getId() == R.id.ld_db_bt){
            fecthDatabaseContact();
        }

    }
}
