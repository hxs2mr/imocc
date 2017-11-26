package microtech.hxswork.com.latte.ec.main.sort.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;

import microtech.hxswork.com.latte.ui.recyclew.DataConVerter;
import microtech.hxswork.com.latte.ui.recyclew.MultipleFields;
import microtech.hxswork.com.latte.ui.recyclew.MultipleItemEntity;
import microtech.hxswork.com.latte.ui.recyclew.itemType;

/**
 * Created by microtech on 2017/11/20.
 */

public class VerticalListDataConverter extends DataConVerter{

    @Override
    public ArrayList<MultipleItemEntity> CONVERT() {
        final  ArrayList<MultipleItemEntity> data_List = new ArrayList<>();
        final JSONArray dataArray= JSON
                .parseObject(getJsonData())
                .getJSONObject("data")
                .getJSONArray("list");

        final  int size = dataArray.size();
        for(int i = 0 ; i < size ;i++){
            final JSONObject data = dataArray.getJSONObject(i);
            final  int id = data.getInteger("id");
            final String name = data.getString("name");

            final  MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setField(MultipleFields.ITEM_TYPE, itemType.VERTICAL_MENU_LIST)
                    .setField(MultipleFields.ID,id)
                    .setField(MultipleFields.NAME,name)
                    .setField(MultipleFields.TAG,false)//Tag用来标记点击
                    .build();
            data_List.add(entity);
            //设置第一个被选择
            data_List.get(0).setField(MultipleFields.TAG,true);
        }
        return data_List;
    }
}
