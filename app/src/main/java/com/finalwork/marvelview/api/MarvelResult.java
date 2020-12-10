package com.finalwork.marvelview.api;

import java.util.ArrayList;
import java.util.List;

public class MarvelResult<T> {//从接口获得的结果类

    private int mOffset;
    private int mTotal;
    private List<T> mEntries;
    private String mAttribution;

    public MarvelResult() {
        mOffset = 0;
        mTotal = 0;
        mEntries = new ArrayList<>();
        mAttribution = "";
    }

    public int getmCount(){
        return mEntries.size();
    }

    public int getmOffset() {
        return mOffset;
    }

    public void setmOffset(int mOffset) {
        this.mOffset = mOffset;
    }

    public int getmTotal() {
        return mTotal;
    }

    public void setmTotal(int mTotal) {
        this.mTotal = mTotal;
    }

    public List<T> getmEntries() {
        return mEntries;
    }

    public void setmEntries(List<T> mEntries) {
        this.mEntries = mEntries;
    }

    public String getmAttribution() {
        return mAttribution;
    }

    public void setmAttribution(String mAttribution) {
        this.mAttribution = mAttribution;
    }
}
