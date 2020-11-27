package com.finalwork.marvelview.model.viewobject;

import java.io.Serializable;
import java.util.List;

public class CharacterVO implements Serializable {

    private long mId;
    private String mName;
    private String mDescription;
    private String mThumbnail;
    private String mImage;
    private List<SectionVO> mComics;
    private List<SectionVO> mSeries;
    private List<SectionVO> mStories;
    private List<SectionVO> mEvents;
    private String mDetail;
    private String mWiki;
    private String mComicLink;

    public CharacterVO() {
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
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

    public List<SectionVO> getmComics() {
        return mComics;
    }

    public void setmComics(List<SectionVO> mComics) {
        this.mComics = mComics;
    }

    public List<SectionVO> getmSeries() {
        return mSeries;
    }

    public void setmSeries(List<SectionVO> mSeries) {
        this.mSeries = mSeries;
    }

    public List<SectionVO> getmStories() {
        return mStories;
    }

    public void setmStories(List<SectionVO> mStories) {
        this.mStories = mStories;
    }

    public List<SectionVO> getmEvents() {
        return mEvents;
    }

    public void setmEvents(List<SectionVO> mEvents) {
        this.mEvents = mEvents;
    }

    public String getmDetail() {
        return mDetail;
    }

    public void setmDetail(String mDetail) {
        this.mDetail = mDetail;
    }

    public String getmWiki() {
        return mWiki;
    }

    public void setmWiki(String mWiki) {
        this.mWiki = mWiki;
    }

    public String getmComicLink() {
        return mComicLink;
    }

    public void setmComicLink(String mComicLink) {
        this.mComicLink = mComicLink;
    }

    @Override
    public String toString() {
        return "Character{" + mId + ", '" + mName + "'}";
    }

}
