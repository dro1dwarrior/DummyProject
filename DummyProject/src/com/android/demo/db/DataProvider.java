package com.android.demo.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DataProvider
{
    private DataProvider()
    {}

    public static final class News implements BaseColumns
    {
        private News()
        {}
        public static final Uri CONTENT_URI = Uri.parse( DemoDatabase.DATABASE_PATH + "/news" );
        public static final String HEADLINE = "headline";
        public static final String SUMMARY = "summary";
        public static final String SOURCE_URL = "source_url";
        public static final String PICTURE = "picture";
    }
    
    public static final class Markets implements BaseColumns
    {
        private Markets()
        {}
        public static final Uri CONTENT_URI = Uri.parse( DemoDatabase.DATABASE_PATH + "/markets" );
        public static final String INDICE = "indice";
        public static final String CURRENT_VAL = "vurrent_val";
        public static final String CHANGE = "change";
    }
    
    public static final class Stocks implements BaseColumns
    {
        private Stocks()
        {}
        public static final Uri CONTENT_URI = Uri.parse( DemoDatabase.DATABASE_PATH + "/stocks" );
        public static final String SYMBOL = "symbol";
        public static final String NAME = "name";
        public static final String EXCHANGE = "exchange";
        public static final String TYPE = "type";
        public static final String OPEN = "open";
        public static final String CLOSE = "close";
        public static final String HIGH = "high";
        public static final String LOW = "low";
        public static final String YEARHIGH = "yearhigh";
        public static final String YEARLOW = "yearlow";
        public static final String REALTIMECHANGE = "realtimechange";
        public static final String PERCENTCHANGE = "percentchange";
        public static final String LASTTRADEPRICE = "lasttradeprice";
        public static final String LASTTRADETIME = "lasttradetime";
        public static final String LASTTRADEDATE = "lasttradedate";
        public static final String VOLUME = "volume";
    }
    
    
}
