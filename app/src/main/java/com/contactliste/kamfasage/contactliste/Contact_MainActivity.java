package com.contactliste.kamfasage.contactliste;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Contact_MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact__main);
        recupContact(getContentResolver());
    }


    private void recupContact(ContentResolver contentResolver ){


        //requete avec la methode query avec en parametre URI permettant de récupérer le contact
        final Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, new String[]{ContactsContract.Data._ID, ContactsContract.Data.DISPLAY_NAME}, null, null, null);
            if (cursor == null){
                Log.e("recupContact","impossible de recuperer le contact");
                return;
            }
            if(cursor.moveToFirst() == true){
                do{
                    //pour recuperer le nom de contact
                   final String nomContact = cursor.getString(cursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
                   final long idContact = cursor.getLong(cursor.getColumnIndex(ContactsContract.Data._ID));

                    final int hasPhoneNum = cursor.getInt(cursor.getColumnIndex(ContactsContract.Data.HAS_PHONE_NUMBER));

                    if( hasPhoneNum > 0){
                        Log.d("recupContact","contact id =" + "" + idContact + "" + "et nom de contact ="+""+nomContact);
                    }



                }while (cursor.moveToNext()== true);
            }
            if(cursor.isClosed()==false){
                cursor.close();
            }
    }
}
