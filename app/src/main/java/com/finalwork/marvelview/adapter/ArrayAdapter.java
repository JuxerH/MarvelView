package com.finalwork.marvelview.adapter;


import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//用于监听ViewItem的监听器
public abstract class ArrayAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private final OnItemClickListener mItemClickListener;//监听列表条目点击的接口
    private final List<T> mItems;

    public ArrayAdapter(@Nullable OnItemClickListener itemClickListener) {//传入一个监听列表的接口
        super();
        mItemClickListener = itemClickListener;
        mItems = new ArrayList<>();
        setHasStableIds(true);
    }

    @Override
    public abstract long getItemId(int position);

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public T getItem(int position) {
        if (mItems == null) {
            return null;
        }
        return mItems.get(position);
    }

    public void setItems(List<T> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    public List<T> getItems() {
        return new ArrayList<>(mItems);
    }

    public interface OnItemClickListener<T, VH extends RecyclerView.ViewHolder, VB extends ViewDataBinding> {
        void onItemClick(ArrayAdapter<T, VH> adapter, VB binding, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ViewDataBinding mBinding;

        public ViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
            mBinding.getRoot().setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION && mItemClickListener != null) {
                //noinspection unchecked
                mItemClickListener.onItemClick(ArrayAdapter.this, mBinding, position);
            }
        }
    }

}
