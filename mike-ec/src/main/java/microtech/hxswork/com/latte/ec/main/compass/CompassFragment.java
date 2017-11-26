package microtech.hxswork.com.latte.ec.main.compass;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import microtech.hxswork.com.latte.ec.R;
import microtech.hxswork.com.latte.middle.bottom.BottomItemFragment;
import microtech.hxswork.com.latte.middle.web.WebFragmentImpl;

/**
 * Created by microtech on 2017/11/17.发现
 */

public class CompassFragment extends BottomItemFragment{
    @Override
    public Object setLayout() {
        return R.layout.compass_fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final WebFragmentImpl fragment = WebFragmentImpl.create("index.html");//加载本地写好的js  获取到index.html
        fragment.setTopFragment(this.getParentFragmen());
        getSupportDelegate().loadRootFragment(R.id.web_discover_sontainer,fragment);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }
}
