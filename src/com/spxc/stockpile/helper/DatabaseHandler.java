package com.spxc.stockpile.helper;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "dataManager";

    // Contacts table name
    private static final String TABLE_DATAS = "datas";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_ICON = "icon";
    private static final String KEY_DEVELOPER = "developer";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_VERSION = "version";
    private static final String KEY_DOWNLOAD = "download";
    private static final String KEY_UPDATED = "updated";
    private static final String KEY_SIZE = "size";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_STATISTICS = "statistics";
    private static final String KEY_PACKAGE = "compackage";
    
    

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATAS_TABLE = "CREATE TABLE " + TABLE_DATAS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_ICON + " TEXT," + KEY_DEVELOPER + " TEXT," + KEY_DESCRIPTION + " TEXT," + KEY_VERSION + " TEXT," + KEY_DOWNLOAD+ " TEXT," + KEY_UPDATED + " TEXT," + KEY_SIZE + " TEXT," + KEY_CATEGORY + " TEXT," + KEY_STATISTICS + " TEXT," + KEY_PACKAGE + " TEXT" + ")";
        db.execSQL(CREATE_DATAS_TABLE);
    }

    public void dropTable (SQLiteDatabase db, String table) {
    	table = TABLE_DATAS;
    	db.execSQL("DROP TABLE IF EXISTS " + table + "");
    	onCreate(db);
    }
    
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATAS);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    public // Adding new contact
    void addData(Datas data) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, data.getName()); 
        values.put(KEY_ICON, data.getIcon()); 
        values.put(KEY_DEVELOPER, data.getDeveloper()); 
        values.put(KEY_DESCRIPTION, data.getDescription()); 
        values.put(KEY_VERSION, data.getVersion()); 
        values.put(KEY_DOWNLOAD, data.getDownload()); 
        values.put(KEY_UPDATED, data.getUpdated()); 
        values.put(KEY_SIZE, data.getSize()); 
        values.put(KEY_CATEGORY, data.getCategory()); 
        values.put(KEY_STATISTICS, data.getStatistics()); 
        values.put(KEY_PACKAGE, data.getPackage()); 

        // Inserting Row
        db.insert(TABLE_DATAS, null, values);
        db.close(); // Closing database connection
    }

    // Getting single contact
    public Datas getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DATAS, new String[] { KEY_ID,
                KEY_NAME, KEY_ICON, KEY_DEVELOPER, KEY_DESCRIPTION, KEY_VERSION, KEY_DOWNLOAD, KEY_UPDATED, KEY_SIZE, KEY_CATEGORY, KEY_STATISTICS, KEY_PACKAGE}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Datas data = new Datas(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2),cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11));
        // return contact
        return data;
    }

 public Datas getItemOnly(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_DATAS, new String[] { KEY_ID,
        		KEY_NAME, KEY_ICON, KEY_DEVELOPER, KEY_DESCRIPTION, KEY_VERSION, KEY_DOWNLOAD, KEY_UPDATED, KEY_SIZE, KEY_CATEGORY, KEY_STATISTICS, KEY_PACKAGE}, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Datas data = new Datas(Integer.parseInt(cursor.getString(0)),
        		cursor.getString(1), cursor.getString(2),cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10), cursor.getString(11));
        // return contact
        return data;
    }

    // Getting All Contacts
    public List<Datas> getAllDatas(String category) {
        List<Datas> dataList = new ArrayList<Datas>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DATAS + " WHERE " + KEY_CATEGORY + "=" + category;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Datas data = new Datas();
                data.setID(Integer.parseInt(cursor.getString(0)));
                data.setName(cursor.getString(1));
                data.setIcon(cursor.getString(2));
                data.setDeveloper(cursor.getString(3));
                data.setDescription(cursor.getString(4));
                data.setVersion(cursor.getString(5));
                data.setDownload(cursor.getString(6));
                data.setUpdated(cursor.getString(7));
                data.setSize(cursor.getString(8));
                data.setCategory(cursor.getString(9));
                data.setStatistics(cursor.getString(10));
                data.setPackage(cursor.getString(11));
                // Adding contact to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        // return contact list
        return dataList;
    }
    
    public List<Datas> getFeaturedDatas() {
        List<Datas> dataList = new ArrayList<Datas>();
        String cat1 = "1";
        String cat2 = "2";
        String cat3 = "3";
        String cat4 = "4";
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DATAS + " WHERE " + KEY_CATEGORY + "=" + cat1 
        		+ " OR " + KEY_CATEGORY + "=" + cat2 
        		+ " OR " + KEY_CATEGORY + "=" + cat4 
        		+ " ORDER BY id DESC LIMIT 3";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Datas data = new Datas();
                data.setID(Integer.parseInt(cursor.getString(0)));
                data.setName(cursor.getString(1));
                data.setIcon(cursor.getString(2));
                data.setDeveloper(cursor.getString(3));
                data.setDescription(cursor.getString(4));
                data.setVersion(cursor.getString(5));
                data.setDownload(cursor.getString(6));
                data.setUpdated(cursor.getString(7));
                data.setSize(cursor.getString(8));
                data.setCategory(cursor.getString(9));
                data.setStatistics(cursor.getString(10));
                data.setPackage(cursor.getString(11));
                // Adding contact to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        // return contact list
        return dataList;
    }
    
    public List<Datas> getFeaturedTweaksDatas(String category) {
        List<Datas> dataList = new ArrayList<Datas>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DATAS + " WHERE " + KEY_CATEGORY + "=" + category + " ORDER BY id DESC LIMIT 3";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Datas data = new Datas();
                data.setID(Integer.parseInt(cursor.getString(0)));
                data.setName(cursor.getString(1));
                data.setIcon(cursor.getString(2));
                data.setDeveloper(cursor.getString(3));
                data.setDescription(cursor.getString(4));
                data.setVersion(cursor.getString(5));
                data.setDownload(cursor.getString(6));
                data.setUpdated(cursor.getString(7));
                data.setSize(cursor.getString(8));
                data.setCategory(cursor.getString(9));
                data.setStatistics(cursor.getString(10));
                data.setPackage(cursor.getString(11));
                // Adding contact to list
                dataList.add(data);
            } while (cursor.moveToNext());
        }

        // return contact list
        return dataList;
    }

    // Updating single contact
    public void updateDatas(int position, String value) {
        SQLiteDatabase db = this.getWritableDatabase();

        String update = "UPDATE datas SET "+ KEY_STATISTICS  + " = '" + value +"' WHERE ID = " + position; 
        db.execSQL(update);

    }

    public void deleteSingleItem (int position) {
    	SQLiteDatabase db = this.getWritableDatabase();
        position = position + 1;
        String id = String.valueOf(position);
        
        String DELETE_ITEM = "DELETE FROM " + TABLE_DATAS + " WHERE id=" + id;
        db.execSQL(DELETE_ITEM);
        db.close();
    }
    
    public void deleteAll() {
    	SQLiteDatabase db = this.getWritableDatabase();
    	
    	db.execSQL("DELETE FROM " + TABLE_DATAS);
        db.close();
    }
    
    // Deleting single contact
        public void deleteValues(int position) {
            SQLiteDatabase db = this.getWritableDatabase();
            position = position + 1;
            String id = String.valueOf(position);
            db.delete(TABLE_DATAS, KEY_ID + "="+id,null);
            //Updating table
            //Creating temporary table

            String CREATE_TABLE_COPY = "CREATE TABLE " + "COPIED_TABLE" + "("
                    + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                    + KEY_ICON + " TEXT," + KEY_DEVELOPER + " TEXT," + KEY_DESCRIPTION + " TEXT," + KEY_VERSION + " TEXT," + KEY_DOWNLOAD+ " TEXT," + KEY_UPDATED + " TEXT," + KEY_SIZE + " TEXT," + KEY_CATEGORY + " TEXT," + KEY_STATISTICS + " TEXT," + KEY_PACKAGE + " TEXT" + ")";
            db.execSQL(CREATE_TABLE_COPY);
            // Copying necessary columns to new temporary table
            String db_insert_command;
            db_insert_command = "INSERT INTO COPIED_TABLE (" + KEY_NAME +", " + KEY_ICON + ", " + KEY_DEVELOPER + ", " + KEY_DESCRIPTION + ", " + KEY_VERSION + ", " + KEY_DOWNLOAD + ", " + KEY_UPDATED + ", " + KEY_SIZE + ", " + KEY_CATEGORY + ", " + KEY_STATISTICS + ", " + KEY_PACKAGE + ") SELECT " + KEY_NAME +", " + KEY_ICON + ", " + KEY_DEVELOPER + ", " + KEY_DESCRIPTION + ", " + KEY_VERSION + ", " + KEY_DOWNLOAD + ", " + KEY_UPDATED + ", " + KEY_SIZE + ", " + KEY_CATEGORY + ", " + KEY_STATISTICS + ", " + KEY_PACKAGE + " FROM "+ TABLE_DATAS;
            System.out.println(db_insert_command);
            db.execSQL(db_insert_command);
            //Dropping old table
            db.execSQL("DROP TABLE " + TABLE_DATAS);
            //Creating old table again
            String CREATE_TABLE = "CREATE TABLE " + TABLE_DATAS + "("
                    + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                    + KEY_ICON + " TEXT," + KEY_DEVELOPER + " TEXT," + KEY_DESCRIPTION + " TEXT," + KEY_VERSION + " TEXT," + KEY_DOWNLOAD+ " TEXT," + KEY_UPDATED + " TEXT," + KEY_SIZE + " TEXT," + KEY_CATEGORY + " TEXT," + KEY_STATISTICS + " TEXT," + KEY_PACKAGE + " TEXT" + ")";
            db.execSQL(CREATE_TABLE);
            //Copying all fields from temporary table to newly created old table
            db.execSQL("INSERT INTO " + TABLE_DATAS + " SELECT * FROM COPIED_TABLE");
            //Dropping temporary table
            db.execSQL("DROP TABLE COPIED_TABLE");
            db.close();
        }


    // Getting contacts Count
    public int getDatasCount() {
        String countQuery = "SELECT  * FROM " + TABLE_DATAS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        //cursor.close();

        // return count
        return cursor.getCount();
    }

	public Cursor query(String tableDatas, String[] strings, String string,
			String[] strings2, Object object, Object object2, Object object3,
			Object object4) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean checkIfExist(String name) {
		boolean status = false;
	    SQLiteDatabase db = this.getReadableDatabase();

	    Cursor cursor = db.query(TABLE_DATAS, new String[] { KEY_ID,
	            KEY_NAME, KEY_DOWNLOAD }, KEY_NAME + "=?",
	            new String[] { name }, null, null, null, null);


	    if(cursor.moveToFirst()){
	    	status = true;
	        return status;
	    }else{
	    	status = false;
	        return status;
	    }

	}

}