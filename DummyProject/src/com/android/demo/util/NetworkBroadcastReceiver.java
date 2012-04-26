package com.android.demo.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NetworkBroadcastReceiver extends BroadcastReceiver
{

    @Override
    public void onReceive( Context argContext, Intent argIntent )
    {
        Util.setNetworkStatus();
    }
}
