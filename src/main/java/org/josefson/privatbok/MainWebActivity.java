package org.josefson.privatbok;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainWebActivity extends Activity {

    public class JsObject {
        private final WebView webView;
        private final Activity activity;

        public JsObject(final WebView webView, Activity activity) {
            this.webView = webView;
            this.activity = activity;
        }

        @JavascriptInterface
        public void obtainGoogleAccessToken(final String callbackName) {
            Log.d("JsObject", "obtainGoogleAccessToken");
            returnResult(callbackName, "'Here it is!'");
        }

        private void returnResult(final String callbackName, final String result) {
            activity.runOnUiThread(new Runnable() {
                public void run() {
                    final String js = callbackName + "(" + result + ");";
                    System.out.println("js = " + js);
                    webView.loadUrl("javascript:" + js);
                }
            });
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        final WebView webView = new WebView(this);

        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true); // enable javascript
        webView.addJavascriptInterface(new JsObject(webView, this), "android");

        final Activity activity = this;
        webView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {

        });

//        webView.loadUrl("https://privatbok-develop.meteor.com/");
//        webView.loadUrl("http://10.0.2.2:3000/");
//        webView.loadUrl("http://alterslash.org/");
        webView.loadUrl("http://jsbin.com/opuvas");

        setContentView(webView);
    }

}
