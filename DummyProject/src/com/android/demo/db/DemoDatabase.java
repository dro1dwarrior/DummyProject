package com.android.demo.db;

import java.util.ArrayList;
import java.util.HashMap;

import com.android.demo.util.Util;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
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
import android.preference.PreferenceManager;
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
    public static final String STOCKSS_TABLE = "stocks";

    private static HashMap< String, String > newsProjectionMap;
    private static HashMap< String, String > marketsProjectionMap;
    private static HashMap< String, String > stocksProjectionMap;

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
        marketsProjectionMap.put( DataProvider.Markets.SYMBOL, DataProvider.Markets.SYMBOL );
        marketsProjectionMap.put( DataProvider.Markets.REALTIMECHANGE, DataProvider.Markets.REALTIMECHANGE );
        marketsProjectionMap.put( DataProvider.Markets.PERCENTCHANGE, DataProvider.Markets.PERCENTCHANGE );
        marketsProjectionMap.put( DataProvider.Markets.LASTTRADEPRICE, DataProvider.Markets.LASTTRADEPRICE );
        
        stocksProjectionMap = new HashMap< String, String >();
        stocksProjectionMap.put( DataProvider.Stocks._ID, DataProvider.Stocks._ID );
        stocksProjectionMap.put( DataProvider.Stocks.SYMBOL, DataProvider.Stocks.SYMBOL );
        stocksProjectionMap.put( DataProvider.Stocks.NAME, DataProvider.Stocks.NAME );
        stocksProjectionMap.put( DataProvider.Stocks.EXCHANGE, DataProvider.Stocks.EXCHANGE );
        stocksProjectionMap.put( DataProvider.Stocks.TYPE, DataProvider.Stocks.TYPE );
        stocksProjectionMap.put( DataProvider.Stocks.OPEN, DataProvider.Stocks.OPEN );
        stocksProjectionMap.put( DataProvider.Stocks.CLOSE, DataProvider.Stocks.CLOSE );
        stocksProjectionMap.put( DataProvider.Stocks.HIGH, DataProvider.Stocks.HIGH );
        stocksProjectionMap.put( DataProvider.Stocks.LOW, DataProvider.Stocks.LOW );
        stocksProjectionMap.put( DataProvider.Stocks.YEARHIGH, DataProvider.Stocks.YEARHIGH );
        stocksProjectionMap.put( DataProvider.Stocks.YEARLOW, DataProvider.Stocks.YEARLOW );
        stocksProjectionMap.put( DataProvider.Stocks.REALTIMECHANGE, DataProvider.Stocks.REALTIMECHANGE );
        stocksProjectionMap.put( DataProvider.Stocks.PERCENTCHANGE, DataProvider.Stocks.PERCENTCHANGE );
        stocksProjectionMap.put( DataProvider.Stocks.LASTTRADEPRICE, DataProvider.Stocks.LASTTRADEPRICE );
        stocksProjectionMap.put( DataProvider.Stocks.LASTTRADETIME, DataProvider.Stocks.LASTTRADETIME );
        stocksProjectionMap.put( DataProvider.Stocks.LASTTRADEDATE, DataProvider.Stocks.LASTTRADEDATE );
        stocksProjectionMap.put( DataProvider.Stocks.VOLUME, DataProvider.Stocks.VOLUME );        
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
            table += DataProvider.Markets.SYMBOL + " varchar,";
            table += DataProvider.Markets.REALTIMECHANGE + " varchar,";
            table += DataProvider.Markets.PERCENTCHANGE + " varchar,";
            table += DataProvider.Markets.LASTTRADEPRICE + " varchar);";
            argDB.execSQL( table );
            
            table = "";
            table = "CREATE TABLE IF NOT EXISTS " + STOCKSS_TABLE + " (";
            table += DataProvider.Stocks._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,";
            table += DataProvider.Stocks.SYMBOL + " varchar not null,";
            table += DataProvider.Stocks.NAME + " varchar,";
            table += DataProvider.Stocks.EXCHANGE + " varchar,";
            table += DataProvider.Stocks.TYPE + " varchar,";
            table += DataProvider.Stocks.OPEN + " varchar,";
            table += DataProvider.Stocks.CLOSE + " varchar,";
            table += DataProvider.Stocks.HIGH + " varchar,";
            table += DataProvider.Stocks.LOW + " varchar,";
            table += DataProvider.Stocks.YEARHIGH + " varchar,";
            table += DataProvider.Stocks.YEARLOW + " varchar,";
            table += DataProvider.Stocks.REALTIMECHANGE + " varchar,";
            table += DataProvider.Stocks.PERCENTCHANGE + " varchar,";
            table += DataProvider.Stocks.LASTTRADEPRICE + " varchar,";
            table += DataProvider.Stocks.LASTTRADETIME + " varchar,";
            table += DataProvider.Stocks.LASTTRADEDATE + " varchar,";
            table += DataProvider.Stocks.VOLUME + " varchar);";
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
        
        //dummy value needs to be add for the first time
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences( context );
        SharedPreferences.Editor editor =  sp.edit();
        if(sp.getBoolean( "firstlaunch", true ))
        {
            editor.putBoolean( "firstlaunch", false );
            editor.commit();
            
            fillNewsTable();
            fillMarketsTable();
        }
        
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
        long lVal = db.insert( argTable, null, argInitialValues );
        return lVal;
    }

    public int delete( String argTable, String argWhere, String[] argWhereArgs )
    {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int iVal = db.delete( argTable, argWhere, argWhereArgs );
        return iVal;
    }

    public int update( String argTable, ContentValues argValues, String argWhere, String[] argWhereArgs )
    {
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int iVal =db.update( argTable, argValues, argWhere, argWhereArgs );
        return iVal;
    }
    
    public void close()
    {
        mOpenHelper.close();
        Log.d("DemoDatabase-close()","close the db");
    }
    
    private void fillNewsTable()
    {
        delete( DemoDatabase.NEWS_TABLE, null, null );

        ContentValues cv = new ContentValues();
        cv.put( DataProvider.News.HEADLINE, "Collector's abduction: Bhushan refuses to mediate" );
        cv.put( DataProvider.News.SOURCE_URL, "http://www.hindustantimes.com/India-news/NewDelhi/Collector-s-abduction-Bhushan-refuses-to-mediate/Article1-845263.aspx" );
        insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "Norway custody row ends, children return to India" );
        cv.put( DataProvider.News.SOURCE_URL, "http://www.indianexpress.com/news/norway-custody-row-ends-children-return-to-india/940825/" );
        insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "The Abhishek Manu Singhvi Test of English" );
        cv.put( DataProvider.News.SOURCE_URL, "http://www.firstpost.com/india/the-abhishek-manu-singhvi-test-of-english-286347.html" );
        insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "Happy birthday, Sachin Tendulkar" );
        cv.put( DataProvider.News.SOURCE_URL, "http://www.dnaindia.com/sport/report_happy-birthday-sachin-tendulkar_1679855" );
        insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "Infosys vs TCS: a study in contrasts" );
        cv.put( DataProvider.News.SOURCE_URL, "http://in.reuters.com/article/2012/04/24/india-infosys-tcs-idINDEE83N02W20120424" );
        insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "US supports India closing missile gap with China: Think tank" );
        cv.put( DataProvider.News.SOURCE_URL,
                "http://www.hindustantimes.com/world-news/Americas/US-supports-India-closing-missile-gap-with-China-Think-tank/Article1-845258.aspx" );
        insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "Sathya Sai Trust divided on Baba idol placement? " );
        cv.put( DataProvider.News.SOURCE_URL, "http://ibnlive.in.com/news/sathya-sai-trust-divided-on-baba-idol-placement/251466-60-114.html" );
        insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "Trial sharpens focus on Breivik's mental state" );
        cv.put( DataProvider.News.SOURCE_URL, "http://www.google.com/hostednews/ap/article/ALeqM5ia0Z4CEzLa5aMaLUND4ReJoegSWA?docId=fd5666d9d6bf4641a6078b62c5ed5003" );
        insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "UN condemns Sudan air raids on rival South" );
        cv.put( DataProvider.News.SOURCE_URL,
                "http://timesofindia.indiatimes.com/world/rest-of-world/UN-condemns-Sudan-air-raids-on-rival-South/articleshow/12847877.cms" );
        insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "Exclusive: China firm boasts about missile-linked North Korea sale: envoys" );
        cv.put( DataProvider.News.SOURCE_URL, "http://www.reuters.com/article/2012/04/24/us-korea-north-china-idUSBRE83N05S20120424" );
        insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "Fresh firing kills 28 civilians in Syria's Hama" );
        cv.put( DataProvider.News.SOURCE_URL, "http://zeenews.india.com/news/world/fresh-firing-kills-28-civilians-in-syria-s-hama_771405.html" );
        insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "Sensex gains 70 points in early trade, TCS up 9.85%" );
        cv.put( DataProvider.News.SOURCE_URL, "http://www.mydigitalfc.com/stock-market/sensex-gains-70-points-early-trade-tcs-985-195" );
        insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "5 reasons why telecom stocks have fallen" );
        cv.put( DataProvider.News.SOURCE_URL, "http://profit.ndtv.com/News/Article/5-reasons-why-telecom-stocks-have-fallen-302653" );
        insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "Rupee at 3-1/2 month low; intervention eyed" );
        cv.put( DataProvider.News.SOURCE_URL, "http://in.reuters.com/article/2012/04/24/market-eye-india-rupee-idINDEE83N02020120424" );
        insert( DemoDatabase.NEWS_TABLE, cv );
    }

    private void fillMarketsTable()
    {
        delete( DemoDatabase.MARKETS_TABLE, null, null );

        ContentValues cv = new ContentValues();
        cv.put( DataProvider.Markets.INDICE, "SENSEX" );
        cv.put( DataProvider.Markets.LASTTRADEPRICE, "" );
        cv.put( DataProvider.Markets.REALTIMECHANGE, "" );
        cv.put( DataProvider.Markets.SYMBOL, "^BSESN" );
        cv.put( DataProvider.Markets.PERCENTCHANGE, "" );
        insert( DemoDatabase.MARKETS_TABLE, cv );

        cv.put( DataProvider.Markets.INDICE, "NIFTY" );
        cv.put( DataProvider.Markets.LASTTRADEPRICE, "" );
        cv.put( DataProvider.Markets.REALTIMECHANGE, "" );
        cv.put( DataProvider.Markets.PERCENTCHANGE, "" );
        cv.put( DataProvider.Markets.SYMBOL, "^NSEI" );
        insert( DemoDatabase.MARKETS_TABLE, cv );

        cv.put( DataProvider.Markets.INDICE, "BSE500" );
        cv.put( DataProvider.Markets.LASTTRADEPRICE, "" );
        cv.put( DataProvider.Markets.REALTIMECHANGE, "" );
        cv.put( DataProvider.Markets.SYMBOL, "BSE-500.BO" );
        cv.put( DataProvider.Markets.PERCENTCHANGE, "" );
        insert( DemoDatabase.MARKETS_TABLE, cv );

        cv.put( DataProvider.Markets.INDICE, "CNXMIDCAP" );
        cv.put( DataProvider.Markets.LASTTRADEPRICE, "" );
        cv.put( DataProvider.Markets.REALTIMECHANGE, "" );
        cv.put( DataProvider.Markets.SYMBOL, "^CRSMID" );
        cv.put( DataProvider.Markets.PERCENTCHANGE, "" );
        insert( DemoDatabase.MARKETS_TABLE, cv );

        cv.put( DataProvider.Markets.INDICE, "GOLD" );
        cv.put( DataProvider.Markets.LASTTRADEPRICE, "7631.50" );
        cv.put( DataProvider.Markets.REALTIMECHANGE, "+200" );
        cv.put( DataProvider.Markets.PERCENTCHANGE, "" );
        cv.put( DataProvider.Markets.SYMBOL, "" );
        insert( DemoDatabase.MARKETS_TABLE, cv );

        cv.put( DataProvider.Markets.INDICE, "SILVER" );
        cv.put( DataProvider.Markets.LASTTRADEPRICE, "7631.50" );
        cv.put( DataProvider.Markets.REALTIMECHANGE, "-400" );
        cv.put( DataProvider.Markets.PERCENTCHANGE, "" );
        cv.put( DataProvider.Markets.SYMBOL, "" );
        insert( DemoDatabase.MARKETS_TABLE, cv );

        cv.put( DataProvider.Markets.INDICE, "CRUDE_OIL" );
        cv.put( DataProvider.Markets.LASTTRADEPRICE, "7631.50" );
        cv.put( DataProvider.Markets.REALTIMECHANGE, "-80" );
        cv.put( DataProvider.Markets.PERCENTCHANGE, "" );
        cv.put( DataProvider.Markets.SYMBOL, "" );
        insert( DemoDatabase.MARKETS_TABLE, cv );
    }
}
