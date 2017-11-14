package microtech.hxswork.com.hxs_mvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import microtech.hxswork.com.latte.Delegate.LatteDelegate;
import microtech.hxswork.com.latte.activity.ProxyActivity;

//单activity
public class MainActivity extends ProxyActivity{
    @Override
    public LatteDelegate setRootDelegare() {
        return new AppDelegate();
    }
}
