package microtech.hxswork.com.latte.ec.main.Person.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

import microtech.hxswork.com.latte.ui.recyclew.DataConVerter;
import microtech.hxswork.com.latte.ui.recyclew.MultipleFields;
import microtech.hxswork.com.latte.ui.recyclew.MultipleItemEntity;

/**
 * Created by microtech on 2017/11/26.
 */

public class OrderListDataConverter extends DataConVerter{
    @Override
    public ArrayList<MultipleItemEntity> CONVERT() {

        final JSONArray array = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = array.size();
        for (int i =0 ; i < size;i++){
            final JSONObject data= array.getJSONObject(i);
            final  String thumb = data.getString("thumb");
            final String title = data.getString("title");
            final int id = data.getInteger("id");
            double price = data.getDouble("price");
            final String time = data.getString("time");
            MultipleItemEntity entity =MultipleItemEntity.builder()
                    .setItemType(OrderListItemType.ITEM_ORDER_LIST)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.IMAGE_URL,thumb)
                    .setField(MultipleFields.TITLE,title)
                    .setField(OrderItemFields.PRICE,price)
                    .setField(OrderItemFields.TIME,time)
                    .build();
            ENTITYES.add(entity);
        }
        return ENTITYES;
    }
}
