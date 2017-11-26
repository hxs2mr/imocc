package microtech.hxswork.com.latte.ec.main.Person.order;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import microtech.hxswork.com.latte.ec.R;
import microtech.hxswork.com.latte.ui.recyclew.MultipViewHolder;
import microtech.hxswork.com.latte.ui.recyclew.MultipleFields;
import microtech.hxswork.com.latte.ui.recyclew.MultipleItemEntity;
import microtech.hxswork.com.latte.ui.recyclew.MultipleRecyclerAdapter;

/**
 * Created by microtech on 2017/11/26.
 */

public class OrderListAdapter extends MultipleRecyclerAdapter {
    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .dontAnimate();

    protected OrderListAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(OrderListItemType.ITEM_ORDER_LIST, R.layout.persion_account_item);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(MultipViewHolder helper, MultipleItemEntity item) {
        super.convert(helper, item);
        switch (helper.getItemViewType())
        {
            case OrderListItemType.ITEM_ORDER_LIST:
                final AppCompatImageView imageView = helper.getView(R.id.image_order_list);
                final AppCompatTextView title = helper.getView(R.id.tv_order_list_title);
                final AppCompatTextView price= helper.getView(R.id.tv_order_list_price);
                final AppCompatTextView time= helper.getView(R.id.tv_order_list_time);

                final  String title_value = item.getField(MultipleFields.TITLE);
                final String time_value =  item.getField(OrderItemFields.TIME);
                final double price_value =  item.getField(OrderItemFields.PRICE);
                title.setText(title_value);
                price.setText("价格:"+price_value);
                time.setText(time_value);

                Glide.with(mContext)
                        .load(item.getField(MultipleFields.IMAGE_URL))
                        .apply(OPTIONS)
                        .into(imageView);

                break;

            default:

            break;

        }
    }
}
