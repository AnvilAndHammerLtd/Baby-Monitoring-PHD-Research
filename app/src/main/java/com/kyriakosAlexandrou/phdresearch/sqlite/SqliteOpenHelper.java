package com.kyriakosAlexandrou.phdresearch.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteOpenHelper extends SQLiteOpenHelper{
	//DB name and version
	private static int DATABASE_VERSION = 1;
	private static String DATABASE_NAME = "phdResearch.db";
	private TablesDB tablesDB = new TablesDB();
	
	public SqliteOpenHelper(Context context){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		//CREATE TABLES			
		db.execSQL(
			"create table " + tablesDB.getTABLE_MAIN() + "( " 
			+ "_id INTEGER PRIMARY KEY, "
			+ tablesDB.getIsRunning() + " INTEGER, "
			+ tablesDB.getTime() + " INTEGER )"
		);
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS reports");
		onCreate(db);
	}
}