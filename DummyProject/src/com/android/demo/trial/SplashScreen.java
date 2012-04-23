package com.android.demo.trial;

import com.android.demo.db.DataProvider;
import com.android.demo.db.DemoDatabase;
import com.android.demo.util.Util;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;

public class SplashScreen extends Activity
{

    /**
     * The thread to process splash screen events
     */
    private Thread mSplashThread;

    /** Called when the activity is first created. */
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );

        // Splash screen view
        this.requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.splash );

        Util.setAppContext( this );
        Util.setNetworkStatus();

       // fillNewsTable();
       // fillMarketsTable();
        
        showSplash( this, null );
    }

    /**
     * Processes splash screen touch events
     */

    private void showSplash( final Context context, final Intent intentShortCut )
    {
        // The thread to wait for splash screen events
        mSplashThread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    synchronized( this )
                    {
                        // Wait given period of time or exit on touch
                        wait( 2000 );
                    }
                }
                catch( InterruptedException ex )
                {
                    ex.printStackTrace();
                }

                Intent intent = new Intent();
                intent.setClass( context, DemoProjectActivity.class );
                startActivity( intent );
                finish();
            }
        };

        mSplashThread.start();
    }

    @Override
    public boolean onTouchEvent( MotionEvent evt )
    {
        if( evt.getAction() == MotionEvent.ACTION_DOWN )
        {
            synchronized( mSplashThread )
            {
                mSplashThread.notifyAll();
            }
        }
        return true;
    }
    private void fillNewsTable()
    {
        Util.getDB().delete( DemoDatabase.NEWS_TABLE, null, null );
        
        ContentValues cv = new ContentValues();
        cv.put( DataProvider.News.HEADLINE, "Mining scam: SC panel for CBI probe against Yeddyurappa kin" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );
        
        cv.put( DataProvider.News.HEADLINE, "Reforms shutdown till 2014? Kaushik Basu's statement sparks political storm" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );
       
        cv.put( DataProvider.News.HEADLINE, "Reforms shutdown till 2014? Kaushik Basu's statement sparks political storm" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );
        
        cv.put( DataProvider.News.HEADLINE, "Reforms shutdown till 2014? Kaushik Basu's statement sparks political storm" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );
        
        cv.put( DataProvider.News.HEADLINE, "Fed up with army, Egyptians back in Tahrir Square" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );
        
        cv.put( DataProvider.News.HEADLINE, "'I am a very likeable person,' Breivik tells court" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );
        
        cv.put( DataProvider.News.HEADLINE, "Ed Miliband calls for cancellation of Bahrain Grand Prix" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );
        
        cv.put( DataProvider.News.HEADLINE, "Smaller Apple iPad: Why it's both a good and bad idea" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );
        
        cv.put( DataProvider.News.HEADLINE, "Assam should become a growth engine: PM" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );
        
        cv.put( DataProvider.News.HEADLINE, "Abhishek Manu Singhvi, his ex-driver inform HC about settlement" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );
        
        cv.put( DataProvider.News.HEADLINE, "Hazare, Ramdev to hold joint fast on June 3" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );
        
        cv.put( DataProvider.News.HEADLINE, "Amid militant strike, J&K government removes 43rd CRPF picket from Srinagar" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );
        
        cv.put( DataProvider.News.HEADLINE, "France elections: Campaign crescendo" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );
        
        cv.put( DataProvider.News.HEADLINE, "South Sudan 'to withdraw troops from Heglig oil field" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );
        
        cv.put( DataProvider.News.HEADLINE, "Suu Kyi MPs may refuse Myanmar seats over oath row" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );
        
    }
    private void fillMarketsTable()
    {
        Util.getDB().delete( DemoDatabase.MARKETS_TABLE, null, null );
        
        ContentValues cv = new ContentValues();
        cv.put( DataProvider.Markets.INDICE, "SENSEX" );
        cv.put( DataProvider.Markets.CURRENT_VAL, "17,383.12" );
        cv.put( DataProvider.Markets.LAST_VAL, "17,193.12" );
        Util.getDB().insert( DemoDatabase.MARKETS_TABLE, cv );
        
        cv.put( DataProvider.Markets.INDICE, "NIFTY" );
        cv.put( DataProvider.Markets.CURRENT_VAL, "5290.05" );
        cv.put( DataProvider.Markets.LAST_VAL, "5275.00" );
        Util.getDB().insert( DemoDatabase.MARKETS_TABLE, cv );
        
        cv.put( DataProvider.Markets.INDICE, "BSE500" );
        cv.put( DataProvider.Markets.CURRENT_VAL, "6780.50" );
        cv.put( DataProvider.Markets.LAST_VAL, "6720.50" );
        Util.getDB().insert( DemoDatabase.MARKETS_TABLE, cv );
        
        cv.put( DataProvider.Markets.INDICE, "CNXMIDCAP" );
        cv.put( DataProvider.Markets.CURRENT_VAL, "7631.50" );
        cv.put( DataProvider.Markets.LAST_VAL, "7610.50" );
        Util.getDB().insert( DemoDatabase.MARKETS_TABLE, cv );
        
        cv.put( DataProvider.Markets.INDICE, "GOLD" );
        cv.put( DataProvider.Markets.CURRENT_VAL, "7631.50" );
        cv.put( DataProvider.Markets.LAST_VAL, "7610.50" );
        Util.getDB().insert( DemoDatabase.MARKETS_TABLE, cv );
        
        cv.put( DataProvider.Markets.INDICE, "SILVER" );
        cv.put( DataProvider.Markets.CURRENT_VAL, "7631.50" );
        cv.put( DataProvider.Markets.LAST_VAL, "7610.50" );
        Util.getDB().insert( DemoDatabase.MARKETS_TABLE, cv );
        
        cv.put( DataProvider.Markets.INDICE, "CRUDE_OIL" );
        cv.put( DataProvider.Markets.CURRENT_VAL, "7631.50" );
        cv.put( DataProvider.Markets.LAST_VAL, "7610.50" );
        Util.getDB().insert( DemoDatabase.MARKETS_TABLE, cv );
    }
}
