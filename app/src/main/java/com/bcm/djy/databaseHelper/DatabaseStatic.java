package com.bcm.djy.databaseHelper;

import com.bcm.djy.businesscardmanager.SearchActivity;

/**
 * Created by 10856 on 2018/4/1.
 */

//Static of the table
public class DatabaseStatic {
    public final static String DATABASE_NAME = "BusinessCard.db";
    public final static int DATABASE_VERSION = 1;

    //Name of the table
    public final static String TABLE_NAME = "card";
    //Id of the business card
    public final static String ID = "id";
    //Name of the company
    public final static String COM = "com";
    //Name of the person
    public final static String NAME = "name";
    //Tel of the person
    public final static String TEL = "tel";
    //E-mail of the person
    public final static String EMAIL = "email";
    //Last time you visit the business card
    public final static String LAST_TIME = "lastTime";
}
