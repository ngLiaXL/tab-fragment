package com.ngliaxl.tab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "db-name";

    public DatabaseHelper(Context context) {
        super(context, Environment.getExternalStorageDirectory() + File.separator + "out"
                + File.separator + DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE car " +
                "(id INTEGER PRIMARY KEY,name TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
