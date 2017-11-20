package microtech.hxswork.com.hxs_mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import microtech.hxswork.com.latte.middle.MiddleFragment;
import microtech.hxswork.com.latte.net.RestClent;
import microtech.hxswork.com.latte.net.callback.IError;
import microtech.hxswork.com.latte.net.callback.IFailure;
import microtech.hxswork.com.latte.net.callback.ISuccess;
import microtech.hxswork.com.latte.net.rx.RxRestClent;

/**
 * Created by microtech on 2017/11/10.//看成是Fragment
 */

public class AppFragment extends MiddleFragment {
    @BindView(R.id.test_content)
    TextView textView;
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
                .url("http://192.168.1.134:8080/php_android_api/api/api.php?")
                //.param("","")
                .loader(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        System.out.println("***********"+response);
                        //textView.setText(response);
                        Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
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

    private void testRxcclient(){
        RxRestClent.builder()
                .url("http://china.chinadaily.com.cn/2017-11/13/content_34483357.htm")
                .params("","")
                .build()
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {
                        Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
