package microtech.hxswork.com.hxs_mvp;

import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import microtech.hxswork.com.latte.Delegate.LatteDelegate;
import microtech.hxswork.com.latte.net.RestClent;
import microtech.hxswork.com.latte.net.callback.IError;
import microtech.hxswork.com.latte.net.callback.IFailure;
import microtech.hxswork.com.latte.net.callback.ISuccess;
import retrofit2.http.GET;

/**
 * Created by microtech on 2017/11/10.//看成是Fragment
 */

public class AppDelegate extends LatteDelegate{
    @Override
    public Object setLayout() {
        return R.layout.deleate_app;
    }
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {//对控件做的操作
        testRestClient();
    }
    private void testRestClient()
    {
        RestClent.builder()//网络请求
                .url("http://china.chinadaily.com.cn/2017-11/13/content_34483357.htm")
                //.param("","")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        System.out.println("***********"+response);
                        //Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onIFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .get();
    }
}
