package microtech.hxswork.com.latte.ui.refresh;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;

import microtech.hxswork.com.latte.init.Latte;
import microtech.hxswork.com.latte.net.RestClent;
import microtech.hxswork.com.latte.net.callback.ISuccess;
import microtech.hxswork.com.latte.ui.LoaderStyle;
import microtech.hxswork.com.latte.ui.recyclew.DataConVerter;
import microtech.hxswork.com.latte.ui.recyclew.MultipleRecyclerAdapter;
import retrofit2.http.PUT;

/**
 * Created by microtech on 2017/11/17.
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener,BaseQuickAdapter.RequestLoadMoreListener{


    private final SwipeRefreshLayout REFRESH_LAYOUT;
    private final PageBean BEAN;
    private final RecyclerView RECYCLEVIEW;
    private MultipleRecyclerAdapter mAdapter=null;
    private final DataConVerter CONVERTER;

    public RefreshHandler(SwipeRefreshLayout REFRESH_LAYOUT,RecyclerView recycleview,DataConVerter conVerter,PageBean bean) {
        this.REFRESH_LAYOUT = REFRESH_LAYOUT;
        REFRESH_LAYOUT.setOnRefreshListener(this);
        this.RECYCLEVIEW = recycleview;
        this.CONVERTER = conVerter;
        this.BEAN = bean;

    }

    public static RefreshHandler create(SwipeRefreshLayout swipeRefreshLayout,RecyclerView recycleview,DataConVerter conVerter){
        return new RefreshHandler(swipeRefreshLayout,recycleview,conVerter,new PageBean());
    }
    private void refresh(){
        REFRESH_LAYOUT.setRefreshing(true);
        Latte.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //进行一些网络请求
                REFRESH_LAYOUT.setRefreshing(false);
            }
        },2000);
    }
    public void firstPage(String url, Context context)
    {
        BEAN.setDelayed(1000);
        RestClent.builder()
                .url(url)
                .loader(context, LoaderStyle.SquareSpinIndicator)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //Toast.makeText(Latte.getApplicationContext(),response,Toast.LENGTH_SHORT).show();
                        final JSONObject object = JSON.parseObject(response);
                        BEAN.setTotal(object.getInteger("totle"))
                                .setPageSize(object.getInteger("page_size"));
                        //设置Adapter
                        mAdapter= MultipleRecyclerAdapter.create(CONVERTER.setJsonData(response));
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this,RECYCLEVIEW);//设置加载更多
                        RECYCLEVIEW.setAdapter(mAdapter);
                        BEAN.addIndex();
                    }
                })
                .build()
                .get();
    }
    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onLoadMoreRequested() {

    }
}
