package com.jack.simple_recycleview.customRv;

import android.util.SparseArray;

import java.util.ArrayList;

public class RecycledViewPool {
    //    7、内部类
    static class ScrapData {
        ArrayList<ViewHolder> mScrapHeap = new ArrayList<>();
    }

    SparseArray<ScrapData> mScrap = new SparseArray<>();//类似于HashMap

    public void clear() {
        for (int i = 0; i < mScrap.size(); i++) {
            ScrapData data = mScrap.valueAt(i);
            data.mScrapHeap.clear();
        }
    }

    //打造一个回收池
    public RecycledViewPool() {
    }

    private ScrapData getScrapDataForType(int viewType) {

        ScrapData scrapData = mScrap.get(viewType);
        if (scrapData == null) {
            scrapData = new ScrapData();
            mScrap.put(viewType, scrapData);
        }
        return scrapData;
    }

    public ViewHolder getRecycledView(int viewType) {
        //        7.2、定位到是哪一个ScrapData
        final ScrapData scrapData = mScrap.get(viewType);
        if (scrapData != null && !scrapData.mScrapHeap.isEmpty()) {
            final ArrayList<ViewHolder> scrapHeap = scrapData.mScrapHeap;
            //            7.3、倒序遍历
            for (int i = scrapHeap.size() - 1; i >= 0; i--) {
                return scrapHeap.remove(i);
            }
        }
        return null;
    }

    public void putRecycledView(ViewHolder scrap, int viewType) {
        //        7.1、根据类型定位到对应的ScrapData
        ArrayList<ViewHolder> scrapHeap = getScrapDataForType(viewType).mScrapHeap;
        scrapHeap.add(scrap);
    }
}
