package microtech.hxswork.com.latte.ec.main.shoping;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import microtech.hxswork.com.latte.ec.R;
import microtech.hxswork.com.latte.ec.pay.FastPay;
import microtech.hxswork.com.latte.ec.pay.PayResultListener;
import microtech.hxswork.com.latte.middle.bottom.BottomItemFragment;
import microtech.hxswork.com.latte.net.RestClent;
import microtech.hxswork.com.latte.net.callback.ISuccess;
import microtech.hxswork.com.latte.ui.recyclew.MultipleItemEntity;

/**
 * Created by microtech on 2017/11/17.购物车
 */

public class ShoppFragment extends BottomItemFragment implements ISuccess, ICartItemListener,PayResultListener, View.OnClickListener {
    ShopAdapter shopAdapter = null;
    private int mCurrentCount = 0;//当前点击删除的数量标记点击的
    private int mTotalCount =0;//购物车中总共的数量

    private double mTotalPreice = 0.00;
    RecyclerView recyclerView =null;//= bind(R.id.shop_reclcerView);

    IconTextView IconSeletcAll=null;// =bind(R.id.shop_select_all);

    ViewStubCompat null_shop=null;// =bind(R.id.null_shop);
    AppCompatTextView total_monkey=null;// = bind(R.id.total_monkey);

    AppCompatTextView  shop_pay=null;//= bind(R.id.shop_pay);
    IconTextView shop_select_all=null;// = bind(R.id.shop_select_all);
    AppCompatTextView shop_clear_delete=null;// =  bind(R.id.shop_clear_delete);
    AppCompatTextView  shop_clear=null;// =  bind(R.id.shop_clear);

   private void onClickPay(){
        createOrder();
    }
    private void createOrder(){
        final  String orderUrl="http://app.api.zanzuanshi.com/api/v1/peyment";//和自己的服务器交互
        final WeakHashMap<String,Object> orderParams = new WeakHashMap<>();
        orderParams.put("userid",264392);//用户的id
        orderParams.put("amount",0.01);//总价
        orderParams.put("comment","描述信息");
        orderParams.put("type","1");//别人的服务器 约束
        orderParams.put("ordertype","1");//别人的服务器 约束
        orderParams.put("isanonymous",true);//别人的服务器 约束
        orderParams.put("followeduser",0);//别人的服务器 约束

        //可添加其他的信息 服务器特有
        RestClent.builder()
                .url(orderUrl)
                .params(orderParams)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final int orderId = JSON.parseObject(response).getInteger("result");//去除orderid
                        //进行具体的支付
                        FastPay.create(ShoppFragment.this)
                                .setPayResultListener(ShoppFragment.this)
                                .setPayOrderId(orderId)
                                .beginPayDialog();
                    }
                })
                .build()
                .post();
    }
    private void onClickSelectAll(){
            final  int tag = (int) IconSeletcAll.getTag();
        if(tag == 0){
            IconSeletcAll.setTextColor(ContextCompat.getColor(getContext(),R.color.soot_left));
            IconSeletcAll.setTag(1);
            shopAdapter.setIsSelectAll(true);
            //跟新Recyele的显示状态
            shopAdapter.notifyItemRangeChanged(0,shopAdapter.getItemCount());
        }else {
            IconSeletcAll.setTextColor(Color.GRAY);
            IconSeletcAll.setTag(0);
            shopAdapter.setIsSelectAll(false);
            shopAdapter.notifyItemRangeChanged(0,shopAdapter.getItemCount());
        }

    }
  private   void onClickRemove(){
            final List<MultipleItemEntity> data = shopAdapter.getData();
        //要删除的数据
        List<MultipleItemEntity> deleteData = new ArrayList<>();
        for(MultipleItemEntity entity:data)
        {
            final  boolean isSelect =entity.getField(ShopFields.IS_SELECT);//判断状态
            if(isSelect)
            {
                deleteData.add(entity);
            }
        }
        for(MultipleItemEntity entity:deleteData){
            int removePostion;
            final  int ePostion = entity.getField(ShopFields.POSTION);
            if(ePostion > mCurrentCount-1){
                removePostion = ePostion-(mTotalCount-mCurrentCount);
            }else {
                removePostion  = ePostion;
            }
            if(removePostion <= shopAdapter.getItemCount()) {
                shopAdapter.remove(removePostion);
                mCurrentCount = shopAdapter.getItemCount();
                //跟新移除后的数据
                shopAdapter.notifyItemRangeChanged(removePostion, shopAdapter.getItemCount());
            }
        }
        checkItemCout();

    }
  private   void onClickRemoveAll(){
        shopAdapter.getData().clear();
        shopAdapter.notifyDataSetChanged();
        checkItemCout();
    }

    private void checkItemCout()//检查购物车里面是否有商品
    {
        final  int count = shopAdapter.getItemCount();
        if(count == 0){
            //final View nullView =null_shop.inflate();
            final AppCompatTextView toBuy = bind(R.id.null_shop_buy);
            toBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(),"购物去了",Toast.LENGTH_SHORT).show();
                }
            });
            recyclerView.setVisibility(View.GONE);
        }else {
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
    private int mSelectCount=0;
    @Override
    public Object setLayout() {
        return R.layout.shop_fragment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

        recyclerView = bind(R.id.shop_reclcerView);

         IconSeletcAll =bind(R.id.shop_select_all);

         null_shop =bind(R.id.null_shop);
         total_monkey = bind(R.id.total_monkey);

          shop_pay= bind(R.id.shop_pay);
         shop_select_all = bind(R.id.shop_select_all);
         shop_clear_delete =  bind(R.id.shop_clear_delete);
          shop_clear =  bind(R.id.shop_clear);

        IconSeletcAll.setOnClickListener(this);
        shop_pay.setOnClickListener(this);
        shop_select_all.setOnClickListener(this);
        shop_clear_delete.setOnClickListener(this);
        shop_clear.setOnClickListener(this);
        IconSeletcAll.setTag(0);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClent.builder()
                .url("http://192.168.1.134:8080/php_android_api/api/shop.php?")
                .loader(getContext())
                .success(this)
                .build()
                .get();
    }

    @Override
    public void onSuccess(String response) {
        final ArrayList<MultipleItemEntity> datalist = new ShopDataConverter().setJsonData(response).CONVERT();
        shopAdapter = new ShopAdapter(datalist);
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        shopAdapter.setCartItemListener(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(shopAdapter);
        checkItemCout();
        mTotalPreice = shopAdapter.getToTalPrice();
        total_monkey.setText(String.valueOf(mTotalPreice));//一开始的商品总价
    }
    @Override
    public void onItemClick(double total) {
        final  double prcie = shopAdapter.getToTalPrice();

        total_monkey.setText(String.valueOf(prcie));
    }

    @Override
    public void onPaySuccess() {

    }

    @Override
    public void onPaying() {

    }

    @Override
    public void onPayFail() {

    }

    @Override
    public void onPayCancel() {

    }

    @Override
    public void onPayConnectError() {

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.shop_pay) {
            onClickPay();
        }else if(i == R.id.shop_select_all)
        {
            onClickSelectAll();
        }else if(i == R.id.shop_clear_delete){
            onClickRemove();
        }else if(i == R.id.shop_clear){
            onClickRemoveAll();
        }
    }
}
