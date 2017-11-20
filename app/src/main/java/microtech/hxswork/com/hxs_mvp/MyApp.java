package microtech.hxswork.com.hxs_mvp;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import microtech.hxswork.com.latte.ec.database.DataBaseManager;
import microtech.hxswork.com.latte.init.Latte;
import microtech.hxswork.com.latte.net.interceptors.DebugInterceptor;

/**
 * Created by microtech on 2017/11/10.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Latte.init(this)
                .withIcon(new FontAwesomeModule())
                .withApiHost("http://127.0.0.1")
                .withInterceptor(new DebugInterceptor("index",R.raw.test))
                .withWxchaAppId("wxace207babfef510d")//微信的APPID
                .withWxchartSecRet("27f0745e8470d10009c15d8173572724")//微信的scret
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
