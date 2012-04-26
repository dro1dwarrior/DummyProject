package com.android.demo.trial;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
    static Cursor m_Cursor;
    private static String m_szYahooGetQuotesURL = "http://in.finance.yahoo.com/d/quotes.csv?s=%s&f=ophgkjc6cl1t1d1v";

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.newsactivity );

        m_Cursor = Util.getDB().query( DemoDatabase.MARKETS_TABLE, null, null, null, null, null, null );
        if( m_Cursor.moveToFirst() )
            Log.d( "MarketsTab", "Cursor Count is :" + m_Cursor.getCount() );
        startManagingCursor( m_Cursor );
        fetchIndicesData();
        m_adapter = new MarketsAdapter( this, m_Cursor, true );
        getListView().setAdapter( m_adapter );

        getListView().setOnItemClickListener( new OnItemClickListener()
        {

            @Override
            public void onItemClick( AdapterView< ? > arg0, View view, int arg2, long arg3 )
            {
                // TODO Auto-generated method stub
                TextView textIndice = (TextView) view.findViewById( R.id.markets_indice );
                TextView textCurrentVal = (TextView) view.findViewById( R.id.markets_current_val );
                TextView textChange = (TextView) view.findViewById( R.id.markets_change );
                TextView textSymbol = (TextView) view.findViewById( R.id.markets_symbol );
                Log.d( "*************", "item click" );
                Log.d( "*************", "item real time change is :" + textChange.getText().toString() );

                Intent intent = new Intent( MarketsTab.this, Quote.class );
                intent.putExtra( "quotesymbol", textSymbol.getText().toString() );
                startActivity( intent );
            }
        } );
    }

    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        m_Cursor.requery();
        m_adapter = new MarketsAdapter( this, m_Cursor, true );
        getListView().setAdapter( m_adapter );
    }

    @Override
    protected void onDestroy()
    {
        // TODO Auto-generated method stub
        super.onDestroy();
        m_Cursor.close();
        m_Cursor.deactivate();
    }

    public static void fetchIndicesData()
    {
        // TODO Auto-generated method stub
        try
        {
            if( Util.getNetworkStatus() )
            {
                if( m_Cursor == null )
                    m_Cursor = Util.getDB().query( DemoDatabase.MARKETS_TABLE, null, null, null, null, null, null );

                if( m_Cursor != null && m_Cursor.moveToFirst() )
                {
                    int i = 1;
                    do
                    {
                        fetchQuotesTask task = new fetchQuotesTask();
                        String szSymbol = "", szIndice = "", szGetQuoteURL = "";
                        szSymbol = m_Cursor.getString( m_Cursor.getColumnIndex( DataProvider.Markets.SYMBOL ) );
                        szIndice = m_Cursor.getString( m_Cursor.getColumnIndex( DataProvider.Markets.INDICE ) );
                        task.szSymbol = szSymbol;
                        Log.d( "MarketsTab-fetchIndicesData", "Symbol is :" + szSymbol );
                        szSymbol = URLEncoder.encode( szSymbol, "UTF-8" );
                        szGetQuoteURL = String.format( m_szYahooGetQuotesURL, szSymbol );
                        task.szQueryURL = szGetQuoteURL;
                        task.szIndice = szIndice;
                        task.execute();
                        m_Cursor.moveToNext();
                        i++;
                    }
                    while( i <= 4 );
                    // m_Cursor.close();
                    // m_Cursor.deactivate();
                }
            }
            else
            {
                Log.d( "MarketsTab", " NETWORK NOT AVAILABLE" );
            }
        }
        catch( Exception e )
        {
            // TODO: handle exception
            // m_Cursor.close();
            // m_Cursor.deactivate();
            e.printStackTrace();
        }

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
            TextView textSymbol = (TextView) view.findViewById( R.id.markets_symbol );

            String szIndice = cursor.getString( cursor.getColumnIndex( DataProvider.Markets.INDICE ) );
            String szCurrentVal = cursor.getString( cursor.getColumnIndex( DataProvider.Markets.LASTTRADEPRICE ) );
            String szChange = cursor.getString( cursor.getColumnIndex( DataProvider.Markets.REALTIMECHANGE ) );
            String szSymbol = cursor.getString( cursor.getColumnIndex( DataProvider.Markets.SYMBOL ) );

            textIndice.setText( szIndice );
            textCurrentVal.setText( szCurrentVal );
            textChange.setText( szChange );
            textSymbol.setText( szSymbol );
            if( szChange.contains( "+" ) )
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

    private static class fetchQuotesTask extends AsyncTask< String, Void, String >
    {
        String szQueryURL = "";
        String szResponse = "";
        String szIndice = "";
        String szSymbol = "";

        @Override
        protected String doInBackground( String... arg0 )
        {
            try
            {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet request = new HttpGet();
                request.setURI( new URI( szQueryURL ) );
                HttpResponse response = httpclient.execute( request );
                szResponse = inputStreamToString( response.getEntity().getContent() ).toString();
                Log.d( "MarketsTab-fetchQuotesTask", "Response is :" + szResponse );
            }
            catch( Exception e )
            {
                // TODO: handle exception
                e.printStackTrace();
                return "error";
            }
            return "success";
        }

        protected void onPostExecute( String szResult )
        {
            if( szResult == "success" )
            {
                String[] RowData = szResponse.split( "," );
                try
                {
                    Log.d( "*************************", "Post Execute ******" );
                    String szPercentChange = RowData[7].replaceAll( "\"", "" );
                    szPercentChange = szPercentChange.substring( szPercentChange.lastIndexOf( "- " ) + 1 );

                    String szWhere = DataProvider.Stocks.SYMBOL + " = '" + szSymbol + "'";
                    Log.d( "*************************", "Post Execute  WHEREEEE ******" + szWhere );

                    ContentValues marketValues = new ContentValues();
                    // marketValues.put( DataProvider.Markets.SYMBOL, szSymbol );
                    // marketValues.put( DataProvider.Markets.INDICE, szIndice );
                    marketValues.put( DataProvider.Markets.REALTIMECHANGE, RowData[6].replaceAll( "\"", "" ) );
                    marketValues.put( DataProvider.Markets.PERCENTCHANGE, szPercentChange );
                    marketValues.put( DataProvider.Markets.LASTTRADEPRICE, RowData[8] );
                    Util.getDB().update( DemoDatabase.MARKETS_TABLE, marketValues, szWhere, null );
                    m_Cursor.requery();

                    Util.getDB().delete( DemoDatabase.STOCKSS_TABLE, DataProvider.Stocks.SYMBOL + " ='" + szSymbol + "'", null );

                    ContentValues stockValues = new ContentValues();
                    stockValues.put( DataProvider.Stocks.SYMBOL, szSymbol );
                    stockValues.put( DataProvider.Stocks.NAME, szIndice );
                    stockValues.put( DataProvider.Stocks.EXCHANGE, "" );
                    stockValues.put( DataProvider.Stocks.TYPE, "" );
                    stockValues.put( DataProvider.Stocks.OPEN, RowData[0] );
                    stockValues.put( DataProvider.Stocks.CLOSE, RowData[1] );
                    stockValues.put( DataProvider.Stocks.HIGH, RowData[2] );
                    stockValues.put( DataProvider.Stocks.LOW, RowData[3] );
                    stockValues.put( DataProvider.Stocks.YEARHIGH, RowData[4] );
                    stockValues.put( DataProvider.Stocks.YEARLOW, RowData[5] );
                    stockValues.put( DataProvider.Stocks.REALTIMECHANGE, RowData[6].replaceAll( "\"", "" ) );
                    stockValues.put( DataProvider.Stocks.PERCENTCHANGE, szPercentChange );
                    stockValues.put( DataProvider.Stocks.LASTTRADEPRICE, RowData[8] );
                    stockValues.put( DataProvider.Stocks.LASTTRADETIME, RowData[9].replaceAll( "\"", "" ) );
                    stockValues.put( DataProvider.Stocks.LASTTRADEDATE, RowData[10].replaceAll( "\"", "" ) );
                    stockValues.put( DataProvider.Stocks.VOLUME, RowData[11] );
                    Util.getDB().insert( DemoDatabase.STOCKSS_TABLE, stockValues );
                }
                catch( Exception e )
                {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
            super.onPostExecute( szResult );
        }
    }

    private static StringBuilder inputStreamToString( InputStream content ) throws IOException
    {
        // TODO Auto-generated method stub
        String line = "";
        StringBuilder total = new StringBuilder();

        BufferedReader rd = new BufferedReader( new InputStreamReader( content ) );
        while( ( line = rd.readLine() ) != null )
        {
            total.append( line );
        }
        return total;
    }
}
