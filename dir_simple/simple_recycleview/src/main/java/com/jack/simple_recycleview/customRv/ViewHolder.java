package com.jack.simple_recycleview.customRv;

import android.view.View;

public class ViewHolder {
    public View itemView;	//持有view的引用
    int mItemViewType = -1;	//type的类型
    public ViewHolder(  View itemView) {
        this.itemView = itemView;
    }

    public View getItemView() {
        return itemView;
    }

    public int getItemViewType() {
        return mItemViewType;
    }

    public void setItemViewType(int mItemViewType) {
        this.mItemViewType = mItemViewType;
    }
}
