package com.f.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "dbWeatherBody";

    // Contacts table name
    private static final String TABLE_PROFILE = "profileTable";
    private static final String TABLE_CARD = "cardTable";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_IMAGE = "image";

    private static final  String KEY_cardno = "cardno";
    private static final  String KEY_cardname = "nameoncard";
    private static final  String KEY_cardtype = "cardtype";
    private static final  String KEY_expirydate = "expirydate";

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_profile_TABLE = "CREATE TABLE " + TABLE_PROFILE + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " Varchar(50) ,"
                + KEY_LNAME + " Varchar(50) ,"
                + KEY_IMAGE + " BLOB" + ")";
        db.execSQL(CREATE_profile_TABLE);

        String CREATE_CARD_TABLE = "CREATE TABLE " + TABLE_CARD + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_cardno + " Varchar(50) ,"
                + KEY_cardname + " Varchar(50) ,"
                + KEY_cardtype + " Varchar(50) ,"
                + KEY_expirydate + " Varchar(50)  )";
        db.execSQL(CREATE_CARD_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARD);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read) Operations
     */
// Adding new profile
    public  int addProfile(ProfileModel profilemodel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, profilemodel._name); // profile Name
        values.put(KEY_LNAME, profilemodel._lname); // profile Name
        values.put(KEY_IMAGE, profilemodel._image); // profile Phone

        // Inserting Row
        long id= db.insert(TABLE_PROFILE, null, values);
        db.close(); // Closing database connection
         return  (int) id ;
    }
    public  int updateProfile(ProfileModel profilemodel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, profilemodel._name); // profile Name
        values.put(KEY_LNAME, profilemodel._lname); // profile Name
        values.put(KEY_IMAGE, profilemodel._image); // profile Phone
//db.update("table", values, "package_name = ? and other_field = ?", new String[]{"com.mynamespase.db", "test"});

      int totalrow=  db.update(TABLE_PROFILE, values,"id= ?",new String[]{""+profilemodel.getID()});
        db.close(); // Closing database connection
        return totalrow;
    }
    // Adding new CardDetails
    public  int addCard(CardModel cardmodel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_cardno, cardmodel._cardno); // card no
        values.put(KEY_cardname, cardmodel._cardname); //  Name on card
        values.put(KEY_cardtype, cardmodel._cardtype); // card type
        values.put(KEY_expirydate, cardmodel._expirydate); // card _expirydate
        // Inserting Row
        long id=  db.insert(TABLE_CARD, null, values);
        db.close(); // Closing database connection
        return (int)id;
    }

    public  void updateCard(CardModel cardmodel) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_cardno, cardmodel._cardno); // card no
        values.put(KEY_cardname, cardmodel._cardname); //  Name on card
        values.put(KEY_cardtype, cardmodel._cardtype); // card type
        values.put(KEY_expirydate, cardmodel._expirydate); // card _expirydate
        // Inserting Row

        db.update(TABLE_CARD, values,"id= ?",new String[]{""+cardmodel.getID()});
        db.close(); // Closing database connection
    }
    // Getting single contact
    ProfileModel getProfile(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PROFILE, new String[] { KEY_ID,
                        KEY_NAME, KEY_IMAGE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ProfileModel profilemodel = new ProfileModel(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),cursor.getString(2), cursor.getBlob(3));

        // return contact
        return profilemodel;

    }

    // Getting Profile
    public  ProfileModel getProfile() {

        // Select All Query
        String selectQuery = "SELECT * FROM "+TABLE_PROFILE+" ORDER BY fname";
        ProfileModel objModel = new ProfileModel();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                objModel.setID(Integer.parseInt(cursor.getString(0)));
                objModel.setName(cursor.getString(1));
                objModel.setLname(cursor.getString(2));
                objModel.setImage(cursor.getBlob(3));
                // Adding contact to list
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        db.close();
        // return contact list
        return objModel;
    }

    public  CardModel getCard() {

        // Select All Query
        String selectQuery = "SELECT * FROM "+TABLE_CARD+" ORDER BY id asc";
        CardModel objModel = new CardModel();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {


                objModel.setID(Integer.parseInt(cursor.getString(0)));
                objModel.set_cardname(cursor.getString(2));
                objModel.set_cardtype(cursor.getString(3));
                objModel.set_carno(cursor.getString(1));
                objModel.set_expirydate(cursor.getString(4));
                // Adding contact to list
            } while (cursor.moveToNext());
        }
        // close inserting data from database
        db.close();
        // return contact list
        return objModel;
    }
}