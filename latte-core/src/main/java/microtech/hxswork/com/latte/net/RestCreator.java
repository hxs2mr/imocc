package microtech.hxswork.com.latte.net;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import microtech.hxswork.com.latte.init.ConfigKeys;
import microtech.hxswork.com.latte.init.Latte;
import microtech.hxswork.com.latte.net.rx.RxRestService;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by microtech on 2017/11/13.
 */

public class RestCreator {//单利模式


    private  static final class ParamsHolder{
        public static final WeakHashMap<String,Object>  PARAMS = new WeakHashMap<>();
    }
public static WeakHashMap<String,Object> getParams(){
    return ParamsHolder.PARAMS;
}
    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE;
    }
    //构造全局Retrofit客户端
    private static final class RetrofitHolder{
        private static final String BASE_URL = (String) Latte.getConfigureation(ConfigKeys.API_HOST.name());

        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static final class OKHttpHolder{
        private static final int TIME_OUT = 60;//设置超时时间
        private static final  OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS = Latte.getConfigureation(ConfigKeys.INTERCEPTOR);
        private static OkHttpClient.Builder addInterceptor(){
            if(INTERCEPTORS !=null && !INTERCEPTORS.isEmpty()){
                for(Interceptor interceptor:INTERCEPTORS){
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }
        private static  final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();
    }
    //service接口
    private  static final class RestServiceHolder{
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }
    //service接口
    private  static final class RxRestServiceHolder{
        private static final RxRestService RXREST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RxRestService.class);
    }
    public static RxRestService getRxRestService(){
        return RxRestServiceHolder.RXREST_SERVICE;
    }
}
