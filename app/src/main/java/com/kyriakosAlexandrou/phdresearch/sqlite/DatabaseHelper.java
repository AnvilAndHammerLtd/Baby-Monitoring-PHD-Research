package com.kyriakosAlexandrou.phdresearch.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseHelper {
	//store variables for the OpenHelper and the database it opens
	private SqliteOpenHelper openHelper;
	private SQLiteDatabase db;
	private TablesDB tablesDB = new TablesDB();
	private Cursor cursor;
	private int timerTime;
	
	
	public DatabaseHelper (Context context){
		openHelper = new SqliteOpenHelper(context);
		db = openHelper.getWritableDatabase();
	}
	
	public void getCursorValues(){
		Cursor c = db.rawQuery("SELECT * FROM " + tablesDB.getTABLE_MAIN(), null);
		//the cursor always shows -1 after a rawQuery so we have to make it point to the first row
		if(c.moveToFirst() == true){
			this.cursor = c;
			timerTime = cursor.getInt(cursor.getColumnIndex(tablesDB.getTime()));
		} else{
			storeTimerTimeToDB(300); // 300 --> 5 minutes
			timerTime = 300;
		}
	}
	
	public void storeTimerTimeToDB(int timeValuesInSeconds){
		ContentValues contentValues = new ContentValues();
		contentValues.put(tablesDB.getTime(), timeValuesInSeconds);
		db.insert(tablesDB.getTABLE_MAIN(), null, contentValues);
//		db.update(tablesDB.getTABLE_MAIN(), contentValues, null, null);
	}
	
	public void storeChangedTimerTimeToDB(int timeValuesInSeconds){
		ContentValues contentValues = new ContentValues();
		contentValues.put(tablesDB.getTime(), timeValuesInSeconds);
		db.update(tablesDB.getTABLE_MAIN(), contentValues, null, null);
	}
	
	
	public int getIntTimerTime(){
		return timerTime;
	}
	
	
	public SQLiteDatabase getDB(){
		return db;
	}
	
	public void closeDB(){
		db.close();
	}
}