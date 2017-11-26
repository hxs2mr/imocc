package microtech.hxswork.com.latte.ec.login;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.AppCompatButton;
import android.util.Patterns;
import android.view.View;

import microtech.hxswork.com.latte.middle.MiddleFragment;
import microtech.hxswork.com.latte.ec.R;
import microtech.hxswork.com.latte.net.RestClent;
import microtech.hxswork.com.latte.net.callback.ISuccess;

/**
 * Created by microtech on 2017/11/15.
 */

public class ResgisterFragment extends MiddleFragment implements View.OnClickListener {
    TextInputEditText mName =null;//= bind(R.id.edit_sign_name);
    TextInputEditText mEmail =null;//= bind(R.id.edit_sign_email);
    TextInputEditText mPassword =null;//= bind(R.id.edit_sign_password);
    AppCompatButton button_sign=null;// =  bind(R.id.button_sign);

    private ILoginListener mILoginListener =null;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(activity instanceof ILoginListener){
            mILoginListener = (ILoginListener) activity;
        }

    }

    private boolean checkForm(){//检查输入的文本是否正确
        final  String name = mName.getText().toString();
        final  String email = mEmail.getText().toString();
        final  String password = mPassword.getText().toString();
        boolean isPass = true;
        if(name.isEmpty()){
            mName.setError("请输入姓名");
            isPass = false;
        }else {
            mName.setError(null);
        }
        if(email.isEmpty()||!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            mEmail.setError("错误的邮箱格式");
            isPass = false;
        }else {
            mEmail.setError(null);
        }
        if(password.isEmpty()|| password.length() < 6){
            mPassword.setError("密码错误");
            isPass = false;
        }else {
            mPassword.setError(null);
        }
        return isPass;
    }
    @Override
    public Object setLayout() {
        return R.layout.register_fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
         mName = bind(R.id.edit_sign_name);
         mEmail = bind(R.id.edit_sign_email);
         mPassword = bind(R.id.edit_sign_password);
         button_sign =  bind(R.id.button_sign);
        button_sign.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.button_sign) {
            if(checkForm())//检查用户注册输入的信息对不对
            {
                RestClent.builder()//网络请求
                        .url("")//请求的地址
                        .params("","")
                        .params("","")
                        .params("","")//添加参数
                        .success(new ISuccess() {
                            @Override
                            public void onSuccess(String response) {
                                LoginHandler.onSignUp(response,mILoginListener);//注册成功之后保存用户信息  达到数据的持久化

                            }
                        })
                        .build()
                        .post();

            }
        }
    }
}
