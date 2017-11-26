package microtech.hxswork.com.latte.ec.main.sort.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by microtech on 2017/11/21.
 */

public class ContentDataConverter {
    final List<ContentBean> convert(String json){
        final List<ContentBean> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(json).getJSONArray("data");

        final  int size = dataArray.size();
        for (int i =0 ;i < size; i++)
        {
            final JSONObject data = dataArray.getJSONObject(i);
            final  int id = data.getInteger("id");
            final  String title = data.getString("setcion");

            //添加title
           final ContentBean contentBean = new ContentBean(true,title);
            contentBean.setId(id);
            contentBean.setIsMore(true);
            dataList.add(contentBean);

            //商品信息
            final  JSONArray goods = data.getJSONArray("goods");
            final  int goods_size = goods.size();
            for(int j =0 ; j < goods_size;j++){
                final  JSONObject  contentitem = goods.getJSONObject(j);
                final  int goodsId = contentitem.getInteger("goods_id");
                final String goodsNaem = contentitem.getString("goods_name");
                final String goodsThumb = contentitem.getString("goods_thumb");

                //获取内容
                final  ContentItemEntity itemEntity = new ContentItemEntity();
                itemEntity.setGoodsId(goodsId);
                itemEntity.setGoodsName(goodsNaem);
                itemEntity.setGoodsThumb(goodsThumb);
                //添加内容
                dataList.add(new ContentBean(itemEntity));
            }

        }
return  dataList;
    }
}
