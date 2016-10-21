package com.initi.thierry.sqlcontacts;

import android.provider.BaseColumns;

/**
 * Created by Thierry on 19/10/2016.
 */
public class ContactData {
    public ContactData(){

    }

    public static abstract class ContactTable implements BaseColumns{
        public final static String TABLE_NAME = "contact_table";
        public final static String ID = "_id";
        public final static String FIRSTNAME = "FIRSTNAME";
        public final static String LASTNAME = "LASRNAME";
        public final static String NUMBER = "NUMBER";
        public final static String PHOTO = "PHOTO";
        public final static String OTHERS = "OTHERS";
        public final static String DATABASE_NAME = "contact_db";

    }
}
