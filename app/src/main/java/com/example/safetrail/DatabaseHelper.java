package com.example.safetrail;


import android.content.Context;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;


/**
 * This class extends the SQLite helper class and defines the database to the android project
 * @version 27.04.2021
 * @authors Cem Hakverdi
 */
public class DatabaseHelper extends SQLiteAssetHelper {
    private static final String DATABASE_NAME = "SafeTrail.db";
    private static final int DATABASE_VERSION = 1;

    /**
     * This constructor creates the database for the project
     * @param context is the application context
     */
    public DatabaseHelper (Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


}