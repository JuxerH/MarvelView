package com.finalwork.marvelview.model.viewobject;

import android.widget.ImageView;

import androidx.annotation.IntDef;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.finalwork.marvelview.R;

import java.io.Serializable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
public class SectionVO  extends BaseObservable implements Serializable {

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
    @Bindable
    private String image;

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

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmThumbnail() {
        return mThumbnail;
    }

    public void setmThumbnail(String mThumbnail) {
        this.mThumbnail = mThumbnail;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String mImage) {
        this.image = mImage;
    }

    @BindingAdapter({"sectionImageUrl"})
    public static void loadImage(ImageView imageView, String image){
        Glide.with(imageView.getContext()).load(image).apply(new RequestOptions().placeholder(R.drawable.unknown)).into(imageView);
    }
}