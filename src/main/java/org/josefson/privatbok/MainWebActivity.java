package org.josefson.privatbok;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;


public class MainWebActivity extends Activity {

    private WebView mWebView1;
    private WebView mWebView2;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_web_layout);
        mWebView1 = (WebView) (findViewById(R.id.webView1));
        mWebView2 = (WebView) (findViewById(R.id.webView2));

//        mWebView1.getSettings().setAllowFileAccess(true);
        mWebView1.getSettings().setDomStorageEnabled(true);
        mWebView1.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView1.getSettings().setSupportMultipleWindows(true);
//        mWebView1.getSettings().setLoadsImagesAutomatically(true); // enable image loading
        mWebView1.getSettings().setJavaScriptEnabled(true); // enable javascript

        final Activity activity = this;
        final WebViewClient webViewClient = new WebViewClient() {
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Toast.makeText(activity, description, Toast.LENGTH_SHORT).show();
            }
        };
        mWebView1.setWebViewClient(webViewClient);

        mWebView1.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(WebView view, boolean dialog, boolean userGesture, Message resultMsg) {
                Toast.makeText(activity, "onCreateWindow", Toast.LENGTH_SHORT).show();

                mWebView1.setVisibility(View.INVISIBLE);
                mWebView2.getSettings().setJavaScriptEnabled(true);
                mWebView2.setWebChromeClient(this);
                mWebView2.setWebViewClient(new WebViewClient());
                mWebView1.getSettings().setDomStorageEnabled(true);
                mWebView2.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(mWebView2);
                resultMsg.sendToTarget();
                return true;
            }
        });

        mWebView1.loadUrl("https://privatbok.meteor.com/");
//        mWebView1.loadUrl("http://alterslash.org/");


    }

}
