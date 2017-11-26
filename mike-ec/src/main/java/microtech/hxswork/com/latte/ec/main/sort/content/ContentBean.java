package microtech.hxswork.com.latte.ec.main.sort.content;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by microtech on 2017/11/21.
 */

public class ContentBean extends SectionEntity<ContentItemEntity> {

    private boolean mIsMore = false;
    private int mId = -1;
    public ContentBean(ContentItemEntity contentItemEntity) {
        super(contentItemEntity);
    }

    public ContentBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public boolean IsMore() {
        return mIsMore;
    }

    public void setIsMore(boolean mIsMore) {
        this.mIsMore = mIsMore;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }
}
