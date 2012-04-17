package com.android.demo.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


public class Util
{
    public static Context ms_AppContext;
    
    public static void setAppContext( Context appContext )
    {
        ms_AppContext = appContext;
    }
    
    public static void setNetworkStatus()
    {
        ConnectivityManager connectivityManager = (ConnectivityManager) ms_AppContext.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo( ConnectivityManager.TYPE_MOBILE );
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo( ConnectivityManager.TYPE_WIFI );
        Log.d( "SplashScreen-onCreate()", "activeNetInfo " + activeNetInfo + " mobNetInfo " + mobNetInfo );

        if( ( mobNetInfo != null && mobNetInfo.isAvailable() && mobNetInfo.isConnected() )
                || ( activeNetInfo != null && activeNetInfo.isAvailable() && activeNetInfo.isConnected() )
                || ( wifiInfo != null && wifiInfo.isAvailable() && wifiInfo.isConnected() ) )
        {
         //   NetworkBroadcastReceiver.ms_bIsNetworkAvailable = true;
            Log.d( "SplashScreen-onCreate()", "NETWORK AVAILABLE" );
        }
        else
        {
            Log.d( "SplashScreen-onCreate()", "NETWORK NOT AVAILABLE" );
        //    NetworkBroadcastReceiver.ms_bIsNetworkAvailable = false;
        }
    }

}
