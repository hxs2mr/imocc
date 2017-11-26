package microtech.hxswork.com.latte.ui.recyclew;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;

import microtech.hxswork.com.latte.R;
import microtech.hxswork.com.latte.ui.banner.GlideImageLoader;

/**
 * Created by microtech on 2017/11/20.主界面的Adapter
 */

public class MultipleRecyclerAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity,MultipViewHolder> implements BaseQuickAdapter.SpanSizeLookup{
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    //确保初始化一次banner  避免重复加载
    private boolean mIsBanner = false;

    //设置图片的加载测曰
    private static final RequestOptions RECYCLER_OPTIONS=
            new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();

    protected MultipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    public static MultipleRecyclerAdapter create(List<MultipleItemEntity> data){
        return new MultipleRecyclerAdapter(data);
    }

    public static MultipleRecyclerAdapter create(DataConVerter verter){
        return new MultipleRecyclerAdapter(verter.CONVERT());
    }

    @Override
    protected void convert(MultipViewHolder helper, MultipleItemEntity item) {//布局设置
        final String text;
        final String imageUrl;
        final ArrayList<String> bannerImages;
        switch (helper.getItemViewType()){
            case itemType.TEXT:
                System.out.println("************TEXT");
                text=item.getField(MultipleFields.TEXT);
                helper.setText(R.id.text_single,text);
                break;
            case itemType.IMAGE:
                System.out.println("************IMAGE");
                imageUrl=item.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(RECYCLER_OPTIONS)
                        .into((ImageView) helper.getView(R.id.image_single));
                break;
            case itemType.TEXT_IMAGE:
                System.out.println("************TEXT_IMAGE");
                text=item.getField(MultipleFields.TEXT);
                imageUrl=item.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(RECYCLER_OPTIONS)
                        .into((ImageView) helper.getView(R.id.it_image_single));
                helper.setText(R.id.it_text_single,text);
                break;
            case itemType.BANNER:
                System.out.println("************在这点");
                if(!mIsBanner)
                {
                    bannerImages = item.getField(MultipleFields.BANNERS);
                    showBanner((Banner) helper.getView(R.id.banner_sign),bannerImages);
                    System.out.println("************"+bannerImages.get(0));
                }
                break;
            default:
                break;
        }
    }

    private void showBanner(Banner home_banner,ArrayList<String>  bannerImages)
    {
        home_banner.setImageLoader(new GlideImageLoader());
        home_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置banner样式
        home_banner.setImages(bannerImages);//设置图片集合
        home_banner.setBannerAnimation(Transformer.Default);//设置banner动画效果
        home_banner.isAutoPlay(true);//设置自动轮播，默认为true
        home_banner.setDelayTime(3000);//设置轮播时间
        home_banner.setIndicatorGravity(BannerConfig.CENTER); //设置指示器位置（当banner模式中有指示器时）
        home_banner.start();//banner设置方法全部调用完毕时最后调用
    }
    private void init()//设置不同的布局
    {
        addItemType(itemType.TEXT, R.layout.item_multipe_text);
        addItemType(itemType.IMAGE, R.layout.item_multipe_image);
        addItemType(itemType.TEXT_IMAGE, R.layout.item_multipe_image_text);
        addItemType(itemType.BANNER, R.layout.item_multipe_banner);
        //设置宽度监听
        setSpanSizeLookup(this);
        openLoadAnimation();
        //多次执行动画
        isFirstOnly(false);
    }

    @Override
    protected MultipViewHolder createBaseViewHolder(View view) {//传入现有的viewholder
        return MultipViewHolder.create(view);
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }
}
