package microtech.hxswork.com.hxs_mvp;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import microtech.hxswork.com.hxs_mvp.event.TestEvent;
import microtech.hxswork.com.latte.ec.database.DataBaseManager;
import microtech.hxswork.com.latte.ec.icon.FontEcModule;
import microtech.hxswork.com.latte.init.Latte;
import microtech.hxswork.com.latte.net.interceptors.DebugInterceptor;
import microtech.hxswork.com.latte.net.rx.AddCookieInterceptor;

/**
 * Created by microtech on 2017/11/10.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEcModule())
                .withApiHost("http://192.168.1.134:8080/php_android_api/api/")
                .withInterceptor(new DebugInterceptor("index.html",R.raw.test))
                .withWxchaAppId("wxace207babfef510d")//微信的APPID
                .withWxchartSecRet("27f0745e8470d10009c15d8173572724")//微信的scret
                .withJavaScriptinterface("web")
                .withWebEvent("test",new TestEvent())
                //添加Cookie同步拦截器
                .withInterceptor(new AddCookieInterceptor())
                .weithWebHost("https://www.baidu.com/")

                .configure();//初始化
        DataBaseManager.getInstance().init(this);//初始化数据库 用来存储用户的信息

        initStetho();
    }
    private void initStetho(){//初始化数据库隐射查看 测试用
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .build());
    }
}
