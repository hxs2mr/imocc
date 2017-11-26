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
import android.widget.ImageView;
import android.widget.Toast;

import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

import microtech.hxswork.com.latte.ec.R;
import microtech.hxswork.com.latte.ec.main.ExBottomFragment;
import microtech.hxswork.com.latte.middle.bottom.BottomItemFragment;
import microtech.hxswork.com.latte.ui.recyclew.BaseDecoration;
import microtech.hxswork.com.latte.ui.refresh.RefreshHandler;

/**
 * Created by microtech on 2017/11/17.主页
 */

public class HomeFragment extends BottomItemFragment {
    SwipeRefreshLayout mRefreshLayout =null;//= bind(R.id.home_swipe);

    RecyclerView mRecyclerView =null;//=bind(R.id.home_rv);

    Toolbar mToolbar =null;//= bind(R.id.home_tb);

    IconTextView home_icon_shao =null;//=bind(R.id.home_icon_shao);

    IconTextView home_icon_message =null;//=bind(R.id.home_icon_message);

    IconTextView home_sou =null;//=bind(R.id.home_icon_message);


    private RefreshHandler mRefreshHandler = null;

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

        mRefreshLayout= bind(R.id.home_swipe);
        mRecyclerView=bind(R.id.home_rv);
        mToolbar= bind(R.id.home_tb);
        home_icon_shao=bind(R.id.home_icon_shao);
        home_icon_message=bind(R.id.home_icon_message);
        home_sou=bind(R.id.home_icon_message);
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
      final   ExBottomFragment exBottomFragment =  getParentFragmen();
        mRecyclerView.addOnItemTouchListener(HomeItemClickListener.create(exBottomFragment));

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initRefreshLayout();
        initRecyclerView();
        mRefreshHandler.firstPage("http://192.168.1.134:8080/php_android_api/api/home_json.php?",getContext());//加载第一页
    }

    @Override
    public Object setLayout() {
        return R.layout.home_fragment;
    }
}
