package microtech.hxswork.com.latte.middle.web.client;

import android.graphics.Bitmap;
import android.os.Handler;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import microtech.hxswork.com.latte.init.ConfigKeys;
import microtech.hxswork.com.latte.init.Latte;
import microtech.hxswork.com.latte.middle.IPageLoadListener;
import microtech.hxswork.com.latte.middle.web.WebFragment;
import microtech.hxswork.com.latte.middle.web.route.Router;
import microtech.hxswork.com.latte.ui.LatteLoader;
import microtech.hxswork.com.latte.util.storage.LattePreference;

/**
 * Created by microtech on 2017/11/21.
 */

public class WebViewClientImpl extends WebViewClient{
    private final WebFragment FRAGMENT;
    private IPageLoadListener mIPageLoadListener = null;
    private static final Handler HANDLER = Latte.getHandler();
    public  void setPageLoadListener(IPageLoadListener listener)
    {
        this.mIPageLoadListener = listener;
    }
    public WebViewClientImpl(WebFragment fragment) {
        this.FRAGMENT = fragment;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        return Router.getInstance().handleWebUrl(FRAGMENT,url);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if(mIPageLoadListener!= null){
            mIPageLoadListener.onLoadStart();
        }
        LatteLoader.showLoading(view.getContext());
    }
    //获取浏览器的cookie
    private  void syncCookie()
    {
        final CookieManager manager = CookieManager.getInstance();
        /*
        这个在网页中不可见
        * */
        final String webHost = Latte.getConfigureation(ConfigKeys.WEB_HOST);
        if(webHost!=null){
            if(manager.hasCookies()){
                final String cookieStr = manager.getCookie(webHost);
                if(cookieStr!=null &&!cookieStr.equals("")){
                    LattePreference.addCustomAppProfile("cookie",cookieStr);
                }
            }
        }
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        syncCookie();
        if(mIPageLoadListener!=null){
            mIPageLoadListener.onLoadStart();
        }
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                LatteLoader.stopLoading();
            }
        },1000);

    }
}
