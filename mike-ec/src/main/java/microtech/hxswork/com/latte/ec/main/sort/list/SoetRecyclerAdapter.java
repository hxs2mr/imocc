package microtech.hxswork.com.latte.ec.main.sort.list;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import java.util.List;

import me.yokeyword.fragmentation.SupportHelper;
import microtech.hxswork.com.latte.ec.R;
import microtech.hxswork.com.latte.ec.main.sort.SortFragment;
import microtech.hxswork.com.latte.ec.main.sort.content.ContentFragment;
import microtech.hxswork.com.latte.middle.MiddleFragment;
import microtech.hxswork.com.latte.middle.bottom.ItemBuilder;
import microtech.hxswork.com.latte.ui.recyclew.MultipViewHolder;
import microtech.hxswork.com.latte.ui.recyclew.MultipleFields;
import microtech.hxswork.com.latte.ui.recyclew.MultipleItemEntity;
import microtech.hxswork.com.latte.ui.recyclew.MultipleRecyclerAdapter;
import microtech.hxswork.com.latte.ui.recyclew.itemType;
import retrofit2.http.DELETE;

/**
 * Created by microtech on 2017/11/20.
 */

public class SoetRecyclerAdapter extends MultipleRecyclerAdapter {

    private final SortFragment FRAGMENT;
    private int mPrePosition = 0 ;
    protected SoetRecyclerAdapter(List<MultipleItemEntity> data, SortFragment fragment) {
        super(data);
        this.FRAGMENT = fragment;
        //添加垂直菜单布局
        addItemType(itemType.VERTICAL_MENU_LIST, R.layout.item_sort_left);
    }

    @Override
    protected void convert(final MultipViewHolder helper, final MultipleItemEntity item) {
        super.convert(helper, item);
        switch (helper.getItemViewType())
        {
            case itemType.VERTICAL_MENU_LIST:
                final  String text = item.getField(MultipleFields.NAME);
                final  boolean isClicked = item.getField(MultipleFields.TAG);
                final AppCompatTextView name = helper.getView(R.id.sort_left_tv);
                final View line = helper.getView(R.id.view_line);
                final View itemView = helper.itemView;

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final  int currentPosition = helper.getAdapterPosition();
                        if(mPrePosition!=currentPosition){//如果上一个postion不等于现在的
                            getData().get(mPrePosition).setField(MultipleFields.TAG,false);
                            notifyItemChanged(mPrePosition);
                            item.setField(MultipleFields.TAG,true);
                            notifyItemChanged(currentPosition);
                            mPrePosition = currentPosition;

                            final  int contentID = getData().get(currentPosition).getField(MultipleFields.ID);
                            showContent(contentID);
                        }
                    }
                });
                if(!isClicked){
                    line.setVisibility(View.INVISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext,R.color.we_chat_black));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext,R.color.item_background));
                }else {
                    line.setVisibility(View.VISIBLE);
                    name.setTextColor(ContextCompat.getColor(mContext,R.color.soot_left));
                    line.setBackgroundColor(ContextCompat.getColor(mContext,R.color.soot_left));
                    itemView.setBackgroundColor(Color.WHITE);
                }
                helper.setText(R.id.sort_left_tv, text);
                break;
            default:
                break;
        }
    }
    private void showContent(int contentId){//显示右边的界面
        final ContentFragment fragment = ContentFragment.newInstance(contentId);
        switchContent(fragment);
    }
    private void switchContent(ContentFragment fragment){
        MiddleFragment  contentFragment = SupportHelper.findFragment(FRAGMENT.getChildFragmentManager(),ContentFragment.class);
        if(contentFragment != null){
            contentFragment.getSupportDelegate().replaceFragment(fragment,false);
        }
    }
}
