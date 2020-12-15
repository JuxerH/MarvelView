package com.finalwork.marvelview.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.finalwork.marvelview.BR;
import com.finalwork.marvelview.R;
import com.finalwork.marvelview.model.viewobject.SectionVO;

public class SectionAdapter extends ArrayAdapter<SectionVO, SectionAdapter.ViewHolder> {

    private final String mImageTransitionName;
    private final int mType;

    public SectionAdapter(Context context, @SectionVO.Type int type, OnItemClickListener listener) {
        super(listener);
        mImageTransitionName = context.getString(R.string.transition_section_image);
        mType = type;
    }

    @SectionVO.Type
    public int getType() {
        return mType;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_list_section, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mBinding.setVariable(BR.section, getItem(position));
        holder.mBinding.setVariable(BR.imageTransition, mImageTransitionName + mType + position);
        holder.mBinding.executePendingBindings();
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getmId();
    }

    public class ViewHolder extends ArrayAdapter.ViewHolder {
        public ViewHolder(ViewDataBinding binding) {
            super(binding);
        }
    }

}
