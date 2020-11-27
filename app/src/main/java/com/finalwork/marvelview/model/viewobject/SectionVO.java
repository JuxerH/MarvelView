package com.finalwork.marvelview.model.viewobject;

import androidx.annotation.IntDef;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
public class SectionVO implements Serializable {

    public static final int TYPE_COMIC = 0;
    public static final int TYPE_SERIES = 1;
    public static final int TYPE_STORY = 2;
    public static final int TYPE_EVENT = 3;

    @IntDef({TYPE_COMIC, TYPE_SERIES, TYPE_STORY, TYPE_EVENT})//以注解的形式替代使用枚举型
    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    private long mId;
    private String mTitle;
    private String mThumbnail;
    private String mImage;

    public SectionVO() {
    }

    @Override
    public String toString() {
        return "Section{" + mId + ", '" + mTitle + "'}";
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmThumbnail() {
        return mThumbnail;
    }

    public void setmThumbnail(String mThumbnail) {
        this.mThumbnail = mThumbnail;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }
}