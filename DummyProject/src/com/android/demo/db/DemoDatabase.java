package com.android.demo.db;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Base64;
import android.util.Log;


public class DemoDatabase
{
    private ArrayList< ContentValues > contentValues = new ArrayList< ContentValues >();
    private static final int DATABASE_VERSION = 1;
    private static final String TAG = "DemoDatabase";
    private static final String DATABASE_NAME = "demodb.db";
    public  static final String DATABASE_PATH = "/data/data/com.android.demo.trial/databases/"+DATABASE_NAME;

    public static final String NEWS_TABLE = "news";
    public static final String MARKETS_TABLE = "markets";

    private static HashMap< String, String > newsProjectionMap;
    private static HashMap< String, String > marketsProjectionMap;

    public static Context applicationContext;
    
    static
    {
        newsProjectionMap = new HashMap< String, String >();
        newsProjectionMap.put( DataProvider.News._ID, DataProvider.News._ID );
        newsProjectionMap.put( DataProvider.News.HEADLINE, DataProvider.News.HEADLINE );
        newsProjectionMap.put( DataProvider.News.SUMMARY, DataProvider.News.SUMMARY );
        newsProjectionMap.put( DataProvider.News.SOURCE_URL, DataProvider.News.SOURCE_URL );
        newsProjectionMap.put( DataProvider.News.PICTURE, DataProvider.News.PICTURE );
        
        marketsProjectionMap = new HashMap< String, String >();
        marketsProjectionMap.put( DataProvider.Markets._ID, DataProvider.Markets._ID );
        marketsProjectionMap.put( DataProvider.Markets.INDICE, DataProvider.Markets.INDICE );
        marketsProjectionMap.put( DataProvider.Markets.CURRENT_VAL, DataProvider.Markets.CURRENT_VAL );
        marketsProjectionMap.put( DataProvider.Markets.LAST_VAL, DataProvider.Markets.LAST_VAL );
        
    }

    /**
     * This class helps open, create, and upgrade the database file.
     */
    private class DBHelper extends SQLiteOpenHelper
    {
        DBHelper( Context argContext )
        {
            super( argContext, DATABASE_NAME, null, DATABASE_VERSION );
            Log.d("DBHelper","constructor");
        }

        @Override
        public void onCreate( SQLiteDatabase argDB )
        {
            Log.d( TAG, "C R E A T I N G   D A T A B A S E   " );

            String table = "";
            table = "CREATE TABLE IF NOT EXISTS " + NEWS_TABLE + " (";
            table += DataProvider.News._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,";
            table += DataProvider.News.HEADLINE + " varchar not null,";
            table += DataProvider.News.SUMMARY + " varchar,";
            table += DataProvider.News.SOURCE_URL + " varchar,";
            table += DataProvider.News.PICTURE + " varchar);";
            argDB.execSQL( table );
            
            table = "";
            table = "CREATE TABLE IF NOT EXISTS " + MARKETS_TABLE + " (";
            table += DataProvider.Markets._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,";
            table += DataProvider.Markets.INDICE + " varchar not null,";
            table += DataProvider.Markets.CURRENT_VAL + " varchar,";
            table += DataProvider.Markets.LAST_VAL + " varchar);";
            argDB.execSQL( table );

        }

        @Override
        public void onOpen( SQLiteDatabase argDB )
        {
        }

        @Override
        public void onUpgrade( SQLiteDatabase argDB, int argOldVersion, int argNewVersion )
        {
            // do need to change this method when the user is doing an upgrade.
            Log.d( TAG, "U P G R A D I N G   D A T A B A S E   F R O M   V E R S I O N  " + argOldVersion + "  T O  " + argNewVersion );
            if( argNewVersion == 2 )
            {
                // new tables created related to broadcast group functionality
                // 1. BroadcastGroup Table
                // 2. BroadcastGroupContacts
            }
            onCreate( argDB );
        }
    }

    public SQLiteOpenHelper mOpenHelper;

    private String salt = "";

    public DemoDatabase(Context context)
    {
        Log.d("DemoDatabase","constructor");
        mOpenHelper = new DBHelper( context );
        mOpenHelper.getReadableDatabase();
        applicationContext = context;
    }

    public Cursor query( String argTable, String[] argProjection, String argSelection, String[] argSelectionArgs, String groupBy, String having, String argSortOrder )
    {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        qb.setTables( argTable );
        
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor curssor = qb.query( db, argProjection, argSelection, argSelectionArgs, groupBy, having, argSortOrder );
        return curssor;
    }

    public long insert( String argTable, ContentValues argInitialValues )
    {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        return db.insert( argTable, null, argInitialValues );
    }

    public int delete( String argTable, String argWhere, String[] argWhereArgs )
    {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        return db.delete( argTable, argWhere, argWhereArgs );
    }

    public int update( String argTable, ContentValues argValues, String argWhere, String[] argWhereArgs )
    {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        return db.update( argTable, argValues, argWhere, argWhereArgs );
    }
}
