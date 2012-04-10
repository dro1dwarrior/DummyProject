package com.android.demo.trial;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;

public class DemoProjectActivity extends TabActivity
{
    TabHost m_tabHost;
    /** Called when the activity is first created. */
    @Override
    public void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.main );
        
        m_tabHost = getTabHost();
        Resources res = getResources();
        Intent intent; // Reusable Intent for each tab

        intent = new Intent().setClass( this, NewsTab.class );
        LayoutInflater inflater1 = LayoutInflater.from( this );
        m_tabHost.addTab( m_tabHost.newTabSpec( getString( R.string.news_tab) )
                 .setIndicator( inflater1.inflate( R.layout.tabnews, getTabWidget(), false ) )
                 .setContent( intent ) );

        intent = new Intent().setClass( this, StocksTab.class );
        LayoutInflater inflater2 = LayoutInflater.from( this );
        m_tabHost.addTab( m_tabHost.newTabSpec( getString( R.string.stocks_tab ) )
                 .setIndicator( inflater2.inflate( R.layout.tabstocks, getTabWidget(), false ) )
                 .setContent( intent ) );

        intent = new Intent().setClass( this, GetQuotesTab.class );
        LayoutInflater inflater3 = LayoutInflater.from( this );
        m_tabHost.addTab( m_tabHost.newTabSpec( getString( R.string.get_quotes_tab ) )
                 .setIndicator( inflater3.inflate( R.layout.tabgetquotes, getTabWidget(), false ) )
                 .setContent( intent ) );

        intent = new Intent().setClass( this, SubscriptionTab.class );
        LayoutInflater inflater4 = LayoutInflater.from( this );
        m_tabHost.addTab( m_tabHost.newTabSpec( getString( R.string.subscription_tab ) )
                 .setIndicator( inflater4.inflate( R.layout.tabsubscription, getTabWidget(), false ) )
                 .setContent( intent ) );

        /*
        m_tabHost.setOnTabChangedListener( new OnTabChangeListener()
        {
            @Override
            public void onTabChanged( String tabId )
            {
                Log.d( "universalIM", "Tab Changed : " + tabId );
                if( tabId.equals( getString( R.string.contacts ) ) )
                {
                    CAPIImplementation.ms_nTabSelected = 1;
                }
                else if( tabId.equals( getString( R.string.chats ) ) )
                {
                    CAPIImplementation.ms_nTabSelected = 2;
                }
                else if( tabId.equals( getString( R.string.groups ) ) )
                {
                    CAPIImplementation.ms_nTabSelected = 3;
                }
                else if( tabId.equals( getString( R.string.settings ) ) )
                {
                    CAPIImplementation.ms_nTabSelected = 4;
                }
                else
                {
                    CAPIImplementation.ms_nTabSelected = 0;
                }
            }
        } );*/

        View view = m_tabHost.getTabWidget().getChildAt( 2 );
        //m_textUnread = (TextView) view.findViewById( R.id.textview_unread_count );

        // /////////////////////////////////////////////////////////////////////////////////////////////////////
        // Code for enabling the bottom strip;
//        RelativeLayout ll = (RelativeLayout) m_tabHost.getChildAt( 0 );
//        TabWidget tw = (TabWidget) ll.getChildAt( 0 );
//
//        Field mStripEnabled;
//        try
//        {
//            mStripEnabled = tw.getClass().getDeclaredField( "mDrawBottomStrips" );
//
//            if( !mStripEnabled.isAccessible() )
//            {
//                mStripEnabled.setAccessible( true );
//            }
//
//            mStripEnabled.set( tw, false );
//
//        }
//        catch( java.lang.NoSuchFieldException e )
//        {
//            // possibly 2.2
//            try
//            {
//                Method stripEnabled = tw.getClass().getDeclaredMethod( "setStripEnabled", boolean.class );
//                stripEnabled.invoke( tw, false );
//
//            }
//            catch( Exception e1 )
//            {
//                e1.printStackTrace();
//            }
//        }
//        catch( Exception e )
//        {
//        }
    }
}
