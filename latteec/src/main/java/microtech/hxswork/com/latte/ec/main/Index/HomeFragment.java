package microtech.hxswork.com.latte.ec.main.Index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

import butterknife.BindView;
import microtech.hxswork.com.latte.ec.R;
import microtech.hxswork.com.latte.ec.R2;
import microtech.hxswork.com.latte.ec.main.ExBottomFragment;
import microtech.hxswork.com.latte.init.Latte;
import microtech.hxswork.com.latte.middle.bottom.BottomItemFragment;
import microtech.hxswork.com.latte.net.RestClent;
import microtech.hxswork.com.latte.net.callback.ISuccess;
import microtech.hxswork.com.latte.ui.recyclew.BaseDecoration;
import microtech.hxswork.com.latte.ui.recyclew.MultipleFields;
import microtech.hxswork.com.latte.ui.recyclew.MultipleItemEntity;
import microtech.hxswork.com.latte.ui.refresh.PageBean;
import microtech.hxswork.com.latte.ui.refresh.RefreshHandler;

/**
 * Created by microtech on 2017/11/17.主页
 */

public class HomeFragment extends BottomItemFragment {
    @BindView(R2.id.home_swipe)
    SwipeRefreshLayout mRefreshLayout = null;

    @BindView(R2.id.home_rv)
    RecyclerView mRecyclerView=null;

    @BindView(R2.id.home_tb)
    Toolbar mToolbar= null;

    @BindView(R2.id.home_icon_shao)
    IconTextView home_icon_shao=null;//扫描

    @BindView(R2.id.home_icon_message)
    IconTextView home_icon_message=null;//t通知

    @BindView(R2.id.home_sou)
    AppCompatEditText home_sou=null;


    private RefreshHandler mRefreshHandler = null;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRefreshHandler = RefreshHandler.create(mRefreshLayout,mRecyclerView,new HomeDataConVerter());
    }

    private void initRefreshLayout(){//设置刷新的颜色和款式
        mRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_dark
        );
        mRefreshLayout.setProgressViewOffset(true,120,300);
    }
    private void initRecyclerView(){
        final GridLayoutManager manager = new GridLayoutManager(getContext(),4);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.addItemDecoration(BaseDecoration.create(ContextCompat.getColor(getContext(),R.color.app_backgraound),5));

        //按照fragment跳转
      final   ExBottomFragment exBottomFragment =  getParentDelegate();
        mRecyclerView.addOnItemTouchListener(HomeItemClickListener.create(exBottomFragment));

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        mRefreshHandler.firstPage("http://192.168.1.134:8080/php_android_api/api/home_json.php?");
    }

    @Override
    public Object setLayout() {
        return R.layout.home_fragment;
    }

}
