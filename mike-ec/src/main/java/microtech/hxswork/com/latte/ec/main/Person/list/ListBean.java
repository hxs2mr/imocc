package microtech.hxswork.com.latte.ec.main.Person.list;

import android.widget.CompoundButton;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import microtech.hxswork.com.latte.middle.MiddleFragment;

/**
 * Created by microtech on 2017/11/26.个人设置中数据的转换
 */

public class ListBean implements MultiItemEntity{

    private int mItemType = 0 ;
    private String mImageUrl = null;
    private String mText = null;
    private String mValue = null;
    private int mId = 0;
    private MiddleFragment middleFragment=null;
    private CompoundButton.OnCheckedChangeListener mOnCheckChangelistener = null;

    public ListBean(int mItemType, String mImageUrl, String mText, String mValue, int mId, MiddleFragment middleFragment, CompoundButton.OnCheckedChangeListener mOnCheckChangelistener) {
        this.mItemType = mItemType;
        this.mImageUrl = mImageUrl;
        this.mText = mText;
        this.mValue = mValue;
        this.mId = mId;
        this.middleFragment = middleFragment;
        this.mOnCheckChangelistener = mOnCheckChangelistener;
    }

    public int getmItemType() {
        return mItemType;
    }

    public void setmItemType(int mItemType) {
        this.mItemType = mItemType;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmText() {
        if(mText ==null)
        {
            return "";
        }
        return mText;
    }

    public void setmText(String mText) {
        this.mText = mText;
    }

    public String getmValue() {
        if(mValue ==null)
        {
            return "";
        }
        return mValue;
    }

    public void setmValue(String mValue) {
        this.mValue = mValue;
    }

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public MiddleFragment getMiddleFragment() {
        return middleFragment;
    }

    public void setMiddleFragment(MiddleFragment middleFragment) {
        this.middleFragment = middleFragment;
    }

    public CompoundButton.OnCheckedChangeListener getmOnCheckChangelistener() {
        return mOnCheckChangelistener;
    }

    public void setmOnCheckChangelistener(CompoundButton.OnCheckedChangeListener mOnCheckChangelistener) {
        this.mOnCheckChangelistener = mOnCheckChangelistener;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }

    public static final  class  Builder{

        private int id = 0 ;
        private int itemType= 0;
        private String imageUrl = null;
        private String text = null;
        private String value = null;
        private CompoundButton.OnCheckedChangeListener mOnCheckChangelistener = null;
        private MiddleFragment fragment =null;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setItemType(int itemType) {
            this.itemType = itemType;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public Builder setText(String text) {
            this.text = text;
            return this;
        }

        public Builder setValue(String value) {
            this.value = value;
            return this;
        }

        public Builder setmOnCheckChangelistener(CompoundButton.OnCheckedChangeListener mOnCheckChangelistener) {
            this.mOnCheckChangelistener = mOnCheckChangelistener;
            return this;
        }

        public Builder setFragment(MiddleFragment fragment) {
            this.fragment = fragment;
            return this;
        }
        public ListBean build(){
            return new ListBean(itemType,imageUrl,text,value,id,fragment,mOnCheckChangelistener);
        }
    }
}
