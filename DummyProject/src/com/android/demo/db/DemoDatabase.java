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

    public static final String TEST_TABLE_NAME = "test";

    private static HashMap< String, String > testProjectionMap;

    private static final int CUSTOMQUERY = 0;
    private static final int TEST = 1;
    private static final int TEST_ID = 2;
    
    private static final UriMatcher sUriMatcher;

    public static Context applicationContext;

    static
    {
        sUriMatcher = new UriMatcher( UriMatcher.NO_MATCH );
        sUriMatcher.addURI( DataProvider.AUTHORITY, "test", TEST );
        sUriMatcher.addURI( DataProvider.AUTHORITY, "test/#", TEST_ID );
        
        testProjectionMap = new HashMap< String, String >();
        testProjectionMap.put( DataProvider.Test._ID, DataProvider.Test._ID );
        testProjectionMap.put( DataProvider.Test.NAME, DataProvider.Test.NAME );
        testProjectionMap.put( DataProvider.Test.NUMBER, DataProvider.Test.NUMBER );
        testProjectionMap.put( DataProvider.Test.ADDRESS, DataProvider.Test.ADDRESS );
    }

    /**
     * This class helps open, create, and upgrade the database file.
     */
    private static class DBHelper extends SQLiteOpenHelper
    {
        DBHelper( Context argContext )
        {
            super( argContext, DATABASE_NAME, null, DATABASE_VERSION );
        }

        @Override
        public void onCreate( SQLiteDatabase argDB )
        {
            Log.w( TAG, "C R E A T I N G   D A T A B A S E   " );

            String table = "";
            table = "CREATE TABLE IF NOT EXISTS " + TEST_TABLE_NAME + " (";
            table += DataProvider.Test.NAME + " varchar(64) not null,";
            table += DataProvider.Test.NUMBER + " varchar(64)";
            table += DataProvider.Test.ADDRESS + "varchar(64));";
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
            Log.w( TAG, "U P G R A D I N G   D A T A B A S E   F R O M   V E R S I O N  " + argOldVersion + "  T O  " + argNewVersion );
            if( argNewVersion == 2 )
            {
                // new tables created related to broadcast group functionality
                // 1. BroadcastGroup Table
                // 2. BroadcastGroupContacts
            }
            onCreate( argDB );
        }
    }

    private SQLiteOpenHelper mOpenHelper;

    private String salt = "";

    void DemoDatabase(Context context)
    {
        mOpenHelper = new DBHelper( context );
        applicationContext = context;
    }

    public Cursor query( Uri argUri, String[] argProjection, String argSelection, String[] argSelectionArgs, String argSortOrder )
    {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        String groupBy = null;
        String having = null;

        switch( sUriMatcher.match( argUri ) )
        {
        case CUSTOMQUERY:
            return mOpenHelper.getReadableDatabase().rawQuery( argSelection, null );
        case TEST:
            qb.setTables( TEST_TABLE_NAME );
            if( argProjection == null )
            {
                qb.setProjectionMap( testProjectionMap );
            }
            break;
        case TEST_ID:
            qb.setTables( TEST_TABLE_NAME );
            argSelection = DataProvider.Test._ID + "=" + argUri.getPathSegments().get( 1 );
            break;
        default:
            throw new IllegalArgumentException( "Unknown URI " + argUri );
        }
        SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        Cursor curssor = qb.query( db, argProjection, argSelection, argSelectionArgs, groupBy, having, argSortOrder );
        return curssor;
    }

    public Uri insert( Uri argUri, ContentValues argInitialValues )
    {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri rowUri = null;
        long rowId;
        switch( sUriMatcher.match( argUri ) )
        {
        case TEST:
            rowId = db.insert( TEST_TABLE_NAME, null, argInitialValues );
            if( rowId > 0 )
            {
                rowUri = ContentUris.withAppendedId( DataProvider.Test.CONTENT_URI, rowId );
            }
            break;
        default:
            throw new SQLException( "Failed to insert row into " + argUri );
        }
        return rowUri;
    }

    public int delete( Uri argUri, String argWhere, String[] argWhereArgs )
    {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        String rowId;
        switch( sUriMatcher.match( argUri ) )
        {
        case TEST_ID:
            rowId = argUri.getPathSegments().get( 1 );
            argWhere = DataProvider.Test._ID + "=" + rowId;
        case TEST:
            count = db.delete( TEST_TABLE_NAME, argWhere, argWhereArgs );
            break;
        default:
            throw new IllegalArgumentException( "Unknown URI " + argUri );
        }

        return count;
    }

    public int update( Uri argUri, ContentValues argValues, String argWhere, String[] argWhereArgs )
    {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count;
        int match = sUriMatcher.match( argUri );
        switch( match )
        {
        case TEST_ID:
            String rowId = argUri.getPathSegments().get( 1 );
            argWhere = DataProvider.Test._ID + "=" + rowId;
        case TEST:
            count = db.update( TEST_TABLE_NAME, argValues, argWhere, argWhereArgs );
            break;
        default:
            throw new IllegalArgumentException( "Unknown URI " + argUri );
        }
        return count;
    }
}
