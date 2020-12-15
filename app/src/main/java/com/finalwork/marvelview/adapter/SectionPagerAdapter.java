package com.finalwork.marvelview.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.PagerAdapter;

import com.finalwork.marvelview.R;
import com.finalwork.marvelview.api.util.PagerSharedElementCallback;
import com.finalwork.marvelview.databinding.PageItemSectionBinding;
import com.finalwork.marvelview.model.viewobject.SectionVO;

import java.util.List;

public class SectionPagerAdapter extends PagerAdapter {

    private final FragmentActivity mActivity;
    private final LayoutInflater mInflater;
    private final int mType;
    private final List<SectionVO> mItems;
    private final PagerSharedElementCallback mSharedElementCallback;
    private final String mImageTransitionName;
    private int mInitialPosition;

    public SectionPagerAdapter(@NonNull FragmentActivity activity, @SectionVO.Type int type, @NonNull List<SectionVO> items, @NonNull PagerSharedElementCallback sharedElementCallback) {
        mActivity = activity;
        mInflater = LayoutInflater.from(activity);
        mType = type;
        mItems = items;
        mSharedElementCallback = sharedElementCallback;
        mImageTransitionName = activity.getString(R.string.transition_section_image);
    }

     public void setInitialPosition(int position) {
        mInitialPosition = position;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        PageItemSectionBinding binding = DataBindingUtil.inflate(mInflater, R.layout.page_item_section, container, false);
        SectionVO data = mItems.get(position);
        onViewBound(binding, position, data);
        container.addView(binding.getRoot());
        return binding;
    }

    private void onViewBound(PageItemSectionBinding binding, int position, SectionVO data) {
        String imageTransitionName = mImageTransitionName + mType + position;
        binding.setSection(data);
        binding.setImageTransition(imageTransitionName);
        binding.setImageListener(new ImageLoadingCallback(position));
    }

    private void startPostponedEnterTransition(int position) {
        if (position == mInitialPosition) {
            mActivity.supportStartPostponedEnterTransition();
        }
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (object instanceof PageItemSectionBinding) {
            mSharedElementCallback.setSharedElementViews(((PageItemSectionBinding) object).image);
        }
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return object instanceof PageItemSectionBinding
                && view.equals(((PageItemSectionBinding) object).getRoot());
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = ((PageItemSectionBinding) object).getRoot();
        container.removeView(view);
    }

    private class ImageLoadingCallback extends ImageLoadingListener {

        final int mPosition;

        ImageLoadingCallback(int position) {
            mPosition = position;
        }

        @Override
        public void onSuccess() {
            startPostponedEnterTransition(mPosition);
        }

        @Override
        public void onFailed(@NonNull Exception e) {
            startPostponedEnterTransition(mPosition);
        }

    }

}
