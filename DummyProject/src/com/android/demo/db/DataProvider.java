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
}
