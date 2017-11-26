package microtech.hxswork.com.hxs_mvp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import microtech.hxswork.com.latte.ec.launcher.LauncherFragment;
import microtech.hxswork.com.latte.ec.login.ILoginListener;
import microtech.hxswork.com.latte.ec.login.LoginFragment;
import microtech.hxswork.com.latte.ec.main.ExBottomFragment;
import microtech.hxswork.com.latte.init.Latte;
import microtech.hxswork.com.latte.middle.MiddleFragment;
import microtech.hxswork.com.latte.activity.ProxyActivity;
import microtech.hxswork.com.latte.ec.login.ResgisterFragment;
import microtech.hxswork.com.latte.ui.luncher.ILuncherListener;
import microtech.hxswork.com.latte.ui.luncher.OnluncherFinishTag;
import qiu.niorgai.StatusBarCompat;

//单activity
public class MainActivity extends ProxyActivity implements ILoginListener,ILuncherListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null){
            actionBar.hide();
        }
        Latte.getConfigurator().withActivity(this);
        StatusBarCompat.translucentStatusBar(this,true);
    }
    @Override
    public MiddleFragment setRootDelegate() {
        return new LauncherFragment();
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnResgisterSuuecc() {

        Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLuncherFinish(OnluncherFinishTag tag) {
        switch (tag){
            case SINGED://已经登录
                Toast.makeText(this,"用户已经登录",Toast.LENGTH_SHORT);
                getSupportDelegate().startWithPop(new ExBottomFragment());//startWithPop把盏中的上一个fragmen清楚掉
                break;
            case NOT_SINGED://没有登录
                Toast.makeText(this,"用户没有登录",Toast.LENGTH_SHORT);
                getSupportDelegate().startWithPop(new LoginFragment());//startWithPop把盏中的上一个fragmen清楚掉
                break;
        }
    }
}
