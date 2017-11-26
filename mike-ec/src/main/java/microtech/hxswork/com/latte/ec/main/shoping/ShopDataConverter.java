package microtech.hxswork.com.latte.ec.main.shoping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

import microtech.hxswork.com.latte.ui.recyclew.DataConVerter;
import microtech.hxswork.com.latte.ui.recyclew.MultipleFields;
import microtech.hxswork.com.latte.ui.recyclew.MultipleItemEntity;

/**
 * Created by microtech on 2017/11/22.
 */

public class ShopDataConverter extends DataConVerter {
    @Override
    public ArrayList<MultipleItemEntity> CONVERT() {
        final ArrayList<MultipleItemEntity> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final String imageUrl = data.getString("thumb");
            final String title = data.getString("title");
            final int id = data.getInteger("id");
            final Double price = data.getDouble("price");
            final int count = data.getInteger("count");
            final String desc = data.getString("desc");


            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE,ShopCartFields.SHOP_TYPE)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.IMAGE_URL,imageUrl)
                    .setField(ShopFields.TITLE,title)
                    .setField(ShopFields.DESC,desc)
                    .setField(ShopFields.COUNT,count)
                    .setField(ShopFields.PRICE,price)
                    .setField(ShopFields.IS_SELECT,false)//为点击
                    .setField(ShopFields.POSTION,i)
                    .build();
            dataList.add(entity);
        }
            return dataList;
    }
}
