package com.mayera.dbliteex;

import java.util.ArrayList;
import java.util.List;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




	public class DatabaseHandler extends SQLiteOpenHelper {
		
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME= "qnsansManager";
 
    // QAPairs table name
    private static final String TABLE_QNS_ANS = "qnsans";
 
    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_QUESTION = "question";
    private static final String KEY_ANSWER = "answer";
 
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
    	
        String CREATE_QNS_ANS_TABLE = "CREATE TABLE " + TABLE_QNS_ANS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_QUESTION + " TEXT,"
                + KEY_ANSWER + " TEXT" + ")";
        db.execSQL(CREATE_QNS_ANS_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QNS_ANS);
 
        // Create tables again
        onCreate(db);
       
    }
    
    
 // Adding new contact
    void addQAPair(QAPair qapair) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_QUESTION, qapair.getQuestion()); 
        values.put(KEY_ANSWER, qapair.getAnswer());
 
        // Inserting Row
        db.insert(TABLE_QNS_ANS, null, values);
        db.close(); // Closing database connection so many times??
    }
 
    // Getting single contact
   QAPair getQAPair(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_QNS_ANS, new String[] { KEY_ID,
                KEY_QUESTION, KEY_ANSWER }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
 
        QAPair qapair = new QAPair(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        db.close();
        return qapair;
    }
 
    // Getting All Contacts
    public List<QAPair> getAllQAPairs() {
        List<QAPair> qnsansList = new ArrayList<QAPair>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_QNS_ANS;
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                QAPair qans = new QAPair();
                qans.setID(Integer.parseInt(cursor.getString(0)));
                qans.setQuestion(cursor.getString(1));
                qans.setAnswer(cursor.getString(2));
               
                qnsansList.add(qans);
            } while (cursor.moveToNext());
        }
 cursor.close();
      db.close();
        return qnsansList;
    }
 
   
    public int updateQn_Ans(QAPair qapair) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_QUESTION, qapair.getQuestion());
        values.put(KEY_ANSWER, qapair.getAnswer());
 db.close();
        // updating row
        return db.update(TABLE_QNS_ANS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(qapair.getID()) });
    }
 
    // Deleting single QAPair
    public void deleteQn_Ans(QAPair qapair) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_QNS_ANS, KEY_ID + " = ?",
                new String[] { String.valueOf(qapair.getID()) });
        db.close();
    }
 
    // Getting contacts Count
    public int getQn_AnssCount() {
        String countQuery = "SELECT  * FROM " + TABLE_QNS_ANS;
        
        SQLiteDatabase db = this.getReadableDatabase();
        
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
 db.close();
        // return count
        return cursor.getCount();
    }
 
    
    
     
	}

