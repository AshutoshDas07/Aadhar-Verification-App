package com.example.verifyuraadhar;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class VerifyAadharActivity extends AppCompatActivity {
    private WebView webView;
    private ProgressBar spinner;
    private View view;
    private  int val=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webviewlayout);
        initialise();
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();
        setWebView(netInfo);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, final android.webkit.JsResult result) {
                new AlertDialog.Builder(VerifyAadharActivity.this)
                        .setTitle("Alert")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok,
                                new AlertDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.confirm();
                                    }
                                }).setCancelable(false).create().show();

                return true;
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
                new AlertDialog.Builder(VerifyAadharActivity.this)
                        .setTitle("Alert")
                        .setMessage(message)
                        .setPositiveButton(android.R.string.ok,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        result.confirm();
                                    }
                                }).setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        }).create().show();
                return true;
            }
        });

    }

    //initializing
    public void initialise() {
        webView = findViewById(R.id.webView);
        spinner = findViewById(R.id.progressBar1);
        view = findViewById(R.id.relative_layout);
    }

    //setting webview
    public void setWebView(NetworkInfo netinfo) {
        webView.setWebViewClient(new CustomWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webView.loadUrl("https://resident.uidai.gov.in/verify");
        webView.addJavascriptInterface(new JSClass(VerifyAadharActivity.this, webView), "HTMLOUT");
        if (netinfo != null && (netinfo.getType() == ConnectivityManager.TYPE_MOBILE || netinfo.getType() == ConnectivityManager.TYPE_WIFI)) {
            //checking for all network connection
        } else {
            //If not connected show snackbar for tunring internet data
            Snackbar snackbar = Snackbar.make(view, "Check Internet Connection", Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    //Back press overriden for webview
    @Override
    public void onBackPressed() {
        if (webView.getUrl().equals("https://resident.uidai.gov.in/verify")) {
            super.onBackPressed();
        } else if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
        super.onBackPressed();
    }

    //ProgessBar Addition in Webview
    private class CustomWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {

                view.setVisibility(view.INVISIBLE);
                spinner.setVisibility(view.VISIBLE);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            spinner.setVisibility(view.GONE);
            view.setVisibility(view.VISIBLE);
            view.loadUrl("javascript:window.HTMLOUT.processHTML(document.getElementsByClassName('alert-message')[0].innerHTML);");
            view.loadUrl("javascript:window.HTMLOUT.processHTML(document.querySelector('.mb-15 .pt-10').innerHTML+document.getElementsByClassName('d-block mb-5')[0].innerHTML+document.getElementsByClassName('d-block mb-5')[1].innerHTML+document.getElementsByClassName('d-block mb-5')[3].innerHTML);");
            super.onPageFinished(view, url);
        }
    }
}

