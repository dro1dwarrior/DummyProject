package com.android.demo.db;

import android.net.Uri;
import android.provider.BaseColumns;

public class DataProvider
{
    public static final String AUTHORITY = DemoDatabase.class.getName();

    private DataProvider()
    {}

    public static final Uri CUSTOMQUERY = Uri.parse( "content://" + AUTHORITY + "/customquery" );

    public static final class Test implements BaseColumns
    {
        private Test()
        {}
        public static final Uri CONTENT_URI = Uri.parse( "content://" + AUTHORITY + "/test" );
        public static final String NAME = "name";
        public static final String NUMBER = "number";
        public static final String ADDRESS = "address";
    }
}
