package microtech.hxswork.com.latte.init;

import android.content.Context;
import android.os.Handler;

import java.util.HashMap;

/**
 * Created by microtech on 2017/11/10.
 */

public  final  class Latte {//对外工具类
            public  static Configurator init(Context context){
                Configurator.getInstance()
                        .getLatteConfigs()
                        .put(ConfigKeys.APPLICATION_CONTEXT,context.getApplicationContext());
                return Configurator.getInstance();
            }

         public static Configurator getConfigurator() {
         return Configurator.getInstance();
            }
            public static <T> T getConfigureation(Object key){
             return getConfigurator().getConfiguration(key);
            }
            public static Context getApplicationContext(){
                return getConfigureation(ConfigKeys.APPLICATION_CONTEXT);
            }
    public static Handler getHandler() {
        return getConfigureation(ConfigKeys.HANDLER);
    }

}
