package com.appcode.downloadsdk.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public abstract class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "downloader.db";
    private static final int DB_VERSION = 1;


    public DBHelper(Context context) {
        this(context, null);
    }

    public DBHelper(Context context, SQLiteDatabase.CursorFactory factory) {
        this(context, factory, null);
    }

    public DBHelper(Context context, SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler errorHandler) {
        super(context, DB_NAME, factory, DB_VERSION, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        onDBCreate(sqLiteDatabase);
    }

    protected abstract void onDBCreate(SQLiteDatabase sqLiteDatabase);

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
