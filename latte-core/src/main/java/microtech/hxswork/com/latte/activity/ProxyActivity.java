package microtech.hxswork.com.latte.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import me.yokeyword.fragmentation.SupportActivity;
import microtech.hxswork.com.latte.middle.MiddleFragment;
import microtech.hxswork.com.latte.R;

/**
 * Created by microtech on 2017/11/10.
 */

public abstract class ProxyActivity extends SupportActivity{
    public abstract MiddleFragment setRootDelegare();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContaniner(savedInstanceState);
    }
    private void initContaniner(@Nullable Bundle savedInstanceState){
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);
        setContentView(container);
        if(savedInstanceState==null)
        {
            loadRootFragment(R.id.delegate_container,setRootDelegare());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }
}
