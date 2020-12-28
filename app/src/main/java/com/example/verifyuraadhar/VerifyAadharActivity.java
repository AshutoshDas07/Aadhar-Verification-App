package com.example.verifyuraadhar;

import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class VerifyAadharActivity extends AppCompatActivity {
    private WebView webView;
    private ProgressBar spinner;
    private View view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webviewlayout);
        initialise();
        ConnectivityManager conMgr =  (ConnectivityManager)getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        setWebView(netInfo);
     }
     //initializing
    public void initialise(){
        webView=findViewById(R.id.webView);
        spinner=findViewById(R.id.progressBar1);
        view=findViewById(R.id.relative_layout);
    }
    //setting webview
    public void setWebView(NetworkInfo netinfo){
        webView.setWebViewClient(new CustomWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webView.loadUrl("https://resident.uidai.gov.in/verify");

        if(netinfo!=null&&(netinfo.getType()==ConnectivityManager.TYPE_MOBILE||netinfo.getType()==ConnectivityManager.TYPE_WIFI)){
            //checking for all network connection
        } else{
            //If not connected show snackbar for tunring internet data
            Snackbar snackbar=Snackbar.make(view, "Check Internet Connection",Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }
    //Back press overriden for webview
    @Override
    public void onBackPressed() {
        if(webView.getUrl().equals("https://resident.uidai.gov.in/verify")){
          super.onBackPressed();
        } else if (webView.canGoBack()) {
            webView.goBack();
        } else{
            super.onBackPressed();
        }
    }

    //ProgessBar Addition in Webview
    private class CustomWebViewClient extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
                view.setVisibility(view.INVISIBLE);
                spinner.setVisibility(view.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            spinner.setVisibility(view.GONE);
            view.setVisibility(view.VISIBLE);
            super.onPageFinished(view, url);
        }
    }

}

