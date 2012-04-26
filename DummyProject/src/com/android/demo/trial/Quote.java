package com.android.demo.trial;

import com.android.demo.db.DataProvider;
import com.android.demo.db.DemoDatabase;
import com.android.demo.util.Util;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Quote extends Activity
{
    String szName = "";
    String szOpen = "";
    String szClose = "";

    String szDayHigh = "";
    String szDayLow = "";

    String szWeekHigh = "";
    String szWeekLow = "";

    String szRealTimeChange = "";
    String szPercentChange = "";

    String szLastTradePrice = "";
    String szLastTradeTime = "";

    String szLastTradeDate = "";
    String szVolume = "";

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        // TODO Auto-generated method stub
        super.onCreate( savedInstanceState );
        setContentView( R.layout.quotes );

        TextView txtName = (TextView) findViewById( R.id.stock_name );
        TextView txtOpen = (TextView) findViewById( R.id.open );
        TextView txtClose = (TextView) findViewById( R.id.close );
        TextView txtDayHigh = (TextView) findViewById( R.id.high );
        TextView txtDayLow = (TextView) findViewById( R.id.low );
        TextView txtWeekHigh = (TextView) findViewById( R.id.weekhigh );
        TextView txtWeekLow = (TextView) findViewById( R.id.weeklow );
        TextView txtRealTimeChange = (TextView) findViewById( R.id.realtimechange );
        TextView txtPercentChange = (TextView) findViewById( R.id.percentchange );
        TextView txtLastTradePrice = (TextView) findViewById( R.id.lasttradeprice );
        TextView txtLastTradeTime = (TextView) findViewById( R.id.lasttradetime );
        TextView txtLastTradeDate = (TextView) findViewById( R.id.lasttradedate );
        TextView txtVolume = (TextView) findViewById( R.id.volume );
        ImageView imageArrow = (ImageView) findViewById( R.id.image_arrow );

        String szQuoteSymbol = getIntent().getStringExtra( "quotesymbol" );

        Log.d( "onCreate", " szQuoteSymbol : " + szQuoteSymbol );

        String szWhere = DataProvider.Stocks.SYMBOL + " = '" + szQuoteSymbol + "'";
        Cursor stockCursor = Util.getDB().query( DemoDatabase.STOCKSS_TABLE, null, szWhere, null, null, null, null );

        if( stockCursor != null && stockCursor.moveToFirst() )
        {
            Log.d( "asdasd", "count" + stockCursor.getCount() );
            szName = stockCursor.getString( stockCursor.getColumnIndex( DataProvider.Stocks.NAME ) );
            szOpen = stockCursor.getString( stockCursor.getColumnIndex( DataProvider.Stocks.OPEN ) );
            szClose = stockCursor.getString( stockCursor.getColumnIndex( DataProvider.Stocks.CLOSE ) );
            szDayHigh = stockCursor.getString( stockCursor.getColumnIndex( DataProvider.Stocks.HIGH ) );
            szDayLow = stockCursor.getString( stockCursor.getColumnIndex( DataProvider.Stocks.LOW ) );
            szWeekHigh = stockCursor.getString( stockCursor.getColumnIndex( DataProvider.Stocks.YEARHIGH ) );
            szWeekLow = stockCursor.getString( stockCursor.getColumnIndex( DataProvider.Stocks.YEARLOW ) );
            szRealTimeChange = stockCursor.getString( stockCursor.getColumnIndex( DataProvider.Stocks.REALTIMECHANGE ) );
            szPercentChange = stockCursor.getString( stockCursor.getColumnIndex( DataProvider.Stocks.PERCENTCHANGE ) );
            szLastTradePrice = stockCursor.getString( stockCursor.getColumnIndex( DataProvider.Stocks.LASTTRADEPRICE ) );
            szLastTradeTime = stockCursor.getString( stockCursor.getColumnIndex( DataProvider.Stocks.LASTTRADETIME ) );
            szLastTradeDate = stockCursor.getString( stockCursor.getColumnIndex( DataProvider.Stocks.LASTTRADEDATE ) );
            szVolume = stockCursor.getString( stockCursor.getColumnIndex( DataProvider.Stocks.VOLUME ) );

            txtName.setText( szName );
            txtOpen.setText( szOpen );
            txtClose.setText( szClose );
            txtDayHigh.setText( szDayHigh );
            txtDayLow.setText( szDayLow );
            txtWeekHigh.setText( szWeekHigh );
            txtWeekLow.setText( szWeekLow );
            txtRealTimeChange.setText( szRealTimeChange );
            txtPercentChange.setText( "(" + szPercentChange + ")" );
            txtLastTradePrice.setText( szLastTradePrice );
            txtLastTradeTime.setText( szLastTradeTime );
            txtLastTradeDate.setText( szLastTradeDate );
            txtVolume.setText( szVolume );

            if( szRealTimeChange.contains( "+" ) )
            {
                imageArrow.setImageResource( R.drawable.arrow_gain_green );
                txtLastTradePrice.setTextColor( getResources().getColor( R.color.green ) );
                txtRealTimeChange.setTextColor( getResources().getColor( R.color.green ) );
                txtPercentChange.setTextColor( getResources().getColor( R.color.green ) );
            }
            else if( szRealTimeChange.contains( "-" ) )
            {
                imageArrow.setImageResource( R.drawable.arrow_loss_red );
                txtLastTradePrice.setTextColor( getResources().getColor( R.color.red ) );
                txtRealTimeChange.setTextColor( getResources().getColor( R.color.red ) );
                txtPercentChange.setTextColor( getResources().getColor( R.color.red ) );
            }
            else
            {
                imageArrow.setVisibility( View.INVISIBLE );
                txtLastTradePrice.setTextColor( getResources().getColor( R.color.header ) );
                txtRealTimeChange.setTextColor( getResources().getColor( R.color.header ) );
                txtPercentChange.setTextColor( getResources().getColor( R.color.header ) );
            }
        }
        stockCursor.close();
        stockCursor.deactivate();
    }
}
