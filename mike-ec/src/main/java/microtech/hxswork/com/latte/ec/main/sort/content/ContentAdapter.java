package microtech.hxswork.com.latte.ec.main.sort.content;

import android.support.v7.widget.AppCompatImageView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import microtech.hxswork.com.latte.ec.R;

/**
 * Created by microtech on 2017/11/21.
 */

public class ContentAdapter extends BaseSectionQuickAdapter<ContentBean,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     */
    //设置图片的加载测曰
    private static final RequestOptions RECYCLER_OPTIONS=
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();
    public ContentAdapter(int layoutResId, int sectionHeadResId, List<ContentBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, ContentBean item) {
        helper.setText(R.id.headler,item.header);
        helper.setVisible(R.id.more,item.IsMore());
        helper.addOnClickListener(R.id.more);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContentBean item) {
        //item.t返回的时ContentBean类型
        final String thumb = item.t.getGoodsThumb();
        final String name = item.t.getGoodsName();
        final int goodsId = item.t.getGoodsId();
        final  ContentItemEntity entity = item.t;
        helper.setText(R.id.content_tv,name);

        final AppCompatImageView imageView = helper.getView(R.id.content_iv);
        Glide.with(mContext)
                .load(thumb)
                .apply(RECYCLER_OPTIONS)
                .into(imageView);
    }
}
