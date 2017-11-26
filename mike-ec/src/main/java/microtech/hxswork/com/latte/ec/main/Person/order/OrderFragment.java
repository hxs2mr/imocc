package microtech.hxswork.com.latte.ec.main.Person.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import microtech.hxswork.com.latte.ec.R;
import microtech.hxswork.com.latte.ec.main.Person.PersonFragment;
import microtech.hxswork.com.latte.middle.MiddleFragment;
import microtech.hxswork.com.latte.net.RestClent;
import microtech.hxswork.com.latte.net.callback.ISuccess;
import microtech.hxswork.com.latte.ui.LoaderStyle;
import microtech.hxswork.com.latte.ui.recyclew.MultipleItemEntity;

/**
 * Created by microtech on 2017/11/26.
 */

public class OrderFragment extends MiddleFragment {
    private String mType = null; //根据此标识来判断是那个界面

    RecyclerView recyclerView  =null;
    @Override
    public Object setLayout() {
        return R.layout.order_fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final  Bundle args = getArguments();
        mType = args.getString(PersonFragment.ORDER_TYPE);

    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        recyclerView = bind(R.id.rv_order_list);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClent.builder()
                .url("person_account_json.php?")
                .loader(getContext(), LoaderStyle.SquareSpinIndicator)
                .params("type",mType)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        System.out.println("*******"+response);
                        LinearLayoutManager manager = new LinearLayoutManager(getContext());
                        recyclerView.setLayoutManager(manager);
                        final List<MultipleItemEntity> data = new OrderListDataConverter().setJsonData(response).CONVERT();
                        final  OrderListAdapter adapter = new OrderListAdapter(data);
                        recyclerView.setAdapter(adapter);
                    }
                })
                .build()
                .get();
    }
}
