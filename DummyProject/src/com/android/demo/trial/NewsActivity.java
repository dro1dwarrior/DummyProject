package com.android.demo.trial;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class NewsActivity extends Activity
{
    String m_szNewsURL;
    WebView m_WebView;
    
    @Override
    protected void onCreate( Bundle savedInstanceState )
    {
        // Let's display the progress in the activity title bar, like the
        // browser app does.
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        
        super.onCreate( savedInstanceState );
        setContentView( R.layout.news_activity);
        
        m_szNewsURL = getIntent().getStringExtra( "news_url" );
        m_WebView = (WebView) findViewById( R.id.webview_news );
    
        m_WebView.getSettings().setJavaScriptEnabled(true);

        final Activity activity = this;
        m_WebView.setWebChromeClient(new WebChromeClient() {
          public void onProgressChanged(WebView view, int progress) {
            // Activities and WebViews measure progress with different scales.
            // The progress meter will automatically disappear when we reach 100%
            activity.setProgress(progress * 100);
          }
        });
        m_WebView.setWebViewClient(new WebViewClient() {
          public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(activity, "Oh no! " + description, Toast.LENGTH_SHORT).show();
          }
        });
        
        m_WebView.loadUrl( m_szNewsURL );
        
    
    }
}
