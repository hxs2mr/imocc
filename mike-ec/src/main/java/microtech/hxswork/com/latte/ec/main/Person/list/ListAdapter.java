package microtech.hxswork.com.latte.ec.main.Person.list;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import microtech.hxswork.com.latte.ec.R;

/**
 * Created by microtech on 2017/11/26.
 */

public class ListAdapter extends BaseMultiItemQuickAdapter<ListBean,BaseViewHolder>{
    public ListAdapter(List<ListBean> data) {
        super(data);
        addItemType(ListItemType.ITEM_NORMAL, R.layout.persion_item_layput);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListBean item) {
        switch (helper.getItemViewType())
        {
            case 20:
                helper.setText(R.id.tv_arrow_text,item.getmText());
                helper.setText(R.id.tv_arrow_value,item.getmValue());
                break;
                default:
                    break;
        }
    }
}
