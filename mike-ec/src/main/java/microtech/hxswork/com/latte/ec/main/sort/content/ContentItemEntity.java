package microtech.hxswork.com.latte.ec.main.sort.content;

/**
 * Created by microtech on 2017/11/21.
 */

public class ContentItemEntity {
    private int goodsId =0  ;
    private String goodsName=null;
    private String goodsThumb=null;

    public String getGoodsThumb() {
        return goodsThumb;
    }

    public void setGoodsThumb(String goodsThumb) {
        this.goodsThumb = goodsThumb;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
