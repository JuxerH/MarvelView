package com.finalwork.marvelview.viewmodel;

import androidx.annotation.NonNull;


import com.finalwork.marvelview.adapter.SectionPagerAdapter;
import com.finalwork.marvelview.model.viewobject.SectionVO;
import com.finalwork.marvelview.view.SectionActivity;

import java.util.List;

public class SectionViewModel extends AbsViewModel<SectionVM.View> implements SectionVM.Actions ,SectionVM.View{

    private final List<SectionVO> mEntries;
    private final int mInitialPosition;
    private  SectionActivity sectionActivity;

    public SectionViewModel(@NonNull List<SectionVO> entries, int initialPosition, SectionActivity sectionActivity) {
        mEntries = entries;
        mInitialPosition = initialPosition;
        this.sectionActivity=sectionActivity;
    }

    @Override
    public void attachView(@NonNull SectionVM.View view) {
        super.attachView(view);
        mView.showItems(mEntries, mInitialPosition);
    }

    @Override
    public void closeClick() {
        mView.close();
    }

    @Override
    public void showItems(List<SectionVO> entries, int position) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(sectionActivity, sectionActivity.getmType(), entries, sectionActivity.getmSharedElementCallback());
        adapter.setInitialPosition(position);
        sectionActivity.getmViewPager().setAdapter(adapter);
        sectionActivity.getmViewPager().setCurrentItem(position, false);
    }

    @Override
    public void close() {
        sectionActivity.onBackPressed();
    }

}
