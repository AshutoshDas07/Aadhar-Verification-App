package com.example.verifyuraadhar;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

class JSClass {
    Context context;
    WebView webview;
    public JSClass(Context context,WebView webView){
        this.context=context;
        this.webview=webView;
    }

    @JavascriptInterface
    @SuppressWarnings("unused")
    public void processHTML(String html) {
        // process the html as needed by the app
        if(html.equals("Please Enter Valid Captcha")){
            Toast.makeText(context,"Verification Failed",Toast.LENGTH_LONG).show();
            Intent i=new Intent(context,FailedActivity.class);
            i.putExtra("status","Please Enter Valid Captcha");
            context.startActivity(i);
        }else if(html.equals("INPUT_RES_UID_NOT_FOUND_GENERIC")) {
            Toast.makeText(context,"Verification Failed",Toast.LENGTH_LONG).show();
            Intent i=new Intent(context,FailedActivity.class);
            i.putExtra("status","INPUT RES UID NOT FOUND GENERIC");
            context.startActivity(i);
        }else{
            Log.e("TAG", html);
            String[] words = html.split(" ");
            String status_text = "";
            status_text += words[0] + " " + words[1] + " " + words[2] + " " + words[3].substring(0, 6);
            String age_text = "";
            age_text += words[6].substring(3, words[6].length() - 10);
            String gender_text = "";
            gender_text += words[8].substring(3, words[8].length() - 10);
            String number_text = "";
            number_text += words[11].substring(3, words[11].length() - 4);
            for (int i = 0; i < words.length; i++) {
                Log.e("TAG", words[i]);
            }
            Log.e("TAG", status_text);
            Log.e("TAG", age_text);
            Log.e("TAG", gender_text);
            Log.e("TAG", number_text);
            Intent i = new Intent(context, ResultActivity.class);
            i.putExtra("status_text", status_text);
            i.putExtra("age_text", age_text);
            i.putExtra("gender_text", gender_text);
            i.putExtra("number_text", number_text);
            Toast.makeText(context, "Verification Completed !", Toast.LENGTH_LONG).show();
            context.startActivity(i);
        }
    }
}
