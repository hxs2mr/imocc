package microtech.hxswork.com.latte.ec.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;
import microtech.hxswork.com.latte.ec.R;
import microtech.hxswork.com.latte.middle.MiddleFragment;

/**
 * Created by microtech on 2017/11/20.详情界面
 */

public class DetailFragment extends MiddleFragment {

    public static DetailFragment create(){
        return new DetailFragment();
    }
    @Override
    public Object setLayout() {
        return R.layout.detail_fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
    @Override
    protected FragmentAnimator onCreateFragmentAnimator() {//fragment进出的水平动画
        return new DefaultHorizontalAnimator();
    }
}
