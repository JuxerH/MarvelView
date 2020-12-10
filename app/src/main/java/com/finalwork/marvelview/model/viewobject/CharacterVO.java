package com.finalwork.marvelview.model.viewobject;


import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.List;

public class CharacterVO  extends BaseObservable implements Serializable  {

    private long mId;
    private String mName;
    private String mDescription;
    private String mThumbnail;
    @Bindable
    private String image;
    private List<SectionVO> mComics;
    private List<SectionVO> mSeries;
    private List<SectionVO> mStories;
    private List<SectionVO> mEvents;
    private String mDetail;
    private String mWiki;
    private String mComicLink;

    public CharacterVO() {
    }

    public long getId() {
        return mId;
    }

    public void setId(long mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(String mThumbnail) {
        this.mThumbnail = mThumbnail;
    }


    public List<SectionVO> getComics() {
        return mComics;
    }

    public void setComics(List<SectionVO> mComics) {
        this.mComics = mComics;
    }

    public List<SectionVO> getSeries() {
        return mSeries;
    }

    public void setSeries(List<SectionVO> mSeries) {
        this.mSeries = mSeries;
    }

    public List<SectionVO> getStories() {
        return mStories;
    }

    public void setStories(List<SectionVO> mStories) {
        this.mStories = mStories;
    }

    public List<SectionVO> getEvents() {
        return mEvents;
    }

    public void setEvents(List<SectionVO> mEvents) {
        this.mEvents = mEvents;
    }

    public String getDetail() {
        return mDetail;
    }

    public void setDetail(String mDetail) {
        this.mDetail = mDetail;
    }

    public String getWiki() {
        return mWiki;
    }

    public void setWiki(String mWiki) {
        this.mWiki = mWiki;
    }

    public String getComicLink() {
        return mComicLink;
    }

    public void setComicLink(String mComicLink) {
        this.mComicLink = mComicLink;
    }

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, String image){
        Glide.with(imageView.getContext()).load(image).into(imageView);
    }
    @Override
    public String toString() {
        return "Character{" + mId + ", '" + mName + "'}";
    }

}
