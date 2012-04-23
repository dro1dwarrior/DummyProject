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

public class MarketsTab extends ListActivity
{
    private ListAdapter m_adapter = null;
    Cursor m_Cursor;

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.newsactivity );

        m_Cursor = Util.getDB().query( DemoDatabase.MARKETS_TABLE, null, null, null, null, null, null );
        startManagingCursor( m_Cursor );
        m_adapter = new MarketsAdapter( this, m_Cursor, true );
        getListView().setAdapter( m_adapter );
    }

    public class MarketsAdapter extends CursorAdapter
    {
        private LayoutInflater inflater;
        private Context context;
        private Cursor cursor;
        AlphabetIndexer alphaIndexer;

        public MarketsAdapter( Context argContext, Cursor argCursor, boolean autoRequery )
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
            TextView textIndice = (TextView) view.findViewById( R.id.markets_indice );
            TextView textCurrentVal = (TextView) view.findViewById( R.id.markets_current_val );
            TextView textChange = (TextView) view.findViewById( R.id.markets_change );

            String szIndice = cursor.getString( cursor.getColumnIndex( DataProvider.Markets.INDICE ) );
            String szCurrentVal = cursor.getString( cursor.getColumnIndex( DataProvider.Markets.CURRENT_VAL ) );
            String szChange = cursor.getString( cursor.getColumnIndex( DataProvider.Markets.CHANGE ) );

            textIndice.setText( szIndice );
            textCurrentVal.setText( szCurrentVal );
            textChange.setText( szChange );
            if(szChange.contains( "+" ))
            {
                textCurrentVal.setTextColor( getResources().getColor( R.color.green ) );
                textChange.setTextColor( getResources().getColor( R.color.green ) );
            }
            else
            {
                textCurrentVal.setTextColor( getResources().getColor( R.color.red ) );
                textChange.setTextColor( getResources().getColor( R.color.red ) );
            }
        }

        @Override
        public View newView( Context context, Cursor cursor, ViewGroup parent )
        {
            // TODO Auto-generated method stub
            final View view = inflater.inflate( R.layout.marketsslistrow, parent, false );
            return view;
        }

        @Override
        public void onContentChanged()
        {
            Log.d( "MarketsAdapter--onContentChanged()", "ContentChanged" );
        }

    }
}
