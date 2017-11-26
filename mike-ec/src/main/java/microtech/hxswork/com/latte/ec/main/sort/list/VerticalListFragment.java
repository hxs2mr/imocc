package microtech.hxswork.com.latte.ec.main.sort.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import microtech.hxswork.com.latte.ec.R;
import microtech.hxswork.com.latte.ec.main.sort.SortFragment;
import microtech.hxswork.com.latte.middle.MiddleFragment;
import microtech.hxswork.com.latte.net.RestClent;
import microtech.hxswork.com.latte.net.callback.ISuccess;
import microtech.hxswork.com.latte.ui.recyclew.MultipleItemEntity;

/**
 * Created by microtech on 2017/11/20.分类中垂直的布局
 */

public class VerticalListFragment extends MiddleFragment {
    RecyclerView mRecyclerView=null;//=bind(R.id.sortmenu_list_rv);
    @Override
    public Object setLayout() {
        return R.layout.vertical_list_fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRecyclerView=bind(R.id.sortmenu_list_rv);
        initRecycleView();
    }

    private void initRecycleView(){
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        //屏蔽动画效果
        mRecyclerView.setItemAnimator(null);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {//数据懒加载
        super.onLazyInitView(savedInstanceState);
        RestClent.builder()
                .url("http://192.168.1.134:8080/php_android_api/api/sort_left_json.php?")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                    final List<MultipleItemEntity> data=new VerticalListDataConverter().setJsonData(response).CONVERT();
                        final SortFragment fragment = getParentFragmen();
                        final SoetRecyclerAdapter adapter = new SoetRecyclerAdapter(data,fragment);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .build()
                .get();
    }
}
