package microtech.hxswork.com.latte.ec.login;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;
import microtech.hxswork.com.latte.ec.R;
import microtech.hxswork.com.latte.ec.R2;
import microtech.hxswork.com.latte.middle.MiddleFragment;
import microtech.hxswork.com.latte.net.RestClent;
import microtech.hxswork.com.latte.net.callback.ISuccess;
import microtech.hxswork.com.latte.util.startlast.Start_EndListener;
import microtech.hxswork.com.latte.wechat.LatteWeChat;
import microtech.hxswork.com.latte.wechat.callbacks.IWeChatLoginCallBack;

/**
 * Created by microtech on 2017/11/15.
 */

public class LoginFragment extends MiddleFragment implements View.OnClickListener {
    @BindView(R2.id.edit_login_name)
    TextInputEditText edit_login_name = null;
    @BindView(R2.id.edit_login_password)
    TextInputEditText edit_login_password = null;
    @BindView(R2.id.button_login)
    AppCompatButton button_login = null;//登录
    @BindView(R2.id.text_register)
    AppCompatTextView text_register = null;//注册
    @BindView(R2.id.text_forget)
    AppCompatTextView text_forget = null;//忘记密码
    @BindView(R2.id.login_start_linear)
    LinearLayoutCompat login_start_linear = null;
    @BindView(R2.id.login_end_linear)
    LinearLayoutCompat login_end_linear = null;
    @OnClick(R2.id.login_weichat)
    void onClickWxChat(){//微信登录
        LatteWeChat
                .getInstance()
                .onLoginSuccess(new IWeChatLoginCallBack() {
            @Override
            public void onLoginSuccess(String userinfo) {
                Toast.makeText(getContext(),"微信登录成功"+userinfo,Toast.LENGTH_SHORT).show();
            }
        }).LogIn();
    }

    String login_url="http://192.168.1.134:8080/php_android_api/api/login_json.php?";

    private ILoginListener mILoginListener =null;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ILoginListener){
            mILoginListener = (ILoginListener) activity;
        }

    }
    @Override
    public Object setLayout() {
        return R.layout.login_fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        new Start_EndListener(login_start_linear,login_end_linear);//用户名和密码一起上去
        button_login.setOnClickListener(this);
        text_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i  = view.getId();
        if(i == R.id.button_login){//登录操作
            login_star();
        }else if(i == R.id.text_register)
        {
            start(new ResgisterFragment());
        }
    }

    private void login_star(){
        RestClent.builder()//网络请求
                .url(login_url)//请求的地址
                .params("name",edit_login_name.getText().toString())
                .params("password",edit_login_password.getText().toString())//添加参数
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        System.out.println("******************"+response);
                        LoginHandler.onSignIn(response,mILoginListener);//注册成功之后保存用户信息  达到数据的持久化
                    }
                })
                .build()
                .post();
    }
}
