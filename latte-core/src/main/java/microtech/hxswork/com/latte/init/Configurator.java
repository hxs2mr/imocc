package microtech.hxswork.com.latte.init;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by microtech on 2017/11/10.
 */

public class Configurator {//配置文件
    private static final HashMap<String, Object> LATTE_CONFIGS = new HashMap<>();

    private static  final ArrayList<IconFontDescriptor> ICON = new ArrayList<>();
    private Configurator() {
        LATTE_CONFIGS.put(ConfigType.CONFIG_READ.name(), false);//刚刚开初始化  配置开始 还没有配置完成
    }

    public static Configurator getInstance() {//线程安全的懒汉模式
        return Holder.INSTANCE;
    }

    final HashMap<String, Object> getLatteConfigs() {
        return LATTE_CONFIGS;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void configure() {
        initicONS();
        LATTE_CONFIGS.put(ConfigType.CONFIG_READ.name(), true);
    }

    public final Configurator withApiHost(String host) {
        LATTE_CONFIGS.put(ConfigType.API_HOST.name(), host);
        return this;
    }


    public   final Configurator withIcon(IconFontDescriptor descriptor)
    {
        ICON.add(descriptor);
        return this;
    }
    private void checkConfigyration()
    {
        final  boolean isRead = (boolean) LATTE_CONFIGS.get(ConfigType.CONFIG_READ.name());
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

    final <T> T getConfiguration(Enum<ConfigType> ket){
        checkConfigyration();
        return (T) LATTE_CONFIGS.get(ket.name());
    }
}
