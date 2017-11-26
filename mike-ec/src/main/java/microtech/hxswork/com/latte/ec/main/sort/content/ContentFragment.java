package microtech.hxswork.com.latte.ec.main.sort.content;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import microtech.hxswork.com.latte.ec.R;
import microtech.hxswork.com.latte.middle.MiddleFragment;
import microtech.hxswork.com.latte.net.RestClent;
import microtech.hxswork.com.latte.net.callback.ISuccess;
import microtech.hxswork.com.latte.ui.LoaderStyle;

import static microtech.hxswork.com.latte.ui.LoaderStyle.BallPulseIndicator;

/**
 * Created by microtech on 2017/11/20.分类的右侧商品右侧
 */

public class ContentFragment extends MiddleFragment {//分类的右侧
    private static final  String ARG_CONTENT_ID="CONTENT_ID";
    private int mContentId = -1;
    private List<ContentBean> mData= new ArrayList<>();

    RecyclerView mRecyclerView =null;//= bind(R.id.sortcontent_list_rv);
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if(args!=null){
            mContentId = args.getInt(ARG_CONTENT_ID);
        }
    }
    public static  ContentFragment newInstance(int contentId){
    final Bundle args = new Bundle();
        args.putInt(ARG_CONTENT_ID,contentId);
        final  ContentFragment fragment = new ContentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Object setLayout() {
        return R.layout.content_list_fragment;
    }


    private void initData(){
        RestClent.builder()
                .url("http://192.168.1.134:8080/php_android_api/api/sort_right_json1.php?contentId="+mContentId)
                .loader(getContext(),LoaderStyle.BallClipRotatePulseIndicator)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        mData=new ContentDataConverter().convert(response);
                        final  ContentAdapter adapter = new ContentAdapter(R.layout.item_content_content,R.layout.item_content_head,mData);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .build()
                .get();
    }
    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRecyclerView = bind(R.id.sortcontent_list_rv);
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        initData();
    }
}
