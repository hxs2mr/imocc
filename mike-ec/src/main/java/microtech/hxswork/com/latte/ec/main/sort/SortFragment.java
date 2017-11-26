package microtech.hxswork.com.latte.ec.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;
import android.view.View;

import microtech.hxswork.com.latte.ec.R;
import microtech.hxswork.com.latte.ec.main.sort.content.ContentFragment;
import microtech.hxswork.com.latte.ec.main.sort.list.VerticalListFragment;
import microtech.hxswork.com.latte.middle.bottom.BottomItemFragment;

/**
 * Created by microtech on 2017/11/17.分类
 */

public class SortFragment extends BottomItemFragment{
    @Override
    public Object setLayout() {
        return R.layout.sort_fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final VerticalListFragment listFragment = new VerticalListFragment();
        getSupportDelegate().loadRootFragment(R.id.sort_left_list_container,listFragment);//父布局的控件容器
        //设置右侧第一个分类显示，默认显示分类一
        getSupportDelegate().loadRootFragment(R.id.sort_right_content_container, ContentFragment.newInstance(1));
    }
}
