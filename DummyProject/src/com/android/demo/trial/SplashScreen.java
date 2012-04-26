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

        fillNewsTable();
        fillMarketsTable();
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
        cv.put( DataProvider.News.HEADLINE, "Collector's abduction: Bhushan refuses to mediate" );
        cv.put( DataProvider.News.SOURCE_URL, "http://www.hindustantimes.com/India-news/NewDelhi/Collector-s-abduction-Bhushan-refuses-to-mediate/Article1-845263.aspx" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "Norway custody row ends, children return to India" );
        cv.put( DataProvider.News.SOURCE_URL, "http://www.indianexpress.com/news/norway-custody-row-ends-children-return-to-india/940825/" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "The Abhishek Manu Singhvi Test of English" );
        cv.put( DataProvider.News.SOURCE_URL, "http://www.firstpost.com/india/the-abhishek-manu-singhvi-test-of-english-286347.html" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "Happy birthday, Sachin Tendulkar" );
        cv.put( DataProvider.News.SOURCE_URL, "http://www.dnaindia.com/sport/report_happy-birthday-sachin-tendulkar_1679855" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "Infosys vs TCS: a study in contrasts" );
        cv.put( DataProvider.News.SOURCE_URL, "http://in.reuters.com/article/2012/04/24/india-infosys-tcs-idINDEE83N02W20120424" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "US supports India closing missile gap with China: Think tank" );
        cv.put( DataProvider.News.SOURCE_URL,
                "http://www.hindustantimes.com/world-news/Americas/US-supports-India-closing-missile-gap-with-China-Think-tank/Article1-845258.aspx" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "Sathya Sai Trust divided on Baba idol placement? " );
        cv.put( DataProvider.News.SOURCE_URL, "http://ibnlive.in.com/news/sathya-sai-trust-divided-on-baba-idol-placement/251466-60-114.html" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "Trial sharpens focus on Breivik's mental state" );
        cv.put( DataProvider.News.SOURCE_URL, "http://www.google.com/hostednews/ap/article/ALeqM5ia0Z4CEzLa5aMaLUND4ReJoegSWA?docId=fd5666d9d6bf4641a6078b62c5ed5003" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "UN condemns Sudan air raids on rival South" );
        cv.put( DataProvider.News.SOURCE_URL,
                "http://timesofindia.indiatimes.com/world/rest-of-world/UN-condemns-Sudan-air-raids-on-rival-South/articleshow/12847877.cms" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "Exclusive: China firm boasts about missile-linked North Korea sale: envoys" );
        cv.put( DataProvider.News.SOURCE_URL, "http://www.reuters.com/article/2012/04/24/us-korea-north-china-idUSBRE83N05S20120424" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "Fresh firing kills 28 civilians in Syria's Hama" );
        cv.put( DataProvider.News.SOURCE_URL, "http://zeenews.india.com/news/world/fresh-firing-kills-28-civilians-in-syria-s-hama_771405.html" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "Sensex gains 70 points in early trade, TCS up 9.85%" );
        cv.put( DataProvider.News.SOURCE_URL, "http://www.mydigitalfc.com/stock-market/sensex-gains-70-points-early-trade-tcs-985-195" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "5 reasons why telecom stocks have fallen" );
        cv.put( DataProvider.News.SOURCE_URL, "http://profit.ndtv.com/News/Article/5-reasons-why-telecom-stocks-have-fallen-302653" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );

        cv.put( DataProvider.News.HEADLINE, "Rupee at 3-1/2 month low; intervention eyed" );
        cv.put( DataProvider.News.SOURCE_URL, "http://in.reuters.com/article/2012/04/24/market-eye-india-rupee-idINDEE83N02020120424" );
        Util.getDB().insert( DemoDatabase.NEWS_TABLE, cv );

    }

    private void fillMarketsTable()
    {
        Util.getDB().delete( DemoDatabase.MARKETS_TABLE, null, null );

        ContentValues cv = new ContentValues();
        cv.put( DataProvider.Markets.INDICE, "SENSEX" );
        cv.put( DataProvider.Markets.LASTTRADEPRICE, "" );
        cv.put( DataProvider.Markets.REALTIMECHANGE, "" );
        cv.put( DataProvider.Markets.SYMBOL, "SENSEX.BO" );
        cv.put( DataProvider.Markets.PERCENTCHANGE, "" );
        Util.getDB().insert( DemoDatabase.MARKETS_TABLE, cv );

        cv.put( DataProvider.Markets.INDICE, "NIFTY" );
        cv.put( DataProvider.Markets.LASTTRADEPRICE, "" );
        cv.put( DataProvider.Markets.REALTIMECHANGE, "" );
        cv.put( DataProvider.Markets.PERCENTCHANGE, "" );
        cv.put( DataProvider.Markets.SYMBOL, "^NSEI" );
        Util.getDB().insert( DemoDatabase.MARKETS_TABLE, cv );

        cv.put( DataProvider.Markets.INDICE, "BSE500" );
        cv.put( DataProvider.Markets.LASTTRADEPRICE, "" );
        cv.put( DataProvider.Markets.REALTIMECHANGE, "" );
        cv.put( DataProvider.Markets.SYMBOL, "BSE-500.BO" );
        cv.put( DataProvider.Markets.PERCENTCHANGE, "" );
        Util.getDB().insert( DemoDatabase.MARKETS_TABLE, cv );

        cv.put( DataProvider.Markets.INDICE, "CNXMIDCAP" );
        cv.put( DataProvider.Markets.LASTTRADEPRICE, "" );
        cv.put( DataProvider.Markets.REALTIMECHANGE, "" );
        cv.put( DataProvider.Markets.SYMBOL, "^CRSMID" );
        cv.put( DataProvider.Markets.PERCENTCHANGE, "" );
        Util.getDB().insert( DemoDatabase.MARKETS_TABLE, cv );

        cv.put( DataProvider.Markets.INDICE, "GOLD" );
        cv.put( DataProvider.Markets.LASTTRADEPRICE, "7631.50" );
        cv.put( DataProvider.Markets.REALTIMECHANGE, "+200" );
        cv.put( DataProvider.Markets.PERCENTCHANGE, "" );
        cv.put( DataProvider.Markets.SYMBOL, "" );
        Util.getDB().insert( DemoDatabase.MARKETS_TABLE, cv );

        cv.put( DataProvider.Markets.INDICE, "SILVER" );
        cv.put( DataProvider.Markets.LASTTRADEPRICE, "7631.50" );
        cv.put( DataProvider.Markets.REALTIMECHANGE, "-400" );
        cv.put( DataProvider.Markets.PERCENTCHANGE, "" );
        cv.put( DataProvider.Markets.SYMBOL, "" );
        Util.getDB().insert( DemoDatabase.MARKETS_TABLE, cv );

        cv.put( DataProvider.Markets.INDICE, "CRUDE_OIL" );
        cv.put( DataProvider.Markets.LASTTRADEPRICE, "7631.50" );
        cv.put( DataProvider.Markets.REALTIMECHANGE, "-80" );
        cv.put( DataProvider.Markets.PERCENTCHANGE, "" );
        cv.put( DataProvider.Markets.SYMBOL, "" );
        Util.getDB().insert( DemoDatabase.MARKETS_TABLE, cv );
    }
}
