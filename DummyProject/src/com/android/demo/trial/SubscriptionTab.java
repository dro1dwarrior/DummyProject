package com.android.demo.trial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.ExpandableListView.OnGroupClickListener;

// import com.geodesic.android.universalIM.GoogleAnalytics.GoogleAnalytics;

public class SubscriptionTab extends ExpandableListActivity
{
    ExpandableListAdapter m_Adapter;

    public final int[] group_drawables = { R.drawable.service_equity, R.drawable.service_commodity, R.drawable.service_ace, R.drawable.service_options };

    private final String[] groups = { "EQUITY", "COMMODITY", "ACE", "OPTIONS" };
    private final String[][] children = {
            { "Equity Futures Trading Tips", "Equity Futures Intraday Tips", "BTST/STBT Tips", "NIFTY Futures Tips", "Equity Intraday Tips Cash Segment",
                    "Equity BTST Tips", "Equity Trading Tips Cash Segment" },
            { "MCX Bullion Tips", "MCX Base Metals Tips", "MCX Crude Futures Tips", "NCDEX Agri Tips", "Comex Bullion Tips", "Nymex Crude Tips" },
            { "Ace Equity", "Ace Commodity" }, { "Stock Options Tips", "NIFTY Options Tips", "Stock Option Strategies", "NIFTY Option Strategies" } };

    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.subscriptionactivity );
        m_Adapter = new MyExpandableListAdapter( this );
        setListAdapter( m_Adapter );

        getExpandableListView().setGroupIndicator( null );

        // GoogleAnalytics.trackPageView( this, GoogleAnalytics.FAVORITESTAB_PAGE );
    }

    /* This function is called on each child click */
    public boolean onChildClick( ExpandableListView parent, View v, int groupPosition, int childPosition, long id )
    {
        Log.d("Demo Project","Inside onChildClick at groupPosition = " + groupPosition + " Child clicked at position " + childPosition );
        return true;
    }
    
    
    /* This function is called on expansion of the group */
    public void onGroupExpand( int groupPosition )
    {
        try
        {
            Log.d("Demo Project","Group exapanding Listener => groupPosition = " + groupPosition );
            for(int i = 0; i < groups.length; i++)
            {
                if(i != groupPosition && getExpandableListView().isGroupExpanded(i))
                    getExpandableListView().collapseGroup(i);
            }
        }
        catch( Exception e )
        {
            System.out.println( " groupPosition Errrr +++ " + e.getMessage() );
        }
    }

    /**
     * A simple adapter which maintains an ArrayList of photo resource Ids. Each photo is displayed as an image. This
     * adapter supports clearing the list of photos and adding a new photo.
     */
    public class MyExpandableListAdapter extends BaseExpandableListAdapter
    {
        private LayoutInflater inflater;

        public MyExpandableListAdapter( Context context )
        {
            inflater = LayoutInflater.from( context );
        }

        public Object getChild( int groupPosition, int childPosition )
        {
            return children[groupPosition][childPosition];
        }

        public long getChildId( int groupPosition, int childPosition )
        {
            return childPosition;
        }

        public int getChildrenCount( int groupPosition )
        {
            return children[groupPosition].length;
        }

        public View getChildView( int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent )
        {
            View view = inflater.inflate( R.layout.servicechildrow, parent, false );
            TextView textGroupName = (TextView) view.findViewById( R.id.textview_child_row );
            textGroupName.setText( children[groupPosition][childPosition] );

            return view;
        }

        public Object getGroup( int groupPosition )
        {
            return groups[groupPosition];
        }

        public int getGroupCount()
        {
            return groups.length;
        }

        public long getGroupId( int groupPosition )
        {
            return groupPosition;
        }

        public View getGroupView( int groupPosition, boolean isExpanded, View convertView, ViewGroup parent )
        {
            View view = inflater.inflate( R.layout.servicegrouprow, parent, false );
            TextView textGroupName = (TextView) view.findViewById( R.id.textView_group_row );
            textGroupName.setText( groups[groupPosition] );
            ImageView imageGroup = (ImageView) view.findViewById( R.id.imageview_group_row );
            imageGroup.setImageResource( group_drawables[groupPosition] );

            return view;

        }

        public boolean isChildSelectable( int groupPosition, int childPosition )
        {
            return true;
        }

        public boolean hasStableIds()
        {
            return true;
        }

    }

    // /* Creating the Hashmap for the row */
    // @SuppressWarnings("unchecked")
    // private List createGroupList()
    // {
    // ArrayList result = new ArrayList();
    // for( int i = 0 ; i < groups.length ; ++i )
    // {
    // HashMap m = new HashMap();
    // m.put( "Group Item",groups[i] ); // the key and it's value.
    // result.add( m );
    // }
    // return (List)result;
    // }
    // 
    // /* creatin the HashMap for the children */
    // @SuppressWarnings("unchecked")
    // private List createChildList()
    // {
    // ArrayList result = new ArrayList();
    // for( int i = 0 ; i < groups.length ; ++i )
    // {
    // ArrayList secList = new ArrayList();
    // for( int n = 0 ; n < children[i].length ; n++ )
    // {
    // HashMap child = new HashMap();
    // child.put( "Sub Item", children[i][n]);
    // secList.add( child );
    // }
    // result.add( secList );
    // }
    // return result;
    // }
    // public void onContentChanged () {
    // System.out.println("onContentChanged");
    // super.onContentChanged();
    // }
    // /* This function is called on each child click */
    // public boolean onChildClick( ExpandableListView parent, View v, int groupPosition,int childPosition,long id) {
    // System.out.println("Inside onChildClick at groupPosition = " + groupPosition +" Child clicked at position " +
    // childPosition);
    // return true;
    // }
    // 
    // /* This function is called on expansion of the group */
    // public void onGroupExpand (int groupPosition) {
    // try{
    // System.out.println("Group exapanding Listener => groupPosition = " + groupPosition);
    // }catch(Exception e){
    // System.out.println(" groupPosition Errrr +++ " + e.getMessage());
    // }
    // }

    // /**
    // * A simple adapter which maintains an ArrayList of photo resource Ids. Each photo is displayed as an image. This
    // * adapter supports clearing the list of photos and adding a new photo.
    // */
    // public class MyExpandableListAdapter extends BaseExpandableListAdapter
    // {
    // // Sample data set. children[i] contains the children (String[]) for groups[i].
    // private String[] groups = { "EQUITY", "COMMODITY", "ACE", "OPTIONS" };
    // private String[][] children = { { "Equity Futures Trading Tips", "Equity Futures Intraday Tips",
    // "BTST/STBT Tips", "NIFTY Futures Tips",
    // "Equity Intraday Tips Cash Segment", "Equity BTST Tips", "Equity Trading Tips Cash Segment"},
    // { "MCX Bullion Tips", "MCX Base Metals Tips", "MCX Crude Futures Tips", "NCDEX Agri Tips",
    // "Comex Bullion Tips", "Nymex Crude Tips"},
    // { "Ace Equity", "Ace Commodity"},
    // { "Stock Options Tips", "NIFTY Options Tips", "Stock Option Strategies", "NIFTY Option Strategies" } };
    // public Object getChild( int groupPosition, int childPosition )
    // {
    // return children[groupPosition][childPosition];
    // }
    //
    // public long getChildId( int groupPosition, int childPosition )
    // {
    // return childPosition;
    // }
    //
    // public int getChildrenCount( int groupPosition )
    // {
    // return children[groupPosition].length;
    // }
    //
    // public View getChildView( int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup
    // parent )
    // {
    // TextView textView = getGenericView();
    // textView.setText( getChild( groupPosition, childPosition ).toString() );
    // return textView;
    // }
    //
    // public Object getGroup( int groupPosition )
    // {
    // return groups[groupPosition];
    // }
    //
    // public int getGroupCount()
    // {
    // return groups.length;
    // }
    //
    // public long getGroupId( int groupPosition )
    // {
    // return groupPosition;
    // }
    //
    // public View getGroupView( int groupPosition, boolean isExpanded, View convertView, ViewGroup parent )
    // {
    // View = getGenericView();
    // return textView;
    // }
    //
    // public boolean isChildSelectable( int groupPosition, int childPosition )
    // {
    // return true;
    // }
    //
    // public boolean hasStableIds()
    // {
    // return true;
    // }
    //
    // }
}
