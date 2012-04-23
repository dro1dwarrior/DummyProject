package com.android.demo.trial;

import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AlphabetIndexer;
import android.widget.CursorAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.android.demo.db.DataProvider;
import com.android.demo.db.DemoDatabase;
import com.android.demo.util.Util;

// import com.geodesic.android.universalIM.GoogleAnalytics.GoogleAnalytics;

public class NewsTab extends ListActivity
{
    private ListAdapter m_adapter = null;
    Cursor m_Cursor;
    
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.newsactivity);
        
        m_Cursor = Util.getDB().query( DemoDatabase.NEWS_TABLE, null, null, null, null,null,null );
        startManagingCursor( m_Cursor );
        m_adapter = new NewsAdapter( this, m_Cursor, true );
        getListView().setAdapter( m_adapter );        
    }
    
    public class NewsAdapter extends CursorAdapter
    {
        private LayoutInflater inflater;
        private Context context;
        private Cursor cursor;
        AlphabetIndexer alphaIndexer;

        public NewsAdapter( Context argContext, Cursor argCursor, boolean autoRequery )
        {
            super( argContext, argCursor );
            context = argContext;
            inflater = LayoutInflater.from( context );
            cursor = argCursor;
        }

        @Override
        public void bindView( View view, Context context, Cursor cursor )
        {
            // TODO Auto-generated method stub
            TextView textHeadline = (TextView) view.findViewById( R.id.news_headline );
            
            String szHeadline = cursor.getString( cursor.getColumnIndex( DataProvider.News.HEADLINE ) );
            
            textHeadline.setText( szHeadline );
        }

        @Override
        public View newView( Context context, Cursor cursor, ViewGroup parent )
        {
            // TODO Auto-generated method stub
            final View view = inflater.inflate( R.layout.newslistrow, parent, false );
            return view;
        }

        @Override
        public void onContentChanged()
        {
            Log.d( "NewsAdapter--onContentChanged()", "ContentChanged" );
        }

    }
}