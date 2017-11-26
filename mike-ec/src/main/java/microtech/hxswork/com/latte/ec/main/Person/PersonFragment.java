package microtech.hxswork.com.latte.ec.main.Person;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import microtech.hxswork.com.latte.ec.R;
import microtech.hxswork.com.latte.ec.main.Person.list.ListAdapter;
import microtech.hxswork.com.latte.ec.main.Person.list.ListBean;
import microtech.hxswork.com.latte.ec.main.Person.list.ListItemType;
import microtech.hxswork.com.latte.ec.main.Person.order.OrderFragment;
import microtech.hxswork.com.latte.middle.bottom.BottomItemFragment;

/**
 * Created by microtech on 2017/11/17.
 */

public class PersonFragment extends BottomItemFragment implements View.OnClickListener {

    public static final String ORDER_TYPE = "ORDER_TYPE";

    private Bundle mArgs=null;
    RecyclerView recyclerView=null;
    LinearLayoutCompat linear_order_all = null;
    @Override
    public Object setLayout() {
        return R.layout.person_fragment;
    }

    private void StartOrderType()
    {
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(mArgs);
        getParentFragmen().getSupportDelegate().start(fragment);//父布局跳转 去除底部的导航
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mArgs = new Bundle();
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        recyclerView= bind(R.id.rv_personal_setting);
        linear_order_all = bind(R.id.persion_order_all);
        linear_order_all.setOnClickListener(this);
       final ListBean address = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(1)
                .setText("收货地址")
                .setValue("")
                .build();


       final ListBean system = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setText("系统设置")
               .setValue("")
                .build();

        ListBean zhu = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(3)
                .setText("我的足迹")
                .setValue("")
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(address);
        data.add(system);
        data.add(zhu);

        //这种recycleview的布局
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);

        final ListAdapter adapter = new ListAdapter(data);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.persion_order_all) {
            mArgs.putString(ORDER_TYPE,"all");//表示的是这是订单的部分 显示
            StartOrderType();
        }
    }
}
