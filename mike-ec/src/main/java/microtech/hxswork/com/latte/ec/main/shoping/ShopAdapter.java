package microtech.hxswork.com.latte.ec.main.shoping;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.List;

import microtech.hxswork.com.latte.ec.R;
import microtech.hxswork.com.latte.init.Latte;
import microtech.hxswork.com.latte.net.RestClent;
import microtech.hxswork.com.latte.net.callback.ISuccess;
import microtech.hxswork.com.latte.ui.recyclew.MultipViewHolder;
import microtech.hxswork.com.latte.ui.recyclew.MultipleFields;
import microtech.hxswork.com.latte.ui.recyclew.MultipleItemEntity;
import microtech.hxswork.com.latte.ui.recyclew.MultipleRecyclerAdapter;
import microtech.hxswork.com.latte.ui.recyclew.itemType;

/**
 * Created by microtech on 2017/11/22.
 */

public class ShopAdapter extends MultipleRecyclerAdapter {

    private boolean mIsSelectAll = false;

    private ICartItemListener mCartItemListener =null;
    private double mToTalPrice = 0.0;
    private static final RequestOptions OPTIONS= new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();
    protected ShopAdapter(List<MultipleItemEntity> data) {
        super(data);

        //初始化总价
        for (MultipleItemEntity entity:data){
            final  double price= entity.getField(ShopFields.PRICE);
            final  int count  = entity.getField(ShopFields.COUNT);
            final  double total = price * count;
            mToTalPrice = mToTalPrice+total;
        }

        //添加垂直菜单布局
        addItemType(ShopCartFields.SHOP_TYPE, R.layout.item_shop);
    }

    public void setIsSelectAll(boolean isSelectAll){
            this.mIsSelectAll = isSelectAll;
    }
    public void setCartItemListener(ICartItemListener listener){
        this.mCartItemListener = listener;
    }
    public double getToTalPrice()
    {
        return mToTalPrice;
    }
    @Override
    protected void convert(final MultipViewHolder helper, final MultipleItemEntity item) {
        super.convert(helper, item);
        switch (helper.getItemViewType())
        {
            case ShopCartFields.SHOP_TYPE:
                final  int id = item.getField(MultipleFields.ID);
                final  String imageurl = item.getField(MultipleFields.IMAGE_URL);
                final  String title = item.getField(ShopFields.TITLE);
                final  String desc = item.getField(ShopFields.DESC);
                final  int count = item.getField(ShopFields.COUNT);
                final  double price = item.getField(ShopFields.PRICE);
                final  boolean is_select = item.getField(ShopFields.IS_SELECT);

                final AppCompatImageView imageView = helper.getView(R.id.item_shop_image);
                final AppCompatTextView tv_title = helper.getView(R.id.item_shop_title);
                final AppCompatTextView tv_desc = helper.getView(R.id.item_shop_descr);
                final AppCompatTextView tv_count = helper.getView(R.id.item_shop_counts);
                final AppCompatTextView tv_price = helper.getView(R.id.item_shop_monkey);

                final IconTextView ic_delete = helper.getView(R.id.item_shop_delete);
                final IconTextView ic_add = helper.getView(R.id.item_shop_add);

                final  IconTextView ic_ok = helper.getView(R.id.item_shop_ok);

                tv_title.setText(title);
                tv_desc.setText(desc);
                tv_count.setText(String.valueOf(count));
                tv_price.setText("￥"+String.valueOf(price));

                Glide.with(mContext)
                        .load(imageurl)
                        .apply(OPTIONS)
                        .into(imageView);

                //全选状态改变
                item.setField(ShopFields.IS_SELECT,mIsSelectAll);
                final  boolean isSelected =item.getField(ShopFields.IS_SELECT);

                //根据数据的状态现在勾选
                if(isSelected){
                    ic_ok.setTextColor(ContextCompat.getColor(Latte.getApplicationContext(),R.color.soot_left));
                }else {
                    ic_ok.setTextColor(Color.GRAY);
                }
                //勾选的点击事件
                ic_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final  boolean  select = item.getField(ShopFields.IS_SELECT);
                        if(select){
                            ic_ok.setTextColor(Color.GRAY);
                            item.setField(ShopFields.IS_SELECT,false);
                        }else {
                            ic_ok.setTextColor(ContextCompat.getColor(Latte.getApplicationContext(),R.color.soot_left));
                            item.setField(ShopFields.IS_SELECT,true);
                        }
                    }
                });

                //添加加事件
                ic_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final  int del_count = item.getField(ShopFields.COUNT);
                        if(Integer.parseInt(tv_count.getText().toString())>1)
                        {
                            RestClent.builder()
                                    .url("http://192.168.1.134:8080/php_android_api/api/shop.php?")
                                    .loader(mContext)
                                    .params("count",del_count)
                                    .success(new ISuccess() {
                                        @Override
                                        public void onSuccess(String response) {
                                            int countNum = Integer.parseInt(tv_count.getText().toString());
                                            countNum--;
                                            tv_count.setText(String.valueOf(countNum));
                                            if(mCartItemListener!=null){
                                                mToTalPrice = mToTalPrice - price;
                                                final  double itemTotal = countNum*price;
                                                mCartItemListener.onItemClick(itemTotal);
                                            }
                                        }
                                    })
                                    .build()
                                    .post();
                        }
                    }
                });
                //添加减事件
                ic_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final  int add_count = item.getField(ShopFields.COUNT);
                        RestClent.builder()
                                .url("http://192.168.1.134:8080/php_android_api/api/shop.php?")
                                .loader(mContext)
                                .params("count",add_count)
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        int countNum = Integer.parseInt(tv_count.getText().toString());
                                        countNum++;
                                        tv_count.setText(String.valueOf(countNum));
                                        if(mCartItemListener!=null){
                                            mToTalPrice = mToTalPrice +price;
                                            final  double itemTotal = countNum*price;
                                            mCartItemListener.onItemClick(itemTotal);
                                        }
                                    }
                                })
                                .build()
                                .post();
                    }
                });
                break;
            default:
                break;
        }
    }
}
