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

import com.joanzapata.iconify.widget.IconTextView;

import microtech.hxswork.com.latte.ec.R;
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
    TextInputEditText edit_login_name=null;// = bind(R.id.edit_login_name);
    TextInputEditText edit_login_password=null;// =bind(R.id.edit_login_password);
    AppCompatButton button_login=null;// = bind(R.id.button_login);//登录
    AppCompatTextView text_register =null;//= bind(R.id.text_register);//注册
    AppCompatTextView text_forget =null;//= bind(R.id.text_forget);//忘记密码
    LinearLayoutCompat login_start_linear=null;// =bind(R.id.login_start_linear);
    LinearLayoutCompat login_end_linear =null;//= bind(R.id.login_end_linear);
    IconTextView  wechat_login =null;//= bind(R.id.login_weichat);
    private  void onClickWxChat(){//微信登录
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

         edit_login_name = bind(R.id.edit_login_name);
         edit_login_password =bind(R.id.edit_login_password);
         button_login = bind(R.id.button_login);//登录
         text_register = bind(R.id.text_register);//注册
         text_forget = bind(R.id.text_forget);//忘记密码
         login_start_linear =bind(R.id.login_start_linear);
         login_end_linear = bind(R.id.login_end_linear);
          wechat_login = bind(R.id.login_weichat);

        new Start_EndListener(login_start_linear,login_end_linear);//用户名和密码一起上去
        button_login.setOnClickListener(this);
        text_register.setOnClickListener(this);
        wechat_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i  = view.getId();
        if(i == R.id.button_login){//登录操作
            login_star();
        }else if(i == R.id.text_register)
        {
            getSupportDelegate().start(new ResgisterFragment());
        }else if(i == R.id.login_weichat)
        {
            onClickWxChat();
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
