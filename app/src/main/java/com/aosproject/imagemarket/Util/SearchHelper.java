package com.aosproject.imagemarket.Util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SearchHelper extends SQLiteOpenHelper {

    public SearchHelper(Context context) { super(context, "SearchHelper.db", null, 1);}
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE searchdata(id INTEGER PRIMARY KEY AUTOINCREMENT, searched TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS searchdata";
        db.execSQL(query);
        onCreate(db);
    }
}
