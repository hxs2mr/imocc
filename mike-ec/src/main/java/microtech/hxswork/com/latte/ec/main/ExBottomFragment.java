package microtech.hxswork.com.latte.ec.main;

import android.graphics.Color;

import java.util.LinkedHashMap;

import microtech.hxswork.com.latte.ec.main.Index.HomeFragment;
import microtech.hxswork.com.latte.ec.main.Person.PersonFragment;
import microtech.hxswork.com.latte.ec.main.compass.CompassFragment;
import microtech.hxswork.com.latte.ec.main.shoping.ShoppFragment;
import microtech.hxswork.com.latte.ec.main.sort.SortFragment;
import microtech.hxswork.com.latte.middle.bottom.BaseBottomFragment;
import microtech.hxswork.com.latte.middle.bottom.BottomItemFragment;
import microtech.hxswork.com.latte.middle.bottom.BottomTabBean;
import microtech.hxswork.com.latte.middle.bottom.ItemBuilder;

/**
 * Created by microtech on 2017/11/17.
 */

public class ExBottomFragment extends BaseBottomFragment {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemFragment> setItems(ItemBuilder builder) {
        final  LinkedHashMap<BottomTabBean,BottomItemFragment> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}","主页"),new HomeFragment());
        items.put(new BottomTabBean("{fa-sort}","分类"),new SortFragment());
        items.put(new BottomTabBean("{fa-compass}","发现"),new CompassFragment());
        items.put(new BottomTabBean("{fa-shopping-cart}","购物车"),new ShoppFragment());
        items.put(new BottomTabBean("{fa-user}","我的"),new PersonFragment());
        return builder.addItem(items).build();
    }

    @Override
    public int setIndexFragment() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#ffff8800");
    }
}
