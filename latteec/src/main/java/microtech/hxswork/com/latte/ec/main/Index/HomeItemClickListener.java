package microtech.hxswork.com.latte.ec.main.Index;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

import microtech.hxswork.com.latte.ec.detail.DetailFragment;
import microtech.hxswork.com.latte.middle.MiddleFragment;

/**
 * Created by microtech on 2017/11/20.
 */

public class HomeItemClickListener extends SimpleClickListener {
    private final MiddleFragment FRAGMENT;

    private HomeItemClickListener(MiddleFragment fragment){
        this.FRAGMENT = fragment;
    }
    public static SimpleClickListener create(MiddleFragment fragment){
        return new HomeItemClickListener(fragment);
    }
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        DetailFragment fragment = DetailFragment.create();
        FRAGMENT.start(fragment);

    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
