package microtech.hxswork.com.hxs_mvp.event;

import android.webkit.WebView;
import android.widget.Toast;

import microtech.hxswork.com.latte.middle.web.event.Event;

/**
 * Created by microtech on 2017/11/21.
 */

public class TestEvent extends Event {
    @Override
    public String execute(String params) {
        Toast.makeText(getContent(),getAction(),Toast.LENGTH_LONG).show();
        if(getAction().equals("test")){
            final WebView webView = getWebView();
            webView.post(new Runnable() {
                @Override
                public void run() {
                 webView.evaluateJavascript("nativeCall();",null);
                }
            });
        }
        return null;
    }
}
