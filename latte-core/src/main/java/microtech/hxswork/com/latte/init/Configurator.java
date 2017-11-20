package microtech.hxswork.com.latte.init;

import android.app.Activity;
import android.os.Handler;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by microtech on 2017/11/10.
 */

public class Configurator {//配置文件
    private static final HashMap<Object, Object> LATTE_CONFIGS = new HashMap<>();
    private static final Handler HANDLER = new Handler();
    private static  final ArrayList<IconFontDescriptor> ICON = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();
    private Configurator() {
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY.name(), false);//刚刚开初始化  配置开始 还没有配置完成
        LATTE_CONFIGS.put(ConfigKeys.HANDLER, HANDLER);
    }

    public static Configurator getInstance() {//线程安全的懒汉模式
        return Holder.INSTANCE;
    }

    final HashMap<Object, Object> getLatteConfigs() {
        return LATTE_CONFIGS;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void configure() {
        initicONS();
        LATTE_CONFIGS.put(ConfigKeys.CONFIG_READY.name(), true);
    }

    public final Configurator withApiHost(String host) {
        LATTE_CONFIGS.put(ConfigKeys.API_HOST.name(), host);
        return this;
    }


    public   final Configurator withIcon(IconFontDescriptor descriptor)
    {
        ICON.add(descriptor);
        return this;
    }
    public final Configurator withInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this;
    }
    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors){
        INTERCEPTORS.addAll(interceptors);
        LATTE_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this;
    }

    public final Configurator withWxchaAppId(String appid){
        LATTE_CONFIGS.put(ConfigKeys.WX_CHAT_APP_ID,appid);
        return this;
    }

    public final Configurator withWxchartSecRet(String secret){
        LATTE_CONFIGS.put(ConfigKeys.WX_CHAT_APP_SECRET,secret);
        return this;
    }
    public final Configurator withActivity(Activity activity){
        LATTE_CONFIGS.put(ConfigKeys.ACTIVITY,activity);
        return this;
    }

    private void checkConfigyration()
    {
        final  boolean isRead = (boolean) LATTE_CONFIGS.get(ConfigKeys.CONFIG_READY.name());
        if(!isRead)
        {
            throw  new RuntimeException("Configuration is not read");
        }
    }

    private void initicONS(){
        if(ICON.size() > 0 )
        {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICON.get(0));
            for(int  i= 0 ; i < ICON.size() ; i ++)
            {
                initializer.with(ICON.get(i));
            }
        }
    }
    private void checkConfiguration() {
        final boolean isReady = (boolean) LATTE_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if (!isReady) {
            throw new RuntimeException("Configuration is not ready,call configure");
        }
    }
    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key) {
        //checkConfiguration();
        final Object value = LATTE_CONFIGS.get(key);
        if (value == null) {
            throw new NullPointerException(key.toString() + " IS NULL");
        }
        return (T) LATTE_CONFIGS.get(key);
    }
}
