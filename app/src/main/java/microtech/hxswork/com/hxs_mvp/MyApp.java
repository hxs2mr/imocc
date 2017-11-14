package microtech.hxswork.com.hxs_mvp;

import android.app.Application;

import com.joanzapata.iconify.fonts.FontAwesomeModule;

import microtech.hxswork.com.latte.init.Latte;

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
                .configure();//初始化
    }
}
