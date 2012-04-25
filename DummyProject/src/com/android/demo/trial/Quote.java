package com.android.demo.trial;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class Quote extends Activity
{
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

        String szQuoteResponse = getIntent().getStringExtra( "response" );
        String szQuoteName = getIntent().getStringExtra( "quotename" );

        Log.d( "onCreate", " Quotes : " + szQuoteResponse );
        Log.d( "onCreate", " Quotes : " + szQuoteName );
        String[] RowData = szQuoteResponse.split( "," );
        try
        {
            szOpen = RowData[0];
            szClose = RowData[1];
            szDayHigh = RowData[2];
            szDayLow = RowData[3];
            szWeekHigh = RowData[4];
            szWeekLow = RowData[5];
            szRealTimeChange = RowData[6].replaceAll( "\"", "" );
            szPercentChange = RowData[7].replaceAll( "\"", "" );
            szPercentChange = szPercentChange.substring( szPercentChange.lastIndexOf( "-" ) );
            szLastTradePrice = RowData[8];
            szLastTradeTime = RowData[9].replaceAll( "\"", "" );
            szLastTradeDate = RowData[10].replaceAll( "\"", "" );
            szVolume = RowData[11];

            txtOpen.setText( szOpen );
            txtClose.setText( szClose );
            txtDayHigh.setText( szDayHigh );
            txtDayLow.setText( szDayLow );
            txtWeekHigh.setText( szWeekHigh );
            txtWeekLow.setText( szWeekLow );
            txtRealTimeChange.setText( szRealTimeChange );
            txtPercentChange.setText( szPercentChange );
            txtLastTradePrice.setText( szLastTradePrice );
            txtLastTradeTime.setText( szLastTradeTime );
            txtLastTradeDate.setText( szLastTradeDate );
            txtVolume.setText( szVolume );

        }
        catch( Exception ex )
        {
            // handle exception
            ex.printStackTrace();
        }

    }
}
