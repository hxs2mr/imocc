package microtech.hxswork.com.latte.net;

import android.content.Context;

import java.util.Map;
import java.util.WeakHashMap;

import microtech.hxswork.com.latte.net.callback.IError;
import microtech.hxswork.com.latte.net.callback.IFailure;
import microtech.hxswork.com.latte.net.callback.IRequest;
import microtech.hxswork.com.latte.net.callback.ISuccess;
import microtech.hxswork.com.latte.net.callback.RequestCallbacks;
import microtech.hxswork.com.latte.ui.LatteLoader;
import microtech.hxswork.com.latte.ui.LoaderStyle;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by microtech on 2017/11/13.
 */

public class RestClent {
    private final String URL;
   private  static final WeakHashMap<String,Object> PARAMS = RestCreator.getParams();
    private final IRequest REQUEST;
    private final ISuccess SUCCESS;
    private final IFailure FAILURE;
    private  final IError ERROR;

    private final ResponseBody BODY;
    private final LoaderStyle LOADER_STYLE;
    private final Context context;

    public RestClent(String url,
                     Map<String, Object> params,
                     IRequest request,
                     ISuccess success,
                     IFailure failure,
                     IError error,
                     ResponseBody body,
                    Context context,
                     LoaderStyle loaderStyle) {
        this.URL = url;
        PARAMS.putAll( params);
        this.REQUEST = request;
        this.SUCCESS = success;
        this.FAILURE = failure;
        this.ERROR = error;
        this.BODY = body;
        this.context = context;
        this.LOADER_STYLE = loaderStyle;
    }

    public static RestClientBuilder  builder(){
        return  new RestClientBuilder();
    }
    private void request(HttpMethod method)
    {
        final  RestService service = RestCreator.getRestService();
        Call<String> call = null;
        if(REQUEST!=null)
        {
            REQUEST.onRequestStart();
        }
        if(LOADER_STYLE !=null){
            LatteLoader.showLoading(context,LOADER_STYLE);
        }
        switch (method){
            case GET:
                call = service.get(URL,PARAMS);
                break;
            case POST:
                call = service.post(URL,PARAMS);
                break;
            case PUT:
                call = service.put(URL,PARAMS);
                break;
            case DELETE:
                call = service.delete(URL,PARAMS);
                break;
            default:
                break;
        }
        if(call!=null)
        {
            call.enqueue(getRequestCallback());//execute()是在主线程中执行  enqueue()是在兴起的线程中执行
        }
    }
    private Callback<String> getRequestCallback(){
        return new RequestCallbacks(
                REQUEST,
                SUCCESS,
                FAILURE,
                ERROR,
                LOADER_STYLE
        );
    }

    public final  void get(){
        request(HttpMethod.GET);
    }

    public final  void post(){
        request(HttpMethod.POST);
    }

    public final  void put(){
        request(HttpMethod.PUT);
    }
    public final  void delete(){
        request(HttpMethod.DELETE);
    }

}

