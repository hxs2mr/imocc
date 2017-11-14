package microtech.hxswork.com.latte.init;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by microtech on 2017/11/10.
 */

public  final  class Latte {//对外工具类
            public  static Configurator init(Context context){
                getConfigureation().put(ConfigType.APPLICATION_CONTEXT.name(),context.getApplicationContext());
                return Configurator.getInstance();
            }
    public static HashMap<String,Object> getConfigureation(){
                return Configurator.getInstance().getLatteConfigs();
            }
            public static Context getApplication(){
                return (Context) getConfigureation().get(ConfigType.APPLICATION_CONTEXT.name());
            }
}
