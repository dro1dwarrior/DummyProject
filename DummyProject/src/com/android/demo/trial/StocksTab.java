package com.android.demo.trial;

import android.app.Activity;
import android.os.Bundle;

//import com.geodesic.android.universalIM.GoogleAnalytics.GoogleAnalytics;

public class StocksTab extends Activity
{
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.stocksactivity);
        
        // GoogleAnalytics.trackPageView( this, GoogleAnalytics.FAVORITESTAB_PAGE );        
    }

}